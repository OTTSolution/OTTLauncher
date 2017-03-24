package com.xugaoxiang.launcher.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by user on 2016/9/13.
 */
public class RecyclerViewItemSpace extends RecyclerView.ItemDecoration{

    int spLeft , spRight , spBottom , spTop;

    public RecyclerViewItemSpace(int spLeft , int spTop , int spRight , int spBottom) {
        this.spLeft = spLeft;
        this.spTop = spTop;
        this.spRight = spRight;
        this.spBottom = spBottom;
    }

    public RecyclerViewItemSpace(int space) {
        this.spLeft = space;
        this.spRight = space;
        this.spBottom = space;
        this.spTop = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = spLeft;
        outRect.right = spRight;
        outRect.bottom = spBottom;
        if (parent.getChildPosition(view) == 0){
            outRect.top = spTop;
        }
    }
}
