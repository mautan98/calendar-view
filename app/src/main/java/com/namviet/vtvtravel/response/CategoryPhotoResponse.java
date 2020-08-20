package com.namviet.vtvtravel.response;

import java.util.List;

public class CategoryPhotoResponse extends BaseResponse {
    private List<CategoryItem> data;

    public List<CategoryItem> getData() {
        return data;
    }

    public void setData(List<CategoryItem> data) {
        this.data = data;
    }
}
