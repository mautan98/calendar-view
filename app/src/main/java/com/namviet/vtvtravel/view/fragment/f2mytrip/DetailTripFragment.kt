package com.namviet.vtvtravel.view.fragment.f2mytrip

import android.os.Bundle
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentDetailTripBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem

class DetailTripFragment: BaseFragment<FragmentDetailTripBinding>() {

    companion object {

        const val KEY_TRIP_ITEM = "trip_item_key"

        fun newInstance(tripItem: TripItem): DetailTripFragment{
            val args = Bundle()
            args.putParcelable(KEY_TRIP_ITEM,tripItem)
            val fragment = DetailTripFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val binding = getBinding()
    private var tripItem:TripItem?= null

    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail_trip
    }

    override fun initView() {

    }

    override fun initData() {
        tripItem = arguments?.getParcelable(KEY_TRIP_ITEM)

        binding.tvDetailTripName.text = tripItem?.name
    }

    override fun inject() {
    }

    override fun setClickListener() {
    }

    override fun setObserver() {
    }
}