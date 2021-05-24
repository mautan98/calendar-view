package com.namviet.vtvtravel.view.fragment.f2travelnote;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.vtvtabstyle.VTVTabStyleAdapter;
import com.namviet.vtvtravel.databinding.FragmentRelationNewsBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.response.travelnews.DetailTravelNewsResponse;

import java.util.ArrayList;
import java.util.List;

public class RelationNewsFragment extends BaseFragment<FragmentRelationNewsBinding> {

    private List<SubRelationNewsFragment> subRelationNewsFragments = new ArrayList<>();
    private VTVTabStyleAdapter vtvTabStyleAdapter;
    private DetailTravelNewsResponse.Data.PlaceNearBy relationNews;

    public RelationNewsFragment(DetailTravelNewsResponse.Data.PlaceNearBy relationNews) {
        this.relationNews = relationNews;
    }

    public RelationNewsFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_relation_news;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        vtvTabStyleAdapter = new VTVTabStyleAdapter(getChildFragmentManager());
        for (int i = 0; i < relationNews.getTabs().size(); i++) {
            SubRelationNewsFragment subRelationNewsFragment = new SubRelationNewsFragment();
            subRelationNewsFragment.setContentLink(relationNews.getTabs().get(i).getContent_link());
            vtvTabStyleAdapter.addFragment(subRelationNewsFragment, "");
            subRelationNewsFragments.add(subRelationNewsFragment);
        }

        getBinding().vpContent.setAdapter(vtvTabStyleAdapter);
        getBinding().tabLayout.setupWithViewPager(getBinding().vpContent);
        for (int i = 0; i < relationNews.getTabs().size(); i++) {

            View tabHome = LayoutInflater.from(mActivity).inflate(R.layout.f2_custom_tab_vtv_style, null);
            TextView tvHome = tabHome.findViewById(R.id.tvTitle);
            tvHome.setText((relationNews.getTabs().get(i).getName()));
            if (i == 0) {
                tvHome.setTextColor(Color.parseColor("#00918D"));
            } else {
                tvHome.setTextColor(Color.parseColor("#101010"));
            }
            View view = tabHome.findViewById(R.id.indicator);
            if (i == 0) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.INVISIBLE);
            }
            getBinding().tabLayout.getTabAt(i).setCustomView(tabHome);
        }

        getBinding().tabLayout.addOnTabSelectedListener(OnTabSelectedListener);
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().btnScrollToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    subRelationNewsFragments.get(getBinding().vpContent.getCurrentItem()).scrollToTop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            vtvTabStyleAdapter.SetOnSelectView(getBinding().tabLayout, c);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            int c = tab.getPosition();
            vtvTabStyleAdapter.SetUnSelectView(getBinding().tabLayout, c);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };
}
