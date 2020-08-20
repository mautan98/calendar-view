package com.namviet.vtvtravel.model.travelnews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Travel implements Serializable {
    private String id;
    private String content_type;
    private String streaming_url;
    private String type;
    private String name;
    private String url_alias;
    private String short_description;
    private String logo_url;
    private String view_count;
    private String created;
    private String detail_link;
    private String detail_linkV2;
    private String time_ago;
    private String address;
    private String region_name;
    private String open_week;
    private String range_time;
    private String price_from;
    private String price_to;

    private String region_id;
    private String type_open;
    private String link_share;
    private String evaluate;
    private String evaluate_text;

    private String comment_count;
    private String distance;
    private String standard_rate;
    private String url_icon;
    private Collection collection;

    private String is_trend;
    private String icon;

    private int typeItem = 1000;

    public String getStreaming_url() {
        return streaming_url;
    }

    public void setStreaming_url(String streaming_url) {
        this.streaming_url = streaming_url;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getEvaluate_text() {
        return evaluate_text;
    }

    public void setEvaluate_text(String evaluate_text) {
        this.evaluate_text = evaluate_text;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public void setType_open(String type_open) {
        this.type_open = type_open;
    }

    public void setLink_share(String link_share) {
        this.link_share = link_share;
    }

    public String getIs_trend() {
        return is_trend;
    }

    public void setIs_trend(String is_trend) {
        this.is_trend = is_trend;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRegion_id() {
        return region_id;
    }

    public String getType_open() {
        return type_open;
    }

    public String getLink_share() {
        return link_share;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public String getOpen_week() {
        return open_week;
    }

    public String getPrice_from() {
        return price_from;
    }

    public void setPrice_from(String price_from) {
        this.price_from = price_from;
    }

    public String getPrice_to() {
        return price_to;
    }

    public void setPrice_to(String price_to) {
        this.price_to = price_to;
    }

    public void setOpen_week(String open_week) {
        this.open_week = open_week;
    }

    public String getRange_time() {
        return range_time;
    }

    public void setRange_time(String range_time) {
        this.range_time = range_time;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl_alias() {
        return url_alias;
    }

    public void setUrl_alias(String url_alias) {
        this.url_alias = url_alias;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDetail_link() {
        return detail_link;
    }

    public void setDetail_link(String detail_link) {
        this.detail_link = detail_link;
    }

    public String getDetail_linkV2() {
        return detail_linkV2;
    }

    public void setDetail_linkV2(String detail_linkV2) {
        this.detail_linkV2 = detail_linkV2;
    }

    public String getTime_ago() {
        return time_ago;
    }

    public void setTime_ago(String time_ago) {
        this.time_ago = time_ago;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getStandard_rate() {
        return standard_rate;
    }

    public void setStandard_rate(String standard_rate) {
        this.standard_rate = standard_rate;
    }

    public String getUrl_icon() {
        return url_icon;
    }

    public void setUrl_icon(String url_icon) {
        this.url_icon = url_icon;
    }

    public int getTypeItem() {
        return typeItem;
    }

    public void setTypeItem(int typeItem) {
        this.typeItem = typeItem;
    }

    public static class Collection implements Serializable {
        @Expose
        @SerializedName("link")
        private String link;
        @Expose
        @SerializedName("content_type")
        private String content_type;
        @Expose
        @SerializedName("name")
        private String name;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getContent_type() {
            return content_type;
        }

        public void setContent_type(String content_type) {
            this.content_type = content_type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
