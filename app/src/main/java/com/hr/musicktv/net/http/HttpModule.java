package com.hr.musicktv.net.http;




import com.hr.musicktv.net.Service.ComService;
import com.hr.musicktv.net.Service.UserService;
import com.hr.musicktv.net.base.BaseService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by 吕 on 2017/10/26.
 */
@Module
public class HttpModule {
    protected static final String APP_CODE = "AppCode";
    protected static final String APP_CODE_VALUE = "ANDROID";

    protected static final String BASEURLONE = "http://testapi.hwhrq.com/api/";

    //用户服务
    @Provides
    @Singleton
    public UserService userService(){
        HttpServiceSetting httpServiceSetting = new HttpServiceSetting(BASEURLONE);
        httpServiceSetting.builderCallback = new OkHttpRequestBuilderCallback() {
            @Override
            public void builder(Request.Builder builder) {
                builder.addHeader(APP_CODE, APP_CODE_VALUE);    //设置请求头
            }
        };
        httpServiceSetting.logLevel = HttpLoggingInterceptor.Level.BODY;
        Retrofit retrofit = OkHttpClientUtils.buildRetrofit(httpServiceSetting);
        return  retrofit.create(UserService.class);
    }

    @Provides
    @Singleton
    public ComService comService(){
        HttpServiceSetting httpServiceSetting = new HttpServiceSetting(BASEURLONE);
        httpServiceSetting.builderCallback = new OkHttpRequestBuilderCallback() {
            @Override
            public void builder(Request.Builder builder) {
                builder.addHeader(APP_CODE, APP_CODE_VALUE);    //设置请求头
            }
        };
        httpServiceSetting.logLevel = HttpLoggingInterceptor.Level.BODY;
        Retrofit retrofit = OkHttpClientUtils.buildRetrofit(httpServiceSetting);
        return  retrofit.create(ComService.class);
    }

    @Provides
    @Singleton
    public BaseService baseService(){
        return new BaseService();
    }
}
