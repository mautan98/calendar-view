package com.namviet.vtvtravel.view.f3.smalllocation.view.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.filter.DistanceAdapter;
import com.namviet.vtvtravel.adapter.filter.MainFilterTabAdapter;
import com.namviet.vtvtravel.adapter.filter.StarAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentFilterHomeBinding;
import com.namviet.vtvtravel.databinding.F3FragmentFilterHomeBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.f2event.OnDoneFilterOption;
import com.namviet.vtvtravel.model.filter.ItemTab;
import com.namviet.vtvtravel.model.travelnews.Location;
import com.namviet.vtvtravel.response.f2filter.DistanceClass;
import com.namviet.vtvtravel.response.f2filter.FilterByCodeResponse;
import com.namviet.vtvtravel.response.f2filter.FilterByPageResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f3.smalllocation.adapter.FilterAdapter;
import com.namviet.vtvtravel.view.f3.smalllocation.adapter.FilterTest;
import com.namviet.vtvtravel.view.fragment.f2filter.BaseFilterFragment;
import com.namviet.vtvtravel.view.fragment.f2filter.FilterHomeFragment;
import com.namviet.vtvtravel.view.fragment.f2filter.TypeFilterFragment;
import com.namviet.vtvtravel.view.fragment.nearbyexperience.SearchLocationFragment;
import com.namviet.vtvtravel.viewmodel.f2filter.FilterHomeViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class FilterSmallLocationFragment extends BaseFragment<F3FragmentFilterHomeBinding> implements Observer {
    private FilterHomeViewModel filterHomeViewModel;
    private FilterAdapter mFilterAdapter;
    private List<List<FilterTest>> data;

    @SuppressLint("ValidFragment")
    public FilterSmallLocationFragment(FilterByCodeResponse filterByCodeResponse) {
        this.filterByCodeResponse = filterByCodeResponse;
    }

    public FilterSmallLocationFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f3_fragment_filter_home;
    }

    @Override
    public void initView() {
        filterHomeViewModel = new FilterHomeViewModel();
        getBinding().setFilterHomeViewModel(filterHomeViewModel);
        filterHomeViewModel.addObserver(this);
        if (filterByCodeResponse == null) {
            filterHomeViewModel.getFilterByCode();
        }


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
        getBinding().btnDoneOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postEventDoneFilterOption();
                mActivity.onBackPressed();
            }
        });
        getBinding().btnSetToDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < filterByCodeResponse.getData().getItems().size(); i++) {

                    try {
                        if (filterByCodeResponse.getData().getItems().get(i).getDataHasLoaded() != null) {
                            int size = filterByCodeResponse.getData().getItems().get(i).getDataHasLoaded().getData().size();
                            FilterByPageResponse filterByPageResponse = filterByCodeResponse.getData().getItems().get(i).getDataHasLoaded();
                            for (int j = 0; j < size; j++) {
                                if (j == 0) {
                                    filterByPageResponse.getData().get(j).setSelected(true);
                                } else {
                                    filterByPageResponse.getData().get(j).setSelected(false);
                                }


                                try {
                                    int size2 = filterByPageResponse.getData().size();
                                    for (int k = 0; k < size2; k++) {
                                        filterByPageResponse.getData().get(j).getInputs().get(k).setSelected(false);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                for (int i = 0; i < filterByCodeResponse.getDistanceClass().getDistances().size(); i++) {
                    filterByCodeResponse.getDistanceClass().getDistances().get(i).setSelected(false);
                }

                filterByCodeResponse.setTypeOpen(false);
                postEventDoneFilterOption();
                mActivity.onBackPressed();
            }
        });
        getBinding().lnlSelectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new SelectLocationFragment(new SelectLocationFragment.DoneSearch() {
                    @Override
                    public void onDoneSearch(Location location) {
                        if (location != null) {
                            if (!location.getName().equals("")) {
                                getBinding().lnlLocation.setVisibility(View.VISIBLE);
                                getBinding().tvLocation.setText(location.getName());
                            }
                        }
                    }
                }));
            }
        });
        setUpFilterRcv();

    }

    private void setUpFilterRcv() {
        data = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            List<FilterTest> items = new ArrayList<>();
            if (i == 0) {

                items.add(new FilterTest("Nhà Hàng",0,false));
                items.add(new FilterTest("Quán ăn bình dân",0,false));
                items.add(new FilterTest("Đồ uống",0,false));
                data.add(items);
            } else if (i == 3) {
                items.add(new FilterTest("Nhà Hàng",0,false));
                items.add(new FilterTest("Nhà hàng ngon",0,false));
                items.add(new FilterTest("Cộng Cafe",0,false));
                items.add(new FilterTest("Bar 1900",0,false));
                data.add(items);
            } else {
                items.add(new FilterTest("Hỗ trợ giao hàng",0,false));
                items.add(new FilterTest("Đồ ăn ngoài trời",0,false));
                items.add(new FilterTest("Nhà hàng",0,false));
                for (int j = 0; j < 8; j++) {
                    items.add(new FilterTest("Khách sạn",0,false));
                }
                data.add(items);
            }
        }
        mFilterAdapter = new FilterAdapter(data, mActivity);
        getBinding().rcvFilter.setLayoutManager(new LinearLayoutManager(mActivity));
        getBinding().rcvFilter.setAdapter(mFilterAdapter);
        mFilterAdapter.notifyDataSetChanged();
    }

    @Override
    public void setObserver() {

    }

    private FilterByCodeResponse filterByCodeResponse;

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof FilterHomeViewModel && null != o) {

        }
    }

    public void postEventDoneFilterOption() {
        EventBus.getDefault().post(new OnDoneFilterOption(filterByCodeResponse));
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.BASE_FILTER, TrackingAnalytic.ScreenTitle.BASE_FILTER);
    }
}
