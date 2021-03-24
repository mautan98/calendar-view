package com.namviet.vtvtravel.response;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryItem implements Parcelable {
    private String id;
    private String name;
    private String link;

    protected CategoryItem(Parcel in) {
        id = in.readString();
        name = in.readString();
        link = in.readString();
    }

    public CategoryItem(){

    }

    public static final Creator<CategoryItem> CREATOR = new Creator<CategoryItem>() {
        @Override
        public CategoryItem createFromParcel(Parcel in) {
            return new CategoryItem(in);
        }

        @Override
        public CategoryItem[] newArray(int size) {
            return new CategoryItem[size];
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(link);
    }
}
