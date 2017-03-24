package com.xugaoxiang.launcher.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.xugaoxiang.launcher.MyApplication;
import com.xugaoxiang.launcher.bean.Them;
import com.xugaoxiang.launcher.bean.Weather;
import com.xugaoxiang.launcher.utils.AppNetConfig;
import com.xugaoxiang.launcher.utils.Constants;
import com.xugaoxiang.launcher.utils.FileUtils;
import com.xugaoxiang.launcher.utils.PrefUtils;
import com.xugaoxiang.launcher.utils.TwitterRestClient;
import com.xugaoxiang.launcher.utils.UIUtils;
import com.xugaoxiang.launcher.view.MainView;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;
import java.io.IOException;

/**
 * Created by user on 2016/12/1.
 */
public class MainPresenter {

    private MainView mainView;
    private final Gson gson;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
        gson = new Gson();
        registerNetReceiver();
    }
    class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo isNetWorkInfo = manager.getActiveNetworkInfo();
            boolean isNetWork = false;
            if (isNetWorkInfo != null){
                isNetWork = isNetWorkInfo.isAvailable();
            }
            if (isNetWork){
                getServiceAppInfo();
                getFromWeather();
                if (Constants.them == null){
                    getServiceThemInfo();
                }
            }
        }
    }
    private void registerNetReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        NetworkReceiver receiver = new NetworkReceiver();
        UIUtils.getContext().registerReceiver(receiver, filter);
    }

    private void getServiceAppInfo() {
        TwitterRestClient.get(AppNetConfig.BASE_URL + AppNetConfig.APPINFO, new RequestParams(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mainView.setAppinfo();
            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    FileUtils.writeBeanFile(s , Constants.APP_INFO , "appinfo");
                    mainView.setAppinfo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
                        FileUtils.delete(Constants.THEM_INFO);
                        try {
                            FileUtils.writeBeanFile(s , Constants.THEM_INFO , "theminfo");
                            mainView.setBackground();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void getFromWeather(){
        TwitterRestClient.get(AppNetConfig.BASE_URL + AppNetConfig.WEATHER, new RequestParams(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Weather weather = gson.fromJson(s, Weather.class);
                mainView.setWeather(weather);
            }
        });
    }
}
