package com.xugaoxiang.launcher.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.xugaoxiang.launcher.utils.TwitterRestClient;
import com.loopj.android.http.BinaryHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by user on 2016/8/31.
 */
public class MyBitmapUtils {

    private static Bitmap bitmap = null;

    public static Bitmap getBitmap(String url , String path){
        if (LocalCatchImg.getBitmapFromLocal(url , path) != null){
            bitmap = LocalCatchImg.getBitmapFromLocal(url , path);
            return bitmap;
        }
        downloadImg(url , path);
        return null;
    }

    public static void downloadImg(final String url , final String path){
        TwitterRestClient.downLoadImg(url, new BinaryHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                LocalCatchImg.setBitmapToLocal(bitmap, url , path);
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {}
        });
    }
}
