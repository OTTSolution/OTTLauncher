package com.xugaoxiang.launcher.bean;

import java.util.List;

/**
 * Created by user on 2016/8/29.
 */
public class Them {


    /**
     * themCover :
     * themInfo : [{"themId":10001,"themUrl":"http://192.168.191.1:8080/10001.jpg"},{"themId":10002,"themUrl":"http://192.168.191.1:8080/10002.jpg"},{"themId":10003,"themUrl":"http://192.168.191.1:8080/10003.jpg"}]
     * themName :
     * themsId : 1
     */

    private ThemBean them;

    public ThemBean getThem() {
        return them;
    }

    public void setThem(ThemBean them) {
        this.them = them;
    }

    public static class ThemBean {
        private String themCover;
        private String themName;
        private String themsId;
        /**
         * themId : 10001
         * themUrl : http://192.168.191.1:8080/10001.jpg
         */

        private List<ThemInfoBean> themInfo;

        public String getThemCover() {
            return themCover;
        }

        public void setThemCover(String themCover) {
            this.themCover = themCover;
        }

        public String getThemName() {
            return themName;
        }

        public void setThemName(String themName) {
            this.themName = themName;
        }

        public String getThemsId() {
            return themsId;
        }

        public void setThemsId(String themsId) {
            this.themsId = themsId;
        }

        public List<ThemInfoBean> getThemInfo() {
            return themInfo;
        }

        public void setThemInfo(List<ThemInfoBean> themInfo) {
            this.themInfo = themInfo;
        }

        public static class ThemInfoBean {
            private int themId;
            private String themUrl;

            public int getThemId() {
                return themId;
            }

            public void setThemId(int themId) {
                this.themId = themId;
            }

            public String getThemUrl() {
                return themUrl;
            }

            public void setThemUrl(String themUrl) {
                this.themUrl = themUrl;
            }

            @Override
            public String toString() {
                return "ThemInfoBean{" +
                        "themId=" + themId +
                        ", themUrl='" + themUrl + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ThemBean{" +
                    "themCover='" + themCover + '\'' +
                    ", themName='" + themName + '\'' +
                    ", themsId='" + themsId + '\'' +
                    ", themInfo=" + themInfo +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Them{" +
                "them=" + them +
                '}';
    }
}
