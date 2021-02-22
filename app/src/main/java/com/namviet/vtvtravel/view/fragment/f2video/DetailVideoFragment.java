package com.namviet.vtvtravel.view.fragment.f2video;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import android.util.Log;

import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.longtailvideo.jwplayer.configuration.PlayerConfig;
import com.longtailvideo.jwplayer.events.ControlBarVisibilityEvent;
import com.longtailvideo.jwplayer.events.FullscreenEvent;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentDetailVideoBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.model.f2event.OnUpdateCommentCount;
import com.namviet.vtvtravel.response.f2video.VideoDetailResponse;
import com.namviet.vtvtravel.response.travelnews.DetailTravelNewsResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.ultils.ViewMoreUtils;
import com.namviet.vtvtravel.view.f2.CommentActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.ShareActivity;
import com.namviet.vtvtravel.view.fragment.share.ShareBottomDialog;
import com.namviet.vtvtravel.viewmodel.f2video.DetailVideoViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class DetailVideoFragment extends BaseFragment<F2FragmentDetailVideoBinding> implements VideoPlayerEvents.OnFullscreenListener, VideoPlayerEvents.OnControlBarVisibilityListener,  Observer {
    private Video video;
    private DetailVideoViewModel viewModel;

    private String detailLink;

    public DetailVideoFragment() {
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(String detailLink) {
        this.detailLink = detailLink;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_detail_video;
    }

    @Override
    public void initView() {
        try {
            viewModel = new DetailVideoViewModel();
            viewModel.addObserver(this);
            viewModel.viewVideo(detailLink);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            Glide.with(mActivity).load(video.getLogo_url()).into(getBinding().imgBanner);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            getBinding().tvTitle.setText(video.getName());
//            try {
//                getBinding().tvType.setText(video.getCategory().getName());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            getBinding().tvNumberOfComment.setText(video.getComment_count());
//            getBinding().tvDescription.setText(video.getShort_description());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        initJWPlayer("");
//        try {
//            if (video.getShort_description().length() > 72) {
//                ViewMoreUtils.makeTextViewResizable(getBinding().tvDescription, 2, "Xem thêm", true, "#FFFFFF");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        updateLikeEvent();

        getBinding().imgHeart.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickHeart();
                    }
                }, 100);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickHeart();
                    }
                }, 100);
            }
        });

//        getBinding().imgHeart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Account account = MyApplication.getInstance().getAccount();
//                    if (null != account && account.isLogin()) {
//                        viewModel.likeEvent(video.getId(), video.getContent_type());
//                        try {
//                            TrackingAnalytic.postEvent(TrackingAnalytic.LIKE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.VIDEO_DETAIL, TrackingAnalytic.ScreenTitle.VIDEO_DETAIL)
//                                    .setContent_type(video.getContent_type())
//                                    .setContent_id(video.getId())
//                                    .setScreen_class(this.getClass().getName()));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        if (video.isLiked()) {
//                            video.setLiked(false);
//                            if (null != video.getLikeCount()) {
//                                String likeCount = (Integer.parseInt(video.getLikeCount()) + 1) + "";
//                                video.setLikeCount(likeCount);
//                            }
//                        } else {
//                            video.setLiked(true);
//                            if (null != video.getLikeCount()) {
//                                String likeCount = (Integer.parseInt(video.getLikeCount()) - 1) + "";
//                                video.setLikeCount(likeCount);
//                            }
//                        }
//                        updateLikeEvent();
//                    } else {
//                        LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

    }

    private void clickHeart() {
        try {
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin()) {
                viewModel.likeEvent(video.getId(), video.getContent_type());
                try {
                    TrackingAnalytic.postEvent(TrackingAnalytic.LIKE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.VIDEO_DETAIL, TrackingAnalytic.ScreenTitle.VIDEO_DETAIL)
                            .setContent_type(video.getContent_type())
                            .setContent_id(video.getId())
                            .setScreen_class(this.getClass().getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (video.isLiked()) {
                    video.setLiked(false);
                    if (null != video.getLikeCount()) {
                        String likeCount = (Integer.parseInt(video.getLikeCount()) + 1) + "";
                        video.setLikeCount(likeCount);
                    }
                } else {
                    video.setLiked(true);
                    if (null != video.getLikeCount()) {
                        String likeCount = (Integer.parseInt(video.getLikeCount()) - 1) + "";
                        video.setLikeCount(likeCount);
                    }
                }
                updateLikeEvent();
            } else {
                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateLikeEvent() {
        try {
            if (video.isLiked()) {
//                getBinding().imgHeart.setImageResource(R.drawable.f2_ic_heart);
                getBinding().imgHeart.setLiked(true);
            } else {
//                getBinding().imgHeart.setImageResource(R.drawable.f2_ic_white_heart);
                getBinding().imgHeart.setLiked(false);
            }
            getBinding().tvCountLike.setText(video.getLikeCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {
        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.VIDEO_DETAIL, TrackingAnalytic.ScreenTitle.VIDEO_DETAIL)
                    .setScreen_class(this.getClass().getName())
                    .setContent_id(video.getId())
                    .setCategory_tree_code(video.getCategory_tree_code())
                    .setCategory_tree_name(video.getCategory_tree_name())
                    .setContent_type("videos"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        getBinding().btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    ShareBottomDialog shareBottomDialog = new ShareBottomDialog(new ShareBottomDialog.DoneClickShare() {
                        @Override
                        public void onDoneClickShare(boolean isVTVApp) {
                            if (isVTVApp) {
                                ShareActivity.startScreen(mActivity, video.getName(), video.getDetail_link(), video.getLogo_url(), "videos");
                            } else {
//                                String linkShare = WSConfig.HOST_LANDING+F2Util.genEndPointShareLink(Constants.ShareLinkType.VIDEO, detailLink);
//                                F2Util.startSenDataText(mActivity, linkShare);
                                F2Util.startSenDataText(mActivity, video.getLink_share());

                            }
                        }
                    });

                    shareBottomDialog.show(mActivity.getSupportFragmentManager(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    TrackingAnalytic.postEvent(TrackingAnalytic.SHARE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.VIDEO_DETAIL, TrackingAnalytic.ScreenTitle.VIDEO_DETAIL)
                            .setContent_id(video.getId())
                            .setContent_type(video.getContent_type())
                            .setScreen_class(this.getClass().getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        getBinding().imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DetailTravelNewsResponse detailTravelNewsResponse = new DetailTravelNewsResponse();
                    DetailTravelNewsResponse.Data data = new DetailTravelNewsResponse().new Data();
                    data.setId(video.getId());
                    data.setContent_type(video.getContent_type());
                    detailTravelNewsResponse.setData(data);
                    CommentActivity.startScreen(mActivity, detailTravelNewsResponse, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        getBinding().btnSoundDisabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBinding().jwPlayer.setMute(false);
                getBinding().btnSoundEnable.setVisibility(View.VISIBLE);
                getBinding().btnSoundDisabled.setVisibility(View.GONE);
            }
        });

        getBinding().btnSoundEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBinding().jwPlayer.setMute(true);
                getBinding().btnSoundEnable.setVisibility(View.GONE);
                getBinding().btnSoundDisabled.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void setObserver() {

    }

    private void initJWPlayer(String url) {
        try {
            PlaylistItem playlistItem = new PlaylistItem.Builder()
                    .file(video.getStreaming_url())
                    .build();

            List<PlaylistItem> playlist = new ArrayList<>();
            playlist.add(playlistItem);
            PlayerConfig config = new PlayerConfig.Builder()
                    .playlist(playlist)
                    .build();
            getBinding().jwPlayer.setup(config);
            getBinding().jwPlayer.addOnFullscreenListener(this);
            getBinding().jwPlayer.addOnControlBarVisibilityListener(this);

            getBinding().jwPlayer.setAnalyticsListener(new AnalyticsListener() {
                @Override
                public void onBandwidthEstimate(EventTime eventTime, int totalLoadTimeMs, long totalBytesLoaded, long bitrateEstimate) {
                    Log.e("hihihihihi", "totalBytesLoaded");

                }




            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getBinding().jwPlayer != null)
            getBinding().jwPlayer.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getBinding().jwPlayer != null) {
            getBinding().jwPlayer.onResume();
            getBinding().jwPlayer.play();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getBinding().jwPlayer != null)
            getBinding().jwPlayer.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (getBinding().jwPlayer != null)
            getBinding().jwPlayer.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getBinding().jwPlayer != null)
            getBinding().jwPlayer.onDestroy();
    }

    @Override
    public void onFullscreen(FullscreenEvent fullscreenEvent) {
        if (fullscreenEvent.getFullscreen()) {
            getBinding().jwPlayer.setFullscreen(true, true);
        } else {
            getBinding().jwPlayer.setFullscreen(false, true);
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        try {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                getBinding().jwPlayer.setFullscreen(true, true);
            } else {
                getBinding().jwPlayer.setFullscreen(false, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onUpdateCommentCountVideo(OnUpdateCommentCount onUpdateCommentCount) {
        try {
            getBinding().tvNumberOfComment.setText(String.valueOf(Integer.parseInt(getBinding().tvNumberOfComment.getText().toString()) + Integer.parseInt(onUpdateCommentCount.getCount())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.VIDEO_DETAIL, TrackingAnalytic.ScreenTitle.VIDEO_DETAIL);
    }

    @Override
    public void update(Observable observable, Object o) {
        hideLoading();
        if (observable instanceof DetailVideoViewModel && null != o) {
            if (o instanceof VideoDetailResponse) {
                VideoDetailResponse response = (VideoDetailResponse) o;
                video = response.getData().get(0);
                setUpView();

            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {

                } catch (Exception e) {

                }
            }

        }
    }

    private void setUpView() {
        try {
            Glide.with(mActivity).load(video.getLogo_url()).into(getBinding().imgBanner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            getBinding().tvTitle.setText(video.getName());
            try {
                getBinding().tvType.setText(video.getCategory().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                double value = Double.parseDouble(video.getComment_count());
                if (value > 1000) {
                    double finalValue = Math.round(value / 1000 * 10.0) / 10.0;
                    getBinding().tvNumberOfComment.setText(finalValue + "k");
                } else {
                    getBinding().tvNumberOfComment.setText(video.getComment_count());
                }
            } catch (Exception e) {
                getBinding().tvNumberOfComment.setText(video.getComment_count());
            }

            getBinding().tvDescription.setText(video.getShort_description());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initJWPlayer("");
        try {
            if (video.getShort_description().length() > 72) {
                ViewMoreUtils.makeTextViewResizable(getBinding().tvDescription, 2, "Xem thêm", true, "#FFFFFF");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onControlBarVisibilityChanged(ControlBarVisibilityEvent controlBarVisibilityEvent) {
        if(controlBarVisibilityEvent.isVisible()){
            getBinding().layoutSound.setVisibility(View.VISIBLE);
        }else {
            getBinding().layoutSound.setVisibility(View.GONE);
        }
    }
}
