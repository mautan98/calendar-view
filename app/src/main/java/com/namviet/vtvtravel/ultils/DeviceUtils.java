package com.namviet.vtvtravel.ultils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import com.namviet.vtvtravel.BuildConfig;


/**
 * Created by admin on 3/2/2018.
 */

public class DeviceUtils {
    public static String getDeviceId(Context mContext) {
        return Settings.Secure.getString(mContext.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static String getPlatform() {
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        return sdkVersion + " (" + release + ")";
    }

    public static String getVerisonCode() {
        return "" + BuildConfig.VERSION_NAME;
    }

    public static String getModel() {
        return Build.MODEL;
    }
}
