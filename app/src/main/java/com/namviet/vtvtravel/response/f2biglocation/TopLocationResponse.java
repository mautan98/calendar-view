package com.namviet.vtvtravel.response.f2biglocation;

import com.namviet.vtvtravel.model.travelnews.Location;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class TopLocationResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data{
        private String title;
        private List<Location> items;

        public String getTitle() {
            return title;
        }

        public List<Location> getItems() {
            return items;
        }
    }
}
