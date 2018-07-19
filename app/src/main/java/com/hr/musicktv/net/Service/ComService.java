package com.hr.musicktv.net.Service;

import com.hr.musicktv.net.base.BaseDataResponse;
import com.hr.musicktv.net.base.BaseResponse;
import com.hr.musicktv.net.entry.response.Comment;
import com.hr.musicktv.net.entry.response.Detail;
import com.hr.musicktv.net.entry.response.FavoriteList;
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

    //1 所有分类
    @GET("List/AllInOneType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> AllInOneType();
    //2 获取电影分类
    @GET("List/FilmType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> FilmType();
    //3 获取电视剧分类
    @GET("List/TVType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> TVType();
    //4 获取综艺分类
    @GET("List/VarietyType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> VarietyType();
    //5 获取动漫分类
    @GET("List/AnimeType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> AnimeType();
    //6 获取体育分类
    @GET("List/SportType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> SportType();
    //7 获取记录片分类
    @GET("List/DocumentaryType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> DocumentaryType();
    //8 获取华人圈分类
    @GET("List/CircleType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> CircleType();
    //9 获取游戏分类
    @GET("List/GameType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> GameType();

    //10 电影
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/Film")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    Film(@Body RequestBody route);
    //11 电视剧
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/TV")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    TV(@Body RequestBody route);
    //12 综艺
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/Variety")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    Variety(@Body RequestBody route);
    //13 动漫
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/Anime")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    Anime(@Body RequestBody route);
    //14 体育
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/Sport")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    Sport(@Body RequestBody route);
    //15 记录片
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/Documentary")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    Documentary(@Body RequestBody route);
    //16 华人
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/Circle")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    Circle(@Body RequestBody route);
    //17 游戏
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/Game")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    Game(@Body RequestBody route);

    //18 踩影片
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("Video/VideoDisLike")
    Observable<Response<BaseResponse<BaseDataResponse<VideoDisLike>>>>
    VideoDisLike(@Body RequestBody route);
    //19点赞影片
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("Video/VideoLike")
    Observable<Response<BaseResponse<BaseDataResponse<VideoDisLike>>>>
    VideoLike(@Body RequestBody route);
    //20收藏影片
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("Video/AddToScj")
    Observable<Response<BaseResponse<BaseDataResponse<VideoDisLike>>>>
    AddToScj(@Body RequestBody route);
    //21删除收藏夹
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/RemoveFavorite")
    Observable<Response<BaseResponse<BaseDataResponse<String>>>>
    RemoveFavorite(@Body RequestBody route);
    //22查询收藏夹
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/FavoriteList")
    Observable<Response<BaseResponse<BaseDataResponse<FavoriteList>>>>
    FavoriteList(@Body RequestBody route);
    //23搜索服务
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/Search")
    Observable<Response<BaseResponse<BaseDataResponse<SearchList>>>>
    Search(@Body RequestBody route);
    //24获取首页推荐列表
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/index")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    index(@Body RequestBody route);
    //25 获取影片详情
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("Video/Detail")
    Observable<Response<BaseResponse<BaseDataResponse<Detail>>>>
    Detail(@Body RequestBody route);
    //26 获取影片播放地址
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("Video/Play")
    Observable<Response<BaseResponse<BaseDataResponse<Detail>>>>
    Play(@Body RequestBody route);
    //27 获取评论列表
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("Video/CommentList")
    Observable<Response<BaseResponse<BaseDataResponse<Comment>>>>
    CommentList(@Body RequestBody route);


    // FIXME: 2018/7/12   动态 改变 URL
    // 通用分类 （可以传url）
    @GET
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>>
    ComType(@Url String url);
    // 通用列表
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    ComList(@Body RequestBody route,@Url String url);

}
