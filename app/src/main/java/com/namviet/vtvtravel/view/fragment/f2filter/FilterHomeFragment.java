package com.namviet.vtvtravel.view.fragment.f2filter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.filter.DistanceAdapter;
import com.namviet.vtvtravel.adapter.filter.MainFilterTabAdapter;
import com.namviet.vtvtravel.adapter.filter.StarAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentFilterHomeBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.f2event.OnDoneFilterOption;
import com.namviet.vtvtravel.model.filter.ItemTab;
import com.namviet.vtvtravel.response.f2filter.DistanceClass;
import com.namviet.vtvtravel.response.f2filter.FilterByCodeResponse;
import com.namviet.vtvtravel.response.f2filter.FilterByPageResponse;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.viewmodel.f2filter.FilterHomeViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class FilterHomeFragment extends BaseFragment<F2FragmentFilterHomeBinding> implements Observer, MainFilterTabAdapter.ClickItem, DistanceAdapter.ClickItem, StarAdapter.ClickItem {
    private MainFilterTabAdapter mainFilterTabAdapter;
    private FilterHomeViewModel filterHomeViewModel;
    private DistanceAdapter distanceAdapter;
    private StarAdapter starAdapter;
    private int positionSelected = 0;
    private String codeSelected;


    @SuppressLint("ValidFragment")
    public FilterHomeFragment(FilterByCodeResponse filterByCodeResponse) {
        this.filterByCodeResponse = filterByCodeResponse;
    }

    public FilterHomeFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_filter_home;
    }

    @Override
    public void initView() {
        filterHomeViewModel = new FilterHomeViewModel();
        getBinding().setFilterHomeViewModel(filterHomeViewModel);
        filterHomeViewModel.addObserver(this);

        if (filterByCodeResponse == null) {
            filterHomeViewModel.getFilterByCode();
        } else {
            setDataForTextViewsAndShowHideTypeFilter();
            getDataForDefaultTab();
            setDataForListStarFilter(getPositionForGetListStarFilter());
        }
    }

    @Override
    public void initData() {
        mainFilterTabAdapter = new MainFilterTabAdapter(filterByCodeResponse.getData().getItems(), mActivity, this);
        getBinding().rclTabFilter.setAdapter(mainFilterTabAdapter);

        distanceAdapter = new DistanceAdapter(filterByCodeResponse.getDistanceClass().getDistances(), mActivity, this);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(mActivity);
        getBinding().rclDistance.setLayoutManager(flexboxLayoutManager);
        getBinding().rclDistance.setAdapter(distanceAdapter);

//        starAdapter = new StarAdapter(getListStar(), mActivity, this);
        FlexboxLayoutManager flexboxLayoutManager2 = new FlexboxLayoutManager(mActivity);
        getBinding().rclStar.setLayoutManager(flexboxLayoutManager2);

        getBinding().switchView.setChecked(filterByCodeResponse.isTypeOpen());


        getBinding().rclDistance.setNestedScrollingEnabled(false);
        getBinding().rclTabFilter.setNestedScrollingEnabled(false);
        getBinding().rclStar.setNestedScrollingEnabled(false);
    }


    @Override
    public void onClickItemStar(Star distance) {

    }

    public class Star {
        private String name;
        private boolean isSelected;

        public Star(String name, boolean isSelected) {
            this.name = name;
            this.isSelected = isSelected;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
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

        getBinding().btnBaseFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseFilterFragment baseFilterFragment = new BaseFilterFragment();
                baseFilterFragment.setData(filterByCodeResponse, codeSelected, new BaseFilterFragment.FinishBaseFilter() {
                    @Override
                    public void onFinishBaseFilter(FilterByCodeResponse filterByCodeResponse) {
                        FilterHomeFragment.this.filterByCodeResponse = filterByCodeResponse;
                        setDataForTextViewsAndShowHideTypeFilter();
                    }
                });
                addFragment(baseFilterFragment);
            }
        });


        getBinding().btnTypeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new TypeFilterFragment(filterByCodeResponse, codeSelected, new TypeFilterFragment.DoneOptionFilterType() {
                    @Override
                    public void onDoneOptionFilterType() {
                        setDataForTextViewsAndShowHideTypeFilter();
                    }
                }));
            }
        });

        getBinding().btnDoneOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postEventDoneFilterOption();
                mActivity.onBackPressed();
            }
        });

        getBinding().switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                filterByCodeResponse.setTypeOpen(b);
            }
        });
        getBinding().btnSetToDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < filterByCodeResponse.getData().getItems().size(); i++) {

                    try {
                        if(filterByCodeResponse.getData().getItems().get(i).getDataHasLoaded() != null) {
                            int size = filterByCodeResponse.getData().getItems().get(i).getDataHasLoaded().getData().size();
                            FilterByPageResponse filterByPageResponse = filterByCodeResponse.getData().getItems().get(i).getDataHasLoaded();
                            for (int j = 0; j < size; j++) {
                                if(j == 0){
                                    filterByPageResponse.getData().get(j).setSelected(true);
                                }else {
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

    }


    private void resetFilter(){

    }

    @Override
    public void setObserver() {

    }

    private FilterByCodeResponse filterByCodeResponse;

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof FilterHomeViewModel && null != o) {
            if (o instanceof FilterByCodeResponse) {
                filterByCodeResponse = (FilterByCodeResponse) o;
                mainFilterTabAdapter = new MainFilterTabAdapter(filterByCodeResponse.getData().getItems(), mActivity, this);
                getBinding().rclTabFilter.setAdapter(mainFilterTabAdapter);
                getDataForDefaultTab();
            } else if (o instanceof FilterByPageResponse) {
                FilterByPageResponse response = (FilterByPageResponse) o;

                for (int i = 0; i < filterByCodeResponse.getData().getItems().size(); i++) {
                    if (filterByCodeResponse.getData().getItems().get(i).getCode().equals(response.getCodeSet())) {
                        filterByCodeResponse.getData().getItems().get(i).setDataHasLoaded(response);
                        codeSelected = response.getCodeSet();
                        setAllTypeForBaseFilter(i);
                        if (codeSelected.equals("APP_WHERE_STAY")) {
                            setDataForListStarFilter(i);
                        }
                        break;
                    }
                }


                setDataForTextViewsAndShowHideTypeFilter();

            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }

    public void postEventDoneFilterOption() {
        EventBus.getDefault().post(new OnDoneFilterOption(filterByCodeResponse));
    }

    @Override
    public void onClickItem(ItemTab itemTab) {
        if (itemTab.getDataHasLoaded() == null) {
            String link = itemTab.getLink();
            filterHomeViewModel.getFilterByPage(link, itemTab.getCode());
        } else {
            codeSelected = itemTab.getCode();
        }

        setDataForTextViewsAndShowHideTypeFilter();

        showOrHideStarFilter(itemTab);

    }

    private void showOrHideStarFilter(ItemTab itemTab) {
        try {
            if (itemTab.getCode().equals("APP_WHERE_STAY")) {
                getBinding().layoutStarFilter.setVisibility(View.VISIBLE);
            } else {
                getBinding().layoutStarFilter.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }
    }


    private void setDataForTextViewsAndShowHideTypeFilter() {
        List<FilterByPageResponse.Data> dataList = new ArrayList<>();
        if (filterByCodeResponse != null
                && filterByCodeResponse.getData() != null
                && filterByCodeResponse.getData().getItems() != null
                && filterByCodeResponse.getData().getItems().size() > 0)
            for (int i = 0; i < filterByCodeResponse.getData().getItems().size(); i++) {
                if (filterByCodeResponse.getData().getItems().get(i).isSelected()) {
                    if (filterByCodeResponse.getData().getItems().get(i).getDataHasLoaded() != null) {
                        dataList = filterByCodeResponse.getData().getItems().get(i).getDataHasLoaded().getData();
                        break;
                    }
                }
            }

        String labelBaseFilter = notYetBaseFilter;
        List<FilterByPageResponse.Data.Input> inputList = new ArrayList<>();
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                if (dataList.get(i).isSelected() && !dataList.get(i).getField().equals("standard_rate")) {
                    labelBaseFilter = dataList.get(i).getLabel();
                    inputList = dataList.get(i).getInputs();
                    break;
                }
            }
        }

        String labelTypeFilter = notYetTypeFilter;
        String typeFilter = "";
        if (inputList != null && inputList.size() > 0) {
            for (int i = 0; i < inputList.size(); i++) {
                if (inputList.get(i).isSelected()) {
                    typeFilter = typeFilter + inputList.get(i).getLabel() + ",";
                }
            }
        }

        if (!typeFilter.isEmpty()) {
            labelTypeFilter = typeFilter;
        }

        getBinding().tvBaseFilterLabel.setText(labelBaseFilter);

        if (labelTypeFilter.equals(notYetTypeFilter)) {
            if (labelBaseFilter.equals(notYetBaseFilter)) {
                getBinding().layoutFilterType.setVisibility(View.GONE);
            } else {
                getBinding().layoutFilterType.setVisibility(View.VISIBLE);
                getBinding().tvFilterTypeLabel.setText(labelTypeFilter);
            }
        } else {
            getBinding().layoutFilterType.setVisibility(View.VISIBLE);
            getBinding().tvFilterTypeLabel.setText(labelTypeFilter);
        }

    }

    private String notYetTypeFilter = "Chưa lọc";
    private String notYetBaseFilter = "Tất cả";

    private int getTabSelectedAndCodeSelected() {
        for (int i = 0; i < filterByCodeResponse.getData().getItems().size(); i++) {
            if (filterByCodeResponse.getData().getItems().get(i).isSelected()) {
                codeSelected = filterByCodeResponse.getData().getItems().get(i).getCode();
                showOrHideStarFilter(filterByCodeResponse.getData().getItems().get(i));
                return i;
            }
        }
        return 0;
    }

    private void getDataForDefaultTab() {
        if (filterByCodeResponse.getData().getItems().get(getTabSelectedAndCodeSelected()).getDataHasLoaded() == null) {
            filterHomeViewModel.getFilterByPage(filterByCodeResponse.getData().getItems().get(getTabSelectedAndCodeSelected()).getLink(), filterByCodeResponse.getData().getItems().get(getTabSelectedAndCodeSelected()).getCode());
        } else {

        }
    }

    @Override
    public void onClickItemDistance(DistanceClass.Distance distance) {

    }

    private int getPositionForGetListStarFilter() {
        for (int i = 0; i < filterByCodeResponse.getData().getItems().size(); i++) {
            if (filterByCodeResponse.getData().getItems().get(i).getCode().equals("APP_WHERE_STAY")) {
                return i;
            }
        }
        return 0;
    }

    private void setDataForListStarFilter(int position) {
        try {
            starAdapter = new StarAdapter(filterByCodeResponse.getData().getItems().get(position).getDataHasLoaded().getData().get(1).getInputs(), mActivity, this);
            getBinding().rclStar.setAdapter(starAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setAllTypeForBaseFilter(int position) {
            FilterByPageResponse.Data data = new FilterByPageResponse().new Data();
            data.setLabel("Tất cả");
            data.setSelected(true);
            data.setField("all");
            filterByCodeResponse.getData().getItems().get(position).getDataHasLoaded().getData().add(0, data);

    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.BASE_FILTER, TrackingAnalytic.ScreenTitle.BASE_FILTER);
    }
}
