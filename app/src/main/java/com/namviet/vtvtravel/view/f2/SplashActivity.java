package com.namviet.vtvtravel.view.f2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivitySplashBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.service.LinphoneService;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.view.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SplashActivity extends BaseActivityNew<F2ActivitySplashBinding> {
    private Handler mHandler;
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
        mHandler = new Handler();

//        Intent intent = new Intent("vn.vtv.vtvtravel");
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        intent.addCategory(Intent.CATEGORY_BROWSABLE);
//        Bundle bundle = new Bundle();
//        bundle.putString("cateId", "hihii");
//        bundle.putString("link", "hihii");
//        intent.putExtras(bundle);
//
//        Log.e("mobikul-->", intent.toUri(Intent.URI_INTENT_SCHEME));

//        checkDateForInvitedUser();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (PreferenceUtil.getInstance(SplashActivity.this).getValue("firstTime", false)) {
//                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                    finish();
//                } else {
//                    PreferenceUtil.getInstance(SplashActivity.this).setValue("firstTime", true);
//                    startActivity(new Intent(SplashActivity.this, IntroductionActivity.class));
//                    finish();
//                }
//            }
//        }, 500);
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

    @Override
    protected void onStart() {
        super.onStart();

        // Check whether the Service is already running
        try {
            if (LinphoneService.isReady()) {
                onServiceReady();
            } else {
                // If it's not, let's start it
                startService(
                        new Intent().setClass(this, LinphoneService.class));
                // And wait for it to be ready, so we can safely use it afterwards
                new ServiceWaitThread().start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onServiceReady() {
        // Once the service is ready, we can move on in the application
        // We'll forward the intent action, type and extras so it can be handled
        // by the next activity if needed, it's not the launcher job to do that
//        Intent intent = new Intent();
//        intent.setClass(SplashActivity.this, MainActivity.class);
//        if (getIntent() != null && getIntent().getExtras() != null) {
//            intent.putExtras(getIntent().getExtras());
//        }
//        intent.setAction(getIntent().getAction());
//        intent.setType(getIntent().getType());
//        startActivity(intent);


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

    // This thread will periodically check if the Service is ready, and then call onServiceReady
    private class ServiceWaitThread extends Thread {
        public void run() {
            while (!LinphoneService.isReady()) {
                try {
                    sleep(30);
                } catch (InterruptedException e) {
                    throw new RuntimeException("waiting thread sleep() has been interrupted");
                }
            }
            // As we're in a thread, we can't do UI stuff in it, must post a runnable in UI thread
            mHandler.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            onServiceReady();
                        }
                    });
        }
    }
}