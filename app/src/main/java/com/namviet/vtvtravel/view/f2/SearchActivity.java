package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2ActivityServiceBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2search.SearchFragment;

public class SearchActivity extends BaseActivityNew<F2ActivityServiceBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_search;
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

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        return new SearchFragment();
    }

    public static void startScreen(Context activity) {
        Intent intent = new Intent(activity, SearchActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void afterSetContentView() {
        super.afterSetContentView();
        try {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
