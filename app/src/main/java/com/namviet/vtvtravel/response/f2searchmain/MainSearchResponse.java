package com.namviet.vtvtravel.response.f2searchmain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class MainSearchResponse extends BaseResponse {

    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {

        @Expose
        @SerializedName("link")
        private String link;
        @Expose
        @SerializedName("title")
        private String title;
        @Expose
        @SerializedName("code")
        private String code;

        private List<Item> items;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }
    }

    public static class Item {

        @Expose
        @SerializedName("link")
        private String link;
        @Expose
        @SerializedName("code")
        private String code;
        @Expose
        @SerializedName("content_type")
        private String content_type;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("icon_url")
        private String icon_url;

        @Expose
        @SerializedName("banner_url")
        private String banner_url;

        public String getBanner_url() {
            return banner_url;
        }

        public void setBanner_url(String banner_url) {
            this.banner_url = banner_url;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getContent_type() {
            return content_type;
        }

        public void setContent_type(String content_type) {
            this.content_type = content_type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
        }
    }

}
