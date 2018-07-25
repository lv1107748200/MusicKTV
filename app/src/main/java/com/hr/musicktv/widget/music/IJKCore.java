package com.hr.musicktv.widget.music;


import android.content.Context;
import android.media.AudioManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hr.musicktv.utils.CheckUtil;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

/*
 * lv   2018/7/24
 */
public class IJKCore extends CoreWhat implements
         IMediaPlayer.OnBufferingUpdateListener
        ,IMediaPlayer.OnCompletionListener
        ,IMediaPlayer.OnErrorListener
        ,IMediaPlayer.OnInfoListener
        ,IMediaPlayer.OnPreparedListener
        ,IMediaPlayer.OnSeekCompleteListener
        ,IMediaPlayer.OnTimedTextListener
        ,IMediaPlayer.OnVideoSizeChangedListener
{

    private IjkMediaPlayer mediaPlayer;

    private Context context;

    public IJKCore(Context context) {
        this.context = context;
    }

    @Override
    public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {

    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        if(null != getCallBack())
        getCallBack().onCompletion();
    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        if(null != getCallBack())
            getCallBack().onError();
        return false;
    }

    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        if(null != getCallBack())
            getCallBack().onPrepared();
    }

    @Override
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {
        if(null != getCallBack())
            getCallBack().onSeekComplete();
    }

    @Override
    public void onTimedText(IMediaPlayer iMediaPlayer, IjkTimedText ijkTimedText) {

    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {

    }

    @Override
    public void start() {
        super.start();
        if(null != mediaPlayer){
            mediaPlayer.start();
        }
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void setUrl(String url ,SurfaceHolder holder) {
        super.setUrl(url,holder);
        initMediaPlayer(url,holder);
    }

    @Override
    public void release() {
        super.release();
        if(mediaPlayer != null){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
    }

    public IjkMediaPlayer initMediaPlayer(String url ,SurfaceHolder holder){
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
        if (false) {
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 1);
        }

        if(CheckUtil.isEmpty(url)){
            return mediaPlayer;
        }

        setCache(url,false,context);

        mediaPlayer.setDisplay(holder);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnVideoSizeChangedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnInfoListener(this);
        mediaPlayer.setOnTimedTextListener(this);


        mediaPlayer.prepareAsync();

        return  mediaPlayer;
    }
}
