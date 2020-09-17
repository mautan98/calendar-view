package com.namviet.vtvtravel.response.f2systeminbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class SystemInbox extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{

        @SerializedName("items")
        @Expose
        private List<InboxItem> inboxItem;

        public List<InboxItem> getInboxItem() {
            return inboxItem;
        }

        public void setInboxItem(List<InboxItem> inboxItem) {
            this.inboxItem = inboxItem;
        }

        public  class InboxItem {
            @Expose
            @SerializedName("createdAt")
            private String createdAt;
            @Expose
            @SerializedName("status")
            private String status;
            @Expose
            @SerializedName("type")
            private String type;
            @Expose
            @SerializedName("content")
            private String content;
            @Expose
            @SerializedName("mobile")
            private String mobile;
            @Expose
            @SerializedName("id")
            private String id;

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

    }
}
