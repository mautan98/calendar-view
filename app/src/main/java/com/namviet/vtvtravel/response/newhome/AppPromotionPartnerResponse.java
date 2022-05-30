package com.namviet.vtvtravel.response.newhome;

import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class AppPromotionPartnerResponse extends BaseResponse {

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public class Item {
        private String imageUri;
        private String linkAdvertise;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLinkAdvertise() {
            return linkAdvertise;
        }

        public String getAvatarUri() {
            return imageUri;
        }
    }

}
