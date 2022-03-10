package com.namviet.vtvtravel.response.f2travelvoucher;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.response.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class ListVoucherResponse extends BaseResponse {

    private Data data;


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        @Expose
        @SerializedName("content")
        private List<Voucher> vouchers;

        public List<Voucher> getVouchers() {
            return vouchers;
        }

        public void setVouchers(List<Voucher> vouchers) {
            this.vouchers = vouchers;
        }

        public class Voucher implements Serializable {
            //
            private boolean isSelectedLuckyWheel;

            public boolean isSelectedLuckyWheel() {
                return isSelectedLuckyWheel;
            }

            public void setSelectedLuckyWheel(boolean selectedLuckyWheel) {
                isSelectedLuckyWheel = selectedLuckyWheel;
            }

            @Expose
            @SerializedName("remainCoupon")
            private String remainCoupon;
            @Expose
            @SerializedName("coupons")
            private List<Coupons> coupons;
            @Expose
            @SerializedName("modifiedAt")
            private String modifiedAt;
            @Expose
            @SerializedName("createdAt")
            private String createdAt;
            @Expose
            @SerializedName("status")
            private String status;
            @Expose
            @SerializedName("couponSerial")
            private String couponSerial;
            @Expose
            @SerializedName("note")
            private String note;
            @Expose
            @SerializedName("isConfirmed")
            private String isConfirmed;
            @Expose
            @SerializedName("createdId")
            private String createdId;
            @Expose
            @SerializedName("serviceId")
            private String serviceId;
            @Expose
            @SerializedName("memberRankId")
            private String memberRankId;
            @Expose
            @SerializedName("linkVoucher")
            private String linkVoucher;
            @Expose
            @SerializedName("homeUri")
            private String homeUri;
            @Expose
            @SerializedName("bannerUri")
            private String bannerUri;
            @Expose
            @SerializedName("avatarUri")
            private String avatarUri;
            @Expose
            @SerializedName("partnerId")
            private String partnerId;
            @Expose
            @SerializedName("content")
            private String content;
            @Expose
            @SerializedName("regionId")
            private String regionId;
            @Expose
            @SerializedName("endAt")
            private String endAt;
            @Expose
            @SerializedName("startAt")
            private String startAt;
            @Expose
            @SerializedName("categoryId")
            private String categoryId;
            @Expose
            @SerializedName("code")
            private String code;
            @Expose
            @SerializedName("name")
            private String name;
            @Expose
            @SerializedName("id")
            private String id;

            public String getRemainCoupon() {
                return remainCoupon;
            }

            public void setRemainCoupon(String remainCoupon) {
                this.remainCoupon = remainCoupon;
            }

            public List<Coupons> getCoupons() {
                return coupons;
            }

            public void setCoupons(List<Coupons> coupons) {
                this.coupons = coupons;
            }

            public String getModifiedAt() {
                return modifiedAt;
            }

            public void setModifiedAt(String modifiedAt) {
                this.modifiedAt = modifiedAt;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCouponSerial() {
                return couponSerial;
            }

            public void setCouponSerial(String couponSerial) {
                this.couponSerial = couponSerial;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public String getIsConfirmed() {
                return isConfirmed;
            }

            public void setIsConfirmed(String isConfirmed) {
                this.isConfirmed = isConfirmed;
            }

            public String getCreatedId() {
                return createdId;
            }

            public void setCreatedId(String createdId) {
                this.createdId = createdId;
            }

            public String getServiceId() {
                return serviceId;
            }

            public void setServiceId(String serviceId) {
                this.serviceId = serviceId;
            }

            public String getMemberRankId() {
                return memberRankId;
            }

            public void setMemberRankId(String memberRankId) {
                this.memberRankId = memberRankId;
            }

            public String getLinkVoucher() {
                return linkVoucher;
            }

            public void setLinkVoucher(String linkVoucher) {
                this.linkVoucher = linkVoucher;
            }

            public String getHomeUri() {
                return homeUri;
            }

            public void setHomeUri(String homeUri) {
                this.homeUri = homeUri;
            }

            public String getBannerUri() {
                return bannerUri;
            }

            public void setBannerUri(String bannerUri) {
                this.bannerUri = bannerUri;
            }

            public String getAvatarUri() {
                return avatarUri;
            }

            public void setAvatarUri(String avatarUri) {
                this.avatarUri = avatarUri;
            }

            public String getPartnerId() {
                return partnerId;
            }

            public void setPartnerId(String partnerId) {
                this.partnerId = partnerId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getRegionId() {
                return regionId;
            }

            public void setRegionId(String regionId) {
                this.regionId = regionId;
            }

            public String getEndAt() {
                return endAt;
            }

            public void setEndAt(String endAt) {
                this.endAt = endAt;
            }

            public String getStartAt() {
                return startAt;
            }

            public void setStartAt(String startAt) {
                this.startAt = startAt;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
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

            public class Coupons implements Serializable {
                @Expose
                @SerializedName("modifiedAt")
                private String modifiedAt;
                @Expose
                @SerializedName("createdAt")
                private String createdAt;
                @Expose
                @SerializedName("remain")
                private String remain;
                @Expose
                @SerializedName("note")
                private String note;
                @Expose
                @SerializedName("couponsType")
                private String couponsType;
                @Expose
                @SerializedName("fileUri")
                private String fileUri;
                @Expose
                @SerializedName("code")
                private String code;
                @Expose
                @SerializedName("isConfirmed")
                private String isConfirmed;
                @Expose
                @SerializedName("cycleId")
                private String cycleId;
                @Expose
                @SerializedName("status")
                private String status;
                @Expose
                @SerializedName("originPrice")
                private String originPrice;
                @Expose
                @SerializedName("maxDiscountPrice")
                private String maxDiscountPrice;
                @Expose
                @SerializedName("miniumApplyIncome")
                private String miniumApplyIncome;
                @Expose
                @SerializedName("discountPrice")
                private String discountPrice;
                @Expose
                @SerializedName("discountType")
                private String discountType;
                @Expose
                @SerializedName("quantity")
                private String quantity;
                @Expose
                @SerializedName("id")
                private String id;

                public String getModifiedAt() {
                    return modifiedAt;
                }

                public void setModifiedAt(String modifiedAt) {
                    this.modifiedAt = modifiedAt;
                }

                public String getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(String createdAt) {
                    this.createdAt = createdAt;
                }

                public String getRemain() {
                    return remain;
                }

                public void setRemain(String remain) {
                    this.remain = remain;
                }

                public String getNote() {
                    return note;
                }

                public void setNote(String note) {
                    this.note = note;
                }

                public String getCouponsType() {
                    return couponsType;
                }

                public void setCouponsType(String couponsType) {
                    this.couponsType = couponsType;
                }

                public String getFileUri() {
                    return fileUri;
                }

                public void setFileUri(String fileUri) {
                    this.fileUri = fileUri;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getIsConfirmed() {
                    return isConfirmed;
                }

                public void setIsConfirmed(String isConfirmed) {
                    this.isConfirmed = isConfirmed;
                }

                public String getCycleId() {
                    return cycleId;
                }

                public void setCycleId(String cycleId) {
                    this.cycleId = cycleId;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getOriginPrice() {
                    return originPrice;
                }

                public void setOriginPrice(String originPrice) {
                    this.originPrice = originPrice;
                }

                public String getMaxDiscountPrice() {
                    return maxDiscountPrice;
                }

                public void setMaxDiscountPrice(String maxDiscountPrice) {
                    this.maxDiscountPrice = maxDiscountPrice;
                }

                public String getMiniumApplyIncome() {
                    return miniumApplyIncome;
                }

                public void setMiniumApplyIncome(String miniumApplyIncome) {
                    this.miniumApplyIncome = miniumApplyIncome;
                }

                public String getDiscountPrice() {
                    return discountPrice;
                }

                public void setDiscountPrice(String discountPrice) {
                    this.discountPrice = discountPrice;
                }

                public String getDiscountType() {
                    return discountType;
                }

                public void setDiscountType(String discountType) {
                    this.discountType = discountType;
                }

                public String getQuantity() {
                    return quantity;
                }

                public void setQuantity(String quantity) {
                    this.quantity = quantity;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }
        }
    }
}
