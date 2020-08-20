package com.namviet.vtvtravel.model.offline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Items {
    @Expose
    @SerializedName("specialText")
    private List<String> specialText;
    @Expose
    @SerializedName("icon")
    private String icon;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("subTitle")
    private String subTitle;
    @Expose
    @SerializedName("action")
    private Action action;
    @Expose
    @SerializedName("button")
    private String button;

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<String> getSpecialText() {
        return specialText;
    }

    public void setSpecialText(List<String> specialText) {
        this.specialText = specialText;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
