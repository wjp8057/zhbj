package com.wjp.mypc.utils;


import android.content.Context;

/*
* 网络缓存工具类
* */
public class CacheUtils {

    /*
     * 获取缓存：
     * url为key
     * 获取后值为null
     * */
    public static String getCache(String key,Context ctx){
        return PrefUtils.getString(ctx,key,null);
    }

    /*
    * 设置缓存：
    * url为key
    * json内容为value
    * */
    public static void setCache(String key, String value, Context ctx){
        PrefUtils.setString(ctx,key,value);
    }
}
