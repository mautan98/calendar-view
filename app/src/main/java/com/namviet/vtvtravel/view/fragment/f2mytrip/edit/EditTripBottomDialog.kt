package com.namviet.vtvtravel.view.fragment.f2mytrip.edit

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.LayoutBottomDialogEditTripBinding
import com.namviet.vtvtravel.response.BaseResponse
import com.namviet.vtvtravel.view.fragment.f2mytrip.DetailTripFragment
import com.namviet.vtvtravel.view.fragment.f2mytrip.adapter.ParticipantsAdapter
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.UserListItem
import com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel.MyTripsViewModel
import java.util.*
import kotlin.collections.ArrayList

class EditTripBottomDialog : BottomSheetDialogFragment(), Observer {

    companion object {
        fun newInstance(tripItem: TripItem):EditTripBottomDialog {
            val args = Bundle()
            args.putParcelable(DetailTripFragment.KEY_TRIP_ITEM,tripItem)
            val fragment = EditTripBottomDialog()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: LayoutBottomDialogEditTripBinding
    private var tripItem: TripItem? = null
    private val viewModel = MyTripsViewModel()
    private var onBackFragmentListener:OnBackFragmentListener?= null

    fun setList(tripItem: TripItem?){
        this.tripItem = tripItem
    }

    fun setOnBackFragmentListener(onBackFragmentListener: OnBackFragmentListener?){
        this.onBackFragmentListener = onBackFragmentListener
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
            R.layout.layout_bottom_dialog_edit_trip,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initClickListener()
    }

    private fun initView(){

        binding.edtTripName.setText(tripItem?.name)
        binding.edtTripDesc.setText(tripItem?.description)
        val adapter = ParticipantsAdapter(requireContext())
        val listAdapter:ArrayList<UserListItem> = tripItem?.userList as ArrayList<UserListItem>
        listAdapter.add(UserListItem())

        adapter.setListParticipants(listAdapter)
        binding.rcvParticipant.adapter = adapter
        viewModel.addObserver(this)
    }

    private fun initClickListener(){
        binding.tvUpdateSchedule.setOnClickListener {
            val name = binding.edtTripName.text.toString()
            val description = binding.edtTripDesc.text.toString()
            val scheduleId = tripItem?.id
            viewModel.updateSchedule(name,description,scheduleId!!)
        }
        binding.tvCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun update(o: Observable?, arg: Any?) {
        if (arg is BaseResponse){
            val response = arg
            if (response.isSuccess){
                dismiss()
                onBackFragmentListener?.onBackFragment()
            }
        }
    }

    interface OnBackFragmentListener{
        fun onBackFragment()
    }
}