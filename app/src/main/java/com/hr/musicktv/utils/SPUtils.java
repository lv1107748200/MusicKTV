package com.hr.musicktv.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.content.SharedPreferencesCompat.EditorCompat;


import com.hr.musicktv.base.BaseApplation;

import java.util.Map;


/**
 * Created by 吕 on 2017/10/26.
 */

public class SPUtils {

    public static String FILLNAME = "playInfoConfig";

    private volatile static SharedPreferences sp;


    public static void init ( Context context ){
        if(sp == null){
            synchronized (SPUtils.class) {
                if(sp == null){
                    sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
                }
            }
        }
    }

    /**
     * 存入某个key对应的value值
     *
     * @param context
     * @param key
     * @param value
     */
    public static void put( String key, Object value) {
        if(null == sp){
            sp = BaseApplation.getBaseApp().getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        }
        Editor edit = sp.edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        }
       EditorCompat.getInstance().apply(edit);
    }

    public static void putInt(String key, int value) {
        if(null == sp){
            sp = BaseApplation.getBaseApp().getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        }

        Editor edit = sp.edit();
        edit.putInt(key, value);
        EditorCompat.getInstance().apply(edit);
    }
    public static int getInt(String key, int defValue) {
        if(null == sp){
            sp = BaseApplation.getBaseApp().getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    /**
     * 得到某个key对应的值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static Object get( String key, Object defValue) {
        if(null == sp){
            sp = BaseApplation.getBaseApp().getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        }
        if (defValue instanceof String) {
            return sp.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (Long) defValue);
        }
        return null;
    }

    /**
     * 返回所有数据
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        if(null == sp){
            sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        }
        return sp.getAll();
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        if(null == sp){
            sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        }
        Editor edit = sp.edit();
        edit.remove(key);
        EditorCompat.getInstance().apply(edit);
    }

    /**
     * 清除所有内容
     */
    public static void clear() {
        if(null == sp){
            sp = BaseApplation.getBaseApp().getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        }
        Editor edit = sp.edit();
        edit.clear();
        EditorCompat.getInstance().apply(edit);
    }
}
