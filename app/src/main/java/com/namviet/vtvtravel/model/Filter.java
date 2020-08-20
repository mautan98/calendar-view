package com.namviet.vtvtravel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Filter implements Parcelable {
    private String value;
    private String label;
    private boolean selected;
    private String id;
    private String name;
    private String link;


    protected Filter(Parcel in) {
        value = in.readString();
        label = in.readString();
        selected = in.readByte() != 0;
        id = in.readString();
        name = in.readString();
        link = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
        dest.writeString(label);
        dest.writeByte((byte) (selected ? 1 : 0));
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(link);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Filter> CREATOR = new Creator<Filter>() {
        @Override
        public Filter createFromParcel(Parcel in) {
            return new Filter(in);
        }

        @Override
        public Filter[] newArray(int size) {
            return new Filter[size];
        }
    };

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

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
}
