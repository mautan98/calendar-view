package com.namviet.vtvtravel.videomanage.manager;


import com.namviet.vtvtravel.videomanage.PlayerMessageState;
import com.namviet.vtvtravel.videomanage.meta.MetaData;
import com.namviet.vtvtravel.videomanage.ui.VideoPlayerView;

public interface VideoPlayerManagerCallback {

    void setCurrentItem(MetaData currentItemMetaData, VideoPlayerView newPlayerView);

    void setVideoPlayerState(VideoPlayerView videoPlayerView, PlayerMessageState playerMessageState);

    PlayerMessageState getCurrentPlayerState();
}
