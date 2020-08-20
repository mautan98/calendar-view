package com.namviet.vtvtravel.view.fragment.f2service;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentServiceBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.view.fragment.MainFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ServiceFragment extends MainFragment implements Observer {
    private FragmentServiceBinding binding;
    private CategoryServicePageAdapter pagerAdapter;
    private ServiceViewModel serviceViewModel;

    public static ServiceFragment getInstance() {
        return new ServiceFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        pagerAdapter = new CategoryServicePageAdapter(getChildFragmentManager());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_service, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void initView() {
        serviceViewModel = new ServiceViewModel(getContext());
        binding.setServiceViewModel(serviceViewModel);
        serviceViewModel.addObserver(this);

        showDialogLoading();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Account account = MyApplication.getInstance().getAccount();
                if (null != account && account.isLogin()) {
                    String code = String.valueOf(MyApplication.getInstance().getAccount().getId());
                    String token = String.valueOf(MyApplication.getInstance().getAccount().getToken());
                    serviceViewModel.getInfo(code, token);
                }


            }
        }, Constants.TimeDelay);

        binding.btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btnBack:
                mActivity.onBackPressed();
        }
    }

    private void setUITab(List<Service> list) {
        pagerAdapter.setList(list);
        binding.vpVideo.setAdapter(pagerAdapter);

        binding.tabVideo.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        binding.tabVideo.setViewPager(binding.vpVideo);
        binding.tabVideo.setCurrentTab(0);
        binding.vpVideo.setOffscreenPageLimit(list.size());

    }

    @Subscribe
    public void onReloadSubAppEvent(ReloadSubAppEvent reloadSubAppEvent) {
        initView();
    }

    @Override
    public void update(Observable o, Object arg) {
        dimissDialogLoading();
        if (o instanceof ServiceViewModel && arg != null) {
            if (arg instanceof ServiceResponse) {
                ServiceResponse serviceResponse = (ServiceResponse) arg;

//                for (Service service : serviceResponse.getData()) {
//                    if ("TRAVEL_VIP".equals(service.getCode())
//                            &&
//                            service.isRegistered()) {
//                        MyApplication.getInstance().setVipRegisted(true);
//                    }
//                }

                setUITab(serviceResponse.getData());

            } else if (arg instanceof AccountResponse) {
                AccountResponse accountResponse = (AccountResponse) arg;
                MyApplication.getInstance().setAccount(accountResponse.getData());
                mActivity.updateLogin();
            } else if (arg instanceof GetInfoResponse) {
                GetInfoResponse getInfoResponse = (GetInfoResponse) arg;
                if(getInfoResponse.getData().getUser().getPackageCode() != null && getInfoResponse.getData().getUser().getPackageCode().equals("TRAVEL_VIP")){
                    MyApplication.getInstance().setVipRegisted(true);
                }
                serviceViewModel.getService("ANDROID");
            } else if (arg instanceof ResponseError) {
                ResponseError responseError = (ResponseError) arg;
                showMessage(responseError.getMessage());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onReloadFromLogin(ReloadService reloadService) {
        initView();
    }


}
