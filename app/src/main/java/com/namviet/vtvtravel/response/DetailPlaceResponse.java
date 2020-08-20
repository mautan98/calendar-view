package com.namviet.vtvtravel.response;

public class DetailPlaceResponse extends BaseResponse {
    private DetailPlaceData data;

    public DetailPlaceData getData() {
        return data;
    }

    public void setData(DetailPlaceData data) {
        this.data = data;
    }
}
