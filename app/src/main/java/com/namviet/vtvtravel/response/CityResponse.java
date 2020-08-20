package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.City;

import java.util.ArrayList;
import java.util.List;

public class CityResponse extends BaseResponse {
    private ArrayList<City> data;

    public ArrayList<City> getData() {
        return data;
    }

    public void setData(ArrayList<City> data) {
        this.data = data;
    }
}
