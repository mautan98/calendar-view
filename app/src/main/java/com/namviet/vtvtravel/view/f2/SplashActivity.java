package com.namviet.vtvtravel.view.f2;

import android.content.Intent;
import android.os.Handler;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivitySplashBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.view.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SplashActivity extends BaseActivityNew<F2ActivitySplashBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_splash;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void doAfterOnCreate() {
        checkDateForInvitedUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PreferenceUtil.getInstance(SplashActivity.this).getValue("firstTime", false)) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    PreferenceUtil.getInstance(SplashActivity.this).setValue("firstTime", true);
                    startActivity(new Intent(SplashActivity.this, IntroductionActivity.class));
                    finish();
                }
            }
        }, 500);
    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        return null;
    }

    private void checkDateForInvitedUser(){
        try {
            String dateCache = PreferenceUtil.getInstance(this).getValue(Constants.PrefKey.DATE, "");
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            if(dateCache.equals(currentDate)){

            }else {
                PreferenceUtil.getInstance(this).setValue(Constants.PrefKey.DATE, currentDate);
                MyApplication.getAppDatabase().foodDao().clearInvitedUser();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
