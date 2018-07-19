package com.hr.musicktv.net.http;



public interface HttpCallback<T>{

    void onError(HttpException e);

    void onSuccess(T t);
}
