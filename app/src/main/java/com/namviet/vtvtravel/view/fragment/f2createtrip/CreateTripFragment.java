package com.namviet.vtvtravel.view.fragment.f2createtrip;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentCreateTripBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.view.f2.DisplayMarkerForMapActivity;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;
import com.namviet.vtvtravel.view.f2.TravelVoucherActivity;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceActivity;

import java.util.HashMap;
import java.util.Map;


public class CreateTripFragment extends BaseFragment<F2FragmentCreateTripBinding> {
    private String link = WSConfig.HOST_CREATE_TRIP;
    Map<String, String> extraHeaders = new HashMap<>();


    public CreateTripFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_create_trip;
    }

    @Override
    public void initView() {
        getBinding().webView.getSettings().setJavaScriptEnabled(true);

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
                try {
                    Log.e("url", url);
                    if (url.toUpperCase().equals("APP://EXIT")) {
                        mActivity.onBackPressed();
                    } else if (url.toUpperCase().startsWith("APP://DETAIL")) {
                        try {
                            String link = new UrlQuerySanitizer(url).getValue("link");
                            SmallLocationActivity.startScreenDetail(mActivity, SmallLocationActivity.OpenType.DETAIL, link);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if(url.toUpperCase().startsWith("APP://MAP")){
                        try {
                            String link = new UrlQuerySanitizer(url).getValue("link");
                            DisplayMarkerForMapActivity.startScreen(mActivity, link);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if(url.toUpperCase().equals("APP://CALL")){
                        try {
                            String phone = "1039";
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                            mActivity.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else {
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
        getBinding().webView.loadUrl(genLink(), extraHeaders);
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
        try {
            Double lat = null;
            Double lng = null;
            if (null != MyApplication.getInstance().getMyLocation()
                    && MyApplication.getInstance().getMyLocation().getLat() != MyApplication.getInstance().getMyLocation().getLog()) {
                lat = MyApplication.getInstance().getMyLocation().getLat();
                lng = MyApplication.getInstance().getMyLocation().getLog();
            }
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin() && lat != null && lng != null) {
                extraHeaders.put("token",account.getToken());
                return link + "?lat=" + lat + "&long=" + lng + "&backLink=" + "true";
            } else if (null != account && account.isLogin()) {
                extraHeaders.put("token",account.getToken());
                return link + "?backLink=" + "true";
            } else {
                return link + "?backLink=" + "true";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return link;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        getBinding().webView.clearCache(true);
        getBinding().webView.clearHistory();
    }
}
