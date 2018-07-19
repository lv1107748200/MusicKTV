package com.hr.musicktv.widget.seek;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.hr.musicktv.R;
import com.hr.musicktv.utils.DisplayUtils;

/*
 * lv   2018/7/10
 */
public class LayoutArrows extends ViewGroup {

    private Paint paint;
    private Path muskPath;
    private int roundRadius;//圆角半径
    private int angleHeight;//下角高度
    private float percent=0.3f;//下角在底部的左边占据百分比
    private Bitmap mRectMask;
    private Xfermode mXfermode;

    public LayoutArrows(Context context) {
        this(context,null);
    }

    public LayoutArrows(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LayoutArrows(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    private void init(AttributeSet attrs) {
        paint=new Paint();
        paint.setColor(ContextCompat.getColor(getContext(),R.color.c_008));
        paint.setAntiAlias(true);
        roundRadius = DisplayUtils.getDimen(R.dimen.x6);
        angleHeight = DisplayUtils.getDimen(R.dimen.x10);
        percent = 0.5f;
        // 关键方法
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
        int widthMode = MeasureSpec. getMode(widthMeasureSpec);
        int heightMode = MeasureSpec. getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec. getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec. getSize(heightMeasureSpec);
        int layoutWidth = 0;
        int layoutHeight = 0;


        for(int i=0; i<getChildCount(); i++){
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT
                    , LayoutParams.MATCH_PARENT);
            params.height = sizeHeight - angleHeight;
            getChildAt(i).setLayoutParams(params);
        }


        measureChildren(widthMeasureSpec,heightMeasureSpec);


        if(widthMode == MeasureSpec. EXACTLY){
            //如果布局容器的宽度模式是确定的（具体的size或者match_parent），直接使用父窗体建议的宽度
            layoutWidth = sizeWidth;
        }
        if(heightMode == MeasureSpec. EXACTLY){
            layoutHeight = sizeHeight;
        }

        setMeasuredDimension(layoutWidth, layoutHeight);

    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int width = getMeasuredWidth();
        int hight = getMeasuredHeight();

        for(int i = 0;i<getChildCount();i++){
            getChildAt(i).layout(0,0,width,hight-angleHeight);
        }

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        createMask(canvas);
        super.dispatchDraw(canvas);
    }

    private void createMask(Canvas canvas) {

            int maskWidth = getMeasuredWidth();
            int maskHeight = getMeasuredHeight();
            muskPath = new Path();
            muskPath.moveTo(roundRadius, 0);
            muskPath.lineTo(maskWidth - roundRadius, 0);
            muskPath.arcTo(new RectF(maskWidth - roundRadius * 2, 0, maskWidth, roundRadius * 2), 270, 90);
            muskPath.lineTo(maskWidth, maskHeight - roundRadius - angleHeight);
            muskPath.arcTo(new RectF(maskWidth - roundRadius * 2, maskHeight - roundRadius * 2 - angleHeight, maskWidth, maskHeight - angleHeight), 0, 90);
            muskPath.lineTo(maskWidth * percent + angleHeight, maskHeight - angleHeight);
            muskPath.lineTo(maskWidth * percent, maskHeight);
            muskPath.lineTo(maskWidth * percent - angleHeight, maskHeight - angleHeight);
            muskPath.lineTo(roundRadius, maskHeight - angleHeight);
            muskPath.arcTo(new RectF(0, maskHeight - roundRadius * 2 - angleHeight, roundRadius * 2, maskHeight - angleHeight), 90, 90);
            muskPath.lineTo(0, roundRadius);
            muskPath.arcTo(new RectF(0, 0, roundRadius * 2, roundRadius * 2), 180, 90);

            canvas.drawPath(muskPath, paint);
    }

    public int getAngleHeight() {
        return angleHeight;
    }
}
