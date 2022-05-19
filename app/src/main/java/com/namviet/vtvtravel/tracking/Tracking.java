package com.namviet.vtvtravel.tracking;


import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.webkit.WebView;

import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.MyLocation;

public class Tracking {
    private String screen_class;
    private String partner_banner_ad_id ;
    private String screen_title;
    private String screen_code_referrer;
    private String screen_resolution;
    private String user_id;
    private String user_mobile;
    private String user_lat;
    private String user_lng;
    private String request_id;
    private String channel;
    private String platform;
    private String origin_platform;
    private String user_agent;
    private String client_ip;
    private String content_lat;
    private String content_lng;
    private String content_type;
    private String content_id;
    private String created;
    private String screen_code;
    private String package_code;
    private String package_price;
    private String package_origin_price;
    private String package_cycle;
    private String deal_code;
    private String deal_campaign_code;
    private String deal_price;
    private String deal_lat;
    private String deal_lng;
    private String payment_method;
    private String region_code;
    private String user_rank_code;
    private String promotion_category_id;
    private String voucher_id;
    private String voucher_region_code;
    private String voucher_category_id;
    private String term;
    private String message;
    private String comment;
    private String rating;
    private String method;
    private String status;
    private String payment_status;
    private String payment_code;
    private String scroll_depth_threshold;
    private String scroll_depth_units;
    private String scroll_direction;
    private String category_tree_code;
    private String category_tree_name;

    public static Tracking Builder() {
        return new Tracking();
    }

    public Tracking setScreen_class(String screen_class) {
        this.screen_class = screen_class;
        return this;
    }

    public Tracking setScreen_title(String screen_title) {
        this.screen_title = screen_title;
        return this;
    }

    public Tracking setScreen_code_referrer(String screen_code_referrer) {
        this.screen_code_referrer = screen_code_referrer;
        return this;
    }

    public Tracking setScreen_resolution(String screen_resolution) {
        this.screen_resolution = screen_resolution;
        return this;
    }

    public Tracking setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public Tracking setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
        return this;
    }

    public Tracking setUser_lat(String user_lat) {
        this.user_lat = user_lat;
        return this;
    }

    public Tracking setUser_lng(String user_lng) {
        this.user_lng = user_lng;
        return this;
    }

    public Tracking setRequest_id(String request_id) {
        this.request_id = request_id;
        return this;
    }

    public Tracking setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public Tracking setPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    public Tracking setOrigin_platform(String origin_platform) {
        this.origin_platform = origin_platform;
        return this;
    }

    public Tracking setUser_agent(String user_agent) {
        this.user_agent = user_agent;
        return this;
    }

    public Tracking setClient_ip(String client_ip) {
        this.client_ip = client_ip;
        return this;
    }

    public Tracking setContent_lat(String content_lat) {
        this.content_lat = content_lat;
        return this;
    }

    public Tracking setContent_lng(String content_lng) {
        this.content_lng = content_lng;
        return this;
    }

    public Tracking setContent_type(String content_type) {
        this.content_type = content_type;
        return this;
    }

    public Tracking setContent_id(String content_id) {
        this.content_id = content_id;
        return this;
    }

    public Tracking setCreated(String created) {
        this.created = created;
        return this;
    }

    public Tracking setScreen_code(String screen_code) {
        this.screen_code = screen_code;
        return this;
    }

    public Tracking setPackage_code(String package_code) {
        this.package_code = package_code;
        return this;
    }

    public Tracking setPackage_price(String package_price) {
        this.package_price = package_price;
        return this;
    }

    public Tracking setPackage_origin_price(String package_origin_price) {
        this.package_origin_price = package_origin_price;
        return this;
    }

    public Tracking setPackage_cycle(String package_cycle) {
        this.package_cycle = package_cycle;
        return this;
    }

    public Tracking setDeal_code(String deal_code) {
        this.deal_code = deal_code;
        return this;
    }

    public Tracking setDeal_campaign_code(String deal_campaign_code) {
        this.deal_campaign_code = deal_campaign_code;
        return this;
    }

    public Tracking setDeal_price(String deal_price) {
        this.deal_price = deal_price;
        return this;
    }

    public Tracking setDeal_lat(String deal_lat) {
        this.deal_lat = deal_lat;
        return this;
    }

    public Tracking setDeal_lng(String deal_lng) {
        this.deal_lng = deal_lng;
        return this;
    }

    public Tracking setPayment_method(String payment_method) {
        this.payment_method = payment_method;
        return this;
    }

    public Tracking setRegion_code(String region_code) {
        this.region_code = region_code;
        return this;
    }

    public Tracking setUser_rank_code(String user_rank_code) {
        this.user_rank_code = user_rank_code;
        return this;
    }

    public Tracking setPromotion_category_id(String promotion_category_id) {
        this.promotion_category_id = promotion_category_id;
        return this;
    }

    public Tracking setVoucher_id(String voucher_id) {
        this.voucher_id = voucher_id;
        return this;
    }

    public Tracking setVoucher_region_code(String voucher_region_code) {
        this.voucher_region_code = voucher_region_code;
        return this;
    }

    public Tracking setVoucher_category_id(String voucher_category_id) {
        this.voucher_category_id = voucher_category_id;
        return this;
    }

    public Tracking setTerm(String term) {
        this.term = term;
        return this;
    }

    public Tracking setMessage(String message) {
        this.message = message;
        return this;
    }

    public Tracking setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Tracking setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public Tracking setMethod(String method) {
        this.method = method;
        return this;
    }

    public Tracking setStatus(String status) {
        this.status = status;
        return this;
    }

    public Tracking setPayment_status(String payment_status) {
        this.payment_status = payment_status;
        return this;
    }

    public Tracking setPayment_code(String payment_code) {
        this.payment_code = payment_code;
        return this;
    }

    public Tracking setScroll_depth_threshold(String scroll_depth_threshold) {
        this.scroll_depth_threshold = scroll_depth_threshold;
        return this;
    }

    public Tracking setScroll_depth_units(String scroll_depth_units) {
        this.scroll_depth_units = scroll_depth_units;
        return this;
    }

    public Tracking setScroll_direction(String scroll_direction) {
        this.scroll_direction = scroll_direction;
        return this;
    }

    public Tracking setCategory_tree_code(String category_tree_code) {
        this.category_tree_code = category_tree_code;
        return this;
    }

    public Tracking setCategory_tree_name(String category_tree_name) {
        this.category_tree_name = category_tree_name;
        return this;
    }

    public Tracking setPartner_banner_ad_id(String partner_banner_ad_id) {
        this.partner_banner_ad_id = partner_banner_ad_id;
        return this;
    }

    public Tracking build() {
        Tracking tracking = new Tracking();
        tracking.platform = this.platform;
        tracking.deal_code = this.deal_code;
        tracking.rating = this.rating;
        tracking.deal_lat = this.deal_lat;
        tracking.request_id = this.request_id;
        tracking.user_lat = this.user_lat;
        tracking.deal_campaign_code = this.deal_campaign_code;
        tracking.package_origin_price = this.package_origin_price;
        tracking.promotion_category_id = this.promotion_category_id;
        tracking.scroll_depth_units = this.scroll_depth_units;
        tracking.message = this.message;
        tracking.screen_code = this.screen_code;
        tracking.screen_resolution = this.screen_resolution;
        tracking.scroll_direction = this.scroll_direction;
        tracking.term = this.term;
        tracking.method = this.method;
        tracking.scroll_depth_threshold = this.scroll_depth_threshold;
        tracking.screen_code_referrer = this.screen_code_referrer;
        tracking.origin_platform = this.origin_platform;
        tracking.status = this.status;
        tracking.channel = this.channel;
        tracking.content_lng = this.content_lng;
        tracking.user_rank_code = this.user_rank_code;
        tracking.voucher_id = this.voucher_id;
        tracking.voucher_region_code = this.voucher_region_code;
        tracking.region_code = this.region_code;
        tracking.deal_price = this.deal_price;
        tracking.user_lng = this.user_lng;
        tracking.created = this.created;
        tracking.content_type = this.content_type;
        tracking.screen_class = this.screen_class;
        tracking.payment_method = this.payment_method;
        tracking.package_code = this.package_code;
        tracking.package_cycle = this.package_cycle;
        tracking.package_price = this.package_price;
        tracking.user_id = this.user_id;
        tracking.comment = this.comment;
        tracking.payment_status = this.payment_status;
        tracking.payment_code = this.payment_code;
        tracking.deal_lng = this.deal_lng;
        tracking.content_lat = this.content_lat;
        tracking.content_id = this.content_id;
        tracking.user_mobile = this.user_mobile;
        tracking.voucher_category_id = this.voucher_category_id;
        tracking.screen_title = this.screen_title;
        tracking.client_ip = this.client_ip;
        tracking.user_agent = this.user_agent;
        tracking.category_tree_code = this.category_tree_code;
        tracking.category_tree_name = this.category_tree_name;
        tracking.partner_banner_ad_id = this.partner_banner_ad_id;
        return tracking;
    }

}
