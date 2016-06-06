package com.app.rxjava.dagger2;

/**
 * 描述：
 * 作者：tyc
 */
public class Banana implements Fruit {
    String name;

    public Banana(String name) {
        this.name = name;
    }


    @Override
    public String getName() {
        return name;
    }

}
