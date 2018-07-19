package com.hr.musicktv.ui.adapter;

import android.content.Context;

import com.hr.musicktv.R;
import com.hr.musicktv.net.entry.ItemBean;
import com.hr.musicktv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.musicktv.ui.adapter.base.CommonRecyclerViewHolder;


/**
 * Created by 吕 on 2018/3/23.
 * item_function_menu
 */

public class FunctionMenuAdapter extends CommonRecyclerViewAdapter {


    public FunctionMenuAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_function_menu;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, Object item, int position) {

        if(item instanceof ItemBean){
            helper.getHolder().setText(R.id.title, ((ItemBean) item).title);
        }

    }


}
