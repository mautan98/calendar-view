package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityDetailVideoBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.response.travelnews.DetailTravelNewsResponse;
import com.namviet.vtvtravel.view.fragment.f2video.DetailVideoFragment;

public class DetailVideoActivity extends BaseActivityNew<F2ActivityDetailVideoBinding> {
    private String videoLink;
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_detail_video;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        videoLink =  getIntent().getStringExtra(Constants.IntentKey.DATA_VIDEO);

    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        DetailVideoFragment detailVideoFragment = new DetailVideoFragment();
        detailVideoFragment.setVideo(videoLink);
        return detailVideoFragment;
    }
    public static void startScreen(Context activity, String videoLink){
        Intent intent = new Intent(activity, DetailVideoActivity.class);
        intent.putExtra(Constants.IntentKey.DATA_VIDEO, videoLink);
        activity.startActivity(intent);
    }
}
