package com.namviet.vtvtravel.view.fragment.f2livetv;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Toast;

import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2livetv.ChannelLiveTVAdapter;
import com.namviet.vtvtravel.adapter.f2livetv.ScheduleLiveTVAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentDetailLivetvBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.response.f2livetv.LiveTvResponse;
import com.namviet.vtvtravel.view.f2.FullVideoActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LiveTVFragment extends BaseFragment<F2FragmentDetailLivetvBinding> implements ChannelLiveTVAdapter.ClickButton, ListChannelDialog.ClickChannel {
    private LiveTvResponse response;

    private ChannelLiveTVAdapter channelLiveTVAdapter;
    private ScheduleLiveTVAdapter scheduleLiveTVAdapter;

    private List<LiveTvResponse.Channel> channelList = new ArrayList<>();
    private List<LiveTvResponse.Channel.Schedule> scheduleList = new ArrayList<>();

    private int position = 0;
    private String urlVideo = "";

    public LiveTVFragment() {
    }

    @SuppressLint("ValidFragment")
    public LiveTVFragment(LiveTvResponse response, int position) {
        this.response = response;
        this.position = position;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_detail_livetv;
    }

    @Override
    public void initView() {
        channelList = response.getItems();

        channelLiveTVAdapter = new ChannelLiveTVAdapter(mActivity, channelList, this);
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
//                .file("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8")
                .build();
        getBinding().jwplayer.load(pi);
        getBinding().jwplayer.play();

        urlVideo = response.getItems().get(position).getStreaming_urls().get(0).getUrl();
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
//                .file("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8")
                .build();
        getBinding().jwplayer.load(pi);
        getBinding().jwplayer.play();

        urlVideo = response.getItems().get(position).getStreaming_urls().get(0).getUrl();
    }
}
