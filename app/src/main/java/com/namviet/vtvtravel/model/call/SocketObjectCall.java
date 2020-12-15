package com.namviet.vtvtravel.model.call;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocketObjectCall {
    @Expose
    @SerializedName("message")
    private Message message;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("room_id")
    private String room_id;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public static class Message {
        @Expose
        @SerializedName("user_id")
        private int user_id;
        @Expose
        @SerializedName("user_name")
        private String user_name;
        @Expose
        @SerializedName("room_id")
        private String room_id;

        @Expose
        @SerializedName("type")
        private String type;


        @Expose
        @SerializedName("sdp")
        private String sdp;

        @Expose
        @SerializedName("label")
        private String label;

        @Expose
        @SerializedName("id")
        private String id;

        @Expose
        @SerializedName("candidate")
        private String candidate;


        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCandidate() {
            return candidate;
        }

        public void setCandidate(String candidate) {
            this.candidate = candidate;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSdp() {
            return sdp;
        }

        public void setSdp(String sdp) {
            this.sdp = sdp;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }
    }
}
