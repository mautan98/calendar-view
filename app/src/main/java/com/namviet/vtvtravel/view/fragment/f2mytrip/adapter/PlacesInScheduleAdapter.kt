package com.namviet.vtvtravel.view.fragment.f2mytrip.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
import com.namviet.vtvtravel.databinding.LayoutItemScheduleByDaysBinding
import com.namviet.vtvtravel.listener.OnItemRecyclerClickListener
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.SchedulePlaceByDaysItem

class PlacesInScheduleAdapter(var context:Context) : RecyclerView.Adapter<PlacesInScheduleAdapter.ViewHolder>() {

    private var listPlace:MutableList<SchedulePlaceByDaysItem> = mutableListOf()
    private lateinit var onItemClickListener : OnItemRecyclerClickListener

    fun setListPlaces(list:MutableList<SchedulePlaceByDaysItem>){
        listPlace = list
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener:OnItemRecyclerClickListener){
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding:LayoutItemScheduleByDaysBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_item_schedule_by_days,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val placeItem = listPlace.get(position)
        holder.bind(placeItem,position)
    }

    override fun getItemCount(): Int {
       return listPlace.size
    }

    inner class ViewHolder(var binding: LayoutItemScheduleByDaysBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(placeItem:SchedulePlaceByDaysItem,position: Int){
            Glide.with(context).load(placeItem.logoUrl).placeholder(R.drawable.img_placeholder).error(R.drawable.img_placeholder).into(binding.imvPlace)
            binding.tvDayNo.text = "Ngày ${position +1}"
            binding.tvDayStartSchedule.text = Utils.formatTimestampTrips(placeItem.day)
            if (placeItem.totalPlace != null && placeItem.totalDistance != null){
                binding.tvNumberPlace.text = "${placeItem.totalPlace} địa điểm, ${placeItem.totalDistance} km"
            } else {
                binding.tvNumberPlace.text = "0 địa điểm, 0 km"
            }
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(position)
            }
        }
    }
}