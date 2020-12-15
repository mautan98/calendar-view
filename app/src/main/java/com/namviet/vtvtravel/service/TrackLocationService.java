package com.namviet.vtvtravel.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import androidx.annotation.Nullable;

import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.ultils.DeviceUtils;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import org.ankit.gpslibrary.MyTracker;

public class TrackLocationService extends Service {
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            long endTime = System.currentTimeMillis() + 30 * 1000;
            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            }
            MyTracker tracker = new MyTracker(getApplication(), loc -> {
                double latNew = loc.getLat();
                double lngNew = loc.getLng();
                double latSave = Double.parseDouble(PreferenceUtil.getInstance(getBaseContext()).getValue(Constants.PrefKey.LAT_LOCATION, ""));
                double lngSave = Double.parseDouble(PreferenceUtil.getInstance(getBaseContext()).getValue(Constants.PrefKey.LNG_LOCATION, ""));
                if (distance(latSave, lngSave, latNew, lngNew) > 200) {
                    PreferenceUtil.getInstance(getBaseContext()).setValue(Constants.PrefKey.LAT_LOCATION, "" + latNew);
                    PreferenceUtil.getInstance(getBaseContext()).setValue(Constants.PrefKey.LNG_LOCATION, "" + lngNew);
                    BaseViewModel baseViewModel = new BaseViewModel();
                    baseViewModel.trackLocation(latNew, lngNew, DeviceUtils.getDeviceId(getBaseContext()));
                }
            });

            sendMessage(new Message());
        }
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
    }

    private float distance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);
        return dist;
    }
}
