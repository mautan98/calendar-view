package com.namviet.vtvtravel.model;

import java.util.ArrayList;

public class GroupSchedule {
    private String title;
    private ArrayList<Schedule> content;
    private String keyGroup;

    public GroupSchedule(String title, ArrayList<Schedule> content, String keyGroup) {
        this.title = title;
        this.content = content;
        this.keyGroup = keyGroup;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Schedule> getContent() {
        return content;
    }

    public void setContent(ArrayList<Schedule> content) {
        this.content = content;
    }

    public String getKeyGroup() {
        return keyGroup;
    }

    public void setKeyGroup(String keyGroup) {
        this.keyGroup = keyGroup;
    }
}
