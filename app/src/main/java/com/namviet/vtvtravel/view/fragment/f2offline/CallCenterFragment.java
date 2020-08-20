package com.namviet.vtvtravel.view.fragment.f2offline;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2offline.CallCenterAdapter;
import com.namviet.vtvtravel.adapter.f2offline.ServicePackageAdapter;
import com.namviet.vtvtravel.adapter.f2offline.TopPackageAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentMainOfflineBinding;
import com.namviet.vtvtravel.databinding.F2FragmentSwitchboardBinding;
import com.namviet.vtvtravel.listener.F2ClickActionListener;
import com.namviet.vtvtravel.model.offline.Action;
import com.namviet.vtvtravel.model.offline.Items;
import com.namviet.vtvtravel.model.offline.OfflineDynamic;
import com.namviet.vtvtravel.model.offline.Package;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.fragment.MainFragment;

import java.io.File;
import java.util.ArrayList;

public class CallCenterFragment extends MainFragment implements F2ClickActionListener {
    private F2FragmentSwitchboardBinding binding;
    private OfflineDynamic offlineDynamic;
    private String parentLink;
    private ArrayList<Items> items = new ArrayList<>();
    private int id;
    public void setId(int id) {
        this.id = id;
    }
    public void setOfflineDynamic(OfflineDynamic offlineDynamic) {
        this.offlineDynamic = offlineDynamic;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_switchboard, container, false);
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
        getParenLink();
        setData();
        updateViews();
        setClick();

    }

    private void setClick() {
        binding.layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                F2Util.startCallIntent(mActivity, "1039");
            }
        });
    }

    @Override
    protected void updateViews() {
        super.updateViews();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setData() {
        int position = 0;
        for (int i = 0; i < offlineDynamic.getListScreens().size(); i++) {
            if(String.valueOf(id).equals( offlineDynamic.getListScreens().get(i).getId())){
                position = i;
                break;
            }
        }
        Glide.with(mActivity).load(parentLink+offlineDynamic.getListScreens().get(position).getBanner().getImage()).into(binding.imgBanner);
        items = (ArrayList<Items>) offlineDynamic.getListScreens().get(position).getItems();
        CallCenterAdapter callCenterAdapter = new CallCenterAdapter(mActivity, items, parentLink, this);
        binding.rclContent.setAdapter(callCenterAdapter);
        binding.rclContent.setNestedScrollingEnabled(false);
    }

    private void getParenLink(){
        parentLink = mActivity.getExternalFilesDir(null) + File.separator + "/VTVTravelDataCall_Now/Call_Now/";
    }

    @Override
    public void onClickF2ClickActionListener(Action action) {
        if (action.getType().equals("call")) {
            F2Util.startCallIntent(mActivity, action.getPhone());
        } else if (action.getType().equals("sms")) {
            F2Util.startSendMessIntent(mActivity, action.getPhone(), action.getCode());
        } else if (action.getType().equals("html")) {
            RuleDialog ruleDialog = new RuleDialog();
            ruleDialog.setOfflineDynamic(action.getCode());
            ruleDialog.show(mActivity.getSupportFragmentManager(), null);
        }
    }
}
