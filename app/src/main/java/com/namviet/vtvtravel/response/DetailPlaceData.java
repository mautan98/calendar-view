package com.namviet.vtvtravel.response;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class DetailPlaceData {
    private String id;
    private String region_id;
    private String content_type;
    private String banner_url;
    private double lat;

    @SerializedName("long")
    private double lng;

    private List<Object> tabs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public List<Object> getTabs() {
        return tabs;
    }

    public void setTabs(List<Object> tabs) {
        this.tabs = tabs;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "DetailPlaceData{" +
                "id='" + id + '\'' +
                ", region_id='" + region_id + '\'' +
                ", content_type='" + content_type + '\'' +
                ", banner_url='" + banner_url + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", tabs=" + tabs +
                '}';
    }
}
