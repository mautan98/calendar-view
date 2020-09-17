package com.namviet.vtvtravel.response.f2searchmain;

import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.response.BaseResponse;

public class SubBaseSearch<T> extends BaseResponse {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
