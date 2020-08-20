package com.namviet.vtvtravel.response.f2travelvoucher;

import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class CategoryVoucherResponse extends BaseResponse {
    private List<Category> data;

    public List<Category> getData() {
        return data;
    }

    public class Category{
        private boolean isChecked;
        private String id;
        private String name;
        private String description;
        private String createdId;
        private String status;
        private String createdAt;
        private String modifiedAt;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setCreatedId(String createdId) {
            this.createdId = createdId;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public void setModifiedAt(String modifiedAt) {
            this.modifiedAt = modifiedAt;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getCreatedId() {
            return createdId;
        }

        public String getStatus() {
            return status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getModifiedAt() {
            return modifiedAt;
        }
    }
}
