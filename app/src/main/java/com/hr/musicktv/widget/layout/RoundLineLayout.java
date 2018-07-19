package com.hr.musicktv.widget.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.hr.musicktv.R;

public class RoundLineLayout extends LinearLayout {
    private float mTopLeftRadius;
    private float mTopRightRadius;
    private float mBottomLeftRadius;
    private float mBottomRightRadius;

    private boolean mIsDrawRound;
    private Path mRoundPath;
    private Paint mRoundPaint;
    private RectF mRoundRectF;

    private boolean mIsDrawn;
    public RoundLineLayout(Context context) {
        this(context, null);
    }

    public RoundLineLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundLineLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundFrameLayout);
            float radius = ta.getDimension(R.styleable.RoundFrameLayout_rfl_radius, 0);
            mTopLeftRadius = ta.getDimension(R.styleable.RoundFrameLayout_topLeftRadius, radius);
            mTopRightRadius = ta.getDimension(R.styleable.RoundFrameLayout_topRightRadius, radius);
            mBottomLeftRadius = ta.getDimension(R.styleable.RoundFrameLayout_bottomLeftRadius, radius);
            mBottomRightRadius = ta.getDimension(R.styleable.RoundFrameLayout_bottomRightRadius, radius);
            ta.recycle();
        }
        mIsDrawRound = mTopLeftRadius != 0 || mTopRightRadius != 0 || mBottomLeftRadius != 0 || mBottomRightRadius != 0;

        mRoundPaint = new Paint();
        mRoundPaint.setAntiAlias(true);
        // 取两层绘制交集。显示下层。
        mRoundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if((w != oldw || h != oldh) && mIsDrawRound) {
            final Path path = new Path();
            mRoundRectF = new RectF(0, 0, w, h);
            final float[] radii = new float[]{
                    mTopLeftRadius, mTopLeftRadius,
                    mTopRightRadius, mTopRightRadius,
                    mBottomRightRadius, mBottomRightRadius,
                    mBottomLeftRadius, mBottomLeftRadius};
            path.addRoundRect(mRoundRectF, radii, Path.Direction.CW);
            mRoundPath = path;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(!mIsDrawRound) {
            super.draw(canvas);
        } else {
            mIsDrawn = true;
            canvas.saveLayer(mRoundRectF, null, Canvas.ALL_SAVE_FLAG);
            super.draw(canvas);
            canvas.drawPath(mRoundPath, mRoundPaint);
            canvas.restore();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if(mIsDrawn || !mIsDrawRound) {
            super.dispatchDraw(canvas);
        } else {
            canvas.saveLayer(mRoundRectF, null, Canvas.ALL_SAVE_FLAG);
            super.dispatchDraw(canvas);
            canvas.drawPath(mRoundPath, mRoundPaint);
            canvas.restore();
        }
    }
}
