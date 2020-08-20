package com.namviet.vtvtravel.model.offline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Banner {
    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("image")
    private String image;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
