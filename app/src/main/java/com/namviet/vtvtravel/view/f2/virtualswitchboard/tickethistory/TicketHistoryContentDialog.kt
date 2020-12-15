package com.namviet.vtvtravel.view.f2.virtualswitchboard.tickethistory

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment2
import kotlinx.android.synthetic.main.f2_dialog_history_content.*

class TicketHistoryContentDialog : BaseDialogFragment2() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.f2_dialog_history_content, container, false)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null && dialog.window != null) {
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(true)
        }


        btnClose?.setOnClickListener {
            dismiss()
        }
        viewOutside?.setOnClickListener { dismiss() }

        tvContent.movementMethod = ScrollingMovementMethod()
        tvContent.text = arguments?.getString(K_TICKET)
    }

    companion object {
        const val K_TICKET = "ticket_history"
        fun newInstance(content: String): TicketHistoryContentDialog {
            val selectVirtualCallTypeDialog = TicketHistoryContentDialog()
            val args = Bundle()
            args.putString(K_TICKET, content)
            selectVirtualCallTypeDialog.arguments = args
            return selectVirtualCallTypeDialog
        }
    }
}