package com.xugaoxiang.launcher.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TimePicker;

/**
 * Created by user on 2016/9/8.
 */
public class MyTimePicker extends TimePicker{
    public MyTimePicker(Context context) {
        this(context , null);
    }

    public MyTimePicker(Context context, AttributeSet attrs) {
        this(context , attrs , 0);
    }

    public MyTimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusable(true);
    }
}
