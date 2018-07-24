package com.hr.musicktv.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hr.musicktv.R;
import com.hr.musicktv.base.BaseActivity;
import com.hr.musicktv.common.BaseMusicData;
import com.hr.musicktv.net.entry.ListData;
import com.hr.musicktv.ui.adapter.ListDataMenuAdapter;
import com.hr.musicktv.ui.adapter.MusicSelectAdapter;
import com.hr.musicktv.utils.DisplayUtils;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.hr.musicktv.ui.adapter.MusicSelectAdapter.SELSONG;


/*
 * lv   2018/7/23  歌曲列表
 */
public class MusicListActivity extends BaseActivity {

    @BindView(R.id.tv_title_child)
    TextView tvTitleChild;

    @BindView(R.id.main_menu)
    TvRecyclerView mainMenu;
    @BindView(R.id.main_list)
    TvRecyclerView mainList;

    private MusicSelectAdapter musicSelectAdapter;
    private ListDataMenuAdapter listDataMenuAdapter;

    private int type ;

    private int is;

    @Override
    public int getLayout() {
        return R.layout.activity_music_list;
    }

    @Override
    public void init() {
        super.init();
        tvTitleChild.setText("歌曲列表");

        type = getIntent().getIntExtra("TYPE",SELSONG);

        setListener();
        setData();

        is = type;
        mainMenu.setSelection(is);
    }


    private void setListener(){

        mainMenu.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x22), 0);
        mainList.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x15), 0);

        listDataMenuAdapter = new ListDataMenuAdapter(this,ListDataMenuAdapter.ONE);
        mainMenu.setAdapter(listDataMenuAdapter);

        musicSelectAdapter = new MusicSelectAdapter(this,type);
        mainList.setAdapter(musicSelectAdapter);


        mainMenu.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.0f, DisplayUtils.dip2px(3));

                if(is == position){

                }else {
                    is = position;
                    musicSelectAdapter.setType(position);
                    musicSelectAdapter.notifyDataSetChanged();
                }
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

    private void setData(){
        List<ListData> listData = new ArrayList<>();
        for(int i =0 ; i<2; i++){
            ListData listData1 = new ListData();
            if(i==0){
                listData1.setTitle("已点歌曲");
            }else if(i==1){
                listData1.setTitle("已唱歌曲");
            }
            listData.add(listData1);
        }
        listDataMenuAdapter.repaceDatas(listData);


        List<ListData> musicDataList = new ArrayList<>();

        for(int i = 0; i<100; i++){
            ListData baseMusicData = new ListData();

            baseMusicData.setTitle(""+i);

            if(i == 5)
            baseMusicData.setStick(true);


            musicDataList.add(baseMusicData);
        }

        musicSelectAdapter.listSort(musicDataList);

        musicSelectAdapter.repaceDatas(musicDataList);
    }


}
