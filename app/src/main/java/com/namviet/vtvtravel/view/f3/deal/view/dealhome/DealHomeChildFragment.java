 package com.namviet.vtvtravel.view.f3.deal.view.dealhome;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.brandongogetap.stickyheaders.StickyLayoutManager;
import com.brandongogetap.stickyheaders.exposed.StickyHeaderListener;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.FragmentDealHomeChildBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.f3.deal.adapter.F3Header1Adapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.F3Header1Adapter2;
import com.namviet.vtvtravel.view.f3.deal.adapter.F3Header2Adapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.F3Header2Adapter2;
import com.namviet.vtvtravel.view.f3.deal.adapter.RecyclerAdapter;
import com.namviet.vtvtravel.view.f3.deal.model.OnClickTabHeader1;
import com.namviet.vtvtravel.view.f3.deal.model.OnClickTabHeader2;
import com.namviet.vtvtravel.widget.PreCachingLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DealHomeChildFragment extends BaseFragment<FragmentDealHomeChildBinding> {


    private RecyclerAdapter adapter;
    private F3Header1Adapter2 f3Header1Adapter2;
    private F3Header2Adapter2 f3Header2Adapter2;
    private RecyclerView rclHeader1;
    private RecyclerView rclHeader2;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_deal_home_child;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        f3Header1Adapter2 = new F3Header1Adapter2(0, ItemGenerator.demoTabHeader1(), mActivity, new F3Header1Adapter.ClickTab() {
            @Override
            public void onClickTab(int position) {
                adapter.setPositionSelected1(position);
                adapter.notifyItemChanged(0);
            }
        }, true);
        f3Header2Adapter2 = new F3Header2Adapter2(0, ItemGenerator.demoTabHeader1(), mActivity, new F3Header2Adapter.ClickTab() {
            @Override
            public void onClickTab(int position) {
                adapter.setPositionSelected2(position);
                adapter.notifyItemChanged(2);
            }
        }, false);
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
                if(adapterPosition == 0){
                    rclHeader1 = headerView.findViewById(R.id.rcv_tab_header1);
                    rclHeader1.setAdapter(f3Header1Adapter2);
                }else if(adapterPosition == 2){
                    rclHeader2 = headerView.findViewById(R.id.rcv_tab_header1);
                    rclHeader2.setAdapter(f3Header2Adapter2);
                }
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

    @Override
    public void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onClickTabHeader1(OnClickTabHeader1 onClickTabHeader1){
        f3Header1Adapter2.setSelectedItem(onClickTabHeader1.getPosition());
    }

    @Subscribe
    public void onClickTabHeader2(OnClickTabHeader2 onClickTabHeader1){
        f3Header2Adapter2.setSelectedItem(onClickTabHeader1.getPosition());
    }
}
