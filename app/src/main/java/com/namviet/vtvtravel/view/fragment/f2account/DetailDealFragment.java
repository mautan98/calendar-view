package com.namviet.vtvtravel.view.fragment.f2account;

import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2FragmentDetailDealBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;

public class DetailDealFragment extends BaseFragment<F2FragmentDetailDealBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_detail_deal;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void setObserver() {

    }
}
