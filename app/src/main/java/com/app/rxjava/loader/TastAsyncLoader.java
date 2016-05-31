package com.app.rxjava.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.Random;

/**
 * 描述：
 * 作者：tyc
 */
public class TastAsyncLoader extends AsyncTaskLoader<String> {


    String data  ;

    String[] datas = {"Activity", "Fragment", "Intent and IntentFilter", "SharePreshare", "DiskCache", "IntentService"};

    public TastAsyncLoader(Context context) {
        super(context);
    }

    @Override
    public String loadInBackground() {

        System.out.println("=========Loader ：loadInBackground( )执行了");
        return datas[new Random().nextInt(datas.length)];
    }


    @Override
    public void forceLoad() {
        super.forceLoad();
    }

    @Override
    protected void onForceLoad() {
        System.out.println("=========Loader ：onForceLoad( )执行了");
        super.onForceLoad();
    }


    @Override
    public void deliverResult(String data) {
        System.out.println("=========Loader ：deliverResult( )执行了");


        if(isReset() && data != null){
            data = null;
        }

        if(isStarted()){
        super.deliverResult(data);
        }

    }

    @Override
    protected void onStopLoading() {
        System.out.println("=========Loader ：onStopLoading( )执行了");
        super.onStopLoading();

        cancelLoad();
    }



    @Override
    protected void onReset() {
        System.out.println("=========Loader ：onReset( )执行了");
        super.onReset();

        // 确保Loader已经stoped
        onStopLoading();


        // 如果有需要，在这里我们可以释放app的资源
        if (data != null) {
            data = null;
        }
    }



    @Override
    protected boolean onCancelLoad() {
        System.out.println("=========Loader ：onCancelLoad( )执行了");
        return super.onCancelLoad();
    }

    @Override
    protected void onAbandon() {
        System.out.println("=========Loader ：onAbandon( )执行了");
        super.onAbandon();
    }

    @Override
    protected void onStartLoading() {
        System.out.println("=========Loader ：onStartLoading( )执行了");
        super.onStartLoading();

        // 如果已经有data实例那就直接返回
        if (data != null) {
            deliverResult(data);
            return;
        }

        // 如果没有
        forceLoad();
    }


    @Override
    public void onCanceled(String data) {
        System.out.println("=========Loader ：onCanceled( )执行了");
        super.onCanceled(data);
    }
}
