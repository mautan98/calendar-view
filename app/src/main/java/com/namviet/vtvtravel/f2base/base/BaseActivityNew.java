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

import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnHaveInviteTrip;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.SearchResponse;
import com.namviet.vtvtravel.response.f2callnow.ZipVersionResponse;
import com.namviet.vtvtravel.response.f2systeminbox.ConfirmEnterTrip;
import com.namviet.vtvtravel.service.GPSTracker;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.ultils.ServiceUltils;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.dialog.f2.ReceiverTripInviteDialog;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f3.notification.model.Notification;
import com.namviet.vtvtravel.view.fragment.f2mytrip.DetailTripActivity;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;
import com.namviet.vtvtravel.viewmodel.SearchViewModel;
import com.namviet.vtvtravel.viewmodel.f2systeminbox.SystemInboxViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Observable;
import java.util.Observer;


public abstract class BaseActivityNew<T extends ViewDataBinding> extends AppCompatActivity implements Observer {

    public abstract int getLayoutRes();
    public abstract int getFrame();
    public abstract void getDataFromIntent();
    public abstract void doAfterOnCreate();
    public abstract void setClick();
    public abstract BaseFragment initFragment();
    private T binding;
    private boolean isActivityRunning = false;
    private SystemInboxViewModel systemInboxViewModel;
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
            EventBus.getDefault().register(this);
            systemInboxViewModel = new SystemInboxViewModel();
            systemInboxViewModel.addObserver(this);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void showPopupInviteTrip(Notification notification) {
        if (isActivityRunning) {
            ReceiverTripInviteDialog receiverTripInviteDialog = ReceiverTripInviteDialog.newInstance(notification.getTitle(), notification.getMessage(), "Đồng ý tham gia", new ReceiverTripInviteDialog.ClickButton() {
                @Override
                public void onClickButton() {
                    try {
                        Account account = MyApplication.getInstance().getAccount();
                        if (null != account && account.isLogin()) {
                            systemInboxViewModel.confirmEnterTrip(notification.getData().getScheduleCustomId(), String.valueOf(account.getId()));
                        } else {
                            LoginAndRegisterActivityNew.startScreen(BaseActivityNew.this, 0, false);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            receiverTripInviteDialog.show(getSupportFragmentManager(), null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityRunning = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityRunning = false;
    }

    @Subscribe
    public void onHaveInviteTripNoti(OnHaveInviteTrip onHaveInviteTrip) {
        showPopupInviteTrip(onHaveInviteTrip.notification);
    }

    @Override
    public void update(Observable observable, final Object o) {

        if (observable instanceof SystemInboxViewModel && null != o) {
            if (o instanceof ConfirmEnterTrip) {
                try {
                    DetailTripActivity.Companion.startScreen(this, ((ConfirmEnterTrip) o).getTripID());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
                } catch (Exception e) {
                }
            }
        }
    }
}
