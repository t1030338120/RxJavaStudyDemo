package com.app.rxjava.http;

import com.app.rxjava.retrofit.entity.IpInfoEntity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 描述：Retrofit的封装类，Http请求都写在这个类中，
 *           如果有请求域名不同的情况可以单独再写个HttpMethods类来封装
 * 作者：tyc
 */
public class HttpMethods {

//    public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    public static final String BASE_URL = "http://ip.taobao.com/";
    private static final int DEFAULT_TIMEOUT = 5;

    private static HttpMethods mInstance;
    private Retrofit retrofit;
    private IpInfoService movieService;

    //构造方法私有
    private HttpMethods() {

        //开启okhttp提供的日志系统
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //配置OkHttpClient
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor);
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        movieService = retrofit.create(IpInfoService.class);
    }


/*    //在访问HttpMethods时创建单例 静态方式创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return INSTANCE;
    }*/

    //获取单例
    public static HttpMethods getInstance(){
        if(mInstance == null){
            synchronized (HttpMethods.class){
                if(mInstance == null){
                    mInstance = new HttpMethods();
                }
            }
        }
        return mInstance;
    }


    /**
     * 第一步：
     * @param subscriber 由调用者传过来的观察者对象
     */
//    public void getIpInfo(Subscriber<IpInfoEntity> subscriber, String ip){
//        movieService.getIpInfoForRx(ip)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }


    /**
     * 第二步：
     * @param subscriber 由调用者传过来的观察者对象
     */
//    public void getIpInfo(Subscriber<HttpResult<IpInfoEntity>> subscriber, String ip){
//        movieService.getIpInfoForRx(ip)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }

    /**
     * 第三步：统一对返回服务器返回的数据做了统一处理
     * @param subscriber 调用者传过来的观察者对象
     * @param ip 请求参数
     */
    public void getIpInfoForRx(Subscriber<IpInfoEntity> subscriber, String ip){
        movieService.getIpInfoForRx(ip)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new HttpResultFunc<IpInfoEntity>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     *
     * @param ip
     * @return 返回一个网络请求的Observalbe
     */
    public Observable<IpInfoEntity> getIpInfoForRx02(String ip){
        return movieService.getIpInfoForRx(ip)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new HttpResultFunc<IpInfoEntity>())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getResultCode() != 100) {
                throw new ApiException(100);
            }
            return httpResult.getData();
        }
    }







}
