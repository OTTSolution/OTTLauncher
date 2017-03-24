package com.xugaoxiang.launcher.utils;

import android.os.Environment;

import com.xugaoxiang.launcher.bean.Them;
import com.xugaoxiang.launcher.bean.XmlThem;

import java.io.File;

/**
 * Created by user on 2016/8/30.
 */
public class Constants {

    public static final String BASE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()+"/MyApp/";

    public static final String LOCAL_URL = Environment.getExternalStorageDirectory() + File.separator + "MyApp" + File.separator + "AppServer";

    public static final String THEM_INFO = BASE_DIR + "Them/theminfo";

    public static final String THEM_IMAGE = BASE_DIR + "Them/themimage";

    public static final String APP_INFO = BASE_DIR + "AppInfo/appinfo";

    public static final String APP_INFO_IMG = BASE_DIR + "AppInfo/appimage";

    public static XmlThem xmlThem;

    public static Them them;

    public static final String APIKEY = "171db28336775";

}
