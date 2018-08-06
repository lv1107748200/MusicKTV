package com.hr.musicktv.widget.music;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.musicktv.R;
import com.hr.musicktv.db.DBResultCallback;
import com.hr.musicktv.db.TabsData;
import com.hr.musicktv.net.entry.response.MKSearch;
import com.hr.musicktv.net.entry.response.MKSearch_Table;
import com.hr.musicktv.ui.adapter.FunctionMenuAdapter;
import com.hr.musicktv.utils.CheckUtil;
import com.hr.musicktv.utils.DisplayUtils;
import com.hr.musicktv.utils.FocusUtil;
import com.hr.musicktv.utils.Formatter;
import com.hr.musicktv.utils.NLog;
import com.hr.musicktv.widget.layout.LoadingLayout;
import com.hr.musicktv.widget.seek.CustomSeekbar;
import com.hr.musicktv.widget.seek.TvSeekBarView;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


import static com.hr.musicktv.common.ImmobilizationData.SelSong;
import static com.hr.musicktv.common.ImmobilizationData.TAB;
import static com.hr.musicktv.common.ImmobilizationData.UseSong;

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
    @BindView(R.id.control_main_layout)
    RelativeLayout controlMainLayout;
    @BindView(R.id.list_menu_one)
    TvRecyclerView listMenuOne;

    @BindView(R.id.EqSeeBar)
    CustomSeekbar EqSeeBar;

    private FunctionMenuAdapter adapterLift;


    private View view;

    private List<MKSearch> songResult; //歌单

    @OnClick({R.id.btn_pause,R.id.btn_rebroadcast,R.id.btn_next_song,R.id.btn_original_singer
    ,R.id.btn_accompany,R.id.btn_up_tune,R.id.btn_down_tune,R.id.btn_song_list})
    public void OnClick(View view){

        this.view = view;

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
                    onKeyDown();
                }
                break;
            case R.id.btn_accompany:
                if(null != methodView){
                    methodView.accompany();
                    onKeyDown();
                }
                break;
            case R.id.btn_up_tune:
                if(null != methodView){

                    onKeyDown();
                    if(EqSeeBar.getVisibility() == GONE){
                        EqSeeBar.setVisibility(VISIBLE);
                    }else {
                        methodView.tone(0);
                    }

                }
                break;
            case R.id.btn_down_tune:
                if(null != methodView){

                    onKeyDown();
                    if(EqSeeBar.getVisibility() == GONE){
                        EqSeeBar.setVisibility(VISIBLE);
                    }else {
                        methodView.tone(1);
                    }

                }
                break;
            case R.id.btn_song_list:
                if(null != methodView){
                    methodView.songList();
                }
                controlMainLayout.setVisibility(GONE);
                break;
        }
    }

    public void setMethodView(MethodView methodView) {
        this.methodView = methodView;
    }

    public MusicControlView(@NonNull Context context) {
        super(context);
    }

    public MusicControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        mainView = View.inflate(context, R.layout.music_control_view,null);

        addView(mainView,getParams());

        ButterKnife.bind(this,mainView);

        load_relayout.setLoadingCallBack(this);

        EqSeeBar.setProgress(5);

    }

    @Override
    public void start() {
        btnPause.setText("暂停");

        if(controlMainLayout.getVisibility() == VISIBLE){
            setUpDisposable();
            doSomething();
        }
        setLoadingLayout(0,0,null);
    }

    @Override
    public void stop() {

    }

    @Override
    public void onPause() {
        btnPause.setText("播放");

        dispose();
        disableUp();
        if(controlMainLayout.getVisibility() == GONE){
            controlMainLayout.setVisibility(VISIBLE);
            FocusUtil.setFocus(view);
        }

    }

    @Override
    public void change() {
        setLoadingLayout(2,LoadingLayout.ONE,null);//换集重新加载
        //默认顺序播放

        huoqv(1);//换集
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

        if(controlMainLayout.getVisibility() == VISIBLE){
            controlMainLayout.setVisibility(GONE);
        }

        if(EqSeeBar.getVisibility() == VISIBLE){
            EqSeeBar.setVisibility(GONE);
        }

    }

    @Override
    public void onSeekComplete() {

    }

    @Override
    public void onCompletion() {
        setLoadingLayout(3,LoadingLayout.TWO,new LoadingLayout.ShowMain(){
            @Override
            public String getText() {
                return "播放完成";
            }
        });//出错显示按钮
    }

    @Override
    public void onError() {
        setLoadingLayout(1,LoadingLayout.TWO,null);//出错显示按钮
    }

    @Override
    public void onCenter() {
        controlMainLayout.setVisibility(VISIBLE);
        FocusUtil.setFocus(view);

        setUpDisposable();
    }

    @Override
    public void onMenu() {
        controlMainLayout.setVisibility(VISIBLE);
        FocusUtil.setFocus(view);

        setUpDisposable();
    }

    @Override
    public boolean onBack() {
        if(listMenuOne.getVisibility() == VISIBLE){
            listMenuOne.setVisibility(GONE);
            return true;
        }
        if(EqSeeBar.getVisibility() == VISIBLE){
            EqSeeBar.setVisibility(GONE);
            return true;
        }
        return false;
    }

    @Override
    public void onKeyDown() {
        doSomething();
    }

    @Override
    public void songList() {
        showMusicSelectPopWindow();
    }

    @Override
    public void btnCallBack() {
        if(null != methodView){
            methodView.rebroadcast();
        }
    }

    public CustomSeekbar getEqSeeBar() {
        return EqSeeBar;
    }

    /**
     * 设置加载页面
     */
    public void setLoadingLayout(int type,int cas, LoadingLayout.ShowMain showMain) {
//        if(controlMainLayout.getVisibility() == GONE)
//            controlMainLayout.setVisibility(VISIBLE);
        switch (type){
            case 0://初次加载
                if(load_relayout.getVisibility() == GONE){
                    return;
                }
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

    /**
     * 显示 功能菜单
     */
    private void showMusicSelectPopWindow(){

        if(listMenuOne.getVisibility() == GONE){
            listMenuOne.setVisibility(VISIBLE);
        }
        if(load_relayout.getVisibility() == VISIBLE){
            load_relayout.setVisibility(GONE);
        }
        if(controlMainLayout.getVisibility() == VISIBLE){
            controlMainLayout.setVisibility(GONE);
        }
        if(EqSeeBar.getVisibility() == VISIBLE){
            EqSeeBar.setVisibility(GONE);
        }

        if(null == adapterLift){

            adapterLift = new FunctionMenuAdapter(getContext());

            listMenuOne.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x10), 0);
            listMenuOne.setAdapter(adapterLift);

            listMenuOne.setOnItemListener(new TvRecyclerView.OnItemListener() {
                @Override
                public void onItemPreSelected(TvRecyclerView parent, View itemView, int position) {

                }

                @Override
                public void onItemSelected(TvRecyclerView parent, View itemView, int position) {


                }

                @Override
                public void onItemClick(TvRecyclerView parent, View itemView, int position) {

                    if(null !=  methodView){
                        Object o = adapterLift.getItem(position);

                        if(o instanceof  MKSearch){
                            methodView.select((MKSearch) o);
                        }

                    }

                }
            });

            huoqv(0);//显示列表

        }else {
            listMenuOne.setSelection(0);
            if(null != methodView){
                MKSearch mkSearch = methodView.getMk();
                List list = adapterLift.getmDatas();

                if(null != mkSearch && !CheckUtil.isEmpty(list)){

                    for(int i = 0; i<list.size(); i++){
                        MKSearch mkSearchww = (MKSearch) list.get(i);
                        if(mkSearch.getID() == mkSearchww.getID()){
                            listMenuOne.setSelection(i);
                            return;
                        }
                    }

                }
            }
        }




    }

    private void huoqv(int point){

        if(point == 0){//

            if(CheckUtil.isEmpty(songResult)){
                SQLite.select()
                        .from(MKSearch.class)
                        .orderBy(MKSearch_Table.timestamp,true)
                        .async().queryListResultCallback(new QueryTransaction.QueryResultListCallback<MKSearch>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<MKSearch> tResult) {
                        if(!CheckUtil.isEmpty(tResult)){
                            songResult = tResult;
                            if(null != adapterLift){
                                adapterLift.repaceDatas(tResult);
                                listMenuOne.setSelection(0);
                                if(null != methodView){
                                    MKSearch mkSearch = methodView.getMk();
                                    List list = adapterLift.getmDatas();

                                    if(null != mkSearch && !CheckUtil.isEmpty(list)){

                                        for(int i = 0; i<list.size(); i++){
                                            MKSearch mkSearchww = (MKSearch) list.get(i);
                                            if(mkSearch.getID() == mkSearchww.getID()){
                                                listMenuOne.setSelection(i);
                                                return;
                                            }
                                        }

                                    }
                                }
                            }
                        }else {
                            if(null != adapterLift)
                            adapterLift.clearDatasAndNot();
                        }
                    }
                }).execute();// 查询所有记录
            }else {
                if(null != adapterLift){
                    adapterLift.repaceDatas(songResult);
                    listMenuOne.setSelection(0);
                    if(null != methodView){
                        MKSearch mkSearch = methodView.getMk();
                        List list = adapterLift.getmDatas();

                        if(null != mkSearch && !CheckUtil.isEmpty(list)){

                            for(int i = 0; i<list.size(); i++){
                                MKSearch mkSearchww = (MKSearch) list.get(i);
                                if(mkSearch.getID() == mkSearchww.getID()){
                                    listMenuOne.setSelection(i);
                                    return;
                                }
                            }

                        }
                    }

                }
            }

        }else if(point == 1){

            if(CheckUtil.isEmpty(songResult)){

                SQLite.select()
                        .from(MKSearch.class)
                        .orderBy(MKSearch_Table.timestamp,true)
                        .async().queryListResultCallback(new QueryTransaction.QueryResultListCallback<MKSearch>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<MKSearch> tResult) {
                        if(!CheckUtil.isEmpty(tResult)){
                            songResult = tResult;

                                if(null !=  methodView) {
                                    methodView.select(songResult.get(0));
                                }

                        }else {
                            if(null !=  methodView) {
                                methodView.select(null);
                            }
                        }
                    }
                }).execute();// 查询所有记录

            }else {

                    if(null != methodView){
                        MKSearch mkSearch = methodView.getMk();
                        if(null != mkSearch){

                            boolean is = false;

                            for(int i = 0; i<songResult.size(); i++){
                                MKSearch mkSearchww = songResult.get(i);
                                if(mkSearch.getID() == mkSearchww.getID()){

                                    if((i+1) < songResult.size()){
                                        methodView.select(songResult.get(i + 1));
                                    }else {
                                        methodView.select(songResult.get(0));
                                    }

                                    is = true;

                                    break;
                                }
                            }

                            if(!is){
                                methodView.select(songResult.get(0));
                            }

                        }
                    }

            }

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


       dispose();


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
                        if(controlMainLayout.getVisibility() == VISIBLE)
                            controlMainLayout.setVisibility(GONE);

                        if(EqSeeBar.getVisibility() == VISIBLE)
                            EqSeeBar.setVisibility(GONE);

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
