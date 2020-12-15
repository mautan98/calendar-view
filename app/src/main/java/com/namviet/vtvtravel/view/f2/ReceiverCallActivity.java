package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.giorgosneokleous.fullscreenintentexample.ActivityUtilsKt;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2ActivityReceiverCallBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.service.InCallForegroundService;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.fragment.f2call.ReceiverCallGuestFragment;

public class ReceiverCallActivity extends BaseActivityNew<F2ActivityReceiverCallBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_receiver_call;
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
        try {
            ActivityUtilsKt.turnScreenOnAndKeyguardOff(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        clearCallNotification();

        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Activity.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
        lock.disableKeyguard();
    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        return new ReceiverCallGuestFragment();
    }

    @Override
    public void afterSetContentView() {
        super.afterSetContentView();
    }

    private Window wind;
    @Override
    protected void onResume() {
        super.onResume();
        wind = this.getWindow();
        wind.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        wind.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        wind.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    private void clearCallNotification() {
        try {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(1000);


            if(F2Util.isAppRunning(this)){
                notificationManager.cancel(1001);

                stopInCallService();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void stopInCallService() {
        Intent serviceIntent = new Intent(this, InCallForegroundService.class);
        stopService(serviceIntent);
    }
}
