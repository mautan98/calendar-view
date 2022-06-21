package com.namviet.vtvtravel.view.fragment.f2mytrip.place

import android.os.Bundle
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentDetailSchedulePlacesBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.adapter.PlacesAdapter
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.model.DetailPlacesResponse
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.model.PlacesScheduleItem
import com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel.MyTripsViewModel
import java.util.*

class DetailPlacesFragment : BaseFragment<FragmentDetailSchedulePlacesBinding>(), Observer {

    companion object {
        const val KEY_DETAIL_PLACE_TRIP_ITEM = "trip_item_place"

        fun newInstance(tripItem: TripItem?): DetailPlacesFragment {
            val args = Bundle()
            args.putParcelable(KEY_DETAIL_PLACE_TRIP_ITEM,tripItem)
            val fragment = DetailPlacesFragment()
            fragment.arguments = args
            return fragment
        }
    }
    private var tripItem:TripItem? = null
    private var viewModel:MyTripsViewModel? =null
    private var listPlaces:MutableList<PlacesScheduleItem> = mutableListOf()
    private var adapter:PlacesAdapter? = null

    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail_schedule_places
    }

    override fun initView() {
        tripItem = arguments?.getParcelable(KEY_DETAIL_PLACE_TRIP_ITEM)
        viewModel = MyTripsViewModel()
        adapter = PlacesAdapter()
        adapter?.setListDetailPlaces(listPlaces)
        binding.rcvPlaces.adapter = adapter
        tripItem?.id?.let { viewModel?.getDetailPlaces(it) }
        viewModel?.addObserver(this)
    }

    override fun initData() {

    }

    override fun inject() {

    }

    override fun setClickListener() {

    }

    override fun setObserver() {

    }

    override fun update(o: Observable?, arg: Any?) {
        val response = arg as DetailPlacesResponse
        listPlaces = response.data?.content as MutableList<PlacesScheduleItem>
        adapter?.setListDetailPlaces(listPlaces)
    }
}