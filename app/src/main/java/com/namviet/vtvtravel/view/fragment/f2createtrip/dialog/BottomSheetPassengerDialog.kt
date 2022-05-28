package com.namviet.vtvtravel.view.fragment.f2createtrip.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.LayoutChooseNumOfPassengerBinding
import com.namviet.vtvtravel.view.fragment.f2offline.BaseDialogBottom

class BottomSheetPassengerDialog(context: Context) : BaseDialogBottom() {

    private lateinit var binding:LayoutChooseNumOfPassengerBinding
    private var applyListener: ApplyPassengerListener? =null

    override fun getLayoutResource(): Int {
       return R.layout.layout_choose_num_of_passenger
    }

    override fun init(saveInstanceState: Bundle?, view: View?) {

    }

    override fun setUp(view: View?) {
        TODO("Not yet implemented")
    }

    interface ApplyPassengerListener{
        fun onApplyClick()
    }

}