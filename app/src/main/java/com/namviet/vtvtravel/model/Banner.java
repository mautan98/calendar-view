package com.namviet.vtvtravel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Banner implements Parcelable {

    private int id;
    private String name;
    private int type;
    private String position_code;
    private int position_index;
    private String embed_link;

    protected Banner(Parcel in) {
        id = in.readInt();
        name = in.readString();
        type = in.readInt();
        position_code = in.readString();
        position_index = in.readInt();
        embed_link = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(type);
        dest.writeString(position_code);
        dest.writeInt(position_index);
        dest.writeString(embed_link);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Banner> CREATOR = new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel in) {
            return new Banner(in);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPosition_code() {
        return position_code;
    }

    public void setPosition_code(String position_code) {
        this.position_code = position_code;
    }

    public int getPosition_index() {
        return position_index;
    }

    public void setPosition_index(int position_index) {
        this.position_index = position_index;
    }

    public String getEmbed_link() {
        return embed_link;
    }

    public void setEmbed_link(String embed_link) {
        this.embed_link = embed_link;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", position_code='" + position_code + '\'' +
                ", position_index=" + position_index +
                ", embed_link='" + embed_link + '\'' +
                '}';
    }
}
