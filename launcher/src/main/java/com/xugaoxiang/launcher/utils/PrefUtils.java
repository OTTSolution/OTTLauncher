package com.xugaoxiang.launcher.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 2016/8/31.
 */
public class PrefUtils {

    public static void putString(Context context , String key , String value){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putString(key , value).commit();
    }

    public static String getString(Context context , String key , String defValue){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getString(key , defValue);
    }

    public static void remove(String key, Context context) {
        SharedPreferences sp = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }


}

