package com.namviet.vtvtravel.view;

import android.content.res.Configuration;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baseapp.activity.BaseActivity;
import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.configuration.PlayerConfig;
import com.longtailvideo.jwplayer.events.AdClickEvent;
import com.longtailvideo.jwplayer.events.AdCompleteEvent;
import com.longtailvideo.jwplayer.events.AdSkippedEvent;
import com.longtailvideo.jwplayer.events.FullscreenEvent;
import com.longtailvideo.jwplayer.events.listeners.AdvertisingEvents;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;
import com.longtailvideo.jwplayer.media.ads.AdBreak;
import com.longtailvideo.jwplayer.media.ads.AdSource;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.PlaylistVideoAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.listener.VideoSelectListener;
import com.namviet.vtvtravel.model.Ads;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.response.PlaylistVideoResponse;
import com.namviet.vtvtravel.ultils.KeepScreenOnHandler;
import com.namviet.vtvtravel.videomanage.controller.ViewAnimator;
import com.namviet.vtvtravel.videomanage.utils.Utils;
import com.namviet.vtvtravel.viewmodel.DisableRecyclerView;
import com.namviet.vtvtravel.viewmodel.VideoViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class PlayListVideoActivity extends BaseActivity implements Observer, VideoSelectListener,
        VideoPlayerEvents.OnFullscreenListener,
        AdvertisingEvents.OnAdClickListener,
        AdvertisingEvents.OnAdCompleteListener, AdvertisingEvents.OnAdSkippedListener {
    private DisableRecyclerView rvVideoList;
    private RelativeLayout rlVideoPlayer;
    private JWPlayerView jvPlayer;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Video> videoList = new ArrayList<>();
    private PlaylistVideoAdapter playlistVideoAdapter;
    private Video mVideo;
    private VideoViewModel videoViewModel;
    private View mCurrentPlayArea;
    private ImageView mBackPlayListVideoImv;

    private int mCurrentActiveVideoItem = 0;
    private float mMoveDeltaY;
    private float mOriginalHeight;
    private boolean mCanTriggerStop = true;
    private boolean mIsClickToStop;

    public float firstItemHeight;
    public float padding;
    public float itemHeight;
    public int allPixels;
    public int finalHeight;

    public void getRecyclerviewDate() {
        if (rvVideoList != null) {
            rvVideoList.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setDateValue();
                }
            }, 300);
        }
        ViewTreeObserver vto = rvVideoList.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {


            @Override
            public boolean onPreDraw() {
                rvVideoList.getViewTreeObserver().removeOnPreDrawListener(this);
                finalHeight = rvVideoList.getMeasuredHeight();
                itemHeight = getResources().getDimension(R.dimen.height_item_video);
                padding = (finalHeight - itemHeight) / 2;
                firstItemHeight = padding;
                allPixels = 0;
                rvVideoList.addOnScrollListener(mOnScrollListener);
                return true;
            }
        });
    }

    private void calculatePositionAndScrollDate(RecyclerView recyclerView) {
        int expectedPosition = Math.round((allPixels + padding - firstItemHeight) / itemHeight);

        if (expectedPosition == -1) {
            expectedPosition = 0;
        } else if (expectedPosition >= recyclerView.getAdapter().getItemCount() - 2) {
            expectedPosition--;
        }
        scrollListToPositionDate(recyclerView, expectedPosition);

    }

    private void scrollListToPositionDate(RecyclerView recyclerView, int expectedPositionDate) {
        setDateValue();
    }
    private void setDateValue() {
        int expectedPositionDateColor = Math.round((allPixels + padding - firstItemHeight) / itemHeight);
        int setColorDate = expectedPositionDateColor + 1;
//        set color here
        playlistVideoAdapter.setSelectedItem(setColorDate);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_play_list_video;
    }

    private void startMoveFloatContainer(boolean click) {

        if (rlVideoPlayer.getVisibility() != View.VISIBLE) return;
        final float moveDelta;

        if (click) {
            ViewAnimator.putOn(rlVideoPlayer).translationY(0);
            int[] playAreaPos = new int[2];
            int[] floatContainerPos = new int[2];
            mCurrentPlayArea.getLocationOnScreen(playAreaPos);
            rlVideoPlayer.getLocationOnScreen(floatContainerPos);
            mOriginalHeight = moveDelta = playAreaPos[1] - floatContainerPos[1];
        } else {
            moveDelta = mMoveDeltaY;
        }

        float translationY = moveDelta + (!click ? mOriginalHeight : 0);


        ViewAnimator.putOn(rlVideoPlayer).translationY(translationY);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        getRecyclerviewDate();
    }

    @Override
    protected void initView() {
        super.initView();
        if (null != getIntent()) {
            mVideo = getIntent().getParcelableExtra(Constants.IntentKey.KEY_ACTIVITY);
        }
        videoViewModel = new VideoViewModel();
        videoViewModel.addObserver(this);
        rvVideoList = findViewById(R.id.rvListVideo);
        rlVideoPlayer = findViewById(R.id.rlVideoPlayer);
        jvPlayer = findViewById(R.id.jwPlayer);
        mBackPlayListVideoImv = findViewById(R.id.imv_back_play_list_video);
        mBackPlayListVideoImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mLayoutManager = new LinearLayoutManager(this);
        rvVideoList.setLayoutManager(mLayoutManager);
        // rvVideoList.addOnScrollListener(mOnScrollListener);
        playlistVideoAdapter = new PlaylistVideoAdapter(this, videoList, rvVideoList);
        playlistVideoAdapter.setVideoSelectListener(this);
        rvVideoList.setAdapter(playlistVideoAdapter);

        jvPlayer.addOnFullscreenListener(this);
        jvPlayer.addOnAdClickListener(this);
        jvPlayer.addOnAdCompleteListener(this);
        jvPlayer.addOnAdSkippedListener(this);
        new KeepScreenOnHandler(jvPlayer, getWindow());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                videoViewModel.loadPlaylistVideo(mVideo.getDetail_link());
            }
        }, Constants.TimeDelay);
        initVideo(mVideo);
    }

    @Override
    public void update(Observable observable, final Object o) {
        if (observable instanceof VideoViewModel) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (o != null) {
                        if (o instanceof PlaylistVideoResponse) {
                            PlaylistVideoResponse videoResponse = (PlaylistVideoResponse) o;
                            ArrayList<Video> list = videoResponse.getData();
                            videoList.addAll(list);
                            playlistVideoAdapter.notifyDataSetChanged();
                            // if (videoList.size() > 0) {
                            //     new Handler().postDelayed(new Runnable() {
                            //         @Override
                            //         public void run() {
                            //             // rvVideoList.findViewHolderForAdapterPosition(0).itemView.performClick();
                            //         }
                            //     }, 100);
                            //
                            // }
                        }
                    } else {

                    }
                }
            });

        }
    }

    @Override
    public void onSelectVideo(Video video, int position, View view) {
        mIsClickToStop = true;
        view.setClickable(false);
        if (mCurrentPlayArea != null) {
            if (mCurrentPlayArea != view) {
                mCurrentPlayArea.setClickable(true);
                mCurrentPlayArea.setVisibility(View.VISIBLE);
                mCurrentPlayArea = view;
            } else {
                if (rlVideoPlayer.getVisibility() == View.VISIBLE) return;
            }
        } else {
            mCurrentPlayArea = view;
        }
        view.setVisibility(View.INVISIBLE);


        rlVideoPlayer.setVisibility(View.VISIBLE);
        mCurrentActiveVideoItem = position;
        mCanTriggerStop = true;

        startMoveFloatContainer(true);

//        mVideoPlayerView.setVisibility(View.INVISIBLE);
        initVideo(video);
    }

    RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        int totalDy;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                mOriginalHeight = rlVideoPlayer.getTranslationY();
                totalDy = 0;
            }
            synchronized (this) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    calculatePositionAndScrollDate(recyclerView);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (jvPlayer.getFullscreen()) {
                return;
            }
            totalDy += dy;
            mMoveDeltaY = -totalDy;
            startMoveFloatContainer(false);

            if (mCurrentActiveVideoItem < mLayoutManager.findFirstVisibleItemPosition()
                    || mCurrentActiveVideoItem > mLayoutManager.findLastVisibleItemPosition()) {
                if (mCanTriggerStop) {
                    mCanTriggerStop = false;
                    jvPlayer.pause();
                }
            }
            allPixels += dy;
        }
    };

    private void initVideo(Video item) {
        PlaylistItem video = new PlaylistItem(item.getStreaming_url());
        if (null != item.getAds_vasts() && item.getAds_vasts().size() > 0) {
            List<AdBreak> adSchedule = new ArrayList<>();
            List<Ads> list = item.getAds_vasts();
            for (int i = 0; i < list.size(); i++) {
//                AdBreak adBreak = new AdBreak("" + list.get(i).getOffset(), AdSource.VAST, "https://59da5899.ngrok.io/ads/videos/vast/3");
//                L.e("Tag ads " + list.get(i).getTag());
                AdBreak adBreak = new AdBreak("" + list.get(i).getOffset(), AdSource.VAST, list.get(i).getTag());
                adSchedule.add(adBreak);
            }
            video.setAdSchedule(adSchedule);
        }
        List<PlaylistItem> playlist = new ArrayList<>();
        playlist.add(video);
//        List<AdBreak> emptyList = new ArrayList<>();
//        Advertising advertising = new Advertising(AdSource.VAST,emptyList);
//        advertising.setSkipOffset(5);
        PlayerConfig playerConfig = new PlayerConfig.Builder()
                .playlist(playlist)
                .autostart(true)
                .build();
        jvPlayer.setup(playerConfig);

        jvPlayer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFullscreen(FullscreenEvent fullscreenEvent) {
        if (fullscreenEvent.getFullscreen()) {
            jvPlayer.setFullscreen(true, true);
        } else {
            jvPlayer.setFullscreen(false, true);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        jvPlayer.setFullscreen(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE,
                true);
        super.onConfigurationChanged(newConfig);

        ViewGroup.LayoutParams layoutParams = rlVideoPlayer.getLayoutParams();
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutParams.height = (int) getResources().getDimension(R.dimen._180sdp);
            layoutParams.width = Utils.getDeviceWidth(this);
            ViewAnimator.putOn(rlVideoPlayer).translationY(mOriginalHeight);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            rvVideoList.setEnableScroll(true);
        } else {
            layoutParams.height = Utils.getDeviceHeight(this);
            layoutParams.width = Utils.getDeviceWidth(this);
            ViewAnimator.putOn(rlVideoPlayer).translationY(0);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            rvVideoList.setEnableScroll(false);
        }
        rlVideoPlayer.setLayoutParams(layoutParams);
    }

    @Override
    public void onBackPressed() {
        if (jvPlayer.getFullscreen()) {
            jvPlayer.setFullscreen(false, true);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        jvPlayer.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        jvPlayer.onResume();
//        binding.jwPlayer.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        jvPlayer.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        jvPlayer.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        jvPlayer.onDestroy();
        videoViewModel.deleteObserver(this);
    }

    @Override
    public void onAdClick(AdClickEvent adClickEvent) {
//        Toast.makeText(this, "ads click " + adClickEvent.getClient().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAdComplete(AdCompleteEvent adCompleteEvent) {
//        Toast.makeText(this, "ads complete", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAdSkipped(AdSkippedEvent adSkippedEvent) {
//        Toast.makeText(this, "ads skip ", Toast.LENGTH_LONG).show();
    }
}
