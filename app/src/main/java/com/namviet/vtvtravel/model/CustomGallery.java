package com.namviet.vtvtravel.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class CustomGallery implements Parcelable {

    private String sdcardPath;
    private boolean isSeleted = false;
    private Uri sdCardUri;
    private String aboutPhoto = "";

    public CustomGallery() {
    }

    protected CustomGallery(Parcel in) {
        sdcardPath = in.readString();
        aboutPhoto = in.readString();
        isSeleted = in.readByte() != 0;
        sdCardUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<CustomGallery> CREATOR = new Creator<CustomGallery>() {
        @Override
        public CustomGallery createFromParcel(Parcel in) {
            return new CustomGallery(in);
        }

        @Override
        public CustomGallery[] newArray(int size) {
            return new CustomGallery[size];
        }
    };

    public String getSdcardPath() {
        return sdcardPath;
    }

    public void setSdcardPath(String sdcardPath) {
        this.sdcardPath = sdcardPath;
    }

    public boolean isSeleted() {
        return isSeleted;
    }

    public void setSeleted(boolean seleted) {
        isSeleted = seleted;
    }

    public Uri getSdCardUri() {
        return sdCardUri;
    }

    public void setSdCardUri(Uri sdCardUri) {
        this.sdCardUri = sdCardUri;
    }

    public String getAboutPhoto() {
        return aboutPhoto;
    }

    public void setAboutPhoto(String aboutPhoto) {
        this.aboutPhoto = aboutPhoto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(sdcardPath);
        parcel.writeString(aboutPhoto);
        parcel.writeByte((byte) (isSeleted ? 1 : 0));
        parcel.writeParcelable(sdCardUri, i);
    }
}
