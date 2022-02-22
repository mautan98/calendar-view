package com.namviet.vtvtravel.view.fragment.f2video;

import android.content.Context;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.ActivitySearchResultContentSpecificBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;

public class ResultSearchVideoActivity extends BaseActivityNew<ActivitySearchResultContentSpecificBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.activity_search_result_content_specific;
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
        ResultSearchVideoFragment resultSearchVideoFragment = new ResultSearchVideoFragment();
        return resultSearchVideoFragment;
    }

    public static void openScreen(Context context){
        Intent intent = new Intent(context, ResultSearchVideoActivity.class);
        context.startActivity(intent);
    }
}
