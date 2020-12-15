package com.namviet.vtvtravel.fcm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.response.f2systeminbox.DataSystemInbox;

import java.io.Serializable;

public class Notification implements Serializable {

    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("mobile")
    private String mobile;

    private DataSystemInbox dataSystemInbox;

    public DataSystemInbox getDataSystemInbox() {
        return dataSystemInbox;
    }

    public void setDataSystemInbox(DataSystemInbox dataSystemInbox) {
        this.dataSystemInbox = dataSystemInbox;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
