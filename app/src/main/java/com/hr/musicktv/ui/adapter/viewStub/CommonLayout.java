package com.hr.musicktv.ui.adapter.viewStub;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.musicktv.R;
import com.hr.musicktv.base.BaseActivity;
import com.hr.musicktv.common.Iddddd;
import com.hr.musicktv.net.entry.ListData;
import com.hr.musicktv.net.entry.response.Result;
import com.hr.musicktv.net.entry.response.WhatList;
import com.hr.musicktv.net.entry.response.WhatType;
import com.hr.musicktv.ui.activity.DetailActivity;
import com.hr.musicktv.ui.activity.ListDataActivity;
import com.hr.musicktv.ui.adapter.ListDataMenuAdapter;
import com.hr.musicktv.ui.fragment.MultipleFragment;
import com.hr.musicktv.utils.CheckUtil;
import com.hr.musicktv.utils.DisplayUtils;
import com.hr.musicktv.utils.GlideUtil;
import com.hr.musicktv.utils.ImgDatasUtils;
import com.hr.musicktv.utils.UrlUtils;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommonLayout {

    private ListDataMenuAdapter listDataMenuAdapter;
    @BindView(R.id.common_menu)
    TvRecyclerView mainMenu;

    @BindView(R.id.tag_title_one)
    TextView tag_title_one;
    @BindView(R.id.tag_title_two)
    TextView tag_title_two;
    @BindView(R.id.tag_title_three)
    TextView tag_title_three;
    @BindView(R.id.tag_title_four)
    TextView tag_title_four;
    @BindView(R.id.tag_title_five)
    TextView tag_title_five;

    @BindView(R.id.tag_image_one)
    ImageView tag_image_one;
    @BindView(R.id.tag_image_two)
    ImageView tag_image_two;
    @BindView(R.id.tag_image_three)
    ImageView tag_image_three;
    @BindView(R.id.tag_image_four)
    ImageView tag_image_four;
    @BindView(R.id.tag_image_five)
    ImageView tag_image_five;

    private BaseActivity mContext;

    private String type;

    private List<WhatList> whatList;

    @OnClick({R.id.tag_flayout_one,R.id.tag_flayout_two,R.id.tag_flayout_three,R.id.tag_flayout_four,R.id.tag_flayout_five})
    public void Onclick(View view){
        Intent intent = new Intent(mContext,DetailActivity.class);

        switch (view.getId()){
            case R.id.tag_flayout_one:
                setIenten(0,intent);
                break;
            case R.id.tag_flayout_two:
                setIenten(1,intent);
                break;
            case R.id.tag_flayout_three:
                setIenten(2,intent);
                break;
            case R.id.tag_flayout_four:
                setIenten(3,intent);
                break;
            case R.id.tag_flayout_five:
                setIenten(4,intent);
                break;

        }
        mContext. startActivity(intent);
    }

    public CommonLayout(View view,BaseActivity context) {
        ButterKnife.bind(this,view);
        mContext = context;
        setListener();
        listDataMenuAdapter = new ListDataMenuAdapter(mContext,ListDataMenuAdapter.TWO);
        mainMenu.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x10), DisplayUtils.getDimen(R.dimen.x40));
        mainMenu.setAdapter(listDataMenuAdapter);

    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setListDataMenuAdapter(List<WhatType> whatTypeList){
        if(!CheckUtil.isEmpty(whatTypeList)){
            if(whatTypeList.size()>=5){
                listDataMenuAdapter.repaceDatas(whatTypeList.subList(0, 5));
            }else {
                listDataMenuAdapter.repaceDatas(whatTypeList);
            }
        }
    }

    private void setIenten(int i,Intent intent){
        if(CheckUtil.isEmpty(whatList)){
            return;
        }
        if(whatList.size() >= (i+1)){
            intent.putExtra("Iddddd",new Iddddd(whatList.get(i).getID(),whatList.get(i).getContxt()));
        }
    }

    public void setView(List<WhatList> whatListList){
        whatList = whatListList;

        for(int i = 0,j = whatListList.size(); i<j; i++){
            WhatList whatList = whatListList.get(i);
            switch (i){
                case 0:
                    GlideUtil.setGlideImage(mContext, UrlUtils.getUrl(whatList.getImgPath()),tag_image_one);
                    tag_title_one.setText(whatList.getTitle());
                    break;
                case 1:
                    GlideUtil.setGlideImage(mContext, UrlUtils.getUrl(whatList.getImgPath()),tag_image_two);
                    tag_title_two.setText(whatList.getTitle());
                    break;
                case 2:
                    GlideUtil.setGlideImage(mContext, UrlUtils.getUrl(whatList.getImgPath()),tag_image_three);
                    tag_title_three.setText(whatList.getTitle());
                    break;
                case 3:
                    GlideUtil.setGlideImage(mContext, UrlUtils.getUrl(whatList.getImgPath()),tag_image_four);
                    tag_title_four.setText(whatList.getTitle());
                    break;
                case 4:
                    GlideUtil.setGlideImage(mContext, UrlUtils.getUrl(whatList.getImgPath()),tag_image_five);
                    tag_title_five.setText(whatList.getTitle());
                    break;
            }
        }
    }

    private void setListener() {

        mainMenu.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                mContext. onMoveFocusBorder(itemView, 1.1f, DisplayUtils.dip2px(3));

            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                Intent intent = new Intent(mContext, ListDataActivity.class);
                intent.putExtra("TYPE",type);
                mContext.startActivity(intent);
            }
        });

        mainMenu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                //  mFocusBorder.setVisible(hasFocus);

            }
        });

    }
}
