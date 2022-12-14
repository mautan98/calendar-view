package com.namviet.vtvtravel.view.fragment.f2callnow;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2FragmentSettingCallnowBinding;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.fragment.MainFragment;

public class SettingCallNowFragment extends MainFragment {

    private F2FragmentSettingCallnowBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_setting_callnow, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.CALL_NOW_SETTING, TrackingAnalytic.ScreenTitle.CALL_NOW_SETTING).setScreen_class(this.getClass().getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        setClick();
    }

    private void setClick() {
        binding.btnSettingNoti.setOnClickListener(view -> mActivity.getSupportFragmentManager().beginTransaction().add(R.id.frame, new SettingNotiFragment()).addToBackStack(null).commit());

        binding.btnSettingContact.setOnClickListener(view -> mActivity.getSupportFragmentManager().beginTransaction().add(R.id.frame, new SettingContactFragment()).addToBackStack(null).commit());

        binding.btnBack.setOnClickListener(view -> mActivity.onBackPressed());
    }


}
