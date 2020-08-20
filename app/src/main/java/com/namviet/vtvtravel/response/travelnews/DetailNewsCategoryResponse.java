package com.namviet.vtvtravel.response.travelnews;

import com.namviet.vtvtravel.model.travelnews.Travel;

import java.util.List;

public class DetailNewsCategoryResponse {
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

    public class Data{
        private String more_link;

        public String getMore_link() {
            return more_link;
        }

        public void setMore_link(String more_link) {
            this.more_link = more_link;
        }

        private List<Travel> items;

        public List<Travel> getItems() {
            return items;
        }

        public void setItems(List<Travel> items) {
            this.items = items;
        }
    }
}
