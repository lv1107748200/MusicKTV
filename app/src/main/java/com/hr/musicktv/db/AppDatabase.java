package com.hr.musicktv.db;


import com.raizlabs.android.dbflow.annotation.Database;

/*
 * lv   2018/7/30
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    //数据库名称
    public static final String NAME = "MKDB";
    //数据库版本号
    public static final int VERSION = 1;
}
