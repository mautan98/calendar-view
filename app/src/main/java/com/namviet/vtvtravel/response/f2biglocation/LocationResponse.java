package com.namviet.vtvtravel.response.f2biglocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.model.travelnews.Location;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class LocationResponse extends BaseResponse {
    private Location data;

    public Location getData() {
        return data;
    }

}
