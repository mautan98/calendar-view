package com.namviet.vtvtravel.model.newhome;

import android.os.Parcel;
import android.os.Parcelable;

public class Voucher implements Parcelable {
    private int image;

    public Voucher(int image) {
        this.image = image;
    }

    protected Voucher(Parcel in) {
        image = in.readInt();
    }

    public static final Creator<Voucher> CREATOR = new Creator<Voucher>() {
        @Override
        public Voucher createFromParcel(Parcel in) {
            return new Voucher(in);
        }

        @Override
        public Voucher[] newArray(int size) {
            return new Voucher[size];
        }
    };

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image);
    }
}
