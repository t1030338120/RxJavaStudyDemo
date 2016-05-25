package com.app.rxjava.http;

import com.app.rxjava.retrofit.entity.IpInfoEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface IpInfoService {

//    @GET("top250")
//    Call<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

//    @GET("service/getIpInfo.php")
//    Observable<IpInfoEntity> getIpInfoForRx(@Query("ip") String ip);

    @GET("service/getIpInfo.php")
    Call<IpInfoEntity> getIpInfo(@Query("ip") String ip);

    @GET("service/getIpInfo.php")
    Observable<HttpResult<IpInfoEntity>> getIpInfoForRx(@Query("ip") String ip);

}