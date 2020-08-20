package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.News;

import java.util.List;

public class SlideShowResponse extends BaseResponse {
    private List<News> data;

    public List<News> getData() {
        return data;
    }

    public void setData(List<News> data) {
        this.data = data;
    }
}
