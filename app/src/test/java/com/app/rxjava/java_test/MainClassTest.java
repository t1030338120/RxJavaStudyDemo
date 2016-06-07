package com.app.rxjava.java_test;

import com.app.rxjava.dagger2.Container;
import com.app.rxjava.dagger2.DaggerFruitComponent;

import org.junit.Test;

import java.lang.Override;
import java.lang.String;

import rx.Observable;
import rx.functions.Action1;

/**
 * 描述：
 * 作者：tyc
 */
public class MainClassTest {

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



    @Test
    public void testRxJava(){

        Observable.just("123")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("===="+s);
                    }
                });

    }


}