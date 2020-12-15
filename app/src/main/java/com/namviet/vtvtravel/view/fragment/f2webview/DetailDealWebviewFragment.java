package com.namviet.vtvtravel.view.fragment.f2webview;

import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentWebviewForDealBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndReloadDeal;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndUpdateUserView;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.UserInformationActivity;
import com.namviet.vtvtravel.view.f2.f2oldbase.SettingActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DetailDealWebviewFragment extends BaseFragment<F2FragmentWebviewForDealBinding> {
    private String link;
    private String platform = "android";

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_webview_for_deal;
    }


    private String backLink;

    @Override
    public void initView() {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            Map<String, String> extraHeaders = new HashMap<>();
            extraHeaders.put("token",account.getToken());
            getBinding().webView.loadUrl(genLink(), extraHeaders);
        } else {
            getBinding().webView.loadUrl(genLink());
        }

        getBinding().webView.getSettings().setJavaScriptEnabled(true);

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
                try {
                    if (url.equals("app://vtvtravel-home")) {
                        mActivity.onBackPressed();
                    } else if (url.startsWith("http://vtvtravel.wap/")) {
                        backLink = new UrlQuerySanitizer(url).getValue("backLink");
                        if (backLink == null) {
                            mActivity.onBackPressed();
                        } else {
                            LoginAndRegisterActivityNew.startScreen(mActivity, 0, false, false, true);
                        }
                    } else if (url.equals("app://logout")) {
                        SettingActivity.startScreen(mActivity);
                    } else if (url.equals("app://login")) {
                        LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                    } else if (url.equals("app://register")) {
                        LoginAndRegisterActivityNew.startScreen(mActivity, 1, false);
                    } else if (url.equals("app://info")) {
                        UserInformationActivity.openScreen(mActivity);
                    } else {
                        view.loadUrl(url);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }


    @Override
    public void initData() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
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

    private String genLink() {

        Uri builtUri = Uri.parse(link)
                .buildUpon()
                .appendQueryParameter("platform", platform)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return url.toString();

    }

    private String genLinkWithBackLink() {

        Uri builtUri = Uri.parse(backLink)
                .buildUpon()
                .appendQueryParameter("platform", platform)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return url.toString();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        try {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeSessionCookie();
            cookieManager.removeAllCookie();
            getBinding().webView.clearCache(true);
            getBinding().webView.clearHistory();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Subscribe
    public void onLoginSuccessAndReloadDeal(OnLoginSuccessAndReloadDeal onLoginSuccessAndReloadDeal) {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            Map<String, String> extraHeaders = new HashMap<>();
            extraHeaders.put("token",account.getToken());
            getBinding().webView.loadUrl(genLinkWithBackLink(), extraHeaders);
        } else {
            getBinding().webView.loadUrl(genLinkWithBackLink());
        }
    }


    @Subscribe
    public void onReloadUserView(OnLoginSuccessAndUpdateUserView onLoginSuccessAndUpdateUserView) {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            Map<String, String> extraHeaders = new HashMap<>();
            extraHeaders.put("token",account.getToken());
            getBinding().webView.loadUrl(genLink(), extraHeaders);
        } else {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeSessionCookie();
            cookieManager.removeAllCookie();
            getBinding().webView.loadUrl(genLink());
        }


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.DEAL_HOT, TrackingAnalytic.ScreenTitle.DEAL_HOT);
    }

}
