package com.hr.musicktv.net.http;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;


public class HttpUtils {


    //添加线程管理并订阅
    @SuppressWarnings("unchecked")
    public static void toSubscribe(Observable o,
                                   Observer s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
    //添加线程管理并订阅
    @SuppressWarnings("unchecked")
    public static void toSubscribe(Observable o,
                                   Observer s,
                                   ObservableTransformer transformer){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(s);
    }
    //添加线程管理并订阅
    public static RequestBody buildRequestBody(Object data){
        String jsonstring = getStringValue(data);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonstring);
    }
    public static RequestBody buildImageRequestBody(File file){
        return   RequestBody.create(okhttp3.MediaType.parse("image/png"), file);
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String getStringValue(Object obj) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        if(obj==null)
            return null;

        try {
            return  objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return null;
    }
}
