package com.namviet.vtvtravel.view.fragment.f2callnow;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2callnow.MissingCallHistoryAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentUserBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.f2event.OnBackToHome;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.view.fragment.f2offline.MainOfflineFragment;

import org.greenrobot.eventbus.EventBus;

public class UserFragment extends MainFragment {
    private F2FragmentUserBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_user, container, false);
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
        updateViews();
        setClick();
    }

    private void setClick(){
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        binding.layoutBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
                EventBus.getDefault().post(new OnBackToHome());
            }
        });

        binding.layoutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.getSupportFragmentManager().beginTransaction().add(R.id.frame, new SettingCallNowFragment()).addToBackStack(null).commit();
            }
        });

        binding.layoutIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainOfflineFragment mainOfflineFragment = new MainOfflineFragment();
                mainOfflineFragment.setFromCallNow(Constants.TypeOpenOffline.FROM_USER_SCREEN);
                mActivity.getSupportFragmentManager().beginTransaction().add(R.id.frame, mainOfflineFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        try {
            Account account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin()) {
                if(account.getImageProfile()!=null && !account.getImageProfile().isEmpty()) {
                    Glide.with(mActivity).load(account.getImageProfile()).apply(new RequestOptions().circleCrop()).into(binding.imgAvatar);
                }
                binding.tvName.setText(account.getFullname());
                binding.tvPhone.setText(String.valueOf(account.getMobile()));
            }else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
