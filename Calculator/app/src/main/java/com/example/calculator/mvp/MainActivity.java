package com.example.calculator.mvp;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculator.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener{

    MainPresenter presenter;
    TextView textView;

    private static final String SET = "set";
    private static final String NUMBER = "number";
    private static final String OPERATOR = "operator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        textView = findViewById(R.id.result);
        presenter.reset();
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
        } else {
            doOpposite();
        }
    }

    private void doOpposite() {
        presenter.doOpposite();
    }
}