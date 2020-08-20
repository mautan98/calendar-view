package com.namviet.vtvtravel.model;

import com.daimajia.slider.library.Travel;

import java.util.List;

public class ListPlace {
    private int limit;
    private int page;
    private boolean hasMore;
    private List<Travel> items;
    private String more_link;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<Travel> getItems() {
        return items;
    }

    public void setItems(List<Travel> items) {
        this.items = items;
    }

    public String getMore_link() {
        return more_link;
    }

    public void setMore_link(String more_link) {
        this.more_link = more_link;
    }
}
