package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.ItemTravel;

import java.util.List;

public class SearchResponse extends BaseResponse {

    private List<Object> data;

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SearchResponse{" +
                "data=" + data +
                '}';
    }
}
