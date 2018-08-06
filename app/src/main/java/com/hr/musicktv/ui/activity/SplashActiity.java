package com.hr.musicktv.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.hr.musicktv.R;
import com.hr.musicktv.base.BaseActivity;
import com.hr.musicktv.base.PermissionCallback;
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
import com.hr.musicktv.widget.loading.LVCircularJump;
import com.hr.musicktv.widget.single.UserInfoManger;

import butterknife.BindView;


/**
 * Created by 吕 on 2018/3/13.
 */

public class SplashActiity extends BaseActivity implements LoadingLayout.LoadingCallBack {


    @BindView(R.id.load_relayout)
    LoadingLayout load_relayout;


    static final int freq = 44100;
    static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    int recBuffSize, playBufSize;
    AudioRecord audioRecord;
    AudioTrack audioTrack;

    volatile boolean isRecording = false;

    private AudioManager audioManager;

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void init() {
        super.init();

        //load_relayout.setLoadingCallBack(this);
        //userAutoLogin();

//        Intent intent = new Intent();
//        intent.setClass(SplashActiity.this,MainActivity.class);
//        startActivity(intent);
//        finish();
       // audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        if (checkPermission(Manifest.permission.RECORD_AUDIO)) {
           // Toast.makeText(SplashActiity.this,"有录音权限",Toast.LENGTH_SHORT).show();
        }else {
            //Toast.makeText(SplashActiity.this,"木有录音权限",Toast.LENGTH_SHORT).show();

            requestPermission(Manifest.permission.RECORD_AUDIO, new PermissionCallback() {
                @Override
                public void requestP(boolean own) {

                }
            });
        }
        Intent intent = new Intent();
        intent.setClass(SplashActiity.this,MainActivity.class);
        startActivity(intent);
        finish();

     //   changeToSpeaker();
      //  record();

    }

    private void userAutoLogin(){

    }

    private void validate(){

    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_kaishi:
                NLog.e(NLog.PLAYER,"开始 录音 --->");
                isRecording = true;
                new RecordPlayThread().start();// 开线程边录边放
                break;
            case R.id.btn_stop:
                isRecording = false;
                break;
        }
    }

    //录音
    private void record(){

        recBuffSize = AudioRecord.getMinBufferSize(freq, channelConfiguration, audioEncoding);
        playBufSize = AudioTrack.getMinBufferSize(freq, channelConfiguration, audioEncoding);

        audioRecord = new AudioRecord(MediaRecorder.AudioSource.VOICE_COMMUNICATION, freq, channelConfiguration, audioEncoding, recBuffSize);

        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, freq, channelConfiguration, audioEncoding, playBufSize, AudioTrack.MODE_STREAM);


        audioTrack.setStereoVolume(1.0f, 1.0f);
    }

    public void changeToSpeaker(){
        audioManager.setMode(AudioManager.MODE_NORMAL);
        audioManager.setSpeakerphoneOn(true);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        isRecording = false;
//        audioRecord.release();
//        audioTrack.release();
//        android.os.Process.killProcess(android.os.Process.myPid());
    }


    class RecordPlayThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {


                byte[] buffer = new byte[recBuffSize];
                try{
                    audioRecord.startRecording(); // 开始录音
                }catch (IllegalStateException e){
                    e.printStackTrace();
                }

                audioTrack.play(); // 开始放音

                NLog.e(NLog.PLAYER,"录音isRecording --->" + isRecording);

                while (isRecording) {
                    // 从MIC保存数据到buffer

                    int bufferReadResult = audioRecord.read(buffer, 0, recBuffSize);
                    byte[] tmpBuffer = new byte[bufferReadResult];
                    System.arraycopy(buffer, 0, tmpBuffer, 0, bufferReadResult);

                    NLog.e(NLog.PLAYER,"录音 while--->" + "  bufferReadResult  =  " + bufferReadResult + " tmpBuffer = " + tmpBuffer.length);
//                    // 写入数据到AudioTrack即可播放
//
                    audioTrack.write(tmpBuffer, 0, tmpBuffer.length);

                }
                audioRecord.stop();
                audioTrack.stop();

            } catch (Throwable t) {

            }
        }
    }


}
