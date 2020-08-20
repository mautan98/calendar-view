package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.Schedule;

import java.util.List;

public class SaveScheduleData {
    private String id;
    private String name;
    private List<Schedule> content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Schedule> getContent() {
        return content;
    }

    public void setContent(List<Schedule> content) {
        this.content = content;
    }
}
