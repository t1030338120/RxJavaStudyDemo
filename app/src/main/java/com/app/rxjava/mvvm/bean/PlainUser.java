package com.app.rxjava.mvvm.bean;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

public class PlainUser {
    private ObservableField<String> name = new ObservableField<>();
    private ObservableInt age = new ObservableInt();

    public PlainUser(ObservableField<String> name, ObservableInt age) {
        this.name = name;
        this.age = age;
    }


    public ObservableInt getAge() {
        return age;
    }

    public ObservableField<String> getName() {
        return name;
    }



}