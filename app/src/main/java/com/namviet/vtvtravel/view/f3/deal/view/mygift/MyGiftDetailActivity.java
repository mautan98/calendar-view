package com.namviet.vtvtravel.view.f3.deal.view.mygift;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.ActivityMyGiftDetailBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.f3.deal.view.dealdetail.DetailDealActivity;

public class MyGiftDetailActivity extends BaseActivityNew<ActivityMyGiftDetailBinding> {
    private String type = Type.CARD_TYPE;
    public static class Type{
        public static String CARD_TYPE = "CARD_TYPE";
        public static String VOUCHER_TYPE = "VOUCHER_TYPE";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_my_gift_detail;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        type = getIntent().getStringExtra("Type");
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        if(type.equals( Type.CARD_TYPE)){
            return new CardDetailFragment();
        }else {
            return new VoucherDetailFragment();
        }

    }

    public static void openScreen(Context activity, String type){
        Intent intent = new Intent(activity, MyGiftDetailActivity.class);
        intent.putExtra("Type", type);
        activity.startActivity(intent);
    }
}
