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
        private int id;
        private String avatarUri;
        private String bannerUri;
        private String linkVoucher;
        private String homeUri;
        private String name;
        private Long startAt;
        private Long endAt;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHomeUri() {
            return homeUri;
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


        public void setAvatarUri(String avatarUri) {
            this.avatarUri = avatarUri;
        }

        public void setBannerUri(String bannerUri) {
            this.bannerUri = bannerUri;
        }

        public void setLinkVoucher(String linkVoucher) {
            this.linkVoucher = linkVoucher;
        }

        public void setHomeUri(String homeUri) {
            this.homeUri = homeUri;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
