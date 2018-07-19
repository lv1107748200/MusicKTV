package com.hr.musicktv.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.EightLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.hr.musicktv.R;
import com.hr.musicktv.base.BaseActivity;
import com.hr.musicktv.base.BaseFragment;
import com.hr.musicktv.common.ImmobilizationData;
import com.hr.musicktv.db.DBResultCallback;
import com.hr.musicktv.db.RealmDBManger;
import com.hr.musicktv.db.TabsData;
import com.hr.musicktv.net.base.BaseDataResponse;
import com.hr.musicktv.net.base.BaseResponse;
import com.hr.musicktv.net.entry.ListData;
import com.hr.musicktv.net.entry.request.WhatCom;
import com.hr.musicktv.net.entry.response.UserToken;
import com.hr.musicktv.net.entry.response.WhatList;
import com.hr.musicktv.net.entry.response.WhatType;
import com.hr.musicktv.net.http.HttpCallback;
import com.hr.musicktv.net.http.HttpException;
import com.hr.musicktv.ui.activity.DetailActivity;
import com.hr.musicktv.ui.activity.ListDataActivity;
import com.hr.musicktv.ui.adapter.GridAdapter;
import com.hr.musicktv.ui.adapter.HomeAdapter;
import com.hr.musicktv.ui.adapter.ListDataMenuAdapter;
import com.hr.musicktv.ui.adapter.MetroAdapter;
import com.hr.musicktv.ui.adapter.SubAdapter;
import com.hr.musicktv.ui.adapter.viewStub.ClassifyLayout;
import com.hr.musicktv.ui.adapter.viewStub.CommonLayout;
import com.hr.musicktv.ui.adapter.viewStub.HomeLayout;
import com.hr.musicktv.ui.adapter.viewholder.MainViewHolder;
import com.hr.musicktv.utils.CheckUtil;
import com.hr.musicktv.utils.DisplayUtils;
import com.hr.musicktv.utils.GlideUtil;
import com.hr.musicktv.utils.ImgDatasUtils;
import com.hr.musicktv.utils.NLog;
import com.hr.musicktv.widget.dialog.LoadingDialog;
import com.hr.musicktv.widget.focus.FocusBorder;
import com.hr.musicktv.widget.single.UserInfoManger;
import com.owen.tvrecyclerview.widget.MetroTitleItemDecoration;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.tvrecyclerview.widget.V7GridLayoutManager;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.VirtualLayoutManager.LayoutParams;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.hr.musicktv.common.ImmobilizationData.ANIME;
import static com.hr.musicktv.common.ImmobilizationData.FILM;
import static com.hr.musicktv.common.ImmobilizationData.OVERSEAS;
import static com.hr.musicktv.common.ImmobilizationData.SPORTS;
import static com.hr.musicktv.common.ImmobilizationData.TELEPLAY;
import static com.hr.musicktv.common.ImmobilizationData.VARIETY;

/**
 * 通用版 根据数据不同来区分页面
 */
public class MultipleFragment extends BaseFragment {

    private  int type ;
    private  ListData typeData;
    private boolean isReady;

    private HomeLayout homeLayout;
    private ClassifyLayout classifyLayout;
    private CommonLayout commonLayout;

    private RealmResults realmResults;
    private RealmChangeListener<RealmResults<TabsData>> realmResultsRealmChangeListener;

    public MultipleFragment setType(ListData typeData) {
        this.type = typeData.getType();
        this.typeData = typeData;
        return this;
    }

    public static MultipleFragment getmultipleFragment(){
        return new MultipleFragment();
    }

    @BindView(R.id.view_stub)
    ViewStub viewStub;

    @Override
    public int getLayout() {
        return R.layout.fragment_multiple;
    }

    @Override
    public void init() {
        super.init();

        switch (type){
            case 0:
                viewStub.setLayoutResource(R.layout.item_multiple_home);
                homeLayout = new HomeLayout(viewStub.inflate(),this);
                break;
            case 1:
                viewStub.setLayoutResource(R.layout.item_multiple_classify);
                classifyLayout =  new ClassifyLayout(viewStub.inflate(),mContext);
                break;
            default:
                viewStub.setLayoutResource(R.layout.item_multiple_common);
                commonLayout = new CommonLayout(viewStub.inflate(),mContext);
                break;
        }

    }

    @Override
    public void loadData() {
        super.loadData();
        isReady = false;
        switch (type){
            case 0:
                if(null != homeLayout){
                    homeLayout.load();
                }
                break;
            case 1:

                break;
            default:
                commonLayout.setType(typeData.getTitle());
                //LoadingDialog.showProgress(mContext);
                huoqv();
                ComType();
                ComList();
                break;
        }

    }

    @Override
    public void stopLoad() {
        super.stopLoad();
        if(null != realmResults && null != realmResultsRealmChangeListener){
            realmResults.removeChangeListener(realmResultsRealmChangeListener);
        }
    }

    //首页布局

    //分类试图


    //common
    private void baocun(List<WhatType> whatTypeList){
        isReady = true;

        TabsData tabsData = new TabsData();
        tabsData.setTab(typeData.getTitle());

        if(!CheckUtil.isEmpty(whatTypeList)){
            RealmList<WhatType> whatTypeRealmList = new RealmList<>();
            whatTypeRealmList.addAll(whatTypeList);
            tabsData.setRealmList(whatTypeRealmList);
        }
      //  NLog.e(NLog.DB,"数据库"+typeData.getTitle()+" 保存--->");
        RealmDBManger.copyToRealmOrUpdate(tabsData, new DBResultCallback() {
            @Override
            public void onSuccess(Object o) {
              //  NLog.e(NLog.DB,"数据库 保存成功--->");
            }

            @Override
            public void onError(String errString) {
              //  NLog.e(NLog.DB,"数据库 保存失败--->"+errString);
            }
        });
    }

    private void huoqv(){

       // NLog.e(NLog.DB,"数据库 " +typeData.getTitle()+"  查询 ------------");
                realmResults = RealmDBManger.getMyRealm()
                        .where(TabsData.class)
                        .equalTo("tab",typeData.getTitle())
                        .findAllAsync();
        realmResultsRealmChangeListener = new RealmChangeListener<RealmResults<TabsData>>() {
            @Override
            public void onChange(RealmResults<TabsData> realmResults) {

                new DBResultCallback<RealmResults<TabsData>>(){
                    @Override
                    public void onSuccess(RealmResults<TabsData> realmResults) {
                        if(!CheckUtil.isEmpty(realmResults)){
                           // NLog.e(NLog.DB,"数据库  "+typeData.getTitle()+" --->"+realmResults.size());
                            if(!CheckUtil.isEmpty(realmResults.get(0).getRealmList())){
                                if(!isReady){
                                    commonLayout.setListDataMenuAdapter(realmResults.get(0).getRealmList());
                                }
                            }

                        }
                    }

                    @Override
                    public void onError(String errString) {

                    }
                }.onCallback(realmResults);

            }
        };
            realmResults.addChangeListener(realmResultsRealmChangeListener);

    }

    /**
     * 获取类型
     */
    private void ComType(){
        if(null == typeData.getTags())
            return;
        baseService.ComType(typeData.getTags().getTypeurl(),new HttpCallback<BaseResponse<BaseDataResponse<WhatType>>>() {
            @Override
            public void onError(HttpException e) {

                if(e.getCode() == 1){
                    LoadingDialog.showText(mContext,e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }

            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<WhatType>> baseDataResponseBaseResponse) {
                LoadingDialog.disMiss();

                BaseDataResponse<WhatType> baseDataResponse = baseDataResponseBaseResponse.getData();
                List<WhatType> whatTypeList = baseDataResponse.getInfo();
                baocun(whatTypeList);
                commonLayout.setListDataMenuAdapter(whatTypeList);
            }
        },MultipleFragment.this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }

    /**
     * 获取列表数据
     */
    private void ComList(){
        if(null == typeData.getTags())
            return;

        UserToken userToken = UserInfoManger.getInstance().getUserToken();
        if(null == userToken)
            return;

        WhatCom data = new WhatCom(
                UserInfoManger.getInstance().getToken(),
                "0,1",
                userToken.getUID(),
                userToken.getGID(),
                userToken.getSign(),
                userToken.getExpire(),
                "5",
                ""+1
        );
        baseService.ComList(typeData.getTags().getUrl(),data, new HttpCallback<BaseResponse<BaseDataResponse<WhatList>>>() {
            @Override
            public void onError(HttpException e) {
                if(e.getCode() == 1){
                    LoadingDialog.showText(mContext,e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }
            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<WhatList>> baseDataResponseBaseResponse) {

                if(baseDataResponseBaseResponse.getData() != null){
                    BaseDataResponse<WhatList> whatListBaseDataResponse = baseDataResponseBaseResponse.getData();

                    List<WhatList> whatListList = whatListBaseDataResponse.getInfo();
                    commonLayout.setView(whatListList);
                }

            }
        }, MultipleFragment.this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }


}
