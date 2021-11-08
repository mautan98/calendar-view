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

import java.util.ArrayList;
import java.util.List;


public class PopupBannerDealActivity extends BaseActivityNew<ActivityDetailDealBinding> {
    private ArrayList<String> urls;
    private int positionSelected;
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
        urls = getIntent().getStringArrayListExtra(Constants.IntentKey.DATA);
        positionSelected = getIntent().getIntExtra(Constants.IntentKey.POSITION,0);
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        return new ViewImageFragment(urls,positionSelected);
    }
    public static void startScreen(Context activity, ArrayList<String> link,int position) {
        Intent intent = new Intent(activity, PopupBannerDealActivity.class);
        intent.putStringArrayListExtra(Constants.IntentKey.DATA, link);
        intent.putExtra(Constants.IntentKey.POSITION, position);
        activity.startActivity(intent);
    }
}
