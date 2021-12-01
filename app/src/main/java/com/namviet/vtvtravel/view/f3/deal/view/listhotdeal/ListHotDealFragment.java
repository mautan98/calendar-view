package com.namviet.vtvtravel.view.f3.deal.view.listhotdeal;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.vtvtabstyle.VTVTabStyleAdapter;
import com.namviet.vtvtravel.databinding.DialogCityBindingImpl;
import com.namviet.vtvtravel.databinding.FragmentListDealBinding;
import com.namviet.vtvtravel.databinding.FragmentListHotDealBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.f2booking.DataHelpCenter;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.f2.MyGiftActivity;
import com.namviet.vtvtravel.view.f3.deal.adapter.DealTabStyleAdapter;
import com.namviet.vtvtravel.view.f3.deal.adapter.GridDealInDealHomeAdapter;
import com.namviet.vtvtravel.view.f3.deal.event.FinishDeal;
import com.namviet.vtvtravel.view.f3.deal.model.Block;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.DealMenuDialog;
import com.namviet.vtvtravel.view.f3.deal.view.dealhome.DealSubcribeFragment;
import com.namviet.vtvtravel.view.f3.deal.view.listdeal.ListDealTabFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class ListHotDealFragment extends BaseFragment<FragmentListHotDealBinding> {
    private ArrayList<Block> listBlock = new ArrayList<>();
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

        for (int i = 0; i < listBlock.size(); i++) {
            ListHotDealTabFragment listDealTabFragment = new ListHotDealTabFragment(listBlock.get(i));
            mainAdapter.addFragment(listDealTabFragment, "");
        }

        getBinding().vpContent.setAdapter(mainAdapter);
        getBinding().tabLayout.setupWithViewPager(getBinding().vpContent);


        for (int i = 0; i < listBlock.size(); i++) {
            View tabHome = LayoutInflater.from(mActivity).inflate(R.layout.f3_item_tab_deal_home, null);
            TextView tvHome = tabHome.findViewById(R.id.tv_title);
            View view = tabHome.findViewById(R.id.v_selected);
            tvHome.setText(listBlock.get(i).getName());
            if (i == 0) {
                tvHome.setTextColor(Color.parseColor("#303030"));
                view.setVisibility(View.VISIBLE);
            } else {
                tvHome.setTextColor(Color.parseColor("#9F9F9F"));
                view.setVisibility(View.INVISIBLE);
            }
            getBinding().tabLayout.getTabAt(i).setCustomView(tabHome);
        }
        getBinding().tabLayout.addOnTabSelectedListener(OnTabSelectedListener);
        getBinding().vpContent.setOffscreenPageLimit(4);

        setTitleText();
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
                DealMenuDialog dealMenuDialog = new DealMenuDialog();
                dealMenuDialog.setClickListener(new DealMenuDialog.Click() {
                    @Override
                    public void onClickRule() {
                        Toast.makeText(mActivity, "Thể lệ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onClickSubscribeDeal() {
                        DealSubcribeFragment dealSubcribeFragment = new DealSubcribeFragment();
                        dealSubcribeFragment.setLocation(1);
                        addFragment(dealSubcribeFragment);
                    }

                    @Override
                    public void onClickHelpCenter() {
                        DataHelpCenter dataHelpCenter = new Gson().fromJson(F2Util.loadJSONFromAsset(mActivity, "helpcenter_pro"), DataHelpCenter.class);
                        MyGiftActivity.startScreen(mActivity, dataHelpCenter.getItemMenus(), dataHelpCenter.getName());
                    }

                    @Override
                    public void onClickGoDealHome() {
                        mActivity.onBackPressed();
                    }

                    @Override
                    public void onClickGoHome() {
                        mActivity.onBackPressed();
                        EventBus.getDefault().post(new FinishDeal());
                    }
                });
                addFragment(dealMenuDialog);
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
            setOnSelectView(getBinding().tabLayout, c);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            int c = tab.getPosition();
            setUnSelectView(getBinding().tabLayout, c);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    public void setListBlock(ArrayList<Block> listBlock) {
        this.listBlock = listBlock;
    }

    public void setOnSelectView(TabLayout tabLayout, int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        TextView iv_text = selected.findViewById(R.id.tv_title);
        View view = selected.findViewById(R.id.v_selected);
        view.setVisibility(View.VISIBLE);
        iv_text.setTextColor(Color.parseColor("#303030"));

    }

    public void setUnSelectView(TabLayout tabLayout, int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        TextView iv_text = selected.findViewById(R.id.tv_title);
        View view = selected.findViewById(R.id.v_selected);
        view.setVisibility(View.INVISIBLE);
        iv_text.setTextColor(Color.parseColor("#9F9F9F"));

    }

    private void setTitleText() {
        try {
            if (listBlock.get(0).getLink().contains("campaigns")){
                getBinding().tvTitle.setText("Tích luỹ nhận quà");
                getBinding().tvDescription.setText("Tích luỹ ngay");
            }else {
                getBinding().tvTitle.setText("Săn quà");
                getBinding().tvDescription.setText("Săn quà ngay");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
