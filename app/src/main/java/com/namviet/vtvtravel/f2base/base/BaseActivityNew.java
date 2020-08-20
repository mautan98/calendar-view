package com.namviet.vtvtravel.f2base.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import androidx.annotation.Nullable;


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

}
