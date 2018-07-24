package com.hr.musicktv.ui.activity;

import android.content.Intent;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hr.musicktv.R;
import com.hr.musicktv.base.BaseActivity;
import com.hr.musicktv.common.Iddddd;
import com.hr.musicktv.net.base.BaseDataResponse;
import com.hr.musicktv.net.base.BaseResponse;
import com.hr.musicktv.net.entry.ListData;
import com.hr.musicktv.net.entry.request.WhatCom;
import com.hr.musicktv.net.entry.response.SearchList;
import com.hr.musicktv.net.entry.response.UserToken;
import com.hr.musicktv.net.entry.response.WhatList;
import com.hr.musicktv.net.http.HttpCallback;
import com.hr.musicktv.net.http.HttpException;
import com.hr.musicktv.ui.adapter.GridAdapter;
import com.hr.musicktv.ui.adapter.MusicSelectAdapter;
import com.hr.musicktv.ui.adapter.base.CommonRecyclerViewHolder;
import com.hr.musicktv.utils.CheckUtil;
import com.hr.musicktv.utils.DisplayUtils;
import com.hr.musicktv.utils.NLog;
import com.hr.musicktv.utils.NToast;
import com.hr.musicktv.widget.AffPasWindow;
import com.hr.musicktv.widget.dialog.LoadingDialog;
import com.hr.musicktv.widget.keyboard.SkbContainer;
import com.hr.musicktv.widget.keyboard.SoftKey;
import com.hr.musicktv.widget.keyboard.SoftKeyBoardListener;
import com.hr.musicktv.widget.single.UserInfoManger;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 搜索页面
 */
public class SearchOrListDataActivity extends BaseActivity implements AffPasWindow.AffPasWindowCallBack {

    public static int pp = 0;

    @BindView(R.id.tv_show_message)
    EditText tv_show_message;
    @BindView(R.id.tv_title_child)
    TextView tvTitleChild;
    @BindView(R.id.skbContainer)
    SkbContainer skbContainer;
    @BindView(R.id.tv_list)
    TvRecyclerView tvList;

    private String type;
    private AffPasWindow affPasWindow;
    private MusicSelectAdapter musicSelectAdapter;

    private StringBuffer stringBuffer;//搜索字符

    private boolean isMore = true;
    private boolean isLoadMore = false;
    private int pageNo = 1;

    private String Tags = "讨债人";

    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void init() {
        super.init();

        Intent intent = getIntent();
        type = intent.getStringExtra("TYPE");
        if(!CheckUtil.isEmpty(type)){
            tvTitleChild.setText(type+getString(R.string.svp_search));
        }else {
            tvTitleChild.setText(getString(R.string.svp_search));
        }


        affPasWindow = new AffPasWindow(this,this);
        skbContainer.setSkbLayout(R.xml.skb_all_key);
        skbContainer.setFocusable(true);
        skbContainer.setFocusableInTouchMode(true);
        // 设置属性(默认是不移动的选中边框)
       // setSkbContainerMove();
        setSkbContainerOther();
        //
       // skbContainer.setSelectSofkKeyFront(true); // 设置选中边框最前面.
        // 监听键盘事件.
        skbContainer.setOnSoftKeyBoardListener(new SoftKeyBoardListener() {
            @Override
            public void onCommitText(SoftKey softKey) {
               // NLog.e(NLog.KEY,"获取view 坐标   :  "+skbContainer.getX());


                int keyCode = softKey.getKeyCode();
                switch (keyCode){
                    case 67:
                        addOrRemove(null,1);
                        break;
                    case 68:
                        addOrRemove(null,2);
                        break;
                    case 69:
                        setSkbContainerOther();
                        skbContainer.setSkbLayout(R.xml.skb_all_key);
                        break;
                    case 70:
                        setSkbContainerOther();
                        skbContainer.setSkbLayout(R.xml.skb_t9_keys);
                        break;
                    default:
                        if ((skbContainer.getSkbLayoutId() == R.xml.skb_t9_keys)) {
                            onCommitT9Text(softKey);
                        } else {

                            NLog.e(NLog.KEY,"--->keycode   :  "+keyCode);

                            String keyLabel = softKey.getKeyLabel();
                            addOrRemove(keyLabel,0);
                        }
                        break;
                }


            }

            @Override
            public void onBack(SoftKey key) {
                    finish();
            }

            @Override
            public void onDelete(SoftKey key) {

            }

        });
        // DEMO（测试键盘失去焦点和获取焦点)
        skbContainer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mOldSoftKey != null)
                        skbContainer.setKeySelected(mOldSoftKey);
                    else
                        skbContainer.setDefualtSelectKey(0, 0);
                } else {
                    mOldSoftKey = skbContainer.getSelectKey();
                    skbContainer.setKeySelected(null);
                }
            }
        });


        setListener();


        initData();

       // load(Tags);
    }

    private void initData(){
        List<ListData> listData = new ArrayList<>();

        for (int i =0 ;i< 37; i++){
            listData.add(new ListData(""+i));
        }

        musicSelectAdapter.repaceDatas(listData);
    }

    private void setListener() {
        tvList.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x22), DisplayUtils.getDimen(R.dimen.x22));
        musicSelectAdapter = new MusicSelectAdapter(this,1){
            @Override
            public void onBindItemHolder(CommonRecyclerViewHolder helper, Object item, int position) {
                super.onBindItemHolder(helper, item, position);
            }
        };
        tvList.setAdapter(musicSelectAdapter);

        tvList.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.1f, DisplayUtils.dip2px(3));
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {


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
              //  load(Tags);
                return isMore; //是否还有更多数据
            }
        });
    }

    private void load(String T){

        if(CheckUtil.isEmpty(T))
            return;

        Tags = T;

        Search();
    }

    private void Search(){
        UserToken userToken = UserInfoManger.getInstance().getUserToken();
        if(null == userToken){
            return;
        }
        WhatCom data = new WhatCom(
                UserInfoManger.getInstance().getToken(),
                "0",
                userToken.getUID(),
                userToken.getGID(),
                userToken.getSign(),
                userToken.getExpire(),
                "20",
                ""+pageNo,
                Tags
        );
        baseService.Search(data, new HttpCallback<BaseResponse<BaseDataResponse<SearchList>>>() {
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
            public void onSuccess(BaseResponse<BaseDataResponse<SearchList>> baseDataResponseBaseResponse) {


                List<WhatList>  whatLists = baseDataResponseBaseResponse.getData().getInfo().get(0).getResult();
                setTvList(whatLists);

            }
        },SearchOrListDataActivity.this.bindUntilEvent(ActivityEvent.STOP));
    }

    private void setTvList(List<WhatList>  whatLists){
        if(isLoadMore){
            tvList.setLoadingMore(false);
        }

        if(!CheckUtil.isEmpty(whatLists)){

            pageNo = pageNo+1;
            if(isLoadMore){
                musicSelectAdapter.appendDatas(whatLists);
            }else {
                musicSelectAdapter.repaceDatas(whatLists);
            }
        }else {
            if(isLoadMore){
                isMore = false;

            }else {
                musicSelectAdapter.clearDatas();
                musicSelectAdapter.notifyDataSetChanged();

            }

        }
    }
    /**
     * 处理T9键盘的按键.
     * @param softKey
     */
    private void onCommitT9Text(SoftKey softKey) {

        switch (softKey.getKeyCode()){
            case 1251:
                addOrRemove("1",0);
                break;
            case 1250:
                addOrRemove("0",0);
                break;
                default:
                   // NLog.e(NLog.KEY,"获取view 坐标  x,y :  "+skbContainer.getX()+","+skbContainer.getY());
                   // NLog.e(NLog.KEY,"获取view 坐标  l,t :  "+softKey.getLeft()+","+softKey.getTop());

                    int x  = (int) (softKey.getLeft() + skbContainer.getX());
                    int y = (int) (softKey.getTop() + skbContainer.getY() + DisplayUtils.getStatusBarHeight(this));

                   // NLog.e(NLog.KEY,"移动view 坐标  x,y :  "+x+","+y);

                    affPasWindow.moveLyout(x - pp,y - pp,softKey.getKeyCode());
                    break;
        }


    }
    private void setSkbContainerMove() {
        mOldSoftKey = null;
        skbContainer.setMoveSoftKey(true); // 设置是否移动按键边框.

        int sise = DisplayUtils.getDimen(R.dimen.x20);

        RectF rectf = new RectF(
                sise,
                sise,
                sise,
                sise
        );
        skbContainer.setSoftKeySelectPadding(rectf); // 设置移动边框相差的间距.
        skbContainer.setMoveDuration(200); // 设置移动边框的时间(默认:300)
        skbContainer.setSelectSofkKeyFront(true); // 设置选中边框在最前面.
    }

    /**
     * 切换布局测试.
     * 因为布局不相同，所以属性不一样，
     * 需要重新设置(不用参考我的,只是DEMO)
     */
    private void setSkbContainerOther() {
        mOldSoftKey = null;
        skbContainer.setMoveSoftKey(false);
        skbContainer.setSoftKeySelectPadding(0);
        skbContainer.setSelectSofkKeyFront(false);
    }

    SoftKey mOldSoftKey;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (skbContainer.onSoftKeyDown(keyCode, event))
            return true;

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (skbContainer.onSoftKeyUp(keyCode, event))
            return true;
        return super.onKeyUp(keyCode, event);
    }
    /**
    *T9子选项
    */
    @Override
    public void whatText(String s) {
        addOrRemove(s,0);
    }

    /**
     * @param s 字符串处理
     */
    private void addOrRemove(String s,int type){
        if(null == stringBuffer){
                stringBuffer = new StringBuffer();
        }
        if(type == 0){//添加
            if(null != s)
                stringBuffer.append(s);
        }else if(type == 1){//删除
            if(stringBuffer.length()>0)
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }else if(type == 2){//清空
            if(stringBuffer.length()>0)
            stringBuffer.delete(0,stringBuffer.length());
        }
        tv_show_message.setText(stringBuffer);



        lifecycleSubject.onNext(ActivityEvent.STOP);
        lifecycleSubject.onNext(ActivityEvent.START);

        isMore = true;
        isLoadMore = false;
        pageNo = 1;

        load(stringBuffer.toString());
    }


}
