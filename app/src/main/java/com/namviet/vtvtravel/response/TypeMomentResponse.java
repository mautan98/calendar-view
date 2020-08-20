package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.Filter;

import java.util.List;

public class TypeMomentResponse extends BaseResponse {
    private List<Filter> data;

    public List<Filter> getData() {
        return data;
    }

    public void setData(List<Filter> data) {
        this.data = data;
    }
}
