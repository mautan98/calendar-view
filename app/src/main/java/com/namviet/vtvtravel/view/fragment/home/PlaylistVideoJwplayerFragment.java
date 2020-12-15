package com.namviet.vtvtravel.view.fragment.home;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.longtailvideo.jwplayer.configuration.PlayerConfig;
import com.longtailvideo.jwplayer.events.FullscreenEvent;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;
import com.longtailvideo.jwplayer.media.ads.AdBreak;
import com.longtailvideo.jwplayer.media.ads.AdSource;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.PlaylistVideoAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentVideoListJwplayerBinding;
import com.namviet.vtvtravel.listener.VideoSelectListener;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.response.PlaylistVideoResponse;
import com.namviet.vtvtravel.ultils.KeepScreenOnHandler;
import com.namviet.vtvtravel.videomanage.controller.ViewAnimator;
import com.namviet.vtvtravel.videomanage.utils.Utils;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.VideoViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class PlaylistVideoJwplayerFragment extends MainFragment implements Observer, VideoSelectListener, VideoPlayerEvents.OnFullscreenListener, MainActivity.OnBackPress {
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Video> videoList = new ArrayList<>();
    private PlaylistVideoAdapter playlistVideoAdapter;
    private Video mVideo;
    private VideoViewModel videoViewModel;
    private FragmentVideoListJwplayerBinding binding;
    private View mCurrentPlayArea;
    private int mCurrentActiveVideoItem = -1;
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
        if (binding.rvListVideo != null) {
            binding.rvListVideo.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setDateValue();
                }
            }, 300);
        }
        ViewTreeObserver vto = binding.rvListVideo.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {


            @Override
            public boolean onPreDraw() {
                binding.rvListVideo.getViewTreeObserver().removeOnPreDrawListener(this);
                finalHeight = binding.rvListVideo.getMeasuredHeight();
                itemHeight = getResources().getDimension(R.dimen.height_item_video);
                padding = (finalHeight - itemHeight) / 2;
                firstItemHeight = padding;
                allPixels = 0;
                binding.rvListVideo.addOnScrollListener(mOnScrollListener);
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_list_jwplayer, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        getRecyclerviewDate();
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        videoViewModel = new VideoViewModel();
        videoViewModel.addObserver(this);
        mLayoutManager = new LinearLayoutManager(getContext());
        binding.rvListVideo.setLayoutManager(mLayoutManager);
        // binding.rvListVideo.addOnScrollListener(mOnScrollListener);
        playlistVideoAdapter = new PlaylistVideoAdapter(getContext(), videoList, binding.rvListVideo);
        playlistVideoAdapter.setVideoSelectListener(this);
        binding.rvListVideo.setAdapter(playlistVideoAdapter);

        binding.jwPlayer.addOnFullscreenListener(this);
        new KeepScreenOnHandler(binding.jwPlayer, getActivity().getWindow());

        showDialogLoading();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                videoViewModel.loadPlaylistVideo(mVideo.getDetail_link());
            }
        }, Constants.TimeDelay);
        if (getView() == null) {
            return;
        }
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    if (binding.jwPlayer.getFullscreen()) {
//                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                        binding.jwPlayer.setFullscreen(false, true);
//                        return false;
//                    } else {
//                        mActivity.onBackPressed();
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });

        binding.imageviewBackListVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
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
                                        binding.rvListVideo.findViewHolderForAdapterPosition(0).itemView.performClick();
                                    }
                                }, 300);

                            }
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
                if (binding.rlVideoPlayer.getVisibility() == View.VISIBLE) return;
            }
        } else {
            mCurrentPlayArea = view;
        }
        view.setVisibility(View.INVISIBLE);


        binding.rlVideoPlayer.setVisibility(View.VISIBLE);
        mCurrentActiveVideoItem = position;
        mCanTriggerStop = true;

        startMoveFloatContainer(true);

//        mVideoPlayerView.setVisibility(View.INVISIBLE);
        initVideo(video.getStreaming_url());
    }

    RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        int totalDy;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                mOriginalHeight = binding.rlVideoPlayer.getTranslationY();
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
            if (binding.jwPlayer.getFullscreen()) {
                return;
            }
            totalDy += dy;
            mMoveDeltaY = -totalDy;
            startMoveFloatContainer(false);

            if (mCurrentActiveVideoItem < mLayoutManager.findFirstVisibleItemPosition()
                    || mCurrentActiveVideoItem > mLayoutManager.findLastVisibleItemPosition()) {
                if (mCanTriggerStop) {
                    mCanTriggerStop = false;
                    binding.jwPlayer.pause();
                }
            }
            allPixels += dy;
        }
    };

    private void startMoveFloatContainer(boolean click) {
        if (binding.rlVideoPlayer.getVisibility() != View.VISIBLE) return;
        final float moveDelta;

        if (click) {
            ViewAnimator.putOn(binding.rlVideoPlayer).translationY(0);
            int[] playAreaPos = new int[2];
            int[] floatContainerPos = new int[2];
            mCurrentPlayArea.getLocationOnScreen(playAreaPos);
            binding.rlVideoPlayer.getLocationOnScreen(floatContainerPos);
            mOriginalHeight = moveDelta = playAreaPos[1] - floatContainerPos[1];
        } else {
            moveDelta = mMoveDeltaY;
        }

        float translationY = moveDelta + (!click ? mOriginalHeight : 0);
        ViewAnimator.putOn(binding.rlVideoPlayer).translationY(translationY);
    }

    private void initVideo(String url) {
        PlaylistItem video = new PlaylistItem(url);

        AdBreak adBreak = new AdBreak("pre", AdSource.VAST, "https://s3.amazonaws.com/demo.jwplayer.com/advertising/assets/vast3_jw_ads.xml");
        List<AdBreak> adSchedule = new ArrayList<>();
        adSchedule.add(adBreak);

        video.setAdSchedule(adSchedule);

        List<PlaylistItem> playlist = new ArrayList<>();

        playlist.add(video);

        PlayerConfig playerConfig = new PlayerConfig.Builder()
                .playlist(playlist)
                .autostart(true)
                .build();
        binding.jwPlayer.setup(playerConfig);
        binding.rlVideoPlayer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFullscreen(FullscreenEvent fullscreenEvent) {
        if (fullscreenEvent.getFullscreen()) {
            binding.jwPlayer.setFullscreen(true, true);
        } else {
            binding.jwPlayer.setFullscreen(false, true);
        }

//        binding.jwPlayer.setFocusableInTouchMode(true);
//        binding.jwPlayer.requestFocus();
//        binding.jwPlayer.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
//
//                    Log.d("LamLV", "ok");
////                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    return true;
//                }
//                return false;
//            }
//        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        binding.jwPlayer.setFullscreen(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE,
                true);
        super.onConfigurationChanged(newConfig);

        if (binding.rlVideoPlayer == null) return;

        ViewGroup.LayoutParams layoutParams = binding.rlVideoPlayer.getLayoutParams();
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutParams.height = (int) getResources().getDimension(R.dimen._180sdp);
            layoutParams.width = Utils.getDeviceWidth(getActivity());
            ViewAnimator.putOn(binding.rlVideoPlayer).translationY(mOriginalHeight);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            binding.rvListVideo.setEnableScroll(true);
            mActivity.setOnBackPress(null);
        } else {
            mActivity.setOnBackPress(this);
            layoutParams.height = Utils.getDeviceHeight(getActivity());
            layoutParams.width = Utils.getDeviceWidth(getActivity());
            ViewAnimator.putOn(binding.rlVideoPlayer).translationY(0);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            binding.rvListVideo.setEnableScroll(false);
        }
        binding.rlVideoPlayer.setLayoutParams(layoutParams);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.jwPlayer.onStart();

//        binding.jwPlayer.setFocusableInTouchMode(true);
//        binding.jwPlayer.requestFocus();
//        binding.jwPlayer.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
//
//                    Log.d("LamLV", "ok");
////                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.jwPlayer.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.jwPlayer.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.jwPlayer.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.jwPlayer.onDestroy();
    }

    @Override
    public void onBackPressFromActivity() {
        if (binding.jwPlayer.getFullscreen()) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            binding.jwPlayer.setFullscreen(false, true);
        } else {
            mActivity.onBackPressed();
        }

    }
}
