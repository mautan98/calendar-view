package com.namviet.vtvtravel.view.fragment.f2mytrip.dialog

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.LayoutEditNoteSchedulesBinding
import java.util.*

class BottomNoteDialog : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(): BottomNoteDialog {
            val args = Bundle()
            val fragment = BottomNoteDialog()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: LayoutEditNoteSchedulesBinding
    private var listener: OnClickSaveNote? = null

    fun setOnSaveListener(listener: OnClickSaveNote) {
        this.listener = listener
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
        binding =
            DataBindingUtil.inflate(inflater, R.layout.layout_edit_note_schedules, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
    }

    private fun initViews() {
        initClickListener()
    }

    private fun initClickListener() {
        binding.tvSave.setOnClickListener {

            dismiss()
        }
        binding.tvCancel.setOnClickListener {
            dismiss()
        }
    }

    interface OnClickSaveNote {
        fun onClickSave()
    }

}