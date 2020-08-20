package com.daimajia.slider.library;

import android.os.Parcel;
import android.os.Parcelable;

public class NearDistance implements Parcelable{
    private String label;
    private int value;
    private boolean checked;

    protected NearDistance(Parcel in) {
        label = in.readString();
        value = in.readInt();
        checked = in.readByte() != 0;
    }

    public static final Creator<NearDistance> CREATOR = new Creator<NearDistance>() {
        @Override
        public NearDistance createFromParcel(Parcel in) {
            return new NearDistance(in);
        }

        @Override
        public NearDistance[] newArray(int size) {
            return new NearDistance[size];
        }
    };

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(label);
        parcel.writeInt(value);
        parcel.writeByte((byte) (checked ? 1 : 0));
    }
}
