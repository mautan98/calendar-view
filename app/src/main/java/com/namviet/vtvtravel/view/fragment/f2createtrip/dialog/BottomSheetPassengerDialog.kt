package com.namviet.vtvtravel.view.fragment.f2createtrip.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.LayoutChooseNumOfPassengerBinding
import com.namviet.vtvtravel.view.fragment.f2offline.BaseDialogBottom

class BottomSheetPassengerDialog(context: Context) : BottomSheetDialogFragment() {

    private lateinit var binding:LayoutChooseNumOfPassengerBinding
    private var applyListener: ApplyPassengerListener? =null

    fun setApplyListener(applyListener: ApplyPassengerListener){
        this.applyListener = applyListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.layout_choose_num_of_passenger,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClickListener()
    }

    private fun initView(){

    }

    private fun initClickListener(){
        val numAdult = binding.tvNumberPassenger.text.toString().toInt()
        val numChildren = binding.tvNumberChildren.text.toString().toInt()
        val numBaby = binding.tvNumberBaby.text.toString().toInt()
        binding.btnApply.setOnClickListener{
            dismiss()
            applyListener?.onApplyClick(numAdult,numChildren,numBaby)
        }
    }

    interface ApplyPassengerListener{
        fun onApplyClick(numAdult:Int,numChildren:Int,numBaby:Int)
    }

}