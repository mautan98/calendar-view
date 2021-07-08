
package com.namviet.vtvtravel.view.f3.notification.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable {
    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("detailLink")
    @Expose
    private String dealDetailLink;

    @SerializedName("icon")
    @Expose
    private String icon;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDealDetailLink() {
        return dealDetailLink;
    }

    public void setDealDetailLink(String dealDetailLink) {
        this.dealDetailLink = dealDetailLink;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
