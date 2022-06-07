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
import com.namviet.vtvtravel.view.fragment.f2mytrip.DetailTripFragment
import com.namviet.vtvtravel.view.fragment.f2mytrip.adapter.ParticipantsAdapter
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.UserListItem
import java.util.*
import kotlin.collections.ArrayList

class EditTripBottomDialog : BottomSheetDialogFragment() {

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

    fun setList(tripItem: TripItem?){
        this.tripItem = tripItem
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
    }

    private fun initView(){

        binding.edtTripName.setText(tripItem?.name)
        binding.edtTripDesc.setText(tripItem?.description)
        val adapter = ParticipantsAdapter(requireContext())
        val listAdapter:ArrayList<UserListItem> = tripItem?.userList as ArrayList<UserListItem>
        listAdapter.add(UserListItem())

        adapter.setListParticipants(listAdapter)
        binding.rcvParticipant.adapter = adapter
    }
}