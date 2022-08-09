package com.namviet.vtvtravel.view.fragment.f2mytrip

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.LayoutCalendarViewBinding
import com.namviet.vtvtravel.ultils.DialogUtil
import java.util.*

class EditDayTripBottomDialog : BottomSheetDialogFragment() {

    private var listDisableDayList: MutableList<Calendar> = mutableListOf()
    private lateinit var binding: LayoutCalendarViewBinding
    private var onSelectDateListener: OnSelectedDateListener? = null
    private var minDate: Calendar? = null
    private var maxDate: Calendar? = null
    private var showDate: Calendar? = null
    private lateinit var lastDate: Date

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (Objects.requireNonNull(view)!!.parent as View).setBackgroundColor(Color.TRANSPARENT)
    }

    fun setOnSelectDateListener(listener: OnSelectedDateListener) {
        onSelectDateListener = listener
    }

    fun setMinDate(date: Calendar) {
        minDate = date
    }

    fun setShowDate(showDate: Calendar) {
        this.showDate = showDate
    }

    fun setMaxDate(date: Calendar) {
        maxDate = date
    }

    fun setLastDate(lastDate: Date) {
        this.lastDate = lastDate
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_calendar_view, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.calendarView.setDisabledDays(listDisableDayList)
        binding.calendarView.setLastDate(lastDate)
        minDate?.let { binding.calendarView.setMinimumDate(it) }
        maxDate?.let { binding.calendarView.setMaximumDate(it) }
        clickListener()
    }

    private fun clickListener() {
        binding.tvAddDate.setOnClickListener {
            onSelectDateListener?.onDateSelected(binding.calendarView.selectedDates)
            dismiss()
        }
        binding.tvResetDate.setOnClickListener {
            binding.calendarView.clearSelectedDays()
        }

        binding.calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {

            }

            override fun onOutDate() {
                DialogUtil.showErrorDialog(
                    getString(R.string.error_title),
                    getString(R.string.close_title),
                    "Bạn chỉ được chọn ngày tiếp theo của lịch trình",
                    childFragmentManager
                )
            }
        })
    }

    fun setDisableDayList(listDisableDay: MutableList<Calendar>) {
        this.listDisableDayList = listDisableDay
    }

    interface OnSelectedDateListener {
        fun onDateSelected(listDates: List<Calendar>)
    }

}