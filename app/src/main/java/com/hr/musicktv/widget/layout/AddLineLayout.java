package com.hr.musicktv.widget.layout;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hr.musicktv.R;
import com.hr.musicktv.utils.NLog;
import com.wang.avi.AVLoadingIndicatorView;

import java.nio.Buffer;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * lv   2018/7/31
 */
public class AddLineLayout extends LinearLayout{

    @BindView(R.id.bufferingIndicator)
    AVLoadingIndicatorView bufferingIndicator;
    @BindView(R.id.tv_message)
    TextView tvMessage;

    public AddLineLayout(Context context) {
        this(context,null);
    }

    public AddLineLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AddLineLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.layout_btn,this);
        ButterKnife.bind(this,view);
    }


    public void hideClick() {

        if(getVisibility() == VISIBLE){
           // bufferingIndicator.hide();
            bufferingIndicator.smoothToHide();
            setVisibility(INVISIBLE);
        }

    }

    public void showClick() {

        if(getVisibility() == INVISIBLE || getVisibility() == GONE){
            bufferingIndicator.smoothToShow();
            tvMessage.setVisibility(INVISIBLE);
            setVisibility(VISIBLE);
        }

    }

    public void hideAndShowMessage(String s){

        if(getVisibility() == INVISIBLE || getVisibility() == GONE){
            bufferingIndicator.smoothToHide();
            setVisibility(VISIBLE);
            tvMessage.setText(s);
            tvMessage.startAnimation(AnimationUtils.loadAnimation(getContext(),android.R.anim.fade_in));
            tvMessage.setVisibility(VISIBLE);
        }else {
            bufferingIndicator.smoothToHide();
            tvMessage.setText(s);
            tvMessage.startAnimation(AnimationUtils.loadAnimation(getContext(),android.R.anim.fade_in));
            tvMessage.setVisibility(VISIBLE);
        }
    }

}
