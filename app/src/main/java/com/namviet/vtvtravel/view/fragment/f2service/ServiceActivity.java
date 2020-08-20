package com.namviet.vtvtravel.view.fragment.f2service;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import androidx.annotation.Nullable;

import com.baseapp.activity.BaseActivity;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2ActivityServiceBinding;
import com.namviet.vtvtravel.view.MainActivity;

public class ServiceActivity extends MainActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new ServiceFragment()).commit();
    }

    public static void startScreen(Context activity) {
        Intent intent = new Intent(activity, ServiceActivity.class);
        activity.startActivity(intent);
    }
}
