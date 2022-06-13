package com.namviet.vtvtravel.view.fragment.f2mytrip.edit

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.LayoutEditTimeTripBinding
import com.namviet.vtvtravel.response.BaseResponse
import com.namviet.vtvtravel.view.fragment.f2mytrip.adapter.TripsTimeAdapter
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.SchedulePlaceByDaysItem
import com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel.MyTripsViewModel
import java.util.*
import kotlin.collections.ArrayList

class EditTripTimeBottomDialog : BottomSheetDialogFragment(), Observer {

    companion object {
        const val KEY_LIST_SCHEDULE_DAY = "list_schedule_day"
        fun newInstance(listDaysItem: ArrayList<SchedulePlaceByDaysItem>): EditTripTimeBottomDialog {
            val args = Bundle()
            args.putParcelableArrayList(KEY_LIST_SCHEDULE_DAY, listDaysItem)
            val fragment = EditTripTimeBottomDialog()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: LayoutEditTimeTripBinding
    private var listDay: MutableList<SchedulePlaceByDaysItem> = mutableListOf()
    private val viewModel = MyTripsViewModel()
    private var onBackFragmentListener: OnBackFragmentListener? = null

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
        listDay = arguments?.get(KEY_LIST_SCHEDULE_DAY) as MutableList<SchedulePlaceByDaysItem>
        val adapter = TripsTimeAdapter()
        adapter.setListScheduleByDays(listDay)
        binding.rcvPlaceByDay.adapter = adapter
        viewModel.addObserver(this)
    }

    private fun initClickListener() {
        binding.tvSave.setOnClickListener {

        }
        binding.tvCancel.setOnClickListener {
            dismiss()
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
}