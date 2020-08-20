package com.namviet.vtvtravel.view.fragment.f2callnow;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentNotiSettingBinding;
import com.namviet.vtvtravel.databinding.F2FragmentSettingContactBinding;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.fragment.MainFragment;

public class SettingNotiFragment extends MainFragment {
    private F2FragmentNotiSettingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_noti_setting, container, false);
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
        boolean b = PreferenceUtil.getInstance(mActivity).getValue(Constants.PrefKey.NOTI_ENABLE, true);
        if(b){
            binding.switchAutoUpdateContact.setChecked(true);
        }else {
            binding.switchAutoUpdateContact.setChecked(false);
        }
        setClick();
    }

    private void setClick() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        binding.switchAutoUpdateContact.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.NOTI_ENABLE, b);
                    }
                });
    }
}
