package com.namviet.vtvtravel.view.fragment.f2mytrip.place

import android.os.Bundle
import android.view.View
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentDetailSchedulePlacesBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.fragment.f2mytrip.dialog.BottomNoteDialog
import com.namviet.vtvtravel.view.fragment.f2mytrip.dialog.BottomWheelDialog
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.adapter.AllPlacesAdapter
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.adapter.PlacesAdapter
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.model.DetailPlacesResponse
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.model.ItemPlaces
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
    private var listPlaces:MutableList<ItemPlaces> = mutableListOf()
    private var adapter:AllPlacesAdapter? = null

    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail_schedule_places
    }

    override fun initView() {
        tripItem = arguments?.getParcelable(KEY_DETAIL_PLACE_TRIP_ITEM)
        viewModel = MyTripsViewModel()
        adapter = AllPlacesAdapter()
        adapter?.setOnItemClickListener(object :AllPlacesAdapter.OnItemClickPlace{
            override fun onItemClick(currentPosition:Int, itemDetailPosition: Int, view: View) {
                when(view.id){
                    R.id.layout_time_travel_place -> {
                        val dialog = BottomWheelDialog()
                        dialog.setOnSaveListener(object : BottomWheelDialog.OnClickSaveWheelTime{
                            override fun onClickSave(hour: Int, minute: Int) {
                                val calendar = Calendar.getInstance()
                                calendar.timeInMillis = listPlaces[currentPosition].day!!
                                calendar.set(Calendar.HOUR_OF_DAY,hour)
                                calendar.set(Calendar.MINUTE,minute)
                                listPlaces.get(currentPosition).schedulePlaceList?.get(itemDetailPosition)?.arrivalTime = calendar.timeInMillis
                                adapter?.notifyDataSetChanged()
                            }
                        })
                        dialog.show(childFragmentManager,null)
                    }
                    R.id.note_place -> {
                        val item = listPlaces.get(currentPosition).schedulePlaceList?.get(
                            itemDetailPosition
                        )
                        val dialog = BottomNoteDialog.newInstance(item)
                        dialog.show(childFragmentManager, null)
                    }
                }
            }
        })
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
        binding.layoutAddPlace.setOnClickListener {
            adapter?.getItemPlaceSelected()
        }
    }

    override fun setObserver() {

    }

    override fun update(o: Observable?, arg: Any?) {
        val response = arg as DetailPlacesResponse
        listPlaces = response.data?.content as MutableList<ItemPlaces>
        adapter?.setListDetailPlaces(listPlaces)
    }
}