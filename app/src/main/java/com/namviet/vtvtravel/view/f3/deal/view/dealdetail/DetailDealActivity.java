package com.namviet.vtvtravel.view.f3.deal.view.dealdetail;

import android.content.Context;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.ActivityDetailDealBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;


public class DetailDealActivity extends BaseActivityNew<ActivityDetailDealBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.activity_detail_deal;
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
    public BaseFragment initFragment() {
        return null;
    }

    public static void startScreen(Context activity){
        Intent intent = new Intent(activity, DetailDealActivity.class);
        activity.startActivity(intent);
    }
}
