package com.xugaoxiang.launcher.view;

import com.xugaoxiang.launcher.bean.Weather;

/**
 * Created by user on 2016/12/1.
 */
public interface MainView {
    void setAppinfo();

    void setBackground();

    void setWeather(Weather weather);
}
