package com.namviet.vtvtravel.response.newhome;

import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class ItemAppVoucherNowResponse extends BaseResponse {

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public class Item {
        private int id;
        private String voucherName;
        private String avatarUri;
        private String bannerUri;
        private String linkVoucher;
        private Long startAt;
        private Long endAt;
        private String regionId;
        private String content;
        private String name;
        private String code;
        private String categoryId;
        private String homeUri;
        private int memberRankId;


        public String getHomeUri() {
            return homeUri;
        }

        public void setHomeUri(String homeUri) {
            this.homeUri = homeUri;
        }

        public Long getStartAt() {
            return startAt;
        }

        public void setStartAt(Long startAt) {
            this.startAt = startAt;
        }

        public Long getEndAt() {
            return endAt;
        }

        public void setEndAt(Long endAt) {
            this.endAt = endAt;
        }

        public String getRegionId() {
            return regionId;
        }

        public String getContent() {
            return content;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }



        public void setRegionId(String regionId) {
            this.regionId = regionId;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public int getMemberRankId() {
            return memberRankId;
        }

        public void setMemberRankId(int memberRankId) {
            this.memberRankId = memberRankId;
        }

        public void setVoucherName(String voucherName) {
            this.voucherName = voucherName;
        }

        public void setAvatarUri(String avatarUri) {
            this.avatarUri = avatarUri;
        }

        public void setBannerUri(String bannerUri) {
            this.bannerUri = bannerUri;
        }

        public String getLinkVoucher() {
            return linkVoucher;
        }

        public void setLinkVoucher(String linkVoucher) {
            this.linkVoucher = linkVoucher;
        }



        public String getVoucherName() {
            return voucherName;
        }

        public String getAvatarUri() {
            return avatarUri;
        }

        public String getBannerUri() {
            return bannerUri;
        }
    }

}
