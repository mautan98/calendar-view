package com.namviet.vtvtravel.ultils;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AlertDialog;
import android.widget.RemoteViews;


import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.view.MainActivity;

import java.util.List;

/**
 * Created by 1 on 2/6/2018.
 */

public class NotificationUtils {

    private static String TAG = NotificationUtils.class.getSimpleName();

    private Context mContext;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }


    public void showNotification(String title, String content, Integer transactionId) {
        int num = (int) System.currentTimeMillis();
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.item_notify);

        RemoteViews notificationLayoutExpanded = new RemoteViews(mContext.getPackageName(), R.layout.notification_large);

        Intent intent = new Intent(mContext, MainActivity.class);
//        intent.putExtra(Constants.FCM.IS_NOTIFY, Constants.FCM_TYPE.FCM_TYPE_1);
//        intent.putExtra(Constants.FCM.TRANSACTION_ID, transactionId);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pi = PendingIntent.getActivity(mContext, num, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_app)
                .setAutoCancel(true)
                .setContentIntent(pi)
                .setPriority(Notification.PRIORITY_MAX)
                .setVibrate(new long[]{100, 100, 100, 100, 100})
                .setContent(remoteViews)
                .setCustomBigContentView(notificationLayoutExpanded);

        Notification notification = builder.build();

        remoteViews.setImageViewResource(R.id.ivAvatar, R.mipmap.ic_launcher);
        notificationLayoutExpanded.setImageViewResource(R.id.ivAvatar, R.mipmap.ic_launcher);

        remoteViews.setTextViewText(R.id.tvTitle, title);
        notificationLayoutExpanded.setTextViewText(R.id.tvTitle, title);

        remoteViews.setTextViewText(R.id.tvMessage, content);
        notificationLayoutExpanded.setTextViewText(R.id.tvMessage, content);

        remoteViews.setTextViewText(R.id.tvTime, ValidateUtils.getCurrentTime());
        notificationLayoutExpanded.setTextViewText(R.id.tvTime, ValidateUtils.getCurrentTime());
        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(num, notification);
    }

    public void showNotificationNotClick(String title, String content, int transactionId) {
        int num = (int) System.currentTimeMillis();
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.item_notify);

        RemoteViews notificationLayoutExpanded = new RemoteViews(mContext.getPackageName(), R.layout.notification_large);

        Intent intent = new Intent();
        PendingIntent pi = PendingIntent.getActivity(mContext, num, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "default")
                .setSmallIcon(R.mipmap.ic_app)
                .setAutoCancel(true)
                .setContentIntent(pi)
                .setPriority(Notification.PRIORITY_MAX)
                .setVibrate(new long[]{100, 100, 100, 100, 100})
                .setContent(remoteViews)
                .setCustomBigContentView(notificationLayoutExpanded);

        Notification notification = builder.build();

        remoteViews.setImageViewResource(R.id.ivAvatar, R.mipmap.ic_app);
        notificationLayoutExpanded.setImageViewResource(R.id.ivAvatar, R.mipmap.ic_launcher);

        remoteViews.setTextViewText(R.id.tvTitle, title);
        notificationLayoutExpanded.setTextViewText(R.id.tvTitle, title);

        remoteViews.setTextViewText(R.id.tvMessage, content);
        notificationLayoutExpanded.setTextViewText(R.id.tvMessage, content);

        remoteViews.setTextViewText(R.id.tvTime, ValidateUtils.getCurrentTime());
        notificationLayoutExpanded.setTextViewText(R.id.tvTime, ValidateUtils.getCurrentTime());
        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(num, notification);
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public void showDialog(Context context, String title) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(title);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
