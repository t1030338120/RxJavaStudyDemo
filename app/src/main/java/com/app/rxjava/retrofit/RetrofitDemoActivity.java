package com.app.rxjava.retrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.rxjava.BaseActivity;
import com.app.rxjava.R;
import com.app.rxjava.http.HttpMethods;
import com.app.rxjava.http.HttpResult;
import com.app.rxjava.http.IpInfoService;
import com.app.rxjava.retrofit.entity.IpInfoEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 参考：RxJava 与 Retrofit 结合的最佳实践
 * http://gank.io/post/56e80c2c677659311bed9841
 */
public class RetrofitDemoActivity extends BaseActivity {

    static final String TAG = "Rxjava-Retrofit";
    private Subscriber<IpInfoEntity> mIpInfoEntitySubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCusContentView(R.layout.activity_retrofit_demo);
    }


    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_rtf_01:
                getIpInfo();

                break;
            case R.id.btn_rtf_02:
                getIpInfoForRx();

                break;
            case R.id.btn_rtf_03:
                getIpInfoForHttpMethods();

                break;
            case R.id.btn_rtf_04:
                cancleRequest();

                break;
        }
    }


    /**
     * 第一步：Retrofit访问网络请求（最简单的demo V2.0之前的示例），Callback方式实现
     */
    private void getIpInfo() {
        String baseUrl = "http://ip.taobao.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IpInfoService movieService = retrofit.create(IpInfoService.class);
        Call<IpInfoEntity> call = movieService.getIpInfo("63.223.108.42");
        call.enqueue(new Callback<IpInfoEntity>() {
            @Override
            public void onResponse(Call<IpInfoEntity> call, Response<IpInfoEntity> response) {
                Log.i(TAG, response.toString());
                Log.i(TAG, "请求网络成功，请重新请求！");
            }

            @Override
            public void onFailure(Call<IpInfoEntity> call, Throwable t) {
                Log.i(TAG, "请求网络失败，请重新请求！");
            }
        });
    }

    /**
     * 第二步：Retrofit访问网络请求，V2.0 Rxjava实现
     */
    private void getIpInfoForRx() {
        String baseUrl = "http://ip.taobao.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IpInfoService movieService = retrofit.create(IpInfoService.class);
        movieService.getIpInfoForRx("63.223.108.42")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult<IpInfoEntity>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "请求网络执行完毕！");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "请求网络失败，请重新请求！");
                    }

                    @Override
                    public void onNext(HttpResult<IpInfoEntity> ipInfoEntity) {
                        Log.i(TAG, ipInfoEntity.toString());
                        Log.i(TAG, "请求网络成功，请重新请求！");
                    }
                });
    }


    /**
     * 第三步： Retrofit访问网络请求，相同格式的Http请求数据统一进行预处理（错误码处理）
     */
    private void getIpInfoForHttpMethods() {
        mIpInfoEntitySubscriber = new Subscriber<IpInfoEntity>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "请求网络执行完毕！");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "请求网络失败，请重新请求！" + e.getMessage());
                //                e.printStackTrace();
            }

            @Override
            public void onNext(IpInfoEntity ipInfoEntity) {
                Log.i(TAG, ipInfoEntity.toString());
                Log.i(TAG, "请求网络成功，请重新请求！");
            }
        };
        HttpMethods.getInstance().getIpInfoForRx(mIpInfoEntitySubscriber, "63.223.108.42");
    }


    /**
     * 取消网络请求
     */
    private void cancleRequest() {
        if (mIpInfoEntitySubscriber != null && !mIpInfoEntitySubscriber.isUnsubscribed()) {
            mIpInfoEntitySubscriber.unsubscribe();
        }
    }


}
