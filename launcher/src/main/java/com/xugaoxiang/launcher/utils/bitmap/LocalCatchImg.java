package com.xugaoxiang.launcher.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by user on 2016/8/31.
 */
public class LocalCatchImg {

    public static Bitmap getBitmapFromLocal(String url , String path){
        File file = new File(path , getUrlSubString(url));
        if (file.exists()){
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                return bitmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void setBitmapToLocal(Bitmap bitmap , String url , String path){
        File dirs = new File(path);
        if (!dirs.exists() || !dirs.isDirectory()){
            dirs.mkdirs();
        }
        File file = new File(path , getUrlSubString(url));
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG , 100 , new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getUrlSubString(String url){
        int index = url.lastIndexOf("/");
        String substring = url.substring(index+1);
        return substring;
    }
}
