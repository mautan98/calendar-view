package com.namviet.vtvtravel.ultils

import androidx.fragment.app.FragmentManager
import com.namviet.vtvtravel.view.fragment.f2travelvoucher.AlreadyReceiverDialog

class DialogUtil {

    companion object {

        fun showErrorDialog(
            title: String,
            buttonLabel: String,
            description: String,
            fm: FragmentManager
        ) {
            val dialog =  AlreadyReceiverDialog()
            dialog.setDialogTitle(title)
            dialog.setLabelButton(buttonLabel)
            dialog.setDescription(description)
            dialog.show(fm, null)
        }
    }

}