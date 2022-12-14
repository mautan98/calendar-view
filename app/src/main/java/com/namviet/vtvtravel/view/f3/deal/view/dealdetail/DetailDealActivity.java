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
    private String idCampaignDetail;
    private boolean isCampaign;
    private boolean isFromHome;
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
        idCampaignDetail = getIntent().getStringExtra(Constants.IntentKey.DATA);
        isCampaign = getIntent().getBooleanExtra(Constants.IntentKey.IS_CAMPAIGN,true);
        isFromHome = getIntent().getBooleanExtra(Constants.IntentKey.IS_FROM_HOME,false);
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        DealItemDetailFragment dealItemDetailFragment  = new DealItemDetailFragment(idCampaignDetail,isCampaign,isFromHome);
        return dealItemDetailFragment;
    }
    public static void startScreen(Context activity, String id,boolean isCampaign) {
        Intent intent = new Intent(activity, DetailDealActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, id);
        intent.putExtra(Constants.IntentKey.IS_CAMPAIGN, isCampaign);
        intent.putExtra(Constants.IntentKey.IS_FROM_HOME, false);
        activity.startActivity(intent);
    }
    public static void startScreen(Context activity, String id,boolean isCampaign, boolean isFromHome) {
        Intent intent = new Intent(activity, DetailDealActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, id);
        intent.putExtra(Constants.IntentKey.IS_CAMPAIGN, isCampaign);
        intent.putExtra(Constants.IntentKey.IS_FROM_HOME, isFromHome);
        activity.startActivity(intent);
    }
}
