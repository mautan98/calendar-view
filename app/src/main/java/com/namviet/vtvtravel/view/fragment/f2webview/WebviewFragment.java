package com.namviet.vtvtravel.view.fragment.f2webview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentMyOrderBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Account;

import java.util.HashMap;
import java.util.Map;

public class WebviewFragment extends BaseFragment<F2FragmentMyOrderBinding> {
    private String token;
    private String chanel = "android";
    private String langCode = "vi";
    private String link;
    private String server = WSConfig.HOST_ORDER;


    public void setLink(String link) {
        this.link = link;
    }

    @SuppressLint("ValidFragment")
    public WebviewFragment(String link) {
        this.link = link;
    }

    public WebviewFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_my_order;
    }

    @Override
    public void initView() {
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
                if(url.toUpperCase().equals("APP://BOOKING-EXIT")){
                    mActivity.finish();
                }else {
                    view.loadUrl(url);
                }
                return true;
            }
        });

        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            token = account.getToken();
            Map<String, String> extraHeaders = new HashMap<String, String>();
            extraHeaders.put("token",token);
            getBinding().webView.loadUrl(genLink(), extraHeaders);
        } else {
//            mActivity.onBackPressed();
        }

    }


    @Override
    public void initData() {

    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getBinding().webView.canGoBack()) {
                    getBinding().webView.goBack();
                } else {
                    mActivity.onBackPressed();
                }
            }
        });
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }



    private String genLink() {
        return server;
    }
}
