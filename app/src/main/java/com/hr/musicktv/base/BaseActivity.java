package com.hr.musicktv.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;

import com.hr.musicktv.R;
import com.hr.musicktv.net.base.BaseService;

import com.hr.musicktv.widget.focus.FocusBorder;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by 吕 on 2018/3/7.
 */

public class BaseActivity extends AbstractBaseActivity {
    @Inject
    public BaseService baseService;

    public FocusBorder mFocusBorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BaseApplation.getBaseApp().getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);

        init();
    }

    public int getLayout(){
        return 0;
    }

    public void init(){
        // 移动框
        if(null == mFocusBorder) {
//            mFocusBorder = new FocusBorder.Builder().asDrawable().borderResId(R.drawable.focus).build(this);
            mFocusBorder = new FocusBorder.Builder()
                    .asColor()
                    .borderColor(getResources().getColor(R.color.c_0a1))
                    .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 0)
                    .shadowColor(getResources().getColor(R.color.c_082))
                    .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 5)
                    .build(this);
        }
    }


    public void onMoveFocusBorder(View focusedView, float scale) {
        if(null != mFocusBorder) {
            mFocusBorder.onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale));
        }
    }

    public void onMoveFocusBorder(View focusedView, float scale, float roundRadius) {
        if(null != mFocusBorder) {
            mFocusBorder.onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale, roundRadius));
        }
    }

//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        NLog.d(NLog.TAGOther,"keyCode--->" + keyCode);
//
//        switch (keyCode) {
//
//            case KeyEvent.KEYCODE_ENTER:     //确定键enter
//            case KeyEvent.KEYCODE_DPAD_CENTER:
//                NLog.d(NLog.TAGOther,"enter--->");
//
//                break;
//
//            case KeyEvent.KEYCODE_BACK:    //返回键
//                NLog.d(NLog.TAGOther,"back--->");
//
//               // finish();
//
//               // return true;   //这里由于break会退出，所以我们自己要处理掉 不返回上一层
//
//                break;
//
//            case KeyEvent.KEYCODE_SETTINGS: //设置键
//                NLog.d(NLog.TAGOther,"setting--->");
//
//                break;
//            case KeyEvent.KEYCODE_MENU:
//                NLog.d(NLog.TAGOther,"MENU--->");
//
//                break;
//
//            case KeyEvent.KEYCODE_DPAD_DOWN:   //向下键
//
//                /*    实际开发中有时候会触发两次，所以要判断一下按下时触发 ，松开按键时不触发
//                 *    exp:KeyEvent.ACTION_UP
//                 */
//                if (event.getAction() == KeyEvent.ACTION_DOWN){
//
//                    NLog.d(NLog.TAGOther,"down--->");
//                }
//
//                break;
//
//            case KeyEvent.KEYCODE_DPAD_UP:   //向上键
//                NLog.d(NLog.TAGOther,"up--->");
//
//                break;
//
//            case     KeyEvent.KEYCODE_0:   //数字键0
//                NLog.d(NLog.TAGOther,"0--->");
//
//                break;
//
//            case KeyEvent.KEYCODE_DPAD_LEFT: //向左键
//
//                NLog.d(NLog.TAGOther,"left--->");
//
//                break;
//
//            case KeyEvent.KEYCODE_DPAD_RIGHT:  //向右键
//                NLog.d(NLog.TAGOther,"right--->");
//                break;
//
//            case KeyEvent.KEYCODE_INFO:    //info键
//                NLog.d(NLog.TAGOther,"info--->");
//
//                break;
//
//            case KeyEvent.KEYCODE_PAGE_DOWN:     //向上翻页键
//            case KeyEvent.KEYCODE_MEDIA_NEXT:
//                NLog.d(NLog.TAGOther,"page down--->");
//
//                break;
//
//
//            case KeyEvent.KEYCODE_PAGE_UP:     //向下翻页键
//            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
//                NLog.d(NLog.TAGOther,"page up--->");
//
//                break;
//
//            case KeyEvent.KEYCODE_VOLUME_UP:   //调大声音键
//                NLog.d(NLog.TAGOther,"voice up--->");
//
//                break;
//
//            case KeyEvent.KEYCODE_VOLUME_DOWN: //降低声音键
//                NLog.d(NLog.TAGOther,"voice down--->");
//
//                break;
//            default:
//                break;
//        }
//
//        return super.onKeyDown(keyCode, event);
//
//    }
}
