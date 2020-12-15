package com.namviet.vtvtravel.response.f2wheel;

import com.namviet.vtvtravel.response.BaseResponse;

public class WheelRotateResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data{
        private String id;
        private String mobile;
        private String telco;
        private String areasWinning;
        private String typeWinning;
        private String turns;
        private String code;
        private String voucherId;
        private String channel;
        private String status;
        private String ipAddress;
        private String operatingSystem;
        private String createdAt;
        private String modifiedAt;

        public String getId() {
            return id;
        }

        public String getMobile() {
            return mobile;
        }

        public String getTelco() {
            return telco;
        }

        public String getAreasWinning() {
            return areasWinning;
        }

        public String getTypeWinning() {
            return typeWinning;
        }

        public String getTurns() {
            return turns;
        }

        public String getCode() {
            return code;
        }

        public String getVoucherId() {
            return voucherId;
        }

        public String getChannel() {
            return channel;
        }

        public String getStatus() {
            return status;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public String getOperatingSystem() {
            return operatingSystem;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getModifiedAt() {
            return modifiedAt;
        }
    }
}
