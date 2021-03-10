package com.namviet.vtvtravel.app;

import android.app.Application;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;


import androidx.annotation.NonNull;

import com.daimajia.slider.library.NearDistance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.namviet.vtvtravel.api.TravelFactory;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.database.AppDatabase;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.City;
import com.namviet.vtvtravel.model.MyLocation;
import com.namviet.vtvtravel.model.NearType;
import com.namviet.vtvtravel.model.chat.ChatData;
import com.namviet.vtvtravel.model.chat.Room;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.ultils.DeviceUtils;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.ultils.StringUtils;
import com.namviet.vtvtravel.viewmodel.AccountViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class MyApplication extends Application implements Observer {

    private boolean isVipRegisted;
    private AppDatabase database;
    private static MyApplication instance;

    private TravelService travelService;
    private TravelService travelService1;
    private TravelService travelService2;
    private TravelService travelService3;
    private Scheduler scheduler;
    private static MyApplication ourInstance;
    private MyLocation myLocation;
    private List<NearDistance> nearDistance;
    private List<NearType> nearType;
    private String titleDetail = "";
    private Account mAccount;
    private int countNotify = 0;
    private List<ChatData> mChatDatas = new ArrayList<>();
    private Room room;

    private boolean isChatBot = true;
    private boolean isFirstTimeApp = true;
    private boolean mIsFirstChat = true;
    private boolean isAdminChated = false;

    public boolean isVipRegisted() {
        return isVipRegisted;
    }

    public void setVipRegisted(boolean vipRegisted) {
        isVipRegisted = vipRegisted;
    }

    public boolean isAdminChated() {
        return isAdminChated;
    }

    public void setAdminChated(boolean adminChated) {
        isAdminChated = adminChated;
    }

    public boolean isFirstTimeApp() {
        return isFirstTimeApp;
    }

    public void setFirstTimeApp(boolean firstTimeApp) {
        isFirstTimeApp = firstTimeApp;
    }

    public boolean isChatBot() {
        return isChatBot;
    }

    public void setChatBot(boolean chatBot) {
        isChatBot = chatBot;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean ismIsFirstChat() {
        return mIsFirstChat;
    }

    public void setmIsFirstChat(boolean mIsFirstChat) {
        this.mIsFirstChat = mIsFirstChat;
    }

    public String getTitleDetail() {
        return titleDetail;
    }

    public void setTitleDetail(String titleDetail) {
        this.titleDetail = titleDetail;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setTimeStamp();
        instance = this;
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        try {
                            if (!task.isSuccessful()) {
                                Log.w("messsss", "getInstanceId failed", task.getException());
                                return;
                            }

                            // Get new Instance ID token
                            String token = task.getResult().getToken();
                            PreferenceUtil.getInstance(MyApplication.this).setValue(Constants.PrefKey.DEVICE_TOKEN, token);
                            Log.e("tokennnn", token);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


        initDatabase();
        mAccount = new Account();
        try {
            Account account = new Gson().fromJson(PreferenceUtil.getInstance(getBaseContext()).getValue(Constants.PrefKey.ACCOUNT, ""), Account.class);
            MyApplication.getInstance().setAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AccountViewModel accountViewModel = new AccountViewModel();
        accountViewModel.addObserver(this);
        boolean isLogin = PreferenceUtil.getInstance(getBaseContext()).getValue(Constants.PrefKey.IS_LOGIN, false);
        if (isLogin) {
            int typeLogin = PreferenceUtil.getInstance(getBaseContext()).getValue(Constants.PrefKey.LOGIN, 0);
            switch (typeLogin) {
                case Constants.TypeLogin.MOBILE:
                    String mobile = PreferenceUtil.getInstance(getBaseContext()).getValue(Constants.PrefKey.MOBILE, "");
                    String password = PreferenceUtil.getInstance(getBaseContext()).getValue(Constants.PrefKey.PASSWORD, "");
//                    accountViewModel.login(StringUtils.isPhoneValidateV2(mobile, 84), password, PreferenceUtil.getInstance(MyApplication.this).getValue(Constants.PrefKey.DEVICE_TOKEN, ""));
                    long currentTime = System.currentTimeMillis()/1000;
                    long cacheTime = getTimeStamp();

//                    if((currentTime - cacheTime) > 518400){
                        accountViewModel.refreshToken();
//                    }
                    Log.e("rangetimeeeee", (currentTime - cacheTime)+"");
                    break;
                case Constants.TypeLogin.GOOGLE:
                    String googleId = PreferenceUtil.getInstance(getBaseContext()).getValue(Constants.PrefKey.GOOGLE_ID, "");
                    accountViewModel.loginGoogle(googleId, null, null, null);
                    break;
                case Constants.TypeLogin.FACEBOOK:
                    String facebookId = PreferenceUtil.getInstance(getBaseContext()).getValue(Constants.PrefKey.FACEBOOK_ID, "");
                    accountViewModel.loginGoogle(facebookId, null, null, null);
                    break;
            }
        } else {
            String token = FirebaseInstanceId.getInstance().getToken();
            accountViewModel.notificationReg(DeviceUtils.getDeviceId(getBaseContext()), token, "ANDROID");
        }

    }

    public static MyApplication getInstance() {
        if (ourInstance == null) {
            ourInstance = new MyApplication();
        }
        return ourInstance;
    }

    public TravelService getTravelService() {
//        if (travelService == null) {
//
//        }
        travelService = TravelFactory.createService(TravelService.class);
        return travelService;
    }

    public TravelService getTravelServiceAcc() {
//        if (travelService1 == null) {
//            travelService1 = TravelFactory.createServiceAcc(TravelService.class);
//        }
        travelService1 = TravelFactory.createServiceAcc(TravelService.class);
        return travelService1;
    }

    public TravelService getTravelServiceChat() {
//        if (travelService1 == null) {
//            travelService1 = TravelFactory.createServiceAcc(TravelService.class);
//        }
        travelService2 = TravelFactory.createServiceChat(TravelService.class);
        return travelService2;
    }

    public TravelService getTravelServiceAccNoneToken() {
//        if (travelService1 == null) {
//            travelService1 = TravelFactory.createServiceAcc(TravelService.class);
//        }
        travelService3 = TravelFactory.createServiceAccNoneToken(TravelService.class);
        return travelService3;
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public void setPeopleService(TravelService newsService) {
        this.travelService = newsService;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public MyLocation getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(MyLocation myMyLocation) {
        this.myLocation = myMyLocation;
    }

    public List<NearDistance> getNearDistance() {
        return nearDistance;
    }

    public void setNearDistance(List<NearDistance> nearDistance) {
        this.nearDistance = nearDistance;
    }

    public List<NearType> getNearType() {
        return nearType;
    }

    public void setNearType(List<NearType> nearType) {
        this.nearType = nearType;
    }

    public Account getAccount() {
        return mAccount;
    }

    public void setAccount(Account mAccount) {
        this.mAccount = mAccount;
    }

    public int getCountNotify() {
        return countNotify;
    }

    public void setCountNotify(int countNotify) {
        this.countNotify = countNotify;
    }

    private void login() {

    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof AccountViewModel) {
            if (null != o) {
                if (o instanceof AccountResponse) {
                    AccountResponse accountResponse = (AccountResponse) o;
                    if (accountResponse.isSuccess()) {
                        setCurrentTimeStampToCache();
                        PreferenceUtil.getInstance(getBaseContext()).setValue(Constants.PrefKey.IS_LOGIN, true);
                        PreferenceUtil.getInstance(getBaseContext()).setValue(Constants.PrefKey.ACCOUNT, new Gson().toJson(accountResponse.getData()));
                        MyApplication.getInstance().setAccount(accountResponse.getData());
                        Intent intent = new Intent(Constants.KeyBroadcast.KEY_SAVE_LOGIN_SCREEN);
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                    }
                }
            }

        }
    }

    private static boolean activityVisible;

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static String SECTION_ID = "";

    public static String getSessionId() {
        if ("".equals(SECTION_ID)) {
            long time = System.currentTimeMillis();
            SECTION_ID = String.valueOf(time);
        }
        return SECTION_ID;
    }

    public List<ChatData> getmChatDatas() {
        return mChatDatas;
    }

    public void setmChatDatas(List<ChatData> mChatDatas) {
        this.mChatDatas = mChatDatas;
    }

    public List<City> cityList;

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    private void initDatabase() {
        database = AppDatabase.getInMemoryDatabase(this);
    }

    public static AppDatabase getAppDatabase() {
        return get().getDatabase();
    }

    public synchronized static MyApplication get() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    private void setTimeStamp() {
        long timeStamp = PreferenceUtil.getInstance(this).getValueLong(Constants.PrefKey.TIME_STAMP, 0);

        if(timeStamp == 0){
            long tsLong = System.currentTimeMillis()/1000;
            PreferenceUtil.getInstance(this).setValue(Constants.PrefKey.TIME_STAMP, tsLong);
        }

    }

    private long getTimeStamp(){
        return PreferenceUtil.getInstance(this).getValueLong(Constants.PrefKey.TIME_STAMP, 0);
    }

    private void setCurrentTimeStampToCache(){
        long tsLong = System.currentTimeMillis()/1000;
        PreferenceUtil.getInstance(this).setValue(Constants.PrefKey.TIME_STAMP, tsLong);
    }

}
