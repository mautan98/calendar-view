package com.namviet.vtvtravel.view.f3.deal.view.listhotdeal;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.vtvtabstyle.VTVTabStyleAdapter;
import com.namviet.vtvtravel.databinding.DialogCityBindingImpl;
import com.namviet.vtvtravel.databinding.FragmentListDealBinding;
import com.namviet.vtvtravel.databinding.FragmentListHotDealBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.f3.deal.adapter.DealTabStyleAdapter;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.DealMenuDialog;
import com.namviet.vtvtravel.view.f3.deal.view.listdeal.ListDealTabFragment;

public class ListHotDealFragment extends BaseFragment<FragmentListHotDealBinding> {
    private DealTabStyleAdapter mainAdapter;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_list_hot_deal;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        mainAdapter = new DealTabStyleAdapter(getChildFragmentManager());

        for (int i = 0; i < 2; i++) {
            ListDealTabFragment listDealTabFragment = new ListDealTabFragment();
            mainAdapter.addFragment(listDealTabFragment, "");
        }

        getBinding().vpContent.setAdapter(mainAdapter);
        getBinding().tabLayout.setupWithViewPager(getBinding().vpContent);


        for (int i = 0; i < 2; i++) {
            View tabHome = LayoutInflater.from(mActivity).inflate(R.layout.f3_item_header1, null);
            TextView tvHome = tabHome.findViewById(R.id.tv_title);
            tvHome.setText("Ăn gì");
            if (i == 0) {
                tvHome.setTextColor(Color.parseColor("#00918D"));
            } else {
                tvHome.setTextColor(Color.parseColor("#101010"));
            }
//            View view = tabHome.findViewById(R.id.indicator);
//            if (i == 0) {
//                view.setVisibility(View.VISIBLE);
//            } else {
//                view.setVisibility(View.INVISIBLE);
//            }
            getBinding().tabLayout.getTabAt(i).setCustomView(tabHome);
        }
        getBinding().tabLayout.addOnTabSelectedListener(OnTabSelectedListener);
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

        getBinding().btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new DealMenuDialog());
            }
        });
    }

    @Override
    public void setObserver() {

    }

    private TabLayout.OnTabSelectedListener OnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int c = tab.getPosition();
            mainAdapter.SetOnSelectView(getBinding().tabLayout, c);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            int c = tab.getPosition();
            mainAdapter.SetUnSelectView(getBinding().tabLayout, c);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };
}
