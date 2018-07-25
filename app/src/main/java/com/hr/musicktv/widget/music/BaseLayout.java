package com.hr.musicktv.widget.music;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/*
 * lv   2018/7/24
 */
public abstract class BaseLayout extends FrameLayout {

    public BaseLayout(@NonNull Context context) {
        this(context,null);
    }

    public BaseLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        addConView(context);
    }

    public abstract void init(Context context);//初始化
    public  void addConView(Context context){}


    public FrameLayout.LayoutParams getParams(){
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        return layoutParams;
    }

}
