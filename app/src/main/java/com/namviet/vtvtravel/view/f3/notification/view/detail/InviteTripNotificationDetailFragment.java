package com.namviet.vtvtravel.view.f3.notification.view.detail;

import android.view.View;
import android.widget.Toast;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.ActivityInviteTripNotificationBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.f2systeminbox.ConfirmEnterTrip;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f3.notification.model.Notification;
import com.namviet.vtvtravel.viewmodel.f2systeminbox.SystemInboxViewModel;

import java.util.Observable;
import java.util.Observer;

public class InviteTripNotificationDetailFragment extends BaseFragment<ActivityInviteTripNotificationBinding> implements Observer {
    private SystemInboxViewModel systemInboxViewModel;
    private Notification notification;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_invite_trip_notification;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        systemInboxViewModel = new SystemInboxViewModel();
        systemInboxViewModel.addObserver(this);
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        try {
                            Account account = MyApplication.getInstance().getAccount();
                            if (null != account && account.isLogin()) {
                                systemInboxViewModel.confirmEnterTrip(notification.getData().getScheduleCustomId(), String.valueOf(account.getId()));
                            } else {
                                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
            }
        });

        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        getBinding().tvTitle.setText(notification.getTitle());
        getBinding().tvMessage.setText(notification.getMessage());
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            if (observable instanceof SystemInboxViewModel && null != o) {
                if (o instanceof ConfirmEnterTrip) {

                }  else if (o instanceof ErrorResponse) {
                    ErrorResponse responseError = (ErrorResponse) o;
                    try {
                        Toast.makeText(mActivity, responseError.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
