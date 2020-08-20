package com.namviet.vtvtravel.model.channel;

import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.model.LiveChannel;

import java.util.List;

public class LiveChannelData {
    @SerializedName("data")
    private List<LiveChannel> data;

    public List<LiveChannel> getData() {
        return data;
    }

    public void setData(List<LiveChannel> data) {
        this.data = data;
    }
}
