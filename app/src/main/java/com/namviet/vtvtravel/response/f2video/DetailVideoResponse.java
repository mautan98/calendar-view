package com.namviet.vtvtravel.response.f2video;

import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.travelnews.DetailCategoryVideoResponse;

import java.util.ArrayList;
import java.util.List;

public class DetailVideoResponse extends BaseResponse {
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
        private String hasMore;
        private List<Video> items;
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

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getHasMore() {
            return hasMore;
        }

        public void setHasMore(String hasMore) {
            this.hasMore = hasMore;
        }

        public List<Video> getItems() {
            return items;
        }

        public void setItems(List<Video> items) {
            this.items = items;
        }
    }
}
