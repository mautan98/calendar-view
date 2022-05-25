package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityLitterPartLuckyWheelBinding;
import com.namviet.vtvtravel.databinding.F2FragmentCreateTripBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Account;

import java.util.HashMap;
import java.util.Map;

public class ReceiveInviteTripDetailActivity extends BaseActivityNew<F2FragmentCreateTripBinding> {
    private String token;
    private String link;
    private String server = WSConfig.HOST_RECEIVE_TRIP;
    private String scheduleId = "";
    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_create_trip;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        scheduleId = getIntent().getStringExtra(Constants.IntentKey.SCHEDULE_ID);
    }

    @Override
    public void doAfterOnCreate() {
    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        return null;
    }


    private String genLink() {
        return server + "/"+scheduleId+"?backLink=" + "true";
    }

    public static void startScreen(Context activity, String scheduleId) {
        Intent intent = new Intent(activity, ReceiveInviteTripDetailActivity.class);
        intent.putExtra(Constants.IntentKey.SCHEDULE_ID, scheduleId);
        activity.startActivity(intent);
    }


}
