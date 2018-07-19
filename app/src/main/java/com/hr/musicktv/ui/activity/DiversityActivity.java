package com.hr.musicktv.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hr.musicktv.R;
import com.hr.musicktv.base.BaseActivity;
import com.hr.musicktv.common.Iddddd;
import com.hr.musicktv.db.DBResultCallback;
import com.hr.musicktv.db.PHData;
import com.hr.musicktv.db.RealmDBManger;
import com.hr.musicktv.db.TabsData;
import com.hr.musicktv.net.base.BaseDataResponse;
import com.hr.musicktv.net.base.BaseResponse;
import com.hr.musicktv.net.entry.ListData;
import com.hr.musicktv.net.entry.request.WhatCom;
import com.hr.musicktv.net.entry.response.FavoriteList;
import com.hr.musicktv.net.entry.response.Result;
import com.hr.musicktv.net.entry.response.SearchList;
import com.hr.musicktv.net.entry.response.UserToken;
import com.hr.musicktv.net.entry.response.WhatList;
import com.hr.musicktv.net.http.HttpCallback;
import com.hr.musicktv.net.http.HttpException;
import com.hr.musicktv.ui.adapter.GridAdapter;
import com.hr.musicktv.utils.CheckUtil;
import com.hr.musicktv.utils.DisplayUtils;
import com.hr.musicktv.widget.dialog.LoadingDialog;
import com.hr.musicktv.widget.single.UserInfoManger;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

import static com.hr.musicktv.ui.adapter.GridAdapter.FAVORITELAYOUT;
import static com.hr.musicktv.ui.adapter.GridAdapter.PLAYERRECORDLAYOUT;

/**
 * 播放记录，收藏。。。
 */
public class DiversityActivity extends BaseActivity {

    public final static int HISTORIESRECORD = 1001;//历史记录
    public final static int FAVORITE = 1002;//收藏夹
    public final static int PLAYERRECORD = 1003;//播放记录

    private int type = -1;

    @BindView(R.id.tv_title_child)
    TextView tvTitleChild;
    @BindView(R.id.tv_list)
    TvRecyclerView tvList;

    private boolean isMore = true;
    private boolean isLoadMore = false;
    private int pageNo = 1;


    private GridAdapter gridAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_diversity;
    }

    @Override
    public void init() {
        super.init();

         type = getIntent().getIntExtra("DiversityType",-1);
        setListener();
        tvList.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x22), DisplayUtils.getDimen(R.dimen.x22));

        switch (type){
            case HISTORIESRECORD:
                tvTitleChild.setText(getString(R.string.svp_histories));
                break;
            case FAVORITE:
                tvTitleChild.setText(getString(R.string.svp_favorite));
                gridAdapter = new GridAdapter(this,FAVORITELAYOUT);
                break;
            case PLAYERRECORD:
                tvTitleChild.setText(getString(R.string.svp_recently_played_videos));
                gridAdapter = new GridAdapter(this,PLAYERRECORDLAYOUT);
                break;

        }

        tvList.setAdapter(gridAdapter);
        load();
    }

    private void setListener() {


        tvList.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.1f, DisplayUtils.dip2px(3));
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                Object o =   gridAdapter.getItem(position);
                Intent intent = new Intent();
                switch (type){
                    case HISTORIESRECORD:

                        break;
                    case FAVORITE:

                     if(o instanceof Result){
                         intent.setClass(DiversityActivity.this,DetailActivity.class);
                         intent.putExtra("Iddddd",new Iddddd(((Result) o).getVideoID(),((Result) o).getSecretVID()));
                         startActivity(intent);
                     }
                        break;
                    case PLAYERRECORD:
                        if(o instanceof PHData){
                            intent.setClass(DiversityActivity.this,PlayerActivity.class);
                            intent.putExtra("PLAYID",((PHData) o).getContxt());
                            startActivity(intent);
                        }
                        break;
                }

            }
        });


        tvList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mFocusBorder.setVisible(hasFocus);
            }
        });

        //边界监听
//        mRecyclerView.setOnInBorderKeyEventListener(new TvRecyclerView.OnInBorderKeyEventListener() {
//            @Override
//            public boolean onInBorderKeyEvent(int direction, int keyCode, KeyEvent event) {
//                Log.i("zzzz", "onInBorderKeyEvent: ");
//                return false;//需要拦截返回true,否则返回false
//            }
//        });


        tvList.setOnLoadMoreListener(new TvRecyclerView.OnLoadMoreListener() {
            @Override
            public boolean onLoadMore() {
                tvList.setLoadingMore(true); //正在加载数据
                isLoadMore = true;
                load();
                return isMore; //是否还有更多数据
            }
        });
    }

    private void load(){
        switch (type){
            case HISTORIESRECORD:

                break;
            case FAVORITE:
             FavoriteList();
                break;
            case PLAYERRECORD:
                getPHData();
                break;
        }
    }

    private void getPHData(){
        RealmDBManger.getMyRealm()
                .where(PHData.class)
                .findAllAsync().addChangeListener(new RealmChangeListener<RealmResults<PHData>>() {
            @Override
            public void onChange(RealmResults<PHData> phData) {
                new DBResultCallback<RealmResults<PHData>>(){
                    @Override
                    public void onSuccess(RealmResults<PHData> phData) {
                        if(!CheckUtil.isEmpty(phData)){
                            gridAdapter.repaceDatas(phData);
                        }
                    }

                    @Override
                    public void onError(String errString) {

                    }
                }.onCallback(phData);
            }
        });
    }

    private void FavoriteList(){
        UserToken userToken = UserInfoManger.getInstance().getUserToken();
        if(null == userToken){
            return;
        }
        WhatCom whatCom = new WhatCom(
                UserInfoManger.getInstance().getToken(),
                "0",
                userToken.getUID(),
                userToken.getGID(),
                userToken.getSign(),
                userToken.getExpire()
        );

        whatCom.setPageSize("20");
        whatCom.setPageIndex(pageNo+"");

        baseService.FavoriteList(whatCom, new HttpCallback<BaseResponse<BaseDataResponse<FavoriteList>>>() {
            @Override
            public void onError(HttpException e) {
                if(e.getCode() == 1){
                    LoadingDialog.showText(DiversityActivity.this,e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }
            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<FavoriteList>> baseDataResponseBaseResponse) {
                if(isLoadMore){
                    tvList.setLoadingMore(false);
                }
                BaseDataResponse<FavoriteList> baseDataResponse = baseDataResponseBaseResponse.getData();
                List<FavoriteList> whatLists = baseDataResponse.getInfo();
                List<Result> results = new ArrayList<>();
                if(!CheckUtil.isEmpty(whatLists)){
                    results = whatLists.get(0).getResult();
                }

                if(!CheckUtil.isEmpty(results)){

                    pageNo = pageNo+1;
                    if(isLoadMore){
                        gridAdapter.appendDatas(results);
                    }else {
                        gridAdapter.repaceDatas(results);
                    }
                }else {
                    if(isLoadMore){
                        isMore = false;

                    }else {
                        gridAdapter.clearDatas();
                        gridAdapter.notifyDataSetChanged();
                    }

                }
            }
        },DiversityActivity.this.bindUntilEvent(ActivityEvent.DESTROY));
    }

}
