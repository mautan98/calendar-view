package com.namviet.vtvtravel.view.f3.booking.view;

import android.graphics.Bitmap;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentBookingBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2booking.DataHelpCenter;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndGoToBooking;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndUpdateUserView;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.MyGiftActivity;
import com.namviet.vtvtravel.view.f2.TravelVoucherActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

public class BookingFragment extends BaseFragment<F2FragmentBookingBinding> {
    private String link = WSConfig.HOST_BOOKING;
    private String token;
    private String backLink;
    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_booking;
    }

    @Override
    public void initView() {
        WebSettings webSettings = getBinding().webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
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
                view.loadUrl(url);
                return true;
            }
        });

        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        getBinding().webView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
//                Log.v("TAG", "+++ scrollchanged "+getBinding().webView.getScrollY());
//
//                try {
//                    if(getBinding().webView.getScrollY() > 800){
//                        setHeightToolbar(getBinding().toolBar, 30);
//                    }else if(getBinding().webView.getScrollY() <= 800 && getBinding().webView.getScrollY() > 700){
//                        setHeightToolbar(getBinding().toolBar, 32);
//                    }else if(getBinding().webView.getScrollY() <= 700 && getBinding().webView.getScrollY() > 600){
//                        setHeightToolbar(getBinding().toolBar, 35);
//                    }else if(getBinding().webView.getScrollY() <= 600 && getBinding().webView.getScrollY() > 500){
//                        setHeightToolbar(getBinding().toolBar, 37);
//                    }else if(getBinding().webView.getScrollY() <= 500 && getBinding().webView.getScrollY() > 400){
//                        setHeightToolbar(getBinding().toolBar, 40);
//                    }else if(getBinding().webView.getScrollY() <= 400 && getBinding().webView.getScrollY() > 300){
//                        setHeightToolbar(getBinding().toolBar, 42);
//                    }else if(getBinding().webView.getScrollY() <= 300 && getBinding().webView.getScrollY() > 200){
//                        setHeightToolbar(getBinding().toolBar, 45);
//                    }else if(getBinding().webView.getScrollY() <= 200 && getBinding().webView.getScrollY() > 100){
//                        setHeightToolbar(getBinding().toolBar, 47);
//                    }else if(getBinding().webView.getScrollY() <= 100 && getBinding().webView.getScrollY() > 0){
//                        setHeightToolbar(getBinding().toolBar, 50);
//                    }else {
//                        setHeightToolbar(getBinding().toolBar, 50);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

            }
        });


        getBinding().webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    if (url.startsWith("app://login")) {
                        backLink = new UrlQuerySanitizer(url).getValue("backlink");
                        LoginAndRegisterActivityNew.startScreen(mActivity, 0, false, true, true);
                    }else if(url.startsWith("app://vtvtravel-help")) {
                        DataHelpCenter dataHelpCenter = new Gson().fromJson(F2Util.loadJSONFromAsset(mActivity, "helpcenter_test"), DataHelpCenter.class);
                        MyGiftActivity.startScreen(mActivity, dataHelpCenter.getItemMenus(), dataHelpCenter.getName());
                    }else if(url.startsWith("app://vtvtravel-voucherforbooking")) {
                        TravelVoucherActivity.openScreen(mActivity, false, TravelVoucherActivity.OpenType.LIST, false);
                    }else if(url.startsWith("app://vtvtravel-home")) {
                        mActivity.finish();
                    }else {
                        view.loadUrl(url);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });


        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            token = account.getToken();
            Map<String, String> extraHeaders = new HashMap<>();
            extraHeaders.put("token", token);
            getBinding().webView.loadUrl(genLink(), extraHeaders);
        } else {
            getBinding().webView.loadUrl(genLink());
//            Toast.makeText(mActivity, "Bạn cần đăng nhập để sử dụng chức năng này!", Toast.LENGTH_SHORT).show();
        }
    }


    private void setHeightToolbar(View view, int dp){
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) view.getLayoutParams();
        params.height = (int) dpToPx(dp);
        view.setLayoutParams(params);
    }


    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private String genLink() {
        return link;
    }

    private String genLinkWithBackLink(){
        return backLink;
    }

    @Override
    public void initData() {
        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.BOOKING, TrackingAnalytic.ScreenTitle.BOOKING).setScreen_class(this.getClass().getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        getBinding().webView.clearCache(true);
        getBinding().webView.clearHistory();
    }

    @Subscribe
    public void OnReload(OnLoginSuccessAndGoToBooking onLoginSuccessAndGoToBooking) {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
//            CookieManager cookieManager = CookieManager.getInstance();
//            cookieManager.removeSessionCookie();
//            binding.webView.clearCache(true);
//            binding.webView.clearHistory();

            token = account.getToken();
            Map<String, String> extraHeaders = new HashMap<>();
            extraHeaders.put("token", token);
            getBinding().webView.loadUrl(genLinkWithBackLink(), extraHeaders);
        } else {
            LoginAndRegisterActivityNew.startScreen(mActivity, 0, false, true);
        }
    }

    @Subscribe
    public void onReloadUserView(OnLoginSuccessAndUpdateUserView onLoginSuccessAndUpdateUserView) {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {

        } else {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeSessionCookie();
            getBinding().webView.clearCache(true);
            getBinding().webView.clearHistory();
            getBinding().webView.loadUrl(genLink());
//            binding.webView.loadDataWithBaseURL("", loginHtml, "text/html", "UTF-8", null);
        }

    }
}
