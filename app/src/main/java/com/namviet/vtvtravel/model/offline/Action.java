package com.namviet.vtvtravel.model.offline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Action {
    @Expose
    @SerializedName("phone")
    private String phone;
    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("type")
    private String type;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
