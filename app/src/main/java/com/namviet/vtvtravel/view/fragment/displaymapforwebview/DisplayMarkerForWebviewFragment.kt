package com.namviet.vtvtravel.view.fragment.displaymapforwebview

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.config.Constants
import com.namviet.vtvtravel.databinding.F2FragmentDisplayMarkerForWebviewBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.f2smalllocation.Travel
import com.namviet.vtvtravel.response.f2smalllocation.SmallLocationResponse
import com.namviet.vtvtravel.viewmodel.f2smalllocation.SmallLocationViewModel
import kotlinx.android.synthetic.main.f2_fragment_display_marker_for_webview.*
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("ValidFragment")
class DisplayMarkerForWebviewFragment : BaseFragment<F2FragmentDisplayMarkerForWebviewBinding>, Observer {
    var link: String? = null
    var mapFragment: SupportMapFragment? = null
    var mGoogleMap: GoogleMap? = null
    var viewModel: SmallLocationViewModel? = null
    var travelList: ArrayList<Travel> = ArrayList()
    var lastMarker: Marker? = null

    constructor(link: String?) : super() {
        this.link = link
    }


    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_display_marker_for_webview
    }

    override fun initView() {
        viewModel = SmallLocationViewModel();
        viewModel?.addObserver(this)


        mapFragment = SupportMapFragment.newInstance()
        mapFragment?.getMapAsync(OnMapReadyCallback { googleMap ->
            mGoogleMap = googleMap;
            viewModel?.getSmallLocation(link, false)


            mGoogleMap?.setOnMarkerClickListener { marker ->
                try {
                    for (travel in travelList) {
                        if (marker.snippet == travel.id) {
                            initItemMarker(travel)
                        }
                    }
                    try {
                        updateMarker(marker, travelList[0].content_type)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                true
            }
        })
        mActivity.supportFragmentManager.beginTransaction().replace(R.id.mapView, mapFragment!!).commit()
    }
    override fun initData() {}
    override fun inject() {}
    override fun setClickListener() {
        btnBack.setOnClickListener{
            mActivity.onBackPressed()
        }
    }
    override fun setObserver() {}



    private fun addMyLocation(lat: Double, lng: Double, address: String, id: String, contentType: String) {
        try {
            val coordinate = LatLng(lat, lng) //Store these lat lng values somewhere. These should be constant.
            restaurants = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_eat_what)
            centers = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_play_what)
            places = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_go_where)
            hotels = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_stay_what)
            restaurantsBig = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_eat_what_big)
            centersBig = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_play_what_big)
            placesBig = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_go_where_big)
            hotelsBig = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_stay_what_big)
            when (contentType) {
                Constants.TypeSchedule.RESTAURANTS -> mGoogleMap!!.addMarker(MarkerOptions().position(coordinate).title(address).snippet(id).icon(restaurants))
                Constants.TypeSchedule.CENTERS -> mGoogleMap!!.addMarker(MarkerOptions().position(coordinate).title(address).snippet(id).icon(centers))
                Constants.TypeSchedule.PLACES -> mGoogleMap!!.addMarker(MarkerOptions().position(coordinate).title(address).snippet(id).icon(places))
                Constants.TypeSchedule.HOTEL -> mGoogleMap!!.addMarker(MarkerOptions().position(coordinate).title(address).snippet(id).icon(hotels))
                else -> mGoogleMap!!.addMarker(MarkerOptions().position(coordinate).title(address).snippet(id))
            }
            val location = CameraUpdateFactory.newLatLngZoom(
                    coordinate, 15f)
            mGoogleMap!!.animateCamera(location)
        } catch (e: java.lang.Exception) {
        }
    }

    private fun updateMarker(marker: Marker, contentType: String) {
        restaurants = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_eat_what)
        centers = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_play_what)
        places = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_go_where)
        hotels = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_stay_what)
        restaurantsBig = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_eat_what_big)
        centersBig = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_play_what_big)
        placesBig = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_go_where_big)
        hotelsBig = BitmapDescriptorFactory.fromResource(R.drawable.f2_ic_marker_stay_what_big)
        lastMarker = when (contentType) {
            Constants.TypeSchedule.RESTAURANTS -> if (lastMarker == null) {
                marker.setIcon(restaurantsBig)
                marker
            } else {
                lastMarker!!.setIcon(restaurants)
                marker.setIcon(restaurantsBig)
                marker
            }
            Constants.TypeSchedule.CENTERS -> if (lastMarker == null) {
                marker.setIcon(centersBig)
                marker
            } else {
                lastMarker!!.setIcon(centers)
                marker.setIcon(centersBig)
                marker
            }
            Constants.TypeSchedule.PLACES -> if (lastMarker == null) {
                marker.setIcon(placesBig)
                marker
            } else {
                lastMarker!!.setIcon(places)
                marker.setIcon(placesBig)
                marker
            }
            Constants.TypeSchedule.HOTEL -> if (lastMarker == null) {
                marker.setIcon(hotelsBig)
                marker
            } else {
                lastMarker!!.setIcon(hotels)
                marker.setIcon(hotelsBig)
                marker
            }
            else -> if (lastMarker == null) {
                marker.setIcon(hotelsBig)
                marker
            } else {
                lastMarker!!.setIcon(hotels)
                marker.setIcon(hotelsBig)
                marker
            }
        }
    }


    private var restaurants: BitmapDescriptor? = null
    private var centers: BitmapDescriptor? = null
    private var places: BitmapDescriptor? = null
    private var hotels: BitmapDescriptor? = null


    private var restaurantsBig: BitmapDescriptor? = null
    private var centersBig: BitmapDescriptor? = null
    private var placesBig: BitmapDescriptor? = null
    private var hotelsBig: BitmapDescriptor? = null

    override fun update(observable: Observable?, o: Any?) {
        if (observable is SmallLocationViewModel && null != o) {
            when (o) {
                is SmallLocationResponse -> {


                    travelList.clear()
                    travelList.addAll(o.data.items)

                    mGoogleMap?.clear()
                    lastMarker = null
                    try {
                        for (i in 0 until travelList.size){
                            var travel = travelList[i]
                            try {
                                if (travel.loc != null && travel.loc.coordinates != null && travel.loc.coordinates[0] != null && travel.loc.coordinates[1] != null && "" != travel.loc.coordinates[0]
                                        && "" != travel.loc.coordinates[1]) {
                                    addMyLocation(
                                            travel.loc.coordinates[1].toDouble(), travel.loc.coordinates[0].toDouble(),
                                            travel.address,
                                            travel.id,
                                            travel.content_type
                                    )
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                is ErrorResponse -> {
                    val responseError = o
                    try {

                    } catch (e: Exception) {

                    }
                }
            }
        }
    }


    private fun initItemMarker(travel: Travel) {
        layoutItem.visibility = View.VISIBLE
        Glide.with(mActivity).load(travel.logo_url).into(binding.imgAvatar)
        tvName.text = travel.name
        tvRate.text = travel.evaluate
        tvRateText.text = travel.evaluate_text
        tvCommentCount.text = travel.comment_count
        tvAddress.text = travel.address
        tvLocationName.text = travel.region_name
        tvType.text = travel.type
        try {
            if (travel.isHas_location) {
                if (travel.distance != null && "" != travel.distance && travel.distance.toDouble() < 1000) {
                    tvDistance.text = travel.distance_text + " " + travel.distance + " m"
                } else if (travel.distance != null && "" != travel.distance) {
                    val finalValue = Math.round(travel.distance.toDouble() / 1000 * 10.0) / 10.0
                    tvDistance.text = travel.distance_text + " " + finalValue + " km"
                }
            } else {
                tvDistance.text = "Không xác định"
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        if (Constants.TypeDestination.RESTAURANTS == travel.content_type || Constants.TypeDestination.HOTELS == travel.content_type) {
            linearPriceType.visibility = View.VISIBLE
            linearOpenType.visibility = View.GONE
            tvPriceRange.text = travel.price_from + " đ" + " - " + travel.price_to + " đ"
        } else {
            linearPriceType.visibility = View.GONE
            linearOpenType.visibility = View.VISIBLE
            try {
                if (travel.range_time.isEmpty()) {
                    viewTime.visibility = View.GONE
                    tvOpenTime.visibility = View.GONE
                } else {
                    viewTime.visibility = View.VISIBLE
                    tvOpenTime.text = travel.range_time
                    tvOpenTime.visibility = View.VISIBLE
                    tvOpenDate.text = travel.open_week
                    tvStatus.text = travel.type_open


                    try {
                        tvStatus.setTextColor(Color.parseColor(travel.typeOpenColor))
                    } catch (e: Exception) {
                        try {
                            tvStatus.setTextColor(Color.parseColor("#FF0000"))
                        } catch (e: Exception) {
                        }
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                viewTime.visibility = View.GONE
                tvOpenTime.visibility = View.GONE
            }
        }
        try {
            if (travel.range_time.isEmpty()) {
                viewTime.visibility = View.GONE
                tvOpenTime.visibility = View.GONE
            } else {
                viewTime.visibility = View.VISIBLE
                tvOpenTime.text = travel.range_time
                tvOpenTime.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
            viewTime.visibility = View.GONE
            tvOpenTime.visibility = View.GONE
        }
    }
}