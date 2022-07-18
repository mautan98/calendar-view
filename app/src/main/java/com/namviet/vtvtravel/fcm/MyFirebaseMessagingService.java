package com.namviet.vtvtravel.fcm;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.namviet.vtvtravel.view.f3.notification.model.NotificationCode;
import com.namviet.vtvtravel.view.f3.notification.model.Notification;

import androidx.core.app.NotificationCompat;


import android.os.PowerManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.f2event.OnReloadCountSystemInbox;

import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.f2.ReceiverCallActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.List;


/**
 * NOTE: There can only be one service in each app that receives FCM messages. If multiple
 * are declared in the Manifest then the first one will be chosen.
 * <p>
 * In order to make this Java sample functional, you must remove the following from the Kotlin messaging
 * service in the AndroidManifest.xml:
 * <p>
 * <intent-filter>
 * <action android:name="com.google.firebase.MESSAGING_EVENT" />
 * </intent-filter>
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Handler handler;


    // [START receive_message]

    static void runOnUiThread(Runnable action) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(action);
    }

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        try {
            if (remoteMessage.getData().size() > 0) {

                try {
                    EventBus.getDefault().post(new OnReloadCountSystemInbox());
                } catch (Exception e) {
                    e.printStackTrace();
                }



                Notification notification = new Gson().fromJson(remoteMessage.getData().get("data"), Notification.class);
                try {
                    Log.e("FirebaseJson", new Gson().toJson(notification));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(notification != null){
                    sendNotificationNotInApp(notification.getData().getCode(),notification);
                }

//                if (notification.getTitle().equals("INVITE_SCHEDULE")) {
//                    //                if (isAppRunning()) {
//                    //                    runOnUiThread(new Runnable() {
//                    //                        @Override
//                    //                        public void run() {
//                    ////                            EventBus.getDefault().post(new OnReceiveNotiVip());
//                    //                        }
//                    //                    });
//                    //                } else {
//                    DataSystemInbox dataSystemInbox = null;
//                    try {
//                        dataSystemInbox = new Gson().fromJson(notification.getContent().replaceAll("\\\\", ""), DataSystemInbox.class);
//                        notification.setDataSystemInbox(dataSystemInbox);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    sendNotificationNotInApp(NotificationType.INVITE_TRIP, notification);
//                    //                }
//                } else if(notification.getTitle().equals("SHARE")) {
//                    DataSystemInbox dataSystemInbox = null;
//                    try {
//                        dataSystemInbox = new Gson().fromJson(notification.getContent().replaceAll("\\\\", ""), DataSystemInbox.class);
//                        notification.setDataSystemInbox(dataSystemInbox);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    sendNotificationNotInApp(NotificationType.SHARE, notification);
//                }else if(notification.getTitle().equals("TICKET")) {
//                    DataSystemInbox dataSystemInbox = null;
//                    try {
//                        dataSystemInbox = new Gson().fromJson(notification.getContent().replaceAll("\\\\", ""), DataSystemInbox.class);
//                        notification.setDataSystemInbox(dataSystemInbox);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    sendNotificationNotInApp(NotificationType.TICKET, notification);
//                }else if(notification.getTitle().equals("VQMM")||notification.getTitle().equals("VOUCHER")){
//                    DataSystemInbox dataSystemInbox = null;
//                    try {
//                        dataSystemInbox = new Gson().fromJson(notification.getContent().replaceAll("\\\\", ""), DataSystemInbox.class);
//                        notification.setDataSystemInbox(dataSystemInbox);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    sendNotificationNotInApp(NotificationType.VIP, notification);
//                }



            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // test call
//        wakeup();
//        Intent intent = new Intent(this, ReceiverCallActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        if (!isAppRunning()) {
//            if (checkLocked()) {
//                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        sendNotification("Ai đó");
//                    }
//                }, 500);
//
//
//            } else {
//                Intent serviceIntent = new Intent(this, InCallForegroundService.class);
//                ContextCompat.startForegroundService(this, serviceIntent);
//
//
//                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        startActivity(intent);
//                    }
//                }, 500);
//            }
//        } else {
//            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    startActivity(intent);
//                }
//            }, 500);
//        }


//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//
//        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */
    private void scheduleJob() {
        // [START dispatch_job]
//        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
//                .build();
//        WorkManager.getInstance().beginWith(work).enqueue();
//        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }



    private boolean isAppRunning() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;

    }


    private void sendNotificationNotInApp(String notificationCode, Notification notification) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.IntentKey.NOTIFICATION_TYPE, notificationCode);
        intent.putExtra(Constants.IntentKey.NOTIFICATION, notification);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "VTVTravel Notification";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_app)
                        .setContentTitle(notification.getTitle())
                        .setContentText(notification.getMessage())
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent);
//        switch (notificationCode) {
//            case NotificationType.INVITE_TRIP:
//                notificationBuilder =
//                        new NotificationCompat.Builder(this, channelId)
//                                .setSmallIcon(R.mipmap.ic_app)
//                                .setContentTitle("Thông báo")
//                                .setContentText("Bạn nhận được lời mời vào chuyến đi.")
//                                .setAutoCancel(true)
//                                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                                .setContentIntent(pendingIntent);
//                break;
//            case NotificationType.VIP:
//                notificationBuilder =
//                        new NotificationCompat.Builder(this, channelId)
//                                .setSmallIcon(R.mipmap.ic_app)
//                                .setContentTitle("Thông báo")
//                                .setContentText("Bạn vừa đăng ký VIP thành công.")
//                                .setAutoCancel(true)
//                                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                                .setContentIntent(pendingIntent);
//                break;
//
//            case NotificationType.SHARE:
//                notificationBuilder =
//                        new NotificationCompat.Builder(this, channelId)
//                                .setSmallIcon(R.mipmap.ic_app)
//                                .setContentTitle("Thông báo")
//                                .setContentText("Bạn nhận được một chia sẻ bài viết.")
//                                .setAutoCancel(true)
//                                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                                .setContentIntent(pendingIntent);
//                break;
//
//            case NotificationType.TICKET:
//                notificationBuilder =
//                        new NotificationCompat.Builder(this, channelId)
//                                .setSmallIcon(R.mipmap.ic_app)
//                                .setContentTitle("Thông báo")
//                                .setContentText("Bạn nhận được ticket mới.")
//                                .setAutoCancel(true)
//                                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                                .setContentIntent(pendingIntent);
//                break;
//        }


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "VTVTravel Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        notificationManager.notify(m, notificationBuilder.build());
    }


    private void sendNotificationMessage(String title, String content) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.IntentKey.FROM_VIP_NOTI, true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "Vip";
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_app)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Vip",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        notificationManager.notify(m, notificationBuilder.build());
    }


    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, ReceiverCallActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

//        String channelId = getString(R.string.default_notification_channel_id);
        String channelId = "OTT Channel Call";
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win_sound);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.f2_ic_small_logo)
                        .setContentTitle("Có một cuộc gọi mới")
                        .setContentText(messageBody)
                        .setAutoCancel(false)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setSound(defaultSoundUri)
                        .setOngoing(true)
                        .setCategory(NotificationCompat.CATEGORY_CALL)
                        .setTimeoutAfter(30000)
                        .setFullScreenIntent(pendingIntent, true);
//                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "OTT call notification channel",
                    NotificationManager.IMPORTANCE_HIGH);
            Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win_sound);

            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();
            channel.setSound(soundUri, att);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(1000 /* ID of notification */, notificationBuilder.build());
//        startForeground(0 /* ID of notification */, notificationBuilder.build());
    }


    private boolean checkLocked() {
        KeyguardManager myKM = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
        if (myKM.inKeyguardRestrictedInputMode()) {
            return true;
        } else {
            return false;
        }
    }

    private void wakeup() {
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        if (isScreenOn == false) {
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyLock");
            wl.acquire(10000);
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyCpuLock");

            wl_cpu.acquire(10000);
        }
    }


}
