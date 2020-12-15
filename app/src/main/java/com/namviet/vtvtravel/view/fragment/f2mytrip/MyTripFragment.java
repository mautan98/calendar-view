package com.namviet.vtvtravel.view.fragment.f2mytrip;

import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentMyTripBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.view.f2.DisplayMarkerForMapActivity;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;

import java.util.HashMap;
import java.util.Map;

public class MyTripFragment extends BaseFragment<F2FragmentMyTripBinding> {
    Map<String, String> extraHeaders = new HashMap<>();
    public MyTripFragment() {
    }

    private String link = WSConfig.HOST_MY_TRIP;
    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_my_trip;
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
                if(url.toUpperCase().equals("APP://EXIT")){
                    mActivity.onBackPressed();
                } else if (url.toUpperCase().startsWith("APP://DETAIL")) {
                    try {
                        String link = new UrlQuerySanitizer(url).getValue("link");
                        SmallLocationActivity.startScreenDetail(mActivity, SmallLocationActivity.OpenType.DETAIL, link);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(url.toUpperCase().startsWith("APP://MAP")){
                    try {
                        String link = new UrlQuerySanitizer(url).getValue("link");
                        DisplayMarkerForMapActivity.startScreen(mActivity, link);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(url.toUpperCase().equals("APP://CALL")){
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
                return link  + "?lat=" + lat + "&long=" + lng +"&channel=android&langCode=vi"+"&backLink="+"true";
            } else if (null != account && account.isLogin()) {
                extraHeaders.put("token",account.getToken());
                return link  +"?channel=android&langCode=vi"+"&backLink="+"true";
            }else {
                return link +"?channel=android&langCode=vi"+"&backLink="+"true";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return link +"?channel=android&langCode=vi"+"&backLink="+"true";
        }
    }
}
