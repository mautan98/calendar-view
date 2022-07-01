package com.namviet.vtvtravel.view.fragment.f2mytrip.place.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
import com.namviet.vtvtravel.databinding.LayoutItemDetailPlaceBinding
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.model.PlacesScheduleItem

class PlacesAdapter : RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {

    private var listDetailPlace: MutableList<PlacesScheduleItem> = mutableListOf()
    private var onItemClickListener: OnItemClickListener? = null

    fun setListDetailPlaces(list: MutableList<PlacesScheduleItem>) {
        this.listDetailPlace = list
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutItemDetailPlaceBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_item_detail_place, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val placeDetailItem = listDetailPlace.get(position)
        holder.bind(placeDetailItem, position)
    }

    override fun getItemCount(): Int {
        return listDetailPlace.size
    }

    inner class ViewHolder(var binding: LayoutItemDetailPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(placeDetailItem: PlacesScheduleItem, position: Int) {
            binding.tvDetailPlaceName.text = placeDetailItem.name
            binding.edtTimeVisiting.text = "${placeDetailItem.durationVisit}p"
            binding.tvArrivalTime.text = Utils.formatTimeStamp(placeDetailItem.arrivalTime,"HH:mm")
            Glide.with(itemView.context).load(placeDetailItem.logoUrl).into(binding.imvDetailPlace)
            if (position == listDetailPlace.size -1)
                binding.layoutMovement.visibility = View.GONE
            binding.tvDescMovement.text = "${placeDetailItem.durationMove} km/${placeDetailItem.freeTime}p"
            binding.layoutTimeTravelPlace.setOnClickListener {
                onItemClickListener?.onItemClick(position,binding.layoutTimeTravelPlace)
            }
            binding.imvIsHaveNote.visibility =
                (if (!TextUtils.isEmpty(placeDetailItem.note)) View.VISIBLE else View.GONE)
            binding.notePlace.setOnClickListener {
                onItemClickListener?.onItemClick(position,binding.notePlace)
            }
            binding.edtTimeVisiting.setOnClickListener {
                onItemClickListener?.onItemClick(position,binding.edtTimeVisiting)
            }
            binding.imvClose.setOnClickListener {
                onItemClickListener?.onItemClick(position,binding.imvClose)
            }
            binding.changePlace.setOnClickListener {
                onItemClickListener?.onItemClick(position,binding.changePlace)
            }
        }

    }

    interface OnItemClickListener{
        fun onItemClick(position: Int,view:View)
    }

}