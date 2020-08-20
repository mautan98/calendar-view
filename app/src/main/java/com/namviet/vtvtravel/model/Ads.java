package com.namviet.vtvtravel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ads implements Parcelable {
//    id: 2,
//    offset: 5,
//    skipoffset: 5,
//    tag: "http://103.21.148.54:8861/ads/videos/vast/2",

    private int id;
    private int offset;
    private int skipoffset;
    private String tag;

    protected Ads(Parcel in) {
        id = in.readInt();
        offset = in.readInt();
        skipoffset = in.readInt();
        tag = in.readString();
    }

    public static final Creator<Ads> CREATOR = new Creator<Ads>() {
        @Override
        public Ads createFromParcel(Parcel in) {
            return new Ads(in);
        }

        @Override
        public Ads[] newArray(int size) {
            return new Ads[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSkipoffset() {
        return skipoffset;
    }

    public void setSkipoffset(int skipoffset) {
        this.skipoffset = skipoffset;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(offset);
        parcel.writeInt(skipoffset);
        parcel.writeString(tag);
    }
}
