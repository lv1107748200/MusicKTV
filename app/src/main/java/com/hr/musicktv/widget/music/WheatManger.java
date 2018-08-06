package com.hr.musicktv.widget.music;


import android.content.Context;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Build;

import com.hr.musicktv.base.BaseApplation;

import com.hr.musicktv.utils.NLog;
import com.hr.musicktv.utils.NToast;

/*
 * lv   2018/8/2
 */
public class WheatManger {

    static final int freq = 44100;
    static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    int recBuffSize, playBufSize;
    AudioRecord audioRecord;
    AudioTrack audioTrack;

    volatile boolean isRecording = false;

    private AudioManager audioManager;


    public WheatManger() {
        audioManager = (AudioManager) BaseApplation.getBaseApp().getSystemService(Context.AUDIO_SERVICE);
       record();
    }

    //录音
    private void record(){

        recBuffSize = AudioRecord.getMinBufferSize(freq, channelConfiguration, audioEncoding);
        playBufSize = AudioTrack.getMinBufferSize(freq, channelConfiguration, audioEncoding);

        audioRecord = new AudioRecord(MediaRecorder.AudioSource.VOICE_COMMUNICATION, freq, channelConfiguration, audioEncoding, recBuffSize);

        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, freq, channelConfiguration, audioEncoding, playBufSize, AudioTrack.MODE_STREAM);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audioTrack.setVolume(1.0f);
        }else {
            audioTrack.setStereoVolume(1.0f, 1.0f);
        }
    }

    public void changeToSpeaker(){
        if(audioManager != null){
            audioManager.setMode(AudioManager.MODE_NORMAL);
            audioManager.setSpeakerphoneOn(true);
        }
    }

    public void start(){
        if(isMicrophoneGood(BaseApplation.getBaseApp())){
            NLog.e(NLog.PLAYER,"开始 录音 --->");
            isRecording = true;
            new RecordPlayThread().start();// 开线程边录边放
        }else {
            NToast.shortToastBaseApp("五麦克风");
        }

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

                  //  NLog.e(NLog.PLAYER,"录音 while--->" + "  bufferReadResult  =  " + bufferReadResult + " tmpBuffer = " + tmpBuffer.length);
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

    /** 检测麦克风 */
    public static boolean isMicrophoneGood(Context paramContext)
    {
        new MediaRecorder();
        return paramContext.getPackageManager().hasSystemFeature("android.hardware.microphone");
    }

    public void release(){
        isRecording = false;
        if(null != audioRecord)
        audioRecord.release();
        if(null != audioTrack)
        audioTrack.release();
    }
}
