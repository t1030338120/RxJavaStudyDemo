package com.app.rxjava.mvp.presenter;

import com.app.rxjava.mvp.http.HttpMethodsForMvp;
import com.app.rxjava.mvp.model.ContentlistEntity;
import com.app.rxjava.mvp.ui.view.IJokeView;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;


public class JokePresenter extends BasePresenter<IJokeView> {


    private void loadList(final int page) {

        Subscriber<List<ContentlistEntity>> subscriber = new Subscriber<List<ContentlistEntity>>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.getMessage());
                getMvpView().showError(null, null);
            }

            @Override
            public void onNext(List<ContentlistEntity> contentlistEntities) {
                if (page == 1) {
                    getMvpView().refresh(contentlistEntities);
                } else {
                    getMvpView().loadMore(contentlistEntities);
                }
            }
        };

        HttpMethodsForMvp.getJoke(subscriber, page);
    }

    public void refresh(){
        loadList(1);
    }

    public void loadMore(int page){
        loadList(page);
    }

}
