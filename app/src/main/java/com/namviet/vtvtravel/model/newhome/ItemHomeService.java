package com.namviet.vtvtravel.model.newhome;

import com.namviet.vtvtravel.response.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class ItemHomeService<T> implements Serializable {

    private String tipUser;
    private boolean showBtnRegisterNow;

    public String getTipUser() {
        return tipUser;
    }

    public void setTipUser(String tipUser) {
        this.tipUser = tipUser;
    }

    public boolean isShowBtnRegisterNow() {
        return showBtnRegisterNow;
    }

    public void setShowBtnRegisterNow(boolean showBtnRegisterNow) {
        this.showBtnRegisterNow = showBtnRegisterNow;
    }

    private String username;
    private String avatar;
    private String descriptionUser;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescriptionUser() {
        return descriptionUser;
    }

    public void setDescriptionUser(String descriptionUser) {
        this.descriptionUser = descriptionUser;
    }

    private int positionClick = 0;

    public int getPositionClick() {
        return positionClick;
    }

    public void setPositionClick(int positionClick) {
        this.positionClick = positionClick;
    }

    private T dataExtraSecondAfterClickTab;

    public T getDataExtraSecondAfterClickTab() {
        return dataExtraSecondAfterClickTab;
    }

    public void setDataExtraSecondAfterClickTab(T dataExtraSecondAfterClickTab) {
        this.dataExtraSecondAfterClickTab = dataExtraSecondAfterClickTab;
    }

    private T dataExtraSecond;

    public T getDataExtraSecond() {
        return dataExtraSecond;
    }

    public void setDataExtraSecond(T dataExtraSecond) {
        this.dataExtraSecond = dataExtraSecond;
    }

    private T dataExtra;

    public T getDataExtra() {
        return dataExtra;
    }

    public void setDataExtra(T dataExtra) {
        this.dataExtra = dataExtra;
    }

    private String widget_type;
    private String widget_class;
    private String widget_id;
    private String code;
    private String placeholder;
    private String link_deal_filter;
    private List<Item> menus;
    private List<String> background_urls;
    private String name;
    private String content_link;
    private String link_home_deal;
    private String description;
    private String html_icon;
    private String icon_url;
    private String content_link_places;
    private String content_link_restaurants;
    private String content_link_centers;
    private String content_link_hotels;
    private String content_link_all;
    private String banner_url;
    private List<Item> items;

    public String getLink_home_deal() {
        return link_home_deal;
    }

    public String getWidget_type() {
        return widget_type;
    }

    public String getWidget_class() {
        return widget_class;
    }

    public String getWidget_id() {
        return widget_id;
    }

    public String getCode() {
        return code;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getLink_deal_filter() {
        return link_deal_filter;
    }

    public List<Item> getMenus() {
        return menus;
    }

    public List<String> getBackground_urls() {
        return background_urls;
    }

    public String getName() {
        return name;
    }

    public String getContent_link() {
        return content_link;
    }

    public String getDescription() {
        return description;
    }

    public String getHtml_icon() {
        return html_icon;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public String getContent_link_places() {
        return content_link_places;
    }

    public String getContent_link_restaurants() {
        return content_link_restaurants;
    }

    public String getContent_link_centers() {
        return content_link_centers;
    }

    public String getContent_link_hotels() {
        return content_link_hotels;
    }

    public String getContent_link_all() {
        return content_link_all;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public List<Item> getItems() {
        return items;
    }

    public class Item implements Serializable {
        private String id;
        private String code;
        private String name;
        private String icon_url;
        private String link;
        private String category_id;
        private String html_icon;
        private String weight;
        private String banner_url;
        private String content_link;


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

        public String getContent_link() {
            return content_link;
        }
    }
}
