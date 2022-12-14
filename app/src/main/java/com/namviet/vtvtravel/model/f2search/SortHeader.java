package com.namviet.vtvtravel.model.f2search;

import java.util.ArrayList;

public class SortHeader {
    private boolean isOpen = false;
    private String name;
    private String namePlace;
    private String nameNews;
    private String nameVideo;
    private String label;
    private String id;
    private boolean isSelected = false;
    private ArrayList<Children> children;
    private ArrayList<Children> childrenPlace;
    private ArrayList<Children> childrenNews;
    private ArrayList<Children> childrenVideo;
    private Content content;

    public String getName() {
        return name;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public String getNameNews() {
        return nameNews;
    }

    public void setNameNews(String nameNews) {
        this.nameNews = nameNews;
    }

    public String getNameVideo() {
        return nameVideo;
    }

    public void setNameVideo(String nameVideo) {
        this.nameVideo = nameVideo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ArrayList<Children> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Children> children) {
        this.children = children;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<Children> getChildrenPlace() {
        return childrenPlace;
    }

    public void setChildrenPlace(ArrayList<Children> childrenPlace) {
        this.childrenPlace = childrenPlace;
    }

    public ArrayList<Children> getChildrenNews() {
        return childrenNews;
    }

    public void setChildrenNews(ArrayList<Children> childrenNews) {
        this.childrenNews = childrenNews;
    }

    public ArrayList<Children> getChildrenVideo() {
        return childrenVideo;
    }

    public void setChildrenVideo(ArrayList<Children> childrenVideo) {
        this.childrenVideo = childrenVideo;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
