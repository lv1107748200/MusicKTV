/*
    ShengDao Android Client, JsonMananger
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.hr.musicktv.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.util.TypeUtils;

import java.util.List;



/**
 * [JSON解析管理类]
 *
 * @author huxinwu
 * @version 1.0
 * @date 2014-3-5
 *
 **/
public class JsonMananger {

    static {
        TypeUtils.compatibleWithJavaBean = true;
    }
    private static final String tag = JsonMananger.class.getSimpleName();

    /**
     * 将json字符串转换成java对象
     * @param json
     * @param cls
     * @return
     * @throws HttpException
     */
    public static <T> T jsonToBean(String json, Class<T> cls)  {
        return JSON.parseObject(json, cls);
    }

    /**
     * 将json字符串转换成java对象泛型
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T jsonToBeanT(String json, TypeReference<T> type){
        return JSON.parseObject(json, type);
    }

    /**
     * 将json字符串转换成java List对象
     * @param json
     * @param cls
     * @return
     * @throws HttpException
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls)  {
        return JSON.parseArray(json, cls);
    }

    /**
     * 将bean对象转化成json字符串
     * @param obj
     * @return
     * @throws HttpException
     */
    public static String beanToJson(Object obj)  {
        String result = JSON.toJSONString(obj);
        Log.e(tag, "beanToJson: " + result);
        return result;
    }

}
