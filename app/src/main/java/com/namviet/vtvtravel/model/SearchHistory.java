package com.namviet.vtvtravel.model;

import java.util.List;

public class SearchHistory {
    private String code;
    private String title;
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

    public List<NearItem> getItems() {
        return items;
    }

    public void setItems(List<NearItem> items) {
        this.items = items;
    }
}
