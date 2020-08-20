package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.Banner;

import java.util.List;

public class BannerResponse extends BaseResponse {
    private List<Banner> data;

    public List<Banner> getData() {
        return data;
    }

    public void setData(List<Banner> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BannerResponse{" +
                "data=" + data +
                '}';
    }
}
