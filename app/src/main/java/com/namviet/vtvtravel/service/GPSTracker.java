package com.namviet.vtvtravel.service;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;


import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.MyLocation;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.view.MainActivity;

import org.json.JSONArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
public class GPSTracker extends Service implements LocationListener {


    // Flag for GPS status
    boolean isGPSEnabled = false;

    // Flag for network status
    boolean isNetworkEnabled = false;

    // Flag for GPS status
    boolean canGetLocation = false;

    Location location; // Location
    double latitude; // Latitude
    double longitude; // Longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    private Timer myTimer;
    private Timer myTimer2;

    private String data = "";

    private int countSend = 0;
    private String token;
    private String[] arrayData;
    private ArrayList<String> arrayListData = new ArrayList<>();
    private ArrayList<String> arrayListData120 = new ArrayList<>();
    private long delay = 5000;
    private String reStartSchedule1 = "ok";
    private String reStartSchedule2 = "ok";
    private String reStartSchedule3 = "ok";

    private String dataSend = "";


    private long time = 999999999;

    CountDownTimer timer;

    private Location lastLocation;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mainLocation();
        return START_STICKY;
    }






    /**
     * Function to check GPS/Wi-Fi enabled
     *
     * @return boolean
     */




    @Override
    public void onLocationChanged(Location location) {
        try {
            Log.e("location", "change" + location.getLongitude());
            PreferenceUtil.getInstance(GPSTracker.this).setValue(Constants.PrefKey.LAT_LOCATION, "" + location.getLatitude());
            PreferenceUtil.getInstance(GPSTracker.this).setValue(Constants.PrefKey.LNG_LOCATION, "" + location.getLongitude());

            MyApplication.getInstance().setMyLocation(new MyLocation("", "", "", location.getLatitude(), location.getLongitude()));
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {

                @Override
                public void run() {
                    try {
                        Toast.makeText(GPSTracker.this,location.getLatitude()+"----"+location.getLongitude(),Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onProviderDisabled(String provider) {
    }


    @Override
    public void onProviderEnabled(String provider) {
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private boolean isAppIsInBackground(Context context) {
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




    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("destroy service", "ok");
        timer.cancel();
    }

    private void getBestLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void mainLocation(){
        timer = new CountDownTimer(15000, 5000) {
            @Override
            public void onTick(long millisUntilFinished) {
                getBestLocation();
            }

            @Override
            public void onFinish() {
//                mainLocation();
            }
        };
        timer.start();
    }
}