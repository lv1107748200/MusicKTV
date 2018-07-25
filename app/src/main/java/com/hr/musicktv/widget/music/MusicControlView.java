package com.hr.musicktv.widget.music;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hr.musicktv.R;
import com.hr.musicktv.utils.Formatter;
import com.hr.musicktv.widget.layout.LoadingLayout;
import com.hr.musicktv.widget.seek.TvSeekBarView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*
 * lv   2018/7/24
 */
public class MusicControlView extends BaseLayout implements MethodLayout , LoadingLayout.LoadingCallBack{
    private View  mainView;
    private MethodView methodView;

    private Disposable upDisposable;//刷新时间和进度条
    private Disposable mDisposable;//脉搏


    @BindView(R.id.title_video)
    TextView titleVideo;
    @BindView(R.id.current)
    TextView current;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.btn_pause)
    Button btnPause;//暂停 开始
    @BindView(R.id.progress)
    TvSeekBarView progress;
    @BindView(R.id.load_relayout)
    LoadingLayout load_relayout;

    @OnClick({R.id.btn_pause,R.id.btn_rebroadcast,R.id.btn_next_song,R.id.btn_original_singer
    ,R.id.btn_accompany,R.id.btn_up_tune,R.id.btn_down_tune,R.id.btn_song_list})
    public void OnClick(View view){
        switch(view.getId()){
            case R.id.btn_pause:
                if(null != methodView){
                    if(methodView.getIsPlayer()){
                        methodView.pause();
                    }else {
                        methodView.vStart();
                    }
                }
                break;
            case R.id.btn_rebroadcast:
                if(null != methodView){
                    methodView.rebroadcast();
                }
                break;
            case R.id.btn_next_song:
                if(null != methodView){
                    methodView.change();
                }
                break;
            case R.id.btn_original_singer:
                if(null != methodView){
                    methodView.originalSinger();
                }
                break;
            case R.id.btn_accompany:
                if(null != methodView){
                    methodView.accompany();
                }
                break;
            case R.id.btn_up_tune:
                if(null != methodView){
                    methodView.tone(0);
                }
                break;
            case R.id.btn_down_tune:
                if(null != methodView){
                    methodView.tone(1);
                }
                break;
            case R.id.btn_song_list:
                if(null != methodView){
                    methodView.songList();
                }
                mainView.setVisibility(GONE);
                break;
        }
    }

    public void setMethodView(MethodView methodView) {
        this.methodView = methodView;
    }

    public MusicControlView(@NonNull Context context) {
        super(context);
    }

    @Override
    public void init(Context context) {
        mainView = View.inflate(context, R.layout.music_control_view,null);

        addView(mainView,getParams());

        ButterKnife.bind(this,mainView);

    }

    @Override
    public void start() {
        if(mainView.getVisibility() == VISIBLE){
            setUpDisposable();
            doSomething();
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void onPause() {
        dispose();
        disableUp();
        if(mainView.getVisibility() == GONE)
        mainView.setVisibility(VISIBLE);
    }

    @Override
    public void change() {

    }

    @Override
    public void release() {
        disableUp();
        dispose();
    }

    @Override
    public void speed() {

    }

    @Override
    public void fastReverse() {

    }

    @Override
    public void rebroadcast() {

        setLoadingLayout(2,LoadingLayout.ONE,null);//换集重新加载

    }

    @Override
    public void onPrepared() {
        setUpDisposable();

        setLoadingLayout(0,0,null);

        if(mainView.getVisibility() == VISIBLE){
            mainView.setVisibility(GONE);
        }
    }

    @Override
    public void onSeekComplete() {

    }

    @Override
    public void onCompletion() {

    }

    @Override
    public void onError() {
        setLoadingLayout(1,LoadingLayout.TWO,null);//出错显示按钮
    }

    @Override
    public void onCenter() {

    }

    @Override
    public void onMenu() {
        mainView.setVisibility(VISIBLE);
        setUpDisposable();
    }

    @Override
    public void onBack() {

    }

    @Override
    public void onKeyDown() {
        doSomething();
    }

    @Override
    public void btnCallBack() {
        if(null != methodView){
            methodView.rebroadcast();
        }
    }

    /**
     * 设置加载页面
     */
    public void setLoadingLayout(int type,int cas, LoadingLayout.ShowMain showMain) {
        if(mainView.getVisibility() == GONE)
            mainView.setVisibility(VISIBLE);
        switch (type){
            case 0://初次加载
                load_relayout.setLoad_layout(android.R.color.transparent);//背景设置
                load_relayout.setVisibility(GONE);
                break;
            case 1://重新加载
                load_relayout.setLoad_layout(android.R.color.transparent);//背景设置
                load_relayout.setVisibility(VISIBLE);
                load_relayout.setLoadingLayout(cas,showMain);
                break;
            case 2://换集
                load_relayout.setLoad_layout(android.R.color.transparent);//背景设置
                load_relayout.setVisibility(VISIBLE);
                load_relayout.setLoadingLayout(cas,showMain);
                break;
            case 3:
                load_relayout.setLoad_layout(android.R.color.transparent);//背景设置
                load_relayout.setVisibility(VISIBLE);
                load_relayout.setLoadingLayout(cas,showMain);
                break;
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

                        if(null != methodView){
                            total.setText(Formatter.formatTime((int) methodView.getTotalPosition()));

                            progress.setmMax((int)methodView.getTotalPosition());

                            current.setText(Formatter.formatTime((int) methodView.getCurrentPosition()));

                            progress.setProgress((int)methodView.getCurrentPosition(),false);
                        }
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
                        if(mainView.getVisibility() == VISIBLE)
                        mainView.setVisibility(GONE);
                        dispose();
                        disableUp();
                    }
                });

    }
    private void dispose(){
        if (mDisposable != null){
            mDisposable.dispose();
            mDisposable = null;
        }
    }
}
