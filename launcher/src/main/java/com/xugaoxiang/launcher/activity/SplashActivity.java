package com.xugaoxiang.launcher.activity;

import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.longjingtech.ott.launcher.R;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.xugaoxiang.launcher.MyApplication;
import com.xugaoxiang.launcher.bean.ServiceAppinfo;
import com.xugaoxiang.launcher.bean.Them;
import com.xugaoxiang.launcher.utils.AppNetConfig;
import com.xugaoxiang.launcher.utils.Constants;
import com.xugaoxiang.launcher.utils.FileUtils;
import com.xugaoxiang.launcher.utils.PrefUtils;
import com.xugaoxiang.launcher.utils.StreamUtils;
import com.xugaoxiang.launcher.utils.TwitterRestClient;
import com.xugaoxiang.launcher.utils.XmlUtils;
import com.xugaoxiang.launcher.utils.bitmap.MyBitmapUtils;

import org.apache.http.Header;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by user on 2016/9/2.
 */
public class SplashActivity extends BaseActivity{

    private Gson gson;

    @Override
    public void initView() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void initData() {
        if (!TextUtils.isEmpty(getLocalFileURL())){
            AppNetConfig.BASE_URL = getLocalFileURL();
        }
        gson = new Gson();
        getServiceAppInfo();
        getServiceThemInfo();
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    startActivity(new Intent(SplashActivity.this , AppLauncherActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private String getLocalFileURL() {
        File file = new File(Constants.LOCAL_URL);
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

    private void getServiceAppInfo() {
        TwitterRestClient.get(AppNetConfig.BASE_URL + AppNetConfig.APPINFO, new RequestParams(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                ServiceAppinfo serviceAppinfo = gson.fromJson(s, ServiceAppinfo.class);
//                ServiceAppinfo appinfo = XmlUtils.pullServiceAppinfo();
//                if (appinfo == null || serviceAppinfo.getAppInfo().size() != appinfo.getAppInfo().size()){
//                    FileUtils.delete(Constants.APP_INFO_IMG);
                    XmlUtils.createAppinfoFile(serviceAppinfo);
//                }
            }
        });
    }

    private void downLoadAppIcon(List<ServiceAppinfo.AppInfoBean> appInfo) {
        for (ServiceAppinfo.AppInfoBean app:appInfo) {
            MyBitmapUtils.downloadImg(app.getAppIcon() , Constants.APP_INFO_IMG);
        }
    }

    private void getServiceThemInfo() {
        TwitterRestClient.get(AppNetConfig.BASE_URL + AppNetConfig.THEM, new RequestParams(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                String themsId = PrefUtils.getString(MyApplication.context , "themsId" , "xxx");
                Them them = gson.fromJson(s , Them.class);
                if (them != null){
                    File file = new File(Constants.THEM_INFO);
                    if (!file.exists() || !them.getThem().getThemsId().equals(themsId)){
                        PrefUtils.putString(MyApplication.context , "themsId" , them.getThem().getThemsId());
                        FileUtils.delete(Constants.THEM_IMAGE);
                        FileUtils.delete(Constants.THEM_INFO);
                        downLoadBackgroundImg(them);
                        setThemXmlFile(them);
                    }
                }
            }
        });
    }


    private void downLoadBackgroundImg(Them them) {
        for (Them.ThemBean.ThemInfoBean infoBean : them.getThem().getThemInfo()) {
            String url = infoBean.getThemUrl();
            MyBitmapUtils.downloadImg(url , Constants.THEM_IMAGE);
        }
    }

    private void setThemXmlFile(Them them) {
        XmlUtils.createThemXmlFile(them);
    }
}
