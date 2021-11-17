package com.namviet.vtvtravel.view.f3.deal.view.listdeal;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.vtvtabstyle.VTVTabStyleAdapter;
import com.namviet.vtvtravel.databinding.DialogCityBindingImpl;
import com.namviet.vtvtravel.databinding.FragmentListDealBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.view.f3.deal.adapter.DealTabStyleAdapter;
import com.namviet.vtvtravel.view.f3.deal.constant.IsProcessingType;
import com.namviet.vtvtravel.view.f3.deal.model.Block;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.DealMenuDialog;

import java.util.ArrayList;

public class ListDealFragment extends BaseFragment<FragmentListDealBinding> {
    private ArrayList<Block> listBlock = new ArrayList<>();
    private DealTabStyleAdapter mainAdapter;
    private String isProcessing = "1";
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_list_deal;
    }

    @Override
    public void initView() {
        getBinding().check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isProcessing = IsProcessingType.SAP_DIEN_RA_TYPE;
                }else {
                    isProcessing = IsProcessingType.DANG_DIEN_RA_TYPE;
                }
            }
        });
    }

    @Override
    public void initData() {
        mainAdapter = new DealTabStyleAdapter(getChildFragmentManager());

        for (int i = 0; i < listBlock.size(); i++) {
            ListDealTabFragment listDealTabFragment = new ListDealTabFragment(listBlock.get(i));
            listDealTabFragment.setIsProcessing(isProcessing);
            mainAdapter.addFragment(listDealTabFragment, "");
        }

        getBinding().vpContent.setAdapter(mainAdapter);
        getBinding().tabLayout.setupWithViewPager(getBinding().vpContent);


        for (int i = 0; i < listBlock.size(); i++) {
            View tabHome = LayoutInflater.from(mActivity).inflate(R.layout.f3_item_header1, null);
            TextView tvHome = tabHome.findViewById(R.id.tv_title);
            tvHome.setText(listBlock.get(i).getName());
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
        getBinding().vpContent.setOffscreenPageLimit(4);
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

    public void setListBlock(ArrayList<Block> listBlock) {
        this.listBlock = listBlock;
    }
}
