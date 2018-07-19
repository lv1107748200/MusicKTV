package com.hr.musicktv.widget.pop;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;


import com.hr.musicktv.R;
import com.hr.musicktv.common.ItemDatas;
import com.hr.musicktv.ui.adapter.SelectionsAdapter;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 吕 on 2018/3/23.
 */

public class SelectionsPopWindow extends BasePopupWindow {


    @BindView(R.id.list)
    TvRecyclerView tvRecyclerView;

    private Context context;
    private SelectionsAdapter selectionsAdapter;

    private SelectionsCallBack selectionsCallBack;
    public void setHintCallBack(SelectionsCallBack selectionsCallBack) {
        this.selectionsCallBack = selectionsCallBack;
    }

    public SelectionsPopWindow(Context context, CustomPopuWindConfig config) {
        super(config);
        this.context = context;


        // 设置布局的横纵间距
        tvRecyclerView.setSpacingWithMargins(0, 15);
        tvRecyclerView.setSelectedItemAtCentered(true);
        selectionsAdapter = new SelectionsAdapter(context);
        tvRecyclerView.setAdapter(selectionsAdapter);

        tvRecyclerView.setOnItemListener(new TvRecyclerView.OnItemListener() {
            @Override
            public void onItemPreSelected(TvRecyclerView parent, View itemView, int position) {

            }

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {

            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

                String url ;
                if(position % 2 == 0){
                    url = "http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4";
                }else {
                    url = "http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4";
                }

                if(null != selectionsCallBack){
                    selectionsCallBack.onItemSelected(url);
                }

            }
        });

        selectionsAdapter.repaceDatas(ItemDatas.getDatas(60));
    }

    @Override
    public View getView(Context context ) {

        final View view = LayoutInflater.from(context).inflate(R.layout.pop_selections,null);

        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void show(View parent) {
        super.show(parent);
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.BOTTOM,0, 0);

//            int[] location = new int[2];
//            parent.getLocationOnScreen(location);
//            this.showAtLocation(parent, Gravity.NO_GRAVITY, location[0]+ DisplayUtils.dip2px(context,15), location[1]-this.getHeight());

        } else {
            this.dismiss();
        }
    }

    @Override
    public void onDiss() {

    }


    public interface SelectionsCallBack{
        void onItemSelected(String position);
        void isDimss();
    }
}
