package com.namviet.vtvtravel.response.newhome;

import com.namviet.vtvtravel.model.newhome.ItemHomeService;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class HomeServiceResponse extends BaseResponse {
    private List<ItemHomeService> data;

    public List<ItemHomeService> getData() {
        return data;
    }

    public void setData(List<ItemHomeService> data) {
        this.data = data;
    }
}
