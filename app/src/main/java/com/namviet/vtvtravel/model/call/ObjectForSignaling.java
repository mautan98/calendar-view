package com.namviet.vtvtravel.model.call;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectForSignaling {
    @Expose
    @SerializedName("message")
    private Payload payload;
    @Expose
    @SerializedName("room_id")
    private String room_id;
    @Expose
    @SerializedName("type")
    private String type;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class Payload {
        @Expose
        @SerializedName("signalData")
        private SignalData signalData;
        @Expose
        @SerializedName("senderInfo")
        private SenderInfo senderInfo;
        @Expose
        @SerializedName("isHasVideo")
        private boolean isHasVideo;
        @Expose
        @SerializedName("roomId")
        private String roomId;

        public SignalData getSignalData() {
            return signalData;
        }

        public void setSignalData(SignalData signalData) {
            this.signalData = signalData;
        }

        public SenderInfo getSenderInfo() {
            return senderInfo;
        }

        public void setSenderInfo(SenderInfo senderInfo) {
            this.senderInfo = senderInfo;
        }

        public boolean isHasVideo() {
            return isHasVideo;
        }

        public void setHasVideo(boolean hasVideo) {
            isHasVideo = hasVideo;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }
    }

    public static class SignalData {
        @Expose
        @SerializedName("sdp")
        private String sdp;
        @Expose
        @SerializedName("type")
        private String type;



        @Expose
        @SerializedName("sdpMLineIndex")
        private String label;

        @Expose
        @SerializedName("sdpMid")
        private String id;

//        @Expose
//        @SerializedName("sdp")
//        private String candidate;

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

//        public String getCandidate() {
//            return candidate;
//        }
//
//        public void setCandidate(String candidate) {
//            this.candidate = candidate;
//        }

        public String getSdp() {
            return sdp;
        }

        public void setSdp(String sdp) {
            this.sdp = sdp;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class SenderInfo {
        @Expose
        @SerializedName("memberId")
        private String memberId;
        @Expose
        @SerializedName("fullName")
        private String fullName;
        @Expose
        @SerializedName("id")
        private int id;

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
