package com.namviet.vtvtravel.view.fragment.f2offline;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentMainOfflineBinding;
import com.namviet.vtvtravel.model.offline.OfflineDynamic;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.fragment.MainFragment;

import java.io.IOException;
import java.io.InputStream;

public class MainOfflineFragment extends MainFragment {

    private F2FragmentMainOfflineBinding binding;
    private int position;
    private int fromCallNow;
    private OfflineDynamic offlineDynamic;

    public static MainOfflineFragment newInstance(Bundle bundle) {
        MainOfflineFragment fragment = new MainOfflineFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            position = getArguments().getInt(Constants.IntentKey.KEY_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_main_offline, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    protected void initViews(View v) {
        super.initViews(v);
        offlineDynamic = mActivity.offlineDynamic;
        updateViews();
        setClick();
        renderViewPager();
        renderTabLayout();
        binding.vpMainOffline.setCurrentItem(getPositionViewpager(position));
    }

    private int getPositionViewpager(int id){
        int position = 0;
        for (int i = 0; i < offlineDynamic.getListScreens().size(); i++) {
            if(String.valueOf(id).equals( offlineDynamic.getListScreens().get(i).getId())){
                position = i;
                break;
            }
        }
        return position;
    }

    private void setClick() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
    }


    private void renderViewPager() {
        MainAdapter mainAdapter = new MainAdapter(getChildFragmentManager());
//        CallNowFragment callNowFragment = new CallNowFragment();
//        callNowFragment.setFromCallNow(fromCallNow);
//        callNowFragment.setOfflineDynamic(offlineDynamic);
//        mainAdapter.addFragment(callNowFragment, "callNowFragment");
//
//        HuntDealFragment huntDealFragment = new HuntDealFragment();
//        huntDealFragment.setOfflineDynamic(offlineDynamic);
//        mainAdapter.addFragment(huntDealFragment, "huntDealFragment");
//
//        ServicePackageFragment servicePackageFragment = new ServicePackageFragment();
//        servicePackageFragment.setOfflineDynamic(offlineDynamic);
//        mainAdapter.addFragment(servicePackageFragment, "servicePackageFragment");
//
//        CallCenterFragment callCenterFragment = new CallCenterFragment();
//        callCenterFragment.setOfflineDynamic(offlineDynamic);
//        mainAdapter.addFragment(callCenterFragment, "servicePackageFragment");

        for (int i = 0; i < offlineDynamic.getListScreens().size(); i++) {
            if (offlineDynamic.getListScreens().get(i).getId().equals("1")) {
                CallNowFragment callNowFragment = new CallNowFragment();
                callNowFragment.setFromCallNow(fromCallNow);
                callNowFragment.setOfflineDynamic(offlineDynamic);
                callNowFragment.setId(1);
                mainAdapter.addFragment(callNowFragment, "callNowFragment");
            } else if (offlineDynamic.getListScreens().get(i).getId().equals("2")) {
                HuntDealFragment huntDealFragment = new HuntDealFragment();
                huntDealFragment.setOfflineDynamic(offlineDynamic);
                huntDealFragment.setId(2);
                mainAdapter.addFragment(huntDealFragment, "huntDealFragment");
            } else if (offlineDynamic.getListScreens().get(i).getId().equals("3")) {
                ServicePackageFragment servicePackageFragment = new ServicePackageFragment();
                servicePackageFragment.setOfflineDynamic(offlineDynamic);
                servicePackageFragment.setId(3);
                mainAdapter.addFragment(servicePackageFragment, "servicePackageFragment");
            } else {
                CallCenterFragment callCenterFragment = new CallCenterFragment();
                callCenterFragment.setId(4);
                callCenterFragment.setOfflineDynamic(offlineDynamic);
                mainAdapter.addFragment(callCenterFragment, "servicePackageFragment");
            }
        }

        binding.vpMainOffline.setAdapter(mainAdapter);
//
    }


    private void renderTabLayout() {
        binding.tabLayout.setTabTextColors(ContextCompat.getColor(mActivity, R.color.md_black_1000), ContextCompat.getColor(mActivity, R.color.f2_color_package));
        binding.tabLayout.setupWithViewPager(binding.vpMainOffline);
//        binding.tabLayout.getTabAt(0).setText("Call Now");
//        binding.tabLayout.getTabAt(1).setText("Săn deal");
//        binding.tabLayout.getTabAt(2).setText("Gói dịch vụ");
//        binding.tabLayout.getTabAt(3).setText("Tổng đài 1039");

        for (int i = 0; i < offlineDynamic.getListScreens().size(); i++) {
            binding.tabLayout.getTabAt(i).setText(offlineDynamic.getListScreens().get(i).getName());
        }
    }

    //    @Override
    protected void updateViews() {
        super.updateViews();
        binding.vpMainOffline.setOffscreenPageLimit(4);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setFromCallNow(int fromCallNow) {
        this.fromCallNow = fromCallNow;
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("offline.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
