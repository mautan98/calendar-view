package com.namviet.vtvtravel.response.f2menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Menus {

    @Expose
    @SerializedName("footer")
    private List<MenuItem> footer;
    @Expose
    @SerializedName("header")
    private List<MenuItem> header;
    @Expose
    @SerializedName("left")
    private List<MenuItem> left;

    public List<MenuItem> getFooter() {
        return footer;
    }

    public void setFooter(List<MenuItem> footer) {
        this.footer = footer;
    }

    public List<MenuItem> getHeader() {
        return header;
    }

    public void setHeader(List<MenuItem> header) {
        this.header = header;
    }

    public List<MenuItem> getLeft() {
        return left;
    }

    public void setLeft(List<MenuItem> left) {
        this.left = left;
    }
}
