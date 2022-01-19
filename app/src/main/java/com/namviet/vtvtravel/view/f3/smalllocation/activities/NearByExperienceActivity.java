package com.namviet.vtvtravel.view.f3.smalllocation.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityBigLocationBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.newhome.ItemHomeService;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.view.f3.smalllocation.view.fragment.SmallLocationMainPageFragment;
import com.namviet.vtvtravel.view.fragment.f2smalllocation.DetailSmallLocationFragment;
import com.namviet.vtvtravel.view.fragment.f2smalllocation.ListNearByFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NearByExperienceActivity extends BaseActivityNew<F2ActivityBigLocationBinding> {
    private String linkToLoadSmallLocation;
    private int screenType;
    private  List<Travel> items ;
    public class OpenType {
        public static final int LIST = 0;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_big_location;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        screenType = getIntent().getIntExtra(Constants.IntentKey.SCREEN_TYPE, OpenType.LIST);
        if (screenType == OpenType.LIST) {
           // linkToLoadSmallLocation = getIntent().getStringExtra(Constants.IntentKey.LINK);
            Bundle bundle = getIntent().getBundleExtra(Constants.IntentKey.DATA);
            items = (List<Travel>) bundle.getSerializable("bundle");
        }
    }


    @Override
    public void getLocationByService() {
        super.getLocationByService();
    }

    @Override
    public void doAfterOnCreate() {
        setTransparentStatusBar();
    }

    @Override
    public void setClick() {

    }


    @Override
    public BaseFragment initFragment() {
//        if (screenType == OpenType.LIST) {
////            return new SmallLocationFragment(linkToLoadSmallLocation, codeToLoadSmallLocation, regionIdToLoadSmallLocation);
//            return new ListNearByFragment(linkToLoadSmallLocation);
//        }
       // return new ListNearByFragment(linkToLoadSmallLocation);
        return new ListNearByFragment(items);
    }

    public static void startScreenDetail(Context activity, int screenType, String detailLink) {
        Intent intent = new Intent(activity, NearByExperienceActivity.class);
        intent.putExtra(Constants.IntentKey.SCREEN_TYPE, screenType);
        intent.putExtra(Constants.IntentKey.DATA, detailLink);
        activity.startActivity(intent);
    }
    public static void startScreenDetail(Context activity, int screenType, List<Travel> items ) {
        Intent intent = new Intent(activity, NearByExperienceActivity.class);
        intent.putExtra(Constants.IntentKey.SCREEN_TYPE, screenType);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bundle", (Serializable) items);
        intent.putExtra(Constants.IntentKey.DATA, bundle);
        activity.startActivity(intent);
    }


}
