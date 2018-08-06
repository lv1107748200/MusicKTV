package com.hr.musicktv.widget.music;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.media.audiofx.Equalizer;
import android.net.Uri;
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
import com.hr.musicktv.net.entry.response.MKSearch;
import com.hr.musicktv.utils.CheckUtil;
import com.hr.musicktv.utils.NLog;
import com.hr.musicktv.utils.NToast;
import com.hr.musicktv.widget.seek.CustomSeekbar;

import java.io.IOException;


/*
 * lv   2018/7/24
 */
public class MusicPlayerLayout extends BaseLayout
        implements SurfaceHolder.Callback2
    ,MediaPlayer.OnBufferingUpdateListener
    ,MediaPlayer.OnCompletionListener
    ,MediaPlayer.OnErrorListener
    ,MediaPlayer.OnInfoListener
    ,MediaPlayer.OnPreparedListener
    ,MediaPlayer.OnSeekCompleteListener
    ,MediaPlayer.OnTimedTextListener
    ,MediaPlayer.OnVideoSizeChangedListener
    ,MethodWhat,MethodView
{
    private volatile String url = null;
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private MusicControlView musicControlView;
    private WheatManger wheatManger;
    private BroadcastReceiver broadcastReceiver;

    private volatile boolean isCreated;

    private Context context;
    private MethodMain methodMain;

    private Equalizer mEqualizer;//均衡器


    public void setMethodMain(MethodMain methodMain) {
        this.methodMain = methodMain;
    }

    public MusicPlayerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        wheatManger = new WheatManger();
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
    public void onBufferingUpdate(MediaPlayer iMediaPlayer, int i) {

    }

    @Override
    public void onCompletion(MediaPlayer iMediaPlayer) {
        if(null != musicControlView){
            musicControlView.onCompletion();
        }
    }

    @Override
    public boolean onError(MediaPlayer iMediaPlayer, int i, int i1) {
        if(null != musicControlView){
            musicControlView.onError();
        }
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer iMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer iMediaPlayer) {

        if(mediaPlayer != null){
            mediaPlayer.start();
        }

        if(null != musicControlView){
            musicControlView.onPrepared();
        }

        if(null != wheatManger){
          //  wheatManger.start();
        }

        if(null != mediaPlayer){
            mediaPlayer.setVolume(0.8f, 0.8f);
        }

    }

    @Override
    public void onSeekComplete(MediaPlayer iMediaPlayer) {
        if(null != musicControlView){
            musicControlView.onSeekComplete();
        }
    }

    @Override
    public void onTimedText(MediaPlayer mp, TimedText text) {

    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

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
          //  if(mediaPlayer.isPlayable()){
                start();
         //   }
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
        pause();
    }
    @Override
    public void rebroadcast() {//重新播放
        if(null != musicControlView){
            musicControlView.rebroadcast();
        }
        pause();
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

        if(null != wheatManger){
            wheatManger.release();
        }

       context.unregisterReceiver(broadcastReceiver);
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
            if(canableChangeTrack()){
                try{
                    mediaPlayer.selectTrack(MediaPlayer.TrackInfo.MEDIA_TRACK_TYPE_VIDEO);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }else {
                NToast.shortToastBaseApp("此资源不支持音轨切换");
            }
        }
    }

    @Override
    public void accompany() {//半场
        if(null != mediaPlayer){
            if(canableChangeTrack()){
                try{
                    mediaPlayer.selectTrack(MediaPlayer.TrackInfo.MEDIA_TRACK_TYPE_AUDIO);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                NToast.shortToastBaseApp("此资源不支持音轨切换");
            }
        }
    }

    @Override
    public void tone(int is) {//升降调
        setmEqualizer(is);
    }
    @Override
    public long getCurrentPosition() {
        if(null != mediaPlayer ){
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
    public void select(MKSearch mkSearch) {
        if(null != methodMain){
            methodMain.change(mkSearch);
        }
    }

    @Override
    public MKSearch getMk() {
        if(null != methodMain){
          return   methodMain.getMK();
        }
        return null;
    }

    @Override
    public void onCenter() {

        if(null != musicControlView){
            musicControlView.onCenter();
        }

        if(null != mediaPlayer){
            if(mediaPlayer.isPlaying()){
               // pause();
            }else {
               // if(mediaPlayer.isPlayable()){
                    start();
               // }
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
    private void setCache(String url, boolean isCache){

        if(isCache){
            HttpProxyCacheServer proxy = BaseApplation.getProxy(getContext());
            url = proxy.getProxyUrl(url);
        }

     //   String path = "android.resource://" + getContext().getPackageName() + "/" + R.raw.ben_pao;
//        mediaPlayer.setDataSource(RawDataSourceProvider.create(getContext(), Uri.parse(path)));

        try {

            AssetFileDescriptor afd = context.getResources().openRawResourceFd(R.raw.ben_pao);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());

           //mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public MediaPlayer initMediaPlayer(String url){


        if(CheckUtil.isEmpty(url)){
            return mediaPlayer;
        }
        //MediaPlayer.create只要成功返回了播放器就不需要再去 prepare  否则报错 (源码中已经 执行了 prepare)
        if(null == mediaPlayer){

           // mediaPlayer =  MediaPlayer.create(getContext(), Uri.parse(url));

            mediaPlayer = new MediaPlayer();


            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setScreenOnWhilePlaying(true);

            mediaPlayer.setDisplay(surfaceView.getHolder());
            setCache(url,true);

            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnSeekCompleteListener(this);
            mediaPlayer.setOnVideoSizeChangedListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnInfoListener(this);
            mediaPlayer.setOnTimedTextListener(this);
            mediaPlayer.setVolume(1.0f, 1.0f);
            mediaPlayer.setAuxEffectSendLevel(1.0f);

            if(null == mEqualizer){
                mEqualizer = new Equalizer(0, mediaPlayer.getAudioSessionId());
                mEqualizer.setEnabled(true);
            }

            mediaPlayer.prepareAsync();

            //start();

        }else {
            mediaPlayer.reset();

            setCache(url,true);

            mediaPlayer.prepareAsync();
        }
        //循环播放
        // mediaPlayer.setLooping(true);
        //屏幕常亮

        return  mediaPlayer;
    }


    private boolean  canableChangeTrack(){

        if(null != mediaPlayer){
            MediaPlayer.TrackInfo[] trackInfos =   mediaPlayer.getTrackInfo();
            if(!CheckUtil.isEmpty(trackInfos) && url.indexOf("mkv")!=-1){
                return true;
            }
        }
        MediaPlayer.TrackInfo[] trackInfos =   mediaPlayer.getTrackInfo();


        NLog.e(NLog.PLAYER,"--->size = " + trackInfos.length);

        for (int i = 0, j = trackInfos.length; i<j; i++){
            NLog.e(NLog.PLAYER,"--->trackInfos = " +  trackInfos[i].toString());
        }
        return true;
    }


    private void setmEqualizer(int is){
        if(null == mEqualizer)
            return;

        final short lowerEqualizerBandLevel = mEqualizer.getBandLevelRange()[0];
        final short upperEqualizerBandLevel = mEqualizer.getBandLevelRange()[1];

        // 获取均衡控制器支持的所有频率
        short brands = mEqualizer.getNumberOfBands();

        NLog.e(NLog.PLAYER," 均衡器  --->size = " + brands);
        NLog.e(NLog.PLAYER," 均衡器  --->lowerEqualizerBandLevel = " + lowerEqualizerBandLevel);
        NLog.e(NLog.PLAYER," 均衡器  --->upperEqualizerBandLevel = " + upperEqualizerBandLevel);

        short  main = (short) (upperEqualizerBandLevel - lowerEqualizerBandLevel);
        short  what = 1500;
        if(null != musicControlView){

            CustomSeekbar customSeekbar =  musicControlView.getEqSeeBar();

               what = (short) (main/ customSeekbar.getJianJv());

            if(is == 0){//升
                customSeekbar.setProgress(customSeekbar.getCur_sections() + 1);

            }else if(is == 1){//降
                customSeekbar.setProgress(customSeekbar.getCur_sections() - 1);
            }

            what = (short) (what * customSeekbar.getCur_sections());
        }

        if(what < 0 ){
            what = 1500;
        }

        for(short i=0; i<brands; i++){
            short zu = (short) (what+lowerEqualizerBandLevel);
            NLog.e(NLog.PLAYER," 均衡器  --->zu = " + zu);
            mEqualizer.setBandLevel(i, zu);
            NLog.e(NLog.PLAYER," 均衡器  --->BandLevel = " + mEqualizer.getBandLevel(i));
        }
    }

    public void setBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action){
                    //插入和拔出耳机会触发此广播
                    case Intent.ACTION_HEADSET_PLUG:
                        int state = intent.getIntExtra("state", 0);
                        if (state == 1){
                            NToast.shortToastBaseApp("插入耳机");

                            if(null != wheatManger){
                                wheatManger.changeToSpeaker();
                            }

                        } else if (state == 0){
                            NToast.shortToastBaseApp("耳机未插");
                        }
                        break;
                }
            }
        };
        IntentFilter intentFilter =   new IntentFilter();
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);

      context.registerReceiver(broadcastReceiver,intentFilter);
    }
}
