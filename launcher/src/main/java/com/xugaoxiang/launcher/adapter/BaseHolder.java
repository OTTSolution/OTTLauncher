package com.xugaoxiang.launcher.adapter;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by user on 2016/9/5.
 */
public abstract class BaseHolder<T> {

    private View rootView;
    private T mData;

    public BaseHolder() {
        this.rootView = initView();
        this.rootView.setTag(this);
        ButterKnife.bind(BaseHolder.this , rootView);
    }

    public T getData(){
        return mData;
    }

    public View getRootView(){
        return rootView;
    }

    public void setData(T data){
        this.mData = data;
        refreshView();
    }

    public abstract View initView();

    public abstract void refreshView();
}
