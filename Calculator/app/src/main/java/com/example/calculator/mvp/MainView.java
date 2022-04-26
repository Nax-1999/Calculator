package com.example.calculator.mvp;

import android.view.View;

public interface MainView {

    //todo 计算器业务抽象  1、数字输入与显示(TextView就做完了) 2、清空当前输入数字/运算符 3、重置计算器 4、输入运算符 5、得到结果

    void saveNumber(String tag);

    void saveOperator(String tag);

    void reset();

    void refreshTextView(String result);

    void showToast(String string);


}
