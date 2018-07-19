package com.hr.musicktv.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hr.musicktv.R;
import com.hr.musicktv.ui.activity.SearchActivity;
import com.hr.musicktv.utils.DisplayUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * T9选择
 */
public class AffPasWindow implements View.OnKeyListener {
    WindowManager windowManager;
    WindowManager.LayoutParams params;
    private LinearLayout linearLayout;
    private AffPasWindowCallBack pasWindowCallBack;

    private boolean isAdd = false;

    private Activity mActivity;

    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_p_one)
    TextView tv_p_one;
    @BindView(R.id.tv_p_two)
    TextView tv_p_two;
    @BindView(R.id.tv_P_three)
    TextView tv_P_three;
    @BindView(R.id.tv_P_four)
    TextView tv_P_four;
    @OnClick({R.id.tv_num,R.id.tv_p_one,R.id.tv_p_two,R.id.tv_P_three,R.id.tv_P_four})
    public void Onclick(View view){
           if(null != pasWindowCallBack){
               pasWindowCallBack.whatText(""+((TextView)view).getText().toString());
           }

    }

    public AffPasWindow(Activity context,AffPasWindowCallBack affPasWindowCallBack) {

        mActivity = context;
        this.pasWindowCallBack = affPasWindowCallBack;

        linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_paster, null);

        ButterKnife.bind(this,linearLayout);

        windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.height = DisplayUtils.getDimen(R.dimen.x200) + SearchActivity.pp*2;
        params.width = DisplayUtils.getDimen(R.dimen.x200)+ SearchActivity.pp*2;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        params.flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        params.gravity = Gravity.TOP| Gravity.LEFT;
        //linearLayout.setFocusableInTouchMode(true);

        tv_num.setOnKeyListener(this);
        tv_p_one.setOnKeyListener(this);
        tv_p_two.setOnKeyListener(this);
        tv_P_three.setOnKeyListener(this);
        tv_P_four.setOnKeyListener(this);
    }


    public void moveLyout(int x,int y,int code){

        params.x = x;
        params.y = y;

        if(!isAdd){
            isAdd = true;
            windowManager.addView(linearLayout, params);
        }else {
            windowManager.updateViewLayout(linearLayout,params);
        }

        if(tv_P_four.getVisibility() != View.GONE)
        tv_P_four.setVisibility(View.GONE);

        switch (code){
            case 1252:
                tv_num.setText("2");
                tv_p_one.setText("A");
                tv_p_two.setText("B");
                tv_P_three.setText("C");
                break;
            case 1253:
                tv_num.setText("3");
                tv_p_one.setText("D");
                tv_p_two.setText("E");
                tv_P_three.setText("F");
                break;
            case 1254:
                tv_num.setText("4");
                tv_p_one.setText("G");
                tv_p_two.setText("H");
                tv_P_three.setText("I");
                break;
            case 1255:
                tv_num.setText("5");
                tv_p_one.setText("J");
                tv_p_two.setText("K");
                tv_P_three.setText("L");
                break;
            case 1256:
                tv_num.setText("6");
                tv_p_one.setText("M");
                tv_p_two.setText("N");
                tv_P_three.setText("O");
                break;
            case 1257:
                tv_num.setText("7");
                tv_p_one.setText("P");
                tv_p_two.setText("Q");
                tv_P_three.setText("R");
                tv_P_four.setVisibility(View.VISIBLE);
                tv_P_four.setText("S");
                break;
            case 1258:
                tv_num.setText("8");
                tv_p_one.setText("T");
                tv_p_two.setText("U");
                tv_P_three.setText("V");
                break;
            case 1259:
                tv_num.setText("9");
                tv_p_one.setText("W");
                tv_p_two.setText("X");
                tv_P_three.setText("Y");
                tv_P_four.setVisibility(View.VISIBLE);
                tv_P_four.setText("Z");
                break;
        }

    }

    public void removeLayout(){

        if(isAdd){
            isAdd = false;
            windowManager.removeViewImmediate(linearLayout);
        }

    }

    public boolean isAdd() {
        return isAdd;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if (i == KeyEvent.KEYCODE_BACK) {
            if(isAdd){
                removeLayout();
            }else {

            }
            return true;
        }
        return false;
    }

    public interface AffPasWindowCallBack{
        void whatText(String s);
    }
}
