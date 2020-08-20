package com.namviet.vtvtravel.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.daimajia.slider.library.Travel;

import java.util.List;

public class ItemTravel implements Parcelable{
    private String type;
    private int total_count;
    private String name;
    private String icon_url;
    private String more_link;
    private List<Travel> items;

    public ItemTravel(String type, int total_count, String name, String icon, String more_link) {
        this.type = type;
        this.total_count = total_count;
        this.name = name;
        this.icon_url = icon;
        this.more_link = more_link;
    }

    protected ItemTravel(Parcel in) {
        type = in.readString();
        total_count = in.readInt();
        name = in.readString();
        icon_url = in.readString();
        more_link = in.readString();
        items = in.createTypedArrayList(Travel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeInt(total_count);
        dest.writeString(name);
        dest.writeString(icon_url);
        dest.writeString(more_link);
        dest.writeTypedList(items);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemTravel> CREATOR = new Creator<ItemTravel>() {
        @Override
        public ItemTravel createFromParcel(Parcel in) {
            return new ItemTravel(in);
        }

        @Override
        public ItemTravel[] newArray(int size) {
            return new ItemTravel[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon_url;
    }

    public void setIcon(String icon) {
        this.icon_url = icon;
    }

    public String getMore_link() {
        return more_link;
    }

    public void setMore_link(String more_link) {
        this.more_link = more_link;
    }

    public List<Travel> getItems() {
        return items;
    }

    public void setItems(List<Travel> items) {
        this.items = items;
    }
}
