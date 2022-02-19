package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityFilterBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.response.f2filter.FilterByCodeResponse;
import com.namviet.vtvtravel.view.f3.smalllocation.view.fragment.FilterSmallLocationFragment;
import com.namviet.vtvtravel.view.fragment.f2filter.FilterHomeFragment;

public class FilterActivity extends BaseActivityNew<F2ActivityFilterBinding> {
    private FilterByCodeResponse filterByCodeResponse;
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_filter;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void doAfterOnCreate() {
        filterByCodeResponse = (FilterByCodeResponse) getIntent().getSerializableExtra(Constants.IntentKey.DATA);
    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        return new FilterSmallLocationFragment(filterByCodeResponse);
    }

    public static void startScreen(Activity activity, FilterByCodeResponse filterByCodeResponse){
        Intent intent = new Intent(activity, FilterActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, filterByCodeResponse);
        activity.startActivity(intent);
    }
}
