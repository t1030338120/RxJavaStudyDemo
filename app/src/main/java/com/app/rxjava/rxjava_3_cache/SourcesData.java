package com.app.rxjava.rxjava_3_cache;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Simulates three different sources - one from memory, one from disk,
 * and one from network. In reality, they're all in-memory, but let's
 * play pretend.
 *
 * Observable.create() is used so that we always return the latest data
 * to the subscriber; if you use just() it will only return the data from
 * a certain point in time.
 *
 *  模拟三种不同的数据来源 - 一个来自内存，一个来磁盘，和一个来自网络。
 *  在真实的环境中，他们都在内存中，这里让我们先测试下。
 *
 * 使用Observable.create() 方法，可以保证返回到Subscriber的数据总是最新的，
 * 如果使用just() 方法，它只会返回在某一时间点上的数据。
 */
public class SourcesData {

    // Memory cache of data
    private Data memory = null;

    // What's currently "written" on disk
    private Data disk = null;

    // Each "network" response is different
    private int requestNumber = 0;


    // In order to simulate memory being cleared, but data still on disk
    public void clearMemory() {
        System.out.println("clear memory...");
        memory = null;
    }


    public void clearDiskAndMemory() {
        System.out.println("clear disk and memory...");
        memory = null;
        disk = null;
    }

    public Observable<Data> memory() {
        Observable<Data> observable = Observable.create(subscriber -> {
            subscriber.onNext(memory);
            subscriber.onCompleted();
        });

        return observable.compose(logSource("MEMORY"));
    }

    public Observable<Data> disk() {
        Observable<Data> observable = Observable.create(subscriber -> {
            subscriber.onNext(disk);
            subscriber.onCompleted();
        });

//        Observable<Data> observable = Observable.just(disk);

        // Cache disk responses in memory
        return observable.doOnNext(data -> memory = data)
            .compose(logSource("DISK"));
    }

    public Observable<Data> network() {
        Observable<Data> observable = Observable.create(new Observable.OnSubscribe<Data>() {
            @Override
            public void call(Subscriber<? super Data> subscriber) {
                requestNumber++;
                subscriber.onNext(new Data("Server Response #" + requestNumber));
                subscriber.onCompleted();
            }
        })
                .doOnNext(new Action1<Data>() {
                    @Override
                    public void call(Data data) {
                        disk = data;
                        memory = data;
                    }
                });


        // Save network responses to disk and cache in memory
        //保存网络数据到磁盘和内存中
        return observable.compose(logSource("NETWORK"));
    }

    // Simple logging to let us know what each source is returning
    Observable.Transformer<Data, Data> logSource(final String source) {

        return dataObservable -> dataObservable.doOnNext(data -> {
            if (data == null) {
                System.out.println(source + " does not have any data.");
            }
            else if (!data.isUpToDate()) {
                System.out.println(source + " has stale data.");
            }
            else {
                System.out.println(source + " has the data you are looking for!.");
            }
        });
    }

}