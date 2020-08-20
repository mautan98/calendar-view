package com.namviet.vtvtravel.response;

import com.daimajia.slider.library.Travel;

import java.util.List;

public class VideoMomentResponse extends BaseResponse {
    private List<Travel> data;

    public List<Travel> getData() {
        return data;
    }

    public void setData(List<Travel> data) {
        this.data = data;
    }
}
