package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2ActivityLivetvBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.response.f2livetv.LiveTvResponse;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.fragment.f2livetv.LiveTVFragment;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class LiveTVActivity extends BaseActivityNew<F2ActivityLivetvBinding> {
    private static final String CHANNEL_LIST = "CHANNEL_LIST";
    private static final String CHANNEL_POSITION = "CHANNEL_POSITION";
    private LiveTvResponse liveTvResponse;
    private int position;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_livetv;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        liveTvResponse = (LiveTvResponse) getIntent().getSerializableExtra(CHANNEL_LIST);
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        LiveTVFragment liveTVFragment = new LiveTVFragment(liveTvResponse, position);
        return liveTVFragment;
    }

    public static void openScreen(Context activity, LiveTvResponse response, int position) {
        Intent intent = new Intent(activity, LiveTVActivity.class);
        intent.putExtra(CHANNEL_LIST, response);
        intent.putExtra(CHANNEL_POSITION, position);
        activity.startActivity(intent);
    }
}
