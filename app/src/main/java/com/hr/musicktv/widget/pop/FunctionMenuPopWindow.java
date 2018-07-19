package com.hr.musicktv.widget.pop;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.hr.musicktv.R;
import com.hr.musicktv.net.entry.ItemBean;
import com.hr.musicktv.ui.adapter.FunctionMenuAdapter;
import com.hr.musicktv.utils.CheckUtil;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 吕 on 2018/3/23.
 */

public class FunctionMenuPopWindow extends BasePopupWindow {

    public final static int RATION = 0;//画面比例
    public final static int DECODE = 1;//解码方式


    @BindView(R.id.list_menu_one)
    TvRecyclerView listMenuLift;
    @BindView(R.id.list_menu_two)
    TvRecyclerView listMenuRight;

    private Activity context;

    private FunctionMenuAdapter adapterLift;
    private FunctionMenuAdapter adapterRight;

    private int pot;

    private List<ItemBean> rationList;//画面比例
    private List<ItemBean> decodeList;//解码方式


    private FunctionMenuBack functionMenuBack;
    public void setFunctionMenuCallBack(FunctionMenuBack functionMenuBack) {
        this.functionMenuBack = functionMenuBack;
    }

    public FunctionMenuPopWindow(Activity context, CustomPopuWindConfig config) {
        super(config);
        this.context = context;


        adapterLift = new FunctionMenuAdapter(context);
        adapterRight = new FunctionMenuAdapter(context);

        listMenuLift.setSpacingWithMargins(0, 0);
        listMenuRight.setSpacingWithMargins(0, 0);

        listMenuLift.setAdapter(adapterLift);
        listMenuRight.setAdapter(adapterRight);


        listMenuLift.setOnItemListener(new TvRecyclerView.OnItemListener() {
            @Override
            public void onItemPreSelected(TvRecyclerView parent, View itemView, int position) {

            }

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {

            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

                if(null != functionMenuBack){
                    functionMenuBack.onItemSelected(pot,position);
                }

            }
        });

        listMenuRight.setOnItemListener(new TvRecyclerView.OnItemListener() {
            @Override
            public void onItemPreSelected(TvRecyclerView parent, View itemView, int position) {

            }

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                pot = position;
                switch (position){
                    case RATION:
                        adapterLift.repaceDatas(setRationList());
                        break;
                    case DECODE:
                        adapterLift.repaceDatas(setDecodeList());
                        break;
                }


            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

            }
        });
        adapterLift.repaceDatas(setRationList());
        adapterRight.repaceDatas(setListMenuRight());

    }

    @Override
    public View getView(Context context ) {

        final View view = LayoutInflater.from(context).inflate(R.layout.pop_function_menu,null);

        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void show(View parent) {
        super.show(parent);
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.RIGHT,0, 0);

//            int[] location = new int[2];
//            parent.getLocationOnScreen(location);
//            this.showAtLocation(parent, Gravity.NO_GRAVITY, location[0]+ DisplayUtils.dip2px(context,15), location[1]-this.getHeight());

        } else {
            this.dismiss();
        }
    }

    @Override
    public void onDiss() {

        if(null != functionMenuBack){
            functionMenuBack.isFunctionMenuDimss();
        }

    }

    private List<ItemBean> setRationList(){

        if(null == rationList){
            rationList = new ArrayList<>();
        }

        if(CheckUtil.isEmpty(rationList)){
            rationList.add(new ItemBean("原始比例"));
            rationList.add(new ItemBean("16:9"));
            rationList.add(new ItemBean("4:3"));
            rationList.add(new ItemBean("铺满"));
        }
        return rationList;
    }

    private List<ItemBean> setDecodeList(){
        if(null == decodeList){
            decodeList = new ArrayList<>();
        }

        if(CheckUtil.isEmpty(decodeList)){
            decodeList.add(new ItemBean("软件解码"));
            decodeList.add(new ItemBean("硬件解码"));
        }
        return decodeList;
    }


    private List<ItemBean> setListMenuRight(){
        List<ItemBean> itemBeanList = new ArrayList<>();

       // itemBeanList.add(new ItemBean("已收藏"));
        itemBeanList.add(new ItemBean("画面比例"));
        itemBeanList.add(new ItemBean("解码方式"));

        return itemBeanList;
    }

    public interface FunctionMenuBack{
        void onItemSelected(int point, int select);
        void isFunctionMenuDimss();
    }

}
