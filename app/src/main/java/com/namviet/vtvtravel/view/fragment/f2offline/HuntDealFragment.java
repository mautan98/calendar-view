package com.namviet.vtvtravel.view.fragment.f2offline;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2offline.HuntDealAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentHuntDealBinding;
import com.namviet.vtvtravel.listener.F2ClickActionListener;
import com.namviet.vtvtravel.model.offline.Action;
import com.namviet.vtvtravel.model.offline.Items;
import com.namviet.vtvtravel.model.offline.OfflineDynamic;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.fragment.MainFragment;

import java.io.File;
import java.util.ArrayList;

public class HuntDealFragment extends MainFragment implements F2ClickActionListener {
    private F2FragmentHuntDealBinding binding;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_hunt_deal, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.SCREEN_VIEW, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.HUNT_DEAL_CALL_NOW, TrackingAnalytic.ScreenTitle.HUNT_DEAL_CALL_NOW).setScreen_class(this.getClass().getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    private void getParenLink(){
        parentLink = mActivity.getExternalFilesDir(null) + File.separator + "/VTVTravelDataCall_Now/Call_Now/";
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
        HuntDealAdapter huntDealAdapter = new HuntDealAdapter(mActivity, items, parentLink, this);
        binding.recyclerHuntDeal.setAdapter(huntDealAdapter);
        binding.recyclerHuntDeal.setNestedScrollingEnabled(false);
        try {
            huntDealAdapter.notifyItemChanged(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateViews() {
        super.updateViews();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setClick(){
//        binding.btnViewDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                RuleDialog ruleDialog = new RuleDialog();
//                ruleDialog.show(mActivity.getSupportFragmentManager(), null);
//            }
//        });
//
//        binding.btnCallNow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                F2Util.startCallIntent(mActivity, "1039");
//            }
//        });
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
