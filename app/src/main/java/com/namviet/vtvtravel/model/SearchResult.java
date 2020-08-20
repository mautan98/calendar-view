package com.namviet.vtvtravel.model;

import com.daimajia.slider.library.Travel;

import java.util.List;

public class SearchResult {
    private String name;
    private String content_type;
    private List<Travel> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public List<Travel> getItems() {
        return items;
    }

    public void setItems(List<Travel> items) {
        this.items = items;
    }
}
