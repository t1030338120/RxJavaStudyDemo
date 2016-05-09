package com.app.rxjava.rxjava_3_cache;

import rx.Observable;

/**
 * 描述：Rxjava实现数据的3级缓存功能（内存->磁盘->网络）
 * 开发者前线：http://www.devtf.cn/?p=764
 * Github：https://github.com/dlew/rxjava-multiple-sources-sample
 */
public class CacheManager {

    private static SourcesData sources = new SourcesData();

    public static void getData() {
        // Create our sequence for querying best available data
        Observable<Data> source = Observable.concat(sources.memory(), sources.disk(), sources.network())
                .first(data -> data != null && data.isUpToDate());

        //第一次加载数据
        source.repeat(2)
                .subscribe(data ->System.out.println("###############Received: " + data.value) );

        //清除内存缓存，获取磁盘缓存数据
        sources.clearMemory();
        source.repeat(2)
                .subscribe(data -> System.out.println("###############Received: " + data.value));

        //清除磁盘和内存缓存，获取网络数据
        sources.clearDiskAndMemory();
        source.repeat(2)
                .subscribe(data -> System.out.println("###############Received: " + data.value));

        //休眠3秒钟，模拟数据已过期，获取网络数据
        sleep(3 * 1000);
        source.repeat(1)
                .subscribe(data -> System.out.println("###############Received: " + data.value));


//        // "Request" latest data once a second 每隔一秒发送一次
//        Observable.interval(1, TimeUnit.SECONDS)
//                .flatMap(__ -> source)
//                .subscribe(data -> System.out.println("Received: " + data.value));
//
//        // Occasionally clear memory (as if app restarted) so that we must go to disk
//        //清除内存缓存，获取磁盘缓存数据
//        Observable.interval(3, TimeUnit.SECONDS)
//                .subscribe(__ -> sources.clearMemory());
//
//        // Java will quit unless we idle
//        sleep(15 * 1000);
    }

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
            // Ignore
        }
    }

}
