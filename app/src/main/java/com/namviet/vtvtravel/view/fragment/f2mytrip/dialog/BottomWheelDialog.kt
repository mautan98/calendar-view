package com.namviet.vtvtravel.view.fragment.f2mytrip.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.LayoutBottomWheelTimeBinding

class BottomWheelDialog : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(): BottomWheelDialog {
            val args = Bundle()

            val fragment = BottomWheelDialog()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: LayoutBottomWheelTimeBinding
    private var listHour:MutableList<String> = mutableListOf()
    private var listMinute:MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.layout_bottom_wheel_time, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
    }

    private fun initViews() {
        initData()
        binding.wheelHourTime.data = listHour
        binding.wheelMinuteTime.data = listMinute
    }

    private fun initData(){
      for (i in 0..23){
          val formatted = String.format("%02d", i)
          listHour.add("${formatted} Giờ")
      }
        for (i in 0..59){
            val formatted = String.format("%02d", i)
            listMinute.add("${formatted} Phút")
        }
    }

}