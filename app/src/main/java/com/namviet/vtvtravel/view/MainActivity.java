package com.namviet.vtvtravel.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;

import com.namviet.vtvtravel.view.f2.DetailDealWebviewActivity;
import com.namviet.vtvtravel.view.f2.WebviewActivity;
import com.namviet.vtvtravel.view.f3.notification.model.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.ContactsContract;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.test.mock.MockPackageManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baseapp.activity.BaseActivity;
import com.baseapp.menu.SlideMenu;
import com.baseapp.utils.MyFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.Travel;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.SlideMenuAdapter;
import com.namviet.vtvtravel.api.DownloadAPI;
import com.namviet.vtvtravel.api.ServiceGenerator;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.listener.CitySelectListener;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.City;
import com.namviet.vtvtravel.model.ItemMenu;
import com.namviet.vtvtravel.model.ItemNotify;
import com.namviet.vtvtravel.model.ItemWeather;
import com.namviet.vtvtravel.model.MenuLeft;
import com.namviet.vtvtravel.model.MyLocation;
import com.namviet.vtvtravel.model.News;
import com.namviet.vtvtravel.model.chat.ChatBase;
import com.namviet.vtvtravel.model.f2.Contact;
import com.namviet.vtvtravel.model.f2event.OnGetLocation;
import com.namviet.vtvtravel.model.f2event.OnLoadContactSuccess;
import com.namviet.vtvtravel.model.f2event.OnLoadFail;
import com.namviet.vtvtravel.model.f2event.OnReceiveNotiVip;
import com.namviet.vtvtravel.model.f2event.OnUpdateLogin;
import com.namviet.vtvtravel.model.offline.OfflineDynamic;
import com.namviet.vtvtravel.receiver.ConnectivityReceiver;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.SearchResponse;
import com.namviet.vtvtravel.response.f2callnow.ZipVersionResponse;
import com.namviet.vtvtravel.response.f2systeminbox.ConfirmEnterTrip;
import com.namviet.vtvtravel.response.f2systeminbox.DataSystemInbox;
import com.namviet.vtvtravel.service.GPSTracker;
import com.namviet.vtvtravel.service.TrackLocationService;
import com.namviet.vtvtravel.ultils.DeviceUtils;
import com.namviet.vtvtravel.ultils.F2UnzipUtil;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.ultils.FirebaseAnalytic;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.ultils.ServiceUltils;
import com.namviet.vtvtravel.ultils.ValidateUtils;
import com.namviet.vtvtravel.view.dialog.CityDialogFragment;
import com.namviet.vtvtravel.view.dialog.WeatherDialog;
import com.namviet.vtvtravel.view.dialog.f2.ReceiverTripInviteDialog;
import com.namviet.vtvtravel.view.f2.DetailVideoActivity;
import com.namviet.vtvtravel.view.f2.ImagePartActivity;
import com.namviet.vtvtravel.view.f2.LiveTVActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.ReceiveInviteTripDetailActivity;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;
import com.namviet.vtvtravel.view.f2.SystemInboxActivity;
import com.namviet.vtvtravel.view.f2.TravelNewsActivity;
import com.namviet.vtvtravel.view.f2.TravelVoucherActivity;
import com.namviet.vtvtravel.view.f3.notification.view.NotificationActivity;
import com.namviet.vtvtravel.view.f2.VQMMWebviewActivity;
import com.namviet.vtvtravel.view.f3.notification.model.NotificationCode;
import com.namviet.vtvtravel.view.fragment.ChatFragment;
import com.namviet.vtvtravel.view.fragment.DetailsFragment;
import com.namviet.vtvtravel.view.fragment.EncodeDemoFragment;
import com.namviet.vtvtravel.view.fragment.FormChatFragment;
import com.namviet.vtvtravel.view.fragment.FormSuccessFragment;
import com.namviet.vtvtravel.view.fragment.MapChatFragment;
import com.namviet.vtvtravel.view.fragment.NearYouFragment;
import com.namviet.vtvtravel.view.fragment.PhotoGalleryFragment;
import com.namviet.vtvtravel.view.fragment.ShowAllFragment;
import com.namviet.vtvtravel.view.fragment.SlideMenuFragment;
import com.namviet.vtvtravel.view.fragment.SplashFragment;
import com.namviet.vtvtravel.view.fragment.WebviewFragment;
import com.namviet.vtvtravel.view.fragment.account.AccountFragment;
import com.namviet.vtvtravel.view.fragment.account.ChangePassFragment;
import com.namviet.vtvtravel.view.fragment.account.ForgotPassFragment;
import com.namviet.vtvtravel.view.fragment.account.LoginFragment;
import com.namviet.vtvtravel.view.fragment.account.OtpFragment;
import com.namviet.vtvtravel.view.fragment.account.RegisterFragment;
import com.namviet.vtvtravel.view.fragment.account.SetPassFragment;
import com.namviet.vtvtravel.view.fragment.account.SettingFragment;
import com.namviet.vtvtravel.view.fragment.account.UpdateInfoFragment;
import com.namviet.vtvtravel.view.fragment.f2callnow.MainCallNowFragment;
import com.namviet.vtvtravel.view.fragment.f2mygift.NotifyDialog;
import com.namviet.vtvtravel.view.fragment.f2offline.MainOfflineFragment;
import com.namviet.vtvtravel.view.fragment.f2offline.MainPageLoginFragment;
import com.namviet.vtvtravel.view.fragment.f2offline.OfflineDialog;
import com.namviet.vtvtravel.view.fragment.f2service.RegisterSuccessFragment;
import com.namviet.vtvtravel.view.fragment.f2service.RegisterSuccessFriendFragment;
import com.namviet.vtvtravel.view.fragment.home.CreateScheduleFragment;
import com.namviet.vtvtravel.view.fragment.home.DetailMomentFrangment;
import com.namviet.vtvtravel.view.fragment.home.DetailScheduleCreateFragment;
import com.namviet.vtvtravel.view.fragment.home.DetailScheduleTravelFragment;
import com.namviet.vtvtravel.view.fragment.home.DetailScheduleTravelFragmentMine;
import com.namviet.vtvtravel.view.fragment.home.HomeFragment;
import com.namviet.vtvtravel.view.fragment.home.ListSuggestFragment;
import com.namviet.vtvtravel.view.fragment.home.PhotoNiceFragment;
import com.namviet.vtvtravel.view.fragment.home.PlaylistVideoJwplayerFragment;
import com.namviet.vtvtravel.view.fragment.home.SchedulePlayFragment;
import com.namviet.vtvtravel.view.fragment.home.ScheduleShoppingFragment;
import com.namviet.vtvtravel.view.fragment.home.ShareMomentFragment;
import com.namviet.vtvtravel.view.fragment.news.DetailNewsHighlightFragment;
import com.namviet.vtvtravel.view.fragment.news.ListNewsHighlightFragment;
import com.namviet.vtvtravel.view.fragment.nofity.NotifyFragment;
import com.namviet.vtvtravel.view.fragment.search.SearchFragment;
import com.namviet.vtvtravel.view.fragment.search.SearchResultFragment;
import com.namviet.vtvtravel.view.fragment.travel.DetailLiveChannelFragment;
import com.namviet.vtvtravel.view.fragment.travel.DetailWhatEatFragment;
import com.namviet.vtvtravel.view.fragment.travel.DetailWhereFragment;
import com.namviet.vtvtravel.view.fragment.travel.DetailWhereStayFragment;
import com.namviet.vtvtravel.view.fragment.travel.FoodFragment;
import com.namviet.vtvtravel.view.fragment.travel.LiveChannelFragment;
import com.namviet.vtvtravel.view.fragment.travel.NearPlaceFragment;
import com.namviet.vtvtravel.view.fragment.travel.StayFragment;
import com.namviet.vtvtravel.view.fragment.travel.VoucherFragment;
import com.namviet.vtvtravel.view.fragment.travel.WhatPlayFragment;
import com.namviet.vtvtravel.view.fragment.travel.WhereFragment;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;
import com.namviet.vtvtravel.viewmodel.HomeViewModel;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;
import com.namviet.vtvtravel.viewmodel.SearchViewModel;
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel;
import com.namviet.vtvtravel.viewmodel.f2systeminbox.SystemInboxViewModel;
import com.namviet.vtvtravel.widget.RobotoTextView;

import org.ankit.gpslibrary.ADLocation;
import org.ankit.gpslibrary.MyTracker;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;
import io.sentry.Sentry;
import io.sentry.android.AndroidSentryClientFactory;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BaseActivity implements Observer, CitySelectListener, View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener, Handler.Callback {
    private OnBackPress onBackPress;
    private Fragment mFragment;
    private Bundle bundle;
    private static final int REQUEST_CODE_PERMISSION = 2;
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 80;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private DrawerLayout drawerLayout;
    private boolean addToBackStack = false;
    private SlideMenuFragment menuFragment;
    private MenuLeft menuLeft;
    private SearchViewModel searchViewModel;
    private List<Object> listSearchTrend;
    private ArrayList<ItemWeather> weatherList;
    private final int REQUEST_CHECK_SETTINGS = 1;

    private static FirebaseAnalytics mFirebaseAnalytics;

    //for drawerlayout
    private MenuLeft menuLeftDrawer;
    private SlideMenuAdapter slideMenuAdapter;
    private Account account;
    private PlaceViewModel placeViewModel;
    private ArrayList<City> cityList;
    private ArrayList<ItemWeather> weatherListDrawer;
    private RobotoTextView mTvCity;
    private RobotoTextView mBtLogin;
    private RobotoTextView mBtRegister;
    private CircleImageView mAvatar;
    private RecyclerView mRvSlideMenu;

    private LinearLayout mLlLogin;
    private LinearLayout mLlInfor;
    private RobotoTextView mTvName;
    private RobotoTextView mTvPhone;
    private ProfilePictureView mProfileAvatar;
    private RobotoTextView mTvTemp;
    private RobotoTextView mTvHumidity;
    private ImageView mIvIconWeather;
    private ImageView mIvBgInfo;
    private ConstraintLayout layoutNoInternet;
    private TextView btnGoOffline;
    private LinearLayout btnClose;
    private View viewClose;
    private List<ItemMenu> itemMenus = new ArrayList<>();

    private HomeViewModel homeViewModel;
    private SystemInboxViewModel systemInboxViewModel;

    public List<Contact> listContact = new ArrayList<>();
    public HashMap<String, Contact> contactHashMap = new HashMap<>();
    public HashMap<Integer, Contact> listContactCallNow = new HashMap<>();

    private boolean fromNotification = false;
    private boolean fromVIPNoti = false;
    private AlertDialog permissionDialog;
    private SearchBigLocationViewModel searchBigLocationViewModel;


    //for Linphone
//    private Handler mHandler;
//    private CoreListenerStub mCoreListener;
//    private ImageView mLed;


    //for pjsip
//    public static MyApp app = null;
//    public static MyCall currentCall = null;
//    public static MyAccount sipAccount = null;
//    public static AccountConfig accCfg = null;
//    public static MyBroadcastReceiver receiverPJSIP = null;
//    public static IntentFilter intentFilter = null;
//
//    private final Handler handler = new Handler(this);

    @Override
    public boolean handleMessage(@androidx.annotation.NonNull Message msg) {
        return false;
    }


//    public class MSG_TYPE
//    {
//        public final static int INCOMING_CALL = 1;
//        public final static int CALL_STATE = 2;
//        public final static int REG_STATE = 3;
//        public final static int BUDDY_STATE = 4;
//        public final static int CALL_MEDIA_STATE = 5;
//        public final static int CHANGE_NETWORK = 6;
//    }

    private void initViewDrawerLayout() {


        mTvCity = findViewById(R.id.tvCity);
        mBtLogin = findViewById(R.id.btLogin);
        mBtRegister = findViewById(R.id.btRegister);
        mAvatar = findViewById(R.id.avatar);
        mRvSlideMenu = findViewById(R.id.rvSlideMenu);
        mLlLogin = findViewById(R.id.llLogin);
        mLlInfor = findViewById(R.id.llInfo);
        mTvName = findViewById(R.id.tvName);
        mTvPhone = findViewById(R.id.tvPhone);
        mProfileAvatar = findViewById(R.id.profileAvatar);
        mTvTemp = findViewById(R.id.tvTemp);
        mTvHumidity = findViewById(R.id.tvHumidity);
        mIvIconWeather = findViewById(R.id.ivIconWeather);
        mIvBgInfo = findViewById(R.id.ivBgInfo);

        mTvCity.setOnClickListener(this);
        mBtLogin.setOnClickListener(this);
        mBtRegister.setOnClickListener(this);
        mAvatar.setOnClickListener(this);
        mTvTemp.setOnClickListener(this);
        mTvHumidity.setOnClickListener(this);
    }

    private void initDrawerLayout() {
//        menuLeft = getArguments().getParcelable(Constants.IntentKey.KEY_FRAGMENT);

        mRvSlideMenu.setLayoutManager(new LinearLayoutManager(this));
        homeViewModel = new HomeViewModel(this);
        homeViewModel.addObserver(this);
        systemInboxViewModel = new SystemInboxViewModel();
        systemInboxViewModel.addObserver(this);

//        slideMenuAdapter = new SlideMenuAdapter(this, itemMenus);
//
//        mRvSlideMenu.setAdapter(slideMenuAdapter);

        placeViewModel = new PlaceViewModel(this);
        placeViewModel.addObserver(this);
//        placeViewModel.loadWeather(null);
//        placeViewModel.loadCity();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.KeyBroadcast.KEY_COUNT_UNREAD);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);

        account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            mLlLogin.setVisibility(View.GONE);
            mLlInfor.setVisibility(View.VISIBLE);

            mTvName.setText(account.getFullname());
            if (null != account.getFullname()) {
                mTvName.setVisibility(View.VISIBLE);
                mTvName.setText(account.getFullname());
            } else {
                mTvName.setVisibility(View.GONE);
            }
            if (null != account.getMobile()) {
                mTvPhone.setVisibility(View.VISIBLE);
                mTvPhone.setText("0" + account.getMobile().substring(2));
            } else {
                mTvPhone.setVisibility(View.GONE);
            }
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.user);
            Glide.with(this).setDefaultRequestOptions(requestOptions).load(account.getImageProfile()).into(mAvatar);
            if (null != account.getFacebookId() && account.getFacebookId().length() > 0) {
                mProfileAvatar.setVisibility(View.VISIBLE);
                mProfileAvatar.setProfileId(account.getFacebookId());
            }
        } else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.user);
            Glide.with(this).setDefaultRequestOptions(requestOptions).load("").into(mAvatar);
            mLlLogin.setVisibility(View.VISIBLE);
            mLlInfor.setVisibility(View.GONE);
        }


    }

    public static long mLastClickTime = 0;

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btLogin) {
            MainActivity.this.pushEvent(FirebaseAnalytic.CLICK_MENU_LOGIN);
            MainActivity.this.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);
//            openNewLogin(0);
        } else if (view.getId() == R.id.avatar) {
            if (null != account && account.isLogin()) {
                MainActivity.this.pushEvent(FirebaseAnalytic.CLICK_MENU_PROFILE);
                MainActivity.this.switchFragment(SlideMenu.MenuType.ACCOUNT_SCREEN);
            } else {
                MainActivity.this.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);
//                openNewLogin(0);
            }
        } else if (view.getId() == R.id.btRegister) {
            MainActivity.this.pushEvent(FirebaseAnalytic.CLICK_MENU_REGISTER);
            MainActivity.this.switchFragment(SlideMenu.MenuType.REGISTER_SCREEN);
//            openNewLogin(1);
        } else if (view.getId() == R.id.tvCity) {
            if (null != cityList) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                ArrayList<City> cities = new ArrayList<>(MyApplication.getInstance().getCityList());
                CityDialogFragment cityDialogFragment = CityDialogFragment.newInstance(cities);

//                CityDialogFragment cityDialogFragment = CityDialogFragment.newInstance(cityList);
                cityDialogFragment.show(MainActivity.this.getSupportFragmentManager(), Constants.TAG_DIALOG);
                cityDialogFragment.setCitySelectListener(this);
            }
        } else if (view == mTvHumidity) {
            if (null != weatherList) {
                WeatherDialog weatherDialog = WeatherDialog.newInstance(weatherList);
                weatherDialog.show(MainActivity.this.getSupportFragmentManager(), Constants.TAG_DIALOG);
            }
        } else if (view == mTvTemp) {
            mTvHumidity.performClick();
        }
    }

    private void setWeather(ItemWeather itemWeather) {
        if (null != itemWeather) {
            mTvCity.setText(itemWeather.getRegion_name());
            if (Math.round(itemWeather.getTemp_min()) == Math.round(itemWeather.getTemp_max())) {
                mTvHumidity.setVisibility(View.INVISIBLE);
            } else {
                mTvHumidity.setVisibility(View.VISIBLE);
                mTvHumidity.setText(Math.round(itemWeather.getTemp_min()) + " - " + Math.round(itemWeather.getTemp_max()) + " °C");
            }
            mTvTemp.setText(Math.round(itemWeather.getTamp()) + "°C");
            setImageUrl(itemWeather.getWeather().getIcon_url(), mIvIconWeather);
            setImageUrl(itemWeather.getBanner_url(), mIvBgInfo);
        }
    }

    public void setImageUrl(String ulrCs, ImageView image) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
        requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
        Glide.with(this).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);
    }

    @Override
    public void onSelect(ArrayList<City> list, City city) {
        placeViewModel.loadWeather(city);
    }

    public MenuLeft getMenuLeft() {
        return menuLeft;
    }

    public void setMenuLeft(MenuLeft menuLeft) {
        this.menuLeft = menuLeft;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return bundle;
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_main;
    }
    public boolean isAddToBackStack() {
        return addToBackStack;
    }



    private void getLocationByService(){
        try {
            Intent intent = new Intent(this, GPSTracker.class);
            startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow();
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        getLocationFromCache();
        getLocationByService();
        getJson();
        getDataFromIntent();
        EventBus.getDefault().register(this);
        setContentView(getLayoutId());
        createLocationRequest();
        registerNetWork();


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission) != MockPackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{mPermission, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_PERMISSION);
            } else {
                getLocation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        searchViewModel = new SearchViewModel(getBaseContext());
        searchViewModel.addObserver(this);
        searchViewModel.loadSearchTrend();

        searchViewModel.zipVersion();

//        getKeyHash();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.KeyBroadcast.KEY_SAVE_LOGIN_SCREEN);
        intentFilter.addAction(Constants.KeyBroadcast.KEY_NOTIFY);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
        initView();
        initSentry();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


//        Intent serviceIntent = new Intent(this, ForegroundService.class);
//        ContextCompat.startForegroundService(this, serviceIntent);
        try {
            searchBigLocationViewModel = new SearchBigLocationViewModel();
            searchBigLocationViewModel.getAllLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pushEvent(String key) {
        Bundle bundle = new Bundle();
        String value = PreferenceUtil.getInstance(this).getValue(Constants.PrefKey.ACCOUNT_ID, "");
        bundle.putString(key, value);
        mFirebaseAnalytics.logEvent(key, bundle);
    }

    private void initSentry() {
        try {
            Context ctx = this.getApplicationContext();

            // Use the Sentry DSN (client key) from the Project Settings page on Sentry
            String sentryDsn = WSConfig.URL_SENTRY_DSN;
            Sentry.init(sentryDsn, new AndroidSentryClientFactory(ctx));

            // Alternatively, if you configured your DSN in a `sentry.properties`
            // file (see the configuration documentation).
            Sentry.init(new AndroidSentryClientFactory(ctx));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView() {
        super.initView();
//        try {
//            mLed = findViewById(R.id.led);
//
//            // Monitors the registration state of our account(s) and update the LED accordingly
//            mCoreListener = new CoreListenerStub() {
//                @Override
//                public void onRegistrationStateChanged(Core core, ProxyConfig cfg, RegistrationState state, String message) {
//                    updateLed(state);
//                }
//            };
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        drawerLayout = findViewById(R.id.drawer_layout);


        layoutNoInternet = findViewById(R.id.layoutNoInternet);
        btnGoOffline = findViewById(R.id.btnGoOffline);
        btnClose = findViewById(R.id.btnClose);
        viewClose = findViewById(R.id.layoutView);

        setClickOffline();

        setDrawerEnabled(false);
        initSlideMenu();
        initViewDrawerLayout();
        try {
            initDrawerLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        switchFragment(getmCurrentMenuTab());
        switchFragment(SlideMenu.MenuType.HOME_SCREEN);
    }

    private void setClickOffline() {
        layoutNoInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutNoInternet.setVisibility(View.GONE);
            }
        });

        btnGoOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(offlineDialog != null){
//                    offlineDialog.dismiss();
//                }
//
//                goToOffline(0);
                if (offlineDynamic == null) {
                    Toast.makeText(MainActivity.this, "Có lỗi, hoặc chưa lấy được dữ  liệu offline", Toast.LENGTH_SHORT).show();
                } else {
                    OfflineDialog offlineDialog = OfflineDialog.newInstance();
                    offlineDialog.show(getSupportFragmentManager(), Constants.TAG_DIALOG);
                }
                layoutNoInternet.setVisibility(View.GONE);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutNoInternet.setVisibility(View.GONE);

            }
        });

        viewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutNoInternet.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void switchFragment(SlideMenu.MenuType screen) {
        super.switchFragment(screen);
        Log.d("LamLV", screen.toString());
        switch (screen) {

            case MAIN_CALL_NOW_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), MainCallNowFragment.class, getBundle(), true);
                break;
            case PAGE_LOGIN_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), MainPageLoginFragment.class, getBundle(), true);
                break;
            case MAIN_OFFLINE_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), MainOfflineFragment.class, getBundle(), true);
                break;
            case SPLASH_SCREEN:
                MyFragment.openFragment(this, getContentFrame(), SplashFragment.class, getBundle(), isAddToBackStack());
                break;
            case HOME_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), HomeFragment.class, getBundle(), isAddToBackStack());
                break;
            case SEARCH_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), SearchFragment.class, getBundle(), true);
                break;
            case NEAR_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), NearYouFragment.class, getBundle(), true);
                break;
            case SHOW_ALL_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), ShowAllFragment.class, getBundle(), true);
                break;
            case DETAIL_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), DetailsFragment.class, getBundle(), true);
                break;
            case CHAT_SCREEN:
                setDrawerEnabled(false);
                // MyFragment.openFragment(this, getContentFrame(), ChatFragment.class, getBundle(), true);
//                MyFragment.openFragment(this, getContentFrame(), ChatFragment.class, getBundle(), true);
                break;
            case LOGIN_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), LoginFragment.class, getBundle(), true);
                break;
            case REGISTER_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), RegisterFragment.class, getBundle(), true);
                break;
            case ACCOUNT_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), AccountFragment.class, getBundle(), true);
                break;
            case WHERE_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), WhereFragment.class, getBundle(), true);
                break;
            case FOOD_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), FoodFragment.class, getBundle(), true);
                break;
            case LIVING_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), StayFragment.class, getBundle(), true);
                break;
            case PLAY_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), WhatPlayFragment.class, getBundle(), true);
                break;
            case DETAIL_WHERE_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), DetailWhereFragment.class, getBundle(), true);
                break;
            case DETAIL_WHERE_STAY_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), DetailWhereStayFragment.class, getBundle(), true);
                break;
            case DETAIL_EAT_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), DetailWhatEatFragment.class, getBundle(), true);
                break;
            case OTP_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), OtpFragment.class, getBundle(), true);
                break;
            case RESET_PASS_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), ForgotPassFragment.class, getBundle(), true);
                break;
            case SET_PASS_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), SetPassFragment.class, getBundle(), true);
                break;
            case UPDATE_INFO_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), UpdateInfoFragment.class, getBundle(), true);
                break;
            case ENCODE_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), EncodeDemoFragment.class, getBundle(), true);
                break;
            case NOTIFY_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), NotifyFragment.class, getBundle(), true);
                break;
            case LIST_SUGGEST_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), ListSuggestFragment.class, getBundle(), true);
                break;
            case SCHEDULE_TRAVEL_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), CreateScheduleFragment.class, getBundle(), true);
                break;
            case SCHEDULE_PREVIEW_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), ScheduleShoppingFragment.class, getBundle(), true);
                break;
            case SCHEDULE_FINISH_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), SchedulePlayFragment.class, getBundle(), true);
                break;
            case SETTING_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), SettingFragment.class, getBundle(), true);
                break;
            case LIST_WHERE_GO_NEWS_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), ListNewsHighlightFragment.class, getBundle(), true);
                break;
            case DETAIL_WHERE_GO_NEWS_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), DetailNewsHighlightFragment.class, getBundle(), true);
                break;
            case CHANGE_PASSWORD:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), ChangePassFragment.class, getBundle(), true);
                break;
            case NEAR_PLACE_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), NearPlaceFragment.class, getBundle(), true);
                break;
            case BEAUTIFUL_PHOTO_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), PhotoNiceFragment.class, getBundle(), true);
                break;
            case PLAYLIST_VIDEO_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), PlaylistVideoJwplayerFragment.class, getBundle(), true);
                break;
            case VOUCHER_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), VoucherFragment.class, getBundle(), true);
                break;
            case DETAIL_SCHEDULE_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), DetailScheduleTravelFragment.class, getBundle(), true);
                break;
            case DETAIL_SCHEDULE_SCREEN_MINE:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), DetailScheduleTravelFragmentMine.class, getBundle(), true);
                break;
            case RESULT_SEARCH_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), SearchResultFragment.class, getBundle(), true);
                break;
            case DETAIL_SCHEDULE_CREATE_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), DetailScheduleCreateFragment.class, getBundle(), true);
                break;
            case DETAIL_MOMENT_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), DetailMomentFrangment.class, getBundle(), true);
                break;
            case PHOTO_GALLERY_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), PhotoGalleryFragment.class, getBundle(), true);
                break;
            case SHARE_MOMENT_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), ShareMomentFragment.class, getBundle(), true);
                break;
            case DETAIL_CHANNEL_LIVE_SCREEN:
                setDrawerEnabled(false);
                MyFragment.openFragment(this, getContentFrame(), DetailLiveChannelFragment.class, getBundle(), true);
                break;
            case CHANNEL_LIVE_SCREEN:
                MyFragment.openFragment(this, getContentFrame(), LiveChannelFragment.class, getBundle(), true);
                break;
            case WEBVIEW_SCREEN:
                MyFragment.openFragment(this, getContentFrame(), WebviewFragment.class, getBundle(), true);
                break;
            case MAP_CHAT_SCREEN:
                MyFragment.openFragment(this, getContentFrame(), MapChatFragment.class, getBundle(), true);
                break;
            case FORM_CHAT_SCREEN:
                MyFragment.openFragment(this, getContentFrame(), FormChatFragment.class, getBundle(), true);
                break;
            case FORM_SUCCESS_SCREEN:
                MyFragment.openFragment(this, getContentFrame(), FormSuccessFragment.class, getBundle(), true);
                break;
            case REGISTER_SUCCESS_SCREEN:
                MyFragment.openFragment(this, getContentFrame(), RegisterSuccessFragment.class, getBundle(), true);
                break;

            case REGISTER_SUCCESS_FRIEND_SCREEN:
                MyFragment.openFragment(this, getContentFrame(), RegisterSuccessFriendFragment.class, getBundle(), true);
                break;
            default:
                setDrawerEnabled(false);
                mFragment = new HomeFragment();
                break;
        }
        try {
//            KeyboardUtils.hideKeyboard(this, this.getCurrentFocus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    private boolean backPressOnce = false;

    @Override
    public void onBackPressed() {
        if (layoutNoInternet.getVisibility() == View.VISIBLE) {
            layoutNoInternet.setVisibility(View.GONE);
            return;
        }

        FragmentManager faFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = faFragmentManager.beginTransaction();
        ft.setCustomAnimations(com.baseapp.R.anim.slide_in_left, com.baseapp.R.anim.slide_out_right);
        if (onBackPress != null) {
            onBackPress.onBackPressFromActivity();
            onBackPress = null;
        } else {
            if (faFragmentManager.getBackStackEntryCount() > 0) {
                faFragmentManager.popBackStack();
            } else {
//                if (backPressOnce) {
                super.onBackPressed();
//                } else {
//                    backPressOnce = true;
//                    Toast.makeText(this, getString(R.string.press_one_more_to_exit), Toast.LENGTH_SHORT).show();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            backPressOnce = false;
//                        }
//                    }, 2000);
//                    return;
//                }
            }
            if (faFragmentManager.getBackStackEntryCount() == 1) {
                setDrawerEnabled(false);
            }
            setBundle(null);
        }
        try {
//            KeyboardUtils.hideKeyboard(this, this.getCurrentFocus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void displayNeverAskAgainDialog() {
        try {
            if (permissionDialog == null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Ứng dụng cần quyền truy cập vào vị trí để các tính năng có thể hoạt động một cách đúng nhất.");
                builder.setCancelable(false);
                builder.setPositiveButton("Đi đến cài đặt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Intent intent = new Intent();
                            intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                permissionDialog = builder.create();
            }
            permissionDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (grantResult == PackageManager.PERMISSION_DENIED){
                        searchViewModel.loadSearchTrend();
                        boolean showRationale = shouldShowRequestPermissionRationale( permission );
                        if (!showRationale) {
                            displayNeverAskAgainDialog();
                        } else if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permission) || Manifest.permission.ACCESS_COARSE_LOCATION.equals(permission)) {
                            getLocation();
                        }
                    }
                }
                break;

            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts(this);
                } else {
                    Toast.makeText(this, "Bạn cần cấp quyền truy cập danh bạ để ứng dụng có thể lấy danh sách liên hệ", Toast.LENGTH_LONG).show();
                }
                break;
        }
        try {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLocation() {
        MyTracker tracker = new MyTracker(this, new MyTracker.ADLocationListener() {
            @Override
            public void whereIAM(ADLocation loc) {
                try {
                    PreferenceUtil.getInstance(MainActivity.this).setValue(Constants.PrefKey.LAT_LOCATION, "" + loc.getLat());
                    PreferenceUtil.getInstance(MainActivity.this).setValue(Constants.PrefKey.LNG_LOCATION, "" + loc.getLng());

                    MyApplication.getInstance().setMyLocation(new MyLocation(loc.getCity(), loc.getAddress(), loc.getCountry(), loc.getLat(), loc.getLng()));
                    BaseViewModel baseViewModel = new BaseViewModel();
                    baseViewModel.trackLocation(loc.getLat(), loc.getLng(), DeviceUtils.getDeviceId(MainActivity.this));
                    if (!ServiceUltils.isMyServiceRunning(MainActivity.this, TrackLocationService.class)) {
                        Intent intent = new Intent(MainActivity.this, TrackLocationService.class);
                        startService(intent);
                    }
                    EventBus.getDefault().post(new OnGetLocation());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        tracker.track();
//        searchViewModel.loadSearchTrend();
    }

    public void call(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage("" + message)
                .setPositiveButton(R.string.call, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String phone = "1039";
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.dimiss, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onStop() {
//        Toast.makeText(this, "onStop", Toast.LENGTH_LONG).show();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        Toast.makeText(this, "onDestroy", Toast.LENGTH_LONG).show();
        unregisterReceiver(connectivityReceiver);
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.activityResumed();


//        try {
//            LinphoneService.getCore().addListener(mCoreListener);
//
//            ProxyConfig proxyConfig = LinphoneService.getCore().getDefaultProxyConfig();
//            if (proxyConfig != null) {
//                updateLed(proxyConfig.getState());
//            } else {
//                // No account configured, we display the configuration activity
//                startActivity(new Intent(this, ConfigureAccountActivity.class));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        startActivity(new Intent(this, ConfigCallPISIP.class));


    }

//    private void updateLed(RegistrationState state) {
//        try {
//            switch (state) {
//                case Ok: // This state means you are connected, to can make and receive calls & messages
//                    mLed.setImageResource(R.drawable.led_connected);
//                    break;
//                case None: // This state is the default state
//                case Cleared: // This state is when you disconnected
//                    mLed.setImageResource(R.drawable.led_disconnected);
//                    break;
//                case Failed: // This one means an error happened, for example a bad password
//                    mLed.setImageResource(R.drawable.led_error);
//                    break;
//                case Progress: // Connection is in progress, next state will be either Ok or Failed
//                    mLed.setImageResource(R.drawable.f2_ic_x);
//                    break;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.activityPaused();
//        try {
//            LinphoneService.getCore().removeListener(mCoreListener);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected int getContentFrame() {
        return R.id.frame;
    }

    public void setDrawerEnabled(boolean enabled) {
        if (enabled) {
            MainActivity.this.pushEvent(FirebaseAnalytic.CLICK_MENU);
        }
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                drawerLayout.setDrawerLockMode(lockMode);
                // Stuff that updates the UI

            }
        });

    }

    public void openAndCloseDrawer() {
        if (null != drawerLayout && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            MainActivity.this.pushEvent(FirebaseAnalytic.CLICK_MENU);
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void initSlideMenu() {
//        menuFragment = SlideMenuFragment.newInstance(getMenuLeft());
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fr_slide_menu, menuFragment).commit();
    }

    private void getKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }


    public void customBack() {
        getFragmentManager().popBackStackImmediate();
    }

    public void updateLogin() {
        if (menuFragment != null) {
            menuFragment.updateViews();
        }
        updateViewsDrawerLayout();
        searchViewModel.loadSearchTrend();
        Intent intent = new Intent(Constants.KeyBroadcast.KEY_LOGIN_SCREEN);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    public void updateViewsDrawerLayout() {
        account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            mLlLogin.setVisibility(View.GONE);
            mLlInfor.setVisibility(View.VISIBLE);

            mTvName.setText(account.getFullname());
            if (null != account.getFullname()) {
                mTvName.setVisibility(View.VISIBLE);
                mTvName.setText(account.getFullname());
            } else {
                mTvName.setVisibility(View.GONE);

                if (null != account.getMobile()) {
                    mTvPhone.setVisibility(View.VISIBLE);
                    mTvPhone.setText("0" + account.getMobile().substring(2));
                } else {
                    mTvPhone.setVisibility(View.GONE);
                }
            }

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.user);
            Glide.with(this).setDefaultRequestOptions(requestOptions).load(account.getImageProfile()).into(mAvatar);
            if (null != account.getFacebookId() && account.getFacebookId().length() > 0) {
                mProfileAvatar.setVisibility(View.VISIBLE);
                mProfileAvatar.setProfileId(account.getFacebookId());
            }
        } else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.user);
            Glide.with(this).setDefaultRequestOptions(requestOptions).load("").into(mAvatar);
            mLlLogin.setVisibility(View.VISIBLE);
            mLlInfor.setVisibility(View.GONE);
        }
        mTvTemp.setOnClickListener(this);
        mTvHumidity.setOnClickListener(this);


    }

    public void updateListMenu() {
        if (menuFragment != null) {
            menuFragment.setDataSlideMenu(getMenuLeft().getLeft());
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    slideMenuAdapter = new SlideMenuAdapter(MainActivity.this, getMenuLeft().getLeft());
                    mRvSlideMenu.setAdapter(slideMenuAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void tabSlideMenuClick(int position) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(getContentFrame());
        if (fragment instanceof HomeFragment) {
            HomeFragment listVideoFragment = (HomeFragment) fragment;
            if (position == 0) {
                listVideoFragment.onHotClick(position);
            } else if (position == 1) {
                listVideoFragment.onSuggestClick(position);
            } else if (position == 2) {
                listVideoFragment.onMomentClick(position);
            } else if (position == 3) {
                listVideoFragment.onVideoClick(position);
            }

            openAndCloseDrawer();
        }
    }

    public void setListSearchTrend(List<Object> listSearchTrend) {
        this.listSearchTrend = listSearchTrend;
    }

    public List<Object> getListSearchTrend() {
        return listSearchTrend;
    }

    public ArrayList<ItemWeather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(ArrayList<ItemWeather> weatherList) {
        this.weatherList = weatherList;
    }

    @Override
    public void update(Observable observable, final Object o) {
        if (observable instanceof SearchViewModel) {
            if (o != null) {
                if (o instanceof SearchResponse) {
                    SearchResponse response = (SearchResponse) o;
                    setListSearchTrend(response.getData());
                } else if (o instanceof ResponseError) {
                    ResponseError responseError = (ResponseError) o;
                    showMessage(responseError.getMessage());
                } else if (o instanceof ZipVersionResponse) {
                    ZipVersionResponse zipVersionResponse = (ZipVersionResponse) o;
                    zipVersionResponse.getData();
                    if (zipVersionResponse != null && zipVersionResponse.getData() != null) {
                        String oldVersion = PreferenceUtil.getInstance(this).getValue(Constants.PrefKey.VERSION, "");
                        if (!oldVersion.equals(zipVersionResponse.getData())) {
                            downloadFile(zipVersionResponse.getData());
                        } else {
                            getJson();
                        }
                    }
                }
            } else {
            }
        }
        if (observable instanceof PlaceViewModel) {
//            if (o instanceof WeatherResponse) {
//                WeatherResponse response = (WeatherResponse) o;
//                weatherList = response.getData();
//                setWeather(response.getData().get(0));
//            } else if (o instanceof CityResponse) {
//                CityResponse response = (CityResponse) o;
//                cityList = response.getData();
//                MyApplication.getInstance().setCityList(response.getData());
//            }
        }

        if (observable instanceof SystemInboxViewModel && null != o) {
            if (o instanceof ConfirmEnterTrip) {
                ReceiveInviteTripDetailActivity.startScreen(this, dataSystemInbox.getScheduleCustomId());
            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
                } catch (Exception e) {
                }
            }
        }
    }

    public void showMessage(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage("" + message)
                .setPositiveButton(R.string.call, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void showMessageNotify(ItemNotify notify) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage("" + notify.getContent())
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(R.string.call, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Bundle bundle = new Bundle();
                        if (notify.getContent_type().equals(Constants.TypePlace.places)) {
                            Travel travel = new Travel();
                            travel.setDetail_link(notify.getContent_link());
                            travel.setId(notify.getContent_id());
                            bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
                            setBundle(bundle);
                            switchFragment(SlideMenu.MenuType.DETAIL_WHERE_SCREEN);
                        } else if (notify.getContent_type().equals(Constants.TypePlace.restaurants)) {
                            Travel travel = new Travel();
                            travel.setDetail_link(notify.getContent_link());
                            travel.setId(notify.getContent_id());
                            bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
                            setBundle(bundle);
                            switchFragment(SlideMenu.MenuType.DETAIL_EAT_SCREEN);
                        } else if (notify.getContent_type().equals(Constants.TypePlace.hotels)) {
                            Travel travel = new Travel();
                            travel.setDetail_link(notify.getContent_link());
                            travel.setId(notify.getContent_id());
                            bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
                            setBundle(bundle);
                            switchFragment(SlideMenu.MenuType.DETAIL_WHERE_STAY_SCREEN);
                        } else if (notify.getContent_type().equals(Constants.TypePlace.centers)) {
                            Travel travel = new Travel();
                            travel.setDetail_link(notify.getContent_link());
                            travel.setId(notify.getContent_id());
                            bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
                            bundle.putString(Constants.IntentKey.KEY_TYPE, getString(R.string.tv_play));
                            setBundle(bundle);
                            switchFragment(SlideMenu.MenuType.DETAIL_WHERE_SCREEN);
                        } else if ((null != notify.getType() && notify.getType().equals(Constants.TypePlace.post)) ||
                                (null != notify.getContent_type() && notify.getContent_type().equals(Constants.TypePlace.sponsored_posts))) {
                            News news = new News(notify.getContent_id(), notify.getContent_link(), notify.getContent_type());
                            bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, news);
                            setBundle(bundle);
                            switchFragment(SlideMenu.MenuType.DETAIL_WHERE_GO_NEWS_SCREEN);
                        } else if (null != notify.getType() && notify.getType().equals(Constants.TypePlace.link)) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(notify.getContent_link()));
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.KeyBroadcast.KEY_SAVE_LOGIN_SCREEN)) {
                updateLogin();
            } else if (intent.getAction().equals(Constants.KeyBroadcast.KEY_NOTIFY)) {
                ItemNotify itemNotify = intent.getParcelableExtra(Constants.IntentKey.KEY_NOTIFY);
                if (null != itemNotify) {
                    if (itemNotify.isLinked()) {
                        showMessageNotify(itemNotify);
                    } else {
                        showMessage(itemNotify.getContent());
                    }
                }
            }
        }
    };

    public void createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                MyApplication.getInstance().getMyLocation();
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MainActivity.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<ChatBase> mListChat;

    public List<ChatBase> getmListChat() {
        return mListChat;
    }

    public void setmListChat(List<ChatBase> mListChat) {
        this.mListChat = mListChat;
    }

    public void setOnBackPress(OnBackPress onBackPress) {
        this.onBackPress = onBackPress;
    }

    private OfflineDialog offlineDialog;

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected) {
            if (!F2Util.isOnline(this)) {
                if (fromNotification) {
                    fromNotification = false;
                } else {
                    sendNotificationMessage();
                }
                layoutNoInternet.setVisibility(View.VISIBLE);

//                Fragment prev = getSupportFragmentManager().findFragmentByTag(Constants.TAG_DIALOG);
//                if (prev != null) {
//                    OfflineDialog df = (OfflineDialog) prev;
//                    df.dismiss();
//
//                    OfflineDialog offlineDialog = OfflineDialog.newInstance();
//                    offlineDialog.show(getSupportFragmentManager(), Constants.TAG_DIALOG);
//                }else {
//                    OfflineDialog offlineDialog = OfflineDialog.newInstance();
//                    offlineDialog.show(getSupportFragmentManager(), Constants.TAG_DIALOG);
//                }
            }
        } else {
            layoutNoInternet.setVisibility(View.GONE);
            try {
                searchViewModel.zipVersion();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnBackPress {
        void onBackPressFromActivity();
    }

    private ConnectivityReceiver connectivityReceiver;

    private void registerNetWork() {
        connectivityReceiver = new ConnectivityReceiver();
        connectivityReceiver.setConnectivityReceiverListener(this);
        registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }


    private void sendNotificationMessage() {
        boolean b = PreferenceUtil.getInstance(this).getValue(Constants.PrefKey.NOTI_ENABLE, true);
        if (!b) {
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.IntentKey.FROM_DISCONNECT_NOTI, true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "Offline Channel";
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_app)
                        .setContentTitle("Oops! Mất mạng rồi")
                        .setContentText("Trải nghiệm ngay các tính năng đang có của VTV Travel để nhận nhiều ưu đãi:")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Trải nghiệm ngay các tính năng đang có của VTV Travel để nhận nhiều ưu đãi: " +
                                        "\n - Gọi thoại giá rẻ (200đ/phút)." +
                                        "\n - Săn Deal giành ngàn quà tặng." +
                                        "\n - Đăng ký dịch vụ, trải nghiệm mọi tính năng." +
                                        "\n - Thông tin du lịch qua tổng đài 1039." +
                                        "\nTrải nghiệm ngay..."))
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Offline channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }


        notificationManager.notify(0, notificationBuilder.build());
    }

    @Subscribe
    public void OnLoadFail(OnLoadFail onLoadFail) {
        if (!F2Util.isOnline(this)) {
//            layoutNoInternet.setVisibility(View.VISIBLE);

        }
    }


    @Subscribe
    public void OnUpdateLogin(OnUpdateLogin onUpdateLogin) {
        updateLogin();
    }


    public void showDialogNoCon() {
        try {
            if (!F2Util.isOnline(this)) {
                layoutNoInternet.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToOffline(int position) {
        if (layoutNoInternet.getVisibility() == View.VISIBLE) {
            layoutNoInternet.setVisibility(View.GONE);
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.IntentKey.KEY_POSITION, position);
        setBundle(bundle);
        switchFragment(SlideMenu.MenuType.MAIN_OFFLINE_SCREEN);
    }


    public void openNewLogin(int position) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.IntentKey.KEY_POSITION, position);
                setBundle(bundle);
                switchFragment(SlideMenu.MenuType.PAGE_LOGIN_SCREEN);
            }
        }, 500);

    }


    private DataSystemInbox dataSystemInbox;

    private void getDataFromIntent() {
        fromNotification = getIntent().getBooleanExtra(Constants.IntentKey.FROM_DISCONNECT_NOTI, false);
        if (F2Util.isOnline(this)) {
            fromNotification = false;
        }

        fromVIPNoti = getIntent().getBooleanExtra(Constants.IntentKey.FROM_VIP_NOTI, false);

        if (fromVIPNoti) {
            TravelVoucherActivity.openScreen(MainActivity.this, true, TravelVoucherActivity.OpenType.LIST, true);
        }


        String notificationType = getIntent().getStringExtra(Constants.IntentKey.NOTIFICATION_TYPE);
        Notification notification = (Notification) getIntent().getSerializableExtra(Constants.IntentKey.NOTIFICATION);
        handleIntentFromNotification(notificationType,notification);


//
//        switch (notificationType) {
//            case NotificationType.INVITE_TRIP:
//                Notification notification = (Notification) getIntent().getSerializableExtra(Constants.IntentKey.NOTIFICATION);
//                dataSystemInbox = notification.getDataSystemInbox();
//                openDialogTripInvite();
//                break;
//            case NotificationType.VIP:
//                SystemInboxActivity.startScreen(this);
//                break;
//            case NotificationType.SHARE:
//                SystemInboxActivity.startScreen(this);
//                break;
//            case NotificationType.TICKET:
//                SystemInboxActivity.startScreen(this);
//                break;
//        }

        handleLinkShare();
        handleLinkShareFromIntent();


    }


    //xử lý dữ liệu lấy từ share được mở từ deeplink
    private void handleLinkShare() {
        String cateId = "news";
        String link = "";
        Uri data = getIntent().getData();
        if (data == null) {
            return;
        }

        Map<String, String> mapQuery = null;
        try {
            mapQuery = splitQuery(data);
            cateId = mapQuery.get("cateId");
            link = mapQuery.get("link");


            switch (cateId) {
                case Constants.ShareLinkType.IMAGES:
                    ImagePartActivity.startScreen(this, ImagePartActivity.Type.DETAIL, link);
                    break;
                case Constants.ShareLinkType.NEWS:
                    TravelNewsActivity.openScreenDetail(this, TravelNewsActivity.OpenType.DETAIL, link);
                    break;
                case Constants.ShareLinkType.VIDEO:
                    DetailVideoActivity.startScreen(this, link);
                    break;
                case Constants.ShareLinkType.PLACE:
                case Constants.ShareLinkType.CENTERS:
                case Constants.ShareLinkType.RESTAURANTS:
                case Constants.ShareLinkType.HOTELS:
                    SmallLocationActivity.startScreenDetail(this, SmallLocationActivity.OpenType.DETAIL, link);
                    break;

            }

        } catch (Exception e) {

        }
    }


    // xử lý dữ liệu lấy từ share được mở từ wap
    private void handleLinkShareFromIntent() {
        String cateId = "news";
        String link = "";
        cateId = getIntent().getStringExtra("cateId");
        link = getIntent().getStringExtra("link");
        if (cateId == null) {
            return;
        }

        try {

            switch (cateId) {
                case Constants.ShareLinkType.IMAGES:
                    ImagePartActivity.startScreen(this, ImagePartActivity.Type.DETAIL, link);
                    break;
                case Constants.ShareLinkType.NEWS:
                    TravelNewsActivity.openScreenDetail(this, TravelNewsActivity.OpenType.DETAIL, link);
                    break;
                case Constants.ShareLinkType.VIDEO:
                    DetailVideoActivity.startScreen(this, link);
                    break;
                case Constants.ShareLinkType.PLACE:
                case Constants.ShareLinkType.CENTERS:
                case Constants.ShareLinkType.RESTAURANTS:
                case Constants.ShareLinkType.HOTELS:
                    SmallLocationActivity.startScreenDetail(this, SmallLocationActivity.OpenType.DETAIL, link);
                    break;

            }

        } catch (Exception e) {

        }
    }


    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            getContacts(this);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public List<Contact> getContacts(Context ctx) {
        listContact = getContactFromDB();
        for (int i = 0; i < listContact.size(); i++) {
            try {
                contactHashMap.put(listContact.get(i).getPhones().get(0), listContact.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<Contact> list = new ArrayList<>();
//        int contactMaxId = PreferenceUtil.getInstance(this).getValue(Constants.PrefKey.CONTACT_MAX_ID, 0);
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.e("MainActivity", "Lấy danh bạ thành công");
                EventBus.getDefault().post(new OnLoadContactSuccess());

            }

            @Override
            protected Void doInBackground(Void... voids) {

                try {

                    ContentResolver contentResolver = ctx.getContentResolver();
                    Cursor cursor;
//                    if (contactMaxId == 0) {
                    cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//                    } else {
//                        cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.Contacts._ID + " > ?", new String[]{String.valueOf(contactMaxId)}, null);
//                    }

                    if (cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
                            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                            if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                                Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                                InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(ctx.getContentResolver(),
                                        ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));

                                Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id));
                                Uri pURI = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

                                Bitmap photo = null;
                                if (inputStream != null) {
                                    photo = BitmapFactory.decodeStream(inputStream);
                                }
                                while (cursorInfo.moveToNext()) {
                                    Contact contact = new Contact();

                                    //                        contact.setId(Integer.parseInt(id));
                                    contact.setId(0);
                                    contact.setContactClientId(Integer.parseInt(id));
                                    contact.setContactName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                                    //                        contact.setEmail(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
                                    //                        contact.setEmail("ahihi@mail.com");

                                    String phone = cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replace(" ", "");
                                    List<String> phones = new ArrayList<>();
                                    //                        phones.add(phone);
                                    phones.add(ValidateUtils.isPhoneValidateV2(phone, 84));
                                    contact.setPhones(phones);
                                    contact.setPhoneToShow(phone);
//                                    contact.setPhoto(photo);
                                    list.add(contact);
                                }
                                cursorInfo.close();
                            }
                        }
                        cursor.close();
                    }
                    Log.d("LamLV contact", list.size() + "");
//                    MyApplication.getAppDatabase().foodDao().insertContact(list);
//                    if (list.size() != 0) {
//                        PreferenceUtil.getInstance(MainActivity.this).setValue(Constants.PrefKey.CONTACT_MAX_ID, list.get(list.size() - 1).getContactClientId());
//                    }

//                    listContact = (ArrayList<Contact>) MyApplication.getAppDatabase().foodDao().getAllContact();
                    listContact.clear();
                    listContact.addAll(list);
                    addContactToDB(listContact);

                    contactHashMap.clear();
                    for (int i = 0; i < listContact.size(); i++) {
                        try {
                            contactHashMap.put(listContact.get(i).getPhones().get(0), listContact.get(i));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {

                }
                return null;
            }


        }.execute();

        return list;
    }


    private void downloadFile(String hash) {
        Log.e("Download file", "ok");
        DownloadAPI apiInterface = ServiceGenerator.createService(DownloadAPI.class);

        Call<ResponseBody> call = apiInterface.downloadFileWithFixedUrl();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.e("Download file", "ok");
//                    try {
                    boolean writtenToDisk = writeResponseBodyToDisk(response.body());

                    String file_path = getExternalFilesDir(null) + File.separator + "/VTVTravelData";
                    F2UnzipUtil f2UnzipUtil = new F2UnzipUtil(getExternalFilesDir(null) + File.separator + "Call_Now.zip", file_path);
                    f2UnzipUtil.unzip();

                    File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + "/VTVTravelDataCall_Now/Call_Now.json");
                    try {
                        offlineDynamic = new Gson().fromJson(readJson(futureStudioIconFile), OfflineDynamic.class);

                        PreferenceUtil.getInstance(MainActivity.this).setValue(Constants.PrefKey.VERSION, hash);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Định dạng dữ liệu offline lỗi", Toast.LENGTH_SHORT).show();
                    }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Download file", "fail");
            }
        });
    }

    private void getJson() {
        try {
            File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + "/VTVTravelDataCall_Now/Call_Now.json");
            offlineDynamic = new Gson().fromJson(readJson(futureStudioIconFile), OfflineDynamic.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    public OfflineDynamic offlineDynamic;


    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + "Call_Now.zip");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("File Download: ", fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private String readJson(File file) {
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();

            Log.e("jsonnnnnnnnn", text.toString());
            return text.toString();
        } catch (Exception e) {
            return text.toString();
            //You'll need to add proper error handling here
        }
    }


    private void addContactToDB(List<Contact> contacts) {
        try {
            List<Contact> contactList = new ArrayList<>();
            contactList.addAll(contacts);
            PreferenceUtil.getInstance(this).setValue(Constants.PrefKey.CONTACT, new Gson().toJson(contactList));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private List<Contact> getContactFromDB() {
        try {

            List<Contact> contacts;
            String json = PreferenceUtil.getInstance(this).getValue(Constants.PrefKey.CONTACT, "");
            if (json.isEmpty()) {
                contacts = new ArrayList<>();
            } else {
                contacts = new Gson().fromJson(json,
                        new TypeToken<ArrayList<Contact>>() {
                        }.getType());
            }
            return contacts;
        } catch (Exception e) {
            e.printStackTrace();

            return new ArrayList<>();
        }
    }


    @Subscribe
    public void OnReceiveNotiVip(OnReceiveNotiVip onReceiveNotiVip) {
        NotifyDialog notifyDialog = NotifyDialog.newInstance("Thông báo", "Bạn đã đăng ký VIP Thành công, mời bạn nhận thưởng", "Đồng ý", new NotifyDialog.ClickButton() {
            @Override
            public void onClickButton() {
                TravelVoucherActivity.openScreen(MainActivity.this, true, TravelVoucherActivity.OpenType.LIST, true);
            }
        });
        notifyDialog.show(getSupportFragmentManager(), null);
    }


    private void openDialogTripInvite() {
        ReceiverTripInviteDialog notifiDialog = ReceiverTripInviteDialog.newInstance(dataSystemInbox.getTitle(), dataSystemInbox.getContent(), "Đồng ý tham gia", new ReceiverTripInviteDialog.ClickButton() {
            @Override
            public void onClickButton() {
                try {

                    Account account = MyApplication.getInstance().getAccount();
                    if (null != account && account.isLogin()) {
                        systemInboxViewModel.confirmEnterTrip(dataSystemInbox.getScheduleCustomId(), String.valueOf(account.getId()));
                    } else {
                        LoginAndRegisterActivityNew.startScreen(MainActivity.this, 0, false);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        notifiDialog.show(getSupportFragmentManager(), null);
    }


    public Map<String, String> splitQuery(Uri url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<>();
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            if (idx != -1) {
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
        }
        return query_pairs;
    }

    private void getLocationFromCache(){
        try {
            String defLat = PreferenceUtil.getInstance(this).getValue(Constants.PrefKey.LAT_LOCATION, "");
            String defLong = PreferenceUtil.getInstance(this).getValue(Constants.PrefKey.LNG_LOCATION, "");
            if(!defLat.isEmpty()){
                MyApplication.getInstance().setMyLocation(new MyLocation("", "", "", Double.parseDouble(defLat), Double.parseDouble(defLong)));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void handleIntentFromNotification(String code,Notification notification){
        try {
            Account account = MyApplication.getInstance().getAccount();
            switch (code){
                case NotificationCode.WHEEL_TIME_OUT :
                case NotificationCode.SUBSCRIBE :
                    if (null != account && account.isLogin()) {
                        VQMMWebviewActivity.startScreen(this, "");
                    } else {
                        LoginAndRegisterActivityNew.startScreen(this, 0, false);
                    }
                    break;
                case NotificationCode.UN_SUBSCRIBE :

                    break;
                case NotificationCode.MAINTENANCE :

                    break;

                case NotificationCode.UPDATE :
                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                    break;
                case NotificationCode.LIVE_TV :
                    LiveTVActivity.openScreen(this, 0, "");
                    break;
                case NotificationCode.ADD_TO_CART :
                case NotificationCode.TIME_OUT :
                case NotificationCode.CODE_CANCEL :
                case NotificationCode.BOOKING_SUCCESS :
                case NotificationCode.FLY_TIME :
                    if (null != account && account.isLogin()) {
                        WebviewActivity.startScreen(this);
                    } else {
                        LoginAndRegisterActivityNew.startScreen(this, 0, false);
                    }
                    break;
                case NotificationCode.HUNT_DEAL_SUCCESS :
                case NotificationCode.DEAL_LOSS :
                case NotificationCode.DEAL_WIN :
                case NotificationCode.HOT_DEAL :
                    try {
                        DetailDealWebviewActivity.startScreen(this, notification.getData().getDealDetailLink());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case NotificationCode.WIN_WHEEL :

                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        String notificationType = getIntent().getStringExtra(Constants.IntentKey.NOTIFICATION_TYPE);
        Notification notification = (Notification) getIntent().getSerializableExtra(Constants.IntentKey.NOTIFICATION);
        handleIntentFromNotification(notificationType,notification);
//        switch (notificationType) {
//            case NotificationType.INVITE_TRIP:
//                Notification notification = (Notification) getIntent().getSerializableExtra(Constants.IntentKey.NOTIFICATION);
//                dataSystemInbox = notification.getDataSystemInbox();
//                openDialogTripInvite();
//                break;
//            case NotificationType.VIP:
//                SystemInboxActivity.startScreen(this);
//                break;
//            case NotificationType.SHARE:
//                SystemInboxActivity.startScreen(this);
//                break;
//            case NotificationType.TICKET:
//                SystemInboxActivity.startScreen(this);
//                break;
//        }






//
        handleLinkShare();
        handleLinkShareFromIntent();
    }


    // for pjsip

//    private void registerSIP(){
//        String acc_id 	 = "sip:1001@171.244.52.28:5060";
//        String registrar = "sip:171.244.52.28:5060";
//        String proxy 	 = "<sip:171.244.52.28:5060;transport=tcp>";
//        String username  = "1001";
//        String password  = "Namviet@2020!@#";
//    }
//
//    public void notifyChangeNetwork()
//    {
//        Message m = Message.obtain(handler, MSG_TYPE.CHANGE_NETWORK, null);
//        m.sendToTarget();
//    }
//
//    private class MyBroadcastReceiver extends BroadcastReceiver {
//        private String conn_name = "";
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (isNetworkChange(context))
//                notifyChangeNetwork();
//        }
//
//        private boolean isNetworkChange(Context context) {
//            boolean network_changed = false;
//            ConnectivityManager connectivity_mgr =
//                    ((ConnectivityManager)context.getSystemService(
//                            Context.CONNECTIVITY_SERVICE));
//
//            NetworkInfo net_info = connectivity_mgr.getActiveNetworkInfo();
//            if(net_info != null && net_info.isConnectedOrConnecting() &&
//                    !conn_name.equalsIgnoreCase(""))
//            {
//                String new_con = net_info.getExtraInfo();
//                if (new_con != null && !new_con.equalsIgnoreCase(conn_name))
//                    network_changed = true;
//
//                conn_name = (new_con == null)?"":new_con;
//            } else {
//                if (conn_name.equalsIgnoreCase(""))
//                    conn_name = net_info.getExtraInfo();
//            }
//            return network_changed;
//        }
//    }
}
