package com.example.calculator.mvp;

import android.text.TextUtils;

public class MainPresenter {

    MainView view;
    MainModel model;

    public MainPresenter(MainView view) {
        this.view = view;
        model = new MainModel();
    }

    public void saveNumber(String text) {
        String res = "";
        if (text.equals(".")) {
            res = model.addSpot();
        } else {
            res = model.setNumber(Integer.parseInt(text));
        }
        if (!TextUtils.isEmpty(res))
            refreshTextView(res);
    }

    public void saveOperator(String text) {
        String res = "";
        switch (text) {
            //fixme 取反不是算数操作，和清空操作类似
            case "%" :
                res = model.setOperator(MainModel.MOLD);
                break;
            case "/" :
                res = model.setOperator(MainModel.DIVIDE);
                break;
            case "*" :
                res = model.setOperator(MainModel.MULTIPLE);
                break;
            case "-" :
                res = model.setOperator(MainModel.SUB);
                break;
            case "+" :
                res = model.setOperator(MainModel.ADD);
                break;
            case "=" :
                refreshTextView(String.valueOf(model.getResult()));
                view.resetOperatorBg();
                return;
        }
        if (!res.equals("")) {
            refreshTextView(res);
            return;
        }
        view.resetOperatorBg();
        refreshTextView("0");
    }

    public void reset() {
        model.reset();
        refreshTextView("0");
    }

    private void refreshTextView(String result) {
        view.refreshTextView(result);
    }

    public void doOpposite() {
        String string = model.doOpposite();
        if (string.equals(""))
            return;
        refreshTextView(string);
    }
}
