package com.xugaoxiang.launcher.activity;

import android.app.Instrumentation;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longjingtech.ott.launcher.R;
import com.open.androidtvwidget.bridge.EffectNoDrawBridge;
import com.open.androidtvwidget.view.MainUpView;
import com.xugaoxiang.launcher.bean.ServiceAppinfo;
import com.xugaoxiang.launcher.bean.Weather;
import com.xugaoxiang.launcher.presenter.MainPresenter;
import com.xugaoxiang.launcher.utils.Constants;
import com.xugaoxiang.launcher.utils.FileUtils;
import com.xugaoxiang.launcher.utils.UIUtils;
import com.xugaoxiang.launcher.view.AppInfoItemView;
import com.xugaoxiang.launcher.view.MainView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by user on 2016/8/29.
 */
public class AppLauncherActivity extends BaseActivity implements  MainView{

    @Bind(R.id.mainUpView1)
    MainUpView mainUpView;
    View mOldFocus;
    @Bind(R.id.ll_app_list)
    LinearLayout llAppList;
    @Bind(R.id.ll_top)
    RelativeLayout llTop;
    private LinearLayout.LayoutParams params;
    public static ServiceAppinfo appinfo;
    private static boolean isRun = true;

    //天气
    private ImageView imageViewWeather;//天气图标
    private TextView textViewTemperature;//天气气温
//1    private LocationClient client;  //定位实例
//1    private BDLocationListener listener;    //定位的回调监听
    private TextView textViewdistrict;
    private static ImageView imageViewnetwire;

    private String[] appLable = {"直播" , "点播"  , "应用商店" , "设置"};
    private String[] appPackage = {"com.longjingtech.ott.live" , "com.longjingtech.ott.play" , "com.longjingtech.ott.appstore" , "com.longjingtech.ott.setting"};
    private int[] appIcon = {R.drawable.live, R.drawable.play , R.drawable.app , R.drawable.setting};
    //时间
    private TextView textView;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd  EEE  hh:mm:ss");    // 对日期进行格式化EEE是星期
                    String time = sdf.format(date);// Date-->String
                    textView.setText(time); //更新时间
                    break;
            }
        }
    };
    private int first = 0;
    private NetworkReceiver receiver;
    private Gson gson;
    private MainPresenter presenter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
    }


    public void sendKeyEvent(final int KeyCode) {
        new Thread() {     //不可在主线程中调用
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }
    @Override
    public void initData() {
        presenter = new MainPresenter(this);
        gson = new Gson();
        isRun = true;
        initViewMove();
        initwWidget();
        new TimeThread().start(); //启动新的线程
        isNetworkAvailable(AppLauncherActivity.this);
        registerNetReceiver();
    }

    private void registerNetReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        registerReceiver(receiver, filter);
    }

    private void initAppInfo() {
        appinfo = gson.fromJson(FileUtils.getFileBeanInfo(Constants.APP_INFO , "appinfo") , ServiceAppinfo.class);
        params = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.w_350) , getResources().getDimensionPixelSize(R.dimen.h_480));
        params.setMargins(getResources().getDimensionPixelSize(R.dimen.w_28) , 0 , 0 ,0);
        if (appinfo != null) {
            for (int i = 0; i < appinfo.getAppInfo().size() ; i++){
                setAppList(appinfo.getAppInfo().get(i) , i);
            }
        }else {
            for (int i = 0; i < 4; i++) {
                setAppList(null , i);
            }
        }
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                llAppList.getChildAt(0).requestFocusFromTouch();
                llAppList.getChildAt(0).requestFocus();
            }
        };
        handler.sendEmptyMessageDelayed(0 , 500);
    }

    @Override
    public void initThem() {
    }

    private void setAppList(ServiceAppinfo.AppInfoBean appInfo, int i) {
        AppInfoItemView view = new AppInfoItemView(this);
        view.setOnFocusChangeListener(this);
        view.setOnClickListener(this);
        view.setId(i);
        if (appInfo != null){
            view.setAppLable(appInfo.getAppName());
            view.setAppIcon(appInfo.getAppIcon());
        }else {
            view.setAppLable(appLable[i]);
            view.setDefalte(appIcon[i]);
        }
        llAppList.addView(view , params);
    }

    @Override
    public void initListener() {

    }


    @Override
    public void processFocus(final View view, boolean hasFocus) {
        if (hasFocus) {
//            if (first == 1){
//                Handler handler = new Handler(){
//                    @Override
//                    public void handleMessage(Message msg) {
//                        mainUpView.setFocusView(view, mOldFocus, 1.2f);
//                        mOldFocus = view;
//                    }
//                };
//                handler.sendEmptyMessageDelayed(0 , 1000);
//                first = 0;
//            }
//            else {
                mainUpView.setFocusView(view, mOldFocus, 1.2f);
                mOldFocus = view;
//            }
        }
    }

    @Override
    public void processClick(View view) {
        if (appinfo != null){
            for (int i = 0; i < appinfo.getAppInfo().size(); i++) {
                if (view.getId() == i && !TextUtils.isEmpty(appinfo.getAppInfo().get(i).getAppPath())){
                    launchApp(appinfo.getAppInfo().get(i).getAppPath());
                }
            }
        }else {
            for (int i = 0; i < appPackage.length; i++) {
                if (view.getId() == i){
                    launchApp(appPackage[i]);
                }
            }
        }
    }

    private void startApp(int i) {
//        if (i >= appinfo.getAppInfo().size() - 1){
//            launchApp(appinfo.getAppInfo().get(i).getAppPath());
//            return;
//        }
        Intent intent = new Intent(appinfo.getAppInfo().get(i).getAppPath());
        startActivity(intent);
    }

    private void launchApp(String packageName) {
        PackageManager pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            startActivity(intent);
        } else {
            UIUtils.Toast("找不到启动界面", false);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                startActivity(new Intent(this, LocalAppActivity.class));
                overridePendingTransition(R.anim.anim_in , R.anim.anim_out);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void initViewMove() {
        mainUpView.setEffectBridge(new EffectNoDrawBridge()); // 4.3以下版本边框移动.
        mainUpView.setUpRectResource(R.drawable.white_light_10); // 设置移动边框的图片.
//        EffectNoDrawBridge bridget = (EffectNoDrawBridge) mainUpView.getEffectBridge();
        mainUpView.setDrawUpRectPadding(10);
//        bridget.setTranDurAnimTime(200);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        isRun = false;
        first = 0;
    }

    //初始化控件
    private void initwWidget() {
        textView = (TextView) findViewById(R.id.time_tv);
        imageViewWeather = (ImageView) findViewById(R.id.weather_iv);
        textViewTemperature = (TextView) findViewById(R.id.weather_tv);
        textViewdistrict= (TextView) findViewById(R.id.district_tv);
        imageViewnetwire= (ImageView) findViewById(R.id.networkwire);
    }


    @Override
    public void setAppinfo() {
        llAppList.removeAllViews();
        initAppInfo();
    }

    @Override
    public void setBackground() {
        UIUtils.setBackground(this , llTop , 10001);
    }

    @Override
    public void setWeather(Weather weather) {
        textViewTemperature.setText(weather.getData().getTemp()+"°C");
        switch (weather.getData().getType()){
            case "多云" :
                imageViewWeather.setImageResource(R.drawable.duoyun);
                break;
            case "少云" :
                imageViewWeather.setImageResource(R.drawable.duoyun);
                break;
            case "晴" :
                imageViewWeather.setImageResource(R.drawable.qing);
                break;
            case "阴" :
                imageViewWeather.setImageResource(R.drawable.yin);
                break;
            case "小雨" :
                imageViewWeather.setImageResource(R.drawable.yu);
                break;
            case "雨" :
                imageViewWeather.setImageResource(R.drawable.yu);
                break;
            case "雷阵雨" :
                imageViewWeather.setImageResource(R.drawable.leizhenyu);
                break;
            case "中雨" :
                imageViewWeather.setImageResource(R.drawable.yu);
                break;
            case "阵雨" :
                imageViewWeather.setImageResource(R.drawable.leizhenyu);
                break;
            case "零散阵雨" :
                imageViewWeather.setImageResource(R.drawable.leizhenyu);
                break;
            case "零散雷雨" :
                imageViewWeather.setImageResource(R.drawable.leizhenyu);
                break;
            case "小雪" :
                imageViewWeather.setImageResource(R.drawable.xue);
                break;
            case "雨夹雪" :
                imageViewWeather.setImageResource(R.drawable.yujiaxue);
                break;
            case "阵雪" :
                imageViewWeather.setImageResource(R.drawable.xue);
                break;
            case "霾" :
                imageViewWeather.setImageResource(R.drawable.mai);
                break;
        }
    }

    class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;  //消息
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

    //定位启动
//    private void startLocationService() {
//
//        // 定位客户端实例的构建（LocationClient）
//        LocationClientOption option = new LocationClientOption();
//        // 设置定位的属性值
//        option.setOpenGps(true);    //开启GPS
//        option.setCoorType("bd09ll");//百度经纬度坐标系
//        option.setIsNeedAddress(true);// 需要地址信息，默认是false
////        option.setCoorType("gcj02");// 国测局经纬度坐标系
////        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);        // 设置定位模式
//        option.setScanSpan(6 * 1000);//设置扫描间隔(设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效)确保地图是最新的状态，不是历史数据
//        client.setLocOption(option);
//        client.start();// 启动定位服务
//    }

    //定位的回调监听
//    private class MyBDLocationListener implements BDLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            String district = location.getDistrict();   //若定位成功,得到当前地区并通过当前地区请求数据,更新到UI中
//            if (district != null && district.length() > 0) {
//                Log.e("1111", "===定位成功====");
//                final String district2 = district.substring(0, district.length() - 1);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        textViewdistrict.setText(district2);
//                    }
//                });
//
//                api.queryByCityName(district2, AppLauncherActivity.this);
//                client.stop();  //得到位置信息后停止定位,节省流量
//            } else {
//
//            }
//        }
//    }

    //判断网络
    public static void isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo isNetWorkInfo = manager.getActiveNetworkInfo();
        boolean isNetWork = false;
        if (isNetWorkInfo != null){
            isNetWork = isNetWorkInfo.isAvailable();
        }
        if(isNetWork){
            NetworkInfo.State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            NetworkInfo.State ethernet = manager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET).getState();
            if(wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING){
                imageViewnetwire.setImageResource(R.drawable.mwifi);
            }
            if(ethernet == NetworkInfo.State.CONNECTED){
                imageViewnetwire.setImageResource(R.drawable.youxian);
            }
        }else {
            imageViewnetwire.setImageResource(R.drawable.no_network);
        }
    }
    class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            isNetworkAvailable(context);
        }
    }

}
