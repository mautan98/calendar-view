package com.namviet.vtvtravel.view.fragment.f2account;

import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2FragmentRulesBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;

public class RulesFragment extends BaseFragment<F2FragmentRulesBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_rules;
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
