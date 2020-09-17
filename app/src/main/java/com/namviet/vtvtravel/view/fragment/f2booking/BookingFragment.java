package com.namviet.vtvtravel.view.fragment.f2booking;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentBookingBinding;
import com.namviet.vtvtravel.databinding.F2FragmentVideoBinding;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndGoToBooking;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndUpdateUserView;
import com.namviet.vtvtravel.response.f2video.VideoResponse;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceActivity;
import com.namviet.vtvtravel.view.fragment.f2video.SubVideoFragment;
import com.namviet.vtvtravel.viewmodel.f2video.VideoViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Observable;
import java.util.Observer;

public class BookingFragment extends MainFragment {
    private F2FragmentBookingBinding binding;
    private String link = WSConfig.HOST_BOOKING;
//    private String link = "http://103.21.148.54:8857/get-list?token=";
    private String token;

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

        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            token = account.getToken();
            binding.webView.loadUrl(genLink());
        } else {
            Toast.makeText(mActivity, "Bạn cần đăng nhập để sử dụng chức năng này!", Toast.LENGTH_SHORT).show();
        }

    }

    private String genLink() {
        return link + token;
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
    public void OnReload(OnLoginSuccessAndGoToBooking onLoginSuccessAndGoToBooking){
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            token = account.getToken();
            binding.webView.loadUrl(genLink());
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
            binding.webView.loadDataWithBaseURL("", loginHtml, "text/html", "UTF-8", null);
        }

    }
}
