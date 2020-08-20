package com.namviet.vtvtravel.model;

import com.daimajia.slider.library.Travel;

import java.util.List;

public class SearchNearSuggest {
    private String code;
    private String title;
    private String name;
    private String welcome;
    private List<NearItem> items;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWelcome() {
        return welcome;
    }

    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }

    public List<NearItem> getItems() {
        return items;
    }

    public void setItems(List<NearItem> items) {
        this.items = items;
    }
}
