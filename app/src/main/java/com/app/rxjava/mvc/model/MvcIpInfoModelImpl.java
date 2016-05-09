package com.app.rxjava.mvc.model;

import com.app.rxjava.http.HttpMethods;
import com.app.rxjava.retrofit.entity.IpInfoEntity;

import rx.Observable;
import rx.Subscriber;

/**
 * 描述：MVC模式中的Model业务层
 * 作者：tyc
 */
public class MvcIpInfoModelImpl implements IMvcIpInfoModel {

    @Override
    public void getIpInfo(Subscriber<IpInfoEntity> subscriber, String ip) {
        HttpMethods.getInstance().getIpInfoForRx(subscriber, ip);
    }

    @Override
    public Observable<IpInfoEntity> getIpInfo(String ip) {
        return HttpMethods.getInstance().getIpInfoForRx02(ip);
    }
}
