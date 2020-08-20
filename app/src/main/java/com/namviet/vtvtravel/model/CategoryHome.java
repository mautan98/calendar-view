package com.namviet.vtvtravel.model;

import com.daimajia.slider.library.ItemGroup;
import com.daimajia.slider.library.Travel;

import java.util.List;

public class CategoryHome {

    private String widget_type;
    private String widget_class;
    private String widget_id;
    private String code;
    private String name;
    private String content_link;
    private String placeholder;
    private List<Travel> travelList;
    private List<ItemMenu> menus;
    private List<Travel> items;
    private List<String> background_urls;

    public String getWidget_type() {
        return widget_type;
    }

    public void setWidget_type(String widget_type) {
        this.widget_type = widget_type;
    }

    public String getWidget_class() {
        return widget_class;
    }

    public void setWidget_class(String widget_class) {
        this.widget_class = widget_class;
    }

    public String getWidget_id() {
        return widget_id;
    }

    public void setWidget_id(String widget_id) {
        this.widget_id = widget_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent_link() {
        return content_link;
    }

    public void setContent_link(String content_link) {
        this.content_link = content_link;
    }

    public List<Travel> getTravelList() {
        return travelList;
    }

    public void setTravelList(List<Travel> itemGroup) {
        this.travelList = itemGroup;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public List<ItemMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<ItemMenu> menus) {
        this.menus = menus;
    }

    public List<Travel> getItems() {
        return items;
    }

    public void setItems(List<Travel> items) {
        this.items = items;
    }

    public List<String> getBackground_urls() {
        return background_urls;
    }

    public void setBackground_urls(List<String> background_urls) {
        this.background_urls = background_urls;
    }
}
