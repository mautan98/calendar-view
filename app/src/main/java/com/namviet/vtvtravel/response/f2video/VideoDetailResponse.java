package com.namviet.vtvtravel.response.f2video;

import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class VideoDetailResponse extends BaseResponse {
    private List<Video> data;

    public void setData(List<Video> data) {
        this.data = data;
    }

    public List<Video> getData() {
        return data;
    }


}
