package com.hr.musicktv.widget.music;


import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.danikula.videocache.HttpProxyCacheServer;
import com.hr.musicktv.R;
import com.hr.musicktv.base.BaseActivity;
import com.hr.musicktv.base.BaseApplation;
import com.hr.musicktv.utils.CheckUtil;
import com.hr.musicktv.utils.DisplayUtils;
import com.hr.musicktv.utils.NLog;
import com.hr.musicktv.widget.pop.CustomPopuWindConfig;
import com.hr.musicktv.widget.pop.FunctionMenuPopWindow;
import com.hr.musicktv.widget.pop.MusicSelectPopWindow;
import com.hr.mylibrary.utils.GSYVideoType;

import java.io.IOException;

import io.reactivex.disposables.Disposable;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

/*
 * lv   2018/7/24
 */
public class MusicPlayerLayout extends BaseLayout
        implements SurfaceHolder.Callback2
    ,IMediaPlayer.OnBufferingUpdateListener
    ,IMediaPlayer.OnCompletionListener
    ,IMediaPlayer.OnErrorListener
    ,IMediaPlayer.OnInfoListener
    ,IMediaPlayer.OnPreparedListener
    ,IMediaPlayer.OnSeekCompleteListener
    ,IMediaPlayer.OnTimedTextListener
    ,IMediaPlayer.OnVideoSizeChangedListener
    ,MethodWhat,MethodView
{
    private volatile String url = null;
    private SurfaceView surfaceView;
    private IjkMediaPlayer mediaPlayer;
    private MusicControlView musicControlView;

    private volatile boolean isCreated;

    public MusicPlayerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        surfaceView = new SurfaceView(context);
        surfaceView.getHolder().addCallback(this);
        addView(surfaceView,getParams());
    }

    @Override
    public void addConView(Context context) {
        musicControlView = new MusicControlView(context);
        musicControlView.setMethodView(this);
        addView(musicControlView,getParams());
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isCreated = true;
        initMediaPlayer(url);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    @Override
    public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {

    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        if(null != musicControlView){
            musicControlView.onCompletion();
        }
    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        if(null != musicControlView){
            musicControlView.onError();
        }
        return false;
    }

    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        if(null != musicControlView){
            musicControlView.onPrepared();
        }
    }

    @Override
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {
        if(null != musicControlView){
            musicControlView.onSeekComplete();
        }
    }

    @Override
    public void onTimedText(IMediaPlayer iMediaPlayer, IjkTimedText ijkTimedText) {

    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {

    }
    @Override
    public void start() {
        if(null != musicControlView){
            musicControlView.start();
        }
        if(null != mediaPlayer){
            mediaPlayer.start();
        }
    }

    @Override
    public void vStart() {
        if(null != mediaPlayer){
            if(mediaPlayer.isPlayable()){
                start();
            }
        }
    }

    @Override
    public void stop() {
        if(null != musicControlView){
            musicControlView.stop();
        }
        if(null != mediaPlayer){
            mediaPlayer.stop();
        }
    }
    @Override
    public void change() {//换集
        if(null != musicControlView){
            musicControlView.change();
        }

            stop();
            initMediaPlayer(url);//换集
    }
    @Override
    public void rebroadcast() {//重新播放
        if(null != musicControlView){
            musicControlView.rebroadcast();
        }

        stop();
        initMediaPlayer(url);
    }
    @Override
    public void release(){
        if(null != musicControlView){
            musicControlView.release();
        }
        if(null != mediaPlayer){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer.release();//释放资源
        }

    }

    @Override
    public void speed() {
        if(null != musicControlView){
            musicControlView.speed();
        }
    }

    @Override
    public void fastReverse() {
        if(null != musicControlView){
            musicControlView.fastReverse();
        }
    }

    @Override
    public void songList() {
        if(null != musicControlView){
            musicControlView.songList();
        }
    }
    @Override
    public void pause() {

        if(null != musicControlView){
            musicControlView.onPause();
        }

        if(null != mediaPlayer){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }
        }
    }

    @Override
    public void originalSinger() {//原唱
        if(null != mediaPlayer){
            mediaPlayer.selectTrack(1);
        }
    }

    @Override
    public void accompany() {//半场
        if(null != mediaPlayer){
            mediaPlayer.selectTrack(2);
        }
    }

    @Override
    public void tone(int is) {//升降调

    }
    @Override
    public long getCurrentPosition() {
        if(null != mediaPlayer){
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public long getTotalPosition() {
        if(null != mediaPlayer){
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    @Override
    public boolean getIsPlayer() {

        if(null != mediaPlayer){
            return mediaPlayer.isPlaying();
        }

        return false;
    }

    @Override
    public void select() {

    }

    @Override
    public void onCenter() {

        if(null != musicControlView){
            musicControlView.onCenter();
        }

        if(null != mediaPlayer){
            if(mediaPlayer.isPlaying()){
                pause();
            }else {
                if(mediaPlayer.isPlayable()){
                    start();
                }
            }
        }
    }

    @Override
    public void onMenu() {
        if(null != musicControlView){
            musicControlView.onMenu();
        }
    }

    @Override
    public boolean onBack() {
        if(null != musicControlView){
         return    musicControlView.onBack();
        }

        return false;
    }

    @Override
    public void onKeyDown() {
        if(null != musicControlView){
            musicControlView.onKeyDown();
        }
    }

    private void setCache(String url, boolean isCache){
        if(isCache){
            HttpProxyCacheServer proxy = BaseApplation.getProxy(getContext());
            url = proxy.getProxyUrl(url);
        }
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setUrlAndPlay(String url) {
        this.url = url;
        if(isCreated){
            initMediaPlayer(url);
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
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
        if (false) {
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 1);
        }

        if(CheckUtil.isEmpty(url)){

            return mediaPlayer;
        }

        setCache(url,true);

        mediaPlayer.setDisplay(surfaceView.getHolder());
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
