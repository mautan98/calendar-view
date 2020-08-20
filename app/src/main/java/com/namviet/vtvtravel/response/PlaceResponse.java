package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.ListPlace;

import java.util.List;

public class PlaceResponse extends BaseResponse {
    private ListPlace data;

    public ListPlace getData() {
        return data;
    }

    public void setData(ListPlace data) {
        this.data = data;
    }
}
