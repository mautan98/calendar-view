package com.namviet.vtvtravel.view.fragment.f2mytrip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
import com.namviet.vtvtravel.databinding.LayoutItemDateTimeTripBinding
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.SchedulePlaceByDaysItem

class TripsTimeAdapter : RecyclerView.Adapter<TripsTimeAdapter.ViewHolder>() {

    private var listScheduleDay: MutableList<SchedulePlaceByDaysItem> = mutableListOf()

    fun setListScheduleByDays(listData: MutableList<SchedulePlaceByDaysItem>) {
        this.listScheduleDay = listData
        notifyDataSetChanged()
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

    class ViewHolder(var binding: LayoutItemDateTimeTripBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SchedulePlaceByDaysItem, position: Int) {
            val sttDate = position + 1
            val date = Utils.formatTimestampTrips(item.day)
            val numberPlace = item.totalPlace
            val distance = item.totalDistance
            binding.tvTime.text = "Ngày ${sttDate}: $date"
            binding.tvDescDistance.text = "${numberPlace} địa điểm, ${distance} km"
        }

    }

}