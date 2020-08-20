package com.namviet.vtvtravel.response.newhome;

import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class ItemAppDiscoverResponse extends BaseResponse {

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public class Item {
        private String id;
        private String content_type;
        private String name;
        private String url_alias;
        private String logo_url;
        private String detail_link;
        private String short_description;
        private String description;

        public String getId() {
            return id;
        }

        public String getContent_type() {
            return content_type;
        }

        public String getName() {
            return name;
        }

        public String getUrl_alias() {
            return url_alias;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public String getDetail_link() {
            return detail_link;
        }

        public String getShort_description() {
            return short_description;
        }

        public String getDescription() {
            return description;
        }
    }

}
