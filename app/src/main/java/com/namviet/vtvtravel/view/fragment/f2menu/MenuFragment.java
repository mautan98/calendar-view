package com.namviet.vtvtravel.view.fragment.f2menu;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2menu.MenuAdapter;
import com.namviet.vtvtravel.adapter.f2menu.SubMenuAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentMenuBinding;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.listener.CitySelectListener;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.City;
import com.namviet.vtvtravel.model.ItemWeather;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndUpdateUserView;
import com.namviet.vtvtravel.model.f2event.OnUpdateAccount;
import com.namviet.vtvtravel.response.CityResponse;
import com.namviet.vtvtravel.response.WeatherResponse;
import com.namviet.vtvtravel.response.f2menu.MenuItem;
import com.namviet.vtvtravel.response.f2menu.MenuResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.dialog.CityDialogFragment;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.UserInformationActivity;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.viewmodel.newhome.NewHomeViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MenuFragment extends MainFragment implements Observer {
    private ArrayList<City> cityList;
    private ArrayList<ItemWeather> weatherList;
    private F2FragmentMenuBinding binding;
    private NewHomeViewModel viewModel;

    private SubMenuAdapter subMenuAdapter;
    private MenuAdapter menuAdapter;
    private List<MenuItem> itemMenusHeader = new ArrayList<>();
    private List<MenuItem> itemMenusNormal = new ArrayList<>();
    private List<MenuItem> itemMenusFooter = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_menu, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.MENU, TrackingAnalytic.ScreenTitle.MENU).setScreen_class(this.getClass().getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateViews();
    }

    private MenuResponse menuResponse;

    @Override
    protected void updateViews() {
        super.updateViews();
        setUpLayout();
        setClickListener();

        viewModel = new NewHomeViewModel();
        binding.setNewHomeViewModel(viewModel);
        viewModel.addObserver(this);
        viewModel.getSetting();
        viewModel.loadWeather(null);
        viewModel.loadCity();

    }

    private void setClickListener() {
        binding.layoutUserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInformationActivity.openScreen(mActivity);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginAndRegisterActivityNew.startScreen(mActivity, 1, false);
            }
        });

        binding.layoutWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != cityList) {
                    CityDialogFragment cityDialogFragment = CityDialogFragment.newInstance(cityList);
                    cityDialogFragment.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                    cityDialogFragment.setCitySelectListener(new CitySelectListener() {
                        @Override
                        public void onSelect(ArrayList<City> list, City city) {
                            viewModel.loadWeather(city);
                        }
                    });
                }
            }
        });
    }

    private void getDataMenu(MenuResponse menuResponse) {
        for (int i = 0; i < menuResponse.getData().getMenus().getLeft().size(); i++) {
            if (menuResponse.getData().getMenus().getLeft().get(i).getCode().toUpperCase().equals("APP_MAIN_HEADER_BOOKING")) {
                itemMenusHeader.add(menuResponse.getData().getMenus().getLeft().get(i));
            } else if (menuResponse.getData().getMenus().getLeft().get(i).getCode().toUpperCase().equals("APP_MAIN_FOOTER_BOOKING")) {
                itemMenusFooter.add(menuResponse.getData().getMenus().getLeft().get(i));
            } else {
                itemMenusNormal.add(menuResponse.getData().getMenus().getLeft().get(i));
            }
        }

//        if (MyApplication.getInstance().getAccount() != null
//                && MyApplication.getInstance().getAccount().isTravelingSupporter()) {
//            MenuItem virtualCallMenu = new MenuItem();
//            virtualCallMenu.setName("Tổng đài số ảo");
//            virtualCallMenu.setCode_type("APP_MAIN_HEADER_VIRTUAL_CALL");
//            virtualCallMenu.setCode("HEADER");
//            itemMenusHeader.add(virtualCallMenu);
//        }

    }

    @Override
    public void update(Observable observable, Object o) {
//

        if (observable instanceof NewHomeViewModel && null != o) {
            if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    Toast.makeText(mActivity, responseError.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
            } else if (o instanceof MenuResponse) {
                menuResponse = (MenuResponse) o;
                getDataMenu(menuResponse);
                subMenuAdapter = new SubMenuAdapter(itemMenusHeader, mActivity, null);
                binding.rclHeaderMenu.setAdapter(subMenuAdapter);

                menuAdapter = new MenuAdapter(itemMenusNormal, itemMenusFooter, mActivity, null);
                binding.rclContent.setAdapter(menuAdapter);

                binding.rclContent.setNestedScrollingEnabled(false);
                binding.rclHeaderMenu.setNestedScrollingEnabled(false);
            }

        }

        if (observable instanceof NewHomeViewModel && null != o) {
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

    private void setUpLayout() {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            if (account.getImageProfile() != null && !account.getImageProfile().isEmpty()) {
                Glide.with(mActivity).load(account.getImageProfile()).apply(new RequestOptions().circleCrop()).error(R.drawable.f2_defaut_user).into(binding.imgAvatar);
            } else {
                binding.imgAvatar.setImageResource(R.drawable.f2_defaut_user);
            }
            binding.tvName.setText(account.getFullname());
            binding.tvPhone.setText(String.valueOf(account.getMobile()));
            binding.layoutUser.setVisibility(View.VISIBLE);
            binding.layoutUserNotYetLogin.setVisibility(View.GONE);
            binding.rclHeaderMenu.setVisibility(View.VISIBLE);
//            if (MyApplication.getInstance().getAccount() != null
//                    && MyApplication.getInstance().getAccount().isTravelingSupporter()) {
//                if (itemMenusHeader != null && itemMenusHeader.size() > 0
//                        && !"APP_MAIN_HEADER_VIRTUAL_CALL".equals(itemMenusHeader.get(itemMenusHeader.size() - 1).getCode_type())) {
//                    MenuItem virtualCallMenu = new MenuItem();
//                    virtualCallMenu.setName("Tổng đài số ảo");
//                    virtualCallMenu.setCode_type("APP_MAIN_HEADER_VIRTUAL_CALL");
//                    virtualCallMenu.setCode("HEADER");
//                    itemMenusHeader.add(virtualCallMenu);
//                }
//
//            }
        } else {
            binding.layoutUser.setVisibility(View.GONE);
            binding.layoutUserNotYetLogin.setVisibility(View.VISIBLE);
            binding.rclHeaderMenu.setVisibility(View.GONE);
        }
    }

    @Subscribe
    public void onReloadUserView(OnLoginSuccessAndUpdateUserView onLoginSuccessAndUpdateUserView) {
        setUpLayout();
    }


    private void setWeather(ItemWeather itemWeather) {
        if (null != itemWeather) {
            try {
                binding.tvCity.setText(itemWeather.getRegion_name());
                binding.tvStatus.setText(itemWeather.getWeather().getDescription() + ", " + Math.round(itemWeather.getTamp()) + "°C");
                Glide.with(mActivity).load(itemWeather.getWeather().getIcon_url()).placeholder(R.drawable.f2_ic_weather).error(R.drawable.f2_ic_weather).into(binding.imgWeather);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe
    public void onUpdateAccount(OnUpdateAccount onUpdateAccount) {
        setUpLayout();
    }

}
