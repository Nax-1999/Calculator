package com.example.calculator.mvp;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculator.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {

    MainPresenter presenter;
    TextView textView;

    private static final String SET = "set";
    private static final String NUMBER = "number";
    private static final String OPERATOR = "operator";

    private Button selectedOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        textView = findViewById(R.id.result);
        presenter.reset();

        initImmersionBar();
    }

    private void initImmersionBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //注意要清除 FLAG_TRANSLUCENT_STATUS flag
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
    }

    @Override
    public void saveNumber(String tag) {
        presenter.saveNumber(tag);
    }

    @Override
    public void saveOperator(String tag) {
        presenter.saveOperator(tag);
    }

    @Override
    public void reset() {
        presenter.reset();
    }

    @Override
    public void refreshTextView(String result) {
        if (result.contains(".") && result.charAt(result.length() - 1) == '0')
            result = result.substring(0, result.length() - 2);
        textView.setText(result);
    }

    @Override
    public void showToast(String string) {
        Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void resetOperatorBg() {
        if (selectedOperator != null) {
            selectedOperator.setBackground(getDrawable(R.drawable.yellow_button_circle_bg));
            selectedOperator.setTextColor(getResources().getColor(R.color.white));
            selectedOperator = null;
        }
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        Button button = (Button) v;
        if (SET.equals(tag)) {
            reset();
        } else if (NUMBER.equals(tag)) {
            saveNumber(button.getText().toString());
        } else if (OPERATOR.equals(tag)) {
            saveOperator(button.getText().toString());
            showToast("点击了运算符");
            if (!button.getText().equals("=")) {
                onOperatorSelected(button);
            }
        } else {
            doOpposite();
        }
    }

    private void onOperatorSelected(Button button) {
        if (selectedOperator != null) {
            selectedOperator.setBackground(getDrawable(R.drawable.yellow_button_circle_bg));
            selectedOperator.setTextColor(getResources().getColor(R.color.white));
        }
        button.setBackground(getDrawable(R.drawable.operator_btn_selected_bg));
        button.setTextColor(getResources().getColor(R.color.yellow));
        selectedOperator = button;
    }

    private void doOpposite() {
        presenter.doOpposite();
    }
}