package com.example.calculator.mvp;

import android.text.TextUtils;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//计算器数据模型类
public class MainModel {

    //fixme 不如处理输入的时候先用StringBuffer去接，等要做到计算了再转换成double？

    @IntDef({ADD, SUB, MULTIPLE, DIVIDE, MOLD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Operator {
    }

    public final static int ADD = 0;
    public final static int SUB = 1;
    public final static int MULTIPLE = 2;
    public final static int DIVIDE = 3;
    public final static int MOLD = 4;

    private StringBuffer currentInput; //当前输入
    private double result; //计算结果（包括阶段性和最终）
    private int operator; //运算符

    boolean isNew; //是否刚被重置
    boolean hasSpot; //是否有小数点
    boolean spotIsAtTop; //小数点是不是刚被输入
    boolean hasResult; //之前是否已经有过计算结果

    public void reset() {
        isNew = true;
        hasSpot = false;
        spotIsAtTop = false;
        hasResult = false;

        currentInput = new StringBuffer("0");
        result = 0;
        operator = -1;
    }

    public String setNumber(int number) {
        if (Double.parseDouble(currentInput.toString()) == 0 && !currentInput.toString().contains("."))
            currentInput = new StringBuffer();
        currentInput.append(number);
        isNew = false;
        spotIsAtTop = false;
        return currentInput.toString();
    }

    public String addSpot() {
        if (hasSpot)
            return "";
        hasSpot = true;
        spotIsAtTop = true;
        currentInput.append(".");
        return currentInput.toString();
    }

    public double getResult() {
        cal();
        return result;
    }

    public String setOperator(@Operator int operator) {
        hasSpot = false;
        spotIsAtTop = false;
        if (isNew)
            return "false";
        if (hasResult) {
            if (!TextUtils.isEmpty(currentInput))
                cal();
            this.operator = operator;
        } else {
            saveResult();
            this.operator = operator;
            return "";
        }
        return String.valueOf(result);
    }

    private void saveResult() {
        result = convertDouble();
        hasResult = true;
        currentInput = new StringBuffer("0");
    }

    private double convertDouble() {
        if (spotIsAtTop)
            currentInput = currentInput.deleteCharAt(currentInput.length() - 1);
        return Double.parseDouble(currentInput.toString());
    }

    private void cal() {
        isNew = false;
        switch (operator) {
            case ADD:
                result += convertDouble();
                break;
            case SUB:
                result -= convertDouble();
                break;
            case MULTIPLE:
                result *= convertDouble();
                break;
            case DIVIDE:
                result /= convertDouble();
                break;
            case MOLD:
                result %= convertDouble();
                break;
        }
        currentInput = new StringBuffer("0");
    }

    //取反
    public String doOpposite() {
        if (isNew)
            return "";
        //这个if处理的是等号后的结果数
        if (currentInput.toString().equals("0")) {
            result = -result;
            return String.valueOf(result);
        }
        //这个if处理正在输入的数
        if (currentInput.toString().contains("-")) {
            currentInput.deleteCharAt(0);
        } else {
            currentInput.insert(0, "-");
        }
        return currentInput.toString();
    }
}
