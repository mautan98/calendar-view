package com.namviet.vtvtravel.model.offline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class PopupScreen {

    @Expose
    @SerializedName("items")
    private List<ItemsPopup> items;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("banner")
    private Banner banner;

    public List<ItemsPopup> getItems() {
        return items;
    }

    public void setItems(List<ItemsPopup> items) {
        this.items = items;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }
}
