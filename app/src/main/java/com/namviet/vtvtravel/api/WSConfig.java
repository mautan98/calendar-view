package com.namviet.vtvtravel.api;

public class WSConfig {

    //Server test
    public static final String HOST = "https://api1.travel.onex.vn/";
    public static final String HOST_ACC = "https://core1.travel.onex.vn/api/v1/";

    //Server product
//    public static final String HOST = "https://api.travel.onex.vn/";
//    public static final String HOST_ACC = "https://core.travel.onex.vn/api/v1/";
    public static final String HOST_CHAT = "https://103.21.148.54:8090/";

    public static final String URL_ENCODE = "http://api.travel.onex.vn/test/rsa/decrypt";
    public static final String URL_DEFAULT_CHANNEL = "http://113.185.19.133:8443/namvietvtv/smil:vtv1.smil/playlist.m3u8";
    public static final String URL_SENTRY_DSN = "http://f3efc727c26d4d7aae5b4cf14fae9ebd@103.21.148.54:9099/5";

    public static final String ENDPOINT_NAME = "SMPPGATEWAY";
    public static final String ENDPOINT_KEY = "NamVietVtvTr@vel2018";
    public static final String REQUEST_ID = "b5ba7da69cff242ba55bab729a098768";

    public static final String GET_HOME = "home";
    public static final String GET_MENU = "setting";
    public static final String GET_SEARCH = "search";
    public static final String GET_NEAR = "nearby";

    public static class Api {
        public static final String LOGIN = "user/login";
        public static final String REGISTER = "user/create";
        public static final String RESENT_OTP = "user/resendOtp";
        public static final String GET_INFO = "user/getInfo";
        public static final String REGISTER_OTP = "user/verifyRegisterOtp";
        public static final String RESET_PASS_OTP = "user/verifyResetPasswordOtp";
        public static final String RESET_PASS = "user/resetPassword";
        public static final String REGISTER_ACCOUNT = "user/register";
        public static final String UPDATE = "user/update";
        public static final String UPDATE_AVATAR = "user/updateImageProfile";
        public static final String RESET_PASSWORD = "user/getPasswordOtp";
        public static final String CHANGE_PASSWORD = "user/changePassword";
        public static final String GET_CATEGORY_NEWS = "news";
        public static final String GET_CATEGORY_NEWS_SLIDE_SHOWS = "news/slideshow";
        public static final String GET_NEWS_BY_ID = "client/news/getById";
        public static final String SELECT_CITY = "regions/select";
        public static final String SELECT_FILTER = "filters";
        public static final String GET_PLACE = "places";
        public static final String GET_RESTAURANTS = "restaurants";
        public static final String GET_HOTELS = "hotels";
        public static final String GET_CENTER = "centers";
        public static final String GET_VOUCHER = "vouchers";
        public static final String SELECT_CATEGORY_HIGHLIGHT = "restaurants/categories/highlight";
        public static final String GET_CATEGORY_PHOTO_NICE = "galleries/categories";
        public static final String GET_CATEGORY_VIDEO = "videos/categories";
        public static final String GET_SLIDE_SHOW = "galleries/slideshow";
        public static final String GET_MOMENT = "moments";
        public static final String GET_MOMENT_NEWSEST = "moments/newest";
        public static final String GET_MOMENT_VIDEO = "moments/videos";
        public static final String GET_TOUR_SUGGEST = "client/tour/getByRegionId";
        public static final String GET_TOUR_SCHEDULE_DETAIL = "client/tour/getById";
        public static final String GET_SCHEDULE_CREATE_DETAIL = "client/user/wishList/getById";
        //        public static final String GET_TOUR_SCHEDULE_PLACES = "client/places/getNearByLocation";
        public static final String GET_TOUR_SCHEDULE_PLACES = "client/hotels/getNearByLocation";
        public static final String GET_TOUR_SCHEDULE_CENTERS = "client/center/getNearByLocation";
        public static final String GET_TOUR_SCHEDULE_RESTAURANTS = "client/restaurants/getNearByLocation";
        public static final String SAVE_SCHEDULE_CENTERS = "client/user/wishList/create";
        public static final String SAVE_TOUR = "client/user/tour/create";
        public static final String GET_SEARCH_TREND = "search/trends";
        //        public static final String GET_SEARCH_SUGGEST = "search/suggestions";
        public static final String GET_SEARCH_SUGGEST = "v2/search/suggestions";
        public static final String GET_SEARCH_RESULT = "search/result";
        public static final String SEARCH_SCHEDULE_CREATE = "client/user/wishList/search";
        public static final String SEARCH_TOUR_CREATE = "client/user/tour/search";
        public static final String NEARBY_FILTERS = "nearby/filters";
        public static final String COMMENTS_SEARCH = "comments/search";
        public static final String COMMENTS_CREATE = "comments/create";
        public static final String COMMENTS_DELETE = "comments/delete";
        public static final String COMMENTS_UPDATE = "comments/update";
        public static final String NOTIFICATION_REG = "notifications/reg";
        public static final String NOTIFICATION_LIST = "notifications";
        public static final String NOTIFICATION_COUNTUNREAD = "notifications/countUnread";
        public static final String WEATHER = "notifications/categories";
        public static final String MOMENTS_TYPES = "moments/types";
        public static final String SHARE_MOMENT = "moments/share";
        public static final String YOUR_MOMENT = "moments/yours";
        public static final String TRACK_LOCATION = "tracking";
        public static final String GET_LIVE_CHANNEL = "tv/channels";
        public static final String GET_DETAIL_LIVE_CHANNEL = "tv/broadcast";
        public static final String GET_DETAIL_LIVE_CHANNEL_FAKE = "tv/schedule/";
        public static final String GET_LICENSE_PHOTO = "pages/";
        public static final String GET_CHAT_BOT_QUESTION = "chatbot/question/";
        public static final String GET_HELLO_LOCATION = "regions/location";
        public static final String CREATE_ROOM = "chat-api/sys/v1/room/create";
        public static final String SEND_FORM_CHAT = "chatbot/insertInfomation";
        public static final String GET_CALL_HISTORY = "user/callHistories";
        public static final String DELETE_CALL_HISTORY = "user/deleteHistories";
        public static final String INVITE_CALL_NOW = "user/invite";
        public static final String CALL_LIST = "user/callList";
        public static final String VERIFY_USER = "user/verifyUser";
        public static final String ZIP_VERSION = "config-callNow";
        public static final String HOME_SERVICE = "V3/home";
        public static final String GET_MOBILE_FROM_VIETTEL = "userMetadata/mobile";
        public static final String GET_SETTING = "V3/setting";
        public static final String GET_NEW_CATEGORY = "news/getNewsCategory";
        public static final String GET_GALLERY_ID = "galleries/listById";
        public static final String GET_GALLERY = "galleries";
        public static final String GET_NOTEBOOK = "news/noteBook";
        public static final String GET_SMALL_LOCATION = "smallVenue/category";
        public static final String FILTER_BY_CODE = "filterByCode";
        public static final String GET_VIDEO_BY_TAG = "videos/byTag";
        public static final String CREATE_REVIEW = "comments/create";
        public static final String GET_REVIEW = "comments/review/search";
        public static final String UPLOAD_IMAGE = "userMetadata/uploadImage";
        public static final String SORT_SMALL_LOCATION = "sort";
        public static final String GET_BIG_LOCATION = "region/Info";
        public static final String LOCATION = "regions/location";
        public static final String TOP_LOCATION = "regions/top";
        public static final String ALL_LOCATION = "regions/select";
        public static final String POST_EVENT = "user/event";
        public static final String USER_GUILD = "user/guide";

        public static final String GET_SERVICE = "userPackage/package";
        public static final String REQUEST_SERVICE_OTP = "userPackage/register/request";
        public static final String CONFIRM_SERVICE_OTP = "userPackage/register/confirm";
        public static final String RESENT_SERVICE_OTP = "userPackage/register/resendOtp";
        public static final String GET_OWNED_VOUCHER_STORE = "user/voucher/suggest";
        public static final String GET_OWNED_VOUCHER = "user/voucher";
        public static final String GET_CATEGORY_VOUCHER = "user/voucher/category";
        public static final String GET_REGION_VOUCHER = "user/voucher/region";
        public static final String GET_RANK = "user/rank";

        public static final String HASH_TAG_SEARCH = "deals/getDealFilter";
        public static final String DEAL_HOT_HOME = "deals/getDealHot";
        public static final String TERMS = "page/dieu-khoan-su-dung";
    }

    public class KeyParam {
        public static final String HEADER_TOKEN = "NVTRAVEL-TOKEN";
        public static final String LANG_CODE = "lang_code";
        public static final String LANG_CODE2 = "langCode";
        public static final String DATA = "data";
        public static final String SERVICE_ENDPOINT_NAME = "serviceEndpointName";
        public static final String REQUEST_ID = "requestId";
        public static final String CHECK_SUM = "checksum";
        public static final String KEY_WORRD = "keyword";
        public static final String PAGE = "page";
        public static final String LIMIT = "limit";
        public static final String TYPE = "type";
        public static final String DISTANCE = "distance";
        public static final String LAT = "lat";
        public static final String LOG = "long";
        public static final String CONTENT = "content";
        public static final String CONTENT_ID = "contentId";
        public static final String CONTENT_TYPE = "contentType";
        public static final String PARENT_ID = "parentId";
        public static final String SHORT_CODE = "shortCode";
        public static final String PASSWORD = "password";
        public static final String OLD_PASSWORD = "oldPassword";
        public static final String MOBILE = "mobile";
        public static final String GOOGLE_ID = "googleId";
        public static final String FACEBOOK_ID = "facebookId";
        public static final String FULLNAME = "fullname";
        public static final String EMAIL = "email";
        public static final String OTP = "otp";
        public static final String ID = "id";
        public static final String USER_ID = "userId";
        public static final String TOUR_ID = "tourId";
        public static final String BIRTHDAY = "birthday";
        public static final String GENDER = "gender";
        public static final String ABOUT = "about";
        public static final String ICPP_NUMBER = "icppNumber";
        public static final String ADDRESS = "address";
        public static final String CATEGORY_CODE = "category_code";
        public static final String CATEGORY_ID = "categoryId";
        public static final String PROFILE = "profile";
        public static final String REGION_ID = "region_id";
        public static final String REGION_ID2 = "regionId";
        public static final String REGIONS = "regions";
        public static final String SIZE = "size";
        public static final String LAT_LOCATION = "latLocation";
        public static final String LOG_LOCATION = "longLocation";
        public static final String BEGIN_TOUR = "begin";
        public static final String BEGIN_START_TOUR = "beginStart";
        public static final String END_TOUR = "end";
        public static final String END_START_TOUR = "endStart";
        public static final String PLATFORM = "platform";
        public static final String DEVICE_TOKEN = "device_token";
        public static final String DEVICE_ID = "device_id";
        // public static final String SEND_NOTIFY = "sendNotify";
        public static final String SEND_NOTIFY = "sendNotification";
        public static final String NAME = "name";
        public static final String SHORT_DESCRIPTION = "short_description";
        public static final String PHOTO_NAMES = "photo_names[]";
        public static final String PHOTO_FILES = "photo_files[]";
        public static final String DESCRIPTION = "description";
        public static final String TAGS = "tags[]";
        public static final String CHANEL = "channel";
        public static final String SESSION_ID = "sessionId";
        public static final String MESSAGE = "message";
        public static final String CHANNEL = "channel";
        public static final String ANDROID = "ANDROID";
        public static final String TOKEN = "token";
        public static final String USERNAME = "username";
        public static final String UUID = "uuId";
        public static final String TIME_CONTACT = "time_contact";
        public static final String START_TIME = "start_time";
        public static final String END_TIME = "end_time";
        public static final String IDS = "ids";
        public static final String CODE = "code";
        public static final String PATH = "path";
        public static final String MOBILES = "mobiles";
        public static final String PACKAGE_CODE = "packageCode";
        public static final String GALLERY_ID = "gallery_id";
        public static final String TAG = "tag";
        public static final String POST_RATE = "postRate";
        public static final String GALLERY_URIS = "galleryUris";
        public static final String SERVICE = "service";
        public static final String MEMBER_RANK_ID = "memberRankId";
        public static final String SORT = "sort";

        public static final String USER_AGENT = "userAgent";
    }

    public class KeySocket {
        public static final String ROOM_ID = "room_id";
        public static final String CONTENT = "content";
        public static final String CLIENT_MESSAGE_ID = "client_message_id";
    }
}
