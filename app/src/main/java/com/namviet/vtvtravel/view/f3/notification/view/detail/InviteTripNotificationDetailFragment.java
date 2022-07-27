package com.namviet.vtvtravel.view.f3.notification.view.detail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.baseapp.menu.SlideMenu;
import com.github.nkzawa.socketio.client.On;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.ActivityInviteTripNotificationBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnReloadNoti;
import com.namviet.vtvtravel.response.f2systeminbox.ConfirmEnterTrip;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f3.notification.NotificationViewModel;
import com.namviet.vtvtravel.view.f3.notification.model.Notification;
import com.namviet.vtvtravel.view.f3.notification.model.ui.UpdateNotificationResponse;
import com.namviet.vtvtravel.view.fragment.f2mytrip.DetailTripActivity;
import com.namviet.vtvtravel.viewmodel.f2systeminbox.SystemInboxViewModel;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class InviteTripNotificationDetailFragment extends BaseFragment<ActivityInviteTripNotificationBinding> implements Observer {
    private SystemInboxViewModel systemInboxViewModel;
    private NotificationViewModel notificationViewModel;
    private Notification notification;
    private String isMarked = "0";
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
        notificationViewModel = new NotificationViewModel();
        notificationViewModel.addObserver(this);
        isMarked = notification.getIsMarked();

        if(isMarked.equals("0")){
            getBinding().btnMark.setVisibility(View.VISIBLE);
            getBinding().btnUnMark.setVisibility(View.GONE);
        }else {
            getBinding().btnMark.setVisibility(View.GONE);
            getBinding().btnUnMark.setVisibility(View.VISIBLE);
        }
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
                                showLoading();
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
        getBinding().time.setText(timeToString(Long.parseLong(notification.getCreatedAt())));

        getBinding().btnMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBinding().btnMark.setVisibility(View.GONE);
                getBinding().btnUnMark.setVisibility(View.VISIBLE);
                notificationViewModel.updateMark(notification.getId(), isMarked.equals("0")? "1":"0", 0);
            }
        });

        getBinding().btnUnMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBinding().btnMark.setVisibility(View.VISIBLE);
                getBinding().btnUnMark.setVisibility(View.GONE);
                notificationViewModel.updateMark(notification.getId(), isMarked.equals("0")? "1":"0", 0);
            }
        });

        getBinding().btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
                alertDialogBuilder
                        .setMessage("Bạn đồng ý xoá thông báo này chứ?")
                        .setNegativeButton("Huỷ bỏ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                notificationViewModel.updateInbox(
                                        notification.getId(),
                                        "2",
                                        1
                                    );
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });
    }

    public static String timeToString(Long dateTime) {
        if (dateTime != null) {
            Date date = new Date(dateTime);
            DateFormat formatter = new SimpleDateFormat("HH:mm dd/MM");
            String dateFormatted = formatter.format(date);
            return dateFormatted;
        } else {
            return null;
        }

    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {
        hideLoading();
        try {
            if (observable instanceof SystemInboxViewModel && null != o) {
                if (o instanceof ConfirmEnterTrip) {
                    try {
                        DetailTripActivity.Companion.startScreen(mActivity, ((ConfirmEnterTrip) o).getTripID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }  else if (o instanceof ErrorResponse) {
                    ErrorResponse responseError = (ErrorResponse) o;
                    try {
                        Toast.makeText(mActivity, responseError.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                }
            }else if(observable instanceof NotificationViewModel && null != o){
                if(o instanceof UpdateNotificationResponse) {
                    if (((UpdateNotificationResponse) o).getPosition() == 0) {
                        if (isMarked.equals("0")) {
                            isMarked = "1";
                        } else {
                            isMarked = "0";
                        }

                        if (isMarked.equals("0")) {
                            getBinding().btnMark.setVisibility(View.VISIBLE);
                            getBinding().btnUnMark.setVisibility(View.GONE);
                        } else {
                            getBinding().btnMark.setVisibility(View.GONE);
                            getBinding().btnUnMark.setVisibility(View.VISIBLE);
                        }
                        EventBus.getDefault().post(new OnReloadNoti());
                    }else {
                        EventBus.getDefault().post(new OnReloadNoti());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mActivity.onBackPressed();
                            }
                        }, 200);

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
