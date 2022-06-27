package com.namviet.vtvtravel.widget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.LayoutConfirmDeleteBinding
import com.namviet.vtvtravel.databinding.LayoutSuccessInviteScheduleBinding
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment2

class ConfirmDeleteDialog : BaseDialogFragment2() {

    private lateinit var binding: LayoutConfirmDeleteBinding
    private var description: String? = null
    private var labelButton: String? = null
    private var confirmListener: OnConfirmListener? = null

    fun setDescription(desc: String) {
        description = desc.trim()
    }

    fun setLabelButton(label: String) {
        labelButton = label.trim()
    }

    fun setConfirmClickListener(listener:OnConfirmListener){
        confirmListener = listener
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null && dialog.window != null) {
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_confirm_delete,
            container,
            false
        )
        initviews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
    }

    private fun initviews() {
        binding.tvDescription.text = description
    }

    private fun initClickListener() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnConfirm.setOnClickListener {

        }
    }

    interface OnConfirmListener{
        fun onClickConfirm()
    }
}