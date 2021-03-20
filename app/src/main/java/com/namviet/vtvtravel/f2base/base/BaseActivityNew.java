package com.namviet.vtvtravel.f2base.base;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.Intent;
import android.os.Bundle;
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
            getLocation();
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

    public void getLocation(){

    }


    public void getMainLocation(){
        Intent intent = new Intent(this, GPSTracker.class);
        startService(intent);
    }


}
