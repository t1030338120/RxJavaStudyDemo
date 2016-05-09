package com.app.rxjava.mvvm.bean;

public class User {
    private final String name;
    private final int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // getXXX形式
    public String getName() {
        return this.name;
    }

    // 或者属性名和方法名相同
    public int age() {
        return this.age;
    }
}