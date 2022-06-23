package com.namviet.vtvtravel.view.fragment.f2mytrip.place.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
import com.namviet.vtvtravel.databinding.LayoutItemPlaceBinding
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.model.ItemPlaces

class AllPlacesAdapter : RecyclerView.Adapter<AllPlacesAdapter.ViewHolder>() {

    private var listDetailPlace: MutableList<ItemPlaces> = mutableListOf()
    private var onItemClickListener: OnItemClickPlace? = null
    private var lastCheckedPos = -1
    fun setListDetailPlaces(list: MutableList<ItemPlaces>) {
        this.listDetailPlace = list
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickPlace) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutItemPlaceBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_item_place, parent, false
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

    fun getItemPlaceSelected(): ItemPlaces? {
        if (lastCheckedPos != -1){
            return listDetailPlace.get(lastCheckedPos)
        }
        return null
    }

    inner class ViewHolder(var binding: LayoutItemPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(placeItem: ItemPlaces, currentPosition: Int) {
            binding.tvDayNoDetail.text = "Ngày${currentPosition + 1}"
            binding.tvDayDetail.text = Utils.formatTimeStamp(placeItem.day, "yyyy-MM-dd")
            binding.tvNumberPlace.text = "${placeItem.totalPlace} địa điểm"
            val adapter = PlacesAdapter()
            adapter.setOnItemClickListener(object : PlacesAdapter.OnItemClickListener{
                override fun onItemClick(position: Int, view: View) {
                    onItemClickListener?.onItemClick(currentPosition,position,view)
                }

            })
            placeItem.schedulePlaceList?.let { adapter.setListDetailPlaces(it) }
            binding.rcvPlacesInday.adapter = adapter
            binding.rbtnSelectAddPlace.isChecked = currentPosition == lastCheckedPos
            placeItem.isSelected = binding.rbtnSelectAddPlace.isChecked
            binding.rbtnSelectAddPlace.setOnCheckedChangeListener { buttonView, isChecked ->
                placeItem.isSelected = isChecked
                if (isChecked){
                    lastCheckedPos = currentPosition
                    notifyDataSetChanged()
                }
            }
        }
    }

    interface OnItemClickPlace{
        fun onItemClick(currentPos: Int, positionItem: Int, view: View)
    }

}