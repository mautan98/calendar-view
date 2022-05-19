package com.namviet.vtvtravel.tracking;

import android.webkit.WebView;

import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.MyLocation;

public class TrackingAnalytic {
    public static final String SCREEN_VIEW = "screen_view";
    public static final String CLICK_PARTNER_BANNER_AD = "click_partner_banner_ad";
    public static final String VIEW_PARTNER_BANNER_AD = "view_partner_banner_ad";
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
    public static final String CATEGORY_TREE_CODE = "category_tree_code";
    public static final String CATEGORY_TREE_NAME = "category_tree_name";


    public static class ScreenCode {
        public static final String HUNT_DEAL_CALL_NOW = "HUNT_DEAL_CALL_NOW";
        public static final String CALL_NOW = "CALL_NOW";
        public static final String CALL_CENTER = "CALL_CENTER";
        public static final String REGISTER_PACKAGE_SUCCESS = "REGISTER_PACKAGE_SUCCESS";
        public static final String SERVICE_PACKAGE = "SERVICE_PACKAGE";
        public static final String SMALL_LOCATIONS = "SMALL_LOCATIONS";
        public static final String TAG_VIDEO = "TAG_VIDEO";
        public static final String TAB_VIDEO = "TAB_VIDEO";
        public static final String HIGH_LIGHT_SEE_MORE_VIDEO = "HIGH_LIGHT_SEE_MORE_VIDEO";
        public static final String SLIDE_IMAGES = "SLIDE_IMAGES";
        public static final String NEARBY_EXPERIENCE = "NEARBY_EXPERIENCE";

        public static final String SMALL_LOCATION_DETAIL = "SMALL_LOCATION_DETAIL";
        public static final String HIGH_LIGHTEST_IMAGE = "HIGH_LIGHTEST_IMAGE";
        public static final String REVIEW = "REVIEW";
        public static final String HOME = "HOME";
        public static final String REGISTER = "REGISTER";
        public static final String SETTING = "SETTING";
        public static final String LOGIN = "LOGIN";
        public static final String SEARCH = "SEARCH";
        public static final String SEARCH_RESULT = "SEARCH_RESULT";
        public static final String BOOKING = "BOOKING";
        public static final String SMALL_LOCATION_SUGGEST = "SMALL_LOCATION_SUGGEST";
        public static final String SMALL_LOCATION_FILTER = "SMALL_LOCATION_FILTER";
        public static final String SMALL_LOCATION_REVIEW = "SMALL_LOCATION_REVIEW";
        public static final String VOUCHER_STORE = "VOUCHER_STORE";
        public static final String VOUCHER_DETAIL = "VOUCHER_DETAIL";
        public static final String MY_GIFT_STORE = "MY_GIFT_STORE";
        public static final String MY_GIFT = "MY_GIFT";
        public static final String RANK_USER = "RANK_USER";
        public static final String MENU = "MENU";
        public static final String LUCKY_WHEEL = "LUCKY_WHEEL";
        public static final String DESTINATIONS = "DESTINATIONS";
        public static final String TOP_CORNER_PHOTO = "TOP_CORNER_PHOTO";
        public static final String CORNER_PHOTO = "CORNER_PHOTO";
        public static final String NOTEBOOK_TRAVEL = "NOTEBOOK_TRAVEL";
        public static final String NEWS_TRAVEL = "NEWS_TRAVEL";
        public static final String LIVE_TV_DETAIL = "LIVE_TV_DETAIL";
        public static final String USER_INFO = "USER_INFO";
        public static final String CHANGE_PASSWORD = "CHANGE_PASSWORD";
        public static final String CHAT_BOT = "CHAT_BOT";
        public static final String COMMENT = "COMMENT";
        public static final String NOTIFICATION = "NOTIFICATION";
        public static final String VIDEOS = "VIDEOS";
        public static final String VIDEO_DETAIL = "VIDEO_DETAIL";
        public static final String NEWS_DETAIL = "NEWS_DETAIL";
        public static final String TOP_EXPERIENCE = "TOP_EXPERIENCE";
        public static final String DEAL_HOT = "DEAL_HOT";
////////
        public static final String DEAL_DETAIL = "DEAL_DETAIL";
        public static final String FORGET_PASS = "FORGET_PASS";
        public static final String LOGIN_AND_REGISTER = "LOGIN_AND_REGISTER";
        public static final String OTP = "OTP";
        public static final String RECREATE_PASS = "RECREATE_PASS";
        public static final String RULES = "RULES";
        public static final String BIG_LOCATION = "BIG_LOCATION";
        public static final String SEARCH_BIG_LOCATION = "SEARCH_BIG_LOCATION";
        public static final String ALL_CALL_HISTORY = "ALL_CALL_HISTORY";
        public static final String ALL_CONTACT = "ALL_CONTACT";
        public static final String CALL_NOW_CONTACT = "CALL_NOW_CONTACT";
        public static final String MISSING_CALL = "MISSING_CALL";
        public static final String SEARCH_NUMBER = "SEARCH_NUMBER";
        public static final String CALL_NOW_SETTING = "CALL_NOW_SETTING";
        public static final String VIEW_IMAGE_PROFILE = "VIEW_IMAGE_PROFILE";
        public static final String BASE_FILTER = "BASE_FILTER";
        public static final String FILTER = "FILTER";
        public static final String FILTER_TYPE = "FILTER_TYPE";
        public static final String INTRODUCTION = "INTRODUCTION";
    }

    public static class ScreenTitle {
        public static final String HUNT_DEAL_CALL_NOW = "SĂN DEAL TRONG CALL NOW";
        public static final String CALL_NOW = "CALL_NOW";
        public static final String CALL_CENTER = "TRUNG TÂM GỌI DỊCH VỤ CALL NOW";
        public static final String REGISTER_PACKAGE_SUCCESS = "ĐĂNG KÝ GÓI DỊCH VỤ THÀNH CÔNG";
        public static final String SERVICE_PACKAGE = "GÓI DỊCH VỤ";
        public static final String SMALL_LOCATIONS = "DANH SÁCH ĐỊA ĐIỂM NHỎ";
        public static final String TAG_VIDEO = "VIDEO THEO TAG";
        public static final String TAB_VIDEO = "TAB_VIDEO";
        public static final String HIGH_LIGHT_SEE_MORE_VIDEO = "XEM THÊM VIDEO NỔI BẬT NHẤT";
        public static final String SLIDE_IMAGES = "SLIDE_ẢNH";
        public static final String NEARBY_EXPERIENCE = "TRẢI NGHIỆM LÂN CẬN";

        public static final String SMALL_LOCATION_DETAIL = "CHI TIẾT ĐỊA ĐIỂM NHỎ";
        public static final String HIGH_LIGHTEST_IMAGE = "HIGH_LIGHTEST_IMAGE";
        public static final String REVIEW = "VIẾT ĐÁNH GIÁ";
        public static final String REGISTER = "ĐĂNG KÝ";
        public static final String SETTING = "CÀI ĐẶT";
        public static final String HOME = "TRANG CHỦ";
        public static final String LOGIN = "ĐĂNG NHẬP";
        public static final String SEARCH = "TÌM KIẾM";
        public static final String SEARCH_RESULT = "KẾT QUẢ TÌM KIẾM";
        public static final String BOOKING = "BOOKING";
        public static final String SMALL_LOCATION_SUGGEST = "GỢI Ý ĐỊA ĐIỂM NHỎ";
        public static final String SMALL_LOCATION_FILTER = "LỌC ĐỊA ĐIỂM NHỎ";
        public static final String SMALL_LOCATION_REVIEW = "ĐÁNH GIÁ ĐỊA ĐIỂM NHỎ";
        public static final String VOUCHER_STORE = "KHO KHUYẾN MÃI";
        public static final String VOUCHER_DEATAIL = "CHI TIẾT KHUYẾN MÃI";
        public static final String MY_GILF_STORE = "QUÀ CỦA TÔI";
        public static final String MY_GILF = "LỰA CHỌN QUÀ CỦA TÔI";
        public static final String RANK_USER = "HẠNG THÀNH VIÊN";
        public static final String MENU = "MENU";
        public static final String LUCKY_WHEEL = "VÒNG QUAY MAY MẮN";
        public static final String DESTINATIONS = "DANH SÁCH ĐIỂM ĐẾN";
        public static final String TOP_CORNER_PHOTO = "GÓC ẢNH NỔI BẬT";
        public static final String CORNER_PHOTO = "GÓC ẢNH";
        public static final String NOTEBOOK_TRAVEL = "SỔ TAY DU LỊCH";
        public static final String NEWS_TRAVEL = "TIN TỨC DU LỊCH";
        public static final String LIVE_TV_DETAIL = "KÊNH TRUYỀN HÌNH";
        public static final String USER_INFO = "THÔNG TIN TÀI KHOẢN";
        public static final String CHANGE_PASSWORD = "ĐỔI MẬT KHẨU";
        public static final String CHAT_BOT = "CHAT BOT";
        public static final String COMMENT = "COMMENT";
        public static final String NOTIFICATIONT = "THÔNG BÁO";
        public static final String VIDEOS = "DANH SÁCH VIDEO";
        public static final String VIDEO_DETAIL = "CHI TIẾT VIDEO";
        public static final String NEWS_DETAIL = "CHI TIẾT TIN TỨC DU LỊCH";
        public static final String TOP_EXPERICENCE = "TOP TRẢI NGHIỆM";
        public static final String DEAL_HOT = "DEAL HOT";
        public static final String DEAL_HUNT_FAIL = "deal_hunt_fail";
/////////
        public static final String DEAL_DETAIL = "CHI TIẾT ƯU ĐÃI";
        public static final String FORGET_PASS = "QUÊN MẬT KHẨU";
        public static final String LOGIN_AND_REGISTER = "ĐĂNG NHẬP VÀ ĐĂNG KÝ";
        public static final String OTP = "OTP";
        public static final String RECREATE_PASS = "TẠO LẠI MẬT KHẨU";
        public static final String RULES = "ĐIỀU KHOẢN";
        public static final String BIG_LOCATION = "ĐỊA ĐIỂM TO";
        public static final String SEARCH_BIG_LOCATION = "TÌM KIỂM ĐỊA ĐIỂM TO";
        public static final String ALL_CALL_HISTORY = "TẤT CẢ LỊCH SỬ CUỘC GỌI";
        public static final String ALL_CONTACT = "DANH BẠ";
        public static final String CALL_NOW_CONTACT = "DANH BẠ CALL NOW";
        public static final String MISSING_CALL = "CUỘC GỌI NHỠ";
        public static final String SEARCH_NUMBER = "TÌM KIẾM SỐ ĐIỆN THOẠI";
        public static final String CALL_NOW_SETTING = "CÀI ĐẶT CALL NOW";
        public static final String VIEW_IMAGE_PROFILE = "XEM ẢNH ĐẠI DIỆN";
        public static final String BASE_FILTER = "CƠ SỞ LỌC";
        public static final String FILTER = "LỌC ĐỊA ĐIỂM NHỎ";
        public static final String FILTER_TYPE = "KIỂU LỌC";
        public static final String INTRODUCTION = "GIỚI THIỆU";
        public static final String TOP_EXPERIENCE = "TOP TRẢI NGHIỆM";
    }


    public static Tracking getDefault(String screenCode, String screenTile) {
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
                    .setScreen_code(screenCode)
                    .setScreen_title(screenTile)
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
        return "VTVTRAVEL APP ANDROID";
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
