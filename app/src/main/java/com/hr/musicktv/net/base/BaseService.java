package com.hr.musicktv.net.base;





import com.hr.musicktv.base.BaseApplation;

import com.hr.musicktv.net.Service.ComService;
import com.hr.musicktv.net.Service.UserService;
import com.hr.musicktv.net.entry.request.AutoLogin;
import com.hr.musicktv.net.entry.request.MKSearch;
import com.hr.musicktv.net.entry.request.WhatCom;
import com.hr.musicktv.net.entry.response.Comment;
import com.hr.musicktv.net.entry.response.Detail;
import com.hr.musicktv.net.entry.response.FavoriteList;
import com.hr.musicktv.net.entry.response.MKGetRecTop;
import com.hr.musicktv.net.entry.response.MKGetStarList;
import com.hr.musicktv.net.entry.response.SearchList;
import com.hr.musicktv.net.entry.response.GetUserInfo;
import com.hr.musicktv.net.entry.response.InfoToken;
import com.hr.musicktv.net.entry.response.UserInfo;
import com.hr.musicktv.net.entry.response.VideoDisLike;
import com.hr.musicktv.net.entry.response.WhatList;
import com.hr.musicktv.net.entry.response.WhatType;
import com.hr.musicktv.net.http.HttpCallback;
import com.hr.musicktv.net.http.HttpUtils;
import com.hr.musicktv.net.subscriber.HttpSubscriber;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import okhttp3.RequestBody;

/**
 * Created by 吕 on 2017/10/27.
 */

public class BaseService {

    @Inject
    public UserService userService;
    @Inject
    public ComService comService;

    public BaseService() {
        BaseApplation.getBaseApp().getAppComponent().inject(this);
    }


    //1 获取K歌首页四个推荐榜单信息
    @SuppressWarnings("unchecked")
    public void GetRecTop(
            HttpCallback<BaseResponse<BaseDataResponse<MKGetRecTop>>> httpCallback,
            ObservableTransformer transformer
    ){

       // RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.GetRecTop();

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<MKGetRecTop>>(httpCallback)
                ,transformer
        );
    }
    //2 搜索歌曲
    @SuppressWarnings("unchecked")
    public void Search(
            MKSearch data,
            HttpCallback<BaseResponse<BaseDataResponse<com.hr.musicktv.net.entry.response.MKSearch>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.Search(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<com.hr.musicktv.net.entry.response.MKSearch>>(httpCallback)
                ,transformer
        );
    }
    //3 获取歌手列表
    @SuppressWarnings("unchecked")
    public void GetStarList(
            HttpCallback<BaseResponse<BaseDataResponse<MKGetStarList>>> httpCallback,
            ObservableTransformer transformer
    ){

      //  RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.GetStarList();

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<MKGetStarList>>(httpCallback)
                ,transformer
        );
    }
    //4 获取语种列表
    @SuppressWarnings("unchecked")
    public void GetLanguageList(
            HttpCallback<BaseResponse<BaseDataResponse<MKGetStarList>>> httpCallback,
            ObservableTransformer transformer
    ){

       // RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.GetLanguageList();

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<MKGetStarList>>(httpCallback)
                ,transformer
        );
    }
    //5 获取曲风列表
    @SuppressWarnings("unchecked")
    public void GetStyleList(
            HttpCallback<BaseResponse<BaseDataResponse<MKGetStarList>>> httpCallback,
            ObservableTransformer transformer
    ){

      //  RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.GetStyleList();

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<MKGetStarList>>(httpCallback)
                ,transformer
        );
    }
    //6 获取歌曲播放地址
    @SuppressWarnings("unchecked")
    public void Play(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<Detail>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.Play(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<Detail>>(httpCallback)
                ,transformer
        );
    }

    @SuppressWarnings("unchecked")
    public void ComList(
            String url,
            HttpCallback<BaseResponse<BaseDataResponse<MKGetStarList>>> httpCallback,
            ObservableTransformer transformer
    ){

      //  RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.ComList(url);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<MKGetStarList>>(httpCallback)
                ,transformer
        );
    }

}
