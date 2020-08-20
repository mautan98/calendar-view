package com.namviet.vtvtravel.response;

public class NewsResponse extends BaseResponse {
    private NewsData data;

    public NewsData getData() {
        return data;
    }

    public void setData(NewsData data) {
        this.data = data;
    }
}
