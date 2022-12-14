package com.namviet.vtvtravel.view.fragment.f2livetv;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longtailvideo.jwplayer.events.ControlBarVisibilityEvent;
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
import com.namviet.vtvtravel.ultils.DateUtltils;
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
    private ChannelLiveTVAdapter channelLiveTVAdapterAboveTV;
    private ScheduleLiveTVAdapter scheduleLiveTVAdapter;

    private List<LiveTvResponse.Channel> channelList = new ArrayList<>();
    private List<LiveTvResponse.Channel.Schedule> scheduleList = new ArrayList<>();

    private int position = 0;
    private String urlVideo = "";

    private String detailLink;


    private LiveTvViewModel liveTvViewModel;

    private boolean isFullScreen = false;

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

            channelLiveTVAdapter = new ChannelLiveTVAdapter(mActivity, channelList, this, position, 0);
            channelLiveTVAdapterAboveTV = new ChannelLiveTVAdapter(mActivity, channelList, this, position, 1);
            getBinding().rclChannel.setAdapter(channelLiveTVAdapter);
            getBinding().rclChannel2.setAdapter(channelLiveTVAdapterAboveTV);

            scheduleLiveTVAdapter = new ScheduleLiveTVAdapter(mActivity, scheduleList);
            getBinding().rclSchedule.setAdapter(scheduleLiveTVAdapter);

            scheduleList.clear();
            for (LiveTvResponse.Channel.Schedule schedule : response.getItems().get(position).getSchedule()) {
                scheduleList.add(schedule);
            }
            Collections.reverse(scheduleList);
            scheduleLiveTVAdapter.notifyDataSetChanged();

            getBinding().tvTitle.setText("L???ch ph??t s??ng tr??n " + response.getItems().get(position).getName());
            getBinding().tvToday.setText(response.getItems().get(position).getDate());

            getRunningObjectAndSetText(position);

            PlaylistItem pi = new PlaylistItem.Builder()
                    .file(response.getItems().get(position).getStreaming_urls().get(0).getUrl())
                    .build();
            getBinding().jwplayer.load(pi);
            getBinding().jwplayer.addOnFullscreenListener(this);
            getBinding().jwplayer.addOnControlBarVisibilityListener(new VideoPlayerEvents.OnControlBarVisibilityListener() {
                @Override
                public void onControlBarVisibilityChanged(ControlBarVisibilityEvent controlBarVisibilityEvent) {
                    if(isFullScreen) {
                        if (controlBarVisibilityEvent.isVisible()) {
                            getBinding().rclChannel2.setVisibility(View.VISIBLE);
                        } else {
                            getBinding().rclChannel2.setVisibility(View.GONE);
                        }
                    }else {
                        getBinding().rclChannel2.setVisibility(View.GONE);
                    }
                }
            });
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

    float x1, x2;

    @Override
    public void initData() {
        getBinding().viewMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        float deltaX = x2 - x1;

                        if (Math.abs(deltaX) > 150)
                        {
                            // Left to Right swipe action
                            if (x2 > x1)
                            {
//                                Toast.makeText(mActivity, "Left to Right swipe [Next]", Toast.LENGTH_SHORT).show ();
                            }

                            // Right to left swipe action
                            else
                            {
//                                Toast.makeText(mActivity, "Right to Left swipe [Previous]", Toast.LENGTH_SHORT).show ();
                            }

                        }
                        else
                        {
                            // consider as something else - a screen tap for example
                        }
                        break;
                }
                return true;
            }
        });
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
//        getBinding().imgFullScreen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getBinding().jwplayer.pause();
//                FullVideoActivity.openScreen(mActivity, urlVideo);
//            }
//        });
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
            getBinding().tvTitle.setText("L???ch ph??t s??ng tr??n " + response.getItems().get(position).getName());
            getBinding().tvToday.setText(response.getItems().get(position).getDate());

            getRunningObjectAndSetText(position);

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
            getBinding().jwplayer.addOnControlBarVisibilityListener(new VideoPlayerEvents.OnControlBarVisibilityListener() {
                @Override
                public void onControlBarVisibilityChanged(ControlBarVisibilityEvent controlBarVisibilityEvent) {
                    if(isFullScreen) {
                        if (controlBarVisibilityEvent.isVisible()) {
                            getBinding().rclChannel2.setVisibility(View.VISIBLE);
                        } else {
                            getBinding().rclChannel2.setVisibility(View.GONE);
                        }
                    }else {
                        getBinding().rclChannel2.setVisibility(View.GONE);
                    }
                }
            });
            getBinding().jwplayer.play();

            urlVideo = response.getItems().get(position).getStreaming_urls().get(0).getUrl();

            if(isFullScreen) {
                isFullScreen = true;
                getBinding().jwplayer.setFullscreen(true, true);
            }

            try {
                channelLiveTVAdapter.setPositionSelected(position);
                channelLiveTVAdapterAboveTV.setPositionSelected(position);
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
                isFullScreen = true;
                getBinding().jwplayer.setFullscreen(true, true);
            } else {
                isFullScreen = false;
                getBinding().jwplayer.setFullscreen(false, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void getRunningObjectAndSetText(int position){
        try {
            Long tsLong = System.currentTimeMillis()/1000;
            int size = response.getItems().get(position).getSchedule().size();
            for (int i = 0; i < size; i++) {
                if(tsLong > response.getItems().get(position).getSchedule().get(i).getStart_time()
                && tsLong < response.getItems().get(position).getSchedule().get(i).getEnd_time()){
                    getBinding().tvRunningCategory.setText(response.getItems().get(position).getSchedule().get(i).getTopic());
                    getBinding().tvRunningName.setText(response.getItems().get(position).getSchedule().get(i).getName());
                    getBinding().tvRunningTime.setText(DateUtltils.timeToString2(response.getItems().get(position).getSchedule().get(i).getStart_time()));

                    scheduleLiveTVAdapter.highLight(size - i - 1);
                    runTimer(getRangeTime(tsLong, response.getItems().get(position).getSchedule().get(i).getEnd_time())+20);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CountDownTimer timer;
    private void runTimer(long second){
        try {
            timer = new CountDownTimer(second*1000, second*1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.e("hihihi", second+"");
                }

                @Override
                public void onFinish() {
                    getRunningObjectAndSetText(position);
                }
            };
            timer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long getRangeTime(long startTime, long endTime){
        return endTime - startTime;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            timer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
