package com.hr.musicktv.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.hr.musicktv.R;
import com.hr.musicktv.net.entry.ListData;
import com.hr.musicktv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.musicktv.ui.adapter.base.CommonRecyclerViewHolder;
import com.hr.musicktv.utils.ImgDatasUtils;
import com.owen.tvrecyclerview.widget.MetroGridLayoutManager;
import com.owen.tvrecyclerview.widget.MetroTitleItemDecoration;

/**
 * Created by owen on 2017/7/14.
 */

public class MetroAdapter extends CommonRecyclerViewAdapter<ListData>
        implements MetroTitleItemDecoration.Adapter{
    public MetroAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_home_grid;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, ListData item, int position) {
        helper.getHolder()
                .showImage(R.id.image, ImgDatasUtils.getUrl());


        final View itemView = helper.itemView;
        MetroGridLayoutManager.LayoutParams lp = (MetroGridLayoutManager.LayoutParams) itemView.getLayoutParams();

      if(position > 7) {
            lp.sectionIndex = 1;
            lp.isSuportIntelligentScrollEnd = false;
            lp.isSuportIntelligentScrollStart = true;

             lp.rowSpan = 5;
             lp.colSpan = 10;

//            if(position < 10) {
//                lp.rowSpan = 15;
//                lp.colSpan = 20;
//            } else if(position < 14) {
//                lp.rowSpan = 9;
//                lp.colSpan = 15;
//            } else {
//                lp.rowSpan = 7;
//                lp.colSpan = 12;
//            }
        } else {
            lp.sectionIndex = 0;

//            if(position == 0){
//                lp.rowSpan = 10;
//                lp.colSpan = 12;
//            }else if(position == 1 || position == 6 || position==3||position==4||position==5){
//                lp.rowSpan = 2;
//                lp.colSpan = 4;
//            }else {
//                lp.rowSpan = 5;
//                lp.colSpan = 8;
//            }

            if(position == 0){
                lp.rowSpan = 10;
                lp.colSpan = 12;
            }else if(position == 1 || position == 2 ){
                lp.rowSpan = 5;
                lp.colSpan = 8;
            }else {
                lp.rowSpan = 2;
                lp.colSpan = 4;

            }


//            if(position == 0 || position == 7 || position==6) {
//                lp.rowSpan = 2;
//                lp.colSpan = 4;
//            } else {
//                lp.rowSpan = 6;
//                lp.colSpan = 4;
//            }
        }

        itemView.setLayoutParams(lp);
    }

    @Override
    public View getTitleView(int index, RecyclerView parent) {
        if(index == 0){
            return null;
        }
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_title, parent, false);
    }
}
