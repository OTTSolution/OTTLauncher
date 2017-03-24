package com.xugaoxiang.launcher.adapter;

import com.open.androidtvwidget.adapter.BaseTabTitleAdapter;

import java.util.ArrayList;

/**
 * Created by user on 2016/8/25.
 */
public class TabTitleAdapter extends BaseTabTitleAdapter{

    private ArrayList<String> titleList;

    public TabTitleAdapter(ArrayList<String> titleList) {
        this.titleList = titleList;
    }

    @Override
    public int getCount() {
        return titleList.size();
    }
}
