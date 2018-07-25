package com.hr.musicktv.widget.music;


import android.content.Context;
import android.view.SurfaceHolder;

import com.danikula.videocache.HttpProxyCacheServer;
import com.hr.musicktv.base.BaseApplation;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/*
 * lv   2018/7/24
 */
public class CoreWhat {

    private CallBack callBack;

    public void setUrl(String url ,SurfaceHolder holder){

    }

    public void start(){

    }

    public void stop(){

    }

    public void pause(){

    }
    public void release(){

    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    protected String setCache(String url, boolean isCache, Context context){
        if(isCache){
            HttpProxyCacheServer proxy = BaseApplation.getProxy(context);
            url = proxy.getProxyUrl(url);
        }

        return url;
    }



    public interface CallBack{
         void onCompletion();
         void onPrepared();
         void onSeekComplete();
         void onError();
    }
}
