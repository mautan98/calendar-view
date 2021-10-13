package com.namviet.vtvtravel.view.f3.deal.view.dealdetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.ActivityDetailDealBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;


public class DetailDealActivity extends BaseActivityNew<ActivityDetailDealBinding> {
    private String link;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_item_detail_deal;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
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

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        DealItemDetailFragment dealItemDetailFragment  = new DealItemDetailFragment(link);
        return dealItemDetailFragment;
    }
    public static void startScreen(Context activity, String link) {
        Intent intent = new Intent(activity, DetailDealActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, link);
        activity.startActivity(intent);
    }
}
