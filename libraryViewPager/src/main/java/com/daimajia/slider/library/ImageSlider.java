package com.daimajia.slider.library;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 9/23/2016.
 */
public class ImageSlider implements Parcelable {
    private String id = "";
    private int type;
    private String text;
    private String images = "";


    public ImageSlider(String id, String images) {
        this.id = id;
        this.images = images;
    }

    public ImageSlider(String id, int type, String text, String images) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.images = images;
    }

    protected ImageSlider(Parcel in) {
        id = in.readString();
        images = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(type);
        dest.writeString(text);
        dest.writeString(images);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageSlider> CREATOR = new Creator<ImageSlider>() {
        @Override
        public ImageSlider createFromParcel(Parcel in) {
            return new ImageSlider(in);
        }

        @Override
        public ImageSlider[] newArray(int size) {
            return new ImageSlider[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ImageSlider{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", text='" + text + '\'' +
                ", images='" + images + '\'' +
                '}';
    }
}