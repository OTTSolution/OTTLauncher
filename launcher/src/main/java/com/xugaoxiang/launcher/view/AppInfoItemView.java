package com.xugaoxiang.launcher.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longjingtech.ott.launcher.R;

/**
 * Created by user on 2016/9/2.
 */
public class AppInfoItemView extends RelativeLayout{

    private String appLable;
    private Drawable appIcon;
    private ImageView iv_app_icon;
    private TextView tv_app_lable;
    private Context context;

    public AppInfoItemView(Context context) {
        this(context , null);
    }

    public AppInfoItemView(Context context, AttributeSet attrs) {
        this(context , attrs , 0);
    }

    public AppInfoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setFocusable(true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AppInfoItemView);
        appLable = typedArray.getString(R.styleable.AppInfoItemView_appLable);
        appIcon = typedArray.getDrawable(R.styleable.AppInfoItemView_appIcon);
        typedArray.recycle();
        initView();
    }

    private void initView() {
        View child = View.inflate(getContext() , R.layout.appinfo_item_view , null);
        iv_app_icon = (ImageView) child.findViewById(R.id.iv_app_icon);
        tv_app_lable = (TextView) child.findViewById(R.id.tv_app_lable);
//        setAppLable(appLable);
//        setAppIcon(appIcon);
        this.addView(child);
    }

    public void setAppLable(String lable){
        tv_app_lable.setText(lable);
    }

    public void setAppIcon(String url){
        iv_app_icon.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context)
                .load(url)
                .into(iv_app_icon);
    }

    public void setDefalte(int id){
        iv_app_icon.setBackgroundResource(id);
    }
}
