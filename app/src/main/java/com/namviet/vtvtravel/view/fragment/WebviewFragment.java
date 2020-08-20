package com.namviet.vtvtravel.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.RobotoTextView;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.ApiClient;
import com.namviet.vtvtravel.api.ApiInterface;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.model.LicensePhoto;
import com.namviet.vtvtravel.ultils.DateUtltils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebviewFragment extends MainFragment {
    public static final String URL_WEBVIEW = "url_webview";

    private WebView mWebView;
    private RobotoTextView mTimeTxt;
    private RobotoTextView mViewTxt;
    private String mUrl;
    private ImageView mBack;
    private TextView mTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mUrl = getArguments().getString(URL_WEBVIEW);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        mWebView = view.findViewById(R.id.web_view);
        mTimeTxt = view.findViewById(R.id.txt_time);
        mViewTxt = view.findViewById(R.id.txt_view);
        mBack = view.findViewById(R.id.imv_back_detail_live_channel);
        mTitle = view.findViewById(R.id.txt_name_detail_live_channel);

        mBack.setOnClickListener(v -> mActivity.onBackPressed());
        mWebView.setWebViewClient(new WebViewClient());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = mUrl.substring(WSConfig.HOST.length() + WSConfig.Api.GET_LICENSE_PHOTO.length() - 1);
        initData(url);
    }

    private void initData(String url) {
        ApiInterface apiInterface = ApiClient.getClientAuthentication().create(ApiInterface.class);
        Call<LicensePhoto> call = apiInterface.getLicensePhotoData(url);
        call.enqueue(new Callback<LicensePhoto>() {
            @Override
            public void onResponse(Call<LicensePhoto> call, Response<LicensePhoto> response) {
                if (response.body() != null) {
                    LicensePhoto.LicensePhotoData body = response.body().getData();
                    if (null != body) {
                        mWebView.loadData(body.getDescription(), "text/html; charset=utf-8", "UTF-8");
                        mTimeTxt.setText(DateUtltils.timeToString(body.getCreated()));
                        mViewTxt.setText(String.valueOf(body.getView_count()));
                        mTitle.setText(body.getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<LicensePhoto> call, Throwable t) {

            }
        });
    }
}
