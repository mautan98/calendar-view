package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.Video;

import java.util.ArrayList;

public class PlaylistVideoResponse extends BaseResponse {
    private ArrayList<Video> data;

    public ArrayList<Video> getData() {
        return data;
    }

    public void setData(ArrayList<Video> data) {
        this.data = data;
    }
}
