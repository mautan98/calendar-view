package com.namviet.vtvtravel.view.fragment.f2mytrip.adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
import com.namviet.vtvtravel.databinding.LayoutItemMyTripsBinding
import com.namviet.vtvtravel.databinding.LayoutItemUserTripsBinding
import com.namviet.vtvtravel.listener.OnItemRecyclerClickListener
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.UserListItem
import java.util.*
import java.util.concurrent.TimeUnit


class MyTripsAdapter(var context: Context) : RecyclerView.Adapter<MyTripsAdapter.ViewHolder>() {

    private var listTripItem: MutableList<TripItem> = mutableListOf()
    private var onClickItemListener: OnItemRecyclerClickListener?= null

    fun setOnClickItemListener(onClickItemListener: OnItemRecyclerClickListener){
        this.onClickItemListener = onClickItemListener
    }

    fun setListTripItem(list:MutableList<TripItem>){
        this.listTripItem = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding:LayoutItemMyTripsBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.layout_item_my_trips,parent,false)
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvEstimateCost.text = Html.fromHtml(Utils.convertPriceTrips(tripItem.estimatedCost),Html.FROM_HTML_MODE_COMPACT)
            } else {
                binding.tvEstimateCost.text = Html.fromHtml(Utils.convertPriceTrips(tripItem.estimatedCost))
            }
            val diff = tripItem.startAt?.let { tripItem.endAt?.minus(it) }
            if (tripItem.startAt == tripItem.endAt){
                binding.tvDaysTrip.text = "1 Ngày"
            } else {
                binding.tvDaysTrip.text =
                    "${diff?.let { TimeUnit.DAYS.convert(it, TimeUnit.MILLISECONDS) + 1}} Ngày"
            }
            binding.tvAmountPeople.text = tripItem.numberPeople.toString()
            binding.tvStartDate.text = formatTimestamp(tripItem.startAt)
            binding.tvEndDate.text = formatTimestamp(tripItem.endAt)
            itemView.setOnClickListener{
                onClickItemListener?.onItemClick(position)
            }

        }

        private fun formatTimestamp(timeStamp: Long?):String{
            val date = timeStamp?.let { Date(it) }
            val calendar = Calendar.getInstance(TimeZone.getDefault())
            calendar.time = date
            val stringTime = "${calendar.get(Calendar.DAY_OF_MONTH)} thg ${calendar.get(Calendar.MONTH)+ 1}"
            return stringTime
        }

    }

    inner class UserListAdapter: RecyclerView.Adapter<UserListViewHolder>() {

        private var listAvt:MutableList<UserListItem> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
            val binding:LayoutItemUserTripsBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.layout_item_user_trips,parent,false)
            return UserListViewHolder(binding)
        }

        override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
            val userItem = listAvt.get(position)
            holder.bind(userItem)
        }

        override fun getItemCount(): Int {
            return listAvt.size
        }

    }

    inner class UserListViewHolder(var binding: LayoutItemUserTripsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(userItem: UserListItem?){
            Glide.with(context).load(userItem?.imageProfile).into(binding.imvUserTrips)
        }

    }

}