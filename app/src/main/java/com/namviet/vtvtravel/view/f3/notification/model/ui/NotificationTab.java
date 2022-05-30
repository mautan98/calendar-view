package com.namviet.vtvtravel.view.f3.notification.model.ui;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.ArrayList;

public class NotificationTab extends BaseResponse {
    private ArrayList<Data> data;

    public ArrayList<Data> getData() {
        return data;
    }


    public  class Data {

        private boolean isNotType;
        @Expose
        @SerializedName("created")
        private String created;
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("code")
        private String code;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private String id;

        public Data(String created, String status, String code, String name, String id, boolean isNotType) {
            this.created = created;
            this.status = status;
            this.code = code;
            this.name = name;
            this.id = id;
            this.isNotType = isNotType;
        }

        public String getCreated() {
            return created;
        }

        public String getStatus() {
            return status;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public boolean isNotType() {
            return isNotType;
        }
    }
}
