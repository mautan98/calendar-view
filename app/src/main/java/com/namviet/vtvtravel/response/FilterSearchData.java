package com.namviet.vtvtravel.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.namviet.vtvtravel.model.Filter;

import java.util.ArrayList;

public class FilterSearchData implements Parcelable {
    private String name;
    private String content_type;
    private ArrayList<Filter> items;

    protected FilterSearchData(Parcel in) {
        name = in.readString();
        content_type = in.readString();
        items = in.createTypedArrayList(Filter.CREATOR);
    }

    public static final Creator<FilterSearchData> CREATOR = new Creator<FilterSearchData>() {
        @Override
        public FilterSearchData createFromParcel(Parcel in) {
            return new FilterSearchData(in);
        }

        @Override
        public FilterSearchData[] newArray(int size) {
            return new FilterSearchData[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public ArrayList<Filter> getItems() {
        return items;
    }

    public void setItems(ArrayList<Filter> items) {
        this.items = items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(content_type);
        parcel.writeTypedList(items);
    }
}
