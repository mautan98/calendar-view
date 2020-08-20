package com.namviet.vtvtravel.view.fragment.f2webview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentDetailDealWebviewBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnRegisterVipSuccess;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class VQMMWebviewFragment extends BaseFragment<F2FragmentDetailDealWebviewBinding> {
    private String token;
    private String chanel = "android";
    private String langCode = "vi";
    private String link;
    private String server = "http://103.21.148.54:8856/voucher-list?";

    public void setLink(String link) {
        this.link = link;
    }

    @SuppressLint("ValidFragment")
    public VQMMWebviewFragment(String link) {
        this.link = link;
    }

    public VQMMWebviewFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_detail_deal_webview;
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
                if(url.toUpperCase().equals("APP://REG_VIP")){
                    ServiceActivity.startScreen(mActivity);
                }else {
                    view.loadUrl(url);
                }

                return true;
            }
        });

        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            token = account.getToken();
            getBinding().webView.loadUrl(genLink());
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
                if(getBinding().webView.canGoBack()){
                    getBinding().webView.goBack();
                }else {
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
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onRegVipSuccess(OnRegisterVipSuccess onRegisterVipSuccess){
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            token = account.getToken();
            getBinding().webView.loadUrl(genLink());
        } else {
//            mActivity.onBackPressed();
        }
    }

    private String genLink(){
        return server+"token="+token+"&chanel="+chanel+"&langCode="+langCode;
    }
}
