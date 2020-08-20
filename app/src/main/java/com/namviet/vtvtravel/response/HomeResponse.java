package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.CategoryHome;

import java.util.List;

public class HomeResponse extends BaseResponse {
    private List<CategoryHome> data;

    public List<CategoryHome> getData() {
        return data;
    }

    public void setData(List<CategoryHome> data) {
        this.data = data;
    }
}
