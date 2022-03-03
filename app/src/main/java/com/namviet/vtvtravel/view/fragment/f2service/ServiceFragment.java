package com.namviet.vtvtravel.view.fragment.f2service;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentServiceBinding;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
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
        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.SERVICE_PACKAGE, TrackingAnalytic.ScreenTitle.SERVICE_PACKAGE).setScreen_class(this.getClass().getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
    }

    public void initView() {
        serviceViewModel = new ServiceViewModel(getContext());
        binding.setServiceViewModel(serviceViewModel);
        serviceViewModel.addObserver(this);

        showDialogLoading();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Account account = MyApplication.getInstance().getAccount();
//                if (null != account && account.isLogin()) {
//                    String code = String.valueOf(MyApplication.getInstance().getAccount().getId());
//                    String token = String.valueOf(MyApplication.getInstance().getAccount().getToken());
//                    serviceViewModel.getInfo(code, token);
//                    Toast.makeText(mActivity, "Đang lấy thông tin gói dịch vụ...", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        }, Constants.TimeDelay);
        serviceViewModel.getService("ANDROID");
        binding.btnBack.setOnClickListener(this);
        binding.btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.rllNoData.setVisibility(View.GONE);
                showDialogLoading();
                serviceViewModel.getService("ANDROID");
            }
        });
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


        binding.tabVideo.setupWithViewPager(binding.vpVideo);
        binding.vpVideo.setOffscreenPageLimit(list.size());
        binding.vpVideo.setCurrentItem(1);

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
                MyApplication.getInstance().setVipRegisted(false);
                GetInfoResponse getInfoResponse = (GetInfoResponse) arg;
                if(getInfoResponse.getData().getUser().getPackageCode() != null && getInfoResponse.getData().getUser().getPackageCode().equals("TRAVEL_VIP")){
                    MyApplication.getInstance().setVipRegisted(true);
                }
                serviceViewModel.getService("ANDROID");
            } else if (arg instanceof ErrorResponse) {
//                try {
//                    ErrorResponse responseError = (ErrorResponse) arg;
//                    showMessage(responseError.getMessage());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                binding.rllNoData.setVisibility(View.VISIBLE);
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
