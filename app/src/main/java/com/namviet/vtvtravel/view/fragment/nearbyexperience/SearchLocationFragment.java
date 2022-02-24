package com.namviet.vtvtravel.view.fragment.nearbyexperience;

import android.annotation.SuppressLint;
import android.view.View;

import com.baseapp.utils.KeyboardUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2biglocation.SearchAllLocationAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentSearchLocationBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.travelnews.Location;
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse;
import com.namviet.vtvtravel.response.f2biglocation.LocationResponse;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.fragment.f2biglocation.BigLocationFragment;
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class SearchLocationFragment extends BaseFragment<F2FragmentSearchLocationBinding> implements Observer {
    private SearchBigLocationViewModel viewModel;
    private SearchAllLocationAdapter searchAllLocationAdapter;
    private List<Location> locationsMain = new ArrayList<>();
    private List<Location> locations = new ArrayList<>();
    private DoneSearch doneSearch;
    private Location mLocation;

    @SuppressLint("ValidFragment")
    public SearchLocationFragment(DoneSearch doneSearch) {
        this.doneSearch = doneSearch;
    }

    public SearchLocationFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_search_location;
    }

    @Override
    public void initView() {
        viewModel = new SearchBigLocationViewModel();
        getBinding().setSearchBigLocationViewModel(viewModel);
        viewModel.addObserver(this);

        viewModel.getAllLocation();
    }

    @Override
    public void initData() {
        searchAllLocationAdapter = new SearchAllLocationAdapter(mActivity, locations, new SearchAllLocationAdapter.ClickItem() {
            @Override
            public void onClick(Location location) {
                mLocation = location;
                getBinding().btnApply.setText("Chọn");
              //  getBinding().edtLocation.setText(location.getName());
            }
        });
        getBinding().rclLocation.setAdapter(searchAllLocationAdapter);
        getBinding().btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLocation != null) {
                    if (mLocation.getId() != null && !mLocation.getId().isEmpty()) {
                        KeyboardUtils.hideKeyboard(mActivity, getBinding().edtLocation);
                        doneSearch.onDoneSearch(mLocation);
                        mActivity.onBackPressed();

                    }
                } else {
                    KeyboardUtils.hideKeyboard(mActivity, getBinding().edtLocation);
                    //doneSearch.onDoneSearch(mLocation);
                    mActivity.onBackPressed();
                }
            }
        });
    }


    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        handleSearch();
        getBinding().imgCloseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBinding().edtLocation.setText("");
            }
        });

        getBinding().btnCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getLocation();

            }
        });
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardUtils.hideKeyboard(mActivity, getBinding().edtLocation);
                mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof SearchBigLocationViewModel && null != o) {
            if (o instanceof AllLocationResponse) {
                AllLocationResponse response = (AllLocationResponse) o;
                locationsMain = response.getData();
                locations.addAll(locationsMain);
                searchAllLocationAdapter.notifyDataSetChanged();
            } else if (o instanceof LocationResponse) {
                LocationResponse response = (LocationResponse) o;
                getBinding().edtLocation.setText(response.getData().getName());
            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {

//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }

    @SuppressLint("CheckResult")
    private void handleSearch() {
        RxTextView.afterTextChangeEvents(getBinding().edtLocation)
                .skipInitialValue()
                .debounce(450, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textViewAfterTextChangeEvent -> {
//                    if(getBinding().edtLocation.getText().toString().isEmpty()){
//                        getBinding().rclLocation.setVisibility(View.GONE);
//                    }else {
                    getBinding().rclLocation.setVisibility(View.VISIBLE);
                    locations.clear();
                    for (int i = 0; i < locationsMain.size(); i++) {
                        if (F2Util.removeAccent(locationsMain.get(i).getName().toLowerCase()).contains(F2Util.removeAccent(getBinding().edtLocation.getText().toString().toLowerCase()))) {
                            locations.add(locationsMain.get(i));
                        }
                    }
                    searchAllLocationAdapter.notifyDataSetChanged();
//                    }

                });
    }

    public interface DoneSearch {
        void onDoneSearch(Location location);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen("SearchLocation", "Tìm kiếm địa điểm");
    }
}
