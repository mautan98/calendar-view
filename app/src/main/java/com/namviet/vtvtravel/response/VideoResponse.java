package com.namviet.vtvtravel.response;

public class VideoResponse extends BaseResponse {
    private VideoData data;

    public VideoData getData() {
        return data;
    }

    public void setData(VideoData data) {
        this.data = data;
    }
}
