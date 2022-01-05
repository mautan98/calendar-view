package com.namviet.vtvtravel.view.fragment.f2account;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentRulesBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.f2account.HtmlResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.viewmodel.f2account.AccountViewModel;

import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

public class RulesFragment extends BaseFragment<F2FragmentRulesBinding> implements Observer {

    @Inject
    AccountViewModel accountViewModel;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_rules;
    }

    @Override
    public void initView() {
        accountViewModel.addObserver(this);
        accountViewModel.getUsageRule();
    }

    @Override
    public void initData() {
        WebSettings webSettings = getBinding().webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setGeolocationEnabled(true);
        webSettings.setSupportMultipleWindows(true); // This forces ChromeClient enabled.

        getBinding().webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.toUpperCase().equals("APP://EXIT")) {
                    mActivity.onBackPressed();
                } else {
                    view.loadUrl(url);
                }

                return true;
            }
        });
    }

    @Override
    public void inject() {
        ((MyApplication)mActivity.getApplication()).getViewModelComponent().inject(this);
    }

    @Override
    public void setClickListener() {
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });


    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof AccountViewModel && null != o) {
            if (o instanceof HtmlResponse) {
                HtmlResponse htmlResponse = (HtmlResponse) o;
                getBinding().webView.setVisibility(View.VISIBLE);
                getBinding().webView.loadDataWithBaseURL("", htmlResponse.getData().getDescription(), "text/html", "UTF-8", null);
            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
            }

        }
    }


    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.RULES, TrackingAnalytic.ScreenTitle.RULES);
    }
}
