package com.namviet.vtvtravel.view.fragment.travel;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseapp.menu.SlideMenu;
import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.events.FullscreenEvent;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.DetailLiveChannelAdapter;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.help.LiveChannelEvent;
import com.namviet.vtvtravel.help.SnappingLinearLayoutManager;
import com.namviet.vtvtravel.model.DetailLiveChannel;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.ultils.JWEventHandler;
import com.namviet.vtvtravel.ultils.KeepScreenOnHandler;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.DetailLiveChannelViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class DetailLiveChannelFragment extends MainFragment implements VideoPlayerEvents.OnFullscreenListener {
    private Button mGoLiveChannelBtn;
    private ImageView mBackDetailLiveChannelImv;
    private DetailLiveChannelViewModel mDetailLiveChannelViewModel;
    private List<DetailLiveChannel> mDetailLiveChannelList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private DetailLiveChannelAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private TextView mNameDetailLiveChannel;
    private TextView mDateDetailLiveChannel;
    private TextView mCalendarDetailLiveChannelTxt;

    private long mStartTimeItem;
    private long mEndTimeItem;
    private long mCurrentTime;
    private ConstraintLayout mConstraintLayout;

    //for JWPlayer
    private JWPlayerView mPlayerView;
    private JWEventHandler mEventHandler;

    private Handler mHandler = new Handler();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe
    public void onLiveChannelEvent(LiveChannelEvent liveChannelEvent) {
        //String a = liveChannelEvent.getmLiveChannel().getDetail_link();
        mNameDetailLiveChannel.setText(liveChannelEvent.getmLiveChannel().getName());
        mCalendarDetailLiveChannelTxt.setText("Lịch phát sóng trên " + liveChannelEvent.getmLiveChannel().getName());
        if (liveChannelEvent.getmLiveChannel().getName() != null) {
            setContentDetailLiveChannel(liveChannelEvent.getmLiveChannel().getName());
        } else {
            setContentDetailLiveChannel("VTV1");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_live_channel, container, false);

        try {
            mGoLiveChannelBtn = view.findViewById(R.id.btn_go_live_channel);
            mBackDetailLiveChannelImv = view.findViewById(R.id.imv_back_detail_live_channel);
            mGoLiveChannelBtn.setOnClickListener(this);
            mBackDetailLiveChannelImv.setOnClickListener(this);

            mDateDetailLiveChannel = view.findViewById(R.id.txt_date_detail_live_channel);
            mNameDetailLiveChannel = view.findViewById(R.id.txt_name_detail_live_channel);
            mCalendarDetailLiveChannelTxt = view.findViewById(R.id.txt_calendar_detail_live_channel);

            mRecyclerView = view.findViewById(R.id.recycle_view_detail_live_channel);
            mAdapter = new DetailLiveChannelAdapter(mDetailLiveChannelList, mActivity, this);
            mLinearLayoutManager = new SnappingLinearLayoutManager(getContext());
            //mLinearLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mAdapter);

            mPlayerView = view.findViewById(R.id.jwplayer);
            mConstraintLayout = view.findViewById(R.id.contraint_layout);
            initJWPlayer(WSConfig.URL_DEFAULT_CHANNEL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private Runnable handleRecyclerView = new Runnable() {
        public void run() {
            try {
                mHandler.postDelayed(this, 3000);
                mAdapter.notifyDataSetChanged();
                mCurrentTime = DateUtltils.currentTime();
                int size = mDetailLiveChannelList.size();
                for (int i = 0; i < size; i++) {
                    mStartTimeItem = Long.parseLong(mDetailLiveChannelList.get(i).getStart_time());
                    mEndTimeItem = Long.parseLong(mDetailLiveChannelList.get(i).getEnd_time());
                    if (mStartTimeItem < mCurrentTime && mCurrentTime < mEndTimeItem) {
                        if (i >= 2) {
                            mRecyclerView.smoothScrollToPosition(i - 2);
                        } else {
                            mRecyclerView.smoothScrollToPosition(i);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void initJWPlayer(String url) {
        // Load a media source
        try {
            mPlayerView.addOnFullscreenListener(this);
            new KeepScreenOnHandler(mPlayerView, mActivity.getWindow());
            mEventHandler = new JWEventHandler(mPlayerView);

            // Load a media source
            PlaylistItem pi = new PlaylistItem.Builder()
                    .file("http://113.185.19.133:8443/namvietvtv/smil:vtv1.smil/playlist.m3u8")
                    .build();

            mPlayerView.load(pi);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Get a reference to the CastContext
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Runnable r = () -> setContentDetailLiveChannel("VTV1");
        new Handler().postDelayed(r, 500);
    }

    private void setContentDetailLiveChannel(String channel) {
        try {
            mDetailLiveChannelViewModel = ViewModelProviders.of(this).get(DetailLiveChannelViewModel.class);
            //TODO
            mDetailLiveChannelViewModel.init(channel);
            mDetailLiveChannelViewModel.getMovies().observe(this, detailLiveChannelData -> {
                //TODO
                mDetailLiveChannelList.clear();
                mDateDetailLiveChannel.setText(detailLiveChannelData.getDate());
                mNameDetailLiveChannel.setText(channel);
                mDetailLiveChannelList.addAll(detailLiveChannelData.getData());
                mAdapter.notifyDataSetChanged();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_go_live_channel:
                mActivity.switchFragment(SlideMenu.MenuType.CHANNEL_LIVE_SCREEN);
                break;
            case R.id.imv_back_detail_live_channel:
                mActivity.onBackPressed();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPlayerView != null)
            mPlayerView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(handleRecyclerView, 1000);
        if (mPlayerView != null) {
            mPlayerView.onResume();
            mPlayerView.play();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(handleRecyclerView);
        if (mPlayerView != null)
            mPlayerView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPlayerView != null)
            mPlayerView.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPlayerView != null)
            mPlayerView.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onFullscreen(FullscreenEvent fullscreenEvent) {
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            if (fullscreenEvent.getFullscreen()) {
                actionBar.hide();
            } else {
                actionBar.show();
            }
        }

        // When going to Fullscreen we want to set fitsSystemWindows="false"
        mConstraintLayout.setFitsSystemWindows(!fullscreenEvent.getFullscreen());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // Set fullscreen when the device is rotated to landscape
        mPlayerView.setFullscreen(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE,
                true);
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkFullScreen();
    }

    private void checkFullScreen() {
        if (getView() == null) {
            return;
        }
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mPlayerView.getFullscreen()) {
                        mPlayerView.setFullscreen(false, true);
                    } else {
                        mActivity.onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
