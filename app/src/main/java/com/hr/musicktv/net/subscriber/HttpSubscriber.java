package com.hr.musicktv.net.subscriber;


import com.hr.musicktv.net.base.BaseDataResponse;
import com.hr.musicktv.net.base.BaseResponse;
import com.hr.musicktv.net.http.HttpCallback;
import com.hr.musicktv.net.http.HttpException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;


/*
 */
public class HttpSubscriber<T extends BaseDataResponse> implements Observer<Response<BaseResponse<T>>> {
    HttpCallback callback;

    public HttpSubscriber(HttpCallback callback) {
        super();
        this.callback = callback;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
      //  NToast.shortToastBaseApp("访问失败!");
        if (callback != null) {
            callback.onError(new HttpException(-100,e.getMessage()));
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    @SuppressWarnings("unchecked")
    public void onNext(Response<BaseResponse<T>> httpResultResponse) {
        if(httpResultResponse.code()==200){
           BaseResponse<T> result = httpResultResponse.body();
            if(result.getRet() == 200 ){
                BaseDataResponse baseDataResponse = result.getData();
                //code为0表示没有错误，code为1表示有错误
                if(null != baseDataResponse){
                    if(baseDataResponse.getCode() == 0){
                        if (callback != null) {
                            callback.onSuccess(result);
                        }
                    }else if(baseDataResponse.getCode() == 1){
                        if (callback != null) {
                            callback.onError(new HttpException(1,baseDataResponse.getMsg()));
                        }
                    }
                }

            } else {
                    if (callback != null) {
                         callback.onError(new HttpException(result.getRet(),result.getMsg()));
                    }
            }
        } else {
            if (callback != null) {
                callback.onError(new HttpException(httpResultResponse.code(),httpResultResponse.message()));
            }
        }
    }



}
