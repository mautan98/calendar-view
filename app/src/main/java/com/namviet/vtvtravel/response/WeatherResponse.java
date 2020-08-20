package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.ItemWeather;

import java.util.ArrayList;

public class WeatherResponse extends BaseResponse {
    private ArrayList<ItemWeather> data;

    public ArrayList<ItemWeather> getData() {
        return data;
    }

    public void setData(ArrayList<ItemWeather> data) {
        this.data = data;
    }
}
