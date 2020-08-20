package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.NearItem;

import java.util.List;

public class NotificationCategoriesResponse extends BaseResponse {
    private List<NearItem> data;

    public List<NearItem> getData() {
        return data;
    }

    public void setData(List<NearItem> data) {
        this.data = data;
    }
}
