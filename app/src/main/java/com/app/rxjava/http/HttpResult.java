package com.app.rxjava.http;

/**
 * 服务器返回的数据格式抽象类
 * @param <T>
 */
public class HttpResult<T>{

    private int code;
    private T data;


    public int getResultCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return data.toString();
    }


}