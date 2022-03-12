package com.namviet.vtvtravel.view.f3.deal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class Block implements Serializable {

    @Expose
    @SerializedName("short_description")
    private String short_description;
    @Expose
    @SerializedName("link")
    private String link;
    @Expose
    @SerializedName("icon_enable_url")
    private String icon_enable_url;
    @Expose
    @SerializedName("icon_url")
    private String icon_url;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("code_type")
    private String code_type;
    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("children")
    private ArrayList<Block> listChildBlock;
    private boolean dataLoaded;

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon_enable_url() {
        return icon_enable_url;
    }

    public void setIcon_enable_url(String icon_enable_url) {
        this.icon_enable_url = icon_enable_url;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode_type() {
        return code_type;
    }

    public void setCode_type(String code_type) {
        this.code_type = code_type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<Block> getListChildBlock() {
        return listChildBlock;
    }

    public void setListChildBlock(ArrayList<Block> listChildBlock) {
        this.listChildBlock = listChildBlock;
    }
    private DealResponse mDealResponse;

    public DealResponse getDealResponse() {
        return mDealResponse;
    }

    public void setDealResponse(DealResponse mDealResponse) {
        this.mDealResponse = mDealResponse;
    }

    public boolean isDataLoaded() {
        return dataLoaded;
    }

    public void setDataLoaded(boolean dataLoaded) {
        this.dataLoaded = dataLoaded;
    }

    public Block(String name) {
        this.name = name;
    }
}
