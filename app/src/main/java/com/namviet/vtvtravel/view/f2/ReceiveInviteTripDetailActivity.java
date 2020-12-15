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
        WebSettings webSettings = getBinding().webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setSupportMultipleWindows(true); // This forces ChromeClient enabled.

        getBinding().webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) { //...page is fully loaded.
                    try {
                        getBinding().shimmerViewContainer.stopShimmer();
                        getBinding().layoutLoading.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        getBinding().webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.toUpperCase().equals("APP://EXIT")) {
                    finish();
                } else if (url.toUpperCase().startsWith("APP://DETAIL")) {
                    try {
                        String link = new UrlQuerySanitizer(url).getValue("link");
                        SmallLocationActivity.startScreenDetail(ReceiveInviteTripDetailActivity.this, SmallLocationActivity.OpenType.DETAIL, link);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if(url.toUpperCase().startsWith("APP://MAP")){
                    try {
                        String link = new UrlQuerySanitizer(url).getValue("link");
                        DisplayMarkerForMapActivity.startScreen(ReceiveInviteTripDetailActivity.this, link);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(url.toUpperCase().equals("APP://CALL")){
                    try {
                        String phone = "1039";
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    view.loadUrl(url);
                }
                return true;
            }
        });

        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            token = account.getToken();
            Map<String, String> extraHeaders = new HashMap<>();
            extraHeaders.put("token",token);
            getBinding().webView.loadUrl(genLink(), extraHeaders);
        } else {

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
        return server + "/"+scheduleId+"?backLink=" + "true";
    }

    public static void startScreen(Context activity, String scheduleId) {
        Intent intent = new Intent(activity, ReceiveInviteTripDetailActivity.class);
        intent.putExtra(Constants.IntentKey.SCHEDULE_ID, scheduleId);
        activity.startActivity(intent);
    }


}
