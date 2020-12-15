package com.namviet.vtvtravel.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.namviet.vtvtravel.model.channel.DetailLiveChannelDataM;
import com.namviet.vtvtravel.response.DetailLiveChannelResponse;

public class DetailLiveChannelViewModel extends ViewModel {

    private MutableLiveData<DetailLiveChannelDataM.DetailLiveChannelData> mDetailLiveChannelData;
    private DetailLiveChannelResponse mLiveChannelResponse;

    public DetailLiveChannelViewModel() {
        mLiveChannelResponse = new DetailLiveChannelResponse();
    }

    public void init(String channel) {
        if (this.mDetailLiveChannelData != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            //return;
        }
        mDetailLiveChannelData = mLiveChannelResponse.getDetailLiveChannels(channel);
    }

    public MutableLiveData<DetailLiveChannelDataM.DetailLiveChannelData> getMovies() {
        return this.mDetailLiveChannelData;
    }
}
