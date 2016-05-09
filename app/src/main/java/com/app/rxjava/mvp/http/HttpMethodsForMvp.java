package com.app.rxjava.mvp.http;

import com.app.rxjava.mvp.model.ContentlistEntity;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 描述：Retrofit的封装类，Http请求都写在这个类中，
 *           如果有请求域名不同的情况可以单独再写个HttpMethods类来封装
 * 作者：tyc
 */
public class HttpMethodsForMvp {

    public static final String BASE_URL = "http://apis.baidu.com/showapi_open_bus/";
    private static final int DEFAULT_TIMEOUT = 5;

    private static Retrofit retrofit;
    private static JokeServiceApi jokeService;

    static {
        //开启okhttp提供的日志系统
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor);
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        jokeService = retrofit.create(JokeServiceApi.class);
    }


    //构造方法私有
    private HttpMethodsForMvp() { }


    public static <T> T createApi(Class<T> clazz) {
        return retrofit.create(clazz);
    }


    /**
     * 获取笑话数据
     * @param subscriber
     * @param page
     * @return
     */
    public static void getJoke(Observer<List<ContentlistEntity>> subscriber, int page) {
//          retrofit.create(JokeServiceApi.class)
        jokeService
                 .getJoke(page)
                  .subscribeOn(Schedulers.io())
                 .map(jokeEntity -> {
                     Gson gson = new Gson();
                     Logger.json(gson.toJson(jokeEntity));
                     return jokeEntity.getShowapi_res_body().getContentlist();
                 })
                .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(subscriber);
    }


}
