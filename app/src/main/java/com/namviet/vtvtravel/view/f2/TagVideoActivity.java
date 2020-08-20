package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityTagVideoBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2video.TagVideoFragment;

public class TagVideoActivity extends BaseActivityNew<F2ActivityTagVideoBinding> {
    private String tag = "";

    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_tag_video;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        tag = getIntent().getStringExtra(Constants.IntentKey.KEY_TAG);
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        TagVideoFragment fragment = new TagVideoFragment();
        fragment.setTag(tag);
        return fragment;
    }

    public static void startScreen(Activity activity, String tag) {
        Intent intent = new Intent(activity, TagVideoActivity.class);
        intent.putExtra(Constants.IntentKey.KEY_TAG, tag);
        activity.startActivity(intent);
    }
}
