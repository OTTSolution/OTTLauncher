package com.xugaoxiang.launcher.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by user on 2016/8/25.
 */
public abstract class BaseActivity extends Activity implements View.OnFocusChangeListener , View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
        initThem();
    }
    public abstract void initView();

    public void initData(){}

    public void initListener(){}

    public void initThem(){}

    public void processFocus(View view , boolean hasFocus){}

    public void processClick(View view){}

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        processFocus(v , hasFocus);
    }

    @Override
    public void onClick(View v) {
        processClick(v);
    }
}
