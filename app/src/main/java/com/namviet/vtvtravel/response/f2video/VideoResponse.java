package com.namviet.vtvtravel.response.f2video;

import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class VideoResponse extends BaseResponse {
    private List<Item> data;

    public List<Item> getData() {
        return data;
    }

    public class Item{
        private String id;
        private String name;
        private String link;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getLink() {
            return link;
        }
    }

}
