package com.namviet.vtvtravel.view.fragment.f2mytrip.place

import android.os.Bundle
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentDetailSchedulePlacesBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem

class DetailPlacesFragment : BaseFragment<FragmentDetailSchedulePlacesBinding>() {

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

    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail_schedule_places
    }

    override fun initView() {
        tripItem = arguments?.getParcelable(KEY_DETAIL_PLACE_TRIP_ITEM)
    }

    override fun initData() {

    }

    override fun inject() {

    }

    override fun setClickListener() {

    }

    override fun setObserver() {

    }
}