package com.namviet.vtvtravel.view.fragment.f2mytrip.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.LayoutItemUserTripsBinding
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.UserListItem

class UserListAdapter(var context: Context) :
    RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {
    private var listAvt: MutableList<UserListItem> = mutableListOf()

    fun setListAvt(listAvt: MutableList<UserListItem>) {
        this.listAvt = listAvt
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val binding: LayoutItemUserTripsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_item_user_trips, parent, false
        )
        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val userItem = listAvt.get(position)
        holder.bind(userItem)
    }

    override fun getItemCount(): Int {
        return listAvt.size
    }

    inner class UserListViewHolder(var binding: LayoutItemUserTripsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userItem: UserListItem?) {
            Glide.with(context).load(userItem?.imageProfile).placeholder(R.drawable.f2_defaut_user).error(R.drawable.f2_defaut_user).into(binding.imvUserTrips)
        }

    }
}