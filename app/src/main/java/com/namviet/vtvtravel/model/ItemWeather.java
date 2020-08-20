package com.namviet.vtvtravel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemWeather implements Parcelable {
    private float temp;
    private float temp_min;
    private float temp_max;
    private Weather weather;
    private long date;
    private String region_name;
    private String region_id;
    private String banner_url;

    protected ItemWeather(Parcel in) {
        temp = in.readFloat();
        temp_min = in.readFloat();
        temp_max = in.readFloat();
        weather = in.readParcelable(Weather.class.getClassLoader());
        date = in.readLong();
        region_id = in.readString();
        region_name = in.readString();
        banner_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(temp);
        dest.writeFloat(temp_min);
        dest.writeFloat(temp_max);
        dest.writeParcelable(weather, flags);
        dest.writeLong(date);
        dest.writeString(region_id);
        dest.writeString(region_name);
        dest.writeString(banner_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemWeather> CREATOR = new Creator<ItemWeather>() {
        @Override
        public ItemWeather createFromParcel(Parcel in) {
            return new ItemWeather(in);
        }

        @Override
        public ItemWeather[] newArray(int size) {
            return new ItemWeather[size];
        }
    };

    public float getTamp() {
        return temp;
    }

    public void setTamp(float tamp) {
        this.temp = tamp;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }
}
