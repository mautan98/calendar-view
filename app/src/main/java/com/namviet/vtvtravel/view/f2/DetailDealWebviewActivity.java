package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityDealWebviewBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.view.fragment.f2webview.DetailDealWebviewFragment;

public class DetailDealWebviewActivity extends BaseActivityNew<F2ActivityDealWebviewBinding> {
    private String link;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_deal_webview;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        link = getIntent().getStringExtra(Constants.IntentKey.DATA);
    }

    @Override
    public void doAfterOnCreate() {
//        getBinding().webView.loadUrl(link);
//
//        getBinding().webView.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//                if (progress == 100) { //...page is fully loaded.
//                    // TODO - Add whatever code you need here based on web page load completion...
//                    getBinding().shimmerViewContainer.stopShimmer();
//                    getBinding().shimmerViewContainer.setVisibility(View.GONE);
//                }
//            }
//        });

//        getBinding().webView.setWebViewClient(new WebViewClient() {
//            public void onPageFinished(WebView view, String url) {
//                // do your stuff here
//                try {
//                    getBinding().shimmerViewContainer.stopShimmer();
//                    getBinding().shimmerViewContainer.setVisibility(View.GONE);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    @Override
    public void setClick() {
//        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (getBinding().webView.canGoBack()) {
//                    getBinding().webView.goBack();
//                } else {
//                    finish();
//                }
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (getBinding().webView.canGoBack()) {
//            getBinding().webView.goBack();
//        } else {
//            finish();
//        }
    }

    @Override
    public BaseFragment initFragment() {
        DetailDealWebviewFragment detailDealWebviewFragment = new DetailDealWebviewFragment();
        detailDealWebviewFragment.setLink(link);
        return detailDealWebviewFragment;
    }

    public static void startScreen(Context activity, String link) {
        Intent intent = new Intent(activity, DetailDealWebviewActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, link);
        activity.startActivity(intent);
    }
}
