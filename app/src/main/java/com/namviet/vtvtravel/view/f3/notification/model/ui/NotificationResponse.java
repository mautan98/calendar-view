package com.namviet.vtvtravel.view.f3.notification.model.ui;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.view.f3.notification.model.Notification;

import java.util.ArrayList;

public class NotificationResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data{
        @Expose
        @SerializedName("content")
        private ArrayList<Notification> notifications;

        public ArrayList<Notification> getNotifications() {
            return notifications;
        }
    }
}