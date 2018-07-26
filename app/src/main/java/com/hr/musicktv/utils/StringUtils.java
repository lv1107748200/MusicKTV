package com.hr.musicktv.utils;


/*
 * lv   2018/7/26
 */
public class StringUtils {
    public static String setWhat(String what){
        if(CheckUtil.isEmpty(what)){
            what = "";
        }
        return what;
    }
    public static String setWhatAdd(String what){
        if(CheckUtil.isEmpty(what)){
            what = "";
            return what;
        }
        what = what + "/";
        return what;
    }
}
