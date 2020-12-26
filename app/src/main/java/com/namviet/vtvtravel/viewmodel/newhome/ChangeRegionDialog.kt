package com.namviet.vtvtravel.viewmodel.newhome

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.F2DialogChangeRegionBinding
import com.namviet.vtvtravel.databinding.F2DialogNotifyBinding
import com.namviet.vtvtravel.view.dialog.f2.BaseDialogFragment2

class ChangeRegionDialog : BaseDialogFragment2() {
    private var binding: F2DialogChangeRegionBinding? = null
    private var title: String? = null
    private var banner: String? = null;
    private var button: String? = null;
    private var click: Click? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_dialog_change_region, container, false)
        setLabel()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
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
        fun newInstance(description: String?, banner: String?, button: String?, click: Click?): ChangeRegionDialog {
            val oneButtonTitleDialog = ChangeRegionDialog()
            oneButtonTitleDialog.title = description
            oneButtonTitleDialog.banner = banner
            oneButtonTitleDialog.button = button
            oneButtonTitleDialog.click = click
            return oneButtonTitleDialog
        }
    }

    private fun setData() {
        try {
            Glide.with(context!!).load(banner).into(binding?.imgBanner!!)
//            Glide.with(context!!).load(button).into(binding?.btnViewNow!!)
//            binding?.tvTitle?.text = Html.fromHtml(title)

            binding!!.imgBanner.setOnClickListener {
                click?.onClick()
                dismiss()
            }

            binding!!.imgClose.setOnClickListener {
                dismiss()
            }
        } catch (e: Exception) {
        }
    }

    public interface Click {
        fun onClick()
    }
}