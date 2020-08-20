package com.namviet.vtvtravel.model.channel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.model.DetailLiveChannel;

import java.util.Collections;
import java.util.List;

public class DetailLiveChannelDataM {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DetailLiveChannelData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DetailLiveChannelData getData() {
        return data;
    }

    public void setData(DetailLiveChannelData data) {
        this.data = data;
    }

    public class DetailLiveChannelData {
        @SerializedName("date")
        private String date;

        @SerializedName("items")
        private List<DetailLiveChannel> data;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<DetailLiveChannel> getData() {
            Collections.reverse(data);
            return data;
        }

        public void setData(List<DetailLiveChannel> data) {
            this.data = data;
        }
    }
}
