package com.hr.musicktv.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by 吕 on 2017/10/26.
 */

public class BaseApplation extends Application  {
    private static BaseApplation baseApp = null;
    private AppComponent mAppComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApp = this;
        mAppComponent = DaggerAppComponent.create();

        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            setInitRealm();
        }



    }

    public static BaseApplation getBaseApp() {
        return baseApp;
    }

    public AppComponent getAppComponent(){
        return mAppComponent;
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
    private void setInitRealm(){
        Realm.init(this);
        RealmConfiguration myConfig = new RealmConfiguration.Builder()
                .name("vpt.realm")
                .schemaVersion(2)
                .build();
        Realm.setDefaultConfiguration(myConfig);
    }

}
