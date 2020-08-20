package com.namviet.vtvtravel.response.f2biglocation;

import com.namviet.vtvtravel.model.travelnews.Location;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class AllLocationResponse extends BaseResponse {
    private List<Location> data;

    public List<Location> getData() {
        return data;
    }
}
