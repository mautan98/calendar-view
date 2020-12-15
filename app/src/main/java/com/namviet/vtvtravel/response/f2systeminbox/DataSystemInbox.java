package com.namviet.vtvtravel.response.f2systeminbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataSystemInbox implements Serializable {

    @Expose
    @SerializedName("scheduleCustomId")
    private String scheduleCustomId;
    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("detailLink")
    private String detailLink;

    @Expose
    @SerializedName("ticketId")
    private String ticketID;


    @Expose
    @SerializedName("contentType")
    private String contentType;


    public String getDetailLink() {
        return detailLink;
    }

    public void setDetailLink(String detailLink) {
        this.detailLink = detailLink;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getScheduleCustomId() {
        return scheduleCustomId;
    }

    public void setScheduleCustomId(String scheduleCustomId) {
        this.scheduleCustomId = scheduleCustomId;
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

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }
}
