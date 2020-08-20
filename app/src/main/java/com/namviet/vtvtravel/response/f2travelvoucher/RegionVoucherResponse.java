package com.namviet.vtvtravel.response.f2travelvoucher;

import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class RegionVoucherResponse extends BaseResponse {
    private List<Category> data;

    public List<Category> getData() {
        return data;
    }

    public class Category {
        private boolean isSelected;
        private String id;
        private String name;
        private String countryId;
        private String status;
        private String weight;
        private String createdAt;
        private String modifiedAt;
        private String lat;
        private String lng;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public void setModifiedAt(String modifiedAt) {
            this.modifiedAt = modifiedAt;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCountryId() {
            return countryId;
        }

        public String getStatus() {
            return status;
        }

        public String getWeight() {
            return weight;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getModifiedAt() {
            return modifiedAt;
        }

        public String getLat() {
            return lat;
        }

        public String getLng() {
            return lng;
        }
    }
}
