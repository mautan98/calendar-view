package com.namviet.vtvtravel.view.fragment.f2travelvoucher;

import android.annotation.SuppressLint;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.C;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.travelvoucher.TravelVoucherAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentTravelVoucherBinding;
import com.namviet.vtvtravel.databinding.F2FragmentTravelVoucherDetailBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse;
import com.namviet.vtvtravel.view.f2.VQMMWebviewActivity;
import com.namviet.vtvtravel.view.fragment.f2offline.CopyVoucherDialog;

public class TravelVoucherDetailFragment extends BaseFragment<F2FragmentTravelVoucherDetailBinding> {
    private TravelVoucherAdapter travelVoucherAdapter;
    private ListVoucherResponse.Data.Voucher voucher;
    private boolean isStore;

    @SuppressLint("ValidFragment")
    public TravelVoucherDetailFragment(ListVoucherResponse.Data.Voucher voucher, boolean isStore) {
        this.voucher = voucher;
        this.isStore = isStore;
    }

    public TravelVoucherDetailFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_travel_voucher_detail;
    }

    @Override
    public void initView() {
        if (isStore) {
            getBinding().btnSend.setText("Quay để nhận thưởng");
        } else {
            getBinding().btnSend.setText("Nhận thưởng ngay");
        }
    }

    @Override
    public void initData() {
        Glide.with(mActivity).load(voucher.getAvatarUri()).into(getBinding().imgAvatar);
        getBinding().tvName.setText(voucher.getName());
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        getBinding().btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStore) {
                    VQMMWebviewActivity.startScreen(mActivity);
                } else {
                    CopyVoucherDialog copyVoucherDialog = CopyVoucherDialog.newInstance(voucher, null);
                    copyVoucherDialog.show(mActivity.getSupportFragmentManager(), null);
                }
            }
        });

        getBinding().webView.loadDataWithBaseURL("", voucher.getContent(), "text/html", "UTF-8", null);
    }

    @Override
    public void setObserver() {

    }
}
