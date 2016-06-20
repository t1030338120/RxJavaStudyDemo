package com.app.rxjava.java_test;

import com.app.rxjava.dagger2.Container;
import com.app.rxjava.dagger2.DaggerFruitComponent;

import org.junit.Test;

/**
 * 描述：
 * 作者：tyc
 */
public class Dagger2Test {

    @Test
    public void testInsertSort() throws Exception {
        System.out.println("=================");
    }

    @Test
    public void testDagger2() throws Exception {

        Container container = new Container();
        DaggerFruitComponent.create().inject(container);
        System.out.println(container.mApple.getName());
        System.out.println(container.mBanana.getName());
    }


}