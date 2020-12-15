package com.namviet.vtvtravel.view.f2.virtualswitchboard

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.model.virtualcall.VirtualTicket
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment2
import kotlinx.android.synthetic.main.f2_dialog_select_virtual_call_type.*

class SelectVirtualCallTypeDialog : BaseDialogFragment2() {

    private var virtualTicket: VirtualTicket? = null
    private var clickButton: ClickButton? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.f2_dialog_select_virtual_call_type, container, false)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null && dialog.window != null) {
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(true)
        }
//        if (virtualTicket?.customerId != null && virtualTicket?.customerId!! > 0) {
//            tvCallInApp?.isEnabled = true
//            ivCallInApp?.isEnabled = true
//            btn_call_in_app?.isEnabled = true
//            btn_call_in_app?.setOnClickListener {
//                clickButton?.onClickCallInApp(this@SelectVirtualCallTypeDialog)
//            }
//        } else {
            tvCallInApp?.isEnabled = false
            ivCallInApp?.isEnabled = false
            btn_call_in_app?.isEnabled = false
//        }

        btn_call_normal?.setOnClickListener {
            clickButton?.onClickCallNormal(this@SelectVirtualCallTypeDialog)
            dismiss()
        }

        viewOutside?.setOnClickListener { dismiss() }
    }

    interface ClickButton {
        fun onClickCallInApp(selectVirtualCallTypeDialog: SelectVirtualCallTypeDialog)
        fun onClickCallNormal(selectVirtualCallTypeDialog: SelectVirtualCallTypeDialog)
    }

    companion object {
        fun newInstance(virtualTicket: VirtualTicket, clickButton: ClickButton?): SelectVirtualCallTypeDialog {
            val selectVirtualCallTypeDialog = SelectVirtualCallTypeDialog()
            selectVirtualCallTypeDialog.virtualTicket = virtualTicket
            selectVirtualCallTypeDialog.clickButton = clickButton
            return selectVirtualCallTypeDialog
        }
    }
}