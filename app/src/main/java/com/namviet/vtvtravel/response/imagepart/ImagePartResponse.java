package com.namviet.vtvtravel.response.imagepart;

import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class ImagePartResponse extends BaseResponse {
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
        private String name;
        private String limit;
        private String page;
        private String hasMore;
        private String more_link;
        private List<Travel> items;

        public String getName() {
            return name;
        }

        public String getLimit() {
            return limit;
        }

        public String getPage() {
            return page;
        }

        public String getHasMore() {
            return hasMore;
        }

        public String getMore_link() {
            return more_link;
        }

        public List<Travel> getItems() {
            return items;
        }
    }
}
