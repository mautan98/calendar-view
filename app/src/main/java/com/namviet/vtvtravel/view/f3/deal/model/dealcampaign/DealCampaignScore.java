
package com.namviet.vtvtravel.view.f3.deal.model.dealcampaign;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealCampaignScore {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("holdTime")
    @Expose
    private Long holdTime;
    @SerializedName("rewardStatus")
    @Expose
    private Integer rewardStatus;
    @SerializedName("created")
    @Expose
    private Long created;
    @SerializedName("modified")
    @Expose
    private Long modified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(Long holdTime) {
        this.holdTime = holdTime;
    }

    public Integer getRewardStatus() {
        return rewardStatus;
    }

    public void setRewardStatus(Integer rewardStatus) {
        this.rewardStatus = rewardStatus;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getModified() {
        return modified;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

}
