package com.namviet.vtvtravel.help;


import com.namviet.vtvtravel.model.News;

import java.util.List;

public class ItemGroupNew {
    private List<News> group;

    public ItemGroupNew(List<News> group) {
        this.group = group;
    }

    public List<News> getGroup() {
        return group;
    }

    public void setGroup(List<News> group) {
        this.group = group;
    }
}
