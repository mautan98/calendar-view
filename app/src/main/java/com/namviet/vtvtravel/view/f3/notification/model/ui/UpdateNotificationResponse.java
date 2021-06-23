package com.namviet.vtvtravel.view.f3.notification.model.ui;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.view.f3.notification.model.Notification;

import java.util.ArrayList;

public class UpdateNotificationResponse extends BaseResponse {
    //
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    //

    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data{
        @Expose
        @SerializedName("items")
        private ArrayList<Notification> notifications;

        public ArrayList<Notification> getNotifications() {
            return notifications;
        }
    }
}
