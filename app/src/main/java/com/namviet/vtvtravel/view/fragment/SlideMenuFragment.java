package com.namviet.vtvtravel.view.fragment;

import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ksyun.media.streamer.capture.CameraCapture;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.SlideMenuAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentSlideMenuBinding;
import com.namviet.vtvtravel.listener.CitySelectListener;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.City;
import com.namviet.vtvtravel.model.ItemMenu;
import com.namviet.vtvtravel.model.ItemWeather;
import com.namviet.vtvtravel.model.MenuLeft;
import com.namviet.vtvtravel.response.CityResponse;
import com.namviet.vtvtravel.response.WeatherResponse;
import com.namviet.vtvtravel.view.BaseCameraActivity;
import com.namviet.vtvtravel.view.dialog.CityDialogFragment;
import com.namviet.vtvtravel.view.dialog.WeatherDialog;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SlideMenuFragment extends MainFragment implements Observer, CitySelectListener {

    private FragmentSlideMenuBinding binding;
    private MenuLeft menuLeft;
    private SlideMenuAdapter slideMenuAdapter;
    private Account account;
    private PlaceViewModel placeViewModel;
    private ArrayList<City> cityList;
    private ArrayList<ItemWeather> weatherList;

    public static SlideMenuFragment newInstance(MenuLeft menuLeft) {
        SlideMenuFragment fragment = new SlideMenuFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, menuLeft);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            menuLeft = getArguments().getParcelable(Constants.IntentKey.KEY_FRAGMENT);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_slide_menu, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvCity.setOnClickListener(this);
        binding.btLogin.setOnClickListener(this);
        binding.btRegister.setOnClickListener(this);
        binding.avatar.setOnClickListener(this);
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);

        binding.rvSlideMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        slideMenuAdapter = new SlideMenuAdapter(getContext());
        binding.rvSlideMenu.setAdapter(slideMenuAdapter);
        placeViewModel = new PlaceViewModel(getContext());
        placeViewModel.addObserver(this);
        placeViewModel.loadWeather(null);
        placeViewModel.loadCity();
        updateViews();

    }

    @Override
    public void updateViews() {
        super.updateViews();
        account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            binding.llLogin.setVisibility(View.GONE);
            binding.llInfo.setVisibility(View.VISIBLE);

            binding.tvName.setText(account.getFullname());
            if (null != account.getFullname()) {
                binding.tvName.setVisibility(View.VISIBLE);
                binding.tvName.setText(account.getFullname());
            } else {
                binding.tvName.setVisibility(View.GONE);
            }
            if (null != account.getMobile()) {
                binding.tvPhone.setVisibility(View.VISIBLE);
                binding.tvPhone.setText("0" + account.getMobile().substring(2));
            } else {
                binding.tvPhone.setVisibility(View.GONE);
            }
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.user);
            Glide.with(this).setDefaultRequestOptions(requestOptions).load(account.getImageProfile()).into(binding.avatar);
            if (null != account.getFacebookId() && account.getFacebookId().length() > 0) {
                binding.profileAvatar.setVisibility(View.VISIBLE);
                binding.profileAvatar.setProfileId(account.getFacebookId());
            }
        } else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.user);
            Glide.with(this).setDefaultRequestOptions(requestOptions).load("").into(binding.avatar);
            binding.llLogin.setVisibility(View.VISIBLE);
            binding.llInfo.setVisibility(View.GONE);
        }
        binding.tvTemp.setOnClickListener(this);
        binding.tvHumidity.setOnClickListener(this);
    }

    public void setDataSlideMenu(List<ItemMenu> list) {
        if (null != list) {
            slideMenuAdapter.updateMenuList(list);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.btLogin) {
            mActivity.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);
        } else if (view.getId() == R.id.avatar) {
            if (null != account && account.isLogin()) {
                mActivity.switchFragment(SlideMenu.MenuType.ACCOUNT_SCREEN);
            } else {
                mActivity.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);
            }
        } else if (view.getId() == R.id.btRegister) {
            mActivity.switchFragment(SlideMenu.MenuType.REGISTER_SCREEN);
//            mActivity.switchFragment(SlideMenu.MenuType.UPDATE_INFO_SCREEN);
        } else if (view.getId() == R.id.tvCity) {
            if (null != cityList) {
                CityDialogFragment cityDialogFragment = CityDialogFragment.newInstance(cityList);
                cityDialogFragment.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                cityDialogFragment.setCitySelectListener(this);
            }
        } else if (view == binding.tvHumidity) {
            if (null != weatherList) {
                WeatherDialog weatherDialog = WeatherDialog.newInstance(weatherList);
                weatherDialog.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
            }
        } else if (view == binding.tvTemp) {
            binding.tvHumidity.performClick();
        }
//        else if (view.getId() == R.id.btLiveVideo) {
////            mActivity.switchFragment(SlideMenu.MenuType.LIVE_SCREEN);
//            BaseCameraActivity.BaseStreamConfig config = new BaseCameraActivity.BaseStreamConfig();
//            loadParams(config, "rtmp://103.21.148.34:1935/partner/namviet");
//            BaseCameraActivity.startActivity(getActivity(), config, BaseCameraActivity.class);
//        } else if (view.getId() == R.id.btPlayLive) {
//            mActivity.switchFragment(SlideMenu.MenuType.PLAY_LIVE_SCREEN);
//        } else if (view.getId() == R.id.btEncode) {
//            mActivity.switchFragment(SlideMenu.MenuType.ENCODE_SCREEN);
//        }
    }

    protected void loadParams(BaseCameraActivity.BaseStreamConfig config, String url) {
        // initial value
        config.mFrameRate = 15.0f;
        config.mVideoKBitrate = 800;
        config.mAudioKBitrate = 48;
        config.mUrl = url;
        config.mCameraFacing = CameraCapture.FACING_FRONT;
        config.mTargetResolution = StreamerConstants.VIDEO_RESOLUTION_720P;
        config.mOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        config.mEncodeMethod = StreamerConstants.ENCODE_METHOD_SOFTWARE;
        config.mAutoStart = false;
        config.mShowDebugInfo = false;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof PlaceViewModel) {
            if (o instanceof WeatherResponse) {
                WeatherResponse response = (WeatherResponse) o;
                weatherList = response.getData();
                setWeather(response.getData().get(0));
            } else if (o instanceof CityResponse) {
                CityResponse response = (CityResponse) o;
                cityList = response.getData();
            }
        }
    }

    private void setWeather(ItemWeather itemWeather) {
        if (null != itemWeather) {
            binding.tvCity.setText(itemWeather.getRegion_name());
            if (Math.round(itemWeather.getTemp_min()) == Math.round(itemWeather.getTemp_max())) {
                binding.tvHumidity.setVisibility(View.INVISIBLE);
            } else {
                binding.tvHumidity.setVisibility(View.VISIBLE);
                binding.tvHumidity.setText(Math.round(itemWeather.getTemp_min()) + " - " + Math.round(itemWeather.getTemp_max()) + " °C");
            }
            binding.tvTemp.setText(Math.round(itemWeather.getTamp()) + "°C");
            setImageUrl(itemWeather.getWeather().getIcon_url(), binding.ivIconWeather);
            setImageUrl(itemWeather.getBanner_url(), binding.ivBgInfo);
        }
    }

    @Override
    public void onSelect(ArrayList<City> list, City city) {
        placeViewModel.loadWeather(city);
    }
}
