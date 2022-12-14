package com.namviet.vtvtravel.view.fragment.newhome;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.newhome.NewHomeAdapter;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.SubSmallHeaderAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentHomeBinding;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.MyLocation;
import com.namviet.vtvtravel.model.f2event.OnClickTab;
import com.namviet.vtvtravel.model.f2event.OnGetLocation;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndUpdateUserView;
import com.namviet.vtvtravel.model.f2event.OnReloadCountSystemInbox;
import com.namviet.vtvtravel.model.f2event.OnUpdateAccount;
import com.namviet.vtvtravel.model.newhome.ItemHomeService;
import com.namviet.vtvtravel.response.f2livetv.LiveTvResponse;
import com.namviet.vtvtravel.response.f2systeminbox.CountSystemInbox;
import com.namviet.vtvtravel.response.newhome.AppFavoriteDestinationResponse;
import com.namviet.vtvtravel.response.newhome.AppPromotionPartnerResponse;
import com.namviet.vtvtravel.response.newhome.AppVideoResponse;
import com.namviet.vtvtravel.response.newhome.AppVoucherResponse;
import com.namviet.vtvtravel.response.newhome.BaseResponseNewHome;
import com.namviet.vtvtravel.response.newhome.BaseResponseSecondNewHome;
import com.namviet.vtvtravel.response.newhome.BaseResponseSpecialNewHome;
import com.namviet.vtvtravel.response.newhome.ConfigPopupResponse;
import com.namviet.vtvtravel.response.newhome.HomeServiceResponse;
import com.namviet.vtvtravel.response.newhome.ItemAppDiscoverResponse;
import com.namviet.vtvtravel.response.newhome.ItemAppExperienceResponse;
import com.namviet.vtvtravel.response.newhome.ItemAppVoucherNowResponse;
import com.namviet.vtvtravel.response.newhome.MobileFromViettelResponse;
import com.namviet.vtvtravel.response.newhome.SettingResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.DeviceUtils;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.view.f2.BigLocationActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.SearchActivity;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;
import com.namviet.vtvtravel.view.f2.SystemInboxActivity;

import com.namviet.vtvtravel.view.f2.UserInformationActivity;
import com.namviet.vtvtravel.view.f3.notification.view.NotificationActivity;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceActivity;
import com.namviet.vtvtravel.view.fragment.f2webview.HomeSpeedyLinearLayoutManager;
import com.namviet.vtvtravel.viewmodel.newhome.ChangeRegionDialog;
import com.namviet.vtvtravel.viewmodel.newhome.NewHomeViewModel;
import com.namviet.vtvtravel.widget.PreCachingLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Observable;
import java.util.Observer;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class NewHomeFragment extends MainFragment implements Observer, NewHomeAdapter.LoadData, NewHomeAdapter.ClickUserView, NewHomeAdapter.ClickButtonRegisterNow, NewHomeAdapter.ClickItemSmallLocation, NewHomeAdapter.ClickSearch {
    private F2FragmentHomeBinding binding;
    private NewHomeAdapter newHomeAdapter;
    private float heightTopbar = -400;
    private NewHomeViewModel newHomeViewModel;
    private SubSmallHeaderAdapter subSmallHeaderAdapter;
    private String phoneNumberDetectedFrom3G;
    private PauseVideo pauseVideo;
    private boolean isScroll = true;
    private IOnClickTabReloadData mIOnClickTabReloadData;
    private boolean isFirtLoad = true;
    private Context mContext;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("SCROLL_TO_TOP")) {
                binding.rclHome.smoothScrollToPosition(0);
            }
        }

    };
    private boolean isLoadFail = false;



    public interface IOnClickTabReloadData {
        void onTabClick(String code);
    }

    public void setmIOnClickTabReloadData(IOnClickTabReloadData mIOnClickTabReloadData) {
        isFirtLoad = false;
        this.mIOnClickTabReloadData = mIOnClickTabReloadData;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault("Home", "Home").setScreen_class(this.getClass().getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        //getDataHomeFromCache();
        updateViews();
        setClick();
        if (haveNetworkConnection()) {
            newHomeViewModel.getMobileFromViettel();
        }
        getCountSystemInbox();
        newHomeViewModel.getSetting();
        mContext.registerReceiver(receiver, new IntentFilter("SCROLL_TO_TOP"));
    }

    private void setClick() {
        binding.layoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.rclHome.smoothScrollToPosition(0);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                onClickUserView();
//                    }
//                }, 500);

            }
        });

        binding.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearch();
            }
        });

        binding.ivNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account1 = MyApplication.getInstance().getAccount();
                if (null != account1 && account1.isLogin()) {
                    NotificationActivity.Companion.openActivity(mActivity);
                } else {
                    LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                }
            }
        });
    }


    private void openSearch() {
        SearchActivity.startScreen(mActivity);
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        binding.rclHome.setHasFixedSize(true);
        binding.rclHome.setItemViewCacheSize(20);
        binding.rclHome.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN &&
                        rv.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING) {
                    rv.stopScroll();
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        OverScrollDecoratorHelper.setUpOverScroll(binding.rclHome, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        PreCachingLayoutManager preCachingLayoutManager = new PreCachingLayoutManager(mActivity);
        preCachingLayoutManager.setExtraLayoutSpace(10000);
        binding.rclHome.setLayoutManager(preCachingLayoutManager);


        newHomeViewModel = new NewHomeViewModel();
        binding.setNewHomeViewModel(newHomeViewModel);
        newHomeViewModel.addObserver(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                newHomeViewModel.getHomeService();
            }
        }, 1000);


//        binding.rootView.post(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    heightTopbar = 0 - (binding.rootView.getMeasuredHeight());
//                    animate(binding.rootView).setDuration(10).translationY(heightTopbar);
//                } catch (Exception e) {
//
//                }
//            }
//        });

        handleScrollRecyclerView();

//        TravelNewsActivity.openScreen(mActivity, false);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (mContext != null) {
                if (receiver != null) {
                    mContext.unregisterReceiver(receiver);
                }
            }
            try {
                if (newHomeAdapter != null && newHomeAdapter.getTimer() != null) {
                    newHomeAdapter.getTimer().cancel();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventBus.getDefault().unregister(this);
        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_EXIT, TrackingAnalytic.getDefault("Home", "Home").setScreen_class(this.getClass().getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onGetLocation(OnGetLocation onGetLocation) {
        try {
            MyLocation myLocation = MyApplication.getInstance().getMyLocation();
            if (null != myLocation
                    && myLocation.getLat() != myLocation.getLog()) {
                newHomeViewModel.getConfigRegion();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onReloadUserView(OnLoginSuccessAndUpdateUserView onLoginSuccessAndUpdateUserView) {
        try {
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin()) {
    //            animate(binding.rootView).setDuration(200).translationY(heightTopbar);

                String s = account.getFullname() != null ? account.getFullname() : "B???n";

    //            homeServiceResponse.getData().get(0).setUsername("Xin ch??o, " + s);
    //            homeServiceResponse.getData().get(0).setDescriptionUser("????ng k?? h???i vi??n ngay");
    //            homeServiceResponse.getData().get(0).setAvatar(account.getImageProfile());

                binding.tvName.setText("Ch??o, " + s);
                binding.tvLoginRightNow.setText("????ng k?? h???i vi??n ngay");
                binding.tvLoginRightNow.setVisibility(View.GONE);
                try {
                    String cut = account.getMobile().substring(7, 11);
                    String mobile = account.getMobile().replace(cut, "xxxxx");
                    binding.tvLoginRightNow.setText(mobile);
                    binding.tvLoginRightNow.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        binding.tvLoginRightNow.setVisibility(View.GONE);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                binding.tvLevel.setVisibility(View.VISIBLE);
                if (!"".equals(account.getImageProfile()) && account.getImageProfile() != null) {
                    Glide.with(mActivity).load(account.getImageProfile()).error(R.drawable.f2_defaut_user).into(binding.imgAvatar);
                }

    //            newHomeAdapter.notifyItemChanged(0);


                if(account.getPackageCode() == null){
                    homeServiceResponse.getData().get(1).setTipUser("??u ????i ?????c quy???n khi ????ng k?? <font color=\"#00918D\"><b>H???i vi??n</b></font> c???a <font color=\"#00918D\"><b>#VTVTravel</b></font>");
                    homeServiceResponse.getData().get(1).setShowBtnRegisterNow(true);
                    newHomeAdapter.notifyItemChanged(1);
                    newHomeAdapter.notifyItemChanged(2);

                }else {
                    homeServiceResponse.getData().get(1).setTipUser("B???n ??ang l?? <font color=\"#00918D\"><b>H???i vi??n</b></font> c???a <font color=\"#00918D\"><b>#VTVTravel</b></font>, ?????ng b??? l??? nh???ng c?? h???i ??u ????i d?????i ????y:");
                    homeServiceResponse.getData().get(1).setShowBtnRegisterNow(false);
                    newHomeAdapter.notifyItemChanged(1);
                    newHomeAdapter.notifyItemChanged(2);
                }


            } else {
                phoneNumberDetectedFrom3G = null;
    //            homeServiceResponse.getData().get(0).setUsername("Xin ch??o!");
    //            homeServiceResponse.getData().get(0).setDescriptionUser("????ng nh???p ngay");
    //            homeServiceResponse.getData().get(0).setAvatar("0");

                binding.tvName.setText("Xin ch??o!");
                binding.tvLoginRightNow.setText("????ng nh???p ngay");
                binding.tvLoginRightNow.setVisibility(View.VISIBLE);
                binding.tvLevel.setVisibility(View.GONE);
    //            Glide.with(mActivity).load("0").into(binding.imgAvatar);
                binding.imgAvatar.setImageResource(R.drawable.f2_defaut_user);

    //            newHomeAdapter.notifyItemChanged(0);


                homeServiceResponse.getData().get(1).setTipUser("??u ????i ?????c quy???n khi ????ng k?? <font color=\"#00918D\"><b>H???i vi??n</b></font> c???a <font color=\"#00918D\"><b>#VTVTravel</b></font>");
                homeServiceResponse.getData().get(1).setShowBtnRegisterNow(true);
                newHomeAdapter.notifyItemChanged(1);



            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int mTotalScrolled = 0;

    private void handleScrollRecyclerView() {

        binding.rclHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try {
                    LinearLayoutManager layoutManager = ((LinearLayoutManager) binding.rclHome.getLayoutManager());
                    int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();

//                int last = layoutManager.findLastCompletelyVisibleItemPosition();
//                try {
//                    if(mainLast != last) {
//                        mainLast = last;
//                        newHomeAdapter.notifyItemChanged(last);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                    mTotalScrolled += dy;

                    Log.e("dy", mTotalScrolled + "");

                    if (mTotalScrolled <= 0) {
                        binding.layoutToolbar.setVisibility(View.GONE);
                        binding.ivSearch.setVisibility(View.GONE);
                        binding.viewColor.setAlpha(0);
                    } else {
                        if (firstVisiblePosition == 0) {
                            float alpha = (((float) mTotalScrolled / 100) / 10) * 2;
                            Log.e("dy", alpha + "");
                            binding.viewColor.setAlpha(alpha);
                            if (binding.layoutToolbar.getVisibility() == View.VISIBLE) {
                                outAlpha();
                            }

                            binding.ivSearch.setVisibility(View.GONE);
                        }


                        if (firstVisiblePosition > 0) {
                            if(binding.ivSearch.getVisibility() == View.GONE) {
                                binding.viewColor.setAlpha(1.0f);
                                inAlpha();
                                binding.ivSearch.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    try {
                        if (pauseVideo != null) {
                            pauseVideo.pauseVideoListener();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (isScroll) {
                            isScroll = false;
                            TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_SCROLL, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.HOME, TrackingAnalytic.ScreenTitle.HOME).setScreen_class(this.getClass().getName()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private int mainLast = 0;


    private HomeServiceResponse homeServiceResponse;

    private void getDataHomeFromCache() {
        try {
            String json = PreferenceUtil.getInstance(mActivity).getValue(Constants.PrefKey.HOME_DATA, new Gson().toJson(homeServiceResponse));
            homeServiceResponse = new Gson().fromJson(json, HomeServiceResponse.class);
            newHomeAdapter = new NewHomeAdapter(mActivity, homeServiceResponse, this, this, this,this, this, this, newHomeViewModel);
            binding.rclHome.setAdapter(newHomeAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Observable observable, Object o) {
        try {
            if (observable instanceof NewHomeViewModel && null != o) {
                if (o instanceof CountSystemInbox) {
                    try {
                        CountSystemInbox countSystemInbox = (CountSystemInbox) o;
                        binding.tvCountNoti.setText(countSystemInbox.getData().getCount());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (o instanceof HomeServiceResponse) {
                    homeServiceResponse = (HomeServiceResponse) o;
                    PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.HOME_DATA, new Gson().toJson(homeServiceResponse));
                    try {
                        setDataForUserViewInRcl();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    newHomeAdapter = new NewHomeAdapter(mActivity, homeServiceResponse, this, this, this,this, this, this, newHomeViewModel);
                    binding.rclHome.setAdapter(newHomeAdapter);

                    subSmallHeaderAdapter = new SubSmallHeaderAdapter(homeServiceResponse.getData().get(0).getMenus(), mActivity);
                    binding.recycleContent.setAdapter(subSmallHeaderAdapter);
                    binding.shimmerViewContainer.stopShimmer();
                    binding.shimmerViewContainer.setVisibility(View.GONE);
                } else if (o instanceof ErrorResponse) {
                    ErrorResponse responseError = (ErrorResponse) o;
                    if(responseError.getErrorCode().equals(NewHomeAdapter.TypeString.APP_HOME)){
                        try {
                            Toast.makeText(mActivity, responseError.getErrorCode(), Toast.LENGTH_SHORT).show();
                            getDataHomeFromCache();
                            binding.shimmerViewContainer.stopShimmer();
                            binding.shimmerViewContainer.setVisibility(View.GONE);
                        } catch (Exception e) {
                        }

                    }
                } else if (o instanceof SettingResponse) {
                    SettingResponse settingResponse = (SettingResponse) o;
                } else if (o instanceof MobileFromViettelResponse) {
                    MobileFromViettelResponse mobileFromViettelResponse = (MobileFromViettelResponse) o;

                    if (mobileFromViettelResponse.getData().getMobile() != null) {
                        String cut = mobileFromViettelResponse.getData().getMobile().substring(7, 11);
                        phoneNumberDetectedFrom3G = mobileFromViettelResponse.getData().getMobile().replace(cut, "xxxxx");
                    } else {
                        phoneNumberDetectedFrom3G = "";
                    }

                    setDataForUserViewInRcl();

                } else if (o instanceof BaseResponseNewHome) {
                    BaseResponseNewHome baseResponseNewHome = (BaseResponseNewHome) o;
                    switch (baseResponseNewHome.getCodeData()) {
                        case NewHomeAdapter.TypeString.APP_VOUCHER:
                            AppVoucherResponse appVoucherResponse = new Gson().fromJson(new Gson().toJson(baseResponseNewHome.getData()), AppVoucherResponse.class);
                            for (int i = 0; i < homeServiceResponse.getData().size(); i++) {
                                if (homeServiceResponse.getData().get(i).getCode().equals(NewHomeAdapter.TypeString.APP_VOUCHER)) {
                                    homeServiceResponse.getData().get(i).setDataExtra(appVoucherResponse);
                                    newHomeAdapter.notifyItemChanged(i);
                                    break;
                                }
                            }
                            break;
                        //                    case NewHomeAdapter.TypeString.APP_DEAL:
                        //                        AppDealResponse appDealResponse = new Gson().fromJson(new Gson().toJson(baseResponseNewHome.getData()), AppDealResponse.class);
                        //                        for (int i = 0; i < homeServiceResponse.getData().size(); i++) {
                        //                            if (homeServiceResponse.getData().get(i).getCode().equals(NewHomeAdapter.TypeString.APP_DEAL)) {
                        //                                homeServiceResponse.getData().get(i).setDataExtra(appDealResponse);
                        //                                newHomeAdapter.notifyItemChanged(i);
                        //                                break;
                        //                            }
                        //                        }
                        //                        break;

                        case NewHomeAdapter.TypeString.APP_TOP_EXPERIENCE:
                            ItemAppExperienceResponse itemAppExperienceResponse = new Gson().fromJson(new Gson().toJson(baseResponseNewHome.getData()), ItemAppExperienceResponse.class);
                            for (int i = 0; i < homeServiceResponse.getData().size(); i++) {
                                if (homeServiceResponse.getData().get(i).getCode().equals(NewHomeAdapter.TypeString.APP_TOP_EXPERIENCE)) {
                                    homeServiceResponse.getData().get(i).setDataExtra(itemAppExperienceResponse);
                                    newHomeAdapter.notifyItemChanged(i);
                                    break;
                                }
                            }

                            break;
                        case NewHomeAdapter.TypeString.APP_PROMOTION_PARTNER:
                            AppPromotionPartnerResponse appPromotionPartnerResponse = new Gson().fromJson(new Gson().toJson(baseResponseNewHome.getData()), AppPromotionPartnerResponse.class);
                            for (int i = 0; i < homeServiceResponse.getData().size(); i++) {
                                if (homeServiceResponse.getData().get(i).getCode().equals(NewHomeAdapter.TypeString.APP_PROMOTION_PARTNER)) {
                                    homeServiceResponse.getData().get(i).setDataExtra(appPromotionPartnerResponse);
                                    newHomeAdapter.notifyItemChanged(i);
                                    break;
                                }
                            }
                            break;
                        case NewHomeAdapter.TypeString.APP_FAVORITE_DESTINATION:
                            AppFavoriteDestinationResponse appFavoriteDestinationResponse = new Gson().fromJson(new Gson().toJson(baseResponseNewHome.getData()), AppFavoriteDestinationResponse.class);
                            for (int i = 0; i < homeServiceResponse.getData().size(); i++) {
                                if (homeServiceResponse.getData().get(i).getCode().equals(NewHomeAdapter.TypeString.APP_FAVORITE_DESTINATION)) {
                                    homeServiceResponse.getData().get(i).setDataExtra(appFavoriteDestinationResponse);
                                    newHomeAdapter.notifyItemChanged(i);
                                    break;
                                }
                            }
                            break;
                        case NewHomeAdapter.TypeString.APP_VIDEO:
                            AppVideoResponse appVideoResponse = new Gson().fromJson(new Gson().toJson(baseResponseNewHome.getData()), AppVideoResponse.class);
                            for (int i = 0; i < homeServiceResponse.getData().size(); i++) {
                                if (homeServiceResponse.getData().get(i).getCode().equals(NewHomeAdapter.TypeString.APP_VIDEO)) {
                                    homeServiceResponse.getData().get(i).setDataExtra(appVideoResponse);
                                    newHomeAdapter.notifyItemChanged(i);
                                    break;
                                }
                            }
                            break;
                        case NewHomeAdapter.TypeString.APP_TV:
                            LiveTvResponse liveTvResponse = null;
                            try {
                                liveTvResponse = new Gson().fromJson(new Gson().toJson(baseResponseNewHome.getData()), LiveTvResponse.class);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < homeServiceResponse.getData().size(); i++) {
                                if (homeServiceResponse.getData().get(i).getCode().equals(NewHomeAdapter.TypeString.APP_TV)) {
                                    homeServiceResponse.getData().get(i).setDataExtra(liveTvResponse);
                                    newHomeAdapter.notifyItemChanged(i);
                                    break;
                                }
                            }
                            break;
                    }
                } else if (o instanceof BaseResponseSecondNewHome) {
                    BaseResponseSecondNewHome baseResponseSecondNewHome = (BaseResponseSecondNewHome) o;
                    switch (baseResponseSecondNewHome.getCodeData()) {
                        case NewHomeAdapter.TypeString.APP_VOUCHER_NOW:
                            ItemAppVoucherNowResponse appVoucherResponse = new Gson().fromJson(new Gson().toJson(baseResponseSecondNewHome.getData()), ItemAppVoucherNowResponse.class);
                            for (int i = 0; i < homeServiceResponse.getData().size(); i++) {
                                if (homeServiceResponse.getData().get(i).getCode().equals(NewHomeAdapter.TypeString.APP_VOUCHER_NOW)) {
                                    if (baseResponseSecondNewHome.isSave()) {
                                        homeServiceResponse.getData().get(i).setDataExtraSecond(appVoucherResponse);
                                    } else {
                                        homeServiceResponse.getData().get(i).setDataExtraSecondAfterClickTab(appVoucherResponse);
                                    }
                                    if (isFirtLoad) {
                                        newHomeAdapter.notifyItemChanged(i);
                                    } else if (mIOnClickTabReloadData != null) {
                                        mIOnClickTabReloadData.onTabClick(NewHomeAdapter.TypeString.APP_VOUCHER_NOW);
                                        isFirtLoad = true;
                                    }
                                    break;
                                }
                            }
                            break;
                        case NewHomeAdapter.TypeString.APP_EXPERIENCES_NEARBY:
                            ItemAppExperienceResponse itemAppExperienceResponse = new Gson().fromJson(new Gson().toJson(baseResponseSecondNewHome.getData()), ItemAppExperienceResponse.class);
                            for (int i = 0; i < homeServiceResponse.getData().size(); i++) {
                                if (homeServiceResponse.getData().get(i).getCode().equals(NewHomeAdapter.TypeString.APP_EXPERIENCES_NEARBY)) {
                                    if (baseResponseSecondNewHome.isSave()) {
                                        homeServiceResponse.getData().get(i).setDataExtraSecond(itemAppExperienceResponse);
                                    } else {
                                        homeServiceResponse.getData().get(i).setDataExtraSecondAfterClickTab(itemAppExperienceResponse);
                                    }
                                    if (isFirtLoad) {
                                        newHomeAdapter.notifyItemChanged(i);
                                    } else if (mIOnClickTabReloadData != null) {
                                        mIOnClickTabReloadData.onTabClick(NewHomeAdapter.TypeString.APP_EXPERIENCES_NEARBY);
                                        isFirtLoad = true;
                                    }
                                    break;
                                }
                            }
                            break;

                        case NewHomeAdapter.TypeString.APP_DISCOVER:
                            ItemAppDiscoverResponse itemAppDiscoverResponse = new Gson().fromJson(new Gson().toJson(baseResponseSecondNewHome.getData()), ItemAppDiscoverResponse.class);
                            for (int i = 0; i < homeServiceResponse.getData().size(); i++) {
                                if (homeServiceResponse.getData().get(i).getCode().equals(NewHomeAdapter.TypeString.APP_DISCOVER)) {
                                    if (baseResponseSecondNewHome.isSave()) {
                                        homeServiceResponse.getData().get(i).setDataExtraSecond(itemAppDiscoverResponse);
                                    } else {
                                        homeServiceResponse.getData().get(i).setDataExtraSecondAfterClickTab(itemAppDiscoverResponse);
                                    }
                                    if (isFirtLoad) {
                                        newHomeAdapter.notifyItemChanged(i);
                                    } else if (mIOnClickTabReloadData != null) {
                                        mIOnClickTabReloadData.onTabClick(NewHomeAdapter.TypeString.APP_DISCOVER);
                                        isFirtLoad = true;
                                    }
                                    break;
                                }
                            }
                            break;

                    }
                } else if (o instanceof BaseResponseSpecialNewHome) {
                    BaseResponseSpecialNewHome baseResponseSpecialNewHome = (BaseResponseSpecialNewHome) o;
                    for (int i = 0; i < homeServiceResponse.getData().size(); i++) {
                        if (homeServiceResponse.getData().get(i).getCode().equals(NewHomeAdapter.TypeString.APP_DEAL)) {
                            homeServiceResponse.getData().get(i).setDataExtra(baseResponseSpecialNewHome);
                            newHomeAdapter.notifyItemChanged(i);
                            break;
                        }
                    }
                } else if (o instanceof ConfigPopupResponse) {
                    try {
                        ConfigPopupResponse configPopupResponse = (ConfigPopupResponse) o;
                        String regionIdOld = PreferenceUtil.getInstance(mActivity).getValue(Constants.PrefKey.REGION_ID, "");
                        String regionIdNew = configPopupResponse.getData().getId();

                        try {
                            if (regionIdNew != null && !regionIdNew.isEmpty()) {
                                if (!regionIdNew.equals(regionIdOld)) {
                                    ChangeRegionDialog changeRegionDialog = ChangeRegionDialog.Companion.newInstance(configPopupResponse.getData().getGreeting(), configPopupResponse.getData().getBanner_greeting(), configPopupResponse.getData().getIcon_greeting_url(), new ChangeRegionDialog.Click() {
                                        @Override
                                        public void onClick() {
                                            try {
                                                BigLocationActivity.startScreen(mActivity, configPopupResponse.getData().getId(), false);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    changeRegionDialog.show(mActivity.getSupportFragmentManager(), null);
                                    PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.REGION_ID, regionIdNew);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
//            else if (!isLoadFail) {
//                    getDataHomeFromCache();
//                    binding.shimmerViewContainer.stopShimmer();
//                    binding.shimmerViewContainer.setVisibility(View.GONE);
//                    isLoadFail = true;
//                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadData(String link, String code) {
        if (code.equals(NewHomeAdapter.TypeString.APP_DEAL)) {
            newHomeViewModel.appFavoriteDestinationForAppDeal(link, code);
        } else {
            newHomeViewModel.appFavoriteDestination(link, code);
        }
    }

    @Override
    public void onLoadDataFloorSecond(String link, String code, boolean isSave) {
        newHomeViewModel.getDataSecond(link, code, isSave);
    }

    @Override
    public void onClickUserView() {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            UserInformationActivity.openScreen(mActivity);
        } else {
            if (phoneNumberDetectedFrom3G != null && !phoneNumberDetectedFrom3G.isEmpty()) {
                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
            } else {
                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
            }
        }
    }

    @Override
    public void onClickButtonRegisterNow() {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            if(account.getPackageCode() == null){
                ServiceActivity.startScreen(mActivity);
            }
        } else {
            if (phoneNumberDetectedFrom3G != null && !phoneNumberDetectedFrom3G.isEmpty()) {
                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
            } else {
                LoginAndRegisterActivityNew.startScreen(mActivity, 1, false);
            }
        }
    }

    private void setDataForUserViewInRcl() {
        try {
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin()) {
                String s = account.getFullname() != null ? account.getFullname() : "B???n";
//                homeServiceResponse.getData().get(0).setUsername("Xin ch??o, " + s);
//                homeServiceResponse.getData().get(0).setDescriptionUser("????ng k?? h???i vi??n ngay");
//                homeServiceResponse.getData().get(0).setAvatar(account.getImageProfile());


                if(account.getPackageCode() == null){
                    homeServiceResponse.getData().get(1).setTipUser("??u ????i ?????c quy???n khi ????ng k?? <font color=\"#00918D\"><b>H???i vi??n</b></font> c???a <font color=\"#00918D\"><b>#VTVTravel</b></font>");
                    homeServiceResponse.getData().get(1).setShowBtnRegisterNow(true);
                }else {
                    homeServiceResponse.getData().get(1).setTipUser("B???n ??ang l?? <font color=\"#00918D\"><b>H???i vi??n</b></font> c???a <font color=\"#00918D\"><b>#VTVTravel</b></font>, ?????ng b??? l??? nh???ng c?? h???i ??u ????i d?????i ????y:");
                    homeServiceResponse.getData().get(1).setShowBtnRegisterNow(false);
                }


                binding.tvName.setText("Ch??o, " + s);
                binding.tvLoginRightNow.setText("????ng k?? h???i vi??n ngay");
                binding.tvLoginRightNow.setVisibility(View.GONE);
                try {
                    String cut = account.getMobile().substring(7, 11);
                    String mobile = account.getMobile().replace(cut, "xxxxx");
                    binding.tvLoginRightNow.setText(mobile);
                    binding.tvLoginRightNow.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        binding.tvLoginRightNow.setVisibility(View.GONE);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                binding.tvLevel.setVisibility(View.VISIBLE);
                if (!"".equals(account.getImageProfile()) && account.getImageProfile() != null) {
                    Glide.with(mActivity).load(account.getImageProfile()).error(R.drawable.f2_defaut_user).into(binding.imgAvatar);
                }
            } else {
                binding.tvLevel.setVisibility(View.GONE);
//                homeServiceResponse.getData().get(0).setAvatar("0");
                if (phoneNumberDetectedFrom3G != null) {
//                    homeServiceResponse.getData().get(0).setUsername("Xin ch??o, " + phoneNumberDetectedFrom3G);
//                    homeServiceResponse.getData().get(0).setDescriptionUser("????ng k?? ngay");

                    binding.tvName.setText("Ch??o, " + phoneNumberDetectedFrom3G);
                    binding.tvLoginRightNow.setText("????ng k?? ngay");
                    binding.tvLoginRightNow.setVisibility(View.VISIBLE);
                } else {
//                    homeServiceResponse.getData().get(0).setUsername("Xin ch??o!");
//                    homeServiceResponse.getData().get(0).setDescriptionUser("????ng nh???p ngay");
                }

                homeServiceResponse.getData().get(1).setTipUser("??u ????i ?????c quy???n khi ????ng k?? <font color=\"#00918D\"><b>H???i vi??n</b></font> c???a <font color=\"#00918D\"><b>#VTVTravel</b></font>");
                homeServiceResponse.getData().get(1).setShowBtnRegisterNow(true);
            }

//            newHomeAdapter.notifyItemChanged(0);
            newHomeAdapter.notifyItemChanged(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedMobile;
    }

    @Override
    public void onClickItemSmallLocation(ItemHomeService.Item item) {
//        SmallLocationActivity.startScreen(mActivity, item.getLink(), item.getCode(), SmallLocationActivity.OpenType.LIST);
    }

    private void inAlpha() {
        binding.layoutToolbar.setAlpha(0f);
        binding.layoutToolbar.setVisibility(View.VISIBLE);
        binding.layoutToolbar.animate()
                .alpha(1.0f)
                .setDuration(400)
                .setListener(null);
    }

    private void outAlpha() {
        binding.layoutToolbar.setAlpha(1.0f);
        binding.layoutToolbar.animate()
                .alpha(0f)
                .setDuration(400)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        binding.layoutToolbar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onClickSearch() {
        openSearch();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (pauseVideo != null) {
            pauseVideo.pauseVideoListener();
        }
        try {
            if (newHomeAdapter != null && newHomeAdapter.getTimer() != null) {
                newHomeAdapter.getTimer().cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface PauseVideo {
        void pauseVideoListener();
    }

    public void setPauseVideo(PauseVideo pauseVideo) {
        this.pauseVideo = pauseVideo;
    }

    @Subscribe
    public void onUpdateAccount(OnUpdateAccount onUpdateAccount) {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            String s = account.getFullname() != null ? account.getFullname() : "B???n";
            binding.tvName.setText("Ch??o, " + s);
            if (!"".equals(account.getImageProfile()) && account.getImageProfile() != null) {
                Glide.with(mActivity).load(account.getImageProfile()).error(R.drawable.f2_defaut_user).into(binding.imgAvatar);
            }
        } else {
            binding.imgAvatar.setImageResource(R.drawable.f2_defaut_user);
        }

    }

    @Subscribe
    public void OnClickTab(OnClickTab onClickTab) {
        try {
            if (pauseVideo != null) {
                pauseVideo.pauseVideoListener();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getCountSystemInbox() {
        newHomeViewModel.getSystemInboxCount();
    }


    @Subscribe
    public void onReloadCountSystemInbox(OnReloadCountSystemInbox onReloadCountSystemInbox) {
        getCountSystemInbox();
    }

    @Override
    public void onResume() {
        super.onResume();
        getCountSystemInbox();
    }
}
