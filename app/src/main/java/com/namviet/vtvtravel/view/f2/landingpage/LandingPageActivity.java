package com.namviet.vtvtravel.view.f2.landingpage;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.databinding.F2ActivityLandingPageBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;

public class LandingPageActivity extends BaseActivityNew<F2ActivityLandingPageBinding> {
    private String server = WSConfig.HOST_LANDING_PAGE;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_landing_page;
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

        getBinding().webView.loadUrl(genLink());
    }

    @Override
    public void setClick() {
    }

    @Override
    public BaseFragment initFragment() {
        return null;
    }


    private String genLink() {
        return server;
    }

    public static void startScreen(Context activity) {
        Intent intent = new Intent(activity, LandingPageActivity.class);
        activity.startActivity(intent);
    }
}
