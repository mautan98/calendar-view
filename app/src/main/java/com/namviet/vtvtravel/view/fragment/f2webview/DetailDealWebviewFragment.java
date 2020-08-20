package com.namviet.vtvtravel.view.fragment.f2webview;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2ActivityDealWebviewBinding;
import com.namviet.vtvtravel.databinding.F2FragmentDetailDealBinding;
import com.namviet.vtvtravel.databinding.F2FragmentDetailDealWebviewBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceActivity;

public class DetailDealWebviewFragment extends BaseFragment<F2FragmentDetailDealWebviewBinding> {
    private String link;

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_detail_deal_webview;
    }

    @Override
    public void initView() {
        getBinding().webView.loadUrl(link);

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
}
