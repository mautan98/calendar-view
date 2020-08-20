package com.namviet.vtvtravel.view.fragment.f2service;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Service implements Parcelable {
    private float id;
    private String code;
    private float created;
    private int cycle;
    private String description;
    private float modified;
    private String name;
    private int price;
    private String providerCode;
    private String serviceCode;
    private String type;
    private float providerId;
    private float serviceId;
    private float callTime;
    private float callTimeCustomerCare;
    private float callTimeVTVER;
    private float callTimeIVRAuto;
    private float freeData;
    private String promptUrl;
    private String promptUrlName;
    private String fullPromptUrlName;
    private String promptUrlExpireDate;
    private String fullPromptUrlExpireDate;
    private float status;
    List<Object> promotions = new ArrayList<Object>();
    List<Object> pkgRefuseMessages = new ArrayList<Object>();
    private String about;
    private String target;
    private String notes;
    private boolean isRegistered;

    protected Service(Parcel in) {
        id = in.readFloat();
        code = in.readString();
        created = in.readFloat();
        cycle = in.readInt();
        description = in.readString();
        modified = in.readFloat();
        name = in.readString();
        price = in.readInt();
        providerCode = in.readString();
        serviceCode = in.readString();
        type = in.readString();
        providerId = in.readFloat();
        serviceId = in.readFloat();
        callTime = in.readFloat();
        callTimeCustomerCare = in.readFloat();
        callTimeVTVER = in.readFloat();
        callTimeIVRAuto = in.readFloat();
        freeData = in.readFloat();
        promptUrl = in.readString();
        promptUrlName = in.readString();
        fullPromptUrlName = in.readString();
        promptUrlExpireDate = in.readString();
        fullPromptUrlExpireDate = in.readString();
        status = in.readFloat();
        about = in.readString();
        target = in.readString();
        notes = in.readString();
        isRegistered = in.readByte() != 0;
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }
    // Getter Methods


    public float getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public float getCreated() {
        return created;
    }

    public int getCycle() {
        return cycle;
    }

    public String getDescription() {
        return description;
    }

    public float getModified() {
        return modified;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public String getType() {
        return type;
    }

    public float getProviderId() {
        return providerId;
    }

    public float getServiceId() {
        return serviceId;
    }

    public float getCallTime() {
        return callTime;
    }

    public float getCallTimeCustomerCare() {
        return callTimeCustomerCare;
    }

    public float getCallTimeVTVER() {
        return callTimeVTVER;
    }

    public float getCallTimeIVRAuto() {
        return callTimeIVRAuto;
    }

    public float getFreeData() {
        return freeData;
    }

    public String getPromptUrl() {
        return promptUrl;
    }

    public String getPromptUrlName() {
        return promptUrlName;
    }

    public String getFullPromptUrlName() {
        return fullPromptUrlName;
    }

    public String getPromptUrlExpireDate() {
        return promptUrlExpireDate;
    }

    public String getFullPromptUrlExpireDate() {
        return fullPromptUrlExpireDate;
    }

    public float getStatus() {
        return status;
    }

    public String getAbout() {
        return about;
    }

    public String getTarget() {
        return target;
    }

    public String getNotes() {
        return notes;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCreated(float created) {
        this.created = created;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setModified(float modified) {
        this.modified = modified;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProviderId(float providerId) {
        this.providerId = providerId;
    }

    public void setServiceId(float serviceId) {
        this.serviceId = serviceId;
    }

    public void setCallTime(float callTime) {
        this.callTime = callTime;
    }

    public void setCallTimeCustomerCare(float callTimeCustomerCare) {
        this.callTimeCustomerCare = callTimeCustomerCare;
    }

    public void setCallTimeVTVER(float callTimeVTVER) {
        this.callTimeVTVER = callTimeVTVER;
    }

    public void setCallTimeIVRAuto(float callTimeIVRAuto) {
        this.callTimeIVRAuto = callTimeIVRAuto;
    }

    public void setFreeData(float freeData) {
        this.freeData = freeData;
    }

    public void setPromptUrl(String promptUrl) {
        this.promptUrl = promptUrl;
    }

    public void setPromptUrlName(String promptUrlName) {
        this.promptUrlName = promptUrlName;
    }

    public void setFullPromptUrlName(String fullPromptUrlName) {
        this.fullPromptUrlName = fullPromptUrlName;
    }

    public void setPromptUrlExpireDate(String promptUrlExpireDate) {
        this.promptUrlExpireDate = promptUrlExpireDate;
    }

    public void setFullPromptUrlExpireDate(String fullPromptUrlExpireDate) {
        this.fullPromptUrlExpireDate = fullPromptUrlExpireDate;
    }

    public void setStatus(float status) {
        this.status = status;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeFloat(id);
        dest.writeString(code);
        dest.writeFloat(created);
        dest.writeInt(cycle);
        dest.writeString(description);
        dest.writeFloat(modified);
        dest.writeString(name);
        dest.writeFloat(price);
        dest.writeString(providerCode);
        dest.writeString(serviceCode);
        dest.writeString(type);
        dest.writeFloat(providerId);
        dest.writeFloat(serviceId);
        dest.writeFloat(callTime);
        dest.writeFloat(callTimeCustomerCare);
        dest.writeFloat(callTimeVTVER);
        dest.writeFloat(callTimeIVRAuto);
        dest.writeFloat(freeData);
        dest.writeString(promptUrl);
        dest.writeString(promptUrlName);
        dest.writeString(fullPromptUrlName);
        dest.writeString(promptUrlExpireDate);
        dest.writeString(fullPromptUrlExpireDate);
        dest.writeFloat(status);
        dest.writeString(about);
        dest.writeString(target);
        dest.writeString(notes);
        dest.writeByte((byte) (isRegistered ? 1 : 0));
    }


}
