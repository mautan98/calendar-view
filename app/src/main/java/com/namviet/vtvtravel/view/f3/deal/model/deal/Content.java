
package com.namviet.vtvtravel.view.f3.deal.model.deal;


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
    private Object rule;
    @SerializedName("promptRank")
    @Expose
    private Object promptRank;
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
    private Long modified;
    @SerializedName("avatarUri")
    @Expose
    private Object avatarUri;
    @SerializedName("galleryUri")
    @Expose
    private Object galleryUri;
    @SerializedName("homeStick")
    @Expose
    private Object homeStick;
    @SerializedName("appHomeStick")
    @Expose
    private Object appHomeStick;
    @SerializedName("guide")
    @Expose
    private Object guide;
    @SerializedName("price")
    @Expose
    private Object price;
    @SerializedName("promptUri")
    @Expose
    private Object promptUri;
    @SerializedName("warehouseId")
    @Expose
    private Integer warehouseId;
    @SerializedName("regionId")
    @Expose
    private Object regionId;
    @SerializedName("countryId")
    @Expose
    private Object countryId;
    @SerializedName("districtId")
    @Expose
    private Object districtId;
    @SerializedName("lat")
    @Expose
    private Object lat;
    @SerializedName("lng")
    @Expose
    private Object lng;
    @SerializedName("awarded")
    @Expose
    private Object awarded;
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
    @SerializedName("isUserHunting")
    @Expose
    private Boolean isUserHunting;
    @SerializedName("userHuntingCount")
    @Expose
    private Integer userHuntingCount;
    @SerializedName("isParent")
    @Expose
    private boolean isCampaign;
    @SerializedName("expireDate")
    @Expose
    private Long expireDate;
    @SerializedName("totalDeal")
    @Expose
    private String totalDeal;
    @Expose
    @SerializedName("isProcessing")
    private String isProcessing;

    public String getIsProcessing() {
        return isProcessing;
    }

    public void setIsProcessing(String isProcessing) {
        this.isProcessing = isProcessing;
    }

    public String getTotalDeal() {
        return totalDeal;
    }

    public void setTotalDeal(String totalDeal) {
        this.totalDeal = totalDeal;
    }

    public Long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Long expireDate) {
        this.expireDate = expireDate;
    }

    public Boolean getUserHunting() {
        return isUserHunting;
    }

    public void setUserHunting(Boolean userHunting) {
        isUserHunting = userHunting;
    }

    public boolean isCampaign() {
        return isCampaign;
    }

    public void setCampaign(boolean campaign) {
        isCampaign = campaign;
    }

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

    public Object getRule() {
        return rule;
    }

    public void setRule(Object rule) {
        this.rule = rule;
    }

    public Object getPromptRank() {
        return promptRank;
    }

    public void setPromptRank(Object promptRank) {
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

    public Long getModified() {
        return modified;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    public Object getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(Object avatarUri) {
        this.avatarUri = avatarUri;
    }

    public Object getGalleryUri() {
        return galleryUri;
    }

    public void setGalleryUri(Object galleryUri) {
        this.galleryUri = galleryUri;
    }

    public Object getHomeStick() {
        return homeStick;
    }

    public void setHomeStick(Object homeStick) {
        this.homeStick = homeStick;
    }

    public Object getAppHomeStick() {
        return appHomeStick;
    }

    public void setAppHomeStick(Object appHomeStick) {
        this.appHomeStick = appHomeStick;
    }

    public Object getGuide() {
        return guide;
    }

    public void setGuide(Object guide) {
        this.guide = guide;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public Object getPromptUri() {
        return promptUri;
    }

    public void setPromptUri(Object promptUri) {
        this.promptUri = promptUri;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Object getRegionId() {
        return regionId;
    }

    public void setRegionId(Object regionId) {
        this.regionId = regionId;
    }

    public Object getCountryId() {
        return countryId;
    }

    public void setCountryId(Object countryId) {
        this.countryId = countryId;
    }

    public Object getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Object districtId) {
        this.districtId = districtId;
    }

    public Object getLat() {
        return lat;
    }

    public void setLat(Object lat) {
        this.lat = lat;
    }

    public Object getLng() {
        return lng;
    }

    public void setLng(Object lng) {
        this.lng = lng;
    }

    public Object getAwarded() {
        return awarded;
    }

    public void setAwarded(Object awarded) {
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

}
