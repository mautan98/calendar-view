package com.namviet.vtvtravel.view.fragment.travel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import androidx.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.baseapp.menu.SlideMenu;
import com.daimajia.slider.library.Travel;
import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.CommentAdapter;
import com.namviet.vtvtravel.adapter.NearPlaceAdapter;
import com.namviet.vtvtravel.adapter.PhotoGalleryAdapter;
import com.namviet.vtvtravel.adapter.SuggestPlaceAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentDetailWhereBinding;
import com.namviet.vtvtravel.listener.PhotoSelectListener;
import com.namviet.vtvtravel.listener.PlaceSelectLisntener;
import com.namviet.vtvtravel.listener.PostCommentListener;
import com.namviet.vtvtravel.model.AboutPlace;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.Comment;
import com.namviet.vtvtravel.model.Gallery;
import com.namviet.vtvtravel.model.ItemWeather;
import com.namviet.vtvtravel.model.NearPlace;
import com.namviet.vtvtravel.model.Suggestion;
import com.namviet.vtvtravel.model.TabItem;
import com.namviet.vtvtravel.model.VideoPlace;
import com.namviet.vtvtravel.response.CommentResponse;
import com.namviet.vtvtravel.response.DetailPlaceData;
import com.namviet.vtvtravel.response.DetailPlaceResponse;
import com.namviet.vtvtravel.response.PostCommentResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.videomanage.controller.VideoControllerView;
import com.namviet.vtvtravel.videomanage.manager.SingleVideoPlayerManager;
import com.namviet.vtvtravel.videomanage.manager.VideoPlayerManager;
import com.namviet.vtvtravel.videomanage.meta.CurrentItemMetaData;
import com.namviet.vtvtravel.videomanage.meta.MetaData;
import com.namviet.vtvtravel.videomanage.ui.MediaPlayerWrapper;
import com.namviet.vtvtravel.videomanage.utils.Utils;
import com.namviet.vtvtravel.view.dialog.CommentDialogFragment;
import com.namviet.vtvtravel.view.dialog.WeatherDialog;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.view.fragment.WebviewFragment;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;
import com.namviet.vtvtravel.widget.CustomNestedScrollView;
import com.namviet.vtvtravel.widget.NestedScrollViewListener;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class DetailWhereFragment extends MainFragment implements Observer, PlaceSelectLisntener, NestedScrollViewListener, PostCommentListener, PhotoSelectListener {
    private FragmentDetailWhereBinding binding;
    private ArrayList<TabItem> arrTabs = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private PhotoGalleryAdapter photoGalleryAdapter;
    private SuggestPlaceAdapter suggestPlaceAdapter;
    private NearPlaceAdapter nearPlaceAdapter;
    private PlaceViewModel placeViewModel;
    private Travel mTravel;
    private int page = 0;
    private int totalPage;
    private boolean isLoadingMore = true;
    private List<Comment> listComment = new ArrayList<>();
    private int countComment = 0;
    private ArrayList<ItemWeather> weatherList;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private VideoControllerView mCurrentVideoControllerView;
    private int mCurrentBuffer;
    private VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(null);
    private VideoPlace videoPlace;
    private String title;
    private final String URL_API = "https://api.travel.onex.vn/pages/5d48fcd8190d81ad1c8b456c";
    public static long mLastClickTime = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mTravel = getArguments().getParcelable(Constants.IntentKey.KEY_TRAVEL);
            title = getArguments().getString(Constants.IntentKey.KEY_TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_where, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.myToolbar);
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
        placeViewModel = new PlaceViewModel(getContext());
        binding.setPlaceViewModel(placeViewModel);
        placeViewModel.addObserver(this);
        if (title != null) {
            binding.tvTitle.setText(title);
        } else {
            binding.tvTitle.setText(getString(R.string.tv_go));
        }
        binding.ivSearch.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        binding.ivBannerVideo.setOnClickListener(this);
        binding.tvComment.setOnClickListener(this);
        binding.tvTemp.setOnClickListener(this);
        binding.ivArrow.setOnClickListener(this);
        binding.nesScroll.setScrollViewListener(this);
        binding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                double percentage = (double) Math.abs(verticalOffset) / binding.myToolbar.getHeight();
                if (percentage > 0.8) {

                } else {

                }
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.KeyBroadcast.KEY_LOGIN_SCREEN);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, intentFilter);
        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            setImageUrl(account.getImageProfile(), binding.avatar);
        }
        if (null != mActivity.getWeatherList() && mActivity.getWeatherList().size() > 0) {
            weatherList = mActivity.getWeatherList();
            if (Math.round(weatherList.get(0).getTemp_min()) == Math.round(weatherList.get(0).getTemp_max())) {
                binding.tvTemp.setText(Math.round(weatherList.get(0).getTemp_min()) + " °C");
            } else {
                binding.tvTemp.setText(Math.round(weatherList.get(0).getTemp_min()) + " - " + Math.round(weatherList.get(0).getTemp_max()) + " °C");
            }
            setImageUrl(weatherList.get(0).getWeather().getIcon_url(), binding.ivIconWeather);
        }
        binding.webDes.getSettings().setJavaScriptEnabled(true);
        binding.rvPhotos.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.rvPhotos.setNestedScrollingEnabled(false);

        binding.rvSuggest.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        binding.rvNearPlace.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvNearPlace.setNestedScrollingEnabled(false);


        binding.rvComment.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvComment.setNestedScrollingEnabled(false);
        commentAdapter = new CommentAdapter(mActivity, listComment, this);
        binding.rvComment.setAdapter(commentAdapter);

        if (null != mTravel) {
            showDialogLoading();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    placeViewModel.loadDetailPlace(mTravel.getDetail_link());
                }
            }, Constants.TimeDelay);
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
    public void onClick(View view) {
        super.onClick(view);
        if (view == binding.ivBack) {
            mActivity.onBackPressed();
        } else if (view == binding.tvComment) {
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin()) {
                CommentDialogFragment commentDialogFragment = CommentDialogFragment.newInstance();
                commentDialogFragment.show(mActivity.getSupportFragmentManager(), "comment_dialog_fragment");
                commentDialogFragment.setPostCommentListener(this);
            } else {
                mActivity.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);
            }
        } else if (view == binding.tvTemp) {
            if (null != mActivity.getWeatherList()) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                WeatherDialog weatherDialog = WeatherDialog.newInstance(mActivity.getWeatherList());
                weatherDialog.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
            }
        } else if (view == binding.ivBannerVideo) {
            if (null != videoPlace) {
                mVideoPlayerManager.playNewVideo(new CurrentItemMetaData(0, view), binding.videoPlayer.videoPlayerView, videoPlace.getItems().get(0).getSource_url());
            }
        } else if (view == binding.ivArrow) {
            binding.tvTemp.performClick();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setUiTab() {
        binding.tabLayout.removeAllTabs();

        for (int i = 0; i < arrTabs.size(); i++) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(arrTabs.get(i).getTitle()));
        }
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                binding.nesScroll.scrollTo(0, arrTabs.get(position).getmView().getTop());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void update(Observable observable, final Object o) {
        dimissDialogLoading();
        if (observable instanceof PlaceViewModel) {
            if (o != null) {
                if (o instanceof DetailPlaceResponse) {
                    DetailPlaceResponse detailPlaceResponse = (DetailPlaceResponse) o;
                    if (detailPlaceResponse.isSuccess() || "SUCCESS".equals(detailPlaceResponse.getCode())){
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateUI((DetailPlaceResponse) o);
                            }
                        }, 1000);
                    } else {
                        showMessage(detailPlaceResponse.getMessage());
                    }

                } else if (o instanceof CommentResponse) {
                    CommentResponse commentResponse = (CommentResponse) o;
                    totalPage = commentResponse.getData().getTotalPages();
                    countComment = commentResponse.getData().getTotalElements();
                    binding.tvTitleComment.setText(getText(R.string.comment) + " (" + countComment + ")");
                    listComment.addAll(commentResponse.getData().getContent());
                    commentAdapter.notifyDataSetChanged();
                } else if (o instanceof PostCommentResponse) {
                    countComment++;
                    binding.tvTitleComment.setText(getText(R.string.comment) + " (" + countComment + ")");
                    PostCommentResponse response = (PostCommentResponse) o;
                    Account account = MyApplication.getInstance().getAccount();
                    Comment comment = response.getData();
                    comment.setUser(account);
                    listComment.add(0, comment);
                    commentAdapter.notifyDataSetChanged();
                }  else if (o instanceof ResponseError) {
                    ResponseError responseError = (ResponseError) o;
                    showMessage(responseError.getMessage());
                }
            } else {
            }
        }
        mHandler.removeCallbacks(mProgressRunnable);
    }

    private void updateUI(DetailPlaceResponse response) {
        dimissDialogLoading();
        arrTabs.clear();
        DetailPlaceData data = response.getData();
        setImageUrl(data.getBanner_url(), binding.ivBanner);
        if (null != data && null != data.getTabs() && data.getTabs().size() > 0){
            for (Object obj : data.getTabs()) {
                String json = new Gson().toJson(obj);
                try {
                    JSONObject tabItem = new JSONObject(json);
                    if (tabItem.getString(Constants.KeyJSON.CODE).equals(Constants.TypeDetail.ABOUT)) {
                        AboutPlace aboutPlace = new Gson().fromJson(json, AboutPlace.class);
                        arrTabs.add(new TabItem(aboutPlace.getTitle(), binding.tvTitleWhere));
                        binding.tvTitleWhere.setText(aboutPlace.getName());
                        binding.tvAddress.setText(aboutPlace.getAddress());

                        String webViewContent = tabItem.getJSONObject("footer").getString("description");
                        String webViewUrl = tabItem.getJSONObject("footer").getString("url");
                        String webViewUrlApi = tabItem.getJSONObject("footer").getString("url_api");
                        if (webViewContent.length() > 0 && webViewUrl.length() > 0){
                            binding.webDes.loadData(aboutPlace.getDescription() + webViewContent, "text/html; charset=utf-8", "UTF-8");
                            binding.webDes.setWebViewClient(new WebViewClient() {
                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    if (url.startsWith(webViewUrl)) {
                                        Bundle bundle = new Bundle();

                                            bundle.putString(WebviewFragment.URL_WEBVIEW, webViewUrlApi);

                                        mActivity.setBundle(bundle);
                                        mActivity.switchFragment(SlideMenu.MenuType.WEBVIEW_SCREEN);
                                        return true;
                                    }
                                    return false;
                                }
                            });
                        }else {
                            binding.webDes.loadData(aboutPlace.getDescription(), "text/html; charset=utf-8", "UTF-8");
                        }

                    } else if (tabItem.getString(Constants.KeyJSON.CODE).equals(Constants.TypeDetail.GALLERY)) {
                        Gallery gallery = new Gson().fromJson(json, Gallery.class);
                        arrTabs.add(new TabItem(gallery.getTitle(), binding.tvTitlePhoto));
                        photoGalleryAdapter = new PhotoGalleryAdapter(getContext(), true, gallery.getItems());
                        binding.rvPhotos.setAdapter(photoGalleryAdapter);
                        photoGalleryAdapter.setPhotoSelectListener(this);
                    } else if (tabItem.getString(Constants.KeyJSON.CODE).equals(Constants.TypeDetail.SUGGESTION)) {
                        Suggestion suggestion = new Gson().fromJson(json, Suggestion.class);
                        arrTabs.add(new TabItem(suggestion.getTitle(), binding.tvTitleSuggest));
                        suggestPlaceAdapter = new SuggestPlaceAdapter(getContext(), suggestion.getItems());
                        binding.rvSuggest.setAdapter(suggestPlaceAdapter);
                        suggestPlaceAdapter.setPlaceSelectLisntener(this);
                    } else if (tabItem.getString(Constants.KeyJSON.CODE).equals(Constants.TypeDetail.VIDEO)) {
                        videoPlace = new Gson().fromJson(json, VideoPlace.class);
                        arrTabs.add(new TabItem(videoPlace.getTitle(), binding.tvTitleVideo));
                        binding.tvTitleVideo.setVisibility(View.VISIBLE);
                        binding.rlVideoPlayer.setVisibility(View.VISIBLE);
                        if (videoPlace.getItems().size() > 0) {
                            setImageUrl(videoPlace.getItems().get(0).getPoster_url(), binding.ivBannerVideo);
                        }
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
                    } else if (tabItem.getString(Constants.KeyJSON.CODE).equals(Constants.TypeDetail.NEARPOINT)) {
                        NearPlace nearPlace = new Gson().fromJson(json, NearPlace.class);
                        arrTabs.add(new TabItem(nearPlace.getTitle(), binding.tvTitleNearPlace));
                        nearPlaceAdapter = new NearPlaceAdapter(getContext(), nearPlace.getItems());
                        binding.rvNearPlace.setAdapter(nearPlaceAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        binding.appBar.setExpanded(true);
        setUiTab();
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

    @Override
    public void onSelect(Travel travel) {
        showDialogLoading();
        arrTabs.clear();
        placeViewModel.loadDetailPlace(travel.getDetail_link());
    }

    @Override
    public void onScrollChanged(CustomNestedScrollView scrollView, int x, int y, int oldx, int oldy) {
        View view = scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
        if (diff == 0) {
            if (isLoadingMore) {
                isLoadingMore = false;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //load comment
                        placeViewModel.loadComment(mTravel.getId(), page);
                        page++;
                    }
                }, 500);

            }
        }
    }

    @Override
    public void onPostComment(String comment, Integer parentId) {
        Account account = MyApplication.getInstance().getAccount();
        placeViewModel.postComment(account.getId(), comment, mTravel.getId(), mTravel.getContent_type(), parentId);
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
    public void onSelect() {
        if (null != photoGalleryAdapter) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(Constants.IntentKey.KEY_FRAGMENT, photoGalleryAdapter.getListPhoto());
            mActivity.setBundle(bundle);
            mActivity.switchFragment(SlideMenu.MenuType.PHOTO_GALLERY_SCREEN);
        }
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (binding.videoPlayer.layoutFloatVideoContainer == null) return;
        ViewGroup.LayoutParams layoutParams = binding.videoPlayer.layoutFloatVideoContainer.getLayoutParams();
        mCurrentVideoControllerView.hide();
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutParams.height = (int) getResources().getDimension(R.dimen._180sdp);
            layoutParams.width = Utils.getDeviceWidth(getActivity());
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            layoutParams.height = Utils.getDeviceHeight(getActivity());
            layoutParams.width = Utils.getDeviceWidth(getActivity());
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        binding.videoPlayer.layoutFloatVideoContainer.setLayoutParams(layoutParams);
        getView().invalidate();
    }
}
