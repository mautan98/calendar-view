package com.namviet.vtvtravel.adapter.vqmm

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse
import com.namviet.vtvtravel.ultils.DateUtltils
import kotlinx.android.synthetic.main.f2_item_voucher_lucky_wheel.view.*

class AllVoucherAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var context: Context? = null
    private var vouchers: List<ListVoucherResponse.Data.Voucher>? = null
    private var clickItem: ClickItem? = null
    private var countSelected = 0;
    private var canTick: Boolean = false

    constructor(vouchers: List<ListVoucherResponse.Data.Voucher>?, context: Context?, clickItem: ClickItem?, canTick: Boolean) {
        this.context = context
        this.vouchers = vouchers
        this.clickItem = clickItem
        this.canTick = canTick
    }

    fun setData(vouchers: List<ListVoucherResponse.Data.Voucher>) {
        this.vouchers = vouchers
        countSelected = 0;
        notifyDataSetChanged()
    }

    fun resetCountSelected(){
        countSelected = 0
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View
//        if (viewType == TYPE_ITEM) {
        v = LayoutInflater.from(parent.context).inflate(R.layout.f2_item_voucher_lucky_wheel, parent, false)
        return HeaderViewHolder(v)
//        }
//        return null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (getItemViewType(position) == TYPE_ITEM) {
                (holder as HeaderViewHolder).bindItem(position)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return try {
            vouchers!!.size
        } catch (e: Exception) {
            0
        }
    }

    inner class HeaderViewHolder : RecyclerView.ViewHolder {
        private var position: Int? = 0

        constructor(itemView: View?) : super(itemView!!) {

            itemView.setOnClickListener {
                clickItem?.onClickItem(position?.let { it1 -> vouchers?.get(it1) })
            }

            itemView.imgSelected.setOnClickListener {
                if(canTick) {
                    itemView.imgSelected.visibility = View.GONE
                    itemView.imgUnSelected.visibility = View.VISIBLE
                    vouchers?.get(position!!)?.isSelectedLuckyWheel  = false
                    countSelected -= 1
                }
            }
            itemView.imgUnSelected.setOnClickListener {

                if(canTick) {
                    if (countSelected >= 3) {
                        clickItem?.onMaxSelected()
                    } else {
                        countSelected += 1
                        itemView.imgSelected.visibility = View.VISIBLE
                        itemView.imgUnSelected.visibility = View.GONE
                        vouchers?.get(position!!)?.isSelectedLuckyWheel  = true
                    }
                }
            }

        }


        fun bindItem(position: Int?) {
            this.position = position
            val travel = vouchers!![position!!]
            Glide.with(context!!).load(travel.avatarUri).into(itemView.imgAvatar)
            itemView.tvTitle.text = travel.name
            try {
                itemView.tvOutDate.text = "Hạn đến " + DateUtltils.timeToString(java.lang.Long.valueOf(travel.endAt) / 1000)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if(!canTick){
                itemView.imgSelected.visibility = View.VISIBLE
                itemView.imgUnSelected.visibility = View.GONE
            }else{
                itemView.imgSelected.visibility = View.GONE
                itemView.imgUnSelected.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        private const val TYPE_ITEM = 0
    }

    interface ClickItem {
        fun onClickItem(voucher: ListVoucherResponse.Data.Voucher?)
        fun onMaxSelected()
    }


}