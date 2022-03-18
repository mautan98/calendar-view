package com.namviet.vtvtravel.view.f3.deal.view.dealrule;

import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.FragmentDealRuleBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;

public class DealRuleFragment extends BaseFragment<FragmentDealRuleBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_deal_rule;
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
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void setObserver() {

    }
}
