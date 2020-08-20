package com.namviet.vtvtravel.model;

import java.util.List;

public class VideoPlace {
    private String code;
    private String title;
    private List<Video> items;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Video> getItems() {
        return items;
    }

    public void setItems(List<Video> items) {
        this.items = items;
    }
}
