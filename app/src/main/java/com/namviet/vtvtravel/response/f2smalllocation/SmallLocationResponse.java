package com.namviet.vtvtravel.response.f2smalllocation;

import com.namviet.vtvtravel.model.f2smalllocation.Travel;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class SmallLocationResponse extends BaseResponse {
    private boolean isLoadMore;

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public void setData(Data data) {
        this.data = data;
    }

    private Data data;

    public Data getData() {
        return data;
    }

    public class Data {
        private List<Travel> items;
        private String region_id;
        private String limit;
        private String page;
        private String hasMore;
        private String more_link;
        private String nameRegion;

        public void setItems(List<Travel> items) {
            this.items = items;
        }

        public void setRegion_id(String region_id) {
            this.region_id = region_id;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public void setHasMore(String hasMore) {
            this.hasMore = hasMore;
        }

        public void setMore_link(String more_link) {
            this.more_link = more_link;
        }

        public String getNameRegion() {
            return nameRegion;
        }

        public void setNameRegion(String nameRegion) {
            this.nameRegion = nameRegion;
        }

        public String getMore_link() {
            return more_link;
        }

        public List<Travel> getItems() {
            return items;
        }

        public String getRegion_id() {
            return region_id;
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
    }
}
