package com.app.rxjava.mvp.presenter;


import com.app.rxjava.mvp.ui.view.IMvpView;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 * 在app中的每一个presenter，都还必须另外实现或继承BasePresenter，关联上MvpView。（Presenter 关联上 View）
 */
public interface IMvpPresenter<V extends IMvpView> {

    void attachView(V mvpView);

    void detachView();
}
