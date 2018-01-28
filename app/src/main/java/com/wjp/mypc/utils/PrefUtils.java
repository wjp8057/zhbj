package com.wjp.mypc.utils;

import android.content.Context;
import android.content.SharedPreferences;

/*
* SharedPreferences(简称SP)是Android中很常用的数据存储方式，SP采用key-value(键值对)形式，主要用于轻量级的数据存储，
* 尤其适合保存应用的配置参数，但不建议使用SP来存储大规模的数据，可能会降低性能。
SP采用xml文件格式来保存数据，改文件所在目录位于/data/data/shared_prefs/。
Context.MODE_PRIVATE：为默认操作模式,代表该文件是私有数据,只能被应用本身访问,在该模式下,写入的内容会覆盖原文件的内容
Context.MODE_APPEND：模式会检查文件是否存在,存在就往文件追加内容,否则就创建新文件.
Context.MODE_WORLD_READABLE和Context.MODE_WORLD_WRITEABLE用来控制其他应用是否有权限读写该文件.
MODE_WORLD_READABLE：表示当前文件可以被其他应用读取.
MODE_WORLD_WRITEABLE：表示当前文件可以被其他应用写入.
*
* */

public class PrefUtils {
    public static boolean getBoolean(Context ctx,String key,boolean defValue){
        SharedPreferences sp=ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static void setBoolean(Context ctx,String key,boolean value){
        SharedPreferences sp=ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    public static void setString(Context ctx,String key,String value){
        SharedPreferences sp=ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public  static  String getString(Context ctx,String key,String defValue){
        SharedPreferences sp=ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }

    public static void setInt(Context ctx, String key, int value) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(Context ctx, String key, int defValue) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

}
