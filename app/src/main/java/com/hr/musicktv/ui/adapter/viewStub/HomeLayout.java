package com.hr.musicktv.ui.adapter.viewStub;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.hr.musicktv.common.Iddddd;
import com.hr.musicktv.common.ImmobilizationData;
import com.hr.musicktv.net.base.BaseDataResponse;
import com.hr.musicktv.net.base.BaseResponse;
import com.hr.musicktv.net.entry.request.WhatCom;
import com.hr.musicktv.net.entry.response.Result;
import com.hr.musicktv.net.entry.response.UserToken;
import com.hr.musicktv.net.entry.response.WhatList;
import com.hr.musicktv.net.http.HttpCallback;
import com.hr.musicktv.net.http.HttpException;
import com.hr.musicktv.ui.activity.DetailActivity;
import com.hr.musicktv.ui.activity.DiversityActivity;
import com.hr.musicktv.ui.activity.ListDataActivity;
import com.hr.musicktv.ui.adapter.SubAdapter;
import com.hr.musicktv.ui.adapter.viewholder.MainViewHolder;
import com.hr.musicktv.ui.fragment.MultipleFragment;
import com.hr.musicktv.utils.CheckUtil;
import com.hr.musicktv.utils.ColorUtils;
import com.hr.musicktv.utils.DisplayUtils;
import com.hr.musicktv.utils.GlideUtil;
import com.hr.musicktv.utils.ImgDatasUtils;
import com.hr.musicktv.utils.NLog;
import com.hr.musicktv.utils.NToast;
import com.hr.musicktv.utils.UrlUtils;
import com.hr.musicktv.widget.dialog.LoadingDialog;
import com.hr.musicktv.widget.single.UserInfoManger;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeLayout {
    @BindView(R.id.tv_list)
    TvRecyclerView tvList;
    private DelegateAdapter delegateAdapter;
    private BaseActivity mContext;
    private BaseFragment baseFragment;
    private Map<String,List> homePagesListMap;

    List<DelegateAdapter.Adapter> adapters;

    private boolean isMore = true;
    private boolean isLoadMore = false;
    private int pageNo = 3;

    public HomeLayout(View view , BaseFragment baseFragment) {
        ButterKnife.bind(this,view);
        mContext = (BaseActivity) baseFragment.getActivity();
        this.baseFragment = baseFragment;
        setListener();

        final VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        tvList.setLayoutManager(layoutManager);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        tvList.setAdapter(delegateAdapter);
        initData();
    }

    private void initData(){
         adapters = new LinkedList<>();
        homePagesListMap = new HashMap<>();
        setSimulationData();//模拟数据
    }

    private void setListener() {


        tvList.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {

               // NLog.e(NLog.TAGOther,"首页选中序号--->" + position);

                if(position < 8){
                    mContext. onMoveFocusBorder(itemView, 1.05f, DisplayUtils.dip2px(3));
                }else {
                    mContext. onMoveFocusBorder(itemView, 1.1f, DisplayUtils.dip2px(3));
                }


            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                Intent intent = new Intent(mContext,ListDataActivity.class);

                mContext.startActivity(intent);
            }
        });


        tvList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mContext.  mFocusBorder.setVisible(hasFocus);
            }
        });

        tvList.setOnLoadMoreListener(new TvRecyclerView.OnLoadMoreListener() {
            @Override
            public boolean onLoadMore() {
                tvList.setLoadingMore(true); //正在加载数据
                isLoadMore = true;
                ComList();
                return isMore; //是否还有更多数据
            }
        });
    }
    public void load(){
        isMore = true;
        isLoadMore = false;
         pageNo = 3;

        index();
        ComList();
    }

    private void setUpData(String key,List  whatLists){

        NLog.e(NLog.TAGOther,"名字 --->" + key);

        homePagesListMap.put(key,whatLists);
    }
    private void setDelegateAdapter(){

        if(!CheckUtil.isEmpty(homePagesListMap)){
            adapters.clear();

            for (ImmobilizationData.HomePages c : ImmobilizationData.HomePages.values()) {
                List list = homePagesListMap.get(c.getKey());

                if(!CheckUtil.isEmpty(list)){
                    switch (c){
                        case HEAD:
                            if (true) {
                                EightLayoutHelper helper = new EightLayoutHelper(DisplayUtils.getDimen(R.dimen.x20));
                                helper.setColWeights(new float[]{50f,20f,20f,20f,20f,20f,30f,30f});
                                VirtualLayoutManager.LayoutParams lp = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.getDimen(R.dimen.x550));
                                adapters.add(new SubAdapter(mContext, helper, 8, c.getKey(),list,lp){
                                    @Override
                                    public int getItemViewType(int position) {
                                        return GRADONE;
                                    }
                                });
                            }
                            break;
                        case REC:
                            if(true){
                                VirtualLayoutManager.LayoutParams lp = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.getDimen(R.dimen.x60));
                                adapters.add(new SubAdapter(mContext, new LinearLayoutHelper(), 1,c.getKey(),null,lp) {
                                    @Override
                                    public int getItemViewType(int position) {
                                        return GRADTHREE;
                                    }
                                });

                            }
                            if(true){
                                VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.getDimen(R.dimen.x180));
                                 GridLayoutHelper helper = new GridLayoutHelper(7, list.size(),DisplayUtils.getDimen(R.dimen.x20));
                                adapters.add(new SubAdapter(mContext, helper, 7,c.getKey(),list,layoutParams) {
                                    @Override
                                    public int getItemViewType(int position) {
                                        return GRADTWO;
                                    }

                                });
                            }
                            break;
                        default:
                            if(true){
                                VirtualLayoutManager.LayoutParams lp = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.getDimen(R.dimen.x60));
                                adapters.add(new SubAdapter(mContext, new LinearLayoutHelper(), 1,c.getKey(),null,lp) {
                                    @Override
                                    public int getItemViewType(int position) {
                                        return GRADTHREE;
                                    }
                                });

                            }
                            if(true){
                                VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.getDimen(R.dimen.x280));
                                GridLayoutHelper helper = new GridLayoutHelper(6, list.size(),DisplayUtils.getDimen(R.dimen.x20));
                                adapters.add(new SubAdapter(mContext, helper, 7,c.getKey(),list,layoutParams) {
                                    @Override
                                    public int getItemViewType(int position) {
                                        return GRADTWO;
                                    }

                                });
                            }
                            break;
                    }
                }
            }
            delegateAdapter.setAdapters(adapters);
        }


    }

  static   class SubAdapter extends DelegateAdapter.Adapter<MainViewHolder> {

        public final static int GRADONE = 987;
        public final static int GRADTWO = 789;
        public final static int GRADTHREE = 787;


        private Context contextontext;

        private LayoutHelper mLayoutHelper;


        private VirtualLayoutManager.LayoutParams mLayoutParams;
        private int mCount = 0;
        private String type;

        private List list;

        public String getType() {
            return type;
        }

        public SubAdapter(Context context, LayoutHelper layoutHelper, int count) {
            this(context, layoutHelper, count, "",null,new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180));
        }

        public SubAdapter(Context context, LayoutHelper layoutHelper, int count,String type, List list,@NonNull VirtualLayoutManager.LayoutParams layoutParams) {
            this.contextontext = context;
            this.mLayoutHelper = layoutHelper;
            this.mCount = count;
            this.mLayoutParams = layoutParams;
            this.type = type;
            this.list = list;
        }

        @Override
        public LayoutHelper onCreateLayoutHelper() {
            return mLayoutHelper;
        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == GRADONE){
                return new MainViewHolder(LayoutInflater.from(contextontext).inflate(R.layout.item_home_grid, parent, false));

            }else if(viewType == GRADTWO){
                return new MainViewHolder(
                        LayoutInflater.from(contextontext).inflate(R.layout.item_home_grid_stragg, parent, false));

            }else if(viewType == GRADTHREE){
                return new MainViewHolder(
                        LayoutInflater.from(contextontext).inflate(R.layout.item_home_title, parent, false));
            }
            return new MainViewHolder(LayoutInflater.from(contextontext).inflate(R.layout.item_home_grid, parent, false));
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
            // only vertical
            holder.itemView.setLayoutParams(
                    new VirtualLayoutManager.LayoutParams(mLayoutParams));
        }


        @Override
        protected void onBindViewHolderWithOffset(MainViewHolder holder, int position, int offsetTotal) {

        if( GRADONE ==  getItemViewType(position)){
          final   Object o = list.get(position);
            if(o instanceof WhatList){

                ImageView imageView = holder.itemView.findViewById(R.id.image_grid);
                TextView textView = holder.itemView.findViewById(R.id.title_grid);

             final    Intent intent = new Intent();

                if(position == 6){

                    textView.setText("收藏夹");
                    textView.setVisibility(View.VISIBLE);

                    intent.setClass(contextontext,DiversityActivity.class);
                    intent.putExtra("DiversityType",DiversityActivity.FAVORITE);

                    GlideUtil.setGlideImage(contextontext
                            , ""
                            ,imageView,R.drawable.foc_back_image);

                }else if(position == 7){

                    textView.setText("播放记录");
                    textView.setVisibility(View.VISIBLE);

                    intent.setClass(contextontext,DiversityActivity.class);
                    intent.putExtra("DiversityType",DiversityActivity.PLAYERRECORD);

                    GlideUtil.setGlideImage(contextontext
                            , ""
                            ,imageView,R.drawable.foc_image_three);

                }else {
                    textView.setVisibility(View.GONE);
                    GlideUtil.setGlideImage(contextontext
                            , UrlUtils.getUrl(((WhatList) o).getImgPath())
                            ,imageView,R.drawable.hehe);

                    intent.setClass(contextontext,DetailActivity.class);
                    intent.putExtra("Iddddd",new Iddddd(((WhatList) o).getID(),((WhatList) o).getContxt()));

                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contextontext.startActivity(intent);
                    }
                });

            }
        }else if( GRADTWO == getItemViewType(position)){
          final   Object o = list.get(position);
            if(o instanceof WhatList){
                ImageView imageView = holder.itemView.findViewById(R.id.image_grid_stragg);
                TextView textView = holder.itemView.findViewById(R.id.title_grid_stragg);

                textView.setText(((WhatList) o).getTitle());
                GlideUtil.setGlideImage(contextontext
                        , UrlUtils.getUrl(((WhatList) o).getImgPath())
                        ,imageView,R.drawable.hehe);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(contextontext,DetailActivity.class);
                        intent.putExtra("Iddddd",new Iddddd(((WhatList) o).getID(),((WhatList) o).getContxt()));
                        contextontext.startActivity(intent);
                    }
                });

            }
        }else if(GRADTHREE == getItemViewType(position)){

            TextView textView = holder.itemView.findViewById(R.id.title);
            textView.setText(getType());
        }else {

        }

        }

        @Override
        public int getItemCount() {
            if(!CheckUtil.isEmpty(list)){
                return list.size();
            }
            return mCount;
        }
    }

    private void index(){
        UserToken userToken = UserInfoManger.getInstance().getUserToken();
        if(null == userToken)
            return;

        WhatCom data = new WhatCom(
                UserInfoManger.getInstance().getToken(),
                "0",
                userToken.getUID(),
                userToken.getGID(),
                userToken.getSign(),
                userToken.getExpire(),
                "13",
                ""+1
        );
        data.setIsindex(true);
        baseFragment.baseService.index(data, new HttpCallback<BaseResponse<BaseDataResponse<WhatList>>>() {
            @Override
            public void onError(HttpException e) {

            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<WhatList>> baseDataResponseBaseResponse) {

                List<WhatList> whatLists = baseDataResponseBaseResponse.getData().getInfo();

                if(!CheckUtil.isEmpty(whatLists)){
                    if(whatLists.size() >= 8){
                        setUpData(ImmobilizationData.HomePages.HEAD.getKey(),whatLists.subList(0, 8));

                        setUpData(ImmobilizationData.HomePages.REC.getKey(),whatLists.subList(0, 7));

                        setDelegateAdapter();
                    }
                }
            }
        },baseFragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }
    /**
     * 获取列表数据
     */
    private void ComList(){

     final String type = ImmobilizationData.HomePages.getKeyByIndex(pageNo - 1);

        if(null == baseFragment || type == null)
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
                "12",
                ""+1
        );
        baseFragment.baseService.ComList(ImmobilizationData.Tags.getUrlByName(type),data, new HttpCallback<BaseResponse<BaseDataResponse<WhatList>>>() {
            @Override
            public void onError(HttpException e) {
                if(e.getCode() == 1){
                    NToast.shortToastBaseApp(e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }
                if(isLoadMore){
                    tvList.setLoadingMore(false);
                }
            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<WhatList>> baseDataResponseBaseResponse) {

                if(isLoadMore){
                    tvList.setLoadingMore(false);
                }
                if(pageNo < ImmobilizationData.HomePages.values().length){
                    pageNo = pageNo +1;
                }else {
                    isMore = false;
                }

                if(baseDataResponseBaseResponse.getData() != null){
                    BaseDataResponse<WhatList> whatListBaseDataResponse = baseDataResponseBaseResponse.getData();

                    List<WhatList> whatListList = whatListBaseDataResponse.getInfo();

                    if(!CheckUtil.isEmpty(whatListList)){
                        setUpData(type,whatListList);
                        setDelegateAdapter();
                    }

                }

            }
        }, baseFragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }

    private void setSimulationData(){
        List<WhatList> whatListList = new ArrayList<>();
        for(int i =0; i<8; i++){
            WhatList whatList = new WhatList();
            whatList.setTitle("... ...");
            whatList.setImgPath("");
            whatListList.add(whatList);
        }
        if(!CheckUtil.isEmpty(whatListList)){
            if(whatListList.size() >= 8){
                setUpData(ImmobilizationData.HomePages.HEAD.getKey(),whatListList.subList(0, 8));

                setUpData(ImmobilizationData.HomePages.REC.getKey(),whatListList.subList(0, 7));

                setDelegateAdapter();
            }
        }
    }

}
