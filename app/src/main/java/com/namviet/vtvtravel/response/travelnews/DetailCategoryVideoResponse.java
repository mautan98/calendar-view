package com.namviet.vtvtravel.response.travelnews;

import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.model.travelnews.Travel;

import java.util.List;

public class DetailCategoryVideoResponse {
    private DetailCategoryVideoResponse.Data data;

    public DetailCategoryVideoResponse.Data getData() {
        return data;
    }

    public class Data{
        private List<Video> items;

        public List<Video> getItems() {
            return items;
        }

        public void setItems(List<Video> items) {
            this.items = items;
        }
    }
}
