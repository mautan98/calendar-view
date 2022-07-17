package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.view.fragment.f2createtrip.CreateTripFragment;

public class CreateTripActivity extends BaseActivityNew {
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_create_trip;
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
        return new CreateTripFragment();
    }

    public static void startScreen(Context activity){
        Intent intent = new Intent(activity, CreateTripActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void afterSetContentView() {
        super.afterSetContentView();
        getWindow().getDecorView().setSystemUiVisibility (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
}
