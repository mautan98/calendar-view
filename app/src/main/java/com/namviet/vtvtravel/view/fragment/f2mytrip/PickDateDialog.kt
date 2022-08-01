package com.namviet.vtvtravel.view.fragment.f2mytrip

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.LayoutPickDateBinding
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem
import java.util.*

class PickDateDialog : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(year: Int, month: Int, dayOfMonth: Int): PickDateDialog {
            val fragment = PickDateDialog()
            fragment.year = year
            fragment.month = month
            fragment.dayOfMonth = dayOfMonth
            return fragment
        }
    }

    private lateinit var binding: LayoutPickDateBinding
    private var tripItem: TripItem? = null
    private lateinit var addDoneListener: PickDate
    private var minDate: Long = 0L
    private var year: Int = 0
    private var month: Int = 0
    private var dayOfMonth: Int = 0

    fun setList(tripItem: TripItem?) {
        this.tripItem = tripItem
    }

    fun setAddDoneListener(addDoneListener: PickDate) {
        this.addDoneListener = addDoneListener
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
            R.layout.layout_pick_date,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        binding.calendarView.minDate = minDate
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        binding.calendarView.date = calendar.timeInMillis
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            this.year = year
            this.month = month
            this.dayOfMonth = dayOfMonth
        }
        binding.btnAddCost.setOnClickListener {
            addDoneListener.onDateSelected(year, month, dayOfMonth)
            dismissAllowingStateLoss()
        }
    }

    public fun setMinDate(minDate: Long) {
        this.minDate = minDate
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: BottomSheetDialog =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val d = it as BottomSheetDialog
            val bottomSheet: FrameLayout =
                d.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        return dialog
    }

    interface PickDate {
        fun onDateSelected(year: Int, month: Int, dayOfMonth: Int)
    }
}