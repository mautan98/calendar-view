package com.namviet.vtvtravel.videomanage.playermessages;

import android.media.MediaPlayer;

import com.namviet.vtvtravel.videomanage.PlayerMessageState;
import com.namviet.vtvtravel.videomanage.manager.VideoPlayerManagerCallback;
import com.namviet.vtvtravel.videomanage.ui.VideoPlayerView;



public class Pause extends PlayerMessage {
    public Pause(VideoPlayerView videoView, VideoPlayerManagerCallback callback) {
        super(videoView, callback);
    }

    @Override
    protected void performAction(VideoPlayerView currentPlayer) {
        currentPlayer.pause();
    }

    @Override
    protected PlayerMessageState stateBefore() {
        return PlayerMessageState.PAUSING;
    }

    @Override
    protected PlayerMessageState stateAfter() {
        return PlayerMessageState.PAUSED;
    }
}
