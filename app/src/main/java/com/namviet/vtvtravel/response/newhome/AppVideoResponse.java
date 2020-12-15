package com.namviet.vtvtravel.response.newhome;

import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class AppVideoResponse extends BaseResponse {

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public class Item {
        private String id;
        private String content_type;
        private String name;
        private String logo_url;
        private String detail_link;
        private String view_count;
        private String short_description;
        private String description;
        private String streaming_url;

        private String category_tree_code;
        private String category_tree_name;

        public String getCategory_tree_code() {
            return category_tree_code;
        }

        public String getCategory_tree_name() {
            return category_tree_name;
        }

        public String getStreaming_url() {
            return streaming_url;
        }

        public String getId() {
            return id;
        }

        public String getContent_type() {
            return content_type;
        }

        public String getName() {
            return name;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public String getDetail_link() {
            return detail_link;
        }

        public String getView_count() {
            return view_count;
        }

        public String getShort_description() {
            return short_description;
        }

        public String getDescription() {
            return description;
        }
    }

}
