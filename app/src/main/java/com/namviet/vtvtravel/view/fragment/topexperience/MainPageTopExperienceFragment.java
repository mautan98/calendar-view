package com.namviet.vtvtravel.view.fragment.topexperience;

import android.annotation.SuppressLint;
import androidx.core.content.ContextCompat;
import android.view.View;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentTopExperienceBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.model.newhome.ItemHomeService;
import com.namviet.vtvtravel.response.travelnews.DetailTravelNewsResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.PropertyReference0Impl;

public class MainPageTopExperienceFragment extends BaseFragment<F2FragmentTopExperienceBinding> {
    private List<SubTopExperienceFragment> subTopExperienceFragments;
    private ItemHomeService itemHomeService;
    private DetailTravelNewsResponse.Data.PlaceNearBy placeNearBy;
    private MainAdapter mainAdapter;
    private int type = 0;

    public class Type{
        public static final int FROM_HOME = 0;
        public static final int FROM_TRAVEL_NEWS = 1;
    }

    @SuppressLint("ValidFragment")
    public MainPageTopExperienceFragment(ItemHomeService itemHomeService, int type) {
        this.itemHomeService = itemHomeService;
        this.type = type;
    }


    @SuppressLint("ValidFragment")
    public MainPageTopExperienceFragment(DetailTravelNewsResponse.Data.PlaceNearBy placeNearBy, int type) {
        this.placeNearBy = placeNearBy;
        this.type = type;
    }

    public MainPageTopExperienceFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_top_experience;
    }

    @Override
    public void initView() {
        subTopExperienceFragments = new ArrayList<>();
    }

    @Override
    public void initData() {
        mainAdapter = new MainAdapter(getChildFragmentManager());
        getBinding().vpContent.setOffscreenPageLimit(10);
        if(type == Type.FROM_HOME) {
            for (int i = 0; i < itemHomeService.getItems().size(); i++) {
                List<ItemHomeService.Item> itemHomeServices = itemHomeService.getItems();
                SubTopExperienceFragment subTopExperienceFragment = new SubTopExperienceFragment(itemHomeServices.get(i).getContent_link());
                subTopExperienceFragments.add(subTopExperienceFragment);
                mainAdapter.addFragment(subTopExperienceFragment, "");
            }
        }else {
            for (int i = 0; i < placeNearBy.getTabs().size(); i++) {
                SubTopExperienceFragment subTopExperienceFragment = new SubTopExperienceFragment(placeNearBy.getTabs().get(i).getContent_link());
                subTopExperienceFragments.add(subTopExperienceFragment);
                mainAdapter.addFragment(subTopExperienceFragment, "");
            }
        }
        getBinding().vpContent.setAdapter(mainAdapter);
        getBinding().tabLayout.setTabTextColors(ContextCompat.getColor(mActivity, R.color.md_black_1000), ContextCompat.getColor(mActivity, R.color.f2_color_package));
        getBinding().tabLayout.setupWithViewPager(getBinding().vpContent);
        if(type == Type.FROM_HOME) {
            for (int i = 0; i < itemHomeService.getItems().size(); i++) {
                List<ItemHomeService.Item> itemHomeServices = itemHomeService.getItems();
                getBinding().tabLayout.getTabAt(i).setText(itemHomeServices.get(i).getName());
            }
        }else {
            for (int i = 0; i < placeNearBy.getTabs().size(); i++) {
                getBinding().tabLayout.getTabAt(i).setText(placeNearBy.getTabs().get(i).getName());
            }
        }
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

        getBinding().btnScrollToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    subTopExperienceFragments.get(getBinding().vpContent.getCurrentItem()).scrollToTop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setObserver() {
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.TOP_EXPERIENCE, TrackingAnalytic.ScreenTitle.TOP_EXPERICENCE);
    }
}
