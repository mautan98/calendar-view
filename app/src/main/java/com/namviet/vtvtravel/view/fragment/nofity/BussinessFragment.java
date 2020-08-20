package com.namviet.vtvtravel.view.fragment.nofity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.FragmentNotifyBussinessBinding;
import com.namviet.vtvtravel.view.fragment.MainFragment;

public class BussinessFragment extends MainFragment {
    public static BussinessFragment newInstance() {
        BussinessFragment fragment = new BussinessFragment();
        return fragment;
    }

    private FragmentNotifyBussinessBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notify_bussiness, container, false);
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
    }

    @Override
    protected void updateViews() {
        super.updateViews();
    }
}
