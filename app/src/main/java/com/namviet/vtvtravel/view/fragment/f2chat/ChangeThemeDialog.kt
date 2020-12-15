package com.namviet.vtvtravel.view.fragment.f2chat

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2chat.ChangeThemeAdapter
import com.namviet.vtvtravel.databinding.F2DialogChangeThemeBinding
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.response.BaseResponse
import com.namviet.vtvtravel.response.f2chat.Content
import com.namviet.vtvtravel.response.f2chat.GetListThemeResponse
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment2
import com.namviet.vtvtravel.viewmodel.f2chat.ChangeThemeDialogViewModel
import kotlinx.android.synthetic.main.f2_dialog_change_theme.*
import java.util.*

class ChangeThemeDialog : BaseDialogFragment2(), Observer {
    private var binding: F2DialogChangeThemeBinding? = null
    private var click: Click? = null
    private var changeThemeAdapter: ChangeThemeAdapter? = null
    private val viewModel: ChangeThemeDialogViewModel = ChangeThemeDialogViewModel()
    private var content: Content? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_change_theme, container, false)
        setLabel()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        viewModel.addObserver(this)
        viewModel.getListTheme()
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null && dialog.window != null) {
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun setLabel() {}
    interface ClickButton {
        fun onClickButton()
    }

    companion object {
        fun newInstance(click: Click?): ChangeThemeDialog {
            val oneButtonTitleDialog = ChangeThemeDialog()
            oneButtonTitleDialog.click = click
            return oneButtonTitleDialog
        }
    }

    private fun setData() {
        try {
            binding!!.btnDone.setOnClickListener {
                viewModel.updateTheme(content?.id)
                dismiss()
            }
        } catch (e: Exception) {
        }
    }

    interface Click {
        fun onClick(url: String)
    }

    override fun update(observable: Observable?, o: Any?) {
        if (observable is ChangeThemeDialogViewModel && null != o) {
            when (o) {
                is GetListThemeResponse -> {
                    changeThemeAdapter = ChangeThemeAdapter(o.data.content) { content: Content, i: Int ->
                        click?.onClick(content.pathUri)
                        o.data.content.forEach { content -> content.isClicked = false }
                        content.isClicked = true
                        changeThemeAdapter?.notifyDataSetChanged()

                        this.content = content
                    }
                    rclTheme.adapter = changeThemeAdapter
                }
                is ErrorResponse -> {

                }
                is BaseResponse -> {

                }
            }
        }
    }
}