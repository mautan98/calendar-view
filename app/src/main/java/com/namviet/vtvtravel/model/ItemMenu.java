package com.namviet.vtvtravel.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ItemMenu implements Parcelable {

    private String id;
    private String code;
    private String name;
    private String icon_url;
    private String link;
    private List<ItemMenu> children;
    private int position;

    protected ItemMenu(Parcel in) {
        id = in.readString();
        code = in.readString();
        name = in.readString();
        icon_url = in.readString();
        link = in.readString();
        children = in.createTypedArrayList(ItemMenu.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(icon_url);
        dest.writeString(link);
        dest.writeTypedList(children);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemMenu> CREATOR = new Creator<ItemMenu>() {
        @Override
        public ItemMenu createFromParcel(Parcel in) {
            return new ItemMenu(in);
        }

        @Override
        public ItemMenu[] newArray(int size) {
            return new ItemMenu[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<ItemMenu> getChildren() {
        return children;
    }

    public void setChildren(List<ItemMenu> children) {
        this.children = children;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
