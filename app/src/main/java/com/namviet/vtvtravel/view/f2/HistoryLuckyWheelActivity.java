package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;
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
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.f2menu.MenuItem;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryLuckyWheelActivity extends BaseActivityNew<F2ActivityLitterPartLuckyWheelBinding> {
    private String token;
    private String chanel = "android";
    private String langCode = "vi";
    private String link;
    private String server = WSConfig.HOST_VQMM_HISTORY;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_litter_part_lucky_wheel;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void doAfterOnCreate() {
        WebSettings webSettings = getBinding().webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setSupportMultipleWindows(true); // This forces ChromeClient enabled.

        getBinding().webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) { //...page is fully loaded.
                    // TODO - Add whatever code you need here based on web page load completion...
                    getBinding().shimmerViewContainer.stopShimmer();
                    getBinding().layoutLoading.setVisibility(View.GONE);
                }
            }
        });

        getBinding().webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.toUpperCase().equals("APP://EXIT")) {
                    finish();
                } else {
                    view.loadUrl(url);
                }

                return true;
            }
        });

        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            token = account.getToken();
//            getBinding().webView.loadUrl(genLink());
            Map<String, String> extraHeaders = new HashMap<String, String>();
            extraHeaders.put("token",token);
            getBinding().webView.loadUrl(genLink(), extraHeaders);
        } else {
//            mActivity.onBackPressed();
        }
    }

    @Override
    public void setClick() {
    }

    @Override
    public BaseFragment initFragment() {
        return null;
    }


    private String genLink() {
        return server+ "chanel=" + chanel + "&langCode=" + langCode;
    }

    public static void startScreen(Context activity) {
        Intent intent = new Intent(activity, HistoryLuckyWheelActivity.class);
        activity.startActivity(intent);
    }
}
