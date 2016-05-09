package com.app.rxjava.java_test;

import android.app.IntentService;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 描述：
 * 作者：tyc
 */
public class MyClass {

    public static void main(String[] args){
        System.out.println("============");

        IntentService intentService;
        ViewGroup viewGroup;
        View view;
        LinearLayout linearLayout;
        AsyncTask asyncTask;


        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        threadLocal.set(12);
        System.out.println("main thread : " + threadLocal.get());

        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set(21);
                System.out.println("01 thread : " + threadLocal.get());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set(31);
                System.out.println("02 thread : " + threadLocal.get());
            }
        }).start();


        System.out.println("03 thread : " + threadLocal.get());
    }
}
