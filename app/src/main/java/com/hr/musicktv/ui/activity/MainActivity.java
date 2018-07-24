package com.hr.musicktv.ui.activity;



import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hr.musicktv.R;
import com.hr.musicktv.base.BaseActivity;
import com.hr.musicktv.base.BaseFragment;
import com.hr.musicktv.common.ImmobilizationData;
import com.hr.musicktv.db.RealmDBManger;
import com.hr.musicktv.net.entry.ListData;
import com.hr.musicktv.ui.adapter.ListDataMenuAdapter;
import com.hr.musicktv.ui.adapter.MainFragmentAdapter;
import com.hr.musicktv.ui.adapter.MusicSelectAdapter;
import com.hr.musicktv.ui.fragment.MultipleFragment;
import com.hr.musicktv.utils.DisplayUtils;
import com.hr.musicktv.utils.Formatter;
import com.hr.musicktv.utils.NLog;
import com.hr.musicktv.widget.focus.FocusBorder;
import com.hr.musicktv.widget.single.WhatView;
import com.hr.musicktv.widget.view.TvViewPager;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
/*
*
* */

public class MainActivity extends BaseActivity implements BaseFragment.FocusBorderHelper {

    private Disposable mDisposable;//脉搏

    private long firstTime=0;

    @BindView(R.id.image_log)
    ImageView imageLog;
    @BindView(R.id.tv_notification)
    TextView tvNotification;
    @BindView(R.id.tv_time)
    TextView tvTime;

    @OnClick({R.id.hit_song_layout,R.id.T_one_layout,R.id.T_two_layout,R.id.T_three_layout,
            R.id.song_search_layout,R.id.song_classify_layout,R.id.song_have_layout,R.id.song_used_layout})
    public void Onclick(View v){
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.hit_song_layout://金曲
                intent.setClass(MainActivity.this,SearchOrListDataActivity.class);
                break;
            case R.id.T_one_layout://推1
                intent.setClass(MainActivity.this,SearchOrListDataActivity.class);
                break;
            case R.id.T_two_layout://推2
                intent.setClass(MainActivity.this,SearchOrListDataActivity.class);
                break;
            case R.id.T_three_layout://推3
                intent.setClass(MainActivity.this,SearchOrListDataActivity.class);
                break;
            case R.id.song_search_layout:
                intent.setClass(MainActivity.this,SearchOrListDataActivity.class);
                break;
            case R.id.song_classify_layout:
                intent.setClass(MainActivity.this,ClassifyListDataActicity.class);
                break;
            case R.id.song_have_layout://已点
                intent.setClass(MainActivity.this,MusicListActivity.class);
                intent.putExtra("TYPE",MusicSelectAdapter.SELSONG);
                break;
            case R.id.song_used_layout://已唱
                intent.setClass(MainActivity.this,MusicListActivity.class);
                intent.putExtra("TYPE",MusicSelectAdapter.USEDSONG);
                break;

        }
        startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        super.init();
        setListener();
        initData();
        getTimesPosable();
    }
    private void setListener() {
        mFocusBorder.boundGlobalFocusListener(new FocusBorder.OnFocusCallback() {
            @Override
            public FocusBorder.Options onFocus(View oldFocus, View newFocus) {
                if(null != newFocus){
                    switch (newFocus.getId()){
                        case R.id.hit_song_layout:
                            return FocusBorder.OptionsFactory.get(1.1f, 1.05f, 0);
                    }
                }
                return FocusBorder.OptionsFactory.get(1.1f, 1.1f, 0); //返回null表示不使用焦点框框架
            }
        });
    }

    private void initData(){

    }

    @Override
    public FocusBorder getFocusBorder() {
        return mFocusBorder;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        View rootview = MainActivity.this.getWindow().getDecorView();

        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_LEFT: //向左键
            case KeyEvent.KEYCODE_DPAD_RIGHT:

                WhatView.getInstance().whatOperation(rootview.findFocus(),true);

                break;
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_DOWN:

                WhatView.getInstance().whatOperation(rootview.findFocus(),false);

                break;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(MainActivity.this,"再按一次退出应用",Toast.LENGTH_SHORT).show();
                firstTime=System.currentTimeMillis();
            }else{
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispose();
        RealmDBManger.closed();
    }

    protected void getTimesPosable() {

        dispose();

        mDisposable = Flowable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        tvTime.setText(Formatter.getTime());
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
