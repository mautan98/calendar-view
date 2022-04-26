package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityBigLocationBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.newhome.ItemHomeService;
import com.namviet.vtvtravel.view.f3.smalllocation.view.fragment.SmallLocationMainPageFragment;
import com.namviet.vtvtravel.view.fragment.f2smalllocation.DetailSmallLocationFragment;

import java.util.ArrayList;
import java.util.List;

public class SmallLocationActivity extends BaseActivityNew<F2ActivityBigLocationBinding> {
    private ArrayList<ItemHomeService<?>.Item> itemsMenu;
    ////

    private String linkToLoadSmallLocation;
    private String codeToLoadSmallLocation;
    private String regionIdToLoadSmallLocation;
    private String detailLink;
    private int screenType;
    private SmallLocationMainPageFragment smallLocationMainPageFragment;
    private int position;

    public class OpenType {
        public static final int LIST = 0;
        public static final int DETAIL = 1;

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
            itemsMenu = (ArrayList<ItemHomeService<?>.Item>) getIntent().getSerializableExtra(Constants.IntentKey.DATA);
            linkToLoadSmallLocation = getIntent().getStringExtra(Constants.IntentKey.LINK);
            codeToLoadSmallLocation = getIntent().getStringExtra(Constants.IntentKey.CODE);
            regionIdToLoadSmallLocation = getIntent().getStringExtra(Constants.IntentKey.REGION_ID);
            position = getIntent().getIntExtra(Constants.IntentKey.POSITION_2,0);
        }else {
            detailLink = getIntent().getStringExtra(Constants.IntentKey.DATA);
        }
    }


    @Override
    public void getLocationByService() {
        super.getLocationByService();
        getMainLocation();
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
        if (screenType == OpenType.LIST) {
//            return new SmallLocationFragment(linkToLoadSmallLocation, codeToLoadSmallLocation, regionIdToLoadSmallLocation);
            return smallLocationMainPageFragment = new SmallLocationMainPageFragment(itemsMenu,position, regionIdToLoadSmallLocation);
        }else {
            return new DetailSmallLocationFragment(detailLink);
        }
    }

//    public static void startScreen(Context activity, String link, String code, int screenType, String regionIdToLoadSmallLocation) {
//        Intent intent = new Intent(activity, SmallLocationActivity.class);
//        intent.putExtra(Constants.IntentKey.LINK, link);
//        intent.putExtra(Constants.IntentKey.CODE, code);
//        intent.putExtra(Constants.IntentKey.REGION_ID, regionIdToLoadSmallLocation);
//        intent.putExtra(Constants.IntentKey.SCREEN_TYPE, screenType);
//        activity.startActivity(intent);
//    }


    public static void startScreen(Context activity, int screenType, ArrayList<ItemHomeService.Item> items, String code, int position, String regionIdToLoadSmallLocation){
        Intent intent = new Intent(activity, SmallLocationActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, items);
        intent.putExtra(Constants.IntentKey.CODE, code);
        intent.putExtra(Constants.IntentKey.POSITION_2, position);
        intent.putExtra(Constants.IntentKey.SCREEN_TYPE, screenType);
        intent.putExtra(Constants.IntentKey.REGION_ID, regionIdToLoadSmallLocation);
        activity.startActivity(intent);
    }

    public static void startScreen(Context activity, int screenType, ArrayList<ItemHomeService.Item> items, String code, int position){
        Intent intent = new Intent(activity, SmallLocationActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, items);
        intent.putExtra(Constants.IntentKey.CODE, code);
        intent.putExtra(Constants.IntentKey.POSITION_2, position);
        intent.putExtra(Constants.IntentKey.SCREEN_TYPE, screenType);
        activity.startActivity(intent);
    }

    public static void startScreenDetail(Context activity, int screenType, String detailLink) {
        Intent intent = new Intent(activity, SmallLocationActivity.class);
        intent.putExtra(Constants.IntentKey.SCREEN_TYPE, screenType);
        intent.putExtra(Constants.IntentKey.DATA, detailLink);
        activity.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
//        if(smallLocationMainPageFragment != null && smallLocationMainPageFragment.getStateMapView() == View.VISIBLE){
//            smallLocationMainPageFragment.hideMapView();
//            return;
//        }
//        else
            super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
