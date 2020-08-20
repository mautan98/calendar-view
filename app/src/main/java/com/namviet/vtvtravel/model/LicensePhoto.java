package com.namviet.vtvtravel.model;

import com.google.gson.annotations.SerializedName;

public class LicensePhoto {
    @SerializedName("status")
    private String status;

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    LicensePhotoData data;

    public LicensePhotoData getData() {
        return data;
    }

    public void setData(LicensePhotoData data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class LicensePhotoData {
        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        @SerializedName("short_description")
        private String short_description;

        @SerializedName("description")
        private String description;

        @SerializedName("logo_url")
        private String logo_url = null;

        @SerializedName("url_alias")
        private String url_alias;

        @SerializedName("view_count")
        private int view_count;

        @SerializedName("like_count")
        private float like_count;

        @SerializedName("comment_count")
        private float comment_count;

        @SerializedName("created")
        private long created;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getShort_description() {
            return short_description;
        }

        public String getDescription() {
            return description;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public String getUrl_alias() {
            return url_alias;
        }

        public int getView_count() {
            return view_count;
        }

        public float getLike_count() {
            return like_count;
        }

        public float getComment_count() {
            return comment_count;
        }

        public long getCreated() {
            return created;
        }

        // Setter Methods

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setShort_description(String short_description) {
            this.short_description = short_description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public void setUrl_alias(String url_alias) {
            this.url_alias = url_alias;
        }

        public void setView_count(int view_count) {
            this.view_count = view_count;
        }

        public void setLike_count(float like_count) {
            this.like_count = like_count;
        }

        public void setComment_count(float comment_count) {
            this.comment_count = comment_count;
        }

        public void setCreated(long created) {
            this.created = created;
        }
    }
}