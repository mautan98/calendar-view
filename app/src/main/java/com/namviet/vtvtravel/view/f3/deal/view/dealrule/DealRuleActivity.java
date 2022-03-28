package com.namviet.vtvtravel.view.f3.deal.view.dealrule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.namviet.vtvtravel.R;

import com.namviet.vtvtravel.databinding.ActivityDealRuleBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;

public class DealRuleActivity extends BaseActivityNew<ActivityDealRuleBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_deal_rule;
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
        return new DealRuleFragment();
    }

    public static void startScreen(Context activity) {
        Intent intent = new Intent(activity, DealRuleActivity.class);
        activity.startActivity(intent);
    }
}
