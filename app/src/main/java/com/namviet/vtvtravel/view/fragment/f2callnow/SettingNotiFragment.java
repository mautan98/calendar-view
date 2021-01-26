package com.namviet.vtvtravel.view.fragment.f2callnow;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentNotiSettingBinding;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
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
            binding.btnEnable.setVisibility(View.VISIBLE);
            binding.btnDisable.setVisibility(View.GONE);
        }else {
            binding.btnEnable.setVisibility(View.GONE);
            binding.btnDisable.setVisibility(View.VISIBLE);
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


        binding.btnEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btnEnable.setVisibility(View.GONE);
                binding.btnDisable.setVisibility(View.VISIBLE);
                PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.NOTI_ENABLE, false);
            }
        });

        binding.btnDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btnEnable.setVisibility(View.VISIBLE);
                binding.btnDisable.setVisibility(View.GONE);
                PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.NOTI_ENABLE, true);
            }
        });
    }
}
