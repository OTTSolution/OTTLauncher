package com.xugaoxiang.launcher;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.xugaoxiang.launcher.utils.Constants;
import com.mob.mobapi.MobAPI;

import java.io.PrintWriter;


public class MyApplication extends Application {

    public static Context context = null;

    public static Handler handler = null;

    public static Thread mainThread = null;

    public static int mainThreadId = 0;

    private static SharedPreferences mSettings;

    @Override
    public void onCreate() {
        super.onCreate();
       // SDKInitializer.initialize(getApplicationContext());
        MobAPI.initSDK(this, Constants.APIKEY);
        context = getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid();
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        Thread.setDefaultUncaughtExceptionHandler(new MyHandler());
    }

    class MyHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            ex.printStackTrace();
            try {
                PrintWriter err = new PrintWriter(
                        Environment.getExternalStorageDirectory()
                                + "MyApp/Log/launch.log");
                ex.printStackTrace(err);
                err.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }

}
