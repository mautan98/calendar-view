package com.namviet.vtvtravel.response.newhome;

import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.f2callnow.CallNowHistoryResponse;

import java.util.List;

public class AppVoucherResponse extends BaseResponse {


    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public class Item {
        private String id;
        private String avatarUri;
        private String bannerUri;
        private String linkVoucher;

        public String getId() {
            return id;
        }

        public String getAvatarUri() {
            return avatarUri;
        }

        public String getBannerUri() {
            return bannerUri;
        }

        public String getLinkVoucher() {
            return linkVoucher;
        }
    }
}
