package com.hr.musicktv.widget.single;


import android.media.AudioManager;

import com.hr.mylibrary.utils.GSYVideoType;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by 吕 on 2018/3/28.
 */

public class IjkPlayerMger {

    volatile private static IjkPlayerMger instance = null;

    public static IjkMediaPlayer mediaPlayer = null;

    public static IjkPlayerMger getInstance(){
        if(instance == null){
            synchronized (IjkPlayerMger.class) {
                if(instance == null){
                    instance = new IjkPlayerMger();
                }
            }
        }

        return instance;
    }

    public static IjkMediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public IjkMediaPlayer initMediaPlayer(String url){
            if(null == mediaPlayer){
                mediaPlayer = new IjkMediaPlayer();
            }else {
                mediaPlayer.reset();
            }
            mediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //循环播放
           // mediaPlayer.setLooping(true);
        //SeekTo的时候，会跳回到拖动前的位置
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1);
            //屏幕常亮
            mediaPlayer.setScreenOnWhilePlaying(true);


        //开启硬解码
            if (GSYVideoType.isMediaCodec()) {
                mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
                mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
                mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 1);
            }

            try {
                mediaPlayer.setDataSource(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return  mediaPlayer;
    }

    public void setOnDesry(){
        if(null != mediaPlayer){

            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();

            mediaPlayer = null;

            IjkMediaPlayer.native_profileEnd();
        }
        instance = null;
    }

}
