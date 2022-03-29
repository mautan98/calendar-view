package com.namviet.vtvtravel.response.f2menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public  class MenuItem implements Serializable {
    @Expose
    @SerializedName("html_icon")
    private String html_icon;
    @Expose
    @SerializedName("link")
    private String link;
    @Expose
    @SerializedName("icon_enable_url")
    private String icon_enable_url;
    @Expose
    @SerializedName("icon_url")
    private String icon_url;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("code_type")
    private String code_type;
    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("children")
    private List<MenuItem> menuChildren;




    public MenuItem() {
        html_icon = "";
        link = "";
        icon_enable_url = "";
        icon_url = "";
        id = "";
    }

    public List<MenuItem> getMenuChildren() {
        return menuChildren;
    }

    public void setMenuChildren(List<MenuItem> menuChildren) {
        this.menuChildren = menuChildren;
    }

    public String getHtml_icon() {
        return html_icon;
    }

    public void setHtml_icon(String html_icon) {
        this.html_icon = html_icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon_enable_url() {
        return icon_enable_url;
    }

    public void setIcon_enable_url(String icon_enable_url) {
        this.icon_enable_url = icon_enable_url;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode_type() {
        return code_type;
    }

    public void setCode_type(String code_type) {
        this.code_type = code_type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
