package com.namviet.vtvtravel.view.fragment.f2smalllocation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.smalllocation.DetailSmallLocationAdapter;
import com.namviet.vtvtravel.adapter.smalllocation.SmallLocationAdapter;
import com.namviet.vtvtravel.adapter.smalllocation.SubNearbyExperienceInSmallLocationDetailAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentSmallLocationBinding;
import com.namviet.vtvtravel.databinding.F3FragmentNearExperienceSmallLocationBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.MyLocation;
import com.namviet.vtvtravel.model.f2event.OnDoneFilterOption;
import com.namviet.vtvtravel.model.f2smalllocation.Travel;
import com.namviet.vtvtravel.model.travelnews.Location;
import com.namviet.vtvtravel.response.f2filter.DistanceClass;
import com.namviet.vtvtravel.response.f2filter.FilterByCodeResponse;
import com.namviet.vtvtravel.response.f2filter.FilterByPageResponse;
import com.namviet.vtvtravel.response.f2review.GetReviewResponse;
import com.namviet.vtvtravel.response.f2smalllocation.DetailSmallLocationResponse;
import com.namviet.vtvtravel.response.f2smalllocation.SmallLocationResponse;
import com.namviet.vtvtravel.response.f2smalllocation.SortSmallLocationResponse;
import com.namviet.vtvtravel.service.TrackLocationService;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.ultils.DeviceUtils;
import com.namviet.vtvtravel.ultils.PreferenceUtil;
import com.namviet.vtvtravel.ultils.ServiceUltils;
import com.namviet.vtvtravel.view.f2.FilterActivity;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.fragment.f2filter.SortDialog;
import com.namviet.vtvtravel.view.fragment.nearbyexperience.SearchLocationFragment;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;
import com.namviet.vtvtravel.viewmodel.f2smalllocation.DetailSmallLocationViewModel;
import com.namviet.vtvtravel.viewmodel.f2smalllocation.SmallLocationViewModel;

import org.ankit.gpslibrary.ADLocation;
import org.ankit.gpslibrary.MyTracker;
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

public class ListNearByFragment extends BaseFragment<F3FragmentNearExperienceSmallLocationBinding> implements Observer {

    private String link;
    private SmallLocationViewModel viewModel;
    private List<DetailSmallLocationResponse.Data.Menu> items = new ArrayList<>();
    private SubNearbyExperienceInSmallLocationDetailAdapter subNearbyExperienceInSmallLocationDetailAdapter;
    private TabLayout tabs;
    private ArrayList<String> tabsList = new ArrayList<>();
    @SuppressLint("ValidFragment")
    public ListNearByFragment(String link) {
        this.link = link;

    }
    @SuppressLint("ValidFragment")
    public ListNearByFragment(List<DetailSmallLocationResponse.Data.Menu> items) {
        this.items = items;

    }

    public ListNearByFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f3_fragment_near_experience_small_location;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
    }

    @Override
    public void initView() {
        tabsList.clear();
        for (int i = 0; i < items.size(); i++) {
            tabsList.add(items.get(i).getName());
        }
        genTab();
        getBinding().tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setOnSelectView(getBinding().tabs, tab.getPosition());
                loadData(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setUnSelectView(getBinding().tabs, tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        viewModel = new SmallLocationViewModel();
        getBinding().setDetailSmallLocationViewModel(viewModel);
        viewModel.addObserver(this);
        loadData(0);
    //    viewModel.getDetailSmallLocation(link, false);
        getBinding().rclContent.setVisibility(View.INVISIBLE);
        getBinding().shimmerMain.setVisibility(View.VISIBLE);
        getBinding().btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });
        subNearbyExperienceInSmallLocationDetailAdapter = new SubNearbyExperienceInSmallLocationDetailAdapter(travelList, mActivity, null);

        getBinding().rclContent.setAdapter(subNearbyExperienceInSmallLocationDetailAdapter);
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
    }
    private void stopLoading(){
        getBinding().rclContent.setVisibility(View.VISIBLE);
        getBinding().shimmerMain.setVisibility(View.GONE);
    }

    @Override
    public void initData() {


    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {

    }

    @Override
    public void setObserver() {

    }

    private SmallLocationResponse response;
    private String loadMoreLink;
    private List<Travel> travelList = new ArrayList<>();
    @Override
    public void update(Observable observable, Object o) {
        getBinding().shimmerMain.setVisibility(View.GONE);
        if (observable instanceof SmallLocationViewModel && null != o) {
            if (o instanceof SmallLocationResponse) {
                try {
                    response = (SmallLocationResponse) o;
                    getBinding().rclContent.setVisibility(View.VISIBLE);
                    loadMoreLink = response.getData().getMore_link();
                    if (response.isLoadMore()) {
                        travelList.addAll(response.getData().getItems());
                    } else {
                        travelList.clear();
                        travelList.addAll(response.getData().getItems());
                    }
                    subNearbyExperienceInSmallLocationDetailAdapter.notifyDataSetChanged();
                    stopLoading();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (o instanceof ErrorResponse) {
                stopLoading();
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }
        }
    }
    private void loadData(int i) {
        getBinding().shimmerMain.setVisibility(View.VISIBLE);
        getBinding().rclContent.setVisibility(View.INVISIBLE);
        viewModel.getSmallLocation(items.get(i).getLink(),false);
    }
    public void setOnSelectView(TabLayout tabLayout, int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        TextView iv_text = selected.findViewById(R.id.tvTitle);
        View view = selected.findViewById(R.id.indicator);
        view.setVisibility(View.VISIBLE);
        iv_text.setTextColor(Color.parseColor("#00918D"));

    }

    public void setUnSelectView(TabLayout tabLayout, int position) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        TextView iv_text = selected.findViewById(R.id.tvTitle);
        View view = selected.findViewById(R.id.indicator);
        view.setVisibility(View.INVISIBLE);
        iv_text.setTextColor(Color.parseColor("#101010"));

    }
    private void genTab() {
        try {
            for (int i = 0; i < tabsList.size(); i++) {
//                getBinding().tabs.addTab(getBinding().tabs.newTab().setText(detailSmallLocationResponse.getData().getTabs().get(i).getTitle()));
                View tabHome = LayoutInflater.from(mActivity).inflate(R.layout.f2_custom_tab_vtv_style, null);
                TextView tvHome = tabHome.findViewById(R.id.tvTitle);
                tvHome.setText((tabsList.get(i)));
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
                getBinding().tabs.addTab(getBinding().tabs.newTab().setCustomView(tabHome));
                //            getBinding().tabs.addTab(getBinding().tabs.newTab().setText(detailSmallLocationResponse.getData().getTabs().get(i).getTitle()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
