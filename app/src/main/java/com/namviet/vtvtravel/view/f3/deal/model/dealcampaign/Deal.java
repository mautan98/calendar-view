
package com.namviet.vtvtravel.view.f3.deal.model.dealcampaign;

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
    @SerializedName("promptUri")
    @Expose
    private Object promptUri;
    @SerializedName("provinceId")
    @Expose
    private Object provinceId;
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
    @SerializedName("isHighlight")
    @Expose
    private Integer isHighlight;
    @SerializedName("price")
    @Expose
    private Object price;

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

    public Object getPromptUri() {
        return promptUri;
    }

    public void setPromptUri(Object promptUri) {
        this.promptUri = promptUri;
    }

    public Object getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Object provinceId) {
        this.provinceId = provinceId;
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

    public Integer getIsHighlight() {
        return isHighlight;
    }

    public void setIsHighlight(Integer isHighlight) {
        this.isHighlight = isHighlight;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

}
