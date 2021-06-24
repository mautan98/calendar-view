package com.namviet.vtvtravel.view.f3.notification.processing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.view.f2.DetailDealWebviewActivity;
import com.namviet.vtvtravel.view.f2.LiveTVActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.VQMMWebviewActivity;
import com.namviet.vtvtravel.view.f2.WebviewActivity;
import com.namviet.vtvtravel.view.f3.notification.model.Notification;
import com.namviet.vtvtravel.view.f3.notification.model.NotificationCode;

public class NotificationCaseProcessing {

    private Context context;
    public NotificationCaseProcessing(Context context) {
        this.context = context;
    }

    public void handleIntentFromNotification(String code, Notification notification){
        try {
            Account account = MyApplication.getInstance().getAccount();
            switch (code){
                case NotificationCode.WHEEL_TIME_OUT :
                case NotificationCode.SUBSCRIBE :
                    if (null != account && account.isLogin()) {
                        VQMMWebviewActivity.startScreen(context, "");
                    } else {
                        LoginAndRegisterActivityNew.startScreen(context, 0, false);
                    }
                    break;
                case NotificationCode.UN_SUBSCRIBE :

                    break;
                case NotificationCode.MAINTENANCE :

                    break;

                case NotificationCode.UPDATE :
                    final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                    break;
                case NotificationCode.LIVE_TV :
                    LiveTVActivity.openScreen(context, 0, "");
                    break;
                case NotificationCode.ADD_TO_CART :
                case NotificationCode.TIME_OUT :
                case NotificationCode.CODE_CANCEL :
                case NotificationCode.BOOKING_SUCCESS :
                case NotificationCode.FLY_TIME :
                    if (null != account && account.isLogin()) {
                        WebviewActivity.startScreen(context);
                    } else {
                        LoginAndRegisterActivityNew.startScreen(context, 0, false);
                    }
                    break;
                case NotificationCode.HUNT_DEAL_SUCCESS :

                    break;
                case NotificationCode.DEAL_LOSS :

                    break;
                case NotificationCode.DEAL_WIN :

                    break;
                case NotificationCode.HOT_DEAL :
                    try {
                        DetailDealWebviewActivity.startScreen(context, "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case NotificationCode.WIN_WHEEL :

                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
