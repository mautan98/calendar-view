package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityBigLocationBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2biglocation.BigLocationFragment;
import com.namviet.vtvtravel.view.fragment.f2biglocation.SearchBigLocationFragment;

public class BigLocationActivity extends BaseActivityNew<F2ActivityBigLocationBinding> {
    private boolean openSearch;
    private String regionId;
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
        regionId = getIntent().getStringExtra(Constants.IntentKey.DATA);
        openSearch = getIntent().getBooleanExtra(Constants.IntentKey.OPEN_SEARCH, false);
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        if (openSearch) {
            return new SearchBigLocationFragment();
        }else {
            return new BigLocationFragment(regionId);
        }
    }

    public static void startScreen(Activity activity, String regionId, boolean openSearch){
        Intent intent = new Intent(activity, BigLocationActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, regionId);
        intent.putExtra(Constants.IntentKey.OPEN_SEARCH, openSearch);
        activity.startActivity(intent);
    }
}
