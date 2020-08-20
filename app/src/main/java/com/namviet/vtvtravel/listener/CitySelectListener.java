package com.namviet.vtvtravel.listener;

import com.namviet.vtvtravel.model.City;

import java.util.ArrayList;

public interface CitySelectListener {
    void onSelect(ArrayList<City> list, City city);
}
