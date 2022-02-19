package com.namviet.vtvtravel.view.f3.smalllocation.view.fragment;

import android.annotation.SuppressLint;
import android.view.View;

import com.baseapp.utils.KeyboardUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2biglocation.SearchAllLocationAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentSearchLocationBinding;
import com.namviet.vtvtravel.databinding.F3FragmentSelectLocationBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.travelnews.Location;
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse;
import com.namviet.vtvtravel.response.f2biglocation.LocationResponse;
import com.namviet.vtvtravel.ultils.F2Util;
import com.namviet.vtvtravel.view.fragment.nearbyexperience.SearchLocationFragment;
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class SelectLocationFragment extends BaseFragment<F3FragmentSelectLocationBinding> implements Observer {

    private DoneSearch doneSearch;
    private Location mLocation;
    private String locationText = "";
    private boolean isSelected,isSelected2,isSelected3;

    @SuppressLint("ValidFragment")
    public SelectLocationFragment(DoneSearch doneSearch) {
        this.doneSearch = doneSearch;
    }

    public SelectLocationFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f3_fragment_select_location;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


    @Override
    public void inject() {

    }
    @Override
    public void setClickListener() {
        getBinding().imgCloseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new SearchLocationFragment(new SearchLocationFragment.DoneSearch() {
                    @Override
                    public void onDoneSearch(Location location) {
                        isSelected = true;
                        locationText = locationText + location.getName();
                        mLocation = location;
                        getBinding().edtLocation.setText(location.getName());
                        getBinding().imgCloseSearch2.setClickable(true);
                        getBinding().edtLocation2.setTextColor(mActivity.getResources().getColor(R.color.black));
                    }
                }));
            }
        });
        getBinding().imgCloseSearch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelected){
                    addFragment(new SearchLocationFragment(new SearchLocationFragment.DoneSearch() {
                        @Override
                        public void onDoneSearch(Location location) {
                            isSelected2 = true;
                            locationText = locationText + ", "+ location.getName();
                            getBinding().edtLocation2.setText(location.getName());
                            getBinding().imgCloseSearch3.setClickable(true);
                            getBinding().edtLocation3.setTextColor(mActivity.getResources().getColor(R.color.black));
                        }
                    }));
                }

            }
        });
        getBinding().imgCloseSearch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelected2){
                    addFragment(new SearchLocationFragment(new SearchLocationFragment.DoneSearch() {
                        @Override
                        public void onDoneSearch(Location location) {
                            locationText = locationText + ", "+ location.getName();
                            getBinding().edtLocation3.setText(location.getName());
                        }
                    }));
                }

            }
        });

        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardUtils.hideKeyboard(mActivity, getBinding().edtLocation);
                mActivity.onBackPressed();
            }
        });
        getBinding().btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(doneSearch != null){
                    doneSearch.onDoneSearch(new Location(""));
                    mActivity.onBackPressed();
                }
            }
        });
        getBinding().btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(doneSearch != null){
                    doneSearch.onDoneSearch(new Location(locationText));
                    mActivity.onBackPressed();
                }
            }
        });
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {

    }


    public interface DoneSearch{
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
