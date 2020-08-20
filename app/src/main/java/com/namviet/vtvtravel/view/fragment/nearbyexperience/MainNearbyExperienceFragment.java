package com.namviet.vtvtravel.view.fragment.nearbyexperience;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentNearbyExperienceBinding;
import com.namviet.vtvtravel.databinding.F2FragmentTopExperienceBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.newhome.ItemHomeService;
import com.namviet.vtvtravel.model.travelnews.Location;
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse;
import com.namviet.vtvtravel.response.f2biglocation.LocationResponse;
import com.namviet.vtvtravel.view.fragment.topexperience.SubTopExperienceFragment;
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainNearbyExperienceFragment extends BaseFragment<F2FragmentNearbyExperienceBinding> implements Observer {
    private List<SubTopExperienceFragment> subTopExperienceFragments;
    private ItemHomeService itemHomeService;
    private MainAdapter mainAdapter;
    private SearchBigLocationViewModel viewModel;

    @SuppressLint("ValidFragment")
    public MainNearbyExperienceFragment(ItemHomeService itemHomeService) {
        this.itemHomeService = itemHomeService;
    }

    public MainNearbyExperienceFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_nearby_experience;
    }

    @Override
    public void initView() {
        subTopExperienceFragments = new ArrayList<>();
        viewModel = new SearchBigLocationViewModel();
        getBinding().setSearchBigLocationViewModel(viewModel);
        viewModel.getLocation();
        viewModel.addObserver(this);
    }

    @Override
    public void initData() {
        mainAdapter = new MainAdapter(getChildFragmentManager());
        getBinding().vpContent.setOffscreenPageLimit(10);
        for (int i = 0; i < itemHomeService.getItems().size(); i++) {
            List<ItemHomeService.Item> itemHomeServices = itemHomeService.getItems();
            SubTopExperienceFragment subTopExperienceFragment = new SubTopExperienceFragment(itemHomeServices.get(i).getContent_link());
            subTopExperienceFragments.add(subTopExperienceFragment);
            mainAdapter.addFragment(subTopExperienceFragment, "");
        }
        getBinding().vpContent.setAdapter(mainAdapter);
        getBinding().tabLayout.setTabTextColors(ContextCompat.getColor(mActivity, R.color.md_black_1000), ContextCompat.getColor(mActivity, R.color.f2_color_package));
        getBinding().tabLayout.setupWithViewPager(getBinding().vpContent);
        for (int i = 0; i < itemHomeService.getItems().size(); i++) {
            List<ItemHomeService.Item> itemHomeServices = itemHomeService.getItems();
            getBinding().tabLayout.getTabAt(i).setText(itemHomeServices.get(i).getName());
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

        getBinding().layoutChooseRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new SearchLocationFragment(new SearchLocationFragment.DoneSearch() {
                    @Override
                    public void onDoneSearch(Location location) {
                        getBinding().tvRegionNameTop.setText(location.getName());
                        pushDataForSubFragment(location.getId());
                    }
                }));
            }
        });
    }

    private void pushDataForSubFragment(String regionId){
//        try {
            for (int i = 0; i < subTopExperienceFragments.size(); i++) {
                subTopExperienceFragments.get(i).loadData(regionId);
            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void setObserver() {
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof SearchBigLocationViewModel && null != o) {
             if (o instanceof LocationResponse) {
                LocationResponse response = (LocationResponse) o;
                getBinding().tvRegionNameTop.setText(response.getData().getName());
            }else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }
}
