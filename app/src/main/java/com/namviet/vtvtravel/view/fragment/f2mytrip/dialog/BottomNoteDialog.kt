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
import com.namviet.vtvtravel.ultils.DialogUtil
import com.namviet.vtvtravel.ultils.ValidateUtils
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.model.PlacesScheduleItem
import java.util.*

class BottomNoteDialog : BottomSheetDialogFragment() {

    companion object {
        const val KEY_ITEM_PLACE = "item_place"
        fun newInstance(item: PlacesScheduleItem?): BottomNoteDialog {
            val args = Bundle()
            args.putParcelable(KEY_ITEM_PLACE,item)
            val fragment = BottomNoteDialog()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: LayoutEditNoteSchedulesBinding
    private var listener: OnClickSaveNote? = null
    private var item: PlacesScheduleItem? = null

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
        initClickListener()
    }

    private fun initViews() {
        item = arguments?.getParcelable(KEY_ITEM_PLACE)
        binding.edtNoteSchedule.setText(item?.note)
    }

    private fun initClickListener() {
        binding.tvSave.setOnClickListener {
            if (ValidateUtils.isEmptyEdittext(binding.edtNoteSchedule)){
                DialogUtil.showErrorDialog(requireContext().getString(R.string.error_title),requireContext().getString(R.string.close_title),
                "Nội dung ghi chú không được để trống",childFragmentManager)
                return@setOnClickListener
            }
            val noteContent = binding.edtNoteSchedule.text.toString().trim()
            listener?.onClickSave(noteContent)
            dismiss()
        }
        binding.tvCancel.setOnClickListener {
            dismiss()
        }
    }

    interface OnClickSaveNote {
        fun onClickSave(note:String)
    }

}