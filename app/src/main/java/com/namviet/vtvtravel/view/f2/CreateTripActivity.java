package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;

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
}
