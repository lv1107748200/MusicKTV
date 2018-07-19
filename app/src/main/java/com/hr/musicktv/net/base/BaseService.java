package com.hr.musicktv.net.base;





import com.hr.musicktv.base.BaseApplation;

import com.hr.musicktv.net.Service.ComService;
import com.hr.musicktv.net.Service.UserService;
import com.hr.musicktv.net.entry.request.AutoLogin;
import com.hr.musicktv.net.entry.request.WhatCom;
import com.hr.musicktv.net.entry.response.Comment;
import com.hr.musicktv.net.entry.response.Detail;
import com.hr.musicktv.net.entry.response.FavoriteList;
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

    //根据栏目id查询所有的文章或视频
    @SuppressWarnings("unchecked")
    public void userAutoLogin(
            AutoLogin data,
            HttpCallback<BaseResponse<InfoToken>> httpCallback
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  userService.userAutoLogin(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<InfoToken>(httpCallback)
        );
    }
    ////获取用户信息，传入登录时获取的token，及影院id（固定为1）
    @SuppressWarnings("unchecked")
    public void validate(
            BaseDataRequest data,
            HttpCallback<BaseResponse<BaseDataResponse<UserInfo>>> httpCallback
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  userService.validate(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<UserInfo>>(httpCallback)
        );
    }
    //获取用户的基本信息，用于用户中心的显示
    @SuppressWarnings("unchecked")
    public void GetUserInfo(
            BaseDataRequest data,
            HttpCallback<BaseResponse<BaseDataResponse<GetUserInfo>>> httpCallback
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  userService.GetUserInfo(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<GetUserInfo>>(httpCallback)
        );
    }

    //1 所有分类
    @SuppressWarnings("unchecked")
    public void AllInOneType(
            HttpCallback<BaseResponse<BaseDataResponse<WhatType>>> httpCallback,
            ObservableTransformer transformer
    ) {

        Observable observable =  comService.AllInOneType();
        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatType>>(httpCallback),
                transformer
        );
    }
    //2 获取电影分类
    @SuppressWarnings("unchecked")
    public void FilmType(
            HttpCallback<BaseResponse<BaseDataResponse<WhatType>>> httpCallback,
            ObservableTransformer transformer
    ) {

        Observable observable =  comService.FilmType();
        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatType>>(httpCallback),
                transformer
        );
    }
    //3 获取电视剧分类
    @SuppressWarnings("unchecked")
    public void TVType(
            HttpCallback<BaseResponse<BaseDataResponse<WhatType>>> httpCallback,
            ObservableTransformer transformer
    ) {

        Observable observable =  comService.TVType();
        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatType>>(httpCallback),
                transformer
        );
    }
    //4 获取综艺分类
    @SuppressWarnings("unchecked")
    public void VarietyType(
            HttpCallback<BaseResponse<BaseDataResponse<WhatType>>> httpCallback,
            ObservableTransformer transformer
    ) {

        Observable observable =  comService.VarietyType();
        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatType>>(httpCallback),
                transformer
        );
    }
    //5 获取动漫分类
    @SuppressWarnings("unchecked")
    public void AnimeType(
            HttpCallback<BaseResponse<BaseDataResponse<WhatType>>> httpCallback,
            ObservableTransformer transformer
    ) {

        Observable observable =  comService.AnimeType();
        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatType>>(httpCallback),
                transformer
        );
    }
    //6 获取体育分类
    @SuppressWarnings("unchecked")
    public void SportType(
            HttpCallback<BaseResponse<BaseDataResponse<WhatType>>> httpCallback,
            ObservableTransformer transformer
    ) {

        Observable observable =  comService.SportType();
        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatType>>(httpCallback),
                transformer
        );
    }
    //7 获取记录片分类
    @SuppressWarnings("unchecked")
    public void DocumentaryType(
            HttpCallback<BaseResponse<BaseDataResponse<WhatType>>> httpCallback,
            ObservableTransformer transformer
    ) {

        Observable observable =  comService.DocumentaryType();
        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatType>>(httpCallback),
                transformer
        );
    }
    //8 获取华人圈分类
    @SuppressWarnings("unchecked")
    public void CircleType(
            HttpCallback<BaseResponse<BaseDataResponse<WhatType>>> httpCallback,
            ObservableTransformer transformer
    ) {

        Observable observable =  comService.CircleType();
        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatType>>(httpCallback),
                transformer
        );
    }
    //9 获取游戏分类
    @SuppressWarnings("unchecked")
    public void GameType(
            HttpCallback<BaseResponse<BaseDataResponse<WhatType>>> httpCallback,
            ObservableTransformer transformer
    ) {

        Observable observable =  comService.GameType();
        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatType>>(httpCallback),
                transformer
        );
    }
    //10 电影
    @SuppressWarnings("unchecked")
    public void Film(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<WhatList>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.Film(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatList>>(httpCallback)
                ,transformer
        );
    }
    //11 电视剧
    @SuppressWarnings("unchecked")
    public void TV(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<WhatList>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.TV(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatList>>(httpCallback)
                ,transformer
        );
    }
    //12 综艺
    @SuppressWarnings("unchecked")
    public void Variety(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<WhatList>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.Variety(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatList>>(httpCallback)
                ,transformer
        );
    }
    //13 动漫
    @SuppressWarnings("unchecked")
    public void Anime(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<WhatList>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.Anime(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatList>>(httpCallback)
                ,transformer
        );
    }
    //14 体育
    @SuppressWarnings("unchecked")
    public void Sport(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<WhatList>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.Sport(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatList>>(httpCallback)
                ,transformer
        );
    }
    //15 记录片
    @SuppressWarnings("unchecked")
    public void Documentary(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<WhatList>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.Documentary(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatList>>(httpCallback)
                ,transformer
        );
    }
    //16 华人
    @SuppressWarnings("unchecked")
    public void Circle(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<WhatList>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.Circle(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatList>>(httpCallback)
                ,transformer
        );
    }
    //17 游戏
    @SuppressWarnings("unchecked")
    public void Game(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<WhatList>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.Game(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatList>>(httpCallback)
                ,transformer
        );
    }

    //18 踩影片
    @SuppressWarnings("unchecked")
    public void VideoDisLike(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<VideoDisLike>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.VideoDisLike(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<VideoDisLike>>(httpCallback)
                ,transformer
        );
    }
    //19点赞影片
    @SuppressWarnings("unchecked")
    public void VideoLike(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<VideoDisLike>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.VideoLike(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<VideoDisLike>>(httpCallback)
                ,transformer
        );
    }
    //20收藏影片
    @SuppressWarnings("unchecked")
    public void AddToScj(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<VideoDisLike>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.AddToScj(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<VideoDisLike>>(httpCallback)
                ,transformer
        );
    }
    //21删除收藏夹
    @SuppressWarnings("unchecked")
    public void RemoveFavorite(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<String>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.RemoveFavorite(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<String>>(httpCallback)
                ,transformer
        );
    }
    //22查询收藏夹
    @SuppressWarnings("unchecked")
    public void FavoriteList(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<FavoriteList>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.FavoriteList(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<FavoriteList>>(httpCallback)
                ,transformer
        );
    }
    //23搜索服务
    @SuppressWarnings("unchecked")
    public void Search(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<SearchList>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.Search(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<SearchList>>(httpCallback)
                ,transformer
        );
    }
    //24获取首页推荐列表
    @SuppressWarnings("unchecked")
    public void index(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<WhatList>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.index(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatList>>(httpCallback)
                ,transformer
        );
    }
    //25 获取影片详情
    @SuppressWarnings("unchecked")
    public void Detail(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<Detail>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.Detail(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<Detail>>(httpCallback)
                ,transformer
        );
    }
    //26 获取影片播放地址
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
    //27 获取评论列表
    @SuppressWarnings("unchecked")
    public void CommentList(
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<Comment>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.CommentList(body);
        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<Comment>>(httpCallback)
                ,transformer
        );
    }

    // 通用分类 （可以传url）
    @SuppressWarnings("unchecked")
    public void ComType(
            String url,
            HttpCallback<BaseResponse<BaseDataResponse<WhatType>>> httpCallback,
            ObservableTransformer transformer
    ) {

        Observable observable =  comService.ComType(url);
        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatType>>(httpCallback),
                transformer
        );
    }
    // 通用列表
    @SuppressWarnings("unchecked")
    public void ComList(
            String url,
            WhatCom data,
            HttpCallback<BaseResponse<BaseDataResponse<WhatList>>> httpCallback,
            ObservableTransformer transformer
    ){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  comService.ComList(body,url);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<WhatList>>(httpCallback)
                ,transformer
        );
    }
}
