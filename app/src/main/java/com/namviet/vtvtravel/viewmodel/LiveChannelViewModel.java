package com.namviet.vtvtravel.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.namviet.vtvtravel.model.channel.LiveChannelData;
import com.namviet.vtvtravel.response.LiveChannelResponse;

public class LiveChannelViewModel extends ViewModel {
    private MutableLiveData<LiveChannelData> mLiveChannelData;
    private LiveChannelResponse mLiveChannelResponse;

    public LiveChannelViewModel() {
        mLiveChannelResponse = new LiveChannelResponse();
    }

    public void init(){
        if (this.mLiveChannelData != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        mLiveChannelData = mLiveChannelResponse.getLiveChannels();
    }

    public MutableLiveData<LiveChannelData> getMovies() {
        return this.mLiveChannelData;
    }
}
