package com.hr.musicktv.ui.activity;


import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hr.musicktv.R;
import com.hr.musicktv.base.BaseActivity;
import com.hr.musicktv.net.entry.ListData;
import com.hr.musicktv.ui.adapter.GridAdapter;
import com.hr.musicktv.ui.adapter.ListDataMenuAdapter;
import com.hr.musicktv.ui.adapter.MusicSelectAdapter;
import com.hr.musicktv.utils.DisplayUtils;
import com.hr.musicktv.utils.NLog;
import com.hr.musicktv.widget.AffPasWindow;
import com.hr.musicktv.widget.keyboard.SkbContainer;
import com.hr.musicktv.widget.keyboard.SoftKey;
import com.hr.musicktv.widget.keyboard.SoftKeyBoardListener;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*
 * lv   2018/7/23  分类搜索
 */
public class ClassifyListDataActicity extends BaseActivity implements AffPasWindow.AffPasWindowCallBack {
    public static int pp = 0;
    @BindView(R.id.tv_title_child)
    TextView tvTitleChild;
    @BindView(R.id.main_menu)
    TvRecyclerView mainMenu;
    @BindView(R.id.main_list)
    TvRecyclerView mainList;
    @BindView(R.id.skbContainer)
    SkbContainer skbContainer;
    @BindView(R.id.tv_show_message)
    EditText tv_show_message;
    @BindView(R.id.skb_layout)
    LinearLayout skb_layout;

    private boolean isMore = true;
    private boolean isLoadMore = false;
    private int pageNo = 1;

    private ListDataMenuAdapter listDataMenuAdapter;
    private GridAdapter gridAdapter;
    private AffPasWindow affPasWindow;
    private StringBuffer stringBuffer;//搜索字符


    @Override
    public int getLayout() {
        return R.layout.activity_classify_list;
    }

    @Override
    public void init() {
        super.init();
        tvTitleChild.setText("分类点歌");
        setListener();
        initData();
        initSkb();
    }

    private void initData(){
        List<ListData> listData = new ArrayList<>();

        for (int i =0 ;i< 37; i++){
            listData.add(new ListData());
        }

        gridAdapter.repaceDatas(listData);

        List<ListData> listData2 = new ArrayList<>();
        for(int i =0 ; i<3; i++){
            ListData listData1 = new ListData();
            if(i==0){
                listData1.setTitle("歌手分类");
            }else if(i==1){
                listData1.setTitle("语种分类");
            }else if(i==2){
                listData1.setTitle("曲风分类");
            }
            listData2.add(listData1);
        }
        listDataMenuAdapter.repaceDatas(listData2);
    }
    private void setListener(){

        mainMenu.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x22), 0);
        mainList.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x22), DisplayUtils.getDimen(R.dimen.x22));

        listDataMenuAdapter = new ListDataMenuAdapter(this,ListDataMenuAdapter.ONE);
        mainMenu.setAdapter(listDataMenuAdapter);

        gridAdapter = new GridAdapter(this);
        mainList.setAdapter(gridAdapter);


        mainMenu.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.0f, DisplayUtils.dip2px(3));


            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {


            }
        });
        mainList.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.1f, DisplayUtils.dip2px(3));
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
    }



    private void initSkb(){

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

                int x  = (int) (softKey.getLeft() + skbContainer.getX()+skb_layout.getX());
                int y = (int) (softKey.getTop() + skbContainer.getY()+skb_layout.getY() + DisplayUtils.getStatusBarHeight(this));

                // NLog.e(NLog.KEY,"移动view 坐标  x,y :  "+x+","+y);

                affPasWindow.moveLyout(x - pp,y - pp,softKey.getKeyCode());
                break;
        }


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



//        lifecycleSubject.onNext(ActivityEvent.STOP);
//        lifecycleSubject.onNext(ActivityEvent.START);
//
//        isMore = true;
//        isLoadMore = false;
//        pageNo = 1;

    }
}
