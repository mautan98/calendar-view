package com.namviet.vtvtravel.view.fragment.f2mytrip.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.LayoutBottomDialogEditTripBinding

class EditTripBottomDialog : BottomSheetDialogFragment() {

    private lateinit var binding: LayoutBottomDialogEditTripBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_bottom_dialog_edit_trip,
            container,
            false
        )
        return binding.root
    }

}