package com.app.rxjava.loader;

import android.content.Context;
import android.support.v4.content.Loader;

import java.util.Random;

/**
 * 描述：
 * 作者：tyc
 */
public class TastLoader extends Loader<String> {

    String data  ;

    String[] datas = {"Activity", "Fragment", "Intent and IntentFilter", "SharePreshare", "DiskCache", "IntentService"};


    public TastLoader(Context context) {
        super(context);
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        System.out.println("=========Loader ：onForceLoad( )执行了");

        data = datas[new Random().nextInt(datas.length)];

        deliverResult(data);
    }


    @Override
    public void deliverResult(String data) {
        System.out.println("=========Loader ：deliverResult( )执行了");

        if(isReset()){
            if(data != null){
                data = null;
            }
        }


        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(data);
        }

    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        System.out.println("=========Loader ：onStopLoading( )执行了");

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
        super.onAbandon();
        System.out.println("=========Loader ：onAbandon( )执行了");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        System.out.println("=========Loader ：onStartLoading( )执行了");

        // 如果已经有data实例那就直接返回
        if (data != null) {
            deliverResult(data);
            return;
        }

        // 如果没有
        forceLoad();
    }
}
