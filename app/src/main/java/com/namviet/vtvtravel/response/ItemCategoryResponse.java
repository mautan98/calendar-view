package com.namviet.vtvtravel.response;

public class ItemCategoryResponse extends BaseResponse {
    private ItemCategoryData data;

    public ItemCategoryData getData() {
        return data;
    }

    public void setData(ItemCategoryData data) {
        this.data = data;
    }
}
