package com.namviet.vtvtravel.view.fragment.f2webview;

import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2ActivityDealWebviewBinding;
import com.namviet.vtvtravel.databinding.F2FragmentDetailDealBinding;
import com.namviet.vtvtravel.databinding.F2FragmentDetailDealWebviewBinding;
import com.namviet.vtvtravel.databinding.F2FragmentWebviewForDealBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndReloadDeal;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndUpdateUserView;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.UserInformationActivity;
import com.namviet.vtvtravel.view.f2.f2oldbase.SettingActivity;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
        getBinding().webView.loadUrl(genLink());
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
//        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(getBinding().webView.canGoBack()){
//                    getBinding().webView.goBack();
//                }else {
//                    mActivity.onBackPressed();
//                }
//            }
//        });
    }

    @Override
    public void setObserver() {

    }

    private String genLink() {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            return link + "?platform=" + platform + "&token=" + account.getToken();
        } else {
            return link + "?platform=" + platform;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        cookieManager.removeAllCookie();
    }

    @Subscribe
    public void onLoginSuccessAndReloadDeal(OnLoginSuccessAndReloadDeal onLoginSuccessAndReloadDeal) {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            getBinding().webView.loadUrl(backLink + "?platform=" + platform + "&token=" + account.getToken());
        } else {
            getBinding().webView.loadUrl(backLink + "?platform=" + platform);
        }
    }


    @Subscribe
    public void onReloadUserView(OnLoginSuccessAndUpdateUserView onLoginSuccessAndUpdateUserView) {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            getBinding().webView.loadUrl(genLink());
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

}
