package com.namviet.vtvtravel.view.fragment.f2mytrip

import android.os.Build
import android.os.Bundle
import android.text.Html
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
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
        binding.tvDetailTripDesc.text = tripItem?.description
        val startDate = Utils.formatTimestampTrips(tripItem?.startAt)
        val endDate = Utils.formatTimestampTrips(tripItem?.endAt)
        binding.tvDetailTimeTrips.text = "($startDate - $endDate, ${tripItem?.numberPeople})"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.tvEstimateCost.text = Html.fromHtml(Utils.convertPriceTrips(tripItem?.estimatedCost),
                Html.FROM_HTML_MODE_COMPACT)
        } else {
            binding.tvEstimateCost.text = Html.fromHtml(Utils.convertPriceTrips(tripItem?.estimatedCost))
        }
    }

    override fun inject() {
    }

    override fun setClickListener() {
    }

    override fun setObserver() {
    }
}