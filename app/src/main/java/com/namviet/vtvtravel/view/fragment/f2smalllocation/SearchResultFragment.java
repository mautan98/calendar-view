package com.namviet.vtvtravel.view.fragment.f2smalllocation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.smalllocation.SmallLocationAdapter;
import com.namviet.vtvtravel.adapter.smalllocation.SubNearbyExperienceInSmallLocationDetailAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F3FragmentNearExperienceSmallLocationBinding;
import com.namviet.vtvtravel.databinding.F3FragmentSearchResultBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.MyLocation;
import com.namviet.vtvtravel.model.f2event.OnDoneFilterOption;
import com.namviet.vtvtravel.model.f2smalllocation.Travel;
import com.namviet.vtvtravel.model.travelnews.Location;
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse;
import com.namviet.vtvtravel.response.f2filter.DistanceClass;
import com.namviet.vtvtravel.response.f2filter.FilterByCodeResponse;
import com.namviet.vtvtravel.response.f2filter.FilterByPageResponse;
import com.namviet.vtvtravel.response.f2searchmain.SearchSuggestionResponse;
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
import com.namviet.vtvtravel.view.f3.model.ClickHideMapView;
import com.namviet.vtvtravel.view.f3.model.HideMapView;
import com.namviet.vtvtravel.view.f3.model.ShowMapView;
import com.namviet.vtvtravel.view.f3.smalllocation.view.fragment.SearchSuggestionSmallLocationFragment;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class SearchResultFragment extends BaseFragment<F3FragmentSearchResultBinding> implements Observer,SearchSuggestionSmallLocationFragment.SearchSuggestionCallback {

    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int REQUEST_CODE_PERMISSION = 2;
    //  private SupportMapFragment mapFragment;
    private GoogleMap mGoogleMap;

    private SmallLocationAdapter smallLocationAdapter;
    private SmallLocationViewModel viewModel;
    private FilterByCodeResponse filterByCodeResponse;
    private SortSmallLocationResponse sortSmallLocationResponse;
    private int positionTabSelected = 0;
    private String typeDestination = Constants.TypeDestination.PLACES;
    private String link;
    private String code;
    private List<com.namviet.vtvtravel.model.f2smalllocation.Travel> travelList = new ArrayList<>();
    private String loadMoreLink;
    private String regionId;

    private Marker lastMarker;
    private Context context;
    private String keyWordSearch;
    private Location location;
    private ArrayList<Location> locationsMain = new ArrayList<>();
    private ArrayList<Location> locations = new ArrayList<>();
    @SuppressLint("ValidFragment")
    public SearchResultFragment(String link, String code, String regionId, String keyWordSearch,int positionTabSelected, Location location) {
        this.link = link;
        this.code = code;
        this.regionId = regionId;
        this.keyWordSearch = keyWordSearch;
        this.positionTabSelected = positionTabSelected;
        this.location = location;
    }

    public SearchResultFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f3_fragment_search_result;
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
        viewModel.getAllLocation();
        getIconForMarker();
        getBinding().rclContent.setVisibility(View.INVISIBLE);
        getBinding().shimmerViewContainer.setVisibility(View.VISIBLE);
        getBinding().shimmerViewContainer.startShimmer();

    }
    @Subscribe
    public void onClickbackHideMap(ClickHideMapView clickHideMapView) {
        getBinding().layoutButtonMap.setVisibility(View.INVISIBLE);
        getBinding().layoutItem.setVisibility(View.GONE);
        getBinding().layoutMap.setVisibility(View.INVISIBLE);
        getBinding().rclContent.setVisibility(View.VISIBLE);
        getBinding().layoutButtonList.setVisibility(View.VISIBLE);
    }
    private void showMenu()  {
        // When user click on the Button 1, create a PopupMenu.
        // And anchor Popup to the Button 2.
        PopupMenu popup = new PopupMenu(mActivity, this.getBinding().btnChooseRegion);
        popup.inflate(R.menu.layout_popup_menu);
        Menu menu = popup.getMenu();
        // com.android.internal.view.menu.MenuBuilder
        // Register Menu Item Click event.
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                menuItemClicked(item);
                handleType(item.getTitle().toString());
                return true;
            }
        });

        // Show the PopupMenu.
        popup.show();
    }
    private void menuItemClicked(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItem_where_go:
                typeDestination = Constants.TypeDestination.PLACES;
                positionTabSelected = 0;
                break;
            case R.id.menuItem_where_stay:
                typeDestination = Constants.TypeDestination.HOTELS;
                positionTabSelected = 1;
                break;
            case R.id.menuItem_what_eat:
                typeDestination = Constants.TypeDestination.RESTAURANTS;
                positionTabSelected = 2;
                break;
            case R.id.menuItem_what_play:
                typeDestination = Constants.TypeDestination.CENTERS;
                positionTabSelected = 3;
                break;
        }
    }
    private void handleType(String type){
        getBinding().tvRegionName.setText(type);
        clearRclData();
        viewModel.getSmallLocation(genLinkToFilter(), false);
    }


    @Override
    public void initData() {
        smallLocationAdapter = new SmallLocationAdapter(travelList, mActivity, new SmallLocationAdapter.ClickItem() {
            @Override
            public void onClickItem(com.namviet.vtvtravel.model.f2smalllocation.Travel travel) {
                addFragment(new DetailSmallLocationFragment(travel.getDetail_link()));
            }

            @Override
            public void likeEvent(int position) {
                try {
                    Account account = MyApplication.getInstance().getAccount();
                    com.namviet.vtvtravel.model.f2smalllocation.Travel travel = travelList.get(position);
                    if (null != account && account.isLogin()) {
                        viewModel.likeEvent(travel.getId(), travel.getContent_type());
                        try {
                            TrackingAnalytic.postEvent(TrackingAnalytic.LIKE, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.SMALL_LOCATIONS, TrackingAnalytic.ScreenTitle.SMALL_LOCATIONS)
                                    .setContent_type(travel.getContent_type())
                                    .setContent_id(travel.getId())
                                    .setScreen_class(this.getClass().getName()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (travel.isLiked()) {
                            travel.setLiked(false);
                        } else {
                            travel.setLiked(true);
                        }
                        smallLocationAdapter.notifyItemChanged(position);
                    } else {
                        LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        getBinding().rclContent.setAdapter(smallLocationAdapter);


        switch (code) {
            case "APP_WHERE_GO":
                typeDestination = Constants.TypeDestination.PLACES;
                getBinding().tvRegionName.setText("Đi đâu");
                break;
            case "APP_WHAT_EAT":
                typeDestination = Constants.TypeDestination.RESTAURANTS;
                getBinding().tvRegionName.setText("Ăn gì");
                break;
            case "APP_WHAT_PLAY":
                typeDestination = Constants.TypeDestination.CENTERS;
                getBinding().tvRegionName.setText("Chơi gì");
                break;
            case "APP_WHERE_STAY":
                typeDestination = Constants.TypeDestination.HOTELS;
                getBinding().tvRegionName.setText("Ở đâu");
                break;
        }
        viewModel.getSmallLocation(link + typeDestination + genLinkToSearch(), false);
        Log.e("xxx", "initData: "+ link + typeDestination + genLinkToSearch());
        viewModel.getFilterByCode();

        viewModel.sortSmallLocation();


        handleSearch();
        initMap();


        try {
            if (ContextCompat.checkSelfPermission(mActivity, mPermission) != MockPackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{mPermission, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERMISSION);
            } else {
//                getLocation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        getBinding().edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new SearchSuggestionSmallLocationFragment( getBinding().edtSearch.getText().toString(), location, locationsMain, false, SearchResultFragment.this));

            }
        });

    }

    public void getLocation() {
        MyTracker tracker = new MyTracker(mActivity, new MyTracker.ADLocationListener() {
            @Override
            public void whereIAM(ADLocation loc) {
                try {
                    PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.LAT_LOCATION, "" + loc.getLat());
                    PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.LNG_LOCATION, "" + loc.getLng());

                    MyApplication.getInstance().setMyLocation(new MyLocation(loc.getCity(), loc.getAddress(), loc.getCountry(), loc.getLat(), loc.getLng()));
                    BaseViewModel baseViewModel = new BaseViewModel();
                    baseViewModel.trackLocation(loc.getLat(), loc.getLng(), DeviceUtils.getDeviceId(mActivity));
                    if (!ServiceUltils.isMyServiceRunning(mActivity, TrackLocationService.class)) {
                        Intent intent = new Intent(mActivity, TrackLocationService.class);
                        mActivity.startService(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        tracker.track();
//        searchViewModel.loadSearchTrend();
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
                onPageSelected(positionTabSelected);
                FilterActivity.startScreen(mActivity, filterByCodeResponse,positionTabSelected);
            }
        });

        getBinding().btnFilter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPageSelected(positionTabSelected);
                FilterActivity.startScreen(mActivity, filterByCodeResponse,positionTabSelected);
            }
        });

        getBinding().btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MapActivity.startScreen(mActivity, travelList);
                getBinding().layoutButtonMap.setVisibility(View.VISIBLE);
//                getBinding().layoutItem.setVisibility(View.VISIBLE);
                getBinding().layoutMap.setVisibility(View.VISIBLE);
                getBinding().layoutButtonList.setVisibility(View.INVISIBLE);
                EventBus.getDefault().post(new ShowMapView());
            }
        });

        getBinding().btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBinding().layoutButtonMap.setVisibility(View.INVISIBLE);
                getBinding().layoutItem.setVisibility(View.GONE);
                getBinding().layoutMap.setVisibility(View.INVISIBLE);
                getBinding().rclContent.setVisibility(View.VISIBLE);
                getBinding().layoutButtonList.setVisibility(View.VISIBLE);
                EventBus.getDefault().post(new HideMapView());
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
//                addFragment(new SearchLocationFragment(new SearchLocationFragment.DoneSearch() {
//                    @Override
//                    public void onDoneSearch(Location location) {
//                        getBinding().tvRegionName.setText(location.getName());
//                        regionId = location.getId();
//                        clearRclData();
//                        viewModel.getSmallLocation(genLinkToFilter(), false);
//                    }
//                }));
                showMenu();
            }
        });
        if(keyWordSearch != null && !keyWordSearch.equals("")){
            getBinding().edtSearch.setText(keyWordSearch);
        }
        getBinding().imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBinding().edtSearch.setText("Bạn muốn tìm gì?");
                keyWordSearch = "";
            }
        });
    }
    public void onPageSelected(int position) {
        for (int i = 0; i < filterByCodeResponse.getData().getItems().size(); i++) {
            if (position == i) {
                filterByCodeResponse.getData().getItems().get(position).setSelected(true);
                Log.e("", "");
            } else filterByCodeResponse.getData().getItems().get(i).setSelected(false);
        }
    }
    private void clearRclData() {
        travelList.clear();
        smallLocationAdapter.notifyDataSetChanged();
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void update(Observable observable, Object o) {
        Log.e("xxx", "update: observable" );
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getBinding().rclContent.setVisibility(View.VISIBLE);
                    getBinding().shimmerViewContainer.setVisibility(View.GONE);
                    getBinding().shimmerViewContainer.stopShimmer();
                }
            },500);

        } catch (Exception e) {
            e.printStackTrace();
        }

        hideLoading();
        if (observable instanceof SmallLocationViewModel && null != o) {
            if (o instanceof SmallLocationResponse) {
                SmallLocationResponse response = (SmallLocationResponse) o;
                loadMoreLink = response.getData().getMore_link();
                if (response.isLoadMore()) {
                    travelList.addAll(response.getData().getItems());
                } else {
                    travelList.clear();
                    travelList.addAll(response.getData().getItems());
                }
                smallLocationAdapter.notifyDataSetChanged();
//                getBinding().tvRegionName.setText(response.getData().getNameRegion());

                try {
                    if (travelList == null || (travelList != null && travelList.size() == 0)) {
                        showToast("Không tìm thấy địa điểm");
                    }
                    getBinding().layoutItem.setVisibility(View.GONE);
                    mGoogleMap.clear();
                    lastMarker = null;
                    for (com.namviet.vtvtravel.model.f2smalllocation.Travel travel : travelList) {
                        try {
                            if (travel.getLoc() != null && travel.getLoc().getCoordinates() != null
                                    && travel.getLoc().getCoordinates().get(0) != null
                                    && travel.getLoc().getCoordinates().get(1) != null
                                    && !"".equals(travel.getLoc().getCoordinates().get(0))
                                    && !"".equals(travel.getLoc().getCoordinates().get(1))) {
                                addMyLocation(Double.parseDouble(
                                        travel.getLoc().getCoordinates().get(1)),
                                        Double.parseDouble(travel.getLoc().getCoordinates().get(0)),
                                        travel.getAddress(),
                                        travel.getId(),
                                        travel.getContent_type()
                                );
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (o instanceof FilterByCodeResponse) {
                filterByCodeResponse = (FilterByCodeResponse) o;
                getDefaultSelectedFilterTab();
                setDefaultSelectedFilterTab(positionTabSelected);
                setDistance();
            } else if (o instanceof SortSmallLocationResponse) {
                sortSmallLocationResponse = (SortSmallLocationResponse) o;

            }
            else if (o instanceof AllLocationResponse){
                  AllLocationResponse data = ((AllLocationResponse) o);
                  locationsMain = (ArrayList<Location>) data.getData();
                  locations.addAll(0, locationsMain);
            }
            else if (o instanceof ErrorResponse) {
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
        getAndSetPlaceHolder();
    }

    private void setDefaultSelectedFilterTab(int position) {
        filterByCodeResponse.getData().getItems().get(position).setSelected(true);
    }

    private void getDefaultSelectedFilterTab() {
        for (int i = 0; i < filterByCodeResponse.getData().getItems().size(); i++) {
            if (filterByCodeResponse.getData().getItems().get(i).getCode().equals(code)) {
                positionTabSelected = i;
                return;
            }
        }
    }

    public void setDistance() {
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

    private void getMainCategory() {
        int size = filterByCodeResponse.getData().getItems().size();
        for (int i = 0; i < size; i++) {
            if (filterByCodeResponse.getData().getItems().get(i).isSelected()) {
                switch (filterByCodeResponse.getData().getItems().get(i).getCode()) {
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

    private String getParamForFilterService() {
        try {
            String baseFilter = "";
            String typeFilter = "";
            int size = filterByCodeResponse.getData().getItems().size();

            for (int i = 0; i < size; i++) {
                if (filterByCodeResponse.getData().getItems().get(i).isSelected()) {
                    FilterByPageResponse dataHasLoaded = filterByCodeResponse.getData().getItems().get(i).getDataHasLoaded();


                    if (dataHasLoaded != null) {
                        for (int j = 0; j < dataHasLoaded.getData().size(); j++) {
                            if (dataHasLoaded.getData().get(j).isSelected() && !dataHasLoaded.getData().get(j).getField().equals("standard_rate")) {
                                baseFilter = baseFilter + dataHasLoaded.getData().get(j).getField();
                                List<FilterByPageResponse.Data.Input> inputs = dataHasLoaded.getData().get(j).getInputs();


                                if (inputs != null) {
                                    for (int k = 0; k < inputs.size(); k++) {
                                        if (inputs.get(k).isSelected()) {
                                            typeFilter = typeFilter + inputs.get(k).getValue() + ",";
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
            if (!baseFilter.isEmpty()) {
                if (!typeFilter.isEmpty()) {
                    typeFilter = typeFilter.substring(0, typeFilter.length() - 1);
                    result = "&" + baseFilter + "=" + typeFilter;
                }
            } else {

            }


            String baseFilterRate = "standard_rate";
            String typeFilterRate = "";
            for (int i = 0; i < size; i++) {
                if (filterByCodeResponse.getData().getItems().get(i).getCode().equals("APP_WHERE_STAY")) {
                    if (filterByCodeResponse.getData().getItems().get(i).isSelected()) {
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

            if (!typeFilterRate.isEmpty()) {
                typeFilterRate = typeFilterRate.substring(0, typeFilterRate.length() - 1);
                result = result + "&" + baseFilterRate + "=" + typeFilterRate;
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    private String getParamFilter() {
        String result = "";
        // field filter
        String baseFilter = "";
        // value filter
        String typeFilter = "";
        int size = filterByCodeResponse.getData().getItems().size();
        for (int i = 0; i < size; i++) {
            FilterByPageResponse dataHasLoaded = filterByCodeResponse.getData().getItems().get(i).getDataHasLoaded();
            if (dataHasLoaded != null) {
                for (int j = 0; j < dataHasLoaded.getData().size(); j++) {
                    List<FilterByPageResponse.Data.Input> inputs = dataHasLoaded.getData().get(j).getInputs();
                    Map<String, String> mapData = new HashMap<>();
                    if (inputs != null) {
                        for (int k = 0; k < inputs.size(); k++) {
                            if (inputs.get(k).isSelected()) {
                                baseFilter = dataHasLoaded.getData().get(j).getField();
                                typeFilter = inputs.get(k).getValue();
                                mapData.put(typeFilter, baseFilter);
                            }
                        }

                    }
                    Set<String> set = mapData.keySet();
                    String keyFilter = "";
                    String paramFilter = "";
                    for (String key : set) {
                        keyFilter = mapData.get(key);
                        paramFilter = paramFilter + key+",";

                    }
                    if (!paramFilter.isEmpty()) {
                        paramFilter = paramFilter.substring(0, paramFilter.length() - 1);
                        result = result + "&" + keyFilter + "=" + paramFilter;
                    }
                }
            }
        }

        return result;
    }

    private String getDistance() {
        String result = "";
        for (int i = 0; i < filterByCodeResponse.getDistanceClass().getDistances().size(); i++) {
            if (filterByCodeResponse.getDistanceClass().getDistances().get(i).isSelected()) {
                result = result + "&" + "distance=" + filterByCodeResponse.getDistanceClass().getDistances().get(i).getValue();
                break;
            }
        }
        return result;
    }

    private String getOpenType() {
        String result = "";
        result = result + "&" + "type_open=" + filterByCodeResponse.isTypeOpen();
        return result;
    }

    private String genLinkToFilter() {
        try {
            String result = link + typeDestination + getParamFilter() + getDistance() + getOpenType() + genLinkSort() + genLinkRegionId() + genLinkSearch(keyWordSearch);
            Log.e("resultttt", result);
            return result;
        } catch (Exception e) {
            return "";
        }
    }
    private String genLinkToSearch() {
        try {
            String result = genLinkRegionId() + genLinkSearch(keyWordSearch);
            Log.e("search", result);
            return result;
        } catch (Exception e) {
            return "";
        }
    }


    private String genLinkSort() {
        try {
            String result = "";
            for (int i = 0; i < sortSmallLocationResponse.getData().getItems().size(); i++) {
                if (sortSmallLocationResponse.getData().getItems().get(i).isChecked()) {
                    result = result + "&" + "sort=" + sortSmallLocationResponse.getData().getItems().get(i).getFields();
                    return result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    private String genLinkRegionId() {
        try {
            String result = "";
            if (!regionId.isEmpty()) {
                result = result + "&" + "region_id=" + regionId;
                return result;
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    private String genLinkSearch(String keyword) {
        try {
            String result = "";
            if (!keyword.isEmpty()) {
                result = result + "&" + "name=" + keyword;
                return result;
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    @SuppressLint("CheckResult")
    private void handleSearch() {
        RxTextView.afterTextChangeEvents(getBinding().edtSearch)
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textViewAfterTextChangeEvent -> {
                    clearRclData();
                    viewModel.getSmallLocation(genLinkToFilter(), false);
                });
    }

    private void resetFilter() {

    }

    private void getAndSetPlaceHolder() {
        try {
            for (int i = 0; i < filterByCodeResponse.getData().getItems().size(); i++) {
                if (filterByCodeResponse.getData().getItems().get(i).isSelected()) {
                    String code = filterByCodeResponse.getData().getItems().get(i).getCode();
                    switch (code) {
                        case "APP_WHERE_GO":
                            getBinding().edtSearch.setHint("Bạn muốn đi đâu");
                            break;
                        case "APP_WHAT_EAT":
                            getBinding().edtSearch.setHint("Bạn muốn ăn gì");
                            break;
                        case "APP_WHAT_PLAY":
                            getBinding().edtSearch.setHint("Bạn muốn chơi gì");
                            break;
                        case "APP_WHERE_STAY":
                            getBinding().edtSearch.setHint("Bạn muốn ở đâu");
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {

                }
                break;
        }
    }

    private void initItemMarker(com.namviet.vtvtravel.model.f2smalllocation.Travel travel) {
        getBinding().layoutItem.setVisibility(View.VISIBLE);
        Glide.with(mActivity).load(travel.getLogo_url()).into(getBinding().imgAvatar);
        getBinding().tvName.setText(travel.getName());
        getBinding().tvRate.setText(travel.getEvaluate());
        getBinding().tvRateText.setText(travel.getEvaluate_text());
        getBinding().tvCommentCount.setText(travel.getComment_count());
        getBinding().tvAddress.setText(travel.getAddress());
        getBinding().tvLocationName.setText(travel.getRegion_name());

        getBinding().tvType.setText(travel.getType());

        try {
            if (travel.isHas_location()) {
                if (travel.getDistance() != null && !"".equals(travel.getDistance()) && Double.parseDouble(travel.getDistance()) < 1000) {
                    getBinding().tvDistance.setText(travel.getDistance_text() + " " + travel.getDistance() + " m");
                } else if (travel.getDistance() != null && !"".equals(travel.getDistance())) {
                    double finalValue = Math.round(Double.parseDouble(travel.getDistance()) / 1000 * 10.0) / 10.0;
                    getBinding().tvDistance.setText(travel.getDistance_text() + " " + finalValue + " km");
                }
            } else {
                getBinding().tvDistance.setText("Không xác định");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (Constants.TypeDestination.RESTAURANTS.equals(travel.getContent_type()) || Constants.TypeDestination.HOTELS.equals(travel.getContent_type())) {
            getBinding().linearPriceType.setVisibility(View.VISIBLE);
            getBinding().linearOpenType.setVisibility(View.GONE);
            getBinding().tvPriceRange.setText(travel.getPrice_from() + " đ" + " - " + travel.getPrice_to() + " đ");
        } else {
            getBinding().linearPriceType.setVisibility(View.GONE);
            getBinding().linearOpenType.setVisibility(View.VISIBLE);

            try {
                if (travel.getRange_time().isEmpty()) {
                    getBinding().viewTime.setVisibility(View.GONE);
                    getBinding().tvOpenTime.setVisibility(View.GONE);
                } else {
                    getBinding().viewTime.setVisibility(View.VISIBLE);
                    getBinding().tvOpenTime.setText(travel.getRange_time());
                    getBinding().tvOpenTime.setVisibility(View.VISIBLE);
                    getBinding().tvOpenDate.setText(travel.getOpen_week());
                    getBinding().tvStatus.setText(travel.getType_open());

                    try {
                        getBinding().tvStatus.setTextColor(Color.parseColor(travel.getTypeOpenColor()));
                    } catch (Exception e) {
                        try {
                            getBinding().tvStatus.setTextColor(Color.parseColor("#FF0000"));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                getBinding().viewTime.setVisibility(View.GONE);
                getBinding().tvOpenTime.setVisibility(View.GONE);
            }
        }

        try {
            if (travel.getRange_time().isEmpty()) {
                getBinding().viewTime.setVisibility(View.GONE);
                getBinding().tvOpenTime.setVisibility(View.GONE);
            } else {
                getBinding().viewTime.setVisibility(View.VISIBLE);
                getBinding().tvOpenTime.setText(travel.getRange_time());
                getBinding().tvOpenTime.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            getBinding().viewTime.setVisibility(View.GONE);
            getBinding().tvOpenTime.setVisibility(View.GONE);
        }
    }

    private void initMap() {
        try {
            // mapFragment = SupportMapFragment.newInstance();

            getBinding().mapView.onCreate(null);
            getBinding().mapView.onResume();
            getBinding().mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mGoogleMap = googleMap;
                    lastMarker = null;
                    for (com.namviet.vtvtravel.model.f2smalllocation.Travel travel : travelList) {
                        try {
                            if (travel.getLoc() != null && travel.getLoc().getCoordinates() != null
                                    && travel.getLoc().getCoordinates().get(0) != null
                                    && travel.getLoc().getCoordinates().get(1) != null
                                    && !"".equals(travel.getLoc().getCoordinates().get(0))
                                    && !"".equals(travel.getLoc().getCoordinates().get(1))) {
                                addMyLocation(Double.parseDouble(
                                        travel.getLoc().getCoordinates().get(1)),
                                        Double.parseDouble(travel.getLoc().getCoordinates().get(0)),
                                        travel.getAddress(),
                                        travel.getId(),
                                        travel.getContent_type());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            try {

                                for (Travel travel : travelList) {
                                    if (marker.getSnippet().equals(travel.getId())) {
                                        initItemMarker(travel);
                                    }
                                }

                                try {
                                    updateMarker(marker, travelList.get(0).getContent_type());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return true;
                        }
                    });

                    mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            getBinding().layoutItem.setVisibility(View.GONE);
                        }
                    });
                }
            });
//            mapFragment.getMapAsync(new OnMapReadyCallback() {
//                @Override
//                public void onMapReady(GoogleMap googleMap) {
//
//                }
//            });
            //mActivity.getSupportFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMyLocation(double lat, double lng, String address, String id, String contentType) {
        try {
            LatLng coordinate = new LatLng(lat, lng); //Store these lat lng values somewhere. These should be constant.
            restaurants = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_eat_what);
            centers = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_play_what);
            places = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_go_where);
            hotels = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_stay_what);


            restaurantsBig = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_eat_what_big);
            centersBig = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_play_what_big);
            placesBig = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_go_where_big);
            hotelsBig = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_stay_what_big);
            switch (contentType) {
                case Constants.TypeSchedule.RESTAURANTS:
                    mGoogleMap.addMarker(new MarkerOptions().position(coordinate).title(address).snippet(id).icon(restaurants));
                    break;
                case Constants.TypeSchedule.CENTERS:
                    mGoogleMap.addMarker(new MarkerOptions().position(coordinate).title(address).snippet(id).icon(centers));
                    break;
                case Constants.TypeSchedule.PLACES:
                    mGoogleMap.addMarker(new MarkerOptions().position(coordinate).title(address).snippet(id).icon(places));
                    break;
                case Constants.TypeSchedule.HOTEL:
                    mGoogleMap.addMarker(new MarkerOptions().position(coordinate).title(address).snippet(id).icon(hotels));
                    break;
                default:
                    mGoogleMap.addMarker(new MarkerOptions().position(coordinate).title(address).snippet(id));
                    break;
            }


            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                    coordinate, 15);
            mGoogleMap.animateCamera(location);
        } catch (Exception e) {

        }
    }

    private void updateMarker(Marker marker, String contentType) {
        restaurants = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_eat_what);
        centers = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_play_what);
        places = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_go_where);
        hotels = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_stay_what);


        restaurantsBig = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_eat_what_big);
        centersBig = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_play_what_big);
        placesBig = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_go_where_big);
        hotelsBig = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_stay_what_big);

        switch (contentType) {
            case Constants.TypeSchedule.RESTAURANTS:
                if (lastMarker == null) {
                    marker.setIcon(restaurantsBig);
                    lastMarker = marker;
                } else {
                    lastMarker.setIcon(restaurants);
                    marker.setIcon(restaurantsBig);
                    lastMarker = marker;
                }

                break;
            case Constants.TypeSchedule.CENTERS:
                if (lastMarker == null) {
                    marker.setIcon(centersBig);
                    lastMarker = marker;
                } else {
                    lastMarker.setIcon(centers);
                    marker.setIcon(centersBig);
                    lastMarker = marker;
                }
                break;
            case Constants.TypeSchedule.PLACES:
                if (lastMarker == null) {
                    marker.setIcon(placesBig);
                    lastMarker = marker;
                } else {
                    lastMarker.setIcon(places);
                    marker.setIcon(placesBig);
                    lastMarker = marker;
                }
                break;
            case Constants.TypeSchedule.HOTEL:
                if (lastMarker == null) {
                    marker.setIcon(hotelsBig);
                    lastMarker = marker;
                } else {
                    lastMarker.setIcon(hotels);
                    marker.setIcon(hotelsBig);
                    lastMarker = marker;
                }
                break;
            default:
                if (lastMarker == null) {
                    marker.setIcon(hotelsBig);
                    lastMarker = marker;
                } else {
                    lastMarker.setIcon(hotels);
                    marker.setIcon(hotelsBig);
                    lastMarker = marker;
                }
                break;
        }


    }


    private BitmapDescriptor restaurants;
    private BitmapDescriptor centers;
    private BitmapDescriptor places;
    private BitmapDescriptor hotels;


    private BitmapDescriptor restaurantsBig;
    private BitmapDescriptor centersBig;
    private BitmapDescriptor placesBig;
    private BitmapDescriptor hotelsBig;

    private void getIconForMarker() {
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.SMALL_LOCATIONS, TrackingAnalytic.ScreenTitle.SMALL_LOCATIONS);
    }

    @Override
    public void onClickSuggestion(@Nullable SearchSuggestionResponse.Data.Item searchKeywordSuggestion, @Nullable Location location) {
        keyWordSearch = searchKeywordSuggestion.getTitle();
        getBinding().edtSearch.setText(keyWordSearch);
        clearRclData();
        viewModel.getSmallLocation(genLinkToFilter(), false);
    }

    @Override
    public void onCancelSearch(@Nullable Location location, @Nullable String keyword) {
        getBinding().edtSearch.setText(keyword);

    }

    @Override
    public void onClickRegion(@Nullable Location location, @Nullable String keyword) {
        this.location = location;
    }

    @Override
    public void onClickLayoutKeyword(@Nullable Location location, @Nullable String keyword) {

    }
}