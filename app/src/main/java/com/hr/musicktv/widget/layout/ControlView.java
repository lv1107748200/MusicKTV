package com.hr.musicktv.widget.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hr.musicktv.R;
import com.hr.musicktv.utils.Formatter;
import com.hr.musicktv.widget.seek.TvSeekBarView;
import com.hr.musicktv.widget.single.IjkPlayerMger;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 吕 on 2018/5/16.
 */

public class ControlView extends FrameLayout
implements LoadingLayout.LoadingCallBack
      {

    public final static int PLAY_COM = 0;//播放完成
    public final static int PLAY_ERROR = 1;//播放出错
    public final  static int PLAY_PREPARED = 2;//开始播放
    public  final static int PLAY_REPLAY = 3;//重新播放
    public final  static int PLAY_SWTICH = 4;//换集
    public final static int PLAY_SHOW_LIST = 5;
    public final static int PLAY_SHOW_MENU = 6;
    public final static int PLAY_SEEKCOMPLETE = 7;//快进结束

    private RelativeLayout control_main_layout;
    private LoadingLayout load_relayout;
    private TextView current,total,tvFenJi,tvFunctionMenu,title_video;

    private ImageButton btnKaiBo;
    private TvSeekBarView seekBar;

    protected Timer seepOrBackTimer;//快进快退控制器

    private boolean inSeek = false;//进度条快进中
    private boolean isCom = false;//播放完成
    private boolean isPrepare = false;//准备完成

    private boolean isK = false;//是快进
    private boolean isT = false;//快退
    private boolean isUp = false;//是否刷新
    private long savePress;//调节的时间
    private long duration;//视频总时间

    private Disposable mDisposable;//脉搏
    private Disposable upDisposable;//刷新时间和进度条
    private ConViewCallBack conViewCallBack;
    private int jiShu = 1;

    public ControlView(@NonNull Context context) {
        this(context,null);
    }

    public ControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

  public void setConViewCallBack(ConViewCallBack conViewCallBack) {
      this.conViewCallBack = conViewCallBack;
  }

   public void initView(View view){

        addView(view,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        control_main_layout = view.findViewById(R.id.control_main_layout);
        load_relayout = view.findViewById(R.id.load_relayout);
        current = findViewById(R.id.current);
        title_video = findViewById(R.id.title_video);
        total = findViewById(R.id.total);
        tvFenJi = findViewById(R.id.tv_fenji);
        tvFunctionMenu = findViewById(R.id.tv_function_menu);
        seekBar = findViewById(R.id.progress);
        btnKaiBo = findViewById(R.id.btnKaiBo);

        load_relayout.setLoadingCallBack(this);

        setLoadingLayout(3,LoadingLayout.ONE,null);//初始化

       btnKaiBo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playOrstopOrRe();
            }
        });

    }
    public void setTitle_video(String title) {
        this.title_video.setText(title);
    }


    @Override
    public void btnCallBack() {
        //出错了
        if(null != conViewCallBack){
            conViewCallBack.rePlay();
        }
    }

    public void set(int play){


        switch (play){
            case PLAY_COM:
                isCom = true;//播放完成
                dispose();
                setControlMainLayout(View.VISIBLE);//播放完成时显示
                btnKaiBo.setSelected(false);
                break;
            case PLAY_ERROR:
                setLoadingLayout(1,LoadingLayout.TWO,null);//出错显示按钮
                disableUp();
                break;
            case PLAY_PREPARED:

                isUp = true;
                isPrepare = true;
                btnKaiBo.setSelected(true);
                setLoadingLayout(0,LoadingLayout.ONE,null);//准备完成
               // doSomething();//延缓关闭菜单栏
                setUpDisposable();

                long duration =  IjkPlayerMger.getInstance().getMediaPlayer().getDuration();
                total.setText(Formatter.formatTime((int) duration));
                seekBar.setmMax((int)duration);

                break;
            case PLAY_REPLAY:

                reSet();
                setLoadingLayout(1,LoadingLayout.ONE,null);//出错重新加载

                break;
            case PLAY_SWTICH:

                reSet();
                setLoadingLayout(2,LoadingLayout.ONE,null);//换集重新加载
                break;
            case PLAY_SHOW_LIST:
               setControlMainLayout(GONE);
                break;
            case PLAY_SHOW_MENU:
                setControlMainLayout(GONE);
                break;
            case PLAY_SEEKCOMPLETE:
                inSeek = false;
                break;
        }
    }

    public void reSet(){
        inSeek = false;
        isCom = false;
        isPrepare = false;
        isK = false;
        isT = false;
        isUp = true;

        savePress = 0;
        dispose();
        disableUp();
    }

    public void setControlMainLayout(int show) {

        String s = "设置控制页面--->" + show + "     isCom:" + isCom
                +"     isPrepare:"+isPrepare
                +"     ik:"+isK+"     it:"+isT;

        if(show == GONE){

            isUp = false;

        }else if(show == VISIBLE){

            isUp = true;

            getDataEveryTime();//再次显示时 刷新一次数据

            if(null != IjkPlayerMger.getInstance().getMediaPlayer()){
                if(!isCom){
                    if(IjkPlayerMger.getInstance().getMediaPlayer().isPlaying()){
                        isUp = true;
                    }else {
                        isUp = false;
                    }
                }else {
                    isUp = false;
                }

            }
        }

        s = s + "    isUp : " + isUp;

        control_main_layout.setVisibility(show);

    }

    /**
     * 设置加载页面
     */
    public void setLoadingLayout(int type,int cas, LoadingLayout.ShowMain showMain) {

        if(control_main_layout.getVisibility() == VISIBLE){
            setControlMainLayout(GONE);
        }

        switch (type){
            case 0://初次加载
                load_relayout.setLoad_layout(R.drawable.hehe);//背景设置
                load_relayout.setVisibility(GONE);
                break;
            case 1://重新加载
                load_relayout.setLoad_layout(R.drawable.hehe);//背景设置
                load_relayout.setVisibility(VISIBLE);
                load_relayout.setLoadingLayout(cas,showMain);
                isPrepare = false;
                break;
            case 2://换集
                load_relayout.setLoad_layout(android.R.color.transparent);//背景设置
                load_relayout.setVisibility(VISIBLE);
                load_relayout.setLoadingLayout(cas,showMain);
                isPrepare = false;
                break;
            case 3:
                load_relayout.setLoad_layout(R.drawable.hehe);//背景设置
                load_relayout.setVisibility(VISIBLE);
                load_relayout.setLoadingLayout(cas,showMain);
                break;
        }

    }
    public void getDataEveryTime(){

        if(!isUp){
            return;
        }
        if (IjkPlayerMger.getInstance().getMediaPlayer() != null ) {

            long curPosition =  IjkPlayerMger.getInstance().getMediaPlayer().getCurrentPosition();

            //  Logger.d("--->"+"curPosition = " + curPosition + " , duration = " + duration + " ， inSeek = " + inSeek);
            current.setText(Formatter.formatTime((int) curPosition));

            if (!inSeek) {
                seekBar.setProgress((int)curPosition,false);
            }
        }
    }


    //事件处理
    public void  playOrstopOrRe(){
      //  Logger.d("--->"+"isPrepare = " + isPrepare + " , isCom = " + isCom + " ， isUp = " + isUp);
        if(!isPrepare){//是否准备完成
            return;
        }

        if (IjkPlayerMger.getInstance().getMediaPlayer() != null) {
            if(!isCom){
                if(IjkPlayerMger.getInstance().getMediaPlayer().isPlaying()){
                    dispose();
                    IjkPlayerMger.getInstance().getMediaPlayer().pause();
                    btnKaiBo.setSelected(false);
                    setControlMainLayout(View.VISIBLE);//暂停时
                }else {
                    isUp = true;
                    doSomething();//延缓关闭菜单栏
                    IjkPlayerMger.getInstance().getMediaPlayer().start();
                    btnKaiBo.setSelected(true);
                    setUpDisposable();//开始播放
                }
            }else {
                if(null != conViewCallBack){
                    conViewCallBack.rePlay();
                }

            }
        }

    }
    /**
     * 快进
     */
    public void seepKey(boolean is,int num) {
        if(isCom){ //已经播放完成不能快进
            //  NToast.shortToastBaseApp("已播放完成");
            return;
        }

        if(isK){//已经触发了快进
            return;
        }

        isT = false;
        if(null != IjkPlayerMger.getInstance().getMediaPlayer()){
            if(isPrepare){
                isK = true;
                inSeek = true;
                dispose();
                setControlMainLayout(VISIBLE);
//                seekBar.getBuilder()
//                        .setIndicatorStay(true)
//                        .setIndicatorColor(Color.parseColor("#77CEEE"))
//                        .setIndicatorCustomTopContentLayout(R.layout.custom_top_content_view_rect_without_progress)
//                        .apply();

                savePress = IjkPlayerMger.getInstance().getMediaPlayer().getCurrentPosition();
                duration = IjkPlayerMger.getInstance().getMediaPlayer().getDuration();

            //    Logger.d("savePress=  "+savePress + "    duration=   "+duration);

                if(is){
                    //seepOrkackTask();
                    setKuaiJin(false,num);
                }else {
                    setKuaiJin(true,num);//短按快进
                }

            }
        }

    }
    /**
     * 快退
     */
    public void backKey(boolean is,int num) {
        if(isCom){//恢复为未播放完成
            //    NToast.shortToastBaseApp("已播放完成");
            return;
        }

        if(isT){//已经触发了快退
            return;
        }

        isK = false;
        if(null != IjkPlayerMger.getInstance().getMediaPlayer()){
            if(isPrepare){
                isT = true;
                inSeek = true;
                dispose();
                setControlMainLayout(VISIBLE);
                savePress = IjkPlayerMger.getInstance().getMediaPlayer().getCurrentPosition();
                duration = IjkPlayerMger.getInstance().getMediaPlayer().getDuration();
                if(is){
                    setKuaiJin(false,num);
                }else {
                    setKuaiJin(true,num);//短按快退
                }

            }
        }
    }
          private void setKuaiJin(boolean isCom,int num){
              if(isCom){
                  jiShu = 3000;
              }else {
                  jiShu = 1000 * num ;
              }

              if(null != IjkPlayerMger.getInstance().getMediaPlayer()){

                  if(isK){
                      savePress = (savePress + jiShu * 2 );
                      if(savePress > duration){
                          savePress = duration;
                          //stopSeep();
                      }
                  } else  if (isT){
                      savePress = (savePress - jiShu * 2);
                      if(savePress < 0){
                          savePress = 0;
                          //  stopSeep();
                      }
                  }
               //   Logger.d("savePress=  "+savePress);

                  seekBar.setProgress((int)savePress);
                  if(false){
                      if(null != IjkPlayerMger.getInstance().getMediaPlayer()){
                       //   Logger.d("seekTo 触发--->");
                          IjkPlayerMger.getInstance().getMediaPlayer().seekTo(savePress);
                      }
                  }
              }
          }


    /**
     * 停止快进或快推
     */
    public void stopSeepOrBackTask(boolean is) {

        seekBar.setShow(false);

//        if(null != seepOrBackTimer){
//            seepOrBackTimer.cancel();
//            seepOrBackTimer = null;
//        }
        isK = false;
        isT = false;

//        seekBar.getBuilder()
//                .setIndicatorStay(false)
//                .setIndicatorCustomTopContentLayout(R.layout.custom_top_content_view_rect_without_progress)
//                .apply();

        if(null != IjkPlayerMger.getInstance().getMediaPlayer()){
           // if(is)
                IjkPlayerMger.getInstance().getMediaPlayer().seekTo(savePress);
        }

        if(IjkPlayerMger.getInstance().getMediaPlayer().isPlaying()){
            doSomething();
        }

    }



    private void  setUpDisposable(){

        if(null != upDisposable){
            if(!upDisposable.isDisposed()){
                disableUp();
            }
        }

        upDisposable =  Flowable.interval(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {

                        getDataEveryTime(); //轮询刷新

                    }
                });

    }

    private void disableUp(){
        if(null != upDisposable){
            upDisposable.dispose();
            upDisposable = null;
        }
    }


    public void doSomething() {
        if(null != mDisposable){
            if(!mDisposable.isDisposed()){
                dispose();
            }
        }
        mDisposable = Flowable.interval(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                        setControlMainLayout(View.GONE);
                        dispose();
                    }
                });

    }

    private void dispose(){
        if (mDisposable != null){
            mDisposable.dispose();
            mDisposable = null;
        }
    }






    //池底销毁
    public void destroy() {
       // Logger.d("controlView  onDestroy--->");

        dispose();
        disableUp();//退出时销毁
    }

    public interface ConViewCallBack{
        void rePlay();
    }
    /**
       * 快进快退任务
       */
      public void seepOrkackTask() {

          if(null == seepOrBackTimer){
              seepOrBackTimer = new Timer();

          }
          seepOrBackTimer.schedule(new UpdataKeySeekBarTimerTask(),0,60);
      }



          public void stopSeep(){
              if(null != seepOrBackTimer){
                  seepOrBackTimer.cancel();
                  seepOrBackTimer = null;
              }
          }
          private class UpdataKeySeekBarTimerTask extends TimerTask {
              @Override
              public void run() {
                  post(new Runnable() {
                      @Override
                      public void run() {

                        //  setKuaiJin(false);

                      }
                  });
              }
          }
}
