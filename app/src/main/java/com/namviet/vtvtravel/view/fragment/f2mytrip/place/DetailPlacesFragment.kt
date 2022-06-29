package com.namviet.vtvtravel.view.fragment.f2mytrip.place

import android.os.Bundle
import android.view.View
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
import com.namviet.vtvtravel.databinding.FragmentDetailSchedulePlacesBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.response.BaseResponse
import com.namviet.vtvtravel.ultils.DialogUtil
import com.namviet.vtvtravel.view.fragment.f2mytrip.AddPlaceToTripFragment
import com.namviet.vtvtravel.view.fragment.f2mytrip.dialog.BottomNoteDialog
import com.namviet.vtvtravel.view.fragment.f2mytrip.dialog.BottomWheelDialog
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.adapter.AllPlacesAdapter
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.model.DetailPlacesResponse
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.model.ItemPlaces
import com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel.DetailPlaceScheduleViewModel
import com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel.MyTripsViewModel
import com.namviet.vtvtravel.view.fragment.f2travelvoucher.AlreadyReceiverDialog
import com.namviet.vtvtravel.widget.ConfirmDeleteDialog
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
    private var detailPlaceViewModel:DetailPlaceScheduleViewModel? =null
    private var listPlaces:MutableList<ItemPlaces> = mutableListOf()
    private var adapter:AllPlacesAdapter? = null

    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail_schedule_places
    }

    override fun initView() {
        tripItem = arguments?.getParcelable(KEY_DETAIL_PLACE_TRIP_ITEM)
        viewModel = MyTripsViewModel()
        detailPlaceViewModel = DetailPlaceScheduleViewModel()
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
                                val arrivalTime = Utils.formatTimeStamp(calendar.timeInMillis,"dd-MM-yyyy HH:mm:ss")
                                val placeItem = listPlaces.get(currentPosition).schedulePlaceList?.get(itemDetailPosition)
                                placeItem?.arrivalTime = calendar.timeInMillis
                                placeItem?.id?.let { detailPlaceViewModel?.updateArrivalTime(it,arrivalTime) }
                            }
                        })
                        dialog.show(childFragmentManager,null)
                    }
                    R.id.note_place -> {
                        val item = listPlaces.get(currentPosition).schedulePlaceList?.get(
                            itemDetailPosition
                        )
                        val dialog = BottomNoteDialog.newInstance(item)
                        dialog.setOnSaveListener(object : BottomNoteDialog.OnClickSaveNote{
                            override fun onClickSave(note: String) {
                                val placeItem = listPlaces.get(currentPosition).schedulePlaceList?.get(itemDetailPosition)
                                placeItem?.note = note
                                placeItem?.id?.let { viewModel?.updateNoteSchedule(it,note) }
                            }
                        })
                        dialog.show(childFragmentManager, null)
                    }
                    R.id.edt_time_visiting -> {
                        val dialog = BottomWheelDialog.newInstance(true)
                        dialog.setOnSaveListener(object : BottomWheelDialog.OnClickSaveWheelTime{
                            override fun onClickSave(hour: Int, minute: Int) {
                                val placeItem = listPlaces.get(currentPosition).schedulePlaceList?.get(itemDetailPosition)
                                placeItem?.freeTime = minute
                                placeItem?.id?.let { detailPlaceViewModel?.updateFreeTime(it,minute.toString()) }
                            }
                        })
                        dialog.show(childFragmentManager,null)
                    }
                    R.id.imv_close -> {
                        val dialog = ConfirmDeleteDialog()
                        dialog.setDescription("Bạn có chắc chắn muốn xoá địa điểm này?")
                        dialog.setConfirmClickListener(object :ConfirmDeleteDialog.OnConfirmListener{
                            override fun onClickConfirm() {
                                val placeItemList = listPlaces.get(currentPosition).schedulePlaceList
                                val placeItem = listPlaces.get(currentPosition).schedulePlaceList?.get(itemDetailPosition)
                                placeItem?.id?.let { detailPlaceViewModel?.deltePlace(it) }
                            }
                        })
                        dialog.show(childFragmentManager,null)
                    }
                }
            }
        })
        adapter?.setListDetailPlaces(listPlaces)
        binding.rcvPlaces.adapter = adapter
        tripItem?.id?.let { viewModel?.getDetailPlaces(it) }
        viewModel?.addObserver(this)
        detailPlaceViewModel?.addObserver(this)
    }

    override fun initData() {

    }

    override fun inject() {

    }

    override fun setClickListener() {
        binding.layoutAddPlace.setOnClickListener {
            val dialog = AlreadyReceiverDialog()
            dialog.setDialogTitle(getString(R.string.error_title))
            dialog.setLabelButton(getString(R.string.close_title))
            if (adapter?.getItemPlaceSelected() == null){
                dialog.setDescription("Bạn chưa chọn ngày để thêm địa điểm")
                dialog.show(childFragmentManager,null)
                return@setOnClickListener
            }
        }
        binding.imvBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnAddPlace.setOnClickListener {
            if (adapter?.getItemPlaceSelected() == null) {
                DialogUtil.showErrorDialog(
                    requireContext().getString(R.string.error_title),
                    requireContext().getString(R.string.close_title),
                    "Bạn chưa chọn ngày để thêm địa điểm",
                    childFragmentManager
                )
                return@setOnClickListener
            }
            var addPlaceToTripFragment  = AddPlaceToTripFragment();
            addPlaceToTripFragment.setData(tripItem?.id)
            addFragment(addPlaceToTripFragment)
        }
    }

    override fun setObserver() {

    }

    override fun update(o: Observable?, arg: Any?) {
        if (arg is DetailPlacesResponse){
            val response = arg
            listPlaces = response.data?.content as MutableList<ItemPlaces>
            adapter?.setListDetailPlaces(listPlaces)
        } else if (arg is BaseResponse) {
            if (arg.isSuccess){
                tripItem?.id?.let { viewModel?.getDetailPlaces(it) }
            }
        }
    }
}