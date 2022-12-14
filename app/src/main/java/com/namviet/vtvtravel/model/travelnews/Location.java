package com.namviet.vtvtravel.model.travelnews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Location {
    //
    private boolean isSelected;
    //

    private String id;
    private String name;
    private String name_ascii;

    @Expose
    @SerializedName("long")
    private String lng;
    private String lat;
    private String distance;
    private String greeting;
    private String banner_greeting;
    private String banner_url;
    private String description;
    private List<String> hashtags;
    private String content_link;
    private String selected;

    public Location(String name) {
        this.name = name;
    }

    public Location() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getName_ascii() {
        return name_ascii;
    }

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }

    public String getDistance() {
        return distance;
    }

    public String getGreeting() {
        return greeting;
    }

    public String getBanner_greeting() {
        return banner_greeting;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public String getContent_link() {
        return content_link;
    }

    public String getSelected() {
        return selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
