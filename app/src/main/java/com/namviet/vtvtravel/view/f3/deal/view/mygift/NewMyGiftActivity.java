package com.namviet.vtvtravel.view.f3.deal.view.mygift;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.ActivityDetailDealBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;


public class NewMyGiftActivity extends BaseActivityNew<ActivityDetailDealBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.activity_my_gift;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        getWindow().getDecorView().setSystemUiVisibility (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
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
        MyGiftFragment myGiftFragment  = new MyGiftFragment();
        return myGiftFragment;
    }
    public static void startScreen(Context activity) {
        Intent intent = new Intent(activity, NewMyGiftActivity.class);
        activity.startActivity(intent);
    }
}
