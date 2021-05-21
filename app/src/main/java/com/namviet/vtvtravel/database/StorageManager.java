package com.namviet.vtvtravel.database;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;



public class StorageManager {

    private Context context;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Inject
    public StorageManager(Context context, SharedPreferences prefs, SharedPreferences.Editor editor) {
        this.context = context;
        this.prefs = prefs;
        this.editor = editor;
    }

    public void delete() {
        prefs.edit().clear().apply();
    }

    public String getStringValue(String key) {
        return getStringValue(key, null);
    }

    public String getStringValue(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    public void setStringValue(String key, String value) {
        if (editor == null) {
            editor = prefs.edit();
        }
        editor.putString(key, value);
        editor.commit();
    }

    public int getIntValue(String key, int valueDefault) {
        return prefs.getInt(key, valueDefault);
    }

    public void setIntValue(String key, int value) {
        if (editor == null) {
            editor = prefs.edit();
        }
        editor.putInt(key, value);
        editor.commit();
    }


    public double getDoubleValue(String key, double valueDefault) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(valueDefault)));
    }

    public void setDoubleValue(String key, double value) {
        if (editor == null) {
            editor = prefs.edit();
        }
        editor.putLong(key, Double.doubleToRawLongBits(value));
        editor.commit();
    }

    public void removeKey(String key) {
        if (editor == null) {
            editor = prefs.edit();
        }
        editor.remove(key);
        editor.commit();
    }

    public void setBooleanValue(String key, boolean value) {
        if (editor == null) {
            editor = prefs.edit();
        }
        editor.putBoolean(key, value);
        editor.commit();
    }


    public boolean getBooleanValue(String key) {
        return prefs.getBoolean(key, false);
    }

    public boolean getBooleanValue(String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }
}
