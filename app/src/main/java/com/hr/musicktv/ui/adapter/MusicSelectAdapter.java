package com.hr.musicktv.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewTreeObserver;

import com.hr.musicktv.R;
import com.hr.musicktv.db.DBResultCallback;
import com.hr.musicktv.db.TabsData;
import com.hr.musicktv.net.entry.response.MKSearch;
import com.hr.musicktv.ui.activity.MusicPlayerActivity;
import com.hr.musicktv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.musicktv.ui.adapter.base.CommonRecyclerViewHolder;
import com.hr.musicktv.utils.CheckUtil;
import com.hr.musicktv.utils.DateUtils;
import com.hr.musicktv.utils.FocusUtil;
import com.hr.musicktv.utils.NLog;
import com.hr.musicktv.utils.NToast;
import com.hr.musicktv.widget.comparator.MusicListComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



import static com.hr.musicktv.common.ImmobilizationData.SelSong;

/*
 * lv   2018/7/19
 */
public class MusicSelectAdapter extends CommonRecyclerViewAdapter {
    public static final int  SELSONG = 0;//一点
    public static final int  USEDSONG = 1;//已唱

    private Context context;


    private int type = -1;
    private MusicCallBack musicCallBack;

    public MusicSelectAdapter(Context context,int type) {
        super(context);
        this.context = context;
        this.type = type;
    }

    public void setMusicCallBack(MusicCallBack musicCallBack) {
        this.musicCallBack = musicCallBack;
    }

    public void setType(int type) {
        this.type = type;

        if(type == 1){
            baocunList();
        }
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_music_one;
    }

    @Override
    public void onBindItemHolder(final CommonRecyclerViewHolder helper, final Object item, int position) {

        if(item instanceof MKSearch){

          final   MKSearch listData = (MKSearch) item;
            helper.getHolder().setText(R.id.title_grid,listData.getTitle());



            if(SELSONG == type){//已点
                helper.getHolder().setVisibility(R.id.btn_sel,View.GONE);

                helper.getHolder().setVisibility(R.id.btn_del,View.VISIBLE);
                helper.getHolder().setVisibility(R.id.btn_down,View.VISIBLE);
                helper.getHolder().setVisibility(R.id.btn_up,View.VISIBLE);
                helper.getHolder().setVisibility(R.id.btn_stick,View.VISIBLE);

                if(listData.isStick()){
                    helper.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.c_96a));
                    helper.getHolder().setText(R.id.btn_stick,"取消置顶");
                }else {
                    helper.getHolder().setText(R.id.btn_stick,"置顶");
                    helper.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.c_96a80));
                }

                helper.getHolder()
                        .setOnClickListener(R.id.btn_song, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baocunList();
                                Intent intent = new Intent(context, MusicPlayerActivity.class);
                                intent.putExtra("PLAY",listData);
                                context.startActivity(intent);
                            }
                        }).setOnClickListener(R.id.btn_del, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Swiped(helper.getLayoutPosition(),listData);
                    }
                }).setOnClickListener(R.id.btn_down, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onMove(true,helper.getLayoutPosition());
                    }
                }).setOnClickListener(R.id.btn_up, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onMove(false,helper.getLayoutPosition());
                    }
                }).setOnClickListener(R.id.btn_stick, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setStick(listData);

                    }
                }).setOnClickListener(R.id.btn_sel, null);

            }else if(USEDSONG == type){//已唱  或  点个页面
                helper.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.c_96a80));

                helper.getHolder().setVisibility(R.id.btn_sel,View.VISIBLE);

                helper.getHolder().setVisibility(R.id.btn_del,View.GONE);
                helper.getHolder().setVisibility(R.id.btn_down,View.GONE);
                helper.getHolder().setVisibility(R.id.btn_up,View.GONE);
                helper.getHolder().setVisibility(R.id.btn_stick,View.GONE);

                helper.getHolder()
                        .setOnClickListener(R.id.btn_song, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MusicPlayerActivity.class);
                        intent.putExtra("PLAY",listData);
                        context.startActivity(intent);
                    }
                })
                .setOnClickListener(R.id.btn_del, null)
                .setOnClickListener(R.id.btn_down, null)
                .setOnClickListener(R.id.btn_up, null)
                .setOnClickListener(R.id.btn_stick, null)
                .setOnClickListener(R.id.btn_sel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baocun(listData);
                    }
                });

            }

        }

    }
    //上下移动替换的方法
    public void onMove(boolean is,int point) {
        int point1 ;

        if(is){

            point1 = point+1;
            if(point1 == getItemCount()){
                NToast.shortToastBaseApp("已是最底部");
            }else {
                MKSearch listData = (MKSearch) getItem(point);
                MKSearch listData1 = (MKSearch) getItem(point1);

                if(listData.isStick() && listData1.isStick()){
                    Collections.swap(getmDatas(), point, point1);
                    this.notifyItemMoved(point, point1);
                }else if(!listData.isStick() && !listData1.isStick()){
                    Collections.swap(getmDatas(), point, point1);
                    this.notifyItemMoved(point, point1);
                }else {
                    NToast.shortToastBaseApp("不能移动");
                }

            }
        }else {
            point1 = point-1;
            if(point1 <0){
                NToast.shortToastBaseApp("已是最顶部");
            }else {
                MKSearch listData = (MKSearch) getItem(point);
                MKSearch listData1 = (MKSearch) getItem(point1);

                if(listData.isStick() && listData1.isStick()){
                    Collections.swap(getmDatas(), point, point1);
                    this.notifyItemMoved(point, point1);
                }else if(!listData.isStick() && !listData1.isStick()){
                    Collections.swap(getmDatas(), point, point1);
                    this.notifyItemMoved(point, point1);
                }else {
                    NToast.shortToastBaseApp("不能移动");
                }
            }

        }


        //替换集合里的数据   调用Collections的方法
      //  Collections.swap(list, oldadapterPosition, newadapterPosition);
        //另一种替换的方法
//        String s1 = list.get(oldadapterPosition);
//        String s2 = list.get(newadapterPosition);
//        list.set(oldadapterPosition,s2);
//        list.set(newadapterPosition,s1);
        //刷新适配器
     //   this.notifyItemMoved(oldadapterPosition, newadapterPosition);
    }

    //左右移动删除的方法
    public void Swiped(int adapterPosition,MKSearch mkSearch) {
        getmDatas().remove(adapterPosition);
        this.notifyItemRemoved(adapterPosition);
        mkSearch.delete();

        if(CheckUtil.isEmpty(getmDatas())){
            if(null != musicCallBack){
                musicCallBack.deleCallBack();
            }
        }
    }

    public void listSort(List list){
        if(CheckUtil.isEmpty(list)){
            return;
        }
        Collections.sort(list, MusicListComparator.getInstance());
    }

    private void setStick(final MKSearch listData){
        if(listData.isStick()){
            listData.setStick(false);
        }else {
            listData.setStick(true);
        }
        listData.update();
        listSort(getmDatas());
        notifyDataSetChanged();
    }

    private void baocun(MKSearch mkSearch){
        mkSearch.setTimestamp(DateUtils.getStringTodayl());
        mkSearch.save();
        NToast.shortToastBaseApp("成功");
    }

    public void baocunList(){
        MKSearch.saveList(getmDatas());
    }


    public interface MusicCallBack{
        void deleCallBack();
    }

}
