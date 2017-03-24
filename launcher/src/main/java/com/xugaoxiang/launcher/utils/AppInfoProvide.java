package com.xugaoxiang.launcher.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.xugaoxiang.launcher.MyApplication;
import com.xugaoxiang.launcher.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/8/26.
 */
public class AppInfoProvide {

    public static ArrayList<AppInfo> getInstallApps(){
        PackageManager pm = MyApplication.context.getPackageManager();
        List<PackageInfo> infos = pm.getInstalledPackages(0);
        ArrayList<AppInfo> list = new ArrayList<>();
        for (PackageInfo info:infos) {
            ApplicationInfo applicationInfo = info.applicationInfo;
            int flags = applicationInfo.flags;
            if ((flags & ApplicationInfo.FLAG_SYSTEM) != ApplicationInfo.FLAG_SYSTEM){
                if (info.packageName.equals("com.longjingtech.ott.launcher")){
                    continue;
                }
                AppInfo appInfo = new AppInfo();
                appInfo.packageName = info.packageName;
                appInfo.appName = applicationInfo.loadLabel(pm).toString();
                appInfo.appIcon = applicationInfo.loadIcon(pm);
                appInfo.versionName = info.versionName;
                list.add(appInfo);
            }
        }
        return list;
    }
}
