package com.namviet.vtvtravel.model;

import com.daimajia.slider.library.Travel;

import java.util.List;

public class Suggestion {
    private String code;
    private String title;
    private List<Travel> items;

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

    public List<Travel> getItems() {
        return items;
    }

    public void setItems(List<Travel> items) {
        this.items = items;
    }
}
