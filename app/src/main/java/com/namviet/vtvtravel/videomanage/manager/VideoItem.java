package com.namviet.vtvtravel.videomanage.manager;


import androidx.recyclerview.widget.RecyclerView;

import com.namviet.vtvtravel.videomanage.meta.MetaData;
import com.namviet.vtvtravel.videomanage.ui.VideoPlayerView;

/**
 * This is basic interface for Items in Adapter of the list. Regardless of is it {@link android.widget.ListView}
 * or {@link RecyclerView}
 */
public interface VideoItem {
    void playNewVideo(MetaData currentItemMetaData, VideoPlayerView player, VideoPlayerManager<MetaData> videoPlayerManager);
    void stopPlayback(VideoPlayerManager videoPlayerManager);
}
