package com.namviet.vtvtravel.view.fragment.f2createtrip.dialog

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.LayoutChooseNumOfPassengerBinding
import com.namviet.vtvtravel.view.fragment.f2offline.BaseDialogBottom
import java.util.*

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (Objects.requireNonNull(view)!!.parent as View).setBackgroundColor(Color.TRANSPARENT)
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

        binding.imvMinusChildren.isEnabled = false
        binding.imvMinusChildren.setImageResource(R.drawable.ic_minus_passenger_disable_min)
        binding.imvMinusBaby.isEnabled = false
        binding.imvMinusBaby.setImageResource(R.drawable.ic_minus_passenger_disable_min)
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
            numAdult = setPlusPassenger(numAdult,binding.tvNumberPassenger,binding.imvPlus,binding.imvMinus)
        })
        binding.imvMinus.setOnClickListener({
            numAdult = setMinusPassenger(numAdult,binding.tvNumberPassenger,binding.imvMinus,binding.imvPlus)
        })

        binding.imvPlusChildren.setOnClickListener({
            numChildren = setPlusPassenger(numChildren,binding.tvNumberChildren,binding.imvPlusChildren,binding.imvMinusChildren)
        })
        binding.imvMinusChildren.setOnClickListener({
            numChildren = setMinusPassenger(numChildren,binding.tvNumberChildren,binding.imvMinusChildren,binding.imvPlusChildren)
        })

        binding.imvPlusBaby.setOnClickListener({
            numBaby = setPlusPassenger(numBaby,binding.tvNumberBaby,binding.imvPlusBaby,binding.imvMinusBaby)
        })
        binding.imvMinusBaby.setOnClickListener({
            numBaby = setMinusPassenger(numBaby,binding.tvNumberBaby,binding.imvMinusBaby,binding.imvPlusBaby)
        })
    }

    private fun setPlusPassenger(
        numberPassenger: Int,
        tvPassenger: TextView,
        imvPlus: ImageView,
        imvMinus: ImageView
    ): Int {
        var number = numberPassenger
        if (!imvMinus.isEnabled) {
            imvMinus.isEnabled = true
            imvMinus.setImageResource(R.drawable.ic_minus_passenger)
        }
        if (numberPassenger < 6) {
            number += 1
            tvPassenger.text = number.toString()
            if (number == 6) {
                imvPlus.isEnabled = false
                setEnableButton(
                    imvPlus,
                    false,
                    R.drawable.ic_plus_passenger,
                    R.drawable.ic_plus_max_disable
                )
            } else {
                setEnableButton(
                    imvPlus,
                    true,
                    R.drawable.ic_plus_passenger,
                    R.drawable.ic_plus_max_disable
                )
            }
        }
        return number
    }

    private fun setMinusPassenger(
        numberPassenger: Int,
        tvPassenger: TextView,
        imvMinus: ImageView,
        imvPlus: ImageView
    ): Int {
        var number = numberPassenger
        if (!imvPlus.isEnabled) {
            imvPlus.isEnabled = true
            imvPlus.setImageResource(R.drawable.ic_plus_passenger)
        }
        if (numberPassenger > 0) {
            number -= 1
            tvPassenger.text = number.toString()
            imvMinus.isEnabled = true
            if (number == 0) {
                imvMinus.isEnabled = false
                setEnableButton(
                    imvMinus,
                    false,
                    R.drawable.ic_minus_passenger,
                    R.drawable.ic_minus_passenger_disable_min
                )
            } else {
                imvMinus.isEnabled = true
                setEnableButton(
                    imvMinus,
                    true,
                    R.drawable.ic_minus_passenger,
                    R.drawable.ic_minus_passenger_disable_min
                )
            }
        }
        return number
    }

    private fun setEnableButton(
        imageView: ImageView,
        isEnable: Boolean,
        resEnable: Int,
        resDisable: Int
    ) {
        if (isEnable) {
            imageView.isEnabled = true
            imageView.setImageResource(resEnable)
        } else {
            imageView.isEnabled = false
            imageView.setImageResource(resDisable)
        }
    }

    interface ApplyPassengerListener{
        fun onApplyClick(numAdult:Int,numChildren:Int,numBaby:Int)
    }

}