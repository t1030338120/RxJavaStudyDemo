package com.app.rxjava.mvvm.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.app.rxjava.BR;

public class ObservableUser extends BaseObservable {
    private  String name;
    private  int age;

    public ObservableUser(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Bindable
    public int getAge() {
        return this.age;
    }

    @Bindable
    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }


}