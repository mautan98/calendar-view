package com.namviet.vtvtravel.view.fragment.f2service;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.utils.KeyboardUtils;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.FragmentRegisterSuccessBinding;
import com.namviet.vtvtravel.model.f2event.OnReloadCountSystemInbox;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f2.TravelVoucherActivity;
import com.namviet.vtvtravel.view.fragment.MainFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

public class RegisterSuccessFragment extends MainFragment {
    private FragmentRegisterSuccessBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_success, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnBack.setOnClickListener(this);
        EventBus.getDefault().post(new OnReloadCountSystemInbox());
        try {
            KeyboardUtils.hideKeyboard(mActivity, Objects.requireNonNull(mActivity.getCurrentFocus()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.REGISTER_PACKAGE_SUCCESS, TrackingAnalytic.ScreenTitle.REGISTER_PACKAGE_SUCCESS).setScreen_class(this.getClass().getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_back:
//                EventBus.getDefault().post(new OnRegisterVipSuccess());
                TravelVoucherActivity.openScreen(mActivity, true, TravelVoucherActivity.OpenType.LIST, true);
                mActivity.finish();
                break;
        }
    }


}
