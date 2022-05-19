package com.namviet.vtvtravel.view.f2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

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
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityMapBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.help.MySupportMapFragment;
import com.namviet.vtvtravel.model.f2smalllocation.Travel;
import com.namviet.vtvtravel.response.f2menu.MenuItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends BaseActivityNew<F2ActivityMapBinding> {
    private String lat;
    private String lng;
    private String address;
    private String contentType;
    private MySupportMapFragment mapFragment;
    private GoogleMap mGoogleMap;
    private String type;
    private List<Travel> travelList;

    private static final String DATA = "DATA";
    private static final String TYPE_MAP = "TYPE_MAP";
    private static final String SINGLE_MARK = "SINGLE_MARK";
    private static final String MULTI_MARK = "MULTI_MARK";

    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_map;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        type = getIntent().getStringExtra(TYPE_MAP);
        if (SINGLE_MARK.equals(type)) {
            lat = getIntent().getStringExtra(Constants.IntentKey.LAT);
            lng = getIntent().getStringExtra(Constants.IntentKey.LONG);
            address = getIntent().getStringExtra(Constants.IntentKey.ADDRESS);
            contentType = getIntent().getStringExtra("contentType");
        } else {
            travelList = (List<Travel>) getIntent().getSerializableExtra(DATA);
        }
    }

    @Override
    public void doAfterOnCreate() {
        try {
            mapFragment = MySupportMapFragment.newInstance();
            mapFragment.setListener(new MySupportMapFragment.OnTouchListener() {
                @Override
                public void onTouch() {
                    //                    binding.nesScroll.requestDisallowInterceptTouchEvent(true);
                }
            });

//            mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                @Override
//                public boolean onMarkerClick(Marker marker) {
//
//                    Log.d("LamLV: ", marker.getTitle());
//                    return true;
//                }
//            });

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mGoogleMap = googleMap;
                    if (SINGLE_MARK.equals(type)) {
                        addMyLocation(Double.parseDouble(lat), Double.parseDouble(lng), address);
                    } else {
                        for (Travel travel : travelList) {
                            if (travel.getLoc() != null && travel.getLoc().getCoordinates() != null
                                    && travel.getLoc().getCoordinates().get(0) != null
                                    && travel.getLoc().getCoordinates().get(1) != null
                                    && !"".equals(travel.getLoc().getCoordinates().get(0))
                                    && !"".equals(travel.getLoc().getCoordinates().get(1))) {
                                addMyLocation(Double.parseDouble(travel.getLoc().getCoordinates().get(1)), Double.parseDouble(travel.getLoc().getCoordinates().get(0)), travel.getAddress());
                            }
                        }
                    }

                }
            });
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, mapFragment).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setClick() {
        getBinding().btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public BaseFragment initFragment() {
        return null;
    }

    private void addMyLocation(double lat, double lng, String address) {
        try {
            LatLng coordinate = new LatLng(lat, lng); //Store these lat lng values somewhere. These should be constant.
            mGoogleMap.addMarker(new MarkerOptions().position(coordinate).title(address).icon(getMarkerFromContentType(contentType)));
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                    coordinate, 15);
            mGoogleMap.animateCamera(location);
        } catch (Exception e) {

        }
    }

    public static void startScreen(Context activity, String lat, String lng, String address, String contentType) {
        Intent intent = new Intent(activity, MapActivity.class);
        intent.putExtra(Constants.IntentKey.LAT, lat);
        intent.putExtra(Constants.IntentKey.LONG, lng);
        intent.putExtra(Constants.IntentKey.ADDRESS, address);
        intent.putExtra("contentType", contentType);
        intent.putExtra(TYPE_MAP, SINGLE_MARK);
        activity.startActivity(intent);
    }

    public static void startScreen(Context activity, List<Travel> travelList) {
        Intent intent = new Intent(activity, MapActivity.class);
        intent.putExtra(DATA, (Serializable) travelList);
        intent.putExtra(TYPE_MAP, MULTI_MARK);
        activity.startActivity(intent);
    }

    private BitmapDescriptor getMarkerFromContentType(String contentType) {


        switch (contentType) {
            case Constants.TypeSchedule.RESTAURANTS:
                return BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_eat_what);
            case Constants.TypeSchedule.CENTERS:
                return BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_play_what);
            case Constants.TypeSchedule.PLACES:
                return BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_go_where);
            case Constants.TypeSchedule.HOTEL:
                return BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_stay_what);
            default:
                return BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_go_where);
        }
    }
}
