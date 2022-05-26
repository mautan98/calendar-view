package com.namviet.vtvtravel.view.fragment.f2mytrip;

import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentMyTripBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.view.f2.DisplayMarkerForMapActivity;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;

import java.util.HashMap;
import java.util.Map;

public class MyTripFragment extends BaseFragment<F2FragmentMyTripBinding> {
    Map<String, String> extraHeaders = new HashMap<>();
    public MyTripFragment() {
    }

    private String link = WSConfig.HOST_MY_TRIP;
    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_my_trip;
    }

    @Override
    public void initView() {



    }

    @Override
    public void initData() {

    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {

    }

    @Override
    public void setObserver() {

    }

}
