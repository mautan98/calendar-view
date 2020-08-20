package com.namviet.vtvtravel.view.fragment.home;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.PlaylistVideoAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.listener.VideoSelectListener;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.response.PlaylistVideoResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.VideoResponse;
import com.namviet.vtvtravel.videomanage.controller.VideoControllerView;
import com.namviet.vtvtravel.videomanage.controller.ViewAnimator;
import com.namviet.vtvtravel.videomanage.manager.SingleVideoPlayerManager;
import com.namviet.vtvtravel.videomanage.manager.VideoPlayerManager;
import com.namviet.vtvtravel.videomanage.meta.CurrentItemMetaData;
import com.namviet.vtvtravel.videomanage.meta.MetaData;
import com.namviet.vtvtravel.videomanage.ui.MediaPlayerWrapper;
import com.namviet.vtvtravel.videomanage.ui.VideoPlayerView;
import com.namviet.vtvtravel.videomanage.utils.Utils;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.DisableRecyclerView;
import com.namviet.vtvtravel.viewmodel.VideoViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class PlaylistVideoFragment extends MainFragment implements VideoSelectListener, Observer {
    public static final String TAG = "RecyclerViewFragment";
    private DisableRecyclerView rvListVideo;
    private LinearLayoutManager mLayoutManager;

    private FrameLayout mVideoFloatContainer;
    private View mVideoPlayerBg;
    private ImageView mVideoCoverMask;
    private VideoPlayerView mVideoPlayerView;
    private View mVideoLoadingView;
    private ProgressBar mVideoProgressBar;

    private View mCurrentPlayArea;
    private VideoControllerView mCurrentVideoControllerView;
    private int mCurrentActiveVideoItem = -1;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private int mCurrentBuffer;


    private boolean mCanTriggerStop = true;

    private VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(null);

    private boolean mIsClickToStop;

    private float mOriginalHeight;

    private float mMoveDeltaY;

    private ArrayList<Video> videoList = new ArrayList<>();
    private PlaylistVideoAdapter playlistVideoAdapter;
    private Video mVideo;
    private VideoViewModel videoViewModel;

    public float firstItemHeight;
    public float padding;
    public float itemHeight;
    public int allPixels;
    public int finalHeight;

    public void getRecyclerviewDate() {
        if (rvListVideo != null) {
            rvListVideo.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setDateValue();
                }
            }, 300);
        }
        ViewTreeObserver vto = rvListVideo.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {


            @Override
            public boolean onPreDraw() {
                rvListVideo.getViewTreeObserver().removeOnPreDrawListener(this);
                finalHeight = rvListVideo.getMeasuredHeight();
                itemHeight = getResources().getDimension(R.dimen.height_item_video);
                padding = (finalHeight - itemHeight) / 2;
                firstItemHeight = padding;
                allPixels = 0;
                rvListVideo.addOnScrollListener(mOnScrollListener);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mVideo = getArguments().getParcelable(Constants.IntentKey.KEY_FRAGMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playlist_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        getRecyclerviewDate();
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        videoViewModel = new VideoViewModel();
        videoViewModel.addObserver(this);
        mVideoFloatContainer = view.findViewById(R.id.layout_float_video_container);
        mVideoPlayerBg = view.findViewById(R.id.video_player_bg);
        mVideoCoverMask = view.findViewById(R.id.video_player_mask);
        mVideoPlayerView = view.findViewById(R.id.video_player_view);
        mVideoLoadingView = view.findViewById(R.id.video_progress_loading);
        mVideoProgressBar = view.findViewById(R.id.video_progress_bar);

        rvListVideo = view.findViewById(R.id.rvListVideo);
        mLayoutManager = new LinearLayoutManager(getContext());
        rvListVideo.setLayoutManager(mLayoutManager);
        // rvListVideo.addOnScrollListener(mOnScrollListener);
        playlistVideoAdapter = new PlaylistVideoAdapter(getContext(), videoList, rvListVideo);
        playlistVideoAdapter.setVideoSelectListener(this);
        rvListVideo.setAdapter(playlistVideoAdapter);
        showDialogLoading();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                videoViewModel.loadPlaylistVideo(mVideo.getDetail_link());
            }
        }, Constants.TimeDelay);

        mVideoPlayerView.addMediaPlayerListener(new MediaPlayerWrapper.MainThreadMediaPlayerListener() {
            @Override
            public void onVideoSizeChangedMainThread(int width, int height) {

            }

            @Override
            public void onVideoPreparedMainThread() {

                mVideoFloatContainer.setVisibility(View.VISIBLE);
                mVideoPlayerView.setVisibility(View.VISIBLE);
                mVideoLoadingView.setVisibility(View.VISIBLE);
                mVideoCoverMask.setVisibility(View.VISIBLE);
            }

            @Override
            public void onVideoCompletionMainThread() {
                if (mCurrentPlayArea != null) {
                    mCurrentPlayArea.setClickable(true);
                }
                mVideoFloatContainer.setVisibility(View.INVISIBLE);
                mCurrentPlayArea.setVisibility(View.VISIBLE);
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                ViewAnimator.putOn(mVideoFloatContainer).translationY(0);
                mVideoProgressBar.setVisibility(View.GONE);
                mHandler.removeCallbacks(mProgressRunnable);
                if (mCurrentActiveVideoItem > -1 && mCurrentActiveVideoItem < videoList.size() - 1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rvListVideo.findViewHolderForAdapterPosition(mCurrentActiveVideoItem + 1).itemView.performClick();
                        }
                    }, 100);
                }
            }

            @Override
            public void onErrorMainThread(int what, int extra) {
                if (mCurrentPlayArea != null) {
                    mCurrentPlayArea.setClickable(true);
                    mCurrentPlayArea.setVisibility(View.VISIBLE);
                }
                mVideoFloatContainer.setVisibility(View.INVISIBLE);

                //stop update progress
                mVideoProgressBar.setVisibility(View.GONE);
                mHandler.removeCallbacks(mProgressRunnable);
            }

            @Override
            public void onBufferingUpdateMainThread(int percent) {
                mCurrentBuffer = percent;
            }

            @Override
            public void onVideoStoppedMainThread() {
                if (!mIsClickToStop) {
                    mCurrentPlayArea.setClickable(true);
                    mCurrentPlayArea.setVisibility(View.VISIBLE);
                }

                //stop update progress
                mVideoProgressBar.setVisibility(View.GONE);
                mHandler.removeCallbacks(mProgressRunnable);
            }

            @Override
            public void onInfoMainThread(int what) {
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {

                    //start update progress
                    mVideoProgressBar.setVisibility(View.VISIBLE);
                    mHandler.post(mProgressRunnable);

                    mVideoPlayerView.setVisibility(View.VISIBLE);
                    mVideoLoadingView.setVisibility(View.GONE);
                    mVideoCoverMask.setVisibility(View.GONE);
                    mVideoPlayerBg.setVisibility(View.VISIBLE);
                    createVideoControllerView();

                    mCurrentVideoControllerView.showWithTitle("");
                } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    mVideoLoadingView.setVisibility(View.VISIBLE);
                } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    mVideoLoadingView.setVisibility(View.GONE);
                }
            }
        });
        if (getView() == null) {
            return;
        }
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mPlayerControlListener.isFullScreen()) {
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    } else {
                        mActivity.onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void updateViews() {
        super.updateViews();
    }

    private void createVideoControllerView() {

        if (mCurrentVideoControllerView != null) {
            mCurrentVideoControllerView.hide();
            mCurrentVideoControllerView = null;
        }

        mCurrentVideoControllerView = new VideoControllerView.Builder(getActivity(), mPlayerControlListener)
                .withVideoTitle("")
                .withVideoView(mVideoPlayerView)//to enable toggle display controller view
                .canControlBrightness(true)
                .canControlVolume(true)
                .canSeekVideo(false)
                .exitIcon(R.drawable.video_top_back)
                .pauseIcon(R.drawable.ic_media_pause)
                .playIcon(R.drawable.ic_media_play)
                .shrinkIcon(R.drawable.ic_media_fullscreen_shrink)
                .stretchIcon(R.drawable.ic_media_fullscreen_stretch)
                .build(mVideoFloatContainer);//layout container that hold video play view
    }


    private VideoControllerView.MediaPlayerControlListener mPlayerControlListener = new VideoControllerView.MediaPlayerControlListener() {
        @Override
        public void start() {
            if (checkMediaPlayerInvalid())
                mVideoPlayerView.getMediaPlayer().start();
        }

        @Override
        public void pause() {
            mVideoPlayerView.getMediaPlayer().pause();
        }

        @Override
        public int getDuration() {
            if (checkMediaPlayerInvalid()) {
                return mVideoPlayerView.getMediaPlayer().getDuration();
            }
            return 0;
        }

        @Override
        public int getCurrentPosition() {
            if (checkMediaPlayerInvalid()) {
                return mVideoPlayerView.getMediaPlayer().getCurrentPosition();
            }
            return 0;
        }

        @Override
        public void seekTo(int position) {
            if (checkMediaPlayerInvalid()) {
                mVideoPlayerView.getMediaPlayer().seekToPosition(position);
            }
        }

        @Override
        public boolean isPlaying() {
            if (checkMediaPlayerInvalid()) {
                return mVideoPlayerView.getMediaPlayer().isPlaying();
            }
            return false;
        }

        @Override
        public boolean isComplete() {
            return false;
        }

        @Override
        public int getBufferPercentage() {
            return mCurrentBuffer;
        }

        @Override
        public boolean isFullScreen() {
            return getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    || getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
        }

        @Override
        public void toggleFullScreen() {
            if (isFullScreen()) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                getActivity().setRequestedOrientation(Build.VERSION.SDK_INT < 9 ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                        ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            }
        }

        @Override
        public void exit() {
            if (isFullScreen()) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    };

    private boolean checkMediaPlayerInvalid() {
        return mVideoPlayerView != null && mVideoPlayerView.getMediaPlayer() != null;
    }


    RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        int totalDy;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                mOriginalHeight = mVideoFloatContainer.getTranslationY();
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
            if (mPlayerControlListener.isFullScreen()) {
                return;
            }
            totalDy += dy;
            mMoveDeltaY = -totalDy;
            startMoveFloatContainer(false);

            if (mCurrentActiveVideoItem < mLayoutManager.findFirstVisibleItemPosition()
                    || mCurrentActiveVideoItem > mLayoutManager.findLastVisibleItemPosition()) {
                if (mCanTriggerStop) {
                    mCanTriggerStop = false;
                    stopPlaybackImmediately();
                }
            }
            allPixels += dy;
        }
    };

    public void stopPlaybackImmediately() {

        mIsClickToStop = false;

        if (mCurrentPlayArea != null) {
            mCurrentPlayArea.setClickable(true);
        }

        if (mVideoPlayerManager != null) {
            mVideoFloatContainer.setVisibility(View.INVISIBLE);
            mVideoPlayerManager.stopAnyPlayback();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (mVideoFloatContainer == null) return;

        ViewGroup.LayoutParams layoutParams = mVideoFloatContainer.getLayoutParams();

        mCurrentVideoControllerView.hide();

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutParams.height = (int) getResources().getDimension(R.dimen._180sdp);
            layoutParams.width = Utils.getDeviceWidth(getActivity());

            ViewAnimator.putOn(mVideoFloatContainer).translationY(mOriginalHeight);

            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            rvListVideo.setEnableScroll(true);

        } else {

            layoutParams.height = Utils.getDeviceHeight(getActivity());
            layoutParams.width = Utils.getDeviceWidth(getActivity());

            ViewAnimator.putOn(mVideoFloatContainer).translationY(0);

            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            rvListVideo.setEnableScroll(false);
        }
        mVideoFloatContainer.setLayoutParams(layoutParams);
    }


    private Runnable mProgressRunnable = new Runnable() {
        @Override
        public void run() {
            if (mPlayerControlListener != null) {

                if (mCurrentVideoControllerView.isShowing()) {
                    mVideoProgressBar.setVisibility(View.GONE);
                } else {
                    mVideoProgressBar.setVisibility(View.VISIBLE);
                }

                int position = mPlayerControlListener.getCurrentPosition();
                int duration = mPlayerControlListener.getDuration();
                if (duration != 0) {
                    long pos = 1000L * position / duration;
                    int percent = mPlayerControlListener.getBufferPercentage() * 10;
                    mVideoProgressBar.setProgress((int) pos);
                    mVideoProgressBar.setSecondaryProgress(percent);
                    mHandler.postDelayed(this, 1000);
                }
            }
        }
    };

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
                if (mVideoFloatContainer.getVisibility() == View.VISIBLE) return;
            }
        } else {
            mCurrentPlayArea = view;
        }
        view.setVisibility(View.INVISIBLE);
        if (mCurrentVideoControllerView != null)
            mCurrentVideoControllerView.hide();

        mVideoFloatContainer.setVisibility(View.VISIBLE);
        mVideoCoverMask.setVisibility(View.GONE);
        mVideoPlayerBg.setVisibility(View.GONE);
        mCurrentActiveVideoItem = position;
        mCanTriggerStop = true;

        startMoveFloatContainer(true);

        mVideoLoadingView.setVisibility(View.VISIBLE);
        mVideoPlayerView.setVisibility(View.INVISIBLE);

        mVideoPlayerManager.playNewVideo(new CurrentItemMetaData(position, view), mVideoPlayerView, video.getStreaming_url());
    }

    private void startMoveFloatContainer(boolean click) {
        if (mVideoFloatContainer.getVisibility() != View.VISIBLE) return;
        final float moveDelta;

        if (click) {
            ViewAnimator.putOn(mVideoFloatContainer).translationY(0);
            int[] playAreaPos = new int[2];
            int[] floatContainerPos = new int[2];
            mCurrentPlayArea.getLocationOnScreen(playAreaPos);
            mVideoFloatContainer.getLocationOnScreen(floatContainerPos);
            mOriginalHeight = moveDelta = playAreaPos[1] - floatContainerPos[1];
        } else {
            moveDelta = mMoveDeltaY;
        }

        float translationY = moveDelta + (!click ? mOriginalHeight : 0);
        ViewAnimator.putOn(mVideoFloatContainer).translationY(translationY);
    }

    @Override
    public void update(Observable observable, final Object o) {
        dimissDialogLoading();
        if (observable instanceof VideoViewModel) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (o != null) {
                        if (o instanceof PlaylistVideoResponse) {
                            PlaylistVideoResponse videoResponse = (PlaylistVideoResponse) o;
                            ArrayList<Video> list = videoResponse.getData();
                            videoList.addAll(list);
                            playlistVideoAdapter.notifyDataSetChanged();
                            if (videoList.size() > 0) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        rvListVideo.findViewHolderForAdapterPosition(0).itemView.performClick();
                                    }
                                }, 100);

                            }
                        }  else if (o instanceof ResponseError) {
                            ResponseError responseError = (ResponseError) o;
                            showMessage(responseError.getMessage());
                        }
                    } else {
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mVideoPlayerManager) {
            mVideoPlayerManager.stopAnyPlayback();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPlaybackImmediately();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
