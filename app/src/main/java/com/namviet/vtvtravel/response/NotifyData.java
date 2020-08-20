package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.ItemNotify;
import com.namviet.vtvtravel.model.Video;

import java.util.ArrayList;
import java.util.List;

public class NotifyData {
    private int limit;
    private int page;
    private boolean hasMore;
    private String more_link;
    private List<ItemNotify> items;

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

    public String getMore_link() {
        return more_link;
    }

    public void setMore_link(String more_link) {
        this.more_link = more_link;
    }

    public List<ItemNotify> getItems() {
        return items;
    }

    public void setItems(List<ItemNotify> items) {
        this.items = items;
    }
}
