package com.namviet.vtvtravel.tracking;

import android.webkit.WebView;

import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.MyLocation;

public class TrackingAnalytic {
    public static final String SCREEN_VIEW = "screen_view";
    public static final String SCREEN_EXIT = "screen_exit";
    public static final String SCREEN_SCROLL = "screen_scroll";
    public static final String SIGN_UP = "sign_up";
    public static final String SIGN_IN = "sign_in";
    public static final String SIGN_OUT = "sign_out";
    public static final String CLICK_PACKAGE_REG = "click_package_reg";
    public static final String CLICK_PACKAGE_CANCEL = "click_package_cancel";
    public static final String CLICK_DEAL_HUNT = "click_deal_hunt";
    public static final String CLICK_MY_PROMOTION = "click_my_promotion";
    public static final String FILTER_PROMOTION = "filter_promotion";
    public static final String COPY_PROMOTION_CODE = "copy_promotion_code";
    public static final String CLICK_RECEIVE_PROMOTION = "click_receive_promotion";
    public static final String CHOOSE_PROMOTION = "choose_promotion";
    public static final String CLICK_SPIN_WHEEL = "click_spin_wheel";
    public static final String SEARCH = "search";
    public static final String CHAT = "chat";
    public static final String LIKE = "like";
    public static final String SHARE = "share";
    public static final String COMMENT = "comment";
    public static final String REVIEW = "review";
    public static final String PACKAGE_REQUEST = "package_request";
    public static final String PACKAGE_SUBSCRIBE_SUCCESS = "package_subscribe_success";
    public static final String PACKAGE_SUBSCRIBE_NOT_ENOUGH_CREDIT = "package_subscribe_not_enough_credit";
    public static final String PACKAGE_SUBSCRIBE_FAIL = "package_subscribe_fail";
    public static final String PACKAGE_CANCEL = "package_cancel";
    public static final String PACKAGE_CANCEL_INVALID = "package_cancel_invalid";
    public static final String PACKAGE_REQUEST_INVALID = "package_request_invalid";
    public static final String DEAL_HUNT_SUCCESS = "deal_hunt_success";
    public static final String DEAL_HUNT_NOT_ENOUGH_CREDIT = "deal_hunt_not_enough_credit";
    public static final String DEAL_HUNT_INVALID = "deal_hunt_invalid";
    public static final String DEAL_HUNT_FAIL = "deal_hunt_fail";

    public static Tracking getDefault() {
        try {
            Tracking trackingBuilder = Tracking.Builder()
                    .setUser_id(getUser_id())
                    .setUser_mobile(getUser_mobile())
                    .setUser_lat(getUser_lat())
                    .setUser_lng(getUser_lng())
                    .setChannel(getChannel())
                    .setPlatform(getPlatform())
                    .setOrigin_platform(getOrigin_platform())
                    .setUser_agent(getUser_agent())
                    .build();
//            TrackingViewModel.getInstance().trackEvent2(eventName, trackingBuilder);

            return trackingBuilder;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void postEvent(String eventName, Tracking tracking) {
        TrackingViewModel.getInstance().trackEvent2(eventName, tracking);
    }

    private static String getUser_id() {
        try {
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin()) {
                return account.getId() + "";
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getUser_mobile() {
        try {
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin()) {
                return account.getMobile();
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getUser_lat() {
        try {
            MyLocation myLocation = MyApplication.getInstance().getMyLocation();
            if (null != myLocation) {
                return myLocation.getLat() + "";
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getUser_lng() {
        try {
            MyLocation myLocation = MyApplication.getInstance().getMyLocation();
            if (null != myLocation) {
                return myLocation.getLog() + "";
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getChannel() {
        return "ANDROID";
    }

    private static String getPlatform() {
        return "VTVTRAVEL";
    }

    private static String getOrigin_platform() {
        return "VTVTRAVEL";
    }

    private static String getUser_agent() {
        try {
            return new WebView(MyApplication.getInstance().getApplicationContext()).getSettings().getUserAgentString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
