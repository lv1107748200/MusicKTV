package com.hr.musicktv.widget.seek;


import android.content.Context;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.TextView;

import com.hr.musicktv.R;

/*
 * lv   2018/7/10
 */
public class VernierView {
    WindowManager windowManager;
    WindowManager.LayoutParams params;
    private LayoutArrows layoutArrows;
    private Context context;
    private TextView textView;
    private TextPaint textPaint;

    private boolean isAdd = false;

    private int padding = 0;
    private int paddingLift = 0;
    private int paddingRight = 0;
    private int paddingTop = 0;
    private int paddingBottom = 0;

    public VernierView(Context context) {
        this.context = context;
        layoutArrows =  (LayoutArrows) LayoutInflater.from(context).inflate(R.layout.view_audio_surfaceview, null);
        textView = layoutArrows.findViewById(R.id.tv);

        windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        params.flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.gravity = Gravity.TOP| Gravity.LEFT;
    }

    public void  setPadding(int padding, int paddingLift, int paddingRight, int paddingTop, int paddingBottom) {
        this.padding = padding;
        this.paddingLift = paddingLift;
        this.paddingRight = paddingRight;
        this.paddingTop = paddingTop;
        this.paddingBottom = paddingBottom;
    }

    public void moveOrAddView(int x, int y, String show){
        if(null == textPaint)
         textPaint = textView.getPaint();

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float textheight = fontMetrics.bottom - fontMetrics.top;

        int w = (int) textPaint.measureText(show) + paddingLift+paddingRight;
        int h = (int) textheight+ paddingTop + paddingBottom + layoutArrows.getAngleHeight();

        int zx = x - w/2;
        int zy = y - h - padding;

        textView.setText(show);

        params.height = h;
        params.width = w;
        params.x = zx;
        params.y = zy;
        if(isAdd){
            windowManager.updateViewLayout(layoutArrows,params);
        }else {
            isAdd = !isAdd;
            windowManager.addView(layoutArrows, params);
        }
    }

    public void remove(){
        if(isAdd){
            isAdd = !isAdd;
            windowManager.removeViewImmediate(layoutArrows);
        }
    }

}
