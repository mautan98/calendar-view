package com.namviet.vtvtravel.view.f2.f2oldbase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.fragment.account.SettingFragment;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceFragment;

public class SettingActivity extends MainActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new SettingFragment()).commit();
    }

    public static void startScreen(Context activity) {
        if(F2Util.isOnline((Activity) activity)){
            Intent intent = new Intent(activity, SettingActivity.class);
            activity.startActivity(intent);
        }else {
            Toast.makeText(activity, "Mời bạn bật kết nối internet để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
        }
    }
}
