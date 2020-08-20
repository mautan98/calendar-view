package com.namviet.vtvtravel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NearItem implements Parcelable {
    private String id;
    private String name;
    private String icon_url;
    private int count;
    private String link;
    private String content_type;
    private String keyword;
    private String titleBar;

    public NearItem() {
    }

    protected NearItem(Parcel in) {
        id = in.readString();
        name = in.readString();
        icon_url = in.readString();
        count = in.readInt();
        link = in.readString();
        content_type = in.readString();
        keyword = in.readString();
        titleBar = in.readString();
    }

    public static final Creator<NearItem> CREATOR = new Creator<NearItem>() {
        @Override
        public NearItem createFromParcel(Parcel in) {
            return new NearItem(in);
        }

        @Override
        public NearItem[] newArray(int size) {
            return new NearItem[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTitleBar() {
        return titleBar;
    }

    public void setTitleBar(String titleBar) {
        this.titleBar = titleBar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(icon_url);
        parcel.writeInt(count);
        parcel.writeString(link);
        parcel.writeString(content_type);
        parcel.writeString(keyword);
        parcel.writeString(titleBar);
    }
}
