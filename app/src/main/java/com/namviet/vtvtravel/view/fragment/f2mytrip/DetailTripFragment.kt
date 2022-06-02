package com.namviet.vtvtravel.view.fragment.f2mytrip

import android.os.Build
import android.os.Bundle
import android.text.Html
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
import com.namviet.vtvtravel.databinding.FragmentDetailTripBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.fragment.f2mytrip.adapter.OverlapDecoration
import com.namviet.vtvtravel.view.fragment.f2mytrip.adapter.PlacesInScheduleAdapter
import com.namviet.vtvtravel.view.fragment.f2mytrip.adapter.UserListAdapter
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.UserListItem
import com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel.MyTripsViewModel
import java.util.*

class DetailTripFragment: BaseFragment<FragmentDetailTripBinding>(), Observer {

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

    private lateinit var binding:FragmentDetailTripBinding
    private var tripItem:TripItem?= null
    private var viewModel = MyTripsViewModel()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail_trip
    }

    override fun initView() {
        binding = getBinding()
    }

    override fun initData() {
        tripItem = arguments?.getParcelable(KEY_TRIP_ITEM)
        tripItem?.id?.let { viewModel.getDetailplaceById(it) }
        viewModel.addObserver(this)

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
        val userAdapter = UserListAdapter(requireContext())
        val listAvt = tripItem?.userList as MutableList<UserListItem>
        userAdapter.setListAvt(listAvt)
        binding.rcvImageUser.addItemDecoration(OverlapDecoration(listAvt))
        binding.rcvImageUser.adapter = userAdapter

        val adapter = PlacesInScheduleAdapter(requireContext())
        binding.rcvAllSchedule.adapter = adapter
    }

    override fun inject() {
    }

    override fun setClickListener() {
    }

    override fun setObserver() {
    }

    override fun update(o: Observable?, arg: Any?) {

    }
}