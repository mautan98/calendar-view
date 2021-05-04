package com.namviet.vtvtravel.view.fragment.f2livetv;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longtailvideo.jwplayer.events.FullscreenEvent;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2livetv.ChannelLiveTVAdapter;
import com.namviet.vtvtravel.adapter.f2livetv.ScheduleLiveTVAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentDetailLivetvBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.response.CityResponse;
import com.namviet.vtvtravel.response.WeatherResponse;
import com.namviet.vtvtravel.response.f2livetv.LiveTvResponse;
import com.namviet.vtvtravel.response.newhome.BaseResponseNewHome;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f2.FullVideoActivity;
import com.namviet.vtvtravel.viewmodel.f2livetv.LiveTvViewModel;
import com.namviet.vtvtravel.viewmodel.newhome.NewHomeViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class LiveTVFragment extends BaseFragment<F2FragmentDetailLivetvBinding> implements ChannelLiveTVAdapter.ClickButton, ListChannelDialog.ClickChannel, Observer , VideoPlayerEvents.OnFullscreenListener{
    private LiveTvResponse response;

    private ChannelLiveTVAdapter channelLiveTVAdapter;
    private ScheduleLiveTVAdapter scheduleLiveTVAdapter;

    private List<LiveTvResponse.Channel> channelList = new ArrayList<>();
    private List<LiveTvResponse.Channel.Schedule> scheduleList = new ArrayList<>();

    private int position = 0;
    private String urlVideo = "";

    private String detailLink;


    private LiveTvViewModel liveTvViewModel;

    public LiveTVFragment() {
    }

    @SuppressLint("ValidFragment")
    public LiveTVFragment(LiveTvResponse response, int position) {
        this.response = response;
        this.position = position;
    }

    @SuppressLint("ValidFragment")
    public LiveTVFragment(String detailLink) {
        this.detailLink = detailLink;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_detail_livetv;
    }

    @Override
    public void initView() {
        if (response == null) {
            initViewModel();
        } else {
            setData();
        }

    }

    private void setData() {
        try {
            getBinding().jwplayer.setFullscreen(false, false);
            channelList = response.getItems();

            channelLiveTVAdapter = new ChannelLiveTVAdapter(mActivity, channelList, this, position);
            getBinding().rclChannel.setAdapter(channelLiveTVAdapter);

            scheduleLiveTVAdapter = new ScheduleLiveTVAdapter(mActivity, scheduleList);
            getBinding().rclSchedule.setAdapter(scheduleLiveTVAdapter);

            scheduleList.clear();
            for (LiveTvResponse.Channel.Schedule schedule : response.getItems().get(position).getSchedule()) {
                scheduleList.add(schedule);
            }
            Collections.reverse(scheduleList);
            scheduleLiveTVAdapter.notifyDataSetChanged();

            getBinding().tvTitle.setText("Lịch phát sóng trên " + response.getItems().get(position).getName());
            getBinding().tvToday.setText(response.getItems().get(position).getDate());

            PlaylistItem pi = new PlaylistItem.Builder()
                    .file(response.getItems().get(position).getStreaming_urls().get(0).getUrl())
                    .build();
            getBinding().jwplayer.load(pi);
            getBinding().jwplayer.play();

            urlVideo = response.getItems().get(position).getStreaming_urls().get(0).getUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getBinding().shimmerMain.setVisibility(View.GONE);
    }


    private void initViewModel() {
        liveTvViewModel = new LiveTvViewModel();
        getBinding().setNewHomeViewModel(liveTvViewModel);
        liveTvViewModel.addObserver(this);
        liveTvViewModel.getLiveTvData(detailLink);
    }

    @Override
    public void initData() {

    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListChannelDialog listChannelDialog = new ListChannelDialog(mActivity, channelList, LiveTVFragment.this);
                listChannelDialog.show(getChildFragmentManager(), null);
            }
        });
        getBinding().imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
        getBinding().imgFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBinding().jwplayer.pause();
                FullVideoActivity.openScreen(mActivity, urlVideo);
            }
        });
    }

    @Override
    public void setObserver() {
    }

    @Override
    public void clickChannel(int position) {
        updateViews(position);
    }

    @Override
    public void clickItem(int position) {
        updateViews(position);
    }

    private void updateViews(int position) {
        try {
            getBinding().tvTitle.setText("Lịch phát sóng trên " + response.getItems().get(position).getName());
            getBinding().tvToday.setText(response.getItems().get(position).getDate());

            scheduleList.clear();
            for (LiveTvResponse.Channel.Schedule schedule : channelList.get(position).getSchedule()) {
                scheduleList.add(schedule);
            }
            Collections.reverse(scheduleList);
            scheduleLiveTVAdapter.notifyDataSetChanged();

            PlaylistItem pi = new PlaylistItem.Builder()
                    .file(response.getItems().get(position).getStreaming_urls().get(0).getUrl())
                    .build();
            getBinding().jwplayer.load(pi);
            getBinding().jwplayer.addOnFullscreenListener(this);
            getBinding().jwplayer.play();

            urlVideo = response.getItems().get(position).getStreaming_urls().get(0).getUrl();

            try {
                channelLiveTVAdapter.setPositionSelected(position);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getBinding().jwplayer != null)
            getBinding().jwplayer.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getBinding().jwplayer != null) {
            getBinding().jwplayer.onResume();
//            getBinding().jwplayer.play();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getBinding().jwplayer != null)
            getBinding().jwplayer.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (getBinding().jwplayer != null)
            getBinding().jwplayer.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getBinding().jwplayer != null)
            getBinding().jwplayer.onDestroy();
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof LiveTvViewModel && null != o) {
            if (o instanceof BaseResponseNewHome) {
                BaseResponseNewHome response = (BaseResponseNewHome) o;
                LiveTVFragment.this.response = new Gson().fromJson(new Gson().toJson(response.getData()), LiveTvResponse.class);
                setData();
            }
        }
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.LIVE_TV_DETAIL, TrackingAnalytic.ScreenTitle.LIVE_TV_DETAIL);
    }

    @Override
    public void onFullscreen(FullscreenEvent fullscreenEvent) {
        if (fullscreenEvent.getFullscreen()) {
            getBinding().jwplayer.setFullscreen(true, true);
        } else {
            getBinding().jwplayer.setFullscreen(false, true);
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        try {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                getBinding().jwplayer.setFullscreen(true, true);
            } else {
                getBinding().jwplayer.setFullscreen(false, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
