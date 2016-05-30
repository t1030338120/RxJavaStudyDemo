package com.app.rxjava.loader_mvp;

/**
 * 描述：
 * 作者：tyc
 */
public interface Presenter<V> {

    void onViewAttached(V view);
    void onViewDetached();
    void onDestroyed();
}
