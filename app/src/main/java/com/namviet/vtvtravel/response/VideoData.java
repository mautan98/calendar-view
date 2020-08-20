package com.namviet.vtvtravel.response;

import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.model.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoData {
    private int limit;
    private int page;
    private boolean hasMore;
    private String more_link;
    private ArrayList<Video> items;

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

    public ArrayList<Video> getItems() {
        return items;
    }

    public void setItems(ArrayList<Video> items) {
        this.items = items;
    }
}
