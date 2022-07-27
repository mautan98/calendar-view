package com.namviet.vtvtravel.view.fragment.f2mytrip.adapter

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mindinventory.overlaprecylcerview.decoration.OverlapRecyclerViewDecoration
import com.namviet.vtvtravel.BuildConfig
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
import com.namviet.vtvtravel.api.WSConfig
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
            Glide.with(context).load(tripItem.bannerUrl).placeholder(R.drawable.img_placeholder).error(R.drawable.img_placeholder).into(binding.imvBannerTrip)
            binding.tvCreatorName.text = tripItem.creator?.fullname
            binding.tvTripsName.text = tripItem.name
            binding.tvTripsDesc.text = tripItem.description
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvEstimateCost.text = Html.fromHtml(Utils.convertPriceTrips(tripItem.estimatedCost?.toBigDecimal()),Html.FROM_HTML_MODE_COMPACT)
            } else {
                binding.tvEstimateCost.text = Html.fromHtml(Utils.convertPriceTrips(tripItem.estimatedCost?.toBigDecimal()))
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
            val userAdapter = UserListAdapter(context)
            val listAvt = tripItem.userList as MutableList<UserListItem>
            userAdapter.setListAvt(listAvt)
//            binding.rcvImageUser.addItemDecoration(OverlapRecyclerViewDecoration(10,-25))
            binding.rcvImageUser.adapter = userAdapter
            binding.btnShare.setOnClickListener {
                val uri = Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/drawable/bg_demo.jpg")
                val share = Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, WSConfig.HOST_SHARE_TRIP+tripItem?.id)
                    putExtra(Intent.EXTRA_SUBJECT, tripItem?.name)
                    type = "text/plain"

                    // (Optional) Here we're setting the title of the content
                    putExtra(Intent.EXTRA_TITLE, tripItem?.name)

                    // (Optional) Here we're passing a content URI to an image to be displayed
//                data = uri
                    clipData = ClipData.newRawUri("", uri)
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }, tripItem?.name)
                context.startActivity(share)
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

}