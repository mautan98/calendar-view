package com.namviet.vtvtravel.model.chat;

import com.google.gson.annotations.SerializedName;

public class ChatBase {
    private String status;
    private String code;
    private String message;
    @SerializedName("data")
    ChatData chatData;

    public ChatBase(ChatData chatData) {
        this.chatData = chatData;
    }

    // Getter Methods
    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ChatData getData() {
        return chatData;
    }

    // Setter Methods

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(ChatData data) {
        this.chatData = data;
    }
}