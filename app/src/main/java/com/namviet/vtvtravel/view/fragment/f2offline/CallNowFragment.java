package com.namviet.vtvtravel.view.fragment.f2offline;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.menu.SlideMenu;
import com.bumptech.glide.Glide;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2offline.CallNowAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentCallNowBinding;
import com.namviet.vtvtravel.databinding.F2FragmentMainOfflineBinding;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.offline.Items;
import com.namviet.vtvtravel.model.offline.OfflineDynamic;
import com.namviet.vtvtravel.view.fragment.MainFragment;

import java.io.File;
import java.util.ArrayList;

public class CallNowFragment  extends MainFragment {
    private String parentLink;
    private F2FragmentCallNowBinding binding;
    private int fromCallNow;
    private OfflineDynamic offlineDynamic;
    private int id;
    public void setId(int id) {
        this.id = id;
    }

    private ArrayList<Items> items = new ArrayList<>();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_call_now, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getParenLink();
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        setData();
        updateViews();

    }

    @Override
    protected void updateViews() {
        super.updateViews();
        setClick();
    }

    private void setClick() {
        binding.imgBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fromCallNow == Constants.TypeOpenOffline.FROM_USER_SCREEN){
                    mActivity.onBackPressed();
                    mActivity.onBackPressed();
                }else if(fromCallNow == Constants.TypeOpenOffline.FROM_LEARN_MORE){
                    mActivity.onBackPressed();
                } else {
                    Account account = MyApplication.getInstance().getAccount();
                    if (null != account && account.isLogin()) {
                        mActivity.switchFragment(SlideMenu.MenuType.MAIN_CALL_NOW_SCREEN);
                    } else {
                        mActivity.switchFragment(SlideMenu.MenuType.LOGIN_SCREEN);
                    }
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setFromCallNow(int fromCallNow) {
        this.fromCallNow = fromCallNow;
    }

    private void getParenLink(){
        parentLink = mActivity.getExternalFilesDir(null) + File.separator + "/VTVTravelDataCall_Now/Call_Now/";
    }

    private void setData(){
        int position = 0;
        for (int i = 0; i < offlineDynamic.getListScreens().size(); i++) {
            if(String.valueOf(id).equals( offlineDynamic.getListScreens().get(i).getId())){
                position = i;
                break;
            }
        }
        Glide.with(mActivity).load(parentLink+offlineDynamic.getListScreens().get(position).getBanner().getImage()).into(binding.imgBanner);
        binding.tvDescription.setText(offlineDynamic.getListScreens().get(position).getDescription());
        items = (ArrayList<Items>) offlineDynamic.getListScreens().get(position).getItems();
        CallNowAdapter callNowAdapter = new CallNowAdapter(mActivity, items, parentLink);
        binding.recyclerCallNow.setAdapter(callNowAdapter);
        binding.recyclerCallNow.setNestedScrollingEnabled(false);
    }


}
