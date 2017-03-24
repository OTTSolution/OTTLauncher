package com.xugaoxiang.launcher.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by user on 2016/9/5.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter{

    private ArrayList<T> list;

    public MyBaseAdapter(ArrayList<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder = null;
        if (convertView == null){
            holder = getHolder();
        }else {
            holder = (BaseHolder) convertView.getTag();
        }
        holder.setData(list.get(position));
        return holder.getRootView();
    }

    public abstract BaseHolder getHolder();
}
