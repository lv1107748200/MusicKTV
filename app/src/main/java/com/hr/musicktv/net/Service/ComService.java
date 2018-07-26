package com.hr.musicktv.net.Service;

import com.hr.musicktv.net.base.BaseDataResponse;
import com.hr.musicktv.net.base.BaseResponse;
import com.hr.musicktv.net.entry.response.Comment;
import com.hr.musicktv.net.entry.response.Detail;
import com.hr.musicktv.net.entry.response.FavoriteList;
import com.hr.musicktv.net.entry.response.MKGetRecTop;
import com.hr.musicktv.net.entry.response.MKGetStarList;
import com.hr.musicktv.net.entry.response.MKSearch;
import com.hr.musicktv.net.entry.response.SearchList;
import com.hr.musicktv.net.entry.response.VideoDisLike;
import com.hr.musicktv.net.entry.response.WhatList;
import com.hr.musicktv.net.entry.response.WhatType;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ComService {

    //1 获取K歌首页四个推荐榜单信息
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("KVideo/GetRecTop")
    Observable<Response<BaseResponse<BaseDataResponse<MKGetRecTop>>>>
    GetRecTop();
    //2 搜索歌曲
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("KVideo/Search")
    Observable<Response<BaseResponse<BaseDataResponse<MKSearch>>>>
    Search(@Body RequestBody route);
    //3 获取歌手列表
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("KVideo/GetStarList")
    Observable<Response<BaseResponse<BaseDataResponse<MKGetStarList>>>>
    GetStarList();
    //4 获取语种列表
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("KVideo/GetLanguageList")
    Observable<Response<BaseResponse<BaseDataResponse<MKGetStarList>>>>
    GetLanguageList();
    //5 获取曲风列表
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("KVideo/GetStyleList")
    Observable<Response<BaseResponse<BaseDataResponse<MKGetStarList>>>>
    GetStyleList();
    //6 获取歌曲播放地址
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("KVideo/Play")
    Observable<Response<BaseResponse<BaseDataResponse<Detail>>>>
    Play(@Body RequestBody route);


    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST
    Observable<Response<BaseResponse<BaseDataResponse<MKGetStarList>>>>
    ComList(@Url String url);
}
