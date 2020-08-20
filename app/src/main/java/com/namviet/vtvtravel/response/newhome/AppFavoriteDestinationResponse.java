package com.namviet.vtvtravel.response.newhome;

import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class AppFavoriteDestinationResponse extends BaseResponse {

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public class Item {
        private String id;
        private String name;
        private String banner_greeting;
        private String banner_url;

        public String getName() {
            return name;
        }

        public String getBanner_greeting() {
            return banner_greeting;
        }

        public String getBanner_url() {
            return banner_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setBanner_greeting(String banner_greeting) {
            this.banner_greeting = banner_greeting;
        }

        public void setBanner_url(String banner_url) {
            this.banner_url = banner_url;
        }
    }

}
