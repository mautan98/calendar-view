
package com.namviet.vtvtravel.view.f3.notification.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Notification implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("isMarked")
    @Expose
    private String isMarked;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("typeId")
    @Expose
    private String typeId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("title")
    @Expose
    private String title;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getId() {
        return id;
    }

    public String getIsMarked() {
        return isMarked;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIsMarked(String isMarked) {
        this.isMarked = isMarked;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
