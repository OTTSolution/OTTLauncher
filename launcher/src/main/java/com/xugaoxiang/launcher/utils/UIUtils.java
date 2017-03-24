package com.xugaoxiang.launcher.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.longjingtech.ott.launcher.R;
import com.xugaoxiang.launcher.MyApplication;
import com.xugaoxiang.launcher.bean.Them;
import com.xugaoxiang.launcher.utils.bitmap.MyBitmapUtils;


public class UIUtils {

    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);
    }

    public static View getXmlView(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    public static String[] getStringArr(int arrId) {
        return getContext().getResources().getStringArray(arrId);
    }

    /**
     * 1dp---1px;
     * 1dp---0.75px;
     * 1dp---0.5px;
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    ;

    public static int px2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    public static Context getContext() {
        return MyApplication.context;
    }

    public static Handler getHandler() {
        return MyApplication.handler;
    }

    /**
     * 保证runnable对象的run方法是运行在主线程当中
     *
     * @param runnable
     */
    public static void runOnUIThread(Runnable runnable) {
        if (isInMainThread()) {
            runnable.run();
        } else {
            getHandler().post(runnable);
        }
    }

    private static boolean isInMainThread() {
        //当前线程的id
        int tid = android.os.Process.myTid();
        if (tid == MyApplication.mainThreadId) {
            return true;
        }
        return false;
    }


    public static void Toast(String text, boolean isLong) {
        Toast.makeText(getContext(), text, isLong == true ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    public static int getDimension(int id){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (getContext().getResources().getDimension(id) * density);
    }

    public static void setViewBackground(View view , int themId){
        if (Constants.xmlThem == null){
            Constants.xmlThem = XmlUtils.pullThemXml();
        }
        if (Constants.xmlThem != null){
            for (Them.ThemBean.ThemInfoBean infoBean: Constants.xmlThem.themInfoBeen) {
                if (infoBean.getThemId() == themId){
                    Bitmap bitmap = MyBitmapUtils.getBitmap(infoBean.getThemUrl() , Constants.THEM_IMAGE);
                    if (bitmap != null){
                        view.setBackground(new BitmapDrawable(bitmap));
                    }
                    break;
                }
            }
        }
    }

    public static void setBackground(Context context ,final View view , int themId){
        if (Constants.them == null){
            Gson gson = new Gson();
            Constants.them = gson.fromJson(FileUtils.getFileBeanInfo(Constants.THEM_INFO , "theminfo") , Them.class);
        }
        if (Constants.them != null){
            for (int i = 0; i < Constants.them.getThem().getThemInfo().size(); i++) {
                if (Constants.them.getThem().getThemInfo().get(i).getThemId() == themId){
                    Glide.with(context)
                            .load(Constants.them.getThem().getThemInfo().get(i).getThemUrl())
                            .asBitmap()
                            .error(R.drawable.bg)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    view.setBackground(new BitmapDrawable(resource));
                                }
                            });
                }
            }
        }
    }
}
