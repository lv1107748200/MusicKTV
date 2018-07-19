package com.hr.musicktv.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.hr.musicktv.R;
import com.hr.musicktv.net.entry.ListData;
import com.hr.musicktv.net.entry.response.GuestSeries;
import com.hr.musicktv.net.entry.response.VipSeries;
import com.hr.musicktv.net.entry.response.WhatType;
import com.hr.musicktv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.musicktv.ui.adapter.base.CommonRecyclerViewHolder;
import com.hr.musicktv.utils.DisplayUtils;

import java.util.List;

/**
 * Created by 吕 on 2018/3/13.
 */

public class ListDataMenuAdapter extends CommonRecyclerViewAdapter {

    private int isMainMenu;

    public final static int ONE = 1111;
    public final static int TWO = 1112;
    public final static int THREE = 1113;
    public final static int FOUR = 1114;
    public boolean isHead = false;
    private Object selectData;
    private View selectView;

    private int layoutId;

    public ListDataMenuAdapter(Context context,int isMainMenu) {
        super(context);
        this.isMainMenu = isMainMenu;
        switch (isMainMenu){
            case ONE:
                layoutId = R.layout.item_main_menu;
                break;
            case TWO:
                layoutId = R.layout.item_classify_menu;
                break;
            case THREE:
                layoutId = R.layout.item_list_data_menu;
                break;
            case FOUR:
                layoutId = R.layout.item_select_menu;
                break;
        }
    }

    public ListDataMenuAdapter(Context context,int isMainMenu,boolean isHead) {
        super(context);
        this.isMainMenu = isMainMenu;
        this.isHead = isHead;
        switch (isMainMenu){
            case ONE:
                layoutId = R.layout.item_main_menu;
                break;
            case TWO:
                layoutId = R.layout.item_classify_menu;
                break;
            case THREE:
                layoutId = R.layout.item_list_data_menu;
                break;
            case FOUR:
                layoutId = R.layout.item_select_menu;
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            if(isHead)
            return 123;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        if(viewType == 123){
            return R.layout.item_head_list_data;
        }
        return layoutId;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, Object item, int position) {

        if(isHead){//列表搜索页

            if(position == 0){

            }else {
                if(item instanceof WhatType){
                    helper.getHolder().setText(R.id.title, ((WhatType) item).getClassName());
                }
            }

        }else if(isMainMenu == FOUR){//选集
            if(item instanceof  GuestSeries){
                helper.getHolder().setText(R.id.title, ((GuestSeries) item).getName());
                if(((GuestSeries) item).getName().equals(((GuestSeries)selectData).getName())){
                    helper.itemView.setActivated(true);
                    selectView = helper.itemView;
                }else {
                    helper.itemView.setActivated(false);
                }
            }else if(item instanceof  VipSeries){
                helper.getHolder().setText(R.id.title, ((VipSeries) item).getName());
                if(null != selectData){
                    if(((VipSeries) item).getName().equals(((VipSeries)selectData).getName())){
                        helper.itemView.setActivated(true);
                        selectView = helper.itemView;
                    }else {
                        helper.itemView.setActivated(false);
                    }
                }else {
                    helper.itemView.setActivated(false);
                }

            }

        }else if( isMainMenu == TWO){
            if(item instanceof WhatType){
                helper.getHolder().setText(R.id.title, ((WhatType) item).getClassName());
            }
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.width = DisplayUtils.getWide(getmDatas().size(),DisplayUtils.getDimen(R.dimen.x40),DisplayUtils.getDimen(R.dimen.x60));
            helper.getHolder().getView(R.id.common_layout).setLayoutParams(params);

        } else if(isMainMenu == ONE){
            if(item instanceof ListData){
                helper.getHolder().setText(R.id.title, ((ListData) item).getTitle());
            }
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.width = DisplayUtils.getWide(getmDatas().size(),DisplayUtils.getDimen(R.dimen.x30),DisplayUtils.getDimen(R.dimen.x60));
            helper.getHolder().getView(R.id.main_layout).setLayoutParams(params);
        } else {
            if(item instanceof ListData){
                helper.getHolder().setText(R.id.title, ((ListData) item).getTitle());
            }else if(item instanceof WhatType){
                helper.getHolder().setText(R.id.title, ((WhatType) item).getClassName());
            }
        }



    }

    public void setSelectData(Object data){
        selectData = data;
    }

    public View getSelectView() {
        return selectView;
    }

    public void setSelectView(View selectView) {
        if(this.selectView != null){
            this.selectView.setActivated(false);
        }
        this.selectView = selectView;
    }

    public Object getSelectData() {
        return selectData;
    }
}
