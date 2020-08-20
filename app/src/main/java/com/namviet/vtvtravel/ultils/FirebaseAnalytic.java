package com.namviet.vtvtravel.ultils;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.namviet.vtvtravel.config.Constants;

public class FirebaseAnalytic {
    private static FirebaseAnalytics mFirebaseAnalytics;

    public static final String SEARCH = "search";//when a user searches in the app
    public static final String VIEW_ITEM = "view_item";//when a user views the details of an item
    public static final String VIEW_ITEM_LIST = "view_item_list";//when a user sees a list of items/offerings
    public static final String VIEW_SEARCH_RESULTS = "view_search_results";//when a user sees search results
    public static final String CLICK_MENU = "click_menu";//Click menu
    public static final String CLICK_MENU_REGISTER = "click_menu_register";//Click đăng ký
    public static final String CLICK_MENU_LOGIN = "click_menu_login";//Click đăng nhập
    public static final String CLICK_MENU_PROFILE = "click_menu_profile";//Click profile
    public static final String CLICK_MENU_SEARCH = "click_menu_search";//Click tìm kiếm
    public static final String CLICK_MENU_DESTINATION = "click_menu_destination";//Click đi đâu
    public static final String CLICK_MENU_HOTEL = "click_menu_hotel";//Click ở đâu
    public static final String CLICK_MENU_EVENT = "click_menu_event";//Click chơi gì
    public static final String CLICK_MENU_RESTAURANT = "click_menu_restaurant";//Click ăn gì
    public static final String CLICK_MENU_VIDEO = "click_menu_video";//Click video
    public static final String CLICK_MENU_VIDEO_PLAY = "click_menu_video_play";//Click xem video chi tiết
    public static final String CLICK_MENU_TV = "click_menu_tv";//Click truyền hình
    public static final String CLICK_MENU_TV_PLAY = "click_menu_tv_play";//Click xen truyền hình chi tiết
    public static final String CLICK_MENU_ALBUM = "click_menu_album";//Click góc ảnh
    public static final String CLICK_MENU_NOTEBOOK = "click_menu_notebook";//Click sổ tay du lịch
    public static final String CLICK_MENU_DEAL = "click_menu_deal";//Click cơ hội du lịch
    public static final String CLICK_MENU_SUGGEST = "click_menu_suggest";//Click gợi ý du lịch
    public static final String CLICK_MENU_MOMENT = "click_menu_moment";//Click khoảnh khắc
    public static final String CLICK_MENU_SUBCRIPTION = "click_menu_subcription";//Click đăng ký gói cước
    public static final String CLICK_MENU_TD1039 = "click_menu_td1039";//Click Hỗ trợ ( td1039 )
    public static final String CLICK_MENU_SETTING = "click_menu_setting";//Click Cài đặt
    public static final String CLICK_HOMEPAGE = "click_homepage";//Click Nổi bật (homepage)
    public static final String CLICK_HP_SEARCH = "click_hp_search";//Click tìm kiếm
    public static final String CLICK_HP_NAVIGATION_DESTINATION = "click_hp_navigation_destination";//Click đi đâu (top navigation)
    public static final String CLICK_HP_NAVIGATION_HOTEL = "click_hp_navigation_hotel";//Click ở đâu (top navigation)
    public static final String CLICK_HP_NAVIGATION_EVENT = "click_hp_navigation_event";//Click chơi gì (top navigation)
    public static final String CLICK_HP_NAVIGATION_RESTAURANT = "click_hp_navigation_restaurant";//Click ăn gì (top navigation)
    public static final String CLICK_HP_HEADER_DESTINATION = "click_hp_header_destination";//Click đi đâu (header)
    public static final String CLICK_HP_HEADER_HOTEL = "click_hp_header_hotel";//Click ở đâu (header)
    public static final String CLICK_HP_HEADER_EVENT = "click_hp_header_event";//Click chơi gì (header)
    public static final String CLICK_HP_HEADER_RESTAURANT = "click_hp_header_restaurant";//Click ăn gì (header)
    public static final String SWIPE_HP_LEFT_FEATURE = "swipe_hp_left_feature";//Vuốt trái Topic nổi bật
    public static final String SWIPE_HP_RIGHT_FEATURE = "swipe_hp_right_feature";//Vuốt phải Topic nổi bật
    public static final String CLICK_HP_FEATURE = "click_hp_feature";//Click Nổi bật
    public static final String CLICK_HP_VIDEO = "click_hp_video";//Click video
    public static final String SWIPE_HP_LEFT_VIDEO = "swipe_hp_left_video";//Vuốt trái Video
    public static final String SWIPE_HP_RIGHT_VIDEO = "swipe_hp_right_video";//Vuốt phải Video
    public static final String CLICK_HP_NOTE_BOOK = "click_hp_notebook";//Click sổ tay du lịch
    public static final String CLICK_HP_DEAL = "click_hp_deal";//Click cơ hội du lịch
    public static final String CLICK_HP_BOTTOM_DESTINATION = "click_hp_bottom_destination";//Click đi đâu (bottom)
    public static final String SWIPE_HP_LEFT_DESTINATION = "swipe_hp_left_destination";//Vuốt trái Đi đâu
    public static final String SWIPE_HP_RIGHT_DESTINATION = "swipe_hp_right_destination";//Vuốt phải Đi đâu
    public static final String CLICK_HP_BOTTOM_EVENT = "click_hp_bottom_event";//Click chơi gì (bottom)
    public static final String SWIPE_HP_LEFT_EVENT = "swipe_hp_left_event";//Vuốt trái Chơi gì
    public static final String SWIPE_HP_RIGHT_EVENT = "swipe_hp_right_event";//Vuốt phải Chơi gì
    public static final String CLICK_HP_BOTTOM_RESTAURANT = "click_hp_bottom_restaurant";//Click ăn gì (bottom)
    public static final String SWIPE_HP_LEFT_RESTAURANT = "swipe_hp_left_restaurant";//Vuốt trái Ăn gì
    public static final String SWIPE_HP_RIGHT_RESTAURANT = "swipe_hp_right_restaurant";//Vuốt phải Ăn gì
    public static final String CLICK_HP_BOTTOM_HOTEL = "click_hp_bottom_hotel";//Click ở đâu (bottom)
    public static final String SWIPE_HP_LEFT_HOTEL = "swipe_hp_left_hotel";//Vuốt trái Ở đâu
    public static final String SWIPE_HP_RIGHT_HOTEL = "swipe_hp_right_hotel";//Vuốt phải Ở đâu
    public static final String CLICK_HP_ALBUM = "click_hp_album";//Click góc ảnh
    public static final String SWIPE_HP_LEFT_ALBUM = "swipe_hp_left_album";//Vuốt trái góc ảnh
    public static final String SWIPE_HP_RIGHT_ALBUM = "swipe_hp_right_album";//Vuốt phải góc ảnh
    public static final String CLICK_HP_TD1039 = "click_hp_td1039";//Click Hỗ trợ ( td1039 )
    public static final String CLICK_HP_LIVECHAT = "click_hp_livechat";//Click livechat
    public static final String CLICK_HP_LIVECHAT_SEND = "click_hp_livechat_send";//Click live chat > send
    public static final String HP_SCROLL10 = "hp_scroll10";//Cuộn trang 10%
    public static final String HP_SCROLL50 = "hp_scroll50";//Cuộn trang 50%
    public static final String HP_SCROLL90 = "hp_scroll90";//Cuộn trang 90%
    public static final String CLICK_HP_SUGGEST = "click_hp_suggest";//Click gợi ý du lịch
    public static final String CLICK_HP_MOMENT = "click_hp_moment";//Click khoảnh khắc
    //    public static final String CLICK_HP_VIDEO = "click_hp_video";//Click video
    public static final String CLICK_HP_VIDEO_PLAY = "click_hp_video_play";//Click xem video chi tiết
    public static final String CALL_1039 = "call_1039";//Gọi tổng đài 1039
    public static final String MESSENGER_SEND = "messenger_send";//Tin nhắn được gửi
    public static final String API_ERROR = "api_error";//Call API ERROR
    public static final String ERROR_MESSAGE = "error_message";//Call API ERROR
}
