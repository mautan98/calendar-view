package com.namviet.vtvtravel.response.f2operator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.response.BaseResponse;

public class OperatorInformationResponse extends BaseResponse {
    @Expose
    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Data {

        @Expose
        @SerializedName("created")
        private String created;
        @Expose
        @SerializedName("userWorkProvince")
        private String userWorkProvince;
        @Expose
        @SerializedName("userPosition")
        private String userPosition;
        @Expose
        @SerializedName("mobile")
        private String mobile;
        @Expose
        @SerializedName("birthday")
        private String birthday;
        @Expose
        @SerializedName("gender")
        private String gender;
        @Expose
        @SerializedName("fullname")
        private String fullname;
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("virtualMobile")
        private String virtualMobile;
        @Expose
        @SerializedName("id")
        private String id;

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUserWorkProvince() {
            return userWorkProvince;
        }

        public void setUserWorkProvince(String userWorkProvince) {
            this.userWorkProvince = userWorkProvince;
        }

        public String getUserPosition() {
            return userPosition;
        }

        public void setUserPosition(String userPosition) {
            this.userPosition = userPosition;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVirtualMobile() {
            return virtualMobile;
        }

        public void setVirtualMobile(String virtualMobile) {
            this.virtualMobile = virtualMobile;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
