package com.namviet.vtvtravel.view.fragment.f2mytrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.LayoutCalendarViewBinding
import java.util.*

class EditDayTripBottomDialog : BottomSheetDialogFragment() {

    private var listDisableDayList: MutableList<Calendar> = mutableListOf()
    private lateinit var binding: LayoutCalendarViewBinding

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
    }

    fun setDisableDayList(listDisableDay: MutableList<Calendar>) {
        this.listDisableDayList = listDisableDay
    }

}