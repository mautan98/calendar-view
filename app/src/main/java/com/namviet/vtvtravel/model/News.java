package com.namviet.vtvtravel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable {
    private String id;
    private String content_type;
    private String type;
    private String name;
    private String short_description;
    private String logo_url;
    private int view_count;
    private long created;
    private String detail_link;
    private String category_code;
    private boolean isBanner;

    public News() {
    }

    public News(String id, String detail_link, String content_type) {
        this.id = id;
        this.detail_link = detail_link;
        this.content_type = content_type;
    }

    public News(String logo_url, boolean isBanner){
        this.logo_url=logo_url;
        this.isBanner=isBanner;
    }

    protected News(Parcel in) {
        id = in.readString();
        content_type = in.readString();
        name = in.readString();
        short_description = in.readString();
        logo_url = in.readString();
        view_count = in.readInt();
        created = in.readLong();
        detail_link = in.readString();
        category_code = in.readString();
        type = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getDetail_link() {
        return detail_link;
    }

    public void setDetail_link(String detail_link) {
        this.detail_link = detail_link;
    }

    public String getCategory_code() {
        return category_code;
    }

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isBanner() {
        return isBanner;
    }

    public void setBanner(boolean banner) {
        isBanner = banner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(content_type);
        parcel.writeString(name);
        parcel.writeString(short_description);
        parcel.writeString(logo_url);
        parcel.writeInt(view_count);
        parcel.writeLong(created);
        parcel.writeString(detail_link);
        parcel.writeString(category_code);
        parcel.writeString(type);
    }

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", content_type='" + content_type + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", short_description='" + short_description + '\'' +
                ", logo_url='" + logo_url + '\'' +
                ", view_count=" + view_count +
                ", created=" + created +
                ", detail_link='" + detail_link + '\'' +
                ", category_code='" + category_code + '\'' +
                ", isBanner=" + isBanner +
                '}';
    }
}
