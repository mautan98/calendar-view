package com.namviet.vtvtravel.view.fragment.f2mytrip.place

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
import com.namviet.vtvtravel.databinding.FragmentDetailSchedulePlacesBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.listener.OnBackToFragmentListener
import com.namviet.vtvtravel.response.BaseResponse
import com.namviet.vtvtravel.ultils.DialogUtil
import com.namviet.vtvtravel.ultils.DialogUtil.Companion.showErrorDialog
import com.namviet.vtvtravel.ultils.toGone
import com.namviet.vtvtravel.ultils.toVisible
import com.namviet.vtvtravel.view.fragment.f2mytrip.AddPlaceToTripFragment
import com.namviet.vtvtravel.view.fragment.f2mytrip.RoadActivity
import com.namviet.vtvtravel.view.fragment.f2mytrip.TripRoadFragment
import com.namviet.vtvtravel.view.fragment.f2mytrip.dialog.BottomNoteDialog
import com.namviet.vtvtravel.view.fragment.f2mytrip.dialog.BottomWheelDialog
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.adapter.AllPlacesAdapter
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.model.DetailPlacesResponse
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.model.ItemPlaces
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.model.NotifyUpdateData
import com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel.DetailPlaceScheduleViewModel
import com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel.MyTripsViewModel
import com.namviet.vtvtravel.view.fragment.f2travelvoucher.AlreadyReceiverDialog
import com.namviet.vtvtravel.widget.ConfirmDeleteDialog
import org.greenrobot.eventbus.EventBus
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
    private var backToFragmentListener:OnBackToFragmentListener? = null

    fun setOnBackToFragmentListener(listener: OnBackToFragmentListener){
        backToFragmentListener = listener
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail_schedule_places
    }

    override fun initView() {
        binding!!.viewLoading.toVisible()
        tripItem = arguments?.getParcelable(KEY_DETAIL_PLACE_TRIP_ITEM)
        viewModel = MyTripsViewModel()
        detailPlaceViewModel = DetailPlaceScheduleViewModel()
        adapter = AllPlacesAdapter()
        adapter?.setIsEditAble(tripItem?.checkRights.equals("true"))
        adapter?.setOnItemClickListener(object :AllPlacesAdapter.OnItemClickPlace{
            override fun onItemClick(currentPosition:Int, itemDetailPosition: Int, view: View,isEditable: Boolean) {
                when(view.id){
                    R.id.layout_time_travel_place -> {
                        if (!isEditable){
                            showErrorEditable(childFragmentManager)
                            return
                        }
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
                        if (!isEditable){
                            showErrorEditable(childFragmentManager)
                            return
                        }
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
                        if (!isEditable){
                            showErrorEditable(childFragmentManager)
                            return
                        }
                        val dialog = BottomWheelDialog.newInstance(true)
                        dialog.setOnSaveListener(object : BottomWheelDialog.OnClickSaveWheelTime{
                            override fun onClickSave(hour: Int, minute: Int) {
                                val placeItem = listPlaces.get(currentPosition).schedulePlaceList?.get(itemDetailPosition)
                                placeItem?.freeTime = minute
                                placeItem?.id?.let { detailPlaceViewModel?.updateDurationVisit(it,minute.toString()) }
                            }
                        })
                        dialog.show(childFragmentManager,null)
                    }
                    R.id.imv_close -> {
                        if (!isEditable){
                            showErrorEditable(childFragmentManager)
                            return
                        }
                        val dialog = ConfirmDeleteDialog()
                        dialog.setDescription("B???n c?? ch???c ch???n mu???n xo?? ?????a ??i???m n??y?")
                        dialog.setConfirmClickListener(object :ConfirmDeleteDialog.OnConfirmListener{
                            override fun onClickConfirm() {
                                val placeItemList = listPlaces.get(currentPosition).schedulePlaceList
                                val placeItem = listPlaces.get(currentPosition).schedulePlaceList?.get(itemDetailPosition)
                                placeItem?.id?.let { detailPlaceViewModel?.deltePlace(it) }
                            }
                        })
                        dialog.show(childFragmentManager,null)
                    }
                    R.id.change_place -> {
                        if (!isEditable){
                            showErrorEditable(childFragmentManager)
                            return
                        }
                        var addPlaceToTripFragment  = AddPlaceToTripFragment();
                        addPlaceToTripFragment.setOnBackToFragmentListener(object :OnBackToFragmentListener{
                            override fun onBack() {
                                tripItem?.id?.let { it1 -> viewModel?.getDetailPlaces(it1) }
                            }
                        })
                        addPlaceToTripFragment.setData(tripItem?.id)
                        addPlaceToTripFragment.setPlaceScheduleItem(listPlaces[currentPosition])
                        addPlaceToTripFragment.setDetailPlaceScheduleItem(listPlaces[currentPosition].schedulePlaceList?.get(itemDetailPosition))
                        addPlaceToTripFragment.setIsUpdatePlace(true)
                        addPlaceToTripFragment.setDestinationRegionId(tripItem?.destinationRegionId)
                        addFragment(addPlaceToTripFragment)
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
                dialog.setDescription("B???n ch??a ch???n ng??y ????? th??m ?????a ??i???m")
                dialog.show(childFragmentManager,null)
                return@setOnClickListener
            }
        }
        binding.imvBack.setOnClickListener {
            backToFragmentListener?.onBack()
            activity?.onBackPressed()
        }

        binding.btnAddPlace.setOnClickListener {
            if (!tripItem?.checkRights.equals("true")){
                showErrorEditable(childFragmentManager)
                return@setOnClickListener
            }
            if (adapter?.getItemPlaceSelected() == null) {
                DialogUtil.showErrorDialog(
                    requireContext().getString(R.string.error_title),
                    requireContext().getString(R.string.close_title),
                    "B???n ch??a ch???n ng??y ????? th??m ?????a ??i???m",
                    childFragmentManager
                )
                return@setOnClickListener
            }
            var addPlaceToTripFragment  = AddPlaceToTripFragment();
            addPlaceToTripFragment.setOnBackToFragmentListener(object :OnBackToFragmentListener{
                override fun onBack() {
                    tripItem?.id?.let { it1 -> viewModel?.getDetailPlaces(it1) }
                }
            })
            addPlaceToTripFragment.setData(tripItem?.id)
            addPlaceToTripFragment.setPlaceScheduleItem(adapter?.getItemPlaceSelected())
            addPlaceToTripFragment.setDestinationRegionId(tripItem?.destinationRegionId)
            addFragment(addPlaceToTripFragment)
        }

        binding!!.btnOpenRoad.setOnClickListener {
            addFragment(TripRoadFragment(listPlaces))
        }
    }

    override fun setObserver() {

    }

    override fun update(o: Observable?, arg: Any?) {
        binding!!.viewLoading.toGone()
        if (arg is DetailPlacesResponse){
            val response = arg
            listPlaces = response.data?.content as MutableList<ItemPlaces>
            adapter?.setListDetailPlaces(listPlaces)
            EventBus.getDefault().post(NotifyUpdateData())
        } else if (arg is BaseResponse) {
            if (arg.isSuccess){
                tripItem?.id?.let { viewModel?.getDetailPlaces(it) }
            }
        } else if (arg is ErrorResponse){
            val responseError = arg
            var des = "???? c?? l???i kh??ng x??c ?????nh"
            if (!TextUtils.isEmpty(responseError.getErrorCode())) {
                des = responseError.getErrorCode()
            }
            showErrorDialog(
                getString(R.string.error_title), getString(R.string.close_title),
                des, childFragmentManager
            )
        }
    }
}