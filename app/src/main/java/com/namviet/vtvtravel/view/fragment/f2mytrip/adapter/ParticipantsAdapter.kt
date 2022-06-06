package com.namviet.vtvtravel.view.fragment.f2mytrip.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.LayoutItemParticipantBinding
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.UserListItem

class ParticipantsAdapter(var context: Context) :
    RecyclerView.Adapter<ParticipantsAdapter.ViewHolder>() {

    private var listParticipants: MutableList<UserListItem> = mutableListOf()

    fun setListParticipants(listData:MutableList<UserListItem> ){
        listParticipants = listData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutItemParticipantBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_item_participant,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val participant = listParticipants.get(position)
        holder.bind(participant,position)
    }

    override fun getItemCount(): Int {
        return listParticipants.size
    }

    inner class ViewHolder(val binding: LayoutItemParticipantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(participant: UserListItem, position: Int) {
            Glide.with(context).load(participant.imageProfile).into(binding.imvParticipant)
            binding.tvParticipantName.text = participant.fullname
            if (position ==listParticipants.size-1){
                Glide.with(context).load(R.drawable.ic_invite_trip_participant).into(binding.imvParticipant)
                binding.tvParticipantName.text = context.getString(R.string.invite)
            }
        }
    }
}