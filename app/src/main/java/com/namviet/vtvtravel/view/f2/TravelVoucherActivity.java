package com.namviet.vtvtravel.view.f2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityTravelVoucherBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse;
import com.namviet.vtvtravel.view.fragment.f2travelvoucher.TravelVoucherDetailFragment;
import com.namviet.vtvtravel.view.fragment.f2travelvoucher.TravelVoucherFragment;

public class TravelVoucherActivity extends BaseActivityNew<F2ActivityTravelVoucherBinding> {
    private boolean isStore;
    private int screenType;
    private boolean isFromRegVip;
    private ListVoucherResponse.Data.Voucher voucher;
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_travel_voucher;
    }

    public class OpenType {
        public static final int LIST = 0;
        public static final int DETAIL = 1;

    }
    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        screenType = getIntent().getIntExtra(Constants.IntentKey.SCREEN_TYPE, TravelVoucherActivity.OpenType.LIST);
        if (screenType == SmallLocationActivity.OpenType.LIST) {
            isStore = getIntent().getBooleanExtra(Constants.IntentKey.DATA, false);
            isFromRegVip = getIntent().getBooleanExtra(Constants.IntentKey.FROM_REG_VIP, false);
        }else {
            isStore = getIntent().getBooleanExtra(Constants.IntentKey.DATA, false);
            voucher = (ListVoucherResponse.Data.Voucher) getIntent().getSerializableExtra(Constants.IntentKey.VOUCHER);
        }
    }

    @Override
    public void doAfterOnCreate() {

    }

    @Override
    public void setClick() {

    }

    @Override
    public BaseFragment initFragment() {
        if(screenType == OpenType.LIST) {
            return new TravelVoucherFragment(isStore, isFromRegVip);
        }else {
            return new TravelVoucherDetailFragment(voucher, isStore, false);
        }
    }

    public static void openScreen(Context activity, boolean isStore, int screenType, boolean isFromRegVip) {
        Intent intent = new Intent(activity, TravelVoucherActivity.class);
        intent.putExtra(Constants.IntentKey.DATA, isStore);
        intent.putExtra(Constants.IntentKey.SCREEN_TYPE, screenType);
        intent.putExtra(Constants.IntentKey.FROM_REG_VIP, isFromRegVip);
        activity.startActivity(intent);
    }

    public static void startScreenDetail(Context activity, boolean isStore, int screenType, ListVoucherResponse.Data.Voucher voucher) {
        Intent intent = new Intent(activity, TravelVoucherActivity.class);
        intent.putExtra(Constants.IntentKey.SCREEN_TYPE, screenType);
        intent.putExtra(Constants.IntentKey.DATA, isStore);
        intent.putExtra(Constants.IntentKey.VOUCHER, voucher);
        activity.startActivity(intent);
    }
}
