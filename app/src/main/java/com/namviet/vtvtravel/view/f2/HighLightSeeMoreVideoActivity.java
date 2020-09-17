package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityHighLightSeeMoreVideoBinding;
import com.namviet.vtvtravel.databinding.F2ActivityTagVideoBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2video.HighLightSeeMoreVideoFragment;
import com.namviet.vtvtravel.view.fragment.f2video.TagVideoFragment;

public class HighLightSeeMoreVideoActivity extends BaseActivityNew<F2ActivityHighLightSeeMoreVideoBinding> {
    private String regionName = "";
    private String regionId = "";
    private String detailLink = "";

    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_high_light_see_more_video;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        regionName = getIntent().getStringExtra(Constants.IntentKey.DATA);
        detailLink = getIntent().getStringExtra(Constants.IntentKey.DETAIL_LINK);
        regionId = getIntent().getStringExtra(Constants.IntentKey.REGION_ID);
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        return new HighLightSeeMoreVideoFragment(regionName, regionId,  detailLink);
    }

    public static void startScreen(Context activity, String regionName, String regionId, String detailLink) {
        Intent intent = new Intent(activity, HighLightSeeMoreVideoActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, regionName);
        intent.putExtra(Constants.IntentKey.DETAIL_LINK, detailLink);
        intent.putExtra(Constants.IntentKey.REGION_ID, regionId);
        activity.startActivity(intent);
    }
}
