package com.baseapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.baseapp.menu.SlideMenu;
import com.baseapp.utils.L;
import com.baseapp.utils.NetworkUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static com.baseapp.utils.ConfigUltils.Network.ACTION_CHANGE_NETWORK;
import static com.baseapp.utils.ConfigUltils.Network.ACTION_NETWORK_DISCONNECT;
import static com.baseapp.utils.ConfigUltils.Network.CHANGE_NETWORK;

/**
 * Created by xuannt on 8/8/2017.
 */

public class BaseActivity extends AppCompatActivity {
    protected Map<SlideMenu.MenuType, Stack<Fragment>> mStackMap;
    protected SlideMenu.MenuType mCurrentMenuTab = SlideMenu.MenuType.SPLASH_SCREEN;
    private IntentFilter filterState = new IntentFilter();
    private BroadcastReceiver stateReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
//      setStatusBarTranslucent(true);
        initStackFragments();
        addActionFilter();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != stateReceiver) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(stateReceiver);
        }
    }

    protected int getLayoutId() {
        return 0;
    }


    protected int getContentFrame() {
        return 0;
    }


    protected void initView() {
    }

    protected void initStackFragments() {
        mStackMap = new HashMap<>();
        for (SlideMenu.MenuType menuType : SlideMenu.MenuType.values()) {
            mStackMap.put(menuType, new Stack<Fragment>());
        }
    }

    public void switchFragment(SlideMenu.MenuType screen) {
//        setCurrentMenuOrTab(screen);
    }

    protected void updateView() {
        if (NetworkUtil.isNetworkAvailable(this)) {
            updateViewNetworkChange(true);
        } else {
            updateViewNetworkChange(false);
        }
    }

    protected void setCurrentMenuOrTab(SlideMenu.MenuType menuType) {
        mCurrentMenuTab = menuType;
    }

    public SlideMenu.MenuType getmCurrentMenuTab() {
        return mCurrentMenuTab;
    }

    public Map<SlideMenu.MenuType, Stack<Fragment>> getStackMap() {
        return mStackMap;
    }

    private void addActionFilter() {
        filterState.addAction(ACTION_CHANGE_NETWORK);
        filterState.addAction(ACTION_NETWORK_DISCONNECT);
        if (stateReceiver == null) {
            stateReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    switch (intent.getAction()) {
                        case ACTION_CHANGE_NETWORK:
                            L.e("ACTION_CHANGE_NETWORK " + intent.getBooleanExtra(CHANGE_NETWORK, false));
                            updateViewNetworkChange(intent.getBooleanExtra(CHANGE_NETWORK, false));
                            break;
                        case ACTION_NETWORK_DISCONNECT:
                            L.e("ACTION_CHANGE_NETWORK " + intent.getBooleanExtra(CHANGE_NETWORK, false));
                            updateViewNetworkChange(intent.getBooleanExtra(CHANGE_NETWORK, false));
                            break;
                    }
                }
            };
            LocalBroadcastManager.getInstance(this).registerReceiver(stateReceiver, filterState);
        }
    }

    protected void updateViewNetworkChange(boolean isConnect) {
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
