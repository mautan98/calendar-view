
package com.namviet.vtvtravel.view.f3.deal.model.dealcampaign;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("warehouseId")
    @Expose
    private Integer warehouseId;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("beginAt")
    @Expose
    private Long beginAt;
    @SerializedName("endAt")
    @Expose
    private Long endAt;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("rule")
    @Expose
    private String rule;
    @SerializedName("guide")
    @Expose
    private String guide;
    @SerializedName("avatarUri")
    @Expose
    private String avatarUri;
    @SerializedName("galleryUri")
    @Expose
    private List<String> galleryUri = null;
    @SerializedName("appHomeStick")
    @Expose
    private Integer appHomeStick;
    @SerializedName("homeStick")
    @Expose
    private Integer homeStick;
    @SerializedName("isHighlight")
    @Expose
    private Integer isHighlight;
    @SerializedName("promptUri")
    @Expose
    private String promptUri;
    @SerializedName("promptRank")
    @Expose
    private Integer promptRank;
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
    @SerializedName("needVerify")
    @Expose
    private Integer needVerify;
    @SerializedName("awarded")
    @Expose
    private Integer awarded;
    @SerializedName("created")
    @Expose
    private Long created;
    @SerializedName("modified")
    @Expose
    private Long modified;
    @SerializedName("price")
    @Expose
    private Object price;
    @SerializedName("priceBeforePromo")
    @Expose
    private Integer priceBeforePromo;
    @SerializedName("priceAfterPromo")
    @Expose
    private Integer priceAfterPromo;
    @SerializedName("totalProduct")
    @Expose
    private Object totalProduct;
    @SerializedName("isDisplayOnApp")
    @Expose
    private Object isDisplayOnApp;
    @SerializedName("valuePromotion")
    @Expose
    private Integer valuePromotion;
    @SerializedName("displayType")
    @Expose
    private Integer displayType;
    @SerializedName("userHuntingCount")
    @Expose
    private Integer userHuntingCount;
    @SerializedName("totalHoldTime")
    @Expose
    private Long totalHoldTime;
    @SerializedName("totalDeal")
    @Expose
    private Integer totalDeal;
    @SerializedName("isUserHunting")
    @Expose
    private Boolean isUserHunting;
    @SerializedName("ranking")
    @Expose
    private Object ranking;
    @SerializedName("deals")
    @Expose
    private List<Deal> deals = null;
    @SerializedName("dealCampaignScores")
    @Expose
    private List<DealCampaignScore> dealCampaignScores = null;
    @SerializedName("isParent")
    @Expose
    private boolean isCampaign;

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

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }

    public List<String> getGalleryUri() {
        return galleryUri;
    }

    public void setGalleryUri(List<String> galleryUri) {
        this.galleryUri = galleryUri;
    }

    public Integer getAppHomeStick() {
        return appHomeStick;
    }

    public void setAppHomeStick(Integer appHomeStick) {
        this.appHomeStick = appHomeStick;
    }

    public Integer getHomeStick() {
        return homeStick;
    }

    public void setHomeStick(Integer homeStick) {
        this.homeStick = homeStick;
    }

    public Integer getIsHighlight() {
        return isHighlight;
    }

    public void setIsHighlight(Integer isHighlight) {
        this.isHighlight = isHighlight;
    }

    public String getPromptUri() {
        return promptUri;
    }

    public void setPromptUri(String promptUri) {
        this.promptUri = promptUri;
    }

    public Integer getPromptRank() {
        return promptRank;
    }

    public void setPromptRank(Integer promptRank) {
        this.promptRank = promptRank;
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

    public Integer getNeedVerify() {
        return needVerify;
    }

    public void setNeedVerify(Integer needVerify) {
        this.needVerify = needVerify;
    }

    public Integer getAwarded() {
        return awarded;
    }

    public void setAwarded(Integer awarded) {
        this.awarded = awarded;
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

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
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

    public Object getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(Object totalProduct) {
        this.totalProduct = totalProduct;
    }

    public Object getIsDisplayOnApp() {
        return isDisplayOnApp;
    }

    public void setIsDisplayOnApp(Object isDisplayOnApp) {
        this.isDisplayOnApp = isDisplayOnApp;
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

    public Integer getUserHuntingCount() {
        return userHuntingCount;
    }

    public void setUserHuntingCount(Integer userHuntingCount) {
        this.userHuntingCount = userHuntingCount;
    }

    public Long getTotalHoldTime() {
        return totalHoldTime;
    }

    public void setTotalHoldTime(Long totalHoldTime) {
        this.totalHoldTime = totalHoldTime;
    }

    public Integer getTotalDeal() {
        return totalDeal;
    }

    public void setTotalDeal(Integer totalDeal) {
        this.totalDeal = totalDeal;
    }

    public Boolean getIsUserHunting() {
        return isUserHunting;
    }

    public void setIsUserHunting(Boolean isUserHunting) {
        this.isUserHunting = isUserHunting;
    }

    public Object getRanking() {
        return ranking;
    }

    public void setRanking(Object ranking) {
        this.ranking = ranking;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }

    public List<DealCampaignScore> getDealCampaignScores() {
        return dealCampaignScores;
    }

    public void setDealCampaignScores(List<DealCampaignScore> dealCampaignScores) {
        this.dealCampaignScores = dealCampaignScores;
    }

}
