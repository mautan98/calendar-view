package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityDealWebviewBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2webview.OldVQMMWebviewFragment;
import com.namviet.vtvtravel.view.fragment.f2webview.VQMMWebviewFragment;

public class VQMMWebviewActivity extends BaseActivityNew<F2ActivityDealWebviewBinding> {
    private String voucherId;

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
        voucherId = getIntent().getStringExtra(Constants.IntentKey.DATA);
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
//        OldVQMMWebviewFragment vqmmWebviewFragment = new OldVQMMWebviewFragment(voucherId);
//        VQMMWebviewFragment vqmmWebviewFragment = new VQMMWebviewFragment(voucherId);
        VQMMWebviewFragment vqmmWebviewFragment = new VQMMWebviewFragment();
        return vqmmWebviewFragment;
    }

    public static void startScreen(Context activity, String voucherId) {
        Intent intent = new Intent(activity, VQMMWebviewActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, voucherId);
        activity.startActivity(intent);
    }

    @Override
    public void afterSetContentView() {
        super.afterSetContentView();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
