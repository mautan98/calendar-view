package com.namviet.vtvtravel.model;

import android.view.View;

public class TabItem {
    private String title;
    private View mView;

    public TabItem(String title, View mView) {
        this.title = title;
        this.mView = mView;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public View getmView() {
        return mView;
    }

    public void setmView(View mView) {
        this.mView = mView;
    }
}
