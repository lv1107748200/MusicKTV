package com.hr.musicktv.widget.pop;


import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.hr.musicktv.R;
import com.hr.musicktv.base.BaseActivity;
import com.hr.musicktv.ui.adapter.FunctionMenuAdapter;
import com.hr.musicktv.utils.DisplayUtils;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * lv   2018/7/25
 */
public class MusicSelectPopWindow extends BasePopupWindow {

    @BindView(R.id.list_menu_one)
    TvRecyclerView listMenuLift;

    private FunctionMenuAdapter adapterLift;

    private BaseActivity context;
    private MusicSelectMenuBack functionMenuBack;
    public void setFunctionMenuCallBack(MusicSelectMenuBack functionMenuBack) {
        this.functionMenuBack = functionMenuBack;
    }
    public MusicSelectPopWindow(BaseActivity context, CustomPopuWindConfig config) {
        super(config);
        this.context = context;

        adapterLift = new FunctionMenuAdapter(context);

        listMenuLift.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x10), 0);
        listMenuLift.setAdapter(adapterLift);


        listMenuLift.setOnItemListener(new TvRecyclerView.OnItemListener() {
            @Override
            public void onItemPreSelected(TvRecyclerView parent, View itemView, int position) {

            }

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                MusicSelectPopWindow.this.context.onMoveFocusBorder(itemView, 1.05f, DisplayUtils.dip2px(3));

            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

                if(null != functionMenuBack){
                    functionMenuBack.onItemSelected(0,position);
                }

            }
        });

        listMenuLift.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                MusicSelectPopWindow.this.context.setVisible(hasFocus);
            }
        });

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
    public View getView(Context context ) {

        final View view = LayoutInflater.from(context).inflate(R.layout.pop_music_sel,null);

        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onDiss() {

        if(null != functionMenuBack){
            functionMenuBack.isFunctionMenuDimss();
        }

    }


    public interface MusicSelectMenuBack{
        void onItemSelected(int point, int select);
        void isFunctionMenuDimss();
    }
}
