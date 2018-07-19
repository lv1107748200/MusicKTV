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
import com.owen.tvrecyclerview.widget.MetroTitleItemDecoration;

public class HomeAdapter extends CommonRecyclerViewAdapter<ListData> {



    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        ListData listData =    getItem(position);

        if(null != listData){
            if(listData.getType() == 1){
                return 1;
            }else {
                return 2;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        if(viewType == 1){
            return R.layout.item_home_grid;
        }else {
            return R.layout.item_home_title;
        }
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, ListData item, int position) {

        if(item.getType() == 1){
            helper.getHolder()
                    .showImage(R.id.image, ImgDatasUtils.getUrl());

        }else {
            helper.getHolder()
                    .setText(R.id.title,"标题");
        }

    }

}
