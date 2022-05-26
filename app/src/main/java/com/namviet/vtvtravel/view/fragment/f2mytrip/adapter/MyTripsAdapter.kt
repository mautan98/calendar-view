package com.namviet.vtvtravel.view.fragment.f2mytrip.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namviet.vtvtravel.databinding.LayoutItemMyTripsBinding
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem
import java.util.*
import java.util.concurrent.TimeUnit


class MyTripsAdapter(var context: Context) : RecyclerView.Adapter<MyTripsAdapter.ViewHolder>() {

    private var listTripItem: MutableList<TripItem> = mutableListOf()

    fun setListTripItem(list:MutableList<TripItem>){
        this.listTripItem = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = LayoutItemMyTripsBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tripItem: TripItem = listTripItem[position]
        holder.bind(tripItem,position)
    }

    override fun getItemCount(): Int {
        return listTripItem.size
    }

    inner class ViewHolder(var binding: LayoutItemMyTripsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tripItem: TripItem , position: Int) {
            Glide.with(context).load(tripItem.bannerUrl).into(binding.imvBannerTrip)
            binding.tvCreatorName.text = tripItem.creator?.fullname
            binding.tvTripsName.text = tripItem.name
            binding.tvTripsDesc.text = tripItem.description
            binding.tvEstimateCost.text = tripItem.estimatedCost.toString()
            val diff = tripItem.startAt?.let { tripItem.endAt?.minus(it) }
            if (tripItem.startAt == tripItem.endAt){
                binding.tvDaysTrip.text = "1 Ngày"
            } else {
                binding.tvDaysTrip.text =
                    diff?.let { TimeUnit.DAYS.convert(it, TimeUnit.MILLISECONDS) }.toString()
            }

        }

    }

}