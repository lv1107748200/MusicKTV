package com.hr.mylibrary.playerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


import com.hr.mylibrary.utils.GSYVideoType;
import com.hr.mylibrary.utils.MeasureHelper;
import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * SurfaceView
 * Created by guoshuyu on 2017/8/26.
 */

public class CusTomSurfaceView extends SurfaceView implements SurfaceHolder.Callback2,
        IGSYRenderView, MeasureHelper.MeasureFormVideoParamsListener {

    private IGSYSurfaceListener mIGSYSurfaceListener;

    private MeasureHelper.MeasureFormVideoParamsListener mVideoParamsListener;

    private MeasureHelper measureHelper;

    public CusTomSurfaceView(Context context) {
        super(context);
        init();
    }

    public CusTomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        measureHelper = new MeasureHelper(this, this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureHelper.prepareMeasure(widthMeasureSpec, heightMeasureSpec, (int) getRotation());
        setMeasuredDimension(measureHelper.getMeasuredWidth(), measureHelper.getMeasuredHeight());
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mIGSYSurfaceListener != null) {
            mIGSYSurfaceListener.onSurfaceAvailable(holder.getSurface());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mIGSYSurfaceListener != null) {
            mIGSYSurfaceListener.onSurfaceSizeChanged(holder.getSurface(), width, height);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //清空释放
        if (mIGSYSurfaceListener != null) {
            mIGSYSurfaceListener.onSurfaceDestroyed(holder.getSurface());
        }
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {

    }

    @Override
    public IGSYSurfaceListener getIGSYSurfaceListener() {
        return mIGSYSurfaceListener;
    }

    @Override
    public void setIGSYSurfaceListener(IGSYSurfaceListener surfaceListener) {
        getHolder().addCallback(this);
        this.mIGSYSurfaceListener = surfaceListener;
    }

    @Override
    public int getSizeH() {
        return measureHelper.getMeasuredHeight();
    }

    @Override
    public int getSizeW() {
        return measureHelper.getMeasuredWidth();
    }

    @Override
    public Bitmap initCover() {
        Logger.d(getClass().getSimpleName() + " not support initCover now");

        return null;
    }

    @Override
    public Bitmap initCoverHigh() {
        Logger.d(getClass().getSimpleName() + " not support initCoverHigh now");
        return null;
    }


    @Override
    public View getRenderView() {
        return this;
    }

    @Override
    public void onRenderResume() {
        Logger.d(getClass().getSimpleName() + " not support onRenderResume now");
    }

    @Override
    public void onRenderPause() {
        Logger.d(getClass().getSimpleName() + " not support onRenderPause now");
    }

    @Override
    public void releaseRenderAll() {
        Logger.d(getClass().getSimpleName() + " not support releaseRenderAll now");
    }

    @Override
    public void setRenderMode(int mode) {
        Logger.d(getClass().getSimpleName() + " not support setRenderMode now");
    }


    @Override
    public void setRenderTransform(Matrix transform) {
        Logger.d(getClass().getSimpleName() + " not support setRenderTransform now");
    }


    @Override
    public void setGLMVPMatrix(float[] MVPMatrix) {
        Logger.d(getClass().getSimpleName() + " not support setGLMVPMatrix now");
    }



    @Override
    public void setVideoParamsListener(MeasureHelper.MeasureFormVideoParamsListener listener) {
        mVideoParamsListener = listener;
    }

    @Override
    public int getCurrentVideoWidth() {
        if (mVideoParamsListener != null) {
            return mVideoParamsListener.getCurrentVideoWidth();
        }
        return 0;
    }

    @Override
    public int getCurrentVideoHeight() {
        if (mVideoParamsListener != null) {
            return mVideoParamsListener.getCurrentVideoHeight();
        }
        return 0;
    }

    @Override
    public int getVideoSarNum() {
        if (mVideoParamsListener != null) {
            return mVideoParamsListener.getVideoSarNum();
        }
        return 0;
    }

    @Override
    public int getVideoSarDen() {
        if (mVideoParamsListener != null) {
            return mVideoParamsListener.getVideoSarDen();
        }
        return 0;
    }

    /**
     * 添加播放的view
     */
    public static CusTomSurfaceView addSurfaceView(Context context, ViewGroup textureViewContainer, int rotate,
                                                final IGSYSurfaceListener gsySurfaceListener,
                                                final MeasureHelper.MeasureFormVideoParamsListener videoParamsListener) {
        if (textureViewContainer.getChildCount() > 0) {
            textureViewContainer.removeAllViews();
        }
        CusTomSurfaceView showSurfaceView = new CusTomSurfaceView(context);
        showSurfaceView.setIGSYSurfaceListener(gsySurfaceListener);
        showSurfaceView.setVideoParamsListener(videoParamsListener);
      //  showSurfaceView.setRotation(rotate);

        addToParent(textureViewContainer, showSurfaceView);

        return showSurfaceView;
    }


    public static void addToParent(ViewGroup textureViewContainer, View render) {

        int params = getTextureParams();

        if (textureViewContainer instanceof RelativeLayout) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(params, params);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            textureViewContainer.addView(render, layoutParams);
        } else if (textureViewContainer instanceof FrameLayout) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(params, params);
            layoutParams.gravity = Gravity.CENTER;
            textureViewContainer.addView(render, layoutParams);
        }
    }
    /**
     * 获取布局参数
     *
     * @return
     */
    public static int getTextureParams() {
        boolean typeChanged = (GSYVideoType.getShowType() != GSYVideoType.SCREEN_TYPE_DEFAULT);
        return (typeChanged) ? ViewGroup.LayoutParams.WRAP_CONTENT : ViewGroup.LayoutParams.MATCH_PARENT;
    }
}
