package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.FoodCate;

import java.util.List;

public class CategoryResponse extends BaseResponse {
    private List<FoodCate> data;

    public List<FoodCate> getData() {
        return data;
    }

    public void setData(List<FoodCate> data) {
        this.data = data;
    }
}
