package com.app.rxjava.dagger2;

/**
 * 描述：
 * 参考博客：http://blog.csdn.net/duo2005duo/article/details/50618171 http://blog.csdn.net/duo2005duo/article/details/50696166
 * 作者：tyc
 */
public class Apple implements Fruit {

    String name;

    public Apple(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
