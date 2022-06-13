package com.namviet.vtvtravel.view.fragment.f2mytrip;

import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.F2FragmentMyTripBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.listener.OnItemRecyclerClickListener;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.newhome.HomeServiceResponse;
import com.namviet.vtvtravel.view.f2.CreateTripActivity;
import com.namviet.vtvtravel.view.f2.DisplayMarkerForMapActivity;
import com.namviet.vtvtravel.view.f2.SmallLocationActivity;
import com.namviet.vtvtravel.view.fragment.f2createtrip.CreateTripFragment;
import com.namviet.vtvtravel.view.fragment.f2mytrip.adapter.MyTripsAdapter;
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.MyTripsResponse;
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem;
import com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel.MyTripsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class MyTripFragment extends BaseFragment<F2FragmentMyTripBinding> implements Observer, OnItemRecyclerClickListener {
    Map<String, String> extraHeaders = new HashMap<>();
    private MyTripsAdapter myTripsAdapter;
    private MyTripsViewModel myTripsViewModel;
    private List<TripItem> tripItemList = new ArrayList<>();
    public MyTripFragment() {
    }

    private String link = WSConfig.HOST_MY_TRIP;
    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_my_trip;
    }

    @Override
    public void initView() {
        myTripsViewModel = new MyTripsViewModel();
        myTripsViewModel.addObserver(this);
        myTripsViewModel.getListScheduleTrips();
        myTripsAdapter = new MyTripsAdapter(getContext());
        myTripsAdapter.setOnClickItemListener(this);
        getBinding().rcvAllSchedule.setAdapter(myTripsAdapter);



    }

    @Override
    public void initData() {

    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().imvBackTrips.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });
        getBinding().imvCreateTrip.setOnClickListener(v -> {
            CreateTripFragment createTripFragment = new CreateTripFragment();
            createTripFragment.setOnBackToTripsFragment(new CreateTripFragment.OnBackToTripsFragment() {
                @Override
                public void backToMainTrips() {
                    myTripsViewModel.getListScheduleTrips();
                }
            });
            addFragment(createTripFragment);
        });
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof MyTripsResponse){
            MyTripsResponse myTripsResponse = (MyTripsResponse) arg;
            if (myTripsResponse.getData() != null && myTripsResponse.getData().getListTrip() != null){
                tripItemList = myTripsResponse.getData().getListTrip();
                myTripsAdapter.setListTripItem(tripItemList);
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        DetailTripFragment detailTripFragment = DetailTripFragment.Companion.newInstance(tripItemList.get(position));
        detailTripFragment.setOnBackToTripsFragment(new DetailTripFragment.OnBackFragmentListener() {
            @Override
            public void onBackFragment() {
                myTripsViewModel.getListScheduleTrips();
            }
        });
        addFragment(detailTripFragment);
    }
}
