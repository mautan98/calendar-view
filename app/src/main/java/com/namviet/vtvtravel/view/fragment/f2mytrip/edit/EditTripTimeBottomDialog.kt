package com.namviet.vtvtravel.view.fragment.f2mytrip.edit

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
import com.namviet.vtvtravel.databinding.LayoutEditTimeTripBinding
import com.namviet.vtvtravel.listener.OnItemRecyclerClickListener
import com.namviet.vtvtravel.response.BaseResponse
import com.namviet.vtvtravel.view.fragment.f2mytrip.EditDayTripBottomDialog
import com.namviet.vtvtravel.view.fragment.f2mytrip.adapter.TripsTimeAdapter
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.SchedulePlaceByDaysItem
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem
import com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel.DetailPlaceScheduleViewModel
import com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel.MyTripsViewModel
import com.namviet.vtvtravel.widget.ConfirmDeleteDialog
import java.util.*

class EditTripTimeBottomDialog : BottomSheetDialogFragment(), Observer,
    OnItemRecyclerClickListener {

    companion object {
        const val KEY_LIST_SCHEDULE_DAY = "list_schedule_day"
        fun newInstance(listDaysItem: TripItem): EditTripTimeBottomDialog {
            val args = Bundle()
            args.putParcelable(KEY_LIST_SCHEDULE_DAY, listDaysItem)
            val fragment = EditTripTimeBottomDialog()
            fragment.arguments = args
            return fragment
        }
    }

    private var listClone: MutableList<SchedulePlaceByDaysItem>? = null
    private lateinit var binding: LayoutEditTimeTripBinding
    private var listDay: MutableList<SchedulePlaceByDaysItem> = mutableListOf()
    private var tripItem: TripItem? = null
    private val viewModel = MyTripsViewModel()
    private val detailPlaceViewModel = DetailPlaceScheduleViewModel()
    private var onBackFragmentListener: OnBackFragmentListener? = null
    private var adapter: TripsTimeAdapter? = null

    fun setOnBackFragmentListener(onBackFragmentListener: OnBackFragmentListener?) {
        this.onBackFragmentListener = onBackFragmentListener
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (Objects.requireNonNull(view)!!.parent as View).setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_edit_time_trip,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initClickListener()
    }

    private fun initView() {
        tripItem = arguments?.getParcelable(KEY_LIST_SCHEDULE_DAY)
        listDay.addAll(tripItem?.schedulePlaceByDays as MutableList<SchedulePlaceByDaysItem>)
        listClone = mutableListOf()
        listClone?.addAll(listDay)
        adapter = TripsTimeAdapter()
        adapter?.setOnItemClickListener(this)
        adapter?.setListScheduleByDays(listDay)
        binding.rcvPlaceByDay.adapter = adapter
        viewModel.addObserver(this)
        detailPlaceViewModel.addObserver(this)
    }

    private fun initClickListener() {
        binding.tvSave.setOnClickListener {
            listDay = adapter?.getListSchedulesDay()!!
            val startAt = Utils.formatTimeStamp(listDay.get(0).day,"yyyy-MM-dd")
            val endAt = Utils.formatTimeStamp(listDay.get(listDay.size - 1).day,"yyyy-MM-dd")
            viewModel.updateRangeScheduleTime(tripItem?.id!!, startAt, endAt)
        }
        binding.tvCancel.setOnClickListener {
            dismiss()
        }
        binding.tvAddNewDate.setOnClickListener {
            var listDisableDay:MutableList<Calendar> = mutableListOf()
            if (tripItem?.schedulePlaceByDays != null){
                for (i in 0..tripItem?.schedulePlaceByDays?.size!!.minus(1)){
                    val dayItem = tripItem?.schedulePlaceByDays?.get(i)
                    if (dayItem != null){
                        val day = Calendar.getInstance()
                        day.timeInMillis = dayItem.day!!
                        listDisableDay.add(day)
                    }
                }
                val editDayDialog = EditDayTripBottomDialog()
                editDayDialog.setDisableDayList(listDisableDay)
            }

        }
    }

    override fun update(o: Observable?, arg: Any?) {
        if (arg is BaseResponse) {
            val response = arg
            if (response.isSuccess) {
                onBackFragmentListener?.onBackFragment(response.apiCode)
                dismiss()
            }
        }
    }

    interface OnBackFragmentListener {
        fun onBackFragment(apiCode: String)
    }

    override fun onItemClick(position: Int) {
        val confirmDialog = ConfirmDeleteDialog()
        confirmDialog.setDescription(requireContext().getString(R.string.confirm_delete_schedule))
        confirmDialog.setLabelButton(getString(R.string.agree))
        confirmDialog.setConfirmClickListener(object :ConfirmDeleteDialog.OnConfirmListener{
            override fun onClickConfirm() {
                val schedulePlaceId = listClone?.get(position)?.schedulePlaceId
                val day = Utils.formatTimeStamp(listClone?.get(position)?.day,"yyyy-MM-dd")
                detailPlaceViewModel.deltePlaceByDay(schedulePlaceId,tripItem?.id,day)
            }

        })
        confirmDialog.show(childFragmentManager,null)
    }
}