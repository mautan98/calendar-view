package com.namviet.vtvtravel.view.fragment.f2booking;

import androidx.databinding.DataBindingUtil;

import android.graphics.Bitmap;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentBookingBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2booking.DataHelpCenter;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndGoToBooking;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndUpdateUserView;
import com.namviet.vtvtravel.response.f2menu.MenuItem;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.MyGiftActivity;
import com.namviet.vtvtravel.view.f2.TravelVoucherActivity;
import com.namviet.vtvtravel.view.f2.UserInformationActivity;
import com.namviet.vtvtravel.view.f2.f2oldbase.SettingActivity;
import com.namviet.vtvtravel.view.fragment.MainFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingFragment extends MainFragment {
    private F2FragmentBookingBinding binding;
    private String link = WSConfig.HOST_BOOKING;
    //    private String link = "http://103.21.148.54:8857/get-list?token=";
    private String token;
    private String backLink;

    private String loginHtml = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<title>Page Title</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<style>\n" +
            "div.container4 {\n" +
            "    height: 10em;\n" +
            "    position: relative }\n" +
            "div.container4 p {\n" +
            "    margin: 0;\n" +
            "    background: white;\n" +
            "    position: absolute;\n" +
            "    top: 50%;\n" +
            "    left: 50%;\n" +
            "    margin-right: -50%;\n" +
            "    transform: translate(-50%, -50%) }\n" +
            "</style>\n" +
            "\n" +
            "<div class=container4>\n" +
            "  <p>Bạn cần đăng nhập để sử dụng chức năng này!\n" +
            "</div>\n" +
            "\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_booking, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.BOOKING, TrackingAnalytic.ScreenTitle.BOOKING).setScreen_class(this.getClass().getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);

        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setGeolocationEnabled(true);
        webSettings.setSupportMultipleWindows(true); // This forces ChromeClient enabled.

        binding.webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) { //...page is fully loaded.
                    // TODO - Add whatever code you need here based on web page load completion...
                    binding.shimmerViewContainer.stopShimmer();
                    binding.layoutLoading.setVisibility(View.GONE);
                }
            }
        });

        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    if (url.startsWith("app://login")) {
                        backLink = new UrlQuerySanitizer(url).getValue("backlink");
                        LoginAndRegisterActivityNew.startScreen(mActivity, 0, false, true, true);

                    }else if(url.startsWith("app://vtvtravel-help")) {
                        DataHelpCenter dataHelpCenter = new Gson().fromJson(F2Util.loadJSONFromAsset(mActivity, "helpcenter_pro"), DataHelpCenter.class);
                        MyGiftActivity.startScreen(mActivity, dataHelpCenter.getItemMenus(), dataHelpCenter.getName());
                    }else if(url.startsWith("app://vtvtravel-voucherforbooking")) {
                        TravelVoucherActivity.openScreen(mActivity, false, TravelVoucherActivity.OpenType.LIST, false);
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
            binding.webView.loadUrl(genLink(), extraHeaders);
        } else {
            binding.webView.loadUrl(genLink());
//            Toast.makeText(mActivity, "Bạn cần đăng nhập để sử dụng chức năng này!", Toast.LENGTH_SHORT).show();
        }

    }

    private String genLink() {
        return link;
    }

    private String genLinkWithBackLink(){
        return backLink;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        binding.webView.clearCache(true);
        binding.webView.clearHistory();
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
            binding.webView.loadUrl(genLinkWithBackLink(), extraHeaders);
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
            binding.webView.clearCache(true);
            binding.webView.clearHistory();
            binding.webView.loadUrl(genLink());
//            binding.webView.loadDataWithBaseURL("", loginHtml, "text/html", "UTF-8", null);
        }

    }


}
