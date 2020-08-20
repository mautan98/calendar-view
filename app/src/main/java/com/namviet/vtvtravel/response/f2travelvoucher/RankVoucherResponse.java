package com.namviet.vtvtravel.response.f2travelvoucher;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class RankVoucherResponse extends BaseResponse {
    private List<Rank> data;

    public List<Rank> getData() {
        return data;
    }

    public  class Rank {
        @Expose
        @SerializedName("iconRank")
        private String iconRank;
        @Expose
        @SerializedName("isConfirmed")
        private String isConfirmed;
        @Expose
        @SerializedName("createdId")
        private String createdId;
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("modifiedAt")
        private String modifiedAt;
        @Expose
        @SerializedName("createdAt")
        private String createdAt;
        @Expose
        @SerializedName("maxIncome")
        private String maxIncome;
        @Expose
        @SerializedName("minIncome")
        private String minIncome;
        @Expose
        @SerializedName("serviceId")
        private String serviceId;
        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private String id;
        private boolean isSelected;

        public String getIconRank() {
            return iconRank;
        }

        public void setIconRank(String iconRank) {
            this.iconRank = iconRank;
        }

        public String getIsConfirmed() {
            return isConfirmed;
        }

        public void setIsConfirmed(String isConfirmed) {
            this.isConfirmed = isConfirmed;
        }

        public String getCreatedId() {
            return createdId;
        }

        public void setCreatedId(String createdId) {
            this.createdId = createdId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getModifiedAt() {
            return modifiedAt;
        }

        public void setModifiedAt(String modifiedAt) {
            this.modifiedAt = modifiedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getMaxIncome() {
            return maxIncome;
        }

        public void setMaxIncome(String maxIncome) {
            this.maxIncome = maxIncome;
        }

        public String getMinIncome() {
            return minIncome;
        }

        public void setMinIncome(String minIncome) {
            this.minIncome = minIncome;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
