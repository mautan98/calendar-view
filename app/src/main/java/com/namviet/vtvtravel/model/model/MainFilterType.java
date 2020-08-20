package com.namviet.vtvtravel.model.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class MainFilterType {

    @Expose
    @SerializedName("link")
    private String link;
    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("content_type")
    private String content_type;
    @Expose
    @SerializedName("name")
    private String name;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
