package com.namviet.vtvtravel.view.fragment.f2mytrip.model.cost

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
import com.namviet.vtvtravel.databinding.LayoutAddNewCostBinding
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem
import java.util.*

class AddNewCostBottomDialog : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(): AddNewCostBottomDialog {
            val fragment = AddNewCostBottomDialog()
            return fragment
        }
    }

    private lateinit var binding: LayoutAddNewCostBinding
    private var tripItem: TripItem? = null
    private lateinit var addDoneListener: AddNewCostDoneListener

    fun setList(tripItem: TripItem?) {
        this.tripItem = tripItem
    }

    fun setAddDoneListener(addDoneListener: AddNewCostDoneListener) {
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
            R.layout.layout_add_new_cost,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {

        binding.btnAddCost.setOnClickListener {
            val typeCost = TypeCost()
            typeCost.costName = binding.tvCostName.text.toString().trim()
            addDoneListener.onAddDone(typeCost)
            dismiss()
        }
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

    interface AddNewCostDoneListener {
        fun onAddDone(typeCost: TypeCost)
    }
}