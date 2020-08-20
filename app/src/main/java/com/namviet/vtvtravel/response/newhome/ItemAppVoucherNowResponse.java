package com.namviet.vtvtravel.response.newhome;

import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class ItemAppVoucherNowResponse extends BaseResponse {

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public class Item {
        private String id;
        private String voucherName;
        private String avatarUri;
        private String bannerUri;
        private String linkVoucher;

        public void setId(String id) {
            this.id = id;
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

        public String getId() {
            return id;
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
