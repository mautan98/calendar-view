package com.namviet.vtvtravel.listener;

import com.namviet.vtvtravel.model.City;
import com.namviet.vtvtravel.model.Filter;
import com.namviet.vtvtravel.response.FilterData;

import java.util.ArrayList;

public interface FilterSelectListener {
    void onSelect(ArrayList<FilterData> list);

    void onSelectItem(Filter filter, String title);
}
