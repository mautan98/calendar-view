package com.namviet.vtvtravel.response;

public class DetailNewsResponse extends BaseResponse {
    private DetailNewsData data;

    public DetailNewsData getData() {
        return data;
    }

    public void setData(DetailNewsData data) {
        this.data = data;
    }
}
