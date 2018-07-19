package com.hr.musicktv.widget.single;

import android.view.View;

import com.daimajia.androidanimations.library.YoYo;
import com.hr.musicktv.base.BaseApplation;
import com.hr.musicktv.utils.NLog;

import static com.daimajia.androidanimations.library.Techniques.Bounce;
import static com.daimajia.androidanimations.library.Techniques.Shake;

/**
 * 判断焦点是否变化
 */
public class WhatView {
    volatile private static WhatView instance = null;

    private static View contrastView = null;

    public static WhatView getInstance(){
        if(instance == null){
            synchronized (WhatView.class) {
                if(instance == null){
                    instance = new WhatView();
                }
            }
        }

        return instance;
    }

    public void  whatOperation(View view,boolean is){


//        if(null != view){
//            NLog.e(NLog.TAGOther,"whatOperation--->"+view.toString() );
//            if(null != contrastView)
//            NLog.e(NLog.TAGOther,"whatOperation--->"+contrastView.toString() );
//
//            if(view == contrastView){
//                NLog.e(NLog.TAGOther,"whatOperation---> ture" );
//                if(is){
//                    YoYo.with(Shake)
//                            .duration(400)
//                            .playOn(view);
//
//                }else {
//                    YoYo.with(Bounce)
//                            .duration(400)
//                            .playOn( view);
//
//                }
//
//            }else {
//                NLog.e(NLog.TAGOther,"whatOperation---> false" );
//
//            }
//
//            contrastView = view;
//        }

    }


}
