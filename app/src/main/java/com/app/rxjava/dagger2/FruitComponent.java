package com.app.rxjava.dagger2;

import dagger.Component;

/**
 * 描述：
 * 参考博客：http://blog.csdn.net/duo2005duo/article/details/50618171 http://blog.csdn.net/duo2005duo/article/details/50696166
 * 作者：tyc
 */
@Component(modules ={AppleModle01.class, BananaModle02.class}) //3 指明Component在哪些Module中查找依赖
public interface FruitComponent { ////4 接口，自动生成实现

     void inject(Container container); // //5  注入方法，在Container中调用
}
