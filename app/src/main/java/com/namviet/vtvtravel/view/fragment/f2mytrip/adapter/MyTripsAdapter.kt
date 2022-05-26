package com.namviet.vtvtravel.view.fragment.f2mytrip.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.namviet.vtvtravel.databinding.LayoutItemMyTripsBinding
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.MyTripsResponse
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem

class MyTripsAdapter : RecyclerView.Adapter<MyTripsAdapter.ViewHolder>() {

    private lateinit var context: Context
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

        }

    }

}