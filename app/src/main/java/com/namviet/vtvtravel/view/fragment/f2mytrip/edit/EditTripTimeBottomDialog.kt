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
            var lastDate:Long?
            val calendar = Calendar.getInstance()
            if (listDay.size > 0){
                lastDate = listDay.get(listDay.size-1).day
            } else {
                lastDate = calendar.timeInMillis
            }
            if (lastDate != null) {
                calendar.timeInMillis = lastDate
            }
            val selectedYear = calendar[Calendar.YEAR]
            val selectedMonth = calendar[Calendar.MONTH]
            val selectedDayOfMonth = calendar[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        calendar[Calendar.YEAR] = year
                        calendar[Calendar.MONTH] = month
                        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth

                        val item = SchedulePlaceByDaysItem()
                        item.totalDistance = 0
                        item.totalPlace = 0
                        item.day = calendar.timeInMillis
                        item.removeAble = false
                        val listClone = listDay
                        listClone.add(item)
                        adapter?.setListScheduleByDays(listClone)
                    }
                }, selectedYear, selectedMonth, selectedDayOfMonth
            )
            if (lastDate != null) {
                datePickerDialog.datePicker.minDate = lastDate
            }
            datePickerDialog.show()
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
//                detailPlaceViewModel.deltePlaceByDay()
                listClone?.removeAt(position)
                listClone?.let { adapter?.setListScheduleByDays(it) }
            }

        })
        confirmDialog.show(childFragmentManager,null)
    }
}