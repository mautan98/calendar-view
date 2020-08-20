package com.namviet.vtvtravel.response;

import com.daimajia.slider.library.Travel;

import java.util.List;

public class ItemCategoryData {
    private int total_count;
    private String id;
    private String code;
    private List<Travel> items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<Travel> getItems() {
        return items;
    }

    public void setItems(List<Travel> items) {
        this.items = items;
    }

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
}
