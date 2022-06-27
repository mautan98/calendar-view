package com.namviet.vtvtravel.view.fragment.f2mytrip.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
import com.namviet.vtvtravel.databinding.LayoutItemDateTimeTripBinding
import com.namviet.vtvtravel.listener.OnItemRecyclerClickListener
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.SchedulePlaceByDaysItem

class TripsTimeAdapter : RecyclerView.Adapter<TripsTimeAdapter.ViewHolder>() {

    private var listScheduleDay: MutableList<SchedulePlaceByDaysItem> = mutableListOf()
    private var onItemClickListener: OnItemRecyclerClickListener? = null

    fun setListScheduleByDays(listData: MutableList<SchedulePlaceByDaysItem>) {
        this.listScheduleDay = listData
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemRecyclerClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutItemDateTimeTripBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_item_date_time_trip, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listScheduleDay.get(position)
        holder.bind(item, position)
    }

    override fun getItemCount(): Int {
        return listScheduleDay.size
    }

    inner class ViewHolder(var binding: LayoutItemDateTimeTripBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SchedulePlaceByDaysItem, position: Int) {
            val sttDate = position + 1
            val date = Utils.formatTimestampTrips(item.day)
            val numberPlace = item.totalPlace
            val distance = item.totalDistance
            binding.tvTime.text = "Ngày ${sttDate}: $date"
            binding.tvDescDistance.text = "${numberPlace} địa điểm, ${distance} km"
            Glide.with(itemView.context).load(item.logoUrl).into(binding.imvPlaceTime)
            if (item.removeAble){
                binding.imvDeleteScheduleDate.visibility = View.VISIBLE
            } else {
                binding.imvDeleteScheduleDate.visibility = View.GONE
            }
            clickListener(position)
        }

        private fun clickListener(position: Int){
            binding.imvDeleteScheduleDate.setOnClickListener {
                onItemClickListener?.onItemClick(position)
            }
        }

    }

}