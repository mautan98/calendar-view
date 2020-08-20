package com.namviet.vtvtravel.response.travelnews;

import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class NewsCategoryResponse extends BaseResponse {
    private List<Item> data;

    public void setData(List<Item> data) {
        this.data = data;
    }

    public List<Item> getData() {
        return data;


    }

    public class Item {
        private String id;
        private String code;
        private String name;
        private String link;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
