package com.namviet.vtvtravel.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class StorageModule {

    private Application app;

    public StorageModule(Application app) {
        this.app = app;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return app;
    }

    @Provides
    public SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    public SharedPreferences.Editor getEditor(SharedPreferences prefs) {
        return prefs.edit();
    }
}
