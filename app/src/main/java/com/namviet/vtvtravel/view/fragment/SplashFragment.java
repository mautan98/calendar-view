package com.namviet.vtvtravel.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.namviet.vtvtravel.R;

import com.namviet.vtvtravel.databinding.FragmentSplashBinding;
import com.namviet.vtvtravel.model.MenuLeft;
import com.namviet.vtvtravel.response.NotifyResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.newhome.HomeServiceResponse;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.MenuLeft;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.viewmodel.HomeViewModel;
import com.namviet.vtvtravel.viewmodel.NotifyViewModel;
import com.namviet.vtvtravel.viewmodel.newhome.SplashViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

public class SplashFragment extends MainFragment implements Observer {
    private long startTime;
    private boolean stopThread = false;

    private FragmentSplashBinding binding;
    private HomeViewModel homeViewModel;
    private SplashViewModel splashViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkDateForInvitedUser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false);
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
        homeViewModel = new HomeViewModel(getContext());
        homeViewModel.addObserver(this);
//        homeViewModel.getListMenu();

//        splashViewModel = new SplashViewModel();
//        binding.setSplashViewModel(splashViewModel);
//        splashViewModel.addObserver(this);
//        splashViewModel.getHomeService();

        onSuccessLoading();
    }

    private void onSuccessLoading() {
        startTime = System.currentTimeMillis();
        final long time = (System.currentTimeMillis() >= startTime + 2000) ? 0 : (2000 + startTime - System.currentTimeMillis());
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {

                } finally {
                    if (!stopThread) {
                        mActivity.updateListMenu();
                        mActivity.switchFragment(SlideMenu.MenuType.HOME_SCREEN);
                    }
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopThread = true;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof MenuLeft) {
            mActivity.setMenuLeft((MenuLeft) o);
            onSuccessLoading();
        } else if (null == o) {
            mActivity.switchFragment(SlideMenu.MenuType.HOME_SCREEN);
//            showMessage(getString(R.string.error_system));
        }

//        if (observable instanceof SplashViewModel && null != o) {
//            if (o instanceof HomeServiceResponse) {
//                HomeServiceResponse homeServiceResponse = (HomeServiceResponse) o;
//                mActivity.switchFragment(SlideMenu.MenuType.HOME_SCREEN);
//            } else if (o instanceof ResponseError) {
//                ResponseError responseError = (ResponseError) o;
//                showMessage(responseError.getMessage());
//            }
//        }
    }

    private void checkDateForInvitedUser(){
        try {
            String dateCache = PreferenceUtil.getInstance(mActivity).getValue(Constants.PrefKey.DATE, "");
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            if(dateCache.equals(currentDate)){

            }else {
                PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.DATE, currentDate);
                MyApplication.getAppDatabase().foodDao().clearInvitedUser();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
