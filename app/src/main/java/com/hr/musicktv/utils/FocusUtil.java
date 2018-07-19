package com.hr.musicktv.utils;

import android.view.View;

/**
 * Created by 吕 on 2018/3/20.
 */

public class FocusUtil {

    public static void setFocus(View view){
        if(null == view){
            return;
        }

//        view. setFocusable(true);
//        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }
    public static void clearFocus(View view){
        if(null == view){
            return;
        }

//        view. setFocusable(true);
//        view.setFocusableInTouchMode(true);
        view.clearFocus();
    }


}
