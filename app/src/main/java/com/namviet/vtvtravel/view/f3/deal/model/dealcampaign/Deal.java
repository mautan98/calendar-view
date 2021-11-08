
package com.namviet.vtvtravel.view.f3.deal.model.dealcampaign;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Deal {

    @SerializedName("id")
    @Expose
    private Integer id;
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
    private String galleryUri;
    @SerializedName("homeStick")
    @Expose
    private Integer homeStick;
    @SerializedName("appHomeStick")
    @Expose
    private Integer appHomeStick;
    @SerializedName("guide")
    @Expose
    private String guide;
    @SerializedName("promptUri")
    @Expose
    private String promptUri;
    @SerializedName("provinceId")
    @Expose
    private Integer provinceId;
    @SerializedName("countryId")
    @Expose
    private Integer countryId;
    @SerializedName("districtId")
    @Expose
    private Integer districtId;
    @SerializedName("wardId")
    @Expose
    private Integer wardId;
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
    @SerializedName("isHighlight")
    @Expose
    private Integer isHighlight;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("dealScores")
    @Expose
    private List<DealScore> dealScores = null;

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

    public String getGalleryUri() {
        return galleryUri;
    }

    public void setGalleryUri(String galleryUri) {
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

    public String getPromptUri() {
        return promptUri;
    }

    public void setPromptUri(String promptUri) {
        this.promptUri = promptUri;
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

    public Integer getWardId() {
        return wardId;
    }

    public void setWardId(Integer wardId) {
        this.wardId = wardId;
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

    public Integer getIsHighlight() {
        return isHighlight;
    }

    public void setIsHighlight(Integer isHighlight) {
        this.isHighlight = isHighlight;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<DealScore> getDealScores() {
        return dealScores;
    }

    public void setDealScores(List<DealScore> dealScores) {
        this.dealScores = dealScores;
    }

}
