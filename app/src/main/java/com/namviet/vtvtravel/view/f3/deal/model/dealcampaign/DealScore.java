
package com.namviet.vtvtravel.view.f3.deal.model.dealcampaign;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DealScore {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("campaignId")
    @Expose
    private Integer campaignId;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("holdTime")
    @Expose
    private Integer holdTime;
    @SerializedName("rewardStatus")
    @Expose
    private Integer rewardStatus;
    @SerializedName("createdAt")
    @Expose
    private Long createdAt;
    @SerializedName("modifiedAt")
    @Expose
    private Long modifiedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(Integer holdTime) {
        this.holdTime = holdTime;
    }

    public Integer getRewardStatus() {
        return rewardStatus;
    }

    public void setRewardStatus(Integer rewardStatus) {
        this.rewardStatus = rewardStatus;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Long modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

}
