package com.hr.musicktv.net.Service;

import com.hr.musicktv.net.base.BaseDataResponse;
import com.hr.musicktv.net.base.BaseResponse;
import com.hr.musicktv.net.entry.response.GetUserInfo;
import com.hr.musicktv.net.entry.response.InfoToken;
import com.hr.musicktv.net.entry.response.UserInfo;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 用户服务
 */
public interface UserService {

    //每个机顶盒内置一个加密的用户帐号，开启APP时，携带此加密账号登录，返回访问服务接口的token
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("User/UserAutoLogin")
    Observable<Response<BaseResponse<InfoToken>>>
    userAutoLogin(@Body RequestBody route);

    //获取用户信息，传入登录时获取的token，及影院id（固定为1）
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("User/Validate")
    Observable<Response<BaseResponse<BaseDataResponse<UserInfo>>>>
    validate(@Body RequestBody route);

    //获取用户的基本信息，用于用户中心的显示
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("User/GetUserInfo")
    Observable<Response<BaseResponse<BaseDataResponse<GetUserInfo>>>>
    GetUserInfo(@Body RequestBody route);

}
