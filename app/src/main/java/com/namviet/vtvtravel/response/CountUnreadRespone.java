package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.CountUnread;

public class CountUnreadRespone extends BaseResponse {
    private CountUnread data;

    public CountUnread getData() {
        return data;
    }

    public void setData(CountUnread data) {
        this.data = data;
    }
}
