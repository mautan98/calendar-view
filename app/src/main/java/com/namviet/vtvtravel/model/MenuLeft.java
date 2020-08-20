package com.namviet.vtvtravel.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MenuLeft implements Parcelable {
    private List<ItemMenu> left;

    protected MenuLeft(Parcel in) {
        left = in.createTypedArrayList(ItemMenu.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(left);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MenuLeft> CREATOR = new Creator<MenuLeft>() {
        @Override
        public MenuLeft createFromParcel(Parcel in) {
            return new MenuLeft(in);
        }

        @Override
        public MenuLeft[] newArray(int size) {
            return new MenuLeft[size];
        }
    };

    public List<ItemMenu> getLeft() {
        return left;
    }

    public void setLeft(List<ItemMenu> left) {
        this.left = left;
    }
}
