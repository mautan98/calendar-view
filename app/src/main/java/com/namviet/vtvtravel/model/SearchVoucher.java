package com.namviet.vtvtravel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchVoucher implements Parcelable {
    private String code;
    private String title;
    private String link;

    protected SearchVoucher(Parcel in) {
        code = in.readString();
        title = in.readString();
        link = in.readString();
    }

    public static final Creator<SearchVoucher> CREATOR = new Creator<SearchVoucher>() {
        @Override
        public SearchVoucher createFromParcel(Parcel in) {
            return new SearchVoucher(in);
        }

        @Override
        public SearchVoucher[] newArray(int size) {
            return new SearchVoucher[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        parcel.writeString(code);
        parcel.writeString(title);
        parcel.writeString(link);
    }
}
