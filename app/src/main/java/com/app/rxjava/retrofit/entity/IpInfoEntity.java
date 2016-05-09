package com.app.rxjava.retrofit.entity;

/**
 * 查询IP，服务端返回的数据对象
 */
public class IpInfoEntity {
    public String country;
    public String country_id;
    public String area;
    public String area_id;
    public String ip;



    @Override
    public String toString() {
        return "country : "+country+"  country_id : "+country_id+"  area:"+area+"  area_id:"+area_id+" ip:"+ip;
    }

}