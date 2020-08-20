package com.namviet.vtvtravel.view.fragment.f2smalllocation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter;
import com.namviet.vtvtravel.adapter.filter.MainFilterTabAdapter;
import com.namviet.vtvtravel.adapter.smalllocation.SmallLocationAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentSmallLocationBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.f2event.OnDoneFilterOption;
import com.namviet.vtvtravel.model.filter.ItemTab;
import com.namviet.vtvtravel.model.travelnews.Location;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2comment.CommentResponse;
import com.namviet.vtvtravel.response.f2filter.DistanceClass;
import com.namviet.vtvtravel.response.f2filter.FilterByCodeResponse;
import com.namviet.vtvtravel.response.f2filter.FilterByPageResponse;
import com.namviet.vtvtravel.response.f2smalllocation.SmallLocationResponse;
import com.namviet.vtvtravel.response.f2smalllocation.SortSmallLocationResponse;
import com.namviet.vtvtravel.response.travelnews.NewsCategoryResponse;
import com.namviet.vtvtravel.response.travelnews.NotebookResponse;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.f2.FilterActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.fragment.f2filter.SortDialog;
import com.namviet.vtvtravel.view.fragment.f2travelnote.SubTravelNewsFragment;
import com.namviet.vtvtravel.view.fragment.nearbyexperience.SearchLocationFragment;
import com.namviet.vtvtravel.viewmodel.f2smalllocation.SmallLocationViewModel;
import com.namviet.vtvtravel.viewmodel.f2travelnews.TravelNewsViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class SmallLocationFragment extends BaseFragment<F2FragmentSmallLocationBinding> implements Observer {
    private SmallLocationAdapter smallLocationAdapter;
    private SmallLocationViewModel viewModel;
    private FilterByCodeResponse filterByCodeResponse;
    private SortSmallLocationResponse sortSmallLocationResponse;
    private int positionTabSelected = 0;
    private String typeDestination  = Constants.TypeDestination.PLACES;
    private String link;
    private String code;
    private List<Travel> travelList = new ArrayList<>();
    private String loadMoreLink;
    private String regionId;

    @SuppressLint("ValidFragment")
    public SmallLocationFragment(String link, String code) {
        this.link = link;
        this.code = code;
    }

    public SmallLocationFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_small_location;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initView() {
        viewModel = new SmallLocationViewModel();
        getBinding().setSmallLocationViewModel(viewModel);
        viewModel.addObserver(this);

    }

    @Override
    public void initData() {
        smallLocationAdapter = new SmallLocationAdapter(travelList, mActivity, new SmallLocationAdapter.ClickItem() {
            @Override
            public void onClickItem(Travel travel) {
                addFragment(new DetailSmallLocationFragment(travel.getDetail_link()));
            }
        });
        getBinding().rclContent.setAdapter(smallLocationAdapter);


        switch (code){
            case "APP_WHERE_GO":
                typeDestination = Constants.TypeDestination.PLACES;
                getBinding().edtSearch.setHint("Bạn muốn đi đâu");
                break;
            case "APP_WHAT_EAT":
                typeDestination = Constants.TypeDestination.RESTAURANTS;
                getBinding().edtSearch.setHint("Bạn muốn ăn gì");
                break;
            case "APP_WHAT_PLAY":
                typeDestination = Constants.TypeDestination.CENTERS;
                getBinding().edtSearch.setHint("Bạn muốn chơi gì");
                break;
            case "APP_WHERE_STAY":
                typeDestination = Constants.TypeDestination.HOTELS;
                getBinding().edtSearch.setHint("Bạn muốn ở đâu");
                break;
        }
        viewModel.getSmallLocation(link + typeDestination, false);
        viewModel.getFilterByCode();

        viewModel.sortSmallLocation();


        handleSearch();
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

        getBinding().btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterActivity.startScreen(mActivity, filterByCodeResponse);
            }
        });

        getBinding().btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortDialog sortDialog = new SortDialog(sortSmallLocationResponse, new SortDialog.DoneSort() {
                    @Override
                    public void onDoneSort() {
                        clearRclData();
                        viewModel.getSmallLocation(genLinkToFilter(), false);
                    }
                });
                sortDialog.show(mActivity.getSupportFragmentManager(), null);
            }
        });


        getBinding().rclContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.getSmallLocation(loadMoreLink, true);
                    loadMoreLink = "";
                }
            }
        });

        getBinding().btnChooseRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new SearchLocationFragment(new SearchLocationFragment.DoneSearch() {
                    @Override
                    public void onDoneSearch(Location location) {
                        getBinding().tvRegionName.setText(location.getName());
                        regionId = location.getId();
                        clearRclData();
                        viewModel.getSmallLocation(genLinkToFilter(), false);
                    }
                }));
            }
        });
    }

    private void clearRclData(){
        travelList.clear();
        smallLocationAdapter.notifyDataSetChanged();
    }
    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {
        hideLoading();
        if (observable instanceof SmallLocationViewModel && null != o) {
            if (o instanceof SmallLocationResponse) {
                SmallLocationResponse response = (SmallLocationResponse) o;
                loadMoreLink = response.getData().getMore_link();
                if(response.isLoadMore()){
                    travelList.addAll(response.getData().getItems());
                }else {
                    travelList.clear();
                    travelList.addAll(response.getData().getItems());
                }
                smallLocationAdapter.notifyDataSetChanged();
                getBinding().tvRegionName.setText(response.getData().getNameRegion());

            } else if (o instanceof FilterByCodeResponse) {
                filterByCodeResponse = (FilterByCodeResponse) o;
                getDefaultSelectedFilterTab();
                setDefaultSelectedFilterTab(positionTabSelected);
                setDistance();
            } else if (o instanceof SortSmallLocationResponse) {
                sortSmallLocationResponse = (SortSmallLocationResponse) o;

            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }



    @Subscribe
    public void onDoneOptionFilter(OnDoneFilterOption onDoneFilterOption) {
        this.filterByCodeResponse = onDoneFilterOption.getFilterByCodeResponse();
        clearRclData();
        getMainCategory();
        viewModel.getSmallLocation(genLinkToFilter(), false);
    }

    private void setDefaultSelectedFilterTab(int position) {
        filterByCodeResponse.getData().getItems().get(position).setSelected(true);
    }

    private void getDefaultSelectedFilterTab(){
        for (int i = 0; i < filterByCodeResponse.getData().getItems().size(); i++) {
            if(filterByCodeResponse.getData().getItems().get(i).getCode().equals(code)){
                positionTabSelected = i;
                return;
            }
        }

    }

    public void setDistance (){
        DistanceClass distanceClass = new Gson().fromJson(loadJSONFromAsset(), DistanceClass.class);
        filterByCodeResponse.setDistanceClass(distanceClass);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mActivity.getAssets().open("distance.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private void getMainCategory(){
        int size = filterByCodeResponse.getData().getItems().size();
        for (int i = 0; i < size; i++) {
            if(filterByCodeResponse.getData().getItems().get(i).isSelected()){
                switch (filterByCodeResponse.getData().getItems().get(i).getCode()){
                    case "APP_WHERE_GO":
                        typeDestination = Constants.TypeDestination.PLACES;
                        break;
                    case "APP_WHAT_EAT":
                        typeDestination = Constants.TypeDestination.RESTAURANTS;
                        break;
                    case "APP_WHAT_PLAY":
                        typeDestination = Constants.TypeDestination.CENTERS;
                        break;
                    case "APP_WHERE_STAY":
                        typeDestination = Constants.TypeDestination.HOTELS;
                        break;
                }
            }
        }

    }
    
    private String getParamForFilterService(){
        String baseFilter = "";
        String typeFilter = "";
        int size = filterByCodeResponse.getData().getItems().size();

        for (int i = 0; i < size; i++) {
            if(filterByCodeResponse.getData().getItems().get(i).isSelected()){
                FilterByPageResponse dataHasLoaded = filterByCodeResponse.getData().getItems().get(i).getDataHasLoaded();



                if(dataHasLoaded != null){
                    for (int j = 0; j < dataHasLoaded.getData().size(); j++) {
                        if(dataHasLoaded.getData().get(j).isSelected() && !dataHasLoaded.getData().get(j).getField().equals("standard_rate")){
                            baseFilter = baseFilter+dataHasLoaded.getData().get(j).getField();
                            List<FilterByPageResponse.Data.Input> inputs = dataHasLoaded.getData().get(j).getInputs();


                            if(inputs != null){
                                for (int k = 0; k < inputs.size(); k++) {
                                    if(inputs.get(k).isSelected()){
                                        typeFilter = typeFilter+inputs.get(k).getValue()+",";
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        Log.e("baseFilter", baseFilter);
        Log.e("typeFilter", typeFilter);

        String result = "";
        if(!baseFilter.isEmpty()){
            if(!typeFilter.isEmpty()){
                typeFilter = typeFilter.substring(0, typeFilter.length() - 1);
                result = "&"+baseFilter+"="+typeFilter;
            }
        }else {

        }


        String baseFilterRate = "standard_rate";
        String typeFilterRate = "";
        for (int i = 0; i < size; i++) {
            if(filterByCodeResponse.getData().getItems().get(i).getCode().equals("APP_WHERE_STAY")){
                if(filterByCodeResponse.getData().getItems().get(i).isSelected()) {
                    FilterByPageResponse dataHasLoaded = filterByCodeResponse.getData().getItems().get(i).getDataHasLoaded();

                    if (dataHasLoaded != null) {
                        for (int j = 0; j < dataHasLoaded.getData().size(); j++) {
                            if (dataHasLoaded.getData().get(j).getField().equals("standard_rate")) {
//                            baseFilter = baseFilter+dataHasLoaded.getData().get(j).getField();


                                List<FilterByPageResponse.Data.Input> inputs = dataHasLoaded.getData().get(j).getInputs();


                                if (inputs != null) {
                                    for (int k = 0; k < inputs.size(); k++) {
                                        if (inputs.get(k).isSelected()) {
                                            typeFilterRate = typeFilterRate + inputs.get(k).getValue() + ",";
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }

        if(!typeFilterRate.isEmpty()){
            typeFilterRate = typeFilterRate.substring(0, typeFilterRate.length() - 1);
            result = result+ "&"+baseFilterRate+"="+typeFilterRate;
        }

        return result;
    }



    private String getDistance(){
        String result = "";
        for (int i = 0; i < filterByCodeResponse.getDistanceClass().getDistances().size(); i++) {
            if(filterByCodeResponse.getDistanceClass().getDistances().get(i).isSelected()){
                result = result+"&"+"distance="+filterByCodeResponse.getDistanceClass().getDistances().get(i).getValue();
                break;
            }
        }
        return result;
    }

    private String getOpenType(){
        String result = "";
        result = result+"&"+"type_open="+filterByCodeResponse.isTypeOpen();
        return result;
    }

    private String genLinkToFilter(){
        String result = link + typeDestination + getParamForFilterService()+getDistance()+getOpenType()+genLinkSort()+genLinkRegionId()+genLinkSearch(getBinding().edtSearch.getText().toString());
        Log.e("resultttt", result);
        return result;
    }


    private String genLinkSort(){
        try {
            String result = "";
            for (int i = 0; i < sortSmallLocationResponse.getData().getItems().size(); i++) {
                if(sortSmallLocationResponse.getData().getItems().get(i).isChecked()){
                    result = result+"&"+"sort="+sortSmallLocationResponse.getData().getItems().get(i).getFields();
                    return result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    private String genLinkRegionId(){
        try {
            String result = "";
            if(!regionId.isEmpty()){
                result = result+"&"+"region_id="+regionId;
                return result;
            }else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    private String genLinkSearch(String keyword){
        try {
            String result = "";
            if(!keyword.isEmpty()){
                result = result+"&"+"name="+keyword;
                return result;
            }else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    @SuppressLint("CheckResult")
    private void handleSearch(){
        RxTextView.afterTextChangeEvents(getBinding().edtSearch)
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textViewAfterTextChangeEvent -> {
                    clearRclData();
                    viewModel.getSmallLocation(genLinkToFilter(), false);
                });
    }

    private void resetFilter(){

    }

}
