
package com.namviet.vtvtravel.view.f3.deal.model.dealbycampaign;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Content {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("campaignId")
    @Expose
    private Integer campaignId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("rule")
    @Expose
    private String rule;
    @SerializedName("promptRank")
    @Expose
    private Integer promptRank;
    @SerializedName("beginAt")
    @Expose
    private Long beginAt;
    @SerializedName("endAt")
    @Expose
    private Long endAt;
    @SerializedName("created")
    @Expose
    private Object created;
    @SerializedName("modified")
    @Expose
    private Object modified;
    @SerializedName("avatarUri")
    @Expose
    private String avatarUri;
    @SerializedName("galleryUri")
    @Expose
    private Object galleryUri;
    @SerializedName("homeStick")
    @Expose
    private Integer homeStick;
    @SerializedName("appHomeStick")
    @Expose
    private Integer appHomeStick;
    @SerializedName("guide")
    @Expose
    private String guide;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("promptUri")
    @Expose
    private String promptUri;
    @SerializedName("warehouseId")
    @Expose
    private Integer warehouseId;
    @SerializedName("provinceId")
    @Expose
    private Integer provinceId;
    @SerializedName("countryId")
    @Expose
    private Integer countryId;
    @SerializedName("districtId")
    @Expose
    private Integer districtId;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;
    @SerializedName("awarded")
    @Expose
    private Integer awarded;
    @SerializedName("needVerify")
    @Expose
    private Integer needVerify;
    @SerializedName("priceBeforePromo")
    @Expose
    private Integer priceBeforePromo;
    @SerializedName("priceAfterPromo")
    @Expose
    private Integer priceAfterPromo;
    @SerializedName("valuePromotion")
    @Expose
    private Integer valuePromotion;
    @SerializedName("displayType")
    @Expose
    private Integer displayType;
    @SerializedName("isHighlight")
    @Expose
    private Integer isHighlight;
    @SerializedName("totalHoldtime")
    @Expose
    private Object totalHoldtime;
    @SerializedName("isUserHunting")
    @Expose
    private Boolean isUserHunting;
    @SerializedName("userHuntingCount")
    @Expose
    private Integer userHuntingCount;
    @SerializedName("ranking")
    @Expose
    private Object ranking;
    @SerializedName("dealScores")
    @Expose
    private List<Object> dealScores = null;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getPromptRank() {
        return promptRank;
    }

    public void setPromptRank(Integer promptRank) {
        this.promptRank = promptRank;
    }

    public Long getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(Long beginAt) {
        this.beginAt = beginAt;
    }

    public Long getEndAt() {
        return endAt;
    }

    public void setEndAt(Long endAt) {
        this.endAt = endAt;
    }

    public Object getCreated() {
        return created;
    }

    public void setCreated(Object created) {
        this.created = created;
    }

    public Object getModified() {
        return modified;
    }

    public void setModified(Object modified) {
        this.modified = modified;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }

    public Object getGalleryUri() {
        return galleryUri;
    }

    public void setGalleryUri(Object galleryUri) {
        this.galleryUri = galleryUri;
    }

    public Integer getHomeStick() {
        return homeStick;
    }

    public void setHomeStick(Integer homeStick) {
        this.homeStick = homeStick;
    }

    public Integer getAppHomeStick() {
        return appHomeStick;
    }

    public void setAppHomeStick(Integer appHomeStick) {
        this.appHomeStick = appHomeStick;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPromptUri() {
        return promptUri;
    }

    public void setPromptUri(String promptUri) {
        this.promptUri = promptUri;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Integer getAwarded() {
        return awarded;
    }

    public void setAwarded(Integer awarded) {
        this.awarded = awarded;
    }

    public Integer getNeedVerify() {
        return needVerify;
    }

    public void setNeedVerify(Integer needVerify) {
        this.needVerify = needVerify;
    }

    public Integer getPriceBeforePromo() {
        return priceBeforePromo;
    }

    public void setPriceBeforePromo(Integer priceBeforePromo) {
        this.priceBeforePromo = priceBeforePromo;
    }

    public Integer getPriceAfterPromo() {
        return priceAfterPromo;
    }

    public void setPriceAfterPromo(Integer priceAfterPromo) {
        this.priceAfterPromo = priceAfterPromo;
    }

    public Integer getValuePromotion() {
        return valuePromotion;
    }

    public void setValuePromotion(Integer valuePromotion) {
        this.valuePromotion = valuePromotion;
    }

    public Integer getDisplayType() {
        return displayType;
    }

    public void setDisplayType(Integer displayType) {
        this.displayType = displayType;
    }

    public Integer getIsHighlight() {
        return isHighlight;
    }

    public void setIsHighlight(Integer isHighlight) {
        this.isHighlight = isHighlight;
    }

    public Object getTotalHoldtime() {
        return totalHoldtime;
    }

    public void setTotalHoldtime(Object totalHoldtime) {
        this.totalHoldtime = totalHoldtime;
    }

    public Boolean getIsUserHunting() {
        return isUserHunting;
    }

    public void setIsUserHunting(Boolean isUserHunting) {
        this.isUserHunting = isUserHunting;
    }

    public Integer getUserHuntingCount() {
        return userHuntingCount;
    }

    public void setUserHuntingCount(Integer userHuntingCount) {
        this.userHuntingCount = userHuntingCount;
    }

    public Object getRanking() {
        return ranking;
    }

    public void setRanking(Object ranking) {
        this.ranking = ranking;
    }

    public List<Object> getDealScores() {
        return dealScores;
    }

    public void setDealScores(List<Object> dealScores) {
        this.dealScores = dealScores;
    }

}
