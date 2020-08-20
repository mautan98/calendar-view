package com.namviet.vtvtravel.view.fragment.f2video;

import android.graphics.Color;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longtailvideo.jwplayer.configuration.PlayerConfig;
import com.longtailvideo.jwplayer.events.FullscreenEvent;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2video.TagVideoAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentDetailVideoBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.response.travelnews.DetailTravelNewsResponse;
import com.namviet.vtvtravel.ultils.DateUtltils;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.ultils.JWEventHandler;
import com.namviet.vtvtravel.ultils.KeepScreenOnHandler;
import com.namviet.vtvtravel.ultils.ViewMoreUtils;
import com.namviet.vtvtravel.view.f2.CommentActivity;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class DetailVideoFragment extends BaseFragment<F2FragmentDetailVideoBinding> implements VideoPlayerEvents.OnFullscreenListener {
    private Video video;

    public DetailVideoFragment() {
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_detail_video;
    }

    @Override
    public void initView() {
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
            getBinding().tvNumberOfComment.setText(video.getComment_count());
            getBinding().tvDescription.setText(video.getShort_description());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initJWPlayer("");
        try {
            if (video.getShort_description().length() > 72){
                ViewMoreUtils.makeTextViewResizable(getBinding().tvDescription, 2, "Xem thêm", true, "#FFFFFF");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initData() {

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
                    F2Util.startSenDataText(mActivity, video.getLink_share());
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
}
