package com.namviet.vtvtravel.view.fragment;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.NearDistance;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.NearDisAdapter;
import com.namviet.vtvtravel.adapter.NearTypeAdapter;
import com.namviet.vtvtravel.adapter.ShowAllAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.databinding.FragmentNearYouBinding;
import com.namviet.vtvtravel.listener.ItemChangeListener;
import com.namviet.vtvtravel.model.MyLocation;
import com.namviet.vtvtravel.model.NearType;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.viewmodel.ShowAllViewModel;
import com.namviet.vtvtravel.widget.EndlessRecyclerViewScrollListener;

import java.util.List;
import java.util.Observer;

public class NearYouFragment extends MainFragment implements Observer, ItemChangeListener {
    private FragmentNearYouBinding binding;
    private NearDisAdapter nearDisAdapter;
    private NearTypeAdapter nearTypeAdapter;
    private List<NearDistance> nearDistanceList;
    private List<NearType> nearTypeList;
    private MyLocation myLocation;
    private ShowAllViewModel showAllViewModel;
    private ShowAllAdapter showAllAdapter;
    private int mPage = 1;
    private String type = "";
    private int distance;
    private SupportMapFragment mapFragment;
    private GoogleMap mGoogleMap;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nearDistanceList = MyApplication.getInstance().getNearDistance();
        nearTypeList = MyApplication.getInstance().getNearType();
        myLocation = MyApplication.getInstance().getMyLocation();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_near_you, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        super.initViews(v);
        showAllViewModel = new ShowAllViewModel(getContext());
        binding.setShowAllViewModel(showAllViewModel);
        showAllViewModel.addObserver(this);

        binding.toolBar.ivBack.setOnClickListener(this);
        binding.toolBar.ivSearch.setOnClickListener(this);
        binding.btFilter.setOnClickListener(this);
        mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                addMyLocation();
            }
        });
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        binding.toolBar.tvTitle.setText(getResources().getString(R.string.near_you));
        nearDisAdapter = new NearDisAdapter(getContext(), nearDistanceList);
        nearDisAdapter.setListener(this);
        binding.rvNearDis.setAdapter(nearDisAdapter);
        binding.rvNearDis.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        nearTypeAdapter = new NearTypeAdapter(getContext(), nearTypeList);
        nearTypeAdapter.setListener(this);
        binding.rvNearType.setAdapter(nearTypeAdapter);
        binding.rvNearType.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        showAllAdapter = new ShowAllAdapter();
        binding.rvNear.setAdapter(showAllAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvNear.setLayoutManager(layoutManager);
        NearDistance nearDistance = nearDistanceList.get(nearDisAdapter.getIndexDis());
        NearType nearType = nearTypeList.get(nearTypeAdapter.getIndexType());
        type = nearType.getType();
        distance = nearDistance.getValue();
        binding.tvTitleList.setText(nearType.getName());
        binding.prLoading.setVisibility(View.VISIBLE);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPage = page;
                if (null != myLocation) {
                    showAllViewModel.loadNearYout(mPage, type, "" + distance, myLocation.getLat(), myLocation.getLog());
                }
            }
        };
        binding.rvNear.addOnScrollListener(scrollListener);
        if (null != myLocation) {
            showAllViewModel.loadNearYout(mPage, type, "" + distance, myLocation.getLat(), myLocation.getLog());
        }

    }

    @Override
    public void update(java.util.Observable observable, Object o) {
        if (observable instanceof ShowAllViewModel) {
            binding.prLoading.setVisibility(View.GONE);
            showAllAdapter.setTravelList(showAllViewModel.getTravelList());

            for (int i = 0; i < showAllViewModel.getTravelList().size(); i++) {
                Travel travel = showAllViewModel.getTravelList().get(i);
                createMarker(travel.getLat(), travel.getLng(), travel.getName());
            }

            if (o instanceof ResponseError) {
                ResponseError responseError = (ResponseError) o;
                showMessage(responseError.getMessage());
            }
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.btFilter) {
            NearDistance nearDistance = nearDistanceList.get(nearDisAdapter.getIndexDis());
            NearType nearType = nearTypeList.get(nearTypeAdapter.getIndexType());
            if (!type.equals(nearType)) {
                showAllViewModel.clearList();
                showAllAdapter.setTravelList(showAllViewModel.getTravelList());
                mPage = 1;
                mGoogleMap.clear();
                addMyLocation();
            }
            binding.tvTitleList.setText(nearType.getName());
            binding.prLoading.setVisibility(View.VISIBLE);
            if (null != myLocation) {
                showAllViewModel.loadNearYout(mPage, nearType.getType(), "" + nearDistance.getValue(), myLocation.getLat(), myLocation.getLog());
            }
        }
    }

    protected Marker createMarker(double latitude, double longitude, String title) {

        return mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin_location)));
    }

    private void addMyLocation() {
        if (null != myLocation) {
            LatLng sydney = new LatLng(myLocation.getLat(), myLocation.getLog());
            CameraUpdate center = CameraUpdateFactory.newLatLng(sydney);
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);
            mGoogleMap.addMarker(new MarkerOptions().position(sydney).title(myLocation.getAddress()));
            mGoogleMap.moveCamera(center);
            mGoogleMap.animateCamera(zoom);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showAllViewModel.reset();
    }

    @Override
    public void onChange() {
        NearDistance nearDistance = nearDistanceList.get(nearDisAdapter.getIndexDis());
        NearType nearType = nearTypeList.get(nearTypeAdapter.getIndexType());
        if (!type.equals(nearType)) {
            showAllViewModel.clearList();
            showAllAdapter.setTravelList(showAllViewModel.getTravelList());
            mPage = 1;
            mGoogleMap.clear();
            addMyLocation();
        }
        binding.tvTitleList.setText(nearType.getName());
        binding.prLoading.setVisibility(View.VISIBLE);
        if (null != myLocation) {
            showAllViewModel.loadNearYout(mPage, nearType.getType(), "" + nearDistance.getValue(), myLocation.getLat(), myLocation.getLog());
        }
    }
}
