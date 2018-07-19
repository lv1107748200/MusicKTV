package com.hr.musicktv.widget.layout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class SlideLayout extends LinearLayout {


    private  Scroller mScroller;

    public SlideLayout(Context context) {
        this(context,null);
    }

    public SlideLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScroller =new Scroller(context);

    }



    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()) {
            ((View)getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
