package com.namviet.vtvtravel.response.newhome;

import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class AppPromotionPartnerResponse extends BaseResponse {

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public class Item {
        private String avatarUri;
        private String linkAdvertise;

        public String getLinkAdvertise() {
            return linkAdvertise;
        }

        public String getAvatarUri() {
            return avatarUri;
        }
    }

}
