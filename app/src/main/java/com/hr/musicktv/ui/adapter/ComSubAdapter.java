package com.hr.musicktv.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.hr.musicktv.R;
import com.hr.musicktv.common.Iddddd;
import com.hr.musicktv.net.entry.response.WhatList;
import com.hr.musicktv.ui.activity.DetailActivity;
import com.hr.musicktv.ui.activity.DiversityActivity;
import com.hr.musicktv.ui.adapter.viewholder.MainViewHolder;
import com.hr.musicktv.utils.CheckUtil;
import com.hr.musicktv.utils.ColorUtils;
import com.hr.musicktv.utils.GlideUtil;
import com.hr.musicktv.utils.ImgDatasUtils;
import com.hr.musicktv.utils.NLog;
import com.hr.musicktv.utils.UrlUtils;

import java.util.List;

/*
 * lv   2018/7/17
 */
public class ComSubAdapter extends DelegateAdapter.Adapter<MainViewHolder> {
    private Context contextontext;

    private LayoutHelper mLayoutHelper;

//    public final static int TITLE = 444;
//    public final static int LISTLAYOUT = 445;
//    public final static int HEADLayout = 446;


    private VirtualLayoutManager.LayoutParams mLayoutParams;
    private int mCount = 1;

    private List mList;


    public ComSubAdapter(Context context, LayoutHelper layoutHelper, List dataList,@NonNull VirtualLayoutManager.LayoutParams layoutParams) {
        this.contextontext = context;
        this.mLayoutHelper = layoutHelper;
        this.mLayoutParams = layoutParams;
        this.mList = dataList;
    }

    public ComSubAdapter(Context context, LayoutHelper layoutHelper, int count,List list) {
        this(context, layoutHelper, count, list,new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
    }

    public ComSubAdapter(
            Context context,
            LayoutHelper layoutHelper,
            int count,
            List list,
            @NonNull VirtualLayoutManager.LayoutParams layoutParams) {
        this.contextontext = context;
        this.mLayoutHelper = layoutHelper;
        this.mCount = count;
        this.mLayoutParams = layoutParams;
        this.mList = list;
    }
    public ComSubAdapter(
            Context context,
            LayoutHelper layoutHelper,
            int count,
            @NonNull VirtualLayoutManager.LayoutParams layoutParams) {
        this.contextontext = context;
        this.mLayoutHelper = layoutHelper;
        this.mCount = count;
        this.mLayoutParams = layoutParams;
    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        if(type == TITLE){
//            return new MainViewHolder(LayoutInflater.from(contextontext).inflate(R.layout.item_home_title, parent, false));
//        }else if(type == LISTLAYOUT){
//            return new MainViewHolder(LayoutInflater.from(contextontext).inflate(R.layout.item_home_grid_stragg, parent, false));
//        }else if(type == HEADLayout){
//            return new MainViewHolder(LayoutInflater.from(contextontext).inflate(R.layout.item_home_grid, parent, false));
//        }
        return new MainViewHolder(LayoutInflater.from(contextontext).inflate(R.layout.item_home_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        // only vertical
        holder.itemView.setLayoutParams(
                new VirtualLayoutManager.LayoutParams(mLayoutParams));
    }


    @Override
    protected void onBindViewHolderWithOffset(MainViewHolder holder,final int position, int offsetTotal) {
       // NLog.e("--->","onBindViewHolderWithOffset --->" + position + offsetTotal);
//        if(type == TITLE){
//            TextView textView = holder.itemView.findViewById(R.id.title);
//            textView.setText(title);
//        }else if(type == LISTLAYOUT){
//            final Object o = mList.get(position);
//
//            TextView textView = holder.itemView.findViewById(R.id.title_one);
//            ImageView imageView =  holder.itemView.findViewById(R.id.image);
//
//            if(o instanceof WhatList){
//
//                textView.setText(((WhatList) o).getTitle());
//
//                GlideUtil.setGlideImage(contextontext
//                        , UrlUtils.getUrl(((WhatList) o).getImgPath())
//                        ,imageView,R.drawable.hehe);
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent();
//                        intent.setClass(contextontext,DetailActivity.class);
//                        intent.putExtra("Iddddd",new Iddddd(((WhatList) o).getID(),((WhatList) o).getContxt()));
//                        contextontext.startActivity(intent);
//                    }
//                });
//            }
//
//        }else if(type == HEADLayout) {
//            final Object o = mList.get(position);
//
//            TextView textView =  holder.itemView.findViewById(R.id.title);
//            ImageView imageView = holder.itemView.findViewById(R.id.image);
//
//            final Intent intent = new Intent();
//
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if(position == 6){
//                        intent.setClass(contextontext,DiversityActivity.class);
//                        intent.putExtra("DiversityType",DiversityActivity.FAVORITE);
//                    }else if(position == 7){
//                        intent.setClass(contextontext,DiversityActivity.class);
//                        intent.putExtra("DiversityType",DiversityActivity.PLAYERRECORD);
//                    }else {
//                        intent.setClass(contextontext,DetailActivity.class);
//                        intent.putExtra("Iddddd",new Iddddd(((WhatList) o).getID(),((WhatList) o).getContxt()));
//                    }
//                    contextontext.startActivity(intent);
//                }
//            });
//
//            if(o instanceof WhatList){
//                if(position<6){
//                    GlideUtil.setGlideImage(contextontext
//                            , UrlUtils.getUrl(((WhatList) o).getImgPath())
//                            ,imageView,R.drawable.hehe);
//                }else {
//                    imageView.setBackgroundColor(ColorUtils.getColor());
//                }
//
//            }
//            if(position == 6){
//                textView.setVisibility(View.VISIBLE);
//                textView.setText("收藏夹");
//
//            }else if(position == 7){
//                textView.setVisibility(View.VISIBLE);
//                textView.setText("播放记录");
//            }else {
//                textView.setVisibility(View.GONE);
//            }
//        }
    }

    @Override
    public int getItemCount() {
        if(!CheckUtil.isEmpty(mList)){
            return mList.size();
        }
        return mCount;
    }
}
