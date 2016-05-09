package com.app.rxjava.mvc.model;

import com.app.rxjava.retrofit.entity.IpInfoEntity;

import rx.Observable;
import rx.Subscriber;

/**
 * 描述：
 * 作者：tyc
 */
public interface IMvcIpInfoModel {

    void getIpInfo(Subscriber<IpInfoEntity> subscriber, String ip);

    Observable<IpInfoEntity> getIpInfo(String ip);
}
