package com.namviet.vtvtravel.model.f2;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;


import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Contact implements Parcelable {
    @SerializedName("accountId")
    private int id;
    private String contactName;
    private String email;
    @TypeConverters(DataConverterPhoneList.class) // add here
    private List<String> phones;
    private String phone;
    private String avatar;
    @Ignore
    private int hasAccountId;
    private String fullName;
    @Ignore
    private int online;
    @Ignore
    private boolean isChecked = false;
    @PrimaryKey
    @NonNull
    private int contactClientId;

    private boolean isHeader;
    private String phoneToShow;
    private boolean isCallNow;



    public Contact() {
    }


    protected Contact(Parcel in) {
        online = in.readInt();
        id = in.readInt();
        contactClientId = in.readInt();
        contactName = in.readString();
        email = in.readString();
        fullName = in.readString();
        phone = in.readString();
        phoneToShow = in.readString();
        avatar = in.readString();
        phones = in.createStringArrayList();
        hasAccountId = in.readInt();
        isChecked = in.readByte() != 0;
        isHeader = in.readByte() !=0;
        isCallNow = in.readByte() !=0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public int getContactClientId() {
        return contactClientId;
    }

    public void setContactClientId(int contactClientId) {
        this.contactClientId = contactClientId;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public static Creator<Contact> getCREATOR() {
        return CREATOR;
    }

    public List<String> getPhones() {
        return phones;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }



    public int getHasAccountId() {
        return hasAccountId;
    }

    public void setHasAccountId(int hasAccountId) {
        this.hasAccountId = hasAccountId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getPhoneToShow() {
        return phoneToShow;
    }

    public void setPhoneToShow(String phoneToShow) {
        this.phoneToShow = phoneToShow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(online);
        parcel.writeInt(id);
        parcel.writeInt(contactClientId);
        parcel.writeString(contactName);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(phoneToShow);
        parcel.writeString(avatar);
        parcel.writeString(fullName);
        parcel.writeStringList(phones);
        parcel.writeInt(hasAccountId);
        parcel.writeByte((byte) (isChecked ? 1 : 0));
        parcel.writeByte((byte) (isHeader ? 1 : 0));
        parcel.writeByte((byte) (isCallNow ? 1 : 0));
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isCallNow() {
        return isCallNow;
    }

    public void setCallNow(boolean callNow) {
        isCallNow = callNow;
    }
}