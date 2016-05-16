package com.app.rxjava;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Single;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.ConnectableObservable;
import rx.schedulers.Timestamped;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testRxJavaOnError() {
        String value = null;
        Observable.just("123")
                .doOnNext(s1 -> {
                    System.out.println("===doOnNext(" + s1 + ")");
                })
                .doOnCompleted(() -> {
                    System.out.println("===doOnCompleted( )");
                })
                .subscribe(s -> {
                    System.out.println("===onNext( )");
                    //                    System.out.println("===:" + value.length());
                }, throwable -> {
                    System.out.println("onError 异常信息：" + throwable.toString());
                });
    }

    public void testSingleOnError() {
        Single single01 = Single.just("123");
        Single single02 = Single.just("456");
        Single<ClassA> single = Single.zip(single01, single02, new Func2<String, String, ClassA>() {
            @Override
            public ClassA call(String s, String s2) {
                return new ClassA(s, s2);
            }
        });
        single.subscribe(classA -> System.out.println(classA.toString()));
    }


    public void testAsyncSubject() {
        AsyncSubject<String> asyncSubject = AsyncSubject.create();
        asyncSubject.subscribe(s -> System.out.println("1 === " + s));
        asyncSubject.onNext("123");
        asyncSubject.onNext("456");
        asyncSubject.onCompleted();
        asyncSubject.subscribe(s -> System.out.println("2 === " + s));
    }


    public void testBehaviorSubject() {
        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();

        behaviorSubject.subscribe(s -> System.out.println("0 === " + s)/*, throwable -> System.out.println(throwable.getMessage())*/);
        behaviorSubject.onNext("123");
        behaviorSubject.subscribe(s -> System.out.println("1 === " + s));
        behaviorSubject.onNext("456");
        behaviorSubject.subscribe(s -> System.out.println("2 === " + s));
        behaviorSubject.onCompleted();
    }


    public void testPublishSubject() {
        PublishSubject<String> publishSubject = PublishSubject.create();
        //        publishSubject.subscribe(s -> System.out.println("0 === " + s), throwable -> System.out.println(throwable.getMessage()));
        publishSubject.onNext("123");
        publishSubject.subscribe(s -> System.out.println("1 === " + s));
        publishSubject.onNext("456");
        publishSubject.subscribe(s -> System.out.println("2 === " + s));
        publishSubject.onNext("678");
        publishSubject.onCompleted();
    }


    public void testReplaySubject() {
        ReplaySubject<String> replaySubject = ReplaySubject.create();
        replaySubject.subscribe(s -> System.out.println("456====" + s));
        replaySubject.onNext("abc");
        replaySubject.onNext("123");
        replaySubject.onNext("456");
        replaySubject.onCompleted();
        replaySubject.subscribe(s -> System.out.println("====" + s));
    }


    public void testJustNull() {

        Observable.just(null)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o == null) {
                            System.out.println("===Observable发射的值为空");
                        }
                    }
                }/*, throwable -> System.out.println(throwable.getMessage())*/);
    }

    public void testRepart() {
        PublishSubject<String> publishSubject = PublishSubject.create();
        Observable observable = Observable.just("1");
        observable.range(1, 10);
        //        publishSubject.subscribe(s -> System.out.println("0 === " + s), throwable -> System.out.println(throwable.getMessage()));
        publishSubject.onNext("123");
        publishSubject.subscribe(s -> System.out.println("1 === " + s));
        publishSubject.onNext("456");
        publishSubject.subscribe(s -> System.out.println("2 === " + s));
        publishSubject.onNext("678");
        publishSubject.onCompleted();
    }

    public void testStartWith() {
        Observable<String> observable01 = Observable.just("456", "567", "789");
        Observable<String> observable02 = Observable.just("123");
        observable01.startWith(observable02)
                .subscribe(s1 -> System.out.println("===" + s1));
    }


    public void testTimestamp() {
        Observable<String> observable01 = Observable.just("456", "567", "789");
        observable01.timestamp()
                .subscribe(new Subscriber<Timestamped<String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Timestamped<String> stringTimestamped) {
                        System.out.println(stringTimestamped.toString());
                    }
                });
    }



    public void testBuffer() {
        Observable<String> observable01 = Observable.just("123", "456", "567", "789", "890", "ab", "bc");
        observable01
                .buffer(2, 3)
                .subscribe(strings -> System.out.println("===" + strings));
    }


    public void testWindow() {
        Observable<String> observable01 = Observable.just("123", "456", "567", "789", "890", "ab", "bc");
        observable01
                .window(3)
                .subscribe(new Subscriber<Observable<String>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("执行完毕");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Observable<String> stringObservable) {
                        stringObservable.subscribe(s -> System.out.println("===" + s));
                    }
                });
    }


    public void testFlatMapIterable() {
        //        Observable<String> observable01 = Observable.just("123", "456");
        //        observable01
        //                .flatMapIterable(new Func1<String, Iterable<String>>() {
        //                    @Override
        //                    public Iterable<String> call(String s) {
        //                        return new ArrayList<String>().add(s);
        //                    }
        //                })
        //                .subscribe(strings -> System.out.println("===" + strings));
    }

    /**
     * reduce()对Observable的发射的每一条数据都应用一个函数，最终一次发射
     */
    public void testReduce() {
        Observable<String> observable01 = Observable.just("123", "456", "789");
        observable01
                .reduce(new Func2<String, String, String>() {
                    @Override
                    public String call(String s, String s2) {
                        return s + s2;
                    }
                })
                .subscribe(s -> System.out.println("===" + s));
    }


    public void testDoOnEach() {
        Observable<String> observable = Observable.just("123", "456");
        observable
                .doOnEach(notification -> System.out.println("doOnEach()" + notification.toString()))
                .doOnSubscribe(() -> System.out.println("doOnSubscribe 执行了"))
                .doOnTerminate(() -> System.out.println("doOnTerminate 执行了"))
                .doOnCompleted(() -> System.out.println("doOnCompleted 执行了"))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("执行完毕");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("异常信息：" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("执行结果：" + s);
                        throw  new RuntimeException("测试错误处理");
                    }
                });
    }




    public void testMaterialize() {
        Observable<String> observable = Observable.just("123", "456");
        observable
                .materialize()
                .subscribe(stringNotification -> System.out.println(stringNotification.toString()));
    }

    /**
     * 将普通的Observable转换为ConnectableObservable
     */
    public void testPublish() {
        Observable<Long> observable01 = Observable.interval(3, TimeUnit.SECONDS);

        ConnectableObservable<Long> connectableObservable = observable01.publish();
        connectableObservable.subscribe(s -> System.out.println("===" + s));
        connectableObservable.connect();

        connectableObservable.subscribe(s1 -> System.out.println("===s1" + s1));
    }


    public void testBlockingObservable() {
        Observable<String> observable = Observable.just("123", "456");
        String value = observable.toBlocking().firstOrDefault("abc", new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return "45d6".equals(s);
            }
        });
        System.out.println("value : " + value);
    }


    public void testBlockingObservableSingle() {
        Observable<String> observable = Observable.just("123");
        String value = observable.toBlocking().single();
        System.out.println("value : " + value);
    }


    public void testBlockingObservableSingled() {
        Observable<String> observable = Observable.empty();
        String value = observable.toBlocking().singleOrDefault("abc");
        System.out.println("value : " + value);
    }

    /**
     * all()判定Observable发射的所有数据是否都满足某个条件
     */
    public void testAll() {
        Observable<String> observable01 = Observable.just("12", "456", "789");
        observable01
                .all(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.length() > 3;
                    }
                })
                .subscribe(aBoolean -> System.out.println("===" + aBoolean));
    }

    /**
     * skipWhile 丢弃Observable发射的数据，直到一个指定的条件不成立
     */
    public void testSkipWhile() {
        Observable<String> observable01 = Observable.just("12", "456", "789", "ab");
        observable01
                .skipWhile(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return !"456".equals(s);
                    }
                })
                .subscribe(s -> System.out.println("===" + s));
    }

    /**
     * takeWhile 发射Observable发射的数据，直到一个指定的条件不成立
     */
    public void testTakeWhile() {
        Observable<String> observable01 = Observable.just("12", "456", "789", "ab");
        observable01
                .takeWhile(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return !"789".equals(s);
                    }
                })
                .subscribe(s -> System.out.println("===" + s));
    }


    public void testSkipUnit() {
        //        Observable<String> observable01 = Observable.just("12", "456", "789", "ab");
        //        Observable<String> observable02 = Observable.just("a", "b", "c", "d");
        //        observable01
        //                .skipUntil(observable02)
        //                .subscribe(s1 -> System.out.println(s1));
        //
        //        observable02.subscribe(s -> System.out.println(s));

    }

    public void testSwitchMap() {
        ArrayList<String> list01 = new ArrayList<String>();
        list01.add("12");
        list01.add("23");
        list01.add("34");
        list01.add("45");

        ArrayList<String> list02 = new ArrayList<String>();
        list02.add("ab");
        list02.add("bc");
        list02.add("cd");
        list02.add("de");

        Observable.just(list01)
                .flatMap(new Func1<ArrayList<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(ArrayList<String> strings) {
                        return Observable.from(strings);
                    }
                })
                .subscribe(s -> System.out.println("===" + s));
    }

    /**
     * 只有观察者订阅的时候才会创建Observable对象
     * defer 延时
     */
    public void testDefer() {

        final String[] values = {"hello", "jake", "Tom", "123"};
        Observable<String> observable1 = Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                int index = new Random().nextInt(4);
                return Observable.just(values[index]);
            }
        });

        observable1.subscribe(s -> System.out.println("===" + s));
        observable1.subscribe(s -> System.out.println("===" + s));
    }


    class ClassA {
        String value01;
        String value02;

        public ClassA(String value01, String value02) {
            this.value01 = value01;
            this.value02 = value02;
        }

        @Override
        public String toString() {
            return value01 + value02;
        }
    }

}