package com.app.rxjava.dagger2;


import javax.inject.Inject;
import javax.inject.Named;

/**
 * 描述：
 * 作者：tyc
 * 参考博客：http://blog.csdn.net/duo2005duo/article/details/50618171
 *                 http://blog.csdn.net/duo2005duo/article/details/50696166
 */
public class Container {

    @Named("Apple")
    @Inject //6 添加@Inject，标记mFruit可以被注入
    public Fruit mApple;

    @Named("Banana")
    @Inject //6 添加@Inject，标记mFruit可以被注入
    public Fruit mBanana;




    public void init(){
//        DaggerFruitComponent.create().inject(this); //7 使用FruitComponent的实现类注入

    }

}
