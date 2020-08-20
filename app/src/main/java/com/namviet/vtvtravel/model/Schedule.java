package com.namviet.vtvtravel.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Schedule implements Parcelable {

    private String id;
    private String region;
    private String name;
    private String logo_url;
    private String address;
    private String tourId;
    private int status;
    private int duration;
    private double distance;
    private int view_count;
    private ArrayList<String> departure_dates;
    private String duration_label;
    private String description;
    private boolean selected;
    private String content_type;

    protected Schedule(Parcel in) {
        id = in.readString();
        region = in.readString();
        name = in.readString();
        logo_url = in.readString();
        address = in.readString();
        tourId = in.readString();
        status = in.readInt();
        duration = in.readInt();
        distance = in.readDouble();
        view_count = in.readInt();
        departure_dates = in.createStringArrayList();
        duration_label = in.readString();
        description = in.readString();
        selected = in.readByte() != 0;
        content_type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(region);
        dest.writeString(name);
        dest.writeString(logo_url);
        dest.writeString(address);
        dest.writeString(tourId);
        dest.writeInt(status);
        dest.writeInt(duration);
        dest.writeDouble(distance);
        dest.writeInt(view_count);
        dest.writeStringList(departure_dates);
        dest.writeString(duration_label);
        dest.writeString(description);
        dest.writeByte((byte) (selected ? 1 : 0));
        dest.writeString(content_type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public ArrayList<String> getDeparture_dates() {
        return departure_dates;
    }

    public void setDeparture_dates(ArrayList<String> departure_dates) {
        this.departure_dates = departure_dates;
    }

    public String getDuration_label() {
        return duration_label;
    }

    public void setDuration_label(String duration_label) {
        this.duration_label = duration_label;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }


    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }
}
