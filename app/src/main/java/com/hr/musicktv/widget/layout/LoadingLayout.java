package com.hr.musicktv.widget.layout;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hr.musicktv.R;
import com.hr.musicktv.utils.CheckUtil;
import com.hr.musicktv.utils.DisplayUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 吕 on 2018/3/20.
 */

public  class LoadingLayout extends LinearLayout {


    public final static int ONE  = 1;//进度条
    public final static int TWO  = 2;//按钮
    public final static int THREE  = 3;//text video
    public final static int FOUR  = 4;//text 用户中心


    @BindView(R.id.load_layout)
    FrameLayout load_layout;
    @BindView(R.id.layout_message)
    LinearLayout layout_message;
    @BindView(R.id.load_lay_one)
    LinearLayout load_lay_one;
    @BindView(R.id.load_lay_two)
    LinearLayout load_lay_two;
    @BindView(R.id.tv_message)
    TextView tv_message;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.main_message)
    TextView main_message;
    @BindView(R.id.btn_load)
    Button btn_load;

    @OnClick({R.id.btn_load})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_load:

                if(null != loadingCallBack){
                    loadingCallBack.btnCallBack();
                }

                break;
        }
    }
    private LoadingCallBack loadingCallBack;

    public void setLoadingCallBack(LoadingCallBack loadingCallBack) {
        this.loadingCallBack = loadingCallBack;
    }

    public LoadingLayout(Context context) {
        this(context,null);
    }

    public LoadingLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View view = View.inflate(context, R.layout.loading_dialog_layout,this);

        ButterKnife.bind(this,view);
    }
    public void setLoad_layout(@DrawableRes int color){
        load_layout.setBackgroundResource(color);
    }

    public void setLoad_lay_one(int show){
        load_lay_one.setVisibility(show);
    }
    public void setLoad_lay_two(int show){
        load_lay_two.setVisibility(show);
    }
    public void setLayout_message(int show){
        layout_message.setVisibility(show);
    }

    public void setLoad(String message){

        load_lay_one.setVisibility(VISIBLE);
        load_lay_two.setVisibility(GONE);
        layout_message.setVisibility(GONE);


        if(!CheckUtil.isEmpty(message)){
            tv.setText(message);
        }else {
            tv.setText("正在加载...");
        }
    }
    public void setBtnLoad(String btn, String message){

        load_lay_one.setVisibility(GONE);
        load_lay_two.setVisibility(VISIBLE);
        layout_message.setVisibility(GONE);

        if(!CheckUtil.isEmpty(message)){
            tv_message.setText(message);
        }else {
            tv_message.setText("加载失败");
        }
        if(!CheckUtil.isEmpty(btn)){
            btn_load.setText(btn);
            btn_load.setText("重新加载");
        }
    }

    public Button getBtnLoad(){
        return btn_load;
    }

    public void showResult(SpannableStringBuilder spannableStringBuilder, int from){
        load_lay_one.setVisibility(GONE);
        load_lay_two.setVisibility(GONE);
        layout_message.setVisibility(VISIBLE);

        LinearLayout.LayoutParams params = (LayoutParams) main_message.getLayoutParams();

        if( ONE == from){
            params.setMargins(0, (DisplayUtils.getScreenHeight(getContext()) -
                    getContext().getResources().getDimensionPixelOffset(R.dimen.x100))/2,0,0);
            main_message.setLayoutParams(params);
        }else {
            params.setMargins(0, DisplayUtils.getScreenHeight(getContext())/4,0,0);
            main_message.setLayoutParams(params);
        }

        if(null != spannableStringBuilder){

            main_message.setText(spannableStringBuilder);

        }

    }

    public interface  LoadingCallBack{
        void btnCallBack();
    }


    public void setLoadingLayout(int cas,ShowMain showMain){

        switch (cas){
            case ONE:


                if(null != showMain){
                    setLoad(showMain.getText());
                }else {
                    setLoad(null);
                }
                break;
            case TWO:
               // FocusUtil.setFocus(getBtnLoad());
                if(null != showMain){
                    setBtnLoad(showMain.getBtnText(),showMain.getText());
                }else {
                    setBtnLoad(null,null);
                }
                break;
            case THREE:

                if(null != showMain){
                    showResult(showMain.getSpannableStringBuilder(),ONE);
                }else {
                    showResult(null,ONE);
                }

                break;
            case FOUR:
                if(null != showMain){
                    showResult(showMain.getSpannableStringBuilder(),TWO);
                }else {
                    showResult(null,TWO);
                }
                break;
        }

    }

    public  static class  ShowMain{

        public String getText() {
            return null;
        }

        public String getBtnText(){
            return null;
        }

        public SpannableStringBuilder getSpannableStringBuilder() {
            return null;
        }
    }
}
