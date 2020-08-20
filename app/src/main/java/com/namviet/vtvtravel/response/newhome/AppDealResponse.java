package com.namviet.vtvtravel.response.newhome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class AppDealResponse extends BaseResponse {
    @Expose
    @SerializedName("data")
    List<Data> dataList;

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    public class Data {

        @Expose
        @SerializedName("rankIcon")
        private String rankIcon;
        @Expose
        @SerializedName("discount")
        private String discount;
        @Expose
        @SerializedName("detailLink")
        private String detailLink;
        @Expose
        @SerializedName("displayPrice")
        private String displayPrice;
        @Expose
        @SerializedName("userTotal")
        private String userTotal;
        @Expose
        @SerializedName("endAt")
        private String endAt;
        @Expose
        @SerializedName("beginAt")
        private String beginAt;
        @Expose
        @SerializedName("originPrice")
        private String originPrice;
        @Expose
        @SerializedName("avatarUri")
        private String avatarUri;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private String id;

        public String getRankIcon() {
            return rankIcon;
        }

        public void setRankIcon(String rankIcon) {
            this.rankIcon = rankIcon;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDetailLink() {
            return detailLink;
        }

        public void setDetailLink(String detailLink) {
            this.detailLink = detailLink;
        }

        public String getDisplayPrice() {
            return displayPrice;
        }

        public void setDisplayPrice(String displayPrice) {
            this.displayPrice = displayPrice;
        }

        public String getUserTotal() {
            return userTotal;
        }

        public void setUserTotal(String userTotal) {
            this.userTotal = userTotal;
        }

        public String getEndAt() {
            return endAt;
        }

        public void setEndAt(String endAt) {
            this.endAt = endAt;
        }

        public String getBeginAt() {
            return beginAt;
        }

        public void setBeginAt(String beginAt) {
            this.beginAt = beginAt;
        }

        public String getOriginPrice() {
            return originPrice;
        }

        public void setOriginPrice(String originPrice) {
            this.originPrice = originPrice;
        }

        public String getAvatarUri() {
            return avatarUri;
        }

        public void setAvatarUri(String avatarUri) {
            this.avatarUri = avatarUri;
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


}
