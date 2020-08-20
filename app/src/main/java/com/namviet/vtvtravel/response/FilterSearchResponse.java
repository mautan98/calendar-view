package com.namviet.vtvtravel.response;

import java.util.ArrayList;

public class FilterSearchResponse extends BaseResponse {
    private ArrayList<FilterSearchData> data;

    public ArrayList<FilterSearchData> getData() {
        return data;
    }

    public void setData(ArrayList<FilterSearchData> data) {
        this.data = data;
    }
}
