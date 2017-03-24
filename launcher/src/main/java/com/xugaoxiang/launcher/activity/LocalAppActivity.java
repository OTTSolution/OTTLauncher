package com.xugaoxiang.launcher.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longjingtech.ott.launcher.R;
import com.open.androidtvwidget.bridge.RecyclerViewBridge;
import com.open.androidtvwidget.recycle.RecyclerViewTV;
import com.open.androidtvwidget.view.MainUpView;
import com.xugaoxiang.launcher.bean.AppInfo;
import com.xugaoxiang.launcher.utils.AppInfoProvide;
import com.xugaoxiang.launcher.utils.UIUtils;
import com.xugaoxiang.launcher.view.RecyclerViewItemSpace;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by user on 2016/8/25.
 */
public class LocalAppActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerViewTV recyclerView;
    @Bind(R.id.mainUpView1)
    MainUpView mainUpView;
    @Bind(R.id.rl_main)
    LinearLayout rlMain;
    private View oldView;
    private ArrayList<AppInfo> installApps;
    private RecyclerViewBridge mRecyclerViewBridge;

    @Override
    public void initView() {
        setContentView(R.layout.activity_local_app);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        initViewMove();
        recyclerViewGridLayout(GridLayoutManager.HORIZONTAL);
        setAppInfoData();
    }

    @Override
    public void initThem() {
        UIUtils.setBackground(this ,rlMain, 10002);
    }

    private void recyclerViewGridLayout(int orientation) {
        GridLayoutManager gridlayoutManager = new GridLayoutManager(this, 1);
        gridlayoutManager.setOrientation(orientation);
        recyclerView.setLayoutManager(gridlayoutManager);
        recyclerView.setSelectedItemAtCentered(false);
        recyclerView.setFocusable(false);
        recyclerView.addItemDecoration(new RecyclerViewItemSpace(0, 0, getResources().getDimensionPixelSize(R.dimen.w_70), 0));
    }

    private void setAppInfoData() {
        installApps = AppInfoProvide.getInstallApps();
        recyclerView.setAdapter(new LocalAppAdapter(installApps));
    }

    @Override
    public void initListener() {
        recyclerView.setOnItemListener(new RecyclerViewTV.OnItemListener() {
            @Override
            public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {
                mRecyclerViewBridge.setUnFocusView(oldView);
            }

            @Override
            public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {
                mRecyclerViewBridge.setFocusView(itemView, 1.0f, 1.0f);
                oldView = itemView;
            }

            @Override
            public void onItemClick(RecyclerViewTV parent, View itemView, int position) {
                launchApp(installApps.get(position).packageName);
//                uninstall(installApps.get(position).packageName);

            }

            @Override
            public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {
                mRecyclerViewBridge.setFocusView(itemView, 1.0f, 1.0f);
                oldView = itemView;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                startActivity(new Intent(this, AppLauncherActivity.class));
                overridePendingTransition(R.anim.anim_previous_in, R.anim.anim_previous_out);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void launchApp(String packageName) {
        PackageManager pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            startActivity(intent);
        } else {
            UIUtils.Toast("找不到启动界面", false);
        }
    }

    public void uninstall(String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        startActivity(intent);
    }

    public void initViewMove() {
        int left = UIUtils.getDimension(R.dimen.w_10);
        int right = UIUtils.getDimension(R.dimen.w_10);
        int top = UIUtils.getDimension(R.dimen.w_10);
        int bottom = -UIUtils.getDimension(R.dimen.h_30);
        Rect rect = new Rect(left, top, right, bottom);
        mainUpView.setEffectBridge(new RecyclerViewBridge());
        mainUpView.setDrawUpRectPadding(rect);
        mRecyclerViewBridge = (RecyclerViewBridge) mainUpView.getEffectBridge();
        mRecyclerViewBridge.setUpRectResource(R.drawable.white_light_10);

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_previous_in, R.anim.anim_previous_out);
    }

    class LocalAppAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<AppInfo> list;

        public LocalAppAdapter(List<AppInfo> list) {
            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(LocalAppActivity.this, R.layout.item_gridview, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            AppInfo appInfo = list.get(position);
            holder.iv_app_icon.setBackground(appInfo.appIcon);
            holder.tv_app_name.setText(appInfo.appName);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_app_icon;
        private final TextView tv_app_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_app_icon = (ImageView) itemView.findViewById(R.id.iv_app_icon);
            tv_app_name = (TextView) itemView.findViewById(R.id.tv_app_name);
        }
    }
}
