package com.app.rxjava.mvvm.common;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

/**
 * 描述：
 * 作者：tyc
 */
public class MyStringUtils {

    public static String convertName(String val){
        String value = String.format("用户姓名：%s", val);
        return value;
    }

    public static String convertAge(int val){
        String value = String.format("用户年龄：%s", val);
        return value;
    }

    public static String convertName01(ObservableField<String> val){
        String value = String.format("用户姓名：%s", val.get());
        return value;
    }

    public static String convertAge(ObservableInt val){
        String value = String.format("用户年龄：%s", val.get());
        return value;
    }

}
