package com.namviet.vtvtravel.model;

import com.google.gson.annotations.SerializedName;

public class LiveChannel {
    @SerializedName("id")
    private String id;

    @SerializedName("content_type")
    private String content_type;

    @SerializedName("code")
    private String code;

    @SerializedName("name")
    private String name;

    @SerializedName("logo_url")
    private String logo_url;

    @SerializedName("banner_url")
    private String banner_url;

    @SerializedName("detail_link")
    private String detail_link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public String getDetail_link() {
        return detail_link;
    }

    public void setDetail_link(String detail_link) {
        this.detail_link = detail_link;
    }
}
