package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityTravelVoucherBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.fragment.f2travelvoucher.TravelVoucherFragment;

public class TravelVoucherActivity extends BaseActivityNew<F2ActivityTravelVoucherBinding> {
    private boolean isStore;
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_travel_voucher;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        isStore = getIntent().getBooleanExtra(Constants.IntentKey.DATA, false);
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        return new TravelVoucherFragment(isStore);
    }

    public static void openScreen(Context activity, boolean isStore) {
        Intent intent = new Intent(activity, TravelVoucherActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, isStore);
        activity.startActivity(intent);
    }
}
