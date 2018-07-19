package com.hr.musicktv.ui.adapter;

import android.content.Context;
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
import com.hr.musicktv.ui.adapter.viewholder.MainViewHolder;
import com.hr.musicktv.ui.fragment.MultipleFragment;
import com.hr.musicktv.utils.ColorUtils;
import com.hr.musicktv.utils.GlideUtil;
import com.hr.musicktv.utils.ImgDatasUtils;

public class SubAdapter extends DelegateAdapter.Adapter<MainViewHolder> {

    private Context contextontext;

    private LayoutHelper mLayoutHelper;


    private VirtualLayoutManager.LayoutParams mLayoutParams;
    private int mCount = 0;


    public SubAdapter(Context context, LayoutHelper layoutHelper, int count) {
        this(context, layoutHelper, count, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
    }

    public SubAdapter(Context context, LayoutHelper layoutHelper, int count, @NonNull VirtualLayoutManager.LayoutParams layoutParams) {
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
        return new MainViewHolder(LayoutInflater.from(contextontext).inflate(R.layout.item_home_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        // only vertical
        holder.itemView.setLayoutParams(
                new VirtualLayoutManager.LayoutParams(mLayoutParams));
    }


    @Override
    protected void onBindViewHolderWithOffset(MainViewHolder holder, int position, int offsetTotal) {


    }

    @Override
    public int getItemCount() {
        return mCount;
    }
}
