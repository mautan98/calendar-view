package com.namviet.vtvtravel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Details implements Parcelable{
    private String id;
    private String name;
    private String banner_url;
    private String map_url;
    private String description;
    private Integer view_count;
    private Long created;

    protected Details(Parcel in) {
        id = in.readString();
        name = in.readString();
        banner_url = in.readString();
        map_url = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            view_count = null;
        } else {
            view_count = in.readInt();
        }
        if (in.readByte() == 0) {
            created = null;
        } else {
            created = in.readLong();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(banner_url);
        dest.writeString(map_url);
        dest.writeString(description);
        if (view_count == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(view_count);
        }
        if (created == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(created);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Details> CREATOR = new Creator<Details>() {
        @Override
        public Details createFromParcel(Parcel in) {
            return new Details(in);
        }

        @Override
        public Details[] newArray(int size) {
            return new Details[size];
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

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getView_count() {
        return view_count;
    }

    public void setView_count(Integer view_count) {
        this.view_count = view_count;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getMap_url() {
        return map_url;
    }

    public void setMap_url(String map_url) {
        this.map_url = map_url;
    }
}
