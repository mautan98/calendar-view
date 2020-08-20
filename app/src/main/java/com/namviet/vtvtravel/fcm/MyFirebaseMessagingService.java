package com.namviet.vtvtravel.fcm;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DebugUtils;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelFactory;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.ItemNotify;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.CountUnreadRespone;
import com.namviet.vtvtravel.ultils.DeviceUtils;
import com.namviet.vtvtravel.ultils.NotificationUtils;

import java.lang.reflect.Parameter;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by 1 on 2/6/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    private NotificationUtils notificationUtils;

    private Account mAccount;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        mAccount = MyApplication.getInstance().getAccount();

        notificationUtils = new NotificationUtils(this);
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage == null)
            return;
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
        }
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            getCountUnread();
            try {
                String json = remoteMessage.getData().get(Constants.FCM.MESSAGE);
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleDataMessage(String json) {
        ItemNotify fcmNotify = new Gson().fromJson(json, ItemNotify.class);
        Log.e(TAG, "handleDataMessage: " + fcmNotify.toString());
        if (null != fcmNotify) {
            if (MyApplication.getInstance() != null && MyApplication.isActivityVisible()) {
                Intent intent = new Intent(Constants.KeyBroadcast.KEY_NOTIFY);
                intent.putExtra(Constants.IntentKey.KEY_NOTIFY, fcmNotify);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            } else {
                notificationUtils.showNotification(fcmNotify.getTitle(), fcmNotify.getContent(), null);
            }
//            notificationUtils.showNotification(fcmNotify.getTitle(), fcmNotify.getContent(), null);
        }
    }

    private void getCountUnread() {
        Map<String, Object> queryMap = Param.getListNotify(DeviceUtils.getDeviceId(getBaseContext()));
        TravelService apiService = TravelFactory.createService(TravelService.class);
        Call<CountUnreadRespone> call = apiService.getCountUnread(queryMap);
        call.enqueue(new Callback<CountUnreadRespone>() {
            @Override
            public void onResponse(Call<CountUnreadRespone> call, Response<CountUnreadRespone> response) {
                if (null != response.body().getData()) {
                    Intent intent = new Intent(Constants.KeyBroadcast.KEY_COUNT_UNREAD);
                    intent.putExtra(Constants.IntentKey.KEY_COUNT_UNREAD, response.body().getData().getCount());
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                }
            }

            @Override
            public void onFailure(Call<CountUnreadRespone> call, Throwable t) {

            }
        });
    }
}
