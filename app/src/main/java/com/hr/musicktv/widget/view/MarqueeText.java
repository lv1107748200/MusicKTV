package com.hr.musicktv.widget.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarqueeText extends android.support.v7.widget.AppCompatTextView {
    public MarqueeText(Context con) {
        super(con);
    }
    public MarqueeText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MarqueeText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {

    }
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        //super.onWindowFocusChanged(hasWindowFocus);
    }

}
