package com.namviet.vtvtravel.response.newhome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.response.BaseResponse;

public class ConfigPopupResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public  class Data {

        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("banner_url")
        private String banner_url;
        @Expose
        @SerializedName("banner_greeting")
        private String banner_greeting;
        @Expose
        @SerializedName("icon_greeting_url")
        private String icon_greeting_url;
        @Expose
        @SerializedName("greeting")
        private String greeting;
        @Expose
        @SerializedName("distance")
        private String distance;
        @Expose
        @SerializedName("lat")
        private String lat;
        @Expose
        @SerializedName("long")
        private String lng;
        @Expose
        @SerializedName("name_ascii")
        private String name_ascii;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private String id;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBanner_url() {
            return banner_url;
        }

        public void setBanner_url(String banner_url) {
            this.banner_url = banner_url;
        }

        public String getBanner_greeting() {
            return banner_greeting;
        }

        public void setBanner_greeting(String banner_greeting) {
            this.banner_greeting = banner_greeting;
        }

        public String getIcon_greeting_url() {
            return icon_greeting_url;
        }

        public void setIcon_greeting_url(String icon_greeting_url) {
            this.icon_greeting_url = icon_greeting_url;
        }

        public String getGreeting() {
            return greeting;
        }

        public void setGreeting(String greeting) {
            this.greeting = greeting;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getName_ascii() {
            return name_ascii;
        }

        public void setName_ascii(String name_ascii) {
            this.name_ascii = name_ascii;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
