package com.xugaoxiang.launcher.bean;

/**
 * Created by user on 2016/12/1.
 */
public class Weather {
    /**
     * id : 2
     * typecode : 10
     * citycode : WTW3SJ5ZBJUY
     * type : 阵雨
     * temp : 21
     * city : 上海
     * citypath : 上海,上海,中国
     * mtime : 2016-11-23 09:11:22
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String typecode;
        private String citycode;
        private String type;
        private String temp;
        private String city;
        private String citypath;
        private String mtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTypecode() {
            return typecode;
        }

        public void setTypecode(String typecode) {
            this.typecode = typecode;
        }

        public String getCitycode() {
            return citycode;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCitypath() {
            return citypath;
        }

        public void setCitypath(String citypath) {
            this.citypath = citypath;
        }

        public String getMtime() {
            return mtime;
        }

        public void setMtime(String mtime) {
            this.mtime = mtime;
        }
    }
}
