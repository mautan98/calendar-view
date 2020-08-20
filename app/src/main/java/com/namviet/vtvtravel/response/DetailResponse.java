package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.Details;

public class DetailResponse extends BaseResponse {
    private Details data;

    public Details getData() {
        return data;
    }

    public void setData(Details data) {
        this.data = data;
    }
}
