package com.app.rxjava.mvp.ui;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.app.rxjava.mvp.ui.view.IMvpView;

/**
 * 描述：
 * 作者：tyc
 */
public class BaseMvpActivity extends AppCompatActivity implements IMvpView {

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg, View.OnClickListener onClickListener) {

    }

    @Override
    public void showEmpty(String msg, View.OnClickListener onClickListener) {

    }

    @Override
    public void showEmpty(String msg, View.OnClickListener onClickListener, int imageId) {

    }

    @Override
    public void showNetError(View.OnClickListener onClickListener) {

    }
}
