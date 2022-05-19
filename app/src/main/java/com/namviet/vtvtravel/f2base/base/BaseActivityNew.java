package com.namviet.vtvtravel.f2base.base;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.Nullable;

import com.namviet.vtvtravel.service.GPSTracker;
import com.namviet.vtvtravel.ultils.ServiceUltils;


public abstract class BaseActivityNew<T extends ViewDataBinding> extends AppCompatActivity {

    public abstract int getLayoutRes();
    public abstract int getFrame();
    public abstract void getDataFromIntent();
    public abstract void doAfterOnCreate();
    public abstract void setClick();
    public abstract BaseFragment initFragment();
    private T binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            afterSetContentView();
            getLocationByService();
            binding = DataBindingUtil.setContentView(this, getLayoutRes());
            getDataFromIntent();
            doAfterOnCreate();
            setClick();
            getSupportFragmentManager().beginTransaction().replace(getFrame(), initFragment()).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public T getBinding(){
        return binding;
    }

    public void afterSetContentView(){

    }

    public void getLocationByService(){

    }


    public void getMainLocation(){
        try {
            Intent intent = new Intent(this, GPSTracker.class);
            startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setTransparentStatusBar(){
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }
    public void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }



}
