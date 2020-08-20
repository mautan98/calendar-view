package com.namviet.vtvtravel.ultils;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.response.ResponseError;

import org.json.JSONObject;

import retrofit2.HttpException;

public class ResponseUltils {
    private static FirebaseAnalytics mFirebaseAnalytics;

    public static ResponseError requestFailed(Throwable throwable) throws Exception {
        HttpException error = (HttpException) throwable;
        String errorBody = error.response().errorBody().string();
        JSONObject jsonOb = new JSONObject(errorBody);
        String arrJson = jsonOb.getString("message");
        ResponseError responseError = new ResponseError(arrJson);
        // String a = throwable.getStackTrace().toString();
        // String b = throwable.getMessage().toString();
        // String c = jsonOb.getString("url");
        // String d = jsonOb.getString("url");

        return responseError;
    }

    public static void logEventApiError(Context context, String errorBody){
        if (null == mFirebaseAnalytics) {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytic.ERROR_MESSAGE, errorBody);
            mFirebaseAnalytics.logEvent(FirebaseAnalytic.API_ERROR, bundle);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytic.API_ERROR, errorBody);
            mFirebaseAnalytics.logEvent(FirebaseAnalytic.API_ERROR, bundle);
        }
    }
}
