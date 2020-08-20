package com.namviet.vtvtravel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Weather implements Parcelable {
//    "id": 800,
//            "main": "Clear",
//            "description": "bầu trời quang đãng",
//            "icon": "01d",
//            "icon_url": "http://openweathermap.org/img/w/01d.png"
    private int id;
    private String main;
    private String description;
    private String icon;
    private String icon_url;

    protected Weather(Parcel in) {
        id = in.readInt();
        main = in.readString();
        description = in.readString();
        icon = in.readString();
        icon_url = in.readString();
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(main);
        parcel.writeString(description);
        parcel.writeString(icon);
        parcel.writeString(icon_url);
    }
}
