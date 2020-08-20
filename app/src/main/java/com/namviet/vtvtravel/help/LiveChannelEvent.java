package com.namviet.vtvtravel.help;

import com.namviet.vtvtravel.model.LiveChannel;

public class LiveChannelEvent {
    private LiveChannel mLiveChannel;

    public LiveChannelEvent(LiveChannel customItem) {
        mLiveChannel = customItem;
    }

    public LiveChannel getmLiveChannel() {
        return mLiveChannel;
    }

    public void setmLiveChannel(LiveChannel customItem) {
        mLiveChannel = customItem;
    }
}
