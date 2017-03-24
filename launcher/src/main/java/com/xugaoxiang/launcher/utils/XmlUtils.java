package com.xugaoxiang.launcher.utils;

import android.os.Environment;
import android.util.Xml;

import com.xugaoxiang.launcher.bean.ServiceAppinfo;
import com.xugaoxiang.launcher.bean.Them;
import com.xugaoxiang.launcher.bean.XmlThem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/8/30.
 */
public class XmlUtils {

    private static ArrayList<Them.ThemBean.ThemInfoBean> themInfoBeen;
    private static List<ServiceAppinfo.AppInfoBean> infoBeanList;

    public static void createThemXmlFile(Them them){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            XmlSerializer xs = Xml.newSerializer();
            File dirFile = new File(Constants.THEM_INFO);
            if (!dirFile.exists() || !dirFile.isDirectory()){
                dirFile.mkdirs();
            }
            try {
                File file = new File(Constants.THEM_INFO , "them.xml");
                FileOutputStream fos = new FileOutputStream(file);
                xs.setOutput(fos , "utf-8");
                xs.startDocument("utf-8" , true);
                xs.startTag(null , "them");
                xs.startTag(null, "themsId");
                xs.text(them.getThem().getThemsId());
                xs.endTag(null , "themsId");
                for (Them.ThemBean.ThemInfoBean infoBean : them.getThem().getThemInfo()) {
                    xs.startTag(null , "themInfo");

                    xs.startTag(null , "themId");
                    xs.text(infoBean.getThemId()+"");
                    xs.endTag(null , "themId");

                    xs.startTag(null , "themUrl");
                    xs.text(infoBean.getThemUrl());
                    xs.endTag(null , "themUrl");

                    xs.endTag(null , "themInfo");
                }
                xs.endTag(null , "them");
                xs.endDocument();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static XmlThem pullThemXml(){
        File file = new File(Constants.THEM_INFO , "them.xml");
        XmlThem them = null;
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                XmlPullParser xpp = Xml.newPullParser();
                xpp.setInput(fis, "utf-8");
                int type = xpp.getEventType();
                Them.ThemBean.ThemInfoBean bean = null;
                while (type != XmlPullParser.END_DOCUMENT) {
                    switch (type) {
                        case XmlPullParser.START_TAG:
                            if ("themsId".equals(xpp.getName())) {
                                them = new XmlThem();
                                them.themsId = xpp.nextText();
                                themInfoBeen = new ArrayList<>();
                            } else if ("themInfo".equals(xpp.getName())) {
                                bean = new Them.ThemBean.ThemInfoBean();
                            } else if ("themId".equals(xpp.getName())) {
                                bean.setThemId(Integer.parseInt(xpp.nextText()));
                            } else if ("themUrl".equals(xpp.getName())) {
                                bean.setThemUrl(xpp.nextText());
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if ("themInfo".equals(xpp.getName())) {
                                themInfoBeen.add(bean);
                            } else if ("them".equals(xpp.getName())) {
                                them.themInfoBeen = themInfoBeen;
                            }
                            break;
                    }
                    type = xpp.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return them;
        }
        else {
            return them;
        }
    }

    public static void createAppinfoFile(ServiceAppinfo appinfo){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            XmlSerializer xs = Xml.newSerializer();
            File dirFile = new File(Constants.APP_INFO);
            if (!dirFile.exists() || !dirFile.isDirectory()){
                dirFile.mkdirs();
            }
            try {
                File file = new File(dirFile , "appInfo.xml");
                FileOutputStream fos = new FileOutputStream(file);
                xs.setOutput(fos , "utf-8");
                xs.startDocument("utf-8" , true);
                xs.startTag(null , "appInfos");
                for (ServiceAppinfo.AppInfoBean app:appinfo.getAppInfo()) {
                    xs.startTag(null , "appInfo");

                    xs.startTag(null , "appId");
                    xs.text(app.getAppId()+"");
                    xs.endTag(null , "appId");

                    xs.startTag(null , "appIcon");
                    xs.text(app.getAppIcon());
                    xs.endTag(null , "appIcon");

                    xs.startTag(null , "appName");
                    xs.text(app.getAppName());
                    xs.endTag(null , "appName");

                    xs.startTag(null , "appPath");
                    xs.text(app.getAppPath());
                    xs.endTag(null , "appPath");

                    xs.endTag(null , "appInfo");
                }
                xs.endTag(null , "appInfos");
                xs.endDocument();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static ServiceAppinfo pullServiceAppinfo(){
        File file = new File(Constants.APP_INFO , "appInfo.xml");
        ServiceAppinfo appinfos = null;
        if (file.exists())
            try {
                FileInputStream fis = new FileInputStream(file);
                XmlPullParser xp = Xml.newPullParser();
                xp.setInput(fis , "utf-8");
                int type = xp.getEventType();
                ServiceAppinfo.AppInfoBean appInfo = null;
                while (type != XmlPullParser.END_DOCUMENT){
                    switch (type){
                        case XmlPullParser.START_TAG:
                            if ("appInfos".equals(xp.getName())){
                                infoBeanList = new ArrayList<>();
                                appinfos = new ServiceAppinfo();
                            }else if ("appInfo".equals(xp.getName())){
                                appInfo = new ServiceAppinfo.AppInfoBean();
                            }
                            else if ("appId".equals(xp.getName())){
                                appInfo.setAppId(Integer.parseInt(xp.nextText()));
                            }
                            else if ("appIcon".equals(xp.getName())){
                                appInfo.setAppIcon(xp.nextText());
                            }
                            else if ("appPath".equals(xp.getName())){
                                appInfo.setAppPath(xp.nextText());
                            }
                            else if ("appName".equals(xp.getName())){
                                appInfo.setAppName(xp.nextText());
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if ("appInfo".equals(xp.getName())){
                                infoBeanList.add(appInfo);
                            }
                            else if ("appInfos".equals(xp.getName())){
                                appinfos.setAppInfo(infoBeanList);
                            }
                            break;
                    }
                    type = xp.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return appinfos;
    }
}
