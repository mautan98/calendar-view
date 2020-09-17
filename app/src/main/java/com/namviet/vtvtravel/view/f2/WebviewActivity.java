package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2ActivityDealWebviewBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2webview.WebviewFragment;

public class WebviewActivity extends BaseActivityNew<F2ActivityDealWebviewBinding> {

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
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public BaseFragment initFragment() {
        WebviewFragment vqmmWebviewFragment = new WebviewFragment();
        return vqmmWebviewFragment;
    }

    public static void startScreen(Context activity) {
        Intent intent = new Intent(activity, WebviewActivity.class);
        activity.startActivity(intent);
    }
}
