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
    private var numAdult: Int = 0
    private var numChildren: Int = 0
    private var numBaby: Int = 0

    fun setApplyListener(applyListener: ApplyPassengerListener){
        this.applyListener = applyListener
    }

    fun setInfor(numAdult: Int,numChildren: Int,numBaby: Int){
        this.numAdult = numAdult
        this.numChildren = numChildren
        this.numBaby = numBaby
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
        binding.tvNumberPassenger.text = numAdult.toString()
        binding.tvNumberChildren.text = numChildren.toString()
        binding.tvNumberBaby.text = numBaby.toString()
    }

    private fun initClickListener(){
        binding.btnApply.setOnClickListener{
            numAdult = binding.tvNumberPassenger.text.toString().toInt()
            numChildren = binding.tvNumberChildren.text.toString().toInt()
            numBaby = binding.tvNumberBaby.text.toString().toInt()
            dismiss()
            applyListener?.onApplyClick(numAdult,numChildren,numBaby)
        }
        binding.imvPlus.setOnClickListener({
            if (numAdult < 6){
                numAdult+= 1
                binding.tvNumberPassenger.text = numAdult.toString()
            }else{
                binding.imvPlus.isEnabled = false
            }
        })
    }

    interface ApplyPassengerListener{
        fun onApplyClick(numAdult:Int,numChildren:Int,numBaby:Int)
    }

}