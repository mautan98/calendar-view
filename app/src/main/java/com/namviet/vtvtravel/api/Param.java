package com.namviet.vtvtravel.api;


import android.util.Log;

import com.baseapp.utils.StringUtils;
import com.google.gson.Gson;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.City;
import com.namviet.vtvtravel.model.CustomGallery;
import com.namviet.vtvtravel.model.GroupSchedule;
import com.namviet.vtvtravel.response.FilterData;
import com.namviet.vtvtravel.tracking.Tracking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Param {
    public static JSONObject getParams(JSONObject param) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.SERVICE_ENDPOINT_NAME, WSConfig.ENDPOINT_NAME);
            map.putOpt(WSConfig.KeyParam.DATA, param);
            map.put(WSConfig.KeyParam.CHECK_SUM, StringUtils.md5(param.toString().replace("\\", "") + WSConfig.ENDPOINT_KEY));
            map.put(WSConfig.KeyParam.CHECK_SUM, "4b1beb638e2dafa1c41a1f00e94e5cda");
            map.put(WSConfig.KeyParam.REQUEST_ID, WSConfig.REQUEST_ID);
            map.put(WSConfig.KeyParam.LANG_CODE, "vi");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject getParams2(JSONObject param) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.SERVICE_ENDPOINT_NAME, WSConfig.ENDPOINT_NAME);
            map.putOpt(WSConfig.KeyParam.DATA, param);
            map.put(WSConfig.KeyParam.CHECK_SUM, StringUtils.md5(param.toString().replace("\\", "") + WSConfig.ENDPOINT_KEY));
            map.put(WSConfig.KeyParam.CHECK_SUM, "4b1beb638e2dafa1c41a1f00e94e5cda");
            map.put(WSConfig.KeyParam.REQUEST_ID, WSConfig.REQUEST_ID);
            map.put(WSConfig.KeyParam.LANG_CODE2, "vi");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, Object> getDefault() {
        Map<String, Object> map = new HashMap<>();
        map.put(WSConfig.KeyParam.LANG_CODE, "vi");
        if (null != MyApplication.getInstance().getMyLocation()
                && MyApplication.getInstance().getMyLocation().getLat() != MyApplication.getInstance().getMyLocation().getLog()) {
            map.put(WSConfig.KeyParam.LAT, MyApplication.getInstance().getMyLocation().getLat());
            map.put(WSConfig.KeyParam.LOG, MyApplication.getInstance().getMyLocation().getLog());
        }
        return map;
    }

    public static Map<String, Object> getDefaultNonLangCode() {
        Map<String, Object> map = new HashMap<>();
        if (null != MyApplication.getInstance().getMyLocation()
                && MyApplication.getInstance().getMyLocation().getLat() != MyApplication.getInstance().getMyLocation().getLog()) {
            map.put(WSConfig.KeyParam.LAT, MyApplication.getInstance().getMyLocation().getLat());
            map.put(WSConfig.KeyParam.LOG, MyApplication.getInstance().getMyLocation().getLog());
        }
        return map;
    }

    public static Map<String, Object> getMenuList() {
        Map<String, Object> map = getDefault();
        return map;
    }

    public static Map<String, Object> getSearchList(String keyword, int page) {
        Map<String, Object> map = new HashMap<>();
        map.put(WSConfig.KeyParam.KEY_WORRD, keyword);
        if (page > 1) {
            map.put(WSConfig.KeyParam.PAGE, page);
        }
        if (null != MyApplication.getInstance().getMyLocation()
                && MyApplication.getInstance().getMyLocation().getLat() != MyApplication.getInstance().getMyLocation().getLog()) {
            map.put(WSConfig.KeyParam.LAT, MyApplication.getInstance().getMyLocation().getLat());
            map.put(WSConfig.KeyParam.LOG, MyApplication.getInstance().getMyLocation().getLog());
        }
        return map;
    }

    public static Map<String, Object> page(int page) {
        Map<String, Object> map = new HashMap<>();
        map.put(WSConfig.KeyParam.PAGE, page);
        map.put(WSConfig.KeyParam.SIZE, 10);
        return map;
    }

    public static Map<String, Object> getShowAll(int page) {
        Map<String, Object> map = new HashMap<>();
        map.put(WSConfig.KeyParam.PAGE, page);
        map.put(WSConfig.KeyParam.LIMIT, 10);
        return map;
    }

    public static Map<String, Object> getNearYou(int page, String type, String distance, double lat, double log) {
        Map<String, Object> map = new HashMap<>();
        map.put(WSConfig.KeyParam.TYPE, type);
        map.put(WSConfig.KeyParam.DISTANCE, distance);
        map.put(WSConfig.KeyParam.LAT, lat);
        map.put(WSConfig.KeyParam.LOG, log);
        map.put(WSConfig.KeyParam.PAGE, page);
        map.put(WSConfig.KeyParam.LIMIT, 10);
        return map;
    }

    public static Map<String, Object> getChatMessage(String mess, String idSession, String lat, String log, String token) {
        Map<String, Object> map = new HashMap<>();
        map.put(WSConfig.KeyParam.MESSAGE, mess);
        map.put(WSConfig.KeyParam.SESSION_ID, idSession);
        map.put(WSConfig.KeyParam.LAT, lat);
        map.put(WSConfig.KeyParam.LOG, log);
        map.put(WSConfig.KeyParam.PLATFORM, WSConfig.KeyParam.ANDROID);
        map.put(WSConfig.KeyParam.TOKEN, token);
        return map;
    }

    public static JSONObject verifyOtp(String mobile, String otp) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.MOBILE, mobile);
            map.put(WSConfig.KeyParam.OTP, otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject setPassRegister(Integer id, String mobile, String pass, String packageCode, String fullName) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.ID, id);
            map.put(WSConfig.KeyParam.MOBILE, mobile);
            map.put(WSConfig.KeyParam.PASSWORD, pass);
            map.put(WSConfig.KeyParam.PACKAGE_CODE, packageCode);
            map.put(WSConfig.KeyParam.FULLNAME, fullName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject setPassReset(Integer id, String mobile, String pass) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.ID, id);
            map.put(WSConfig.KeyParam.MOBILE, mobile);
            map.put(WSConfig.KeyParam.PASSWORD, pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject register(String mobile, String name) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.MOBILE, mobile);
            map.put(WSConfig.KeyParam.FULLNAME, name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject resendOtp(String mobile) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.MOBILE, mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject resetPassword(String mobile) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.MOBILE, mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject login(String mobile, String pass, String deviceToken) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.MOBILE, mobile);
            map.put(WSConfig.KeyParam.PASSWORD, pass);
            map.put(WSConfig.KeyParam.DEVICE_TOKEN, deviceToken);
            map.put(WSConfig.KeyParam.DEVICE_TYPE, "Android");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject loginGoogle(String googleId, String displayName, String email, String avatar) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.GOOGLE_ID, googleId);
            map.put(WSConfig.KeyParam.FULLNAME, displayName);
            map.put(WSConfig.KeyParam.EMAIL, email);
            map.put(WSConfig.KeyParam.PROFILE, avatar);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject loginFace(String faceId, String fullName, String mail) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.FACEBOOK_ID, faceId);
            map.put(WSConfig.KeyParam.FULLNAME, fullName);
            map.put(WSConfig.KeyParam.EMAIL, mail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, Object> uploadAvatar(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put(WSConfig.KeyParam.ID, id);
        return map;
    }

    public static JSONObject getUserId(int id) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.USER_ID, id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject updateInfo(Integer id, String name, String mobile, String mail, String birthday, Integer gender, String address, String cmnd, String about) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.ID, id);
            map.put(WSConfig.KeyParam.FULLNAME, name);
            map.put(WSConfig.KeyParam.MOBILE, mobile);
            map.put(WSConfig.KeyParam.EMAIL, mail);
            if (null != birthday && birthday.length() > 0) {
                map.put(WSConfig.KeyParam.BIRTHDAY, birthday + " 00:00:00");
            }
            map.put(WSConfig.KeyParam.GENDER, gender);
            map.put(WSConfig.KeyParam.ADDRESS, address);
            map.put(WSConfig.KeyParam.ICPP_NUMBER, cmnd);
            map.put(WSConfig.KeyParam.ABOUT, about);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject changePassword(String oldPass, String newPass, String mobile) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.OLD_PASSWORD, oldPass);
            map.put(WSConfig.KeyParam.PASSWORD, newPass);
            map.put(WSConfig.KeyParam.MOBILE, mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, Object> getCategoryNews(String category, int page) {
        Map<String, Object> map = getDefault();
        if (page > 1) {
            map.put(WSConfig.KeyParam.PAGE, page);
        }
        map.put(WSConfig.KeyParam.CATEGORY_CODE, category);
        map.put(WSConfig.KeyParam.CHANNEL, WSConfig.KeyParam.ANDROID);
        return map;
    }

    public static Map<String, Object> getNewsById(String category, String id) {
        Map<String, Object> map = getDefault();
        map.put(WSConfig.KeyParam.CATEGORY_CODE, category);
        map.put(WSConfig.KeyParam.ID, id);
        return map;
    }

    public static Map<String, Object> loadFilter(String category) {
        Map<String, Object> map = getDefault();
        map.put(WSConfig.KeyParam.PAGE, category);
        return map;
    }

    public static Map<String, Object> loadPlace(City city, ArrayList<FilterData> list, int page) {
        Map<String, Object> map = getDefault();
        if (page > 1) {
            map.put(WSConfig.KeyParam.PAGE, page);
        }
        if (null != city) {
            map.put(WSConfig.KeyParam.REGION_ID, city.getId());
        }
        if (null != list && list.size() > 0) {
            int position = 0;

            for (int i = 0; i < list.size(); i++) {
                FilterData filterData = list.get(i);
                for (int j = 0; j < filterData.getInputs().size(); j++) {
                    if (filterData.getInputs().get(j).isSelected()) {
                        String key = insertString(
                                filterData.getName(),
                                String.valueOf(position),
                                filterData.getName().length() - 2);
                        map.put(key, filterData.getInputs().get(j).getValue());
                        position++;
                        // map.put(filterData.getName(), filterData.getInputs().get(j).getValue());
                        Log.d("LamLV:", filterData.getName());
                    }
                }

            }
        }
        return map;
    }

    private static String insertString(String originalString, String stringToBeInserted, int index) {
        String newString = new String();
        for (int i = 0; i < originalString.length(); i++) {
            newString += originalString.charAt(i);
            if (i == index) {
                newString += stringToBeInserted;
            }
        }
        return newString;
    }

    public static JSONObject loadTourSuggest(City regions, int page) {
        JSONObject map = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(regions.getId());
            map.putOpt(WSConfig.KeyParam.REGIONS, jsonArray);
            map.put(WSConfig.KeyParam.PAGE, page);
            map.put(WSConfig.KeyParam.SIZE, 10);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject loadDetailSchedule(String id) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.ID, id);
            if (null != MyApplication.getInstance().getMyLocation()) {
                map.put(WSConfig.KeyParam.LAT_LOCATION, MyApplication.getInstance().getMyLocation().getLat());
                map.put(WSConfig.KeyParam.LOG_LOCATION, MyApplication.getInstance().getMyLocation().getLog());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject loadNearByLocation(double lat, double log, int page) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.LAT_LOCATION, lat);
            map.put(WSConfig.KeyParam.LOG_LOCATION, log);
//            map.put(WSConfig.KeyParam.PAGE, page);
//            map.put(WSConfig.KeyParam.SIZE, 10);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject saveScheduleTravel(int id, ArrayList<GroupSchedule> groupSchedules) {
        JSONObject map = new JSONObject();
        try {
            for (int i = 0; i < groupSchedules.size(); i++) {
                GroupSchedule groupSchedule = groupSchedules.get(i);
                JSONArray jsonArray = new JSONArray();
                for (int j = 0; j < groupSchedule.getContent().size(); j++) {
                    jsonArray.put(groupSchedule.getContent().get(j).getId());
                }
                map.putOpt(groupSchedule.getKeyGroup(), jsonArray);
                map.putOpt(WSConfig.KeyParam.USER_ID, id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject saveTourTravel(Integer id, String tourId, String startTour, String endTour, boolean isCheck) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.USER_ID, id);
            map.put(WSConfig.KeyParam.TOUR_ID, tourId);
            map.put(WSConfig.KeyParam.BEGIN_START_TOUR, startTour + " 00:00:00");
            map.put(WSConfig.KeyParam.END_START_TOUR, endTour + " 23:59:59");
            if (isCheck) {
                map.put(WSConfig.KeyParam.SEND_NOTIFY, "1");
            } else {
                map.put(WSConfig.KeyParam.SEND_NOTIFY, "0");
            }
            map.put(WSConfig.KeyParam.SEND_NOTIFY, "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject commentsCreate(int userId, String content, String contentId, String contentType, Integer parentId) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.USER_ID, userId);
            map.put(WSConfig.KeyParam.CONTENT, content);
            map.put(WSConfig.KeyParam.CONTENT_ID, contentId);
            map.put(WSConfig.KeyParam.CONTENT_TYPE, contentType);
            map.put(WSConfig.KeyParam.PARENT_ID, parentId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject commentsSearch(String contentId, Integer page) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.CONTENT_ID, contentId);
            map.put(WSConfig.KeyParam.PAGE, page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, Object> notificationReg(String deviceID, String token, String platform) {
        Map<String, Object> map = getDefault();
        map.put(WSConfig.KeyParam.DEVICE_ID, deviceID);
        map.put(WSConfig.KeyParam.DEVICE_TOKEN, token);
        map.put(WSConfig.KeyParam.PLATFORM, platform);
        return map;
    }

    public static Map<String, Object> loadWeather(City city) {
        Map<String, Object> map = getDefault();
        if (null != city) {
            map.put(WSConfig.KeyParam.REGION_ID, city.getId());
        }
        return map;
    }

    public static Map<String, RequestBody> shareMoment(String title, String shortDes, String descript, ArrayList<CustomGallery> listPhoto, ArrayList<String> tags, String type) {
        Map<String, RequestBody> map = new HashMap<>();
        map.put(WSConfig.KeyParam.NAME, RequestBody.create(MediaType.parse("text/plain"), title));
        map.put(WSConfig.KeyParam.SHORT_DESCRIPTION, RequestBody.create(MediaType.parse("text/plain"), shortDes));
        map.put(WSConfig.KeyParam.DESCRIPTION, RequestBody.create(MediaType.parse("text/plain"), descript));
        if (listPhoto.size() > 0 && type.equals(Constants.TypeLeftMenu.PHOTO)) {
            for (int i = 0; i < listPhoto.size(); i++) {
                map.put("photo_names[" + i + "]", RequestBody.create(MediaType.parse("text/plain"), listPhoto.get(i).getAboutPhoto()));
            }
        }
        if (tags.size() > 0) {
            for (int i = 0; i < tags.size(); i++) {
                map.put("tags[" + i + "]", RequestBody.create(MediaType.parse("text/plain"), tags.get(i)));
            }
        }
        map.put(WSConfig.KeyParam.TYPE, RequestBody.create(MediaType.parse("text/plain"), type));
        return map;
    }

    public static Map<String, Object> getListNotify(String deviceID) {
        Map<String, Object> map = new HashMap<>();
        map.put(WSConfig.KeyParam.LANG_CODE, "vi");
        map.put(WSConfig.KeyParam.DEVICE_ID, deviceID);
        return map;
    }

    public static Map<String, Object> trackLocation(double lat, double lng, String deviceId) {
        Map<String, Object> map = new HashMap<>();
        map.put(WSConfig.KeyParam.LAT, lat);
        map.put(WSConfig.KeyParam.LOG, lng);
        map.put(WSConfig.KeyParam.DEVICE_ID, deviceId);
        map.put(WSConfig.KeyParam.CHANEL, "APP");
        map.put(WSConfig.KeyParam.PLATFORM, "ANDROID");
        return map;
    }

    public static JSONObject createRoom(String mobile) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.MOBILE, mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject sendFormChat(String username, String mobile, String timeContact, String startTime, String endTime, String content, String email) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.USERNAME, username);
            map.put(WSConfig.KeyParam.MOBILE, mobile);
            map.put(WSConfig.KeyParam.TIME_CONTACT, timeContact);
            map.put(WSConfig.KeyParam.START_TIME, startTime);
            map.put(WSConfig.KeyParam.END_TIME, endTime);
            map.put(WSConfig.KeyParam.CONTENT, content);
            map.put(WSConfig.KeyParam.EMAIL, email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject deleteCallHistory(String type, List<String> callList) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.TYPE, type);

            JSONArray jsonArray = new JSONArray();
            for (String s : callList) {
                jsonArray.put(s);
            }
            map.put(WSConfig.KeyParam.IDS, jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject inviteCallNow(String mobile) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.MOBILE, mobile);
            map.put(WSConfig.KeyParam.CODE, "ANDROID");
            map.put(WSConfig.KeyParam.PATH, "ANDROID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject callList(List<String> callList) {
        JSONObject map = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            for (String s : callList) {
                jsonArray.put(s);
            }
            map.put(WSConfig.KeyParam.MOBILES, jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject verifyUser(String mobile) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.MOBILE, mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject getMobileFromViettel() {
        JSONObject map = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            map.put(WSConfig.KeyParam.SERVICE_ENDPOINT_NAME, WSConfig.ENDPOINT_NAME);
            map.putOpt(WSConfig.KeyParam.DATA, jsonArray);
            map.put(WSConfig.KeyParam.CHECK_SUM, StringUtils.md5(jsonArray.toString().replace("\\", "") + WSConfig.ENDPOINT_KEY));
            map.put(WSConfig.KeyParam.CHECK_SUM, "4b1beb638e2dafa1c41a1f00e94e5cda");
            map.put(WSConfig.KeyParam.REQUEST_ID, WSConfig.REQUEST_ID);
            map.put(WSConfig.KeyParam.LANG_CODE2, "vi");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }


    public static Map<String, Object> zipVersion() {
        Map<String, Object> map = new HashMap<>();
        map.put(WSConfig.KeyParam.NAME, "Call_Now");
        return map;
    }

    public static JSONObject getComment(String contentId) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.CONTENT_ID, contentId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }


    public static JSONObject updateSystemInbox(String id) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.ID, id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject share(String contentType, String detailLink, ArrayList<String> listContact) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.DETAIL_LINK, detailLink);
            map.put(WSConfig.KeyParam.CONTENT_TYPE, contentType);
            JSONArray jsonArray = new JSONArray();
            for (String s : listContact) {
                jsonArray.put(s);
            }
            map.put(WSConfig.KeyParam.MOBILE_SHARE_LIST, jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject confirmEnterTrip(String id, String userId) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.SCHEDULE_CUSTOM_ID, id);
            map.put(WSConfig.KeyParam.USER_ID, userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject ticketInfo(String ticketId) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.TICKET_ID, ticketId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject createComment(String parentId, String userId, String content, String contentId, String contentType) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.PARENT_ID, parentId);
            map.put(WSConfig.KeyParam.USER_ID, userId);
            map.put(WSConfig.KeyParam.CONTENT, content);
            map.put(WSConfig.KeyParam.CONTENT_ID, contentId);
            map.put(WSConfig.KeyParam.CONTENT_TYPE, contentType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject deleteComment(String id) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.ID, id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject updateComment(String id, String content) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.ID, id);
            map.put(WSConfig.KeyParam.CONTENT, content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, Object> getGallery(String galleryId) {
        Map<String, Object> map = new HashMap<>();
        map.put(WSConfig.KeyParam.GALLERY_ID, galleryId);
        return map;
    }

    public static Map<String, Object> getVideoByTag(String tag) {
        Map<String, Object> map = new HashMap<>();
        map.put(WSConfig.KeyParam.TAG, tag);
        return map;
    }

    public static JSONObject createReview(String parentId, String userId, String content, String contentId, String contentType, String postRate, List<String> galleryUris) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.PARENT_ID, parentId);
            map.put(WSConfig.KeyParam.USER_ID, userId);
            map.put(WSConfig.KeyParam.CONTENT, content);
            map.put(WSConfig.KeyParam.CONTENT_ID, contentId);
            map.put(WSConfig.KeyParam.CONTENT_TYPE, contentType);
            map.put(WSConfig.KeyParam.POST_RATE, postRate);

            JSONArray jsonArray = new JSONArray();
            for (String s : galleryUris) {
                jsonArray.put(s);
            }
            map.put(WSConfig.KeyParam.GALLERY_URIS, jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject getReview(String contentId, String parentId) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.CONTENT_ID, contentId);
            map.put(WSConfig.KeyParam.PARENT_ID, parentId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }


    public static JSONObject checkUserReceiver(String voucherId, String channel, String os, String event) {
        JSONObject map = new JSONObject();
        try {
            map.put("voucherId", voucherId);
            map.put("channel", channel);
            map.put("os", os);
            map.put("event", event);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, Object> getBigLocation(String regionId) {
        Map<String, Object> map = getDefault();
        map.put(WSConfig.KeyParam.REGION_ID, regionId);
        return map;
    }

    public static Map<String, Object> postUserGuild(String code) {
        Map<String, Object> map = new HashMap<>();
        map.put(WSConfig.KeyParam.CODE, code);
        return map;
    }

    public static JSONObject requestServiceOtp(String mobile, String packageCode, String channel) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.MOBILE, mobile);
            map.put(WSConfig.KeyParam.PACKAGE_CODE, packageCode);
            map.put(WSConfig.KeyParam.CHANNEL, channel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject confirmServiceOtp(String mobile, String packageCode, String channel, String otp) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.MOBILE, mobile);
            map.put(WSConfig.KeyParam.PACKAGE_CODE, packageCode);
            map.put(WSConfig.KeyParam.CHANNEL, channel);
            map.put(WSConfig.KeyParam.OTP, otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject resentServiceOtp(String mobile, String packageCode, String channel) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.MOBILE, mobile);
            map.put(WSConfig.KeyParam.PACKAGE_CODE, packageCode);
            map.put(WSConfig.KeyParam.CHANNEL, channel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject getInfo(String code) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.ID, code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, Object> getService(String channel) {
        Map<String, Object> map = getDefault();
        map.put(WSConfig.KeyParam.CHANNEL, channel);
        return map;
    }

    public static Map<String, Object> getOwnedVoucher(String service, String sort, String regionId, String memberRankId, String categoryId, int page) {
        Map<String, Object> map = new HashMap<>();
        map.put(WSConfig.KeyParam.SERVICE, service);
        map.put(WSConfig.KeyParam.SORT, sort);
        map.put(WSConfig.KeyParam.REGION_ID2, regionId);
        map.put(WSConfig.KeyParam.MEMBER_RANK_ID, memberRankId);
        map.put(WSConfig.KeyParam.CATEGORY_ID, categoryId);
        map.put(WSConfig.KeyParam.PAGE, page);
        return map;
    }

    public static Map<String, Object> getComment(int page) {
        Map<String, Object> map = new HashMap<>();
        map.put(WSConfig.KeyParam.PAGE, page);
        return map;
    }

    public static JSONObject postEvent(String eventName) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.EVENT_NAME, eventName);
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin()) {
                map.put(WSConfig.KeyParam.USER_ID, account.getId());
                map.put(WSConfig.KeyParam.MOBILE, account.getMobile());
            }
            map.put(WSConfig.KeyParam.CHANNEL, "ANDROID");
            map.put(WSConfig.KeyParam.METHOD, "POST");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject postEvent2(String eventName, Tracking tracking) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.EVENT_NAME, eventName);

            String jsonInString = new Gson().toJson(tracking);
            JSONObject jsonObject = new JSONObject(jsonInString);

            map.put(WSConfig.KeyParam.REQUEST, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject likeEvent(String contentId, String type) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.CONTENT_ID, contentId);
            map.put(WSConfig.KeyParam.TYPE, type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject wheelRotate(String wheelLogId, String service, String wheelId) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.WHEEL_LOG_ID, wheelLogId);
            map.put(WSConfig.KeyParam.SERVICE, service);
            map.put(WSConfig.KeyParam.WHEEL_ID, wheelId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }


    public static JSONObject userGuideNew(String code) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.CODE, code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject wheelResult(String service, String os, String channel) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.SERVICE, service);
            map.put(WSConfig.KeyParam.OS, os);
            map.put(WSConfig.KeyParam.CHANNEL, channel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject wheelAreas(String service, List<String> listIdVoucher) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.SERVICE, service);

            JSONArray jsonArray = new JSONArray();
            for (String s : listIdVoucher) {
                jsonArray.put(s);
            }

            map.put(WSConfig.KeyParam.LIST_ID_VOUCHER, jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject getRoomId(String myPhone, String guestPhone) {
        JSONObject map = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(myPhone);
            jsonArray.put(guestPhone);

            JSONArray jsonArrayIP = new JSONArray();
            jsonArrayIP.put("myPhone");
            jsonArrayIP.put("guestPhone");


            map.put(WSConfig.KeyParam.MOBILES, jsonArray);
            map.put(WSConfig.KeyParam.IPS, jsonArrayIP);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject reviewChat(String admin, String note, List<String> reasons, String rate) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.ADMIN, admin);
            map.put(WSConfig.KeyParam.NOTE, note);

            JSONArray jsonArray = new JSONArray();
            for (String s : reasons) {
                jsonArray.put(s);
            }

            map.put(WSConfig.KeyParam.REASONS, jsonArray);
            map.put(WSConfig.KeyParam.RATE, rate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject processTicket(String content, int status) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.PROCESS_TICKET_CONTENT, content);
            if (status > 0) {
                map.put(WSConfig.KeyParam.TICKET_STATUS, status);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject yesNoReview(String satisfied) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.SATISFIED, satisfied);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject updateTheme(String themeId) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.THEME_ID, themeId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject updateTicket(String ticketId, String content, String status) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.TICKET_ID, ticketId);

            map.put(WSConfig.KeyParam.CONTENT, content);
            map.put(WSConfig.KeyParam.STATUS, status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject getListHistoryTicket(String ticketId, String field) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeyParam.TICKET_ID, ticketId);
            map.put(WSConfig.KeyParam.FIELD, field);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }


    public static JSONObject updateInbox(String id, String status) {
        JSONObject map = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(id);
            map.putOpt(WSConfig.KeyParam.IDS, jsonArray);
            map.put(WSConfig.KeyParam.STATUS, status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject updateViewedAllInbox() {
        JSONObject map = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            map.putOpt(WSConfig.KeyParam.IDS, jsonArray);
            map.put(WSConfig.KeyParam.STATUS, "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
}
