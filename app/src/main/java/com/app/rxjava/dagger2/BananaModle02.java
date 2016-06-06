package com.app.rxjava.dagger2;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * 描述：
 *
 * 作者：tyc
 * 参考博客：http://blog.csdn.net/duo2005duo/article/details/50618171 http://blog.csdn.net/duo2005duo/article/details/50696166
 */
@Module //1  注明本类属于Module
public class BananaModle02 {

    @Named("Banana")
    @Provides //2 注明该方法是用来提供依赖对象的特殊方法
    public Fruit provideBFruit(){

        return new Banana("bbb香蕉sss");
    }
}
