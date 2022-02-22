package com.namviet.vtvtravel.view.fragment.f2search;

import android.view.View;

import androidx.fragment.app.FragmentTransaction;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F3LayoutSearchDestinationBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;

public class ChooseRegionFragment extends BaseFragment<F3LayoutSearchDestinationBinding> {
    @Override
    public int getLayoutRes() {
        return R.layout.f3_layout_search_destination;
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
        getBinding().btnCloseFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).remove(ChooseRegionFragment.this).commit();
            }
        });
    }

    @Override
    public void setObserver() {

    }
}
