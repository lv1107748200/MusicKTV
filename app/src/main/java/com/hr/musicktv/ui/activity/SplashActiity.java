package com.hr.musicktv.ui.activity;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;

import com.hr.musicktv.R;
import com.hr.musicktv.base.BaseActivity;
import com.hr.musicktv.net.base.BaseDataRequest;
import com.hr.musicktv.net.base.BaseDataResponse;
import com.hr.musicktv.net.base.BaseResponse;
import com.hr.musicktv.net.entry.request.AutoLogin;
import com.hr.musicktv.net.entry.response.InfoToken;
import com.hr.musicktv.net.entry.response.UserInfo;
import com.hr.musicktv.net.http.HttpCallback;
import com.hr.musicktv.net.http.HttpException;
import com.hr.musicktv.utils.CheckUtil;
import com.hr.musicktv.utils.NLog;
import com.hr.musicktv.widget.layout.LoadingLayout;
import com.hr.musicktv.widget.single.UserInfoManger;

import butterknife.BindView;


/**
 * Created by 吕 on 2018/3/13.
 */

public class SplashActiity extends BaseActivity implements LoadingLayout.LoadingCallBack {


    @BindView(R.id.load_relayout)
    LoadingLayout load_relayout;

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void init() {
        super.init();

        //load_relayout.setLoadingCallBack(this);
        //userAutoLogin();

        Intent intent = new Intent();
        intent.setClass(SplashActiity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void userAutoLogin(){

    }

    private void validate(){

    }


    private void getV(){
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
// 屏幕宽度（像素）
        int width = metric.widthPixels;
// 屏幕高度（像素）
        int height = metric.heightPixels;
// 屏幕密度（1.0 / 1.5 / 2.0）
        float density = metric.density;
// 屏幕密度DPI（160 / 240 / 320）
        int densityDpi = metric.densityDpi;
        String info = "机顶盒型号: " + android.os.Build.MODEL
                + ",\nSDK版本:" + android.os.Build.VERSION.SDK
                + ",\n系统版本:" + android.os.Build.VERSION.RELEASE
                + "\n屏幕宽度（像素）: "+width
                + "\n屏幕高度（像素）: " + height
                + "\n屏幕密度 : "+density
                +"\n屏幕密度DPI: "+densityDpi;
        NLog.e(NLog.TAGOther, info);
    }

    @Override
    public void btnCallBack() {
        load_relayout.setLoadingLayout(LoadingLayout.ONE,null);
        userAutoLogin();
    }
}
