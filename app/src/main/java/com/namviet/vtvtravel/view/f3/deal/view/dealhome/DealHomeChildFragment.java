package com.namviet.vtvtravel.view.f3.deal.view.dealhome;

import android.util.Log;
import android.view.View;

import com.brandongogetap.stickyheaders.StickyLayoutManager;
import com.brandongogetap.stickyheaders.exposed.StickyHeaderListener;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.FragmentDealHomeChildBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.f3.deal.adapter.RecyclerAdapter;

public class DealHomeChildFragment extends BaseFragment<FragmentDealHomeChildBinding> {
    private RecyclerAdapter adapter;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_deal_home_child;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        adapter = new RecyclerAdapter();
        adapter.setData(ItemGenerator.demoList(), mActivity);
        StickyLayoutManager layoutManager = new TopSnappedStickyLayoutManager(mActivity, adapter);
        layoutManager.elevateHeaders(false); // Default elevation of 5dp
        // You can also specify a specific dp for elevation
//        layoutManager.elevateHeaders(10);
        getBinding().recyclerView.setLayoutManager(layoutManager);
        getBinding().recyclerView.setAdapter(adapter);
        layoutManager.setStickyHeaderListener(new StickyHeaderListener() {
            @Override
            public void headerAttached(View headerView, int adapterPosition) {
                Log.d("Listener", "Attached with position: " + adapterPosition);
            }

            @Override
            public void headerDetached(View headerView, int adapterPosition) {
                Log.d("Listener", "Detached with position: " + adapterPosition);
            }
        });
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {

    }

    @Override
    public void setObserver() {

    }
}
