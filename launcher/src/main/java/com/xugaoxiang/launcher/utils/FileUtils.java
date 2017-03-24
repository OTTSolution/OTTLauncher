package com.xugaoxiang.launcher.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by user on 2016/8/31.
 */
public class FileUtils {

    public static void delete(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                return ;
            }
            for (int i = 0; i < childFiles.length; i++) {
                childFiles[i].delete();
            }
        }
    }

    public static String getFileBeanInfo(String path , String fileName) {
        File file = new File(path , fileName);
        String str = "";
        if (file.exists()){
            try {
                FileInputStream stream = new FileInputStream(file);
                str = StreamUtils.stream2String(stream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static void writeBeanFile(String s , String path , String fileName) throws IOException {
        if (!TextUtils.isEmpty(s)) {
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            File file = new File(path, fileName);
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(s.getBytes());
        }
    }
}
