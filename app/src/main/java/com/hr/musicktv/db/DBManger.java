package com.hr.musicktv.db;


import android.os.Handler;
import android.os.Looper;

/*
 * lv   2018/7/30
 */
public class DBManger {

    private Handler mainHandler;
    private static DBManger realmDBManger = null;

    public static DBManger getRealmSingle(){
        if(null == realmDBManger){
            synchronized (DBManger.class){
                if(null == realmDBManger){
                    realmDBManger = new DBManger();
                }
            }
        }
        return realmDBManger;
    }
    public static Handler getMainHandler(){
        if(null == getRealmSingle().mainHandler){
            getRealmSingle(). mainHandler = new Handler(Looper.getMainLooper());
        }
        return getRealmSingle(). mainHandler;
    }


}
