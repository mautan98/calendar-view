package com.namviet.vtvtravel.model.f2callnow;

public class CallNowHistory {
    private String id;
    private String userId;
    private String mobile;
    private String receiver;
    private String ivrSipCallId;
    private String beginTime;
    private String endTime;
    private String durations;
    private String created;
    private String updated;
    private String price;
    private String telcoCode;
    private String deletedByMobile;
    private String deletedByReceiver;

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getMobile() {
        return mobile;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getIvrSipCallId() {
        return ivrSipCallId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDurations() {
        return durations;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public String getPrice() {
        return price;
    }

    public String getTelcoCode() {
        return telcoCode;
    }

    public String getDeletedByMobile() {
        return deletedByMobile;
    }

    public String getDeletedByReceiver() {
        return deletedByReceiver;
    }
}
