package com.xugaoxiang.launcher.bean;

import java.util.ArrayList;

/**
 * Created by user on 2016/8/30.
 */
public class XmlThem {
    public String themsId;
    public ArrayList<Them.ThemBean.ThemInfoBean> themInfoBeen;

    @Override
    public String toString() {
        return "XmlThem{" +
                "themsId='" + themsId + '\'' +
                ", themInfoBeen=" + themInfoBeen +
                '}';
    }
}
