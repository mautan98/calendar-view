package com.namviet.vtvtravel.view.fragment.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.baseapp.menu.SlideMenu;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.CommentAdapter;
import com.namviet.vtvtravel.adapter.CommentChildAdapter;
import com.namviet.vtvtravel.adapter.DetailMomentFragmentAdapter;
import com.namviet.vtvtravel.adapter.ListNewsHighlightAdapter;
import com.namviet.vtvtravel.adapter.RelationNewsAdapter;
import com.namviet.vtvtravel.adapter.SlideMomentAdapter;
import com.namviet.vtvtravel.adapter.SlideMomentPhotoAdapter;
import com.namviet.vtvtravel.adapter.TagDetailMomentAdapter;
import com.namviet.vtvtravel.adapter.TagMomentAdapter;
import com.namviet.vtvtravel.adapter.TagVideoAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentDetailMomentBinding;
import com.namviet.vtvtravel.listener.NewsSelectListener;
import com.namviet.vtvtravel.listener.PostCommentListener;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.Comment;
import com.namviet.vtvtravel.model.News;
import com.namviet.vtvtravel.response.CommentResponse;
import com.namviet.vtvtravel.response.DetailNewsData;
import com.namviet.vtvtravel.response.DetailNewsResponse;
import com.namviet.vtvtravel.response.PostCommentResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.videomanage.controller.VideoControllerView;
import com.namviet.vtvtravel.videomanage.controller.ViewAnimator;
import com.namviet.vtvtravel.videomanage.manager.SingleVideoPlayerManager;
import com.namviet.vtvtravel.videomanage.manager.VideoPlayerManager;
import com.namviet.vtvtravel.videomanage.meta.CurrentItemMetaData;
import com.namviet.vtvtravel.videomanage.meta.MetaData;
import com.namviet.vtvtravel.videomanage.ui.MediaPlayerWrapper;
import com.namviet.vtvtravel.videomanage.utils.Utils;
import com.namviet.vtvtravel.view.dialog.CommentDialogFragment;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.NewsViewModel;
import com.namviet.vtvtravel.widget.CustPagerTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class DetailMomentFrangment extends MainFragment implements Observer, NewsSelectListener, PostCommentListener {
    private FragmentDetailMomentBinding binding;
    private NewsViewModel newsViewModel;
    private Travel mTravel;
    private DetailMomentFragmentAdapter mDetailMomentFragmentAdapter;
    private RelationNewsAdapter relationNewsAdapter;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList = new ArrayList<>();
    private int countComment = 0;
    private int totalPage = 0;
    private int page = 0;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private VideoControllerView mCurrentVideoControllerView;
    private int mCurrentBuffer;
    private VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(null);
    private DetailNewsData detailNewsData;
    private SlideMomentPhotoAdapter slideMomentAdapter;
    private SnapHelper mSnapHelper;
    private SnapHelper mSnapHelper2;
    private CommentChildAdapter commentChildAdapter;
    private TagDetailMomentAdapter tagDetailMomentAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mTravel = getArguments().getParcelable(Constants.IntentKey.KEY_FRAGMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_moment, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolBar.myToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        binding.toolBar.ivBack.setOnClickListener(this);
        binding.toolBar.ivSearch.setOnClickListener(this);
        binding.tvComment.setOnClickListener(this);
        binding.ivBannerVideo.setOnClickListener(this);
        newsViewModel = new NewsViewModel(getContext());
        binding.setNewsViewModel(newsViewModel);
        newsViewModel.addObserver(this);

        binding.rvComment.setNestedScrollingEnabled(false);
        binding.rvComment.setLayoutManager(new LinearLayoutManager(getContext()));
        commentAdapter = new CommentAdapter(mActivity, commentList, this);
        binding.rvComment.setAdapter(commentAdapter);
        commentChildAdapter = new CommentChildAdapter();

        binding.rvNewsSameCategory.setNestedScrollingEnabled(false);
        binding.rvNewsSameCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mSnapHelper = new LinearSnapHelper();
        mSnapHelper.attachToRecyclerView(binding.rvNewsSameCategory);

        binding.rvNewsRelated.setNestedScrollingEnabled(false);
        binding.rvNewsRelated.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mSnapHelper2 = new LinearSnapHelper();
        mSnapHelper2.attachToRecyclerView(binding.rvNewsRelated);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.KeyBroadcast.KEY_LOGIN_SCREEN);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, intentFilter);
        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        showDialogLoading();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (null != mTravel) {
                    newsViewModel.getNewsById(mTravel.getDetail_link());
                }
            }
        }, Constants.TimeDelay);

        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            setImageUrl(account.getImageProfile(), binding.avatar);
        }
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
    public void update(Observable observable, final Object o) {
        dimissDialogLoading();
        if (observable instanceof NewsViewModel) {
            if (o != null) {
                if (o instanceof DetailNewsResponse) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DetailNewsResponse response = (DetailNewsResponse) o;
                            updateUI(response.getData());
                        }
                    });
                } else if (o instanceof CommentResponse) {
                    CommentResponse commentResponse = (CommentResponse) o;
                    totalPage = commentResponse.getData().getTotalPages();
                    countComment = commentResponse.getData().getTotalElements();
                    binding.tvTitleComment.setText(getText(R.string.comment) + " (" + countComment + ")");
                    commentList.clear();
                    commentList.addAll(commentResponse.getData().getContent());
                    commentAdapter.notifyDataSetChanged();
                } else if (o instanceof PostCommentResponse) {
                    countComment++;
                    binding.tvTitleComment.setText(getText(R.string.comment) + " (" + countComment + ")");
                    PostCommentResponse response = (PostCommentResponse) o;
                    Account account = MyApplication.getInstance().getAccount();
                    Comment comment = response.getData();
                    comment.setUser(account);
                    // commentList.add(0, comment);
                    commentAdapter.notifyDataSetChanged();
                    commentChildAdapter.notifyDataSetChanged();
                }  else if (o instanceof ResponseError) {
                    ResponseError responseError = (ResponseError) o;
                    showMessage(responseError.getMessage());
                }
            } else {
            }
        }
    }

    private void updateUI(DetailNewsData data) {
        this.detailNewsData = data;
        binding.toolBar.tvTitle.setText(data.getTitle());
        binding.tvTitleNews.setText(data.getName());
        binding.tvTime.setText(DateUtltils.timeToString(data.getCreated()));
        binding.tvView.setText("" + data.getAuthor());

        binding.tvShortDescription.setText(data.getShort_description());
        tagDetailMomentAdapter = new TagDetailMomentAdapter(getContext(), data.getTags());
        binding.rvTag.setAdapter(tagDetailMomentAdapter);

        if (null != mTravel && mTravel.getType().equals(Constants.TypeLeftMenu.VIDEO)) {
            setImageUrl(data.getLogo_url(), binding.ivBannerVideo);
            binding.rlVideoPlayer.setVisibility(View.VISIBLE);
            binding.vpSlideShow.setVisibility(View.GONE);
            binding.videoPlayer.videoPlayerView.addMediaPlayerListener(new MediaPlayerWrapper.MainThreadMediaPlayerListener() {
                @Override
                public void onVideoSizeChangedMainThread(int width, int height) {

                }

                @Override
                public void onVideoPreparedMainThread() {
                    binding.ivBannerVideo.setVisibility(View.GONE);
                    binding.ivIconPlay.setVisibility(View.GONE);
                    binding.videoPlayer.layoutFloatVideoContainer.setVisibility(View.VISIBLE);
                    binding.videoPlayer.videoPlayerView.setVisibility(View.VISIBLE);
                    binding.videoPlayer.videoProgressBar.setVisibility(View.VISIBLE);
                    binding.videoPlayer.videoPlayerMask.setVisibility(View.VISIBLE);
                }

                @Override
                public void onVideoCompletionMainThread() {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    binding.videoPlayer.videoProgressBar.setVisibility(View.GONE);
                    binding.ivBannerVideo.setVisibility(View.VISIBLE);
                    binding.ivIconPlay.setVisibility(View.VISIBLE);
                    mHandler.removeCallbacks(mProgressRunnable);
                }

                @Override
                public void onErrorMainThread(int what, int extra) {
                    binding.videoPlayer.layoutFloatVideoContainer.setVisibility(View.INVISIBLE);
                    binding.videoPlayer.videoProgressBar.setVisibility(View.GONE);
                    binding.ivBannerVideo.setVisibility(View.VISIBLE);
                    binding.ivIconPlay.setVisibility(View.VISIBLE);
                    mHandler.removeCallbacks(mProgressRunnable);
                }

                @Override
                public void onBufferingUpdateMainThread(int percent) {
                    mCurrentBuffer = percent;
                }

                @Override
                public void onVideoStoppedMainThread() {
                    binding.videoPlayer.videoProgressBar.setVisibility(View.GONE);
                    mHandler.removeCallbacks(mProgressRunnable);
                    binding.ivBannerVideo.setVisibility(View.VISIBLE);
                    binding.ivIconPlay.setVisibility(View.VISIBLE);
                }

                @Override
                public void onInfoMainThread(int what) {
                    if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {

                        //start update progress
                        binding.videoPlayer.videoProgressBar.setVisibility(View.VISIBLE);
                        mHandler.post(mProgressRunnable);

                        binding.videoPlayer.videoPlayerView.setVisibility(View.VISIBLE);
                        binding.videoPlayer.videoProgressLoading.setVisibility(View.GONE);
                        binding.videoPlayer.videoPlayerMask.setVisibility(View.GONE);
                        binding.videoPlayer.videoPlayerBg.setVisibility(View.VISIBLE);
                        createVideoControllerView();

                        mCurrentVideoControllerView.showWithTitle("");
                    } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                        binding.videoPlayer.videoProgressLoading.setVisibility(View.VISIBLE);
                    } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                        binding.videoPlayer.videoProgressLoading.setVisibility(View.GONE);
                    }
                }
            });
        } else if (null != mTravel && mTravel.getType().equals(Constants.TypeLeftMenu.PHOTO)) {
            stopPlaybackImmediately();
            binding.rlVideoPlayer.setVisibility(View.GONE);
            if (null != data.getPhotos() && data.getPhotos().size() > 0){
                binding.vpSlideShow.setVisibility(View.VISIBLE);
                binding.vpSlideShow.setPageTransformer(false, new CustPagerTransformer(getContext()));
                slideMomentAdapter = new SlideMomentPhotoAdapter(getContext(), data.getPhotos());
                binding.vpSlideShow.setAdapter(slideMomentAdapter);
//            slideMomentAdapter.notifyDataSetChanged();
            }

        } else {
            stopPlaybackImmediately();
            binding.rlVideoPlayer.setVisibility(View.GONE);
            binding.vpSlideShow.setVisibility(View.GONE);
        }
        binding.webContent.loadData(data.getDescription(), "text/html; charset=utf-8", "UTF-8");
        if (data.getInteresting() != null) {
            mDetailMomentFragmentAdapter = new DetailMomentFragmentAdapter(getContext(), data.getInteresting());
            binding.rvNewsSameCategory.setAdapter(mDetailMomentFragmentAdapter);
            binding.rvNewsSameCategoryPagerIndicator.attachToRecyclerView(binding.rvNewsSameCategory);
            mDetailMomentFragmentAdapter.setNewsSelectListener(this);
        } else {
            binding.tvTitleNewsSameCategory.setVisibility(View.GONE);
        }

        if (data.getBelongs() != null) {
            relationNewsAdapter = new RelationNewsAdapter(getContext(), data.getBelongs());
            binding.rvNewsRelated.setAdapter(relationNewsAdapter);
            binding.rvNewsRelatedPagerIndicator.attachToRecyclerView(binding.rvNewsRelated);
            relationNewsAdapter.setNewsSelectListener(this);
        } else {
            binding.tvTitleNewsRelated.setVisibility(View.GONE);
        }
        newsViewModel.loadComment(mTravel.getId(), page);

    }

    @Override
    public void onSelectNews(News news) {
        mTravel.setType(news.getType());
        showDialogLoading();
        newsViewModel.getNewsById(news.getDetail_link());
        binding.nesScroll.scrollTo(0, 0);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.toolBar.ivBack) {
            mActivity.onBackPressed();
        } else if (view == binding.tvComment) {
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin()) {
                CommentDialogFragment commentDialogFragment = CommentDialogFragment.newInstance();
                commentDialogFragment.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                commentDialogFragment.setPostCommentListener(this);
            } else {
                mActivity.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);
            }
        } else if (view == binding.ivBannerVideo) {
            if (null != detailNewsData) {
                mVideoPlayerManager.playNewVideo(new CurrentItemMetaData(0, view), binding.videoPlayer.videoPlayerView, detailNewsData.getStreaming_url());
            }

        }
    }

    @Override
    public void onPostComment(String comment, Integer parentId) {
        Account account = MyApplication.getInstance().getAccount();
        newsViewModel.postComment(account.getId(), comment, mTravel.getId(), mTravel.getContent_type(), parentId);
        showMessage(getString(R.string.send_comment_success));
    }

    private void createVideoControllerView() {

        if (mCurrentVideoControllerView != null) {
            mCurrentVideoControllerView.hide();
            mCurrentVideoControllerView = null;
        }

        mCurrentVideoControllerView = new VideoControllerView.Builder(getActivity(), mPlayerControlListener)
                .withVideoTitle("")
                .withVideoView(binding.videoPlayer.videoPlayerView)//to enable toggle display controller view
                .canControlBrightness(true)
                .canControlVolume(true)
                .canSeekVideo(false)
                .exitIcon(R.drawable.video_top_back)
                .pauseIcon(R.drawable.ic_media_pause)
                .playIcon(R.drawable.ic_media_play)
                .shrinkIcon(R.drawable.ic_media_fullscreen_shrink)
                .stretchIcon(R.drawable.ic_media_fullscreen_stretch)
                .build(binding.videoPlayer.layoutFloatVideoContainer);//layout container that hold video play view
    }

    private Runnable mProgressRunnable = new Runnable() {
        @Override
        public void run() {
            if (mPlayerControlListener != null) {
                if (mCurrentVideoControllerView.isShowing()) {
                    binding.videoPlayer.videoProgressBar.setVisibility(View.GONE);
                } else {
                    binding.videoPlayer.videoProgressBar.setVisibility(View.VISIBLE);
                }

                int position = mPlayerControlListener.getCurrentPosition();
                int duration = mPlayerControlListener.getDuration();
                if (duration != 0) {
                    long pos = 1000L * position / duration;
                    int percent = mPlayerControlListener.getBufferPercentage() * 10;
                    binding.videoPlayer.videoProgressBar.setProgress((int) pos);
                    binding.videoPlayer.videoProgressBar.setSecondaryProgress(percent);
                    mHandler.postDelayed(this, 1000);
                }
            }
        }
    };
    private VideoControllerView.MediaPlayerControlListener mPlayerControlListener = new VideoControllerView.MediaPlayerControlListener() {
        @Override
        public void start() {
            if (checkMediaPlayerInvalid())
                binding.videoPlayer.videoPlayerView.getMediaPlayer().start();
        }

        @Override
        public void pause() {
            try {
                binding.videoPlayer.videoPlayerView.getMediaPlayer().pause();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getDuration() {
            if (checkMediaPlayerInvalid()) {
                return binding.videoPlayer.videoPlayerView.getMediaPlayer().getDuration();
            }
            return 0;
        }

        @Override
        public int getCurrentPosition() {
            if (checkMediaPlayerInvalid()) {
                return binding.videoPlayer.videoPlayerView.getMediaPlayer().getCurrentPosition();
            }
            return 0;
        }

        @Override
        public void seekTo(int position) {
            if (checkMediaPlayerInvalid()) {
                binding.videoPlayer.videoPlayerView.getMediaPlayer().seekToPosition(position);
            }
        }

        @Override
        public boolean isPlaying() {
            if (checkMediaPlayerInvalid()) {
                return binding.videoPlayer.videoPlayerView.getMediaPlayer().isPlaying();
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
        return binding.videoPlayer.videoPlayerView != null && binding.videoPlayer.videoPlayerView.getMediaPlayer() != null;
    }

    public void stopPlaybackImmediately() {

        if (mVideoPlayerManager != null) {
            binding.videoPlayer.layoutFloatVideoContainer.setVisibility(View.INVISIBLE);
            mVideoPlayerManager.stopAnyPlayback();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mProgressRunnable);
        stopPlaybackImmediately();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mVideoPlayerManager) {
            mVideoPlayerManager.stopAnyPlayback();
        }
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.KeyBroadcast.KEY_LOGIN_SCREEN)) {
                Account account = MyApplication.getInstance().getAccount();
                setImageUrl(account.getImageProfile(), binding.avatar);
            }
        }

    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (binding.videoPlayer.layoutFloatVideoContainer == null) return;
        ViewGroup.LayoutParams layoutParams = binding.videoPlayer.layoutFloatVideoContainer.getLayoutParams();
        mCurrentVideoControllerView.hide();
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

//            binding.llVisible.setVisibility(View.VISIBLE);
//            binding.tvTitleNews.setVisibility(View.VISIBLE);
//            binding.llVisibleTop.setVisibility(View.VISIBLE);
//            binding.toolBar.myToolbar.setVisibility(View.VISIBLE);

            layoutParams.height = (int) getResources().getDimension(R.dimen._180sdp);
            layoutParams.width = Utils.getDeviceWidth(getActivity());
            ViewAnimator.putOn(binding.rlVideoPlayer).translationY(0);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {

//            binding.llVisible.setVisibility(View.GONE);
//            binding.tvTitleNews.setVisibility(View.GONE);
//            binding.llVisibleTop.setVisibility(View.GONE);
//            binding.toolBar.myToolbar.setVisibility(View.GONE);
            layoutParams.width = Utils.getDeviceWidth(getActivity());
            layoutParams.height = Utils.getDeviceHeight(getActivity());
            ViewAnimator.putOn(binding.rlVideoPlayer).translationY(0);

            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        binding.videoPlayer.layoutFloatVideoContainer.setLayoutParams(layoutParams);
        getView().invalidate();


    }
}

