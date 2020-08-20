package com.namviet.vtvtravel.model.filter;

import com.namviet.vtvtravel.response.f2filter.FilterByPageResponse;

import java.io.Serializable;

public class ItemTab implements Serializable {
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private String id;
    private String code;
    private String name;
    private String icon_url;
    private String icon_enable_url;
    private String link;
    private String category_id;
    private String html_icon;
    private String weight;
    private String banner_url;

    private FilterByPageResponse dataHasLoaded;


    public FilterByPageResponse getDataHasLoaded() {
        return dataHasLoaded;
    }

    public void setDataHasLoaded(FilterByPageResponse dataHasLoaded) {
        this.dataHasLoaded = dataHasLoaded;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public String getIcon_enable_url() {
        return icon_enable_url;
    }

    public String getLink() {
        return link;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getHtml_icon() {
        return html_icon;
    }

    public String getWeight() {
        return weight;
    }

    public String getBanner_url() {
        return banner_url;
    }
}
