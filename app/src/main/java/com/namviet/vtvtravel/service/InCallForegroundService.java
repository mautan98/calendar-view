package com.namviet.vtvtravel.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.github.nkzawa.socketio.client.Socket;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.view.f2.ReceiverCallActivity;

public class InCallForegroundService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannelOTT";
//    private NotificationFirebaseCall notificationFirebase;
    private Handler handler;
    private Socket socket;
    MediaPlayer mPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initSocket();
//        notificationFirebase = (NotificationFirebaseCall) intent.getSerializableExtra(Constants.IntentKey.NOTI_DATA);
//        if (notificationFirebase.getSenderInfo().getFullName() != null && !notificationFirebase.getSenderInfo().getFullName().isEmpty()) {
//            sendNotification(notificationFirebase.getSenderInfo().getFullName());
//        } else {
            sendNotification("Ai đó");
//        }

        setUpRingtone();

//        String input = intent.getStringExtra("inputExtra");
//        createNotificationChannel();
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                0, notificationIntent, 0);
//
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("Foreground Service")
//                .setContentText(input)
//                .setSmallIcon(R.drawable.logo_app)
//                .setContentIntent(pendingIntent)
//                .build();
//
//        startForeground(1, notification);



        //do heavy work on a background thread


        //stopSelf();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    stopForeground(true);
                    stopSelf();
                } catch (Exception e) {

                }
            }
        }, 45000);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.release();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void setUpRingtone() {
        mPlayer = MediaPlayer.create(this, R.raw.win_sound);
        mPlayer.start();
    }

    private void initSocket() {
//        IO.Options opts = new IO.Options();
//        opts.forceNew = true;
//        opts.query = "auth_token=" + MyApplication.getInstance().getAccount().getToken();
//
//        try {
//            socket = IO.socket(SocketManager.URL, opts);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        socket.connect();
//
//
//        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
////                    socket.emit("myConnect", "userinfo");
//                Log.e("SignalingResponse", "connected");
//            }
//        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
//            @Override
//            public void call(Object... arg0) {
//                Log.e("SignalingResponse", "EVENT_CONNECT_ERROR" + arg0[0].toString());
//            }
//        }).on(Socket.EVENT_ERROR, new Emitter.Listener() {
//            @Override
//            public void call(Object... arg0) {
//                Log.e("SignalingResponse", "EVENT_ERROR" + arg0[0].toString());
//            }
//        }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
//            @Override
//            public void call(Object... arg0) {
//                Log.e("SignalingResponse", "EVENT_CONNECT_ERROR" + arg0[0].toString());
//            }
//
//        }).on("something_changed", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                JSONObject obj = (JSONObject) args[0];
//                Log.i("something_changed", obj.toString());
//            }
//        });
//
//
//        socket.on(SignallingClient.KEY, args -> {
//            try {
//                SocketObjectCall socketObjectCall = new Gson().fromJson(String.valueOf(args[0]), SocketObjectCall.class);
//                int type = Integer.parseInt(socketObjectCall.getType());
//                if (type == 4) {
//                    if (socketObjectCall.getMessage().getUser_id() != MyApplication.getInstance().getAccount().getId()) {
//                        stopForeground(true);
//                        stopSelf();
//                    }
//                }
//            } catch (Exception e) {
//            }
//        });
    }

    public void emitHangup(String roomId) {
//        try {
//            JSONObject obj = new JSONObject();
//            obj.put("type", 4);
//            obj.put("room_id", roomId);
//
//            JSONObject payload = new JSONObject();
//            payload.put("roomId", roomId);
//
//            JSONObject senderInfo = new JSONObject();
//            senderInfo.put("fullName", MyApplication.getInstance().getAccount().getUserInfo().getFullName());
//            senderInfo.put("id", MyApplication.getInstance().getAccount().getUserInfo().getId());
//
//            payload.put("senderInfo", senderInfo);
//            obj.put("payload", payload);
//            socket.emit(SignallingClient.KEY, obj);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, ReceiverCallActivity.class);
//        intent.putExtra(Constants.IntentKey.ROOM_ID, notificationFirebase.getRoomId());
//        intent.putExtra(Constants.IntentKey.FULL_NAME, notificationFirebase.getSenderInfo().getFullName());
//        intent.putExtra(Constants.IntentKey.IS_VIDEO_CALL, notificationFirebase.isHasVideo());
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

//        String channelId = getString(R.string.default_notification_channel_id);
        String channelId = "ForegroundServiceChannelOTT";
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
                        .addAction(R.drawable.f2_ic_small_logo, "Nhấn để trả lời hoặc từ chối", pendingIntent)
                        .setFullScreenIntent(pendingIntent, true);
//                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_HIGH);
            Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win_sound);

            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();
            channel.setSound(soundUri, att);
            notificationManager.createNotificationChannel(channel);
        }

//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        startForeground(1001 /* ID of notification */, notificationBuilder.build());
    }





}
