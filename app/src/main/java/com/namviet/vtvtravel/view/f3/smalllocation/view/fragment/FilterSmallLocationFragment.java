package com.namviet.vtvtravel.view.f3.smalllocation.view.fragment;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F3FragmentFilterHomeBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.f2event.OnDoneFilterOption;
import com.namviet.vtvtravel.model.travelnews.Location;
import com.namviet.vtvtravel.response.f2filter.FilterByCodeResponse;
import com.namviet.vtvtravel.response.f2filter.FilterByPageResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f3.smalllocation.adapter.FilterAdapter;
import com.namviet.vtvtravel.view.f3.smalllocation.adapter.FilterTest;
import com.namviet.vtvtravel.viewmodel.f2filter.FilterHomeViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class FilterSmallLocationFragment extends BaseFragment<F3FragmentFilterHomeBinding> implements Observer {
    private FilterHomeViewModel filterHomeViewModel;
    private FilterAdapter mFilterAdapter;
    private List<List<FilterTest>> data;
    private int i;

    @SuppressLint("ValidFragment")
    public FilterSmallLocationFragment(FilterByCodeResponse filterByCodeResponse, int i) {
        this.filterByCodeResponse = filterByCodeResponse;
        this.i = i;
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
        getDataForDefaultTab();
    }

    private void getDataForDefaultTab() {
        if (filterByCodeResponse.getData().getItems().get(getTabSelectedAndCodeSelected()).getDataHasLoaded() == null) {
            filterHomeViewModel.getFilterByPage(filterByCodeResponse.getData().getItems().get(getTabSelectedAndCodeSelected()).getLink(), filterByCodeResponse.getData().getItems().get(getTabSelectedAndCodeSelected()).getCode());
        } else {
            setUpFilterRcv(filterByCodeResponse.getData().getItems().get(getTabSelectedAndCodeSelected()).getDataHasLoaded());
        }
    }

    private int getTabSelectedAndCodeSelected() {
        for (int i = 0; i < filterByCodeResponse.getData().getItems().size(); i++) {
            if (filterByCodeResponse.getData().getItems().get(i).isSelected()) {
                return i;
            }
        }
        return 0;
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
                    FilterByPageResponse dataHasLoaded = filterByCodeResponse.getData().getItems().get(i).getDataHasLoaded();
                    if (dataHasLoaded != null) {
                        for (int j = 0; j < dataHasLoaded.getData().size(); j++) {
                            List<FilterByPageResponse.Data.Input> inputs = dataHasLoaded.getData().get(j).getInputs();
                            if (inputs != null) {
                                for (int k = 0; k < inputs.size(); k++) {
                                    inputs.get(k).setSelected(false);
                                }

                            }
                        }
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
    }

    public void setUpFilterRcv(FilterByPageResponse filterByPageResponse) {
        mFilterAdapter = new FilterAdapter(filterByPageResponse, mActivity, filterByCodeResponse);
        getBinding().rcvFilter.setLayoutManager(new LinearLayoutManager(mActivity));
        getBinding().rcvFilter.setAdapter(mFilterAdapter);
        mFilterAdapter.notifyDataSetChanged();
    }

//    private void setUpFilterRcvTest() {
//        data = new ArrayList<>();
//        for (int i = 0; i < 7; i++) {
//            List<FilterTest> items = new ArrayList<>();
//            if (i == 0) {
//
//                items.add(new FilterTest("Nhà Hàng",0,false));
//                items.add(new FilterTest("Quán ăn bình dân",0,false));
//                items.add(new FilterTest("Đồ uống",0,false));
//                data.add(items);
//            } else if (i == 3) {
//                items.add(new FilterTest("Nhà Hàng",0,false));
//                items.add(new FilterTest("Nhà hàng ngon",0,false));
//                items.add(new FilterTest("Cộng Cafe",0,false));
//                items.add(new FilterTest("Bar 1900",0,false));
//                data.add(items);
//            } else {
//                items.add(new FilterTest("Hỗ trợ giao hàng",0,false));
//                items.add(new FilterTest("Đồ ăn ngoài trời",0,false));
//                items.add(new FilterTest("Nhà hàng",0,false));
//                for (int j = 0; j < 8; j++) {
//                    items.add(new FilterTest("Khách sạn",0,false));
//                }
//                data.add(items);
//            }
//        }
//        mFilterAdapter = new FilterAdapter(data, mActivity);
//        getBinding().rcvFilter.setLayoutManager(new LinearLayoutManager(mActivity));
//        getBinding().rcvFilter.setAdapter(mFilterAdapter);
//        mFilterAdapter.notifyDataSetChanged();
//    }

    @Override
    public void setObserver() {

    }

    private FilterByCodeResponse filterByCodeResponse;

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof FilterHomeViewModel && null != o) {
            if (o instanceof FilterByCodeResponse) {
                filterByCodeResponse = (FilterByCodeResponse) o;
//                mainFilterTabAdapter = new MainFilterTabAdapter(filterByCodeResponse.getData().getItems(), mActivity, this);
//                getBinding().rclTabFilter.setAdapter(mainFilterTabAdapter);
//                getDataForDefaultTab();
            } else if (o instanceof FilterByPageResponse) {
                FilterByPageResponse response = (FilterByPageResponse) o;
                for (int i = 0; i < filterByCodeResponse.getData().getItems().size(); i++) {
                    if (filterByCodeResponse.getData().getItems().get(i).getCode().equals(response.getCodeSet())) {
                        response.getData().add(new FilterByPageResponse.Data(FilterByPageResponse.TYPE_OPEN_STATE));
                        response.getData().add(new FilterByPageResponse.Data(FilterByPageResponse.TYPE_DISTANCE));
                        filterByCodeResponse.getData().getItems().get(i).setDataHasLoaded(response);
                    }
                }
                setUpFilterRcv(response);
            }
            //     setDataForTextViewsAndShowHideTypeFilter();

        } else if (o instanceof ErrorResponse) {
            ErrorResponse responseError = (ErrorResponse) o;
            try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
            } catch (Exception e) {

            }
        }
    }

    public void postEventDoneFilterOption() {
//        if(filterByCodeResponse.getData() != null){
//            FilterByPageResponse filterByPageResponse = filterByCodeResponse.getData().getItems().get(getTabSelectedAndCodeSelected()).getDataHasLoaded();
//            if(filterByPageResponse != null){
//                for (int i = 0; i< filterByPageResponse.getData().size(); i++){
//                    if(filterByPageResponse.getData().get(i) == null){
//                        filterByPageResponse.getData().remove(i);
//                    }
//                }
//            }
//
//        }
        EventBus.getDefault().post(new OnDoneFilterOption(filterByCodeResponse, i));
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.BASE_FILTER, TrackingAnalytic.ScreenTitle.BASE_FILTER);
    }
}
