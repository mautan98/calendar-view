package com.namviet.vtvtravel.response.f2searchmain;

import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class MainResultSearchResponse extends BaseResponse {

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

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String code;
        private String more_link;
        private List<Travel> items;
        private List<Travel> items_news;

        public String getMore_link() {
            return more_link;
        }

        public void setMore_link(String more_link) {
            this.more_link = more_link;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<Travel> getItems() {
            return items;
        }

        public void setItems(List<Travel> items) {
            this.items = items;
        }

        public List<Travel> getItems_news() {
            return items_news;
        }

        public void setItems_news(List<Travel> items_news) {
            this.items_news = items_news;
        }
    }
}
