package com.namviet.vtvtravel.response.f2topexperience;

import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class SubTopExperienceResponse extends BaseResponse {
    private int type; //for 2 tab in travel news detail

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private boolean isLoadMore;

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    private Data data;

    public Data getData() {
        return data;
    }

    public class Data{
        private String limit;
        private String page;
        private String total;
        private String hasMore;
        private String keyword;
        private List<Travel> items;
        private String more_link;

        public String getMore_link() {
            return more_link;
        }

        public void setMore_link(String more_link) {
            this.more_link = more_link;
        }

        public String getLimit() {
            return limit;
        }

        public String getPage() {
            return page;
        }

        public String getTotal() {
            return total;
        }

        public String getHasMore() {
            return hasMore;
        }

        public String getKeyword() {
            return keyword;
        }

        public List<Travel> getItems() {
            return items;
        }
    }
}
