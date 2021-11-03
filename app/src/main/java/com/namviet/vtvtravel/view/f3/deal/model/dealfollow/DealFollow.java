package com.namviet.vtvtravel.view.f3.deal.model.dealfollow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DealFollow {
    @Expose
    @SerializedName("rewardStatus")
    private String rewardStatus;
    @Expose
    @SerializedName("isWin")
    private String isWin;
    @Expose
    @SerializedName("isProcessing")
    private String isProcessing;
    @Expose
    @SerializedName("ranking")
    private String ranking;
    @Expose
    @SerializedName("totalHoldTime")
    private String totalHoldTime;
    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("deals")
    private ArrayList<DealFollow> listDealChild;

    public ArrayList<DealFollow> getListDealChild() {
        return listDealChild;
    }

    public void setListDealChild(ArrayList<DealFollow> listDealChild) {
        this.listDealChild = listDealChild;
    }

    public String getRewardStatus() {
        return rewardStatus;
    }

    public void setRewardStatus(String rewardStatus) {
        this.rewardStatus = rewardStatus;
    }

    public String getIsWin() {
        return isWin;
    }

    public void setIsWin(String isWin) {
        this.isWin = isWin;
    }

    public String getIsProcessing() {
        return isProcessing;
    }

    public void setIsProcessing(String isProcessing) {
        this.isProcessing = isProcessing;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getTotalHoldTime() {
        return totalHoldTime;
    }

    public void setTotalHoldTime(String totalHoldTime) {
        this.totalHoldTime = totalHoldTime;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
