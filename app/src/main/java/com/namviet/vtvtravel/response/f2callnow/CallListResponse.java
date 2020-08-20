package com.namviet.vtvtravel.response.f2callnow;

import com.namviet.vtvtravel.model.f2callnow.CallList;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class CallListResponse extends BaseResponse {
    private List<CallList> data;

    public List<CallList> getData() {
        return data;
    }

    public void setData(List<CallList> data) {
        this.data = data;
    }

}
