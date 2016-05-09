package com.app.rxjava.mvp.ui.view;

import com.app.rxjava.mvp.model.ContentlistEntity;

import java.util.List;

/**
 * 描述：主界面的功能抽象化（刷新和加载更多）
 * 作者：tyc
 */
public interface IJokeView extends IMvpView {

     void refresh(List<ContentlistEntity> data);
     void loadMore(List<ContentlistEntity> data);

}
