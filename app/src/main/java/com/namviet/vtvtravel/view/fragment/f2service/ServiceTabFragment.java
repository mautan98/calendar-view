package com.namviet.vtvtravel.view.fragment.f2service;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.FragmentTabServiceBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.fragment.MainFragment;

public class ServiceTabFragment extends MainFragment {

    private Service service;
    private FragmentTabServiceBinding binding;
    private TypeRegisterDialog typeRegisterDialog;

    public static ServiceTabFragment newInstance(Service service) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, service);
        ServiceTabFragment fragment = new ServiceTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            service = getArguments().getParcelable(Constants.IntentKey.KEY_FRAGMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_service, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        binding.btnRegister.setOnClickListener(this);

        boolean isVipRegisted = MyApplication.getInstance().isVipRegisted();
//        boolean isVipRegisted  = false;
//
//        Account account = MyApplication.getInstance().getAccount();
//        if (null != account && account.isLogin()) {
//            isVipRegisted = account.getPackageCode().equals("TRAVEL_VIP");
//        } else {
//
//        }

        if (service.isRegistered() || isVipRegisted) {
            binding.btnRegister.setClickable(false);
            binding.btnRegister.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.bg_register_service2));
        } else {
            binding.btnRegister.setClickable(true);
            binding.btnRegister.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.bg_register_service));
        }


//        if (service.isRegistered() && !checkSdkLower16()) {
//            binding.btnRegister.setClickable(false);
//            binding.btnRegister.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.bg_register_service2));
//        } else if (service.isRegistered() && checkSdkLower16()){
//            binding.btnRegister.setClickable(false);
//            binding.btnRegister.setBackgroundDrawable(ContextCompat.getDrawable(mActivity, R.drawable.bg_register_service2));
//        } else if (!service.isRegistered() && !checkSdkLower16() && !isVipRegisted){
//            binding.btnRegister.setClickable(true);
//            binding.btnRegister.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.bg_register_service));
//        } else if (!service.isRegistered() && checkSdkLower16() && !isVipRegisted){
//            binding.btnRegister.setClickable(true);
//            binding.btnRegister.setBackgroundDrawable(ContextCompat.getDrawable(mActivity, R.drawable.bg_register_service));
//        }

        String title = "";
        switch (service.getCycle()) {
            case 0:
                title = "MIỄN PHÍ";
                break;
            case 1:
                title = "GIÁ CƯỚC: " + service.getPrice() + " VNĐ/NGÀY";
                break;
            case 7:
                title = "GIÁ CƯỚC: " + service.getPrice() + " VNĐ/TUẦN";
                break;
            case 30:
                title = "GIÁ CƯỚC: " + service.getPrice() + " VNĐ/THÁNG";
                break;
            default:
                title = "GIÁ CƯỚC: " + service.getPrice() + " VNĐ/" + service.getCycle() + " NGÀY";
                break;
        }


        binding.txtHeaderTitle.setText(title);


        binding.txtContent1.setText(service.getAbout());
        binding.txtContent2.setText(service.getTarget());
        binding.txtContent3.setText(service.getNotes());
    }

    private boolean checkSdkLower16() {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        return sdk < android.os.Build.VERSION_CODES.JELLY_BEAN;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_register:
                Account account = MyApplication.getInstance().getAccount();
                if (null != account && account.isLogin()) {
                    typeRegisterDialog = TypeRegisterDialog.newInstance(service);
                    typeRegisterDialog.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                    typeRegisterDialog.setCancelable(true);
                } else {
                    LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                }
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

}
