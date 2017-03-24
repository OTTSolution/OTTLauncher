package com.xugaoxiang.launcher.bean;

import java.util.List;

/**
 * Created by user on 2016/9/2.
 */
public class ServiceAppinfo {

    /**
     * appId : 1
     * appName : app
     * appIcon : http://192.168.1.140/images/989a576f251ffe521be0577f5f4d4ad0.png
     * appPath : com.longjing.MainActivity
     */

    private List<AppInfoBean> data;

    public List<AppInfoBean> getAppInfo() {
        return data;
    }

    public void setAppInfo(List<AppInfoBean> appInfo) {
        this.data = appInfo;
    }

    public static class AppInfoBean {
        private int appId;
        private String appName;
        private String appIcon;
        private String appPath;

        public int getAppId() {
            return appId;
        }

        public void setAppId(int appId) {
            this.appId = appId;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getAppIcon() {
            return appIcon;
        }

        public void setAppIcon(String appIcon) {
            this.appIcon = appIcon;
        }

        public String getAppPath() {
            return appPath;
        }

        public void setAppPath(String appPath) {
            this.appPath = appPath;
        }

        @Override
        public String toString() {
            return "AppInfoBean{" +
                    "appId=" + appId +
                    ", appName='" + appName + '\'' +
                    ", appIcon='" + appIcon + '\'' +
                    ", appPath='" + appPath + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ServiceAppinfo{" +
                "appInfo=" + data +
                '}';
    }
}
