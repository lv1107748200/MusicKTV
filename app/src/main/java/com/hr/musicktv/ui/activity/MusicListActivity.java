package com.hr.musicktv.ui.activity;

import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.hr.musicktv.R;
import com.hr.musicktv.base.BaseActivity;
import com.hr.musicktv.db.DBResultCallback;
import com.hr.musicktv.db.TabsData;
import com.hr.musicktv.db.UseMusic;
import com.hr.musicktv.net.entry.ListData;
import com.hr.musicktv.net.entry.response.MKSearch;
import com.hr.musicktv.net.entry.response.MKSearch_Table;
import com.hr.musicktv.ui.adapter.ListDataMenuAdapter;
import com.hr.musicktv.ui.adapter.MusicSelectAdapter;
import com.hr.musicktv.utils.CheckUtil;
import com.hr.musicktv.utils.DisplayUtils;
import com.hr.musicktv.utils.FocusUtil;
import com.hr.musicktv.utils.JsonMananger;
import com.hr.musicktv.utils.NLog;
import com.hr.musicktv.widget.layout.AddLineLayout;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


import static com.hr.musicktv.common.ImmobilizationData.SelSong;
import static com.hr.musicktv.common.ImmobilizationData.TAB;
import static com.hr.musicktv.common.ImmobilizationData.UseSong;
import static com.hr.musicktv.ui.adapter.MusicSelectAdapter.SELSONG;


/*
 * lv   2018/7/23  歌曲列表
 */
public class MusicListActivity extends BaseActivity implements MusicSelectAdapter.MusicCallBack {

    @BindView(R.id.tv_title_child)
    TextView tvTitleChild;

    @BindView(R.id.main_menu)
    TvRecyclerView mainMenu;
    @BindView(R.id.main_list)
    TvRecyclerView mainList;
    @BindView(R.id.addLayout)
    AddLineLayout addLayout;

    private MusicSelectAdapter musicSelectAdapter;
    private ListDataMenuAdapter listDataMenuAdapter;

    private int type ;

    private int is = -1;

    private boolean isTure = false;
    private int id = -1;

    @Override
    public int getLayout() {
        return R.layout.activity_music_list;
    }

    @Override
    public void init() {
        super.init();
        tvTitleChild.setText("歌曲列表");

        type = getIntent().getIntExtra("TYPE",SELSONG);

        setListener();
        setData();

        mainMenu.setSelection(type);
    }


    private void setListener(){

        mainMenu.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x22), 0);
        mainList.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x15), 0);

        listDataMenuAdapter = new ListDataMenuAdapter(this,ListDataMenuAdapter.ONE);
        mainMenu.setAdapter(listDataMenuAdapter);

        musicSelectAdapter = new MusicSelectAdapter(this,type);
        mainList.setAdapter(musicSelectAdapter);
        musicSelectAdapter.setMusicCallBack(this);

        mainMenu.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.0f, DisplayUtils.dip2px(3));

                if(is == position){

                }else {
                    is = position;
                    musicSelectAdapter.setType(position);
                    addLayout.showClick();
                    huoqv(position);
                }
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {


            }
        });
        mainList.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
               // onMoveFocusBorder(itemView, 1.01f, DisplayUtils.dip2px(3));
                if(isInclude()){
                    FocusUtil.setFocus(itemView.findViewById(id));
                }else {
                    FocusUtil.setFocus(itemView.findViewById(R.id.btn_song));
                }
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {



            }
        });

        mainMenu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(mainList.hasFocus() && !hasFocus)
                    return;
                mFocusBorder.setVisible(hasFocus);
            }
        });

        mainList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(mainMenu.hasFocus() && !hasFocus)
                    return;
                mFocusBorder.setVisible(hasFocus);
            }
        });

        mainList.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
//                if(oldFocus != null){
//                    NLog.e(NLog.TAGOther,"焦点 oldFocus id ---"+ oldFocus.getId());
//                }
//                if(newFocus != null){
//                    NLog.e(NLog.TAGOther,"焦点 newFocus id ---"+ newFocus.getId());
//                }

                if(isTure){
                    isTure = false;
                    if(null != newFocus){
                        id = newFocus.getId();
                    }
                }
            }
        });
    }

    private void setData(){
        List<ListData> listData = new ArrayList<>();
        for(int i =0 ; i<2; i++){
            ListData listData1 = new ListData();
            if(i==0){
                listData1.setTitle("已点歌曲");
            }else if(i==1){
                listData1.setTitle("已唱歌曲");
            }
            listData.add(listData1);
        }
        listDataMenuAdapter.repaceDatas(listData);
    }

    private void huoqv(int point){

        musicSelectAdapter.clearDatasAndNot();

        if(point == 0){//一点歌曲

                SQLite.select()
                        .from(MKSearch.class)
                        .orderBy(MKSearch_Table.timestamp,true)
                        .async().queryListResultCallback(new QueryTransaction.QueryResultListCallback<MKSearch>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<MKSearch> tResult) {
                        if(!CheckUtil.isEmpty(tResult)){
                            musicSelectAdapter.listSort(tResult);
                            musicSelectAdapter.repaceDatas(tResult);
                            addLayout.hideClick();
                        }else {
                            musicSelectAdapter.clearDatasAndNot();
                            addLayout.hideAndShowMessage(getString(R.string.mk_null_data));
                        }
                    }
                }).execute();// 查询所有记录



        }else if(point == 1){//已唱歌曲
           SQLite.select()
                    .from(UseMusic.class)
                    .orderBy(MKSearch_Table.timestamp,false)
                    .async().queryListResultCallback(new QueryTransaction.QueryResultListCallback<UseMusic>() {
                        @Override
                        public void onListQueryResult(QueryTransaction transaction, @NonNull List<UseMusic> tResult) {
                            if(CheckUtil.isEmpty(tResult)){
                                musicSelectAdapter.clearDatasAndNot();
                                addLayout.hideAndShowMessage(getString(R.string.mk_null_data));
                            }else {
                                List<MKSearch> mkSearches1 = JsonMananger.jsonToList(JsonMananger.beanToJson(tResult),MKSearch.class);

                                if(!CheckUtil.isEmpty(mkSearches1)){
                                    musicSelectAdapter.listSort(mkSearches1);
                                    musicSelectAdapter.repaceDatas(mkSearches1);
                                    addLayout.hideClick();
                                }else {
                                    musicSelectAdapter.clearDatasAndNot();
                                    addLayout.hideAndShowMessage(getString(R.string.mk_null_data));
                                }
                            }
                        }
                    }).execute();// 查询所有记录
        }

    }
    @Override
    public void deleCallBack() {
        addLayout.hideAndShowMessage(getString(R.string.mk_null_data));
    }
    private boolean isInclude(){

        int ID[] = {R.id.btn_song,R.id.btn_del,R.id.btn_down,R.id.btn_stick,R.id.btn_sel,R.id.btn_up};

        for(int i=0; i<ID.length; i++){
            if(ID[i] == id){
                return true;
            }
        }

        return false;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:

                //NLog.e(NLog.TAGOther,"按下左右 --->");
                isTure = true;

                break;
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_DOWN:

                //NLog.e(NLog.TAGOther,"按下上下 --->");

                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(is == 0){
            musicSelectAdapter.baocunList();
        }

    }


}
