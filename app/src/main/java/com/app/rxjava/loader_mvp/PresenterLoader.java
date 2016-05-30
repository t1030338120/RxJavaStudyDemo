package com.app.rxjava.loader_mvp;

import android.content.Context;
import android.support.v4.content.Loader;

/**
 * 描述：
 * 作者：tyc
 */
public class PresenterLoader<T extends Presenter> extends Loader<T> {

    private PresenterFactory<T> factory;
    private T presenter;

    public PresenterLoader(Context context) {
        super(context);
    }


    @Override
    protected void onForceLoad() {
        super.onForceLoad();

        // 通过工厂来实例化Presenter
        presenter = factory.create();

        // 返回Presenter
        deliverResult(presenter);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        // 如果已经有Presenter实例那就直接返回
        if (presenter != null) {
            deliverResult(presenter);
            return;
        }

        // 如果没有
        forceLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        presenter.onDestroyed();
        presenter = null;
    }
}
