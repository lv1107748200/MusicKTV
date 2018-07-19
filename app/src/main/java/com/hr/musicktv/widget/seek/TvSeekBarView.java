package com.hr.musicktv.widget.seek;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.hr.musicktv.R;
import com.hr.musicktv.utils.DisplayUtils;
import com.hr.musicktv.utils.Formatter;

/*
 * lv   2018/7/10
 */
public class TvSeekBarView extends View {


    private int mPaddingLeft;
    private int mPaddingRight;
    private int mMeasuredWidth;
    private int mPaddingTop;

    private float mThumbRadius;
    private float mThumbHight;
    private float mTrackY;
    private float mSeekLength;
    private float lineHight;
    private float mSeekStart;
    private float mSeekEnd;

    private float mMax = 100;
    private float mMin = 0;
    private float mProgress = 0;

    private float touchX = 0;

    private Paint mPaint;
    private VernierView vernierView;

    private int[] mLocation = new int[2];

    private boolean isShow = false;

    private @ColorInt
    int colorBack;
    private @ColorInt
    int colorLine;
    private @ColorInt
    int colorThumb;


    public TvSeekBarView(Context context) {
        this(context,null);
    }

    public TvSeekBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TvSeekBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        vernierView = new VernierView(getContext());
        vernierView.setPadding(DisplayUtils.getDimen(R.dimen.x2),DisplayUtils.getDimen(R.dimen.x10)
                ,DisplayUtils.getDimen(R.dimen.x10),DisplayUtils.getDimen(R.dimen.x2),DisplayUtils.getDimen(R.dimen.x2));

        mThumbRadius = DisplayUtils.getDimen(R.dimen.x15);
        mThumbHight = mThumbRadius * 2.0f;
        lineHight = DisplayUtils.getDimen(R.dimen.x8);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        colorBack = ContextCompat.getColor(getContext(), R.color.c_687);

        colorLine  = ContextCompat.getColor(getContext(),R.color.c_007);
        colorThumb  = ContextCompat.getColor(getContext(),R.color.c_008);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = Math.round(mThumbHight + .5f + getPaddingTop() + getPaddingBottom());

        initSeekBarInfo();
        setMeasuredDimension(widthMeasureSpec,height);
    }

    @Override
    protected  void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStrokeWidth(lineHight);

        mPaint.setColor(colorBack);
        canvas.drawLine(mSeekStart, mTrackY, mSeekEnd, mTrackY, mPaint);

        mPaint.setColor(colorLine);
        float x = getThumbx();
        canvas.drawLine( mSeekStart, mTrackY, x, mTrackY, mPaint);

        drawThumb(canvas,x);
        if(isShow){
            setVernier(x);
        }
    }
    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (View.GONE == visibility || View.INVISIBLE == visibility) {
            if (vernierView != null) {
                isShow = false;
                vernierView.remove();
            }
        }
    }

    private float getThumbx(){
        return mSeekStart + touchX;
    }

    /**
     * @param xdd 刷新弹出气泡数据
     */
    private void setVernier(float xdd ){
        this.getLocationOnScreen(mLocation);
        int x = mLocation[0];
        int y = mLocation[1];

        int zx = (int) (x+xdd);
        int zy = y;


        vernierView.moveOrAddView(zx,zy,Formatter.formatTime((int) mProgress));
    }
    public void setShow(boolean isShow){
        this.isShow = isShow;
        if(isShow){

        }else {
            if (vernierView != null) {
                vernierView.remove();
            }
        }

    }
    public synchronized void setmMax(float mMax) {
        this.mMax = mMax;
    }
    public synchronized void setProgress(float progress,boolean isShow) {
       this.isShow = isShow;

        if (progress < mMin) {
            mProgress = mMin;
        } else if (progress > mMax) {
            mProgress = mMax;
        } else {
            mProgress = progress;
        }
        touchX = (mProgress - mMin) * mSeekLength / (mMax - mMin);
        initSeekBarInfo();
        postInvalidate();
    }
    public synchronized void setProgress(float progress) {
        isShow = true;

        if (progress < mMin) {
           mProgress = mMin;
        } else if (progress > mMax) {
            mProgress = mMax;
        } else {
            mProgress = progress;
        }
        touchX = (mProgress - mMin) * mSeekLength / (mMax - mMin);
        initSeekBarInfo();
        postInvalidate();
    }
    private void initSeekBarInfo() {
        mMeasuredWidth = getMeasuredWidth();
        mPaddingLeft = getPaddingLeft();
        mPaddingRight = getPaddingRight();
        mPaddingTop = getPaddingTop();
        mSeekLength = mMeasuredWidth - mPaddingLeft - mPaddingRight- mThumbRadius*2;

        mTrackY = mPaddingTop + mThumbRadius;

        mSeekStart = mPaddingLeft + mThumbRadius ;
        mSeekEnd = mMeasuredWidth - mPaddingRight - mThumbRadius;
    }

    private void drawThumb(Canvas canvas, float thumbX) {
        mPaint.setColor(colorThumb);
        canvas.drawCircle(thumbX, mTrackY,mThumbRadius, mPaint);
    }
}
