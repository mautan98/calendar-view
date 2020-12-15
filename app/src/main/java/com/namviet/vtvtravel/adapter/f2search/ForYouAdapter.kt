package com.namviet.vtvtravel.adapter.f2search

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse
import com.namviet.vtvtravel.response.newhome.AppVoucherResponse
import com.namviet.vtvtravel.ultils.DateUtltils
import com.namviet.vtvtravel.view.f2.TravelVoucherActivity
import kotlinx.android.synthetic.main.f2_item_search_for_you.view.*

class ForYouAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val TYPE_ITEM = 0
    private var context: Context? = null
    private var items: List<AppVoucherResponse.Item>? = null

    constructor()

    constructor(items: List<AppVoucherResponse.Item>?, context: Context?) {
        this.context = context
        this.items = items
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View
//        if (viewType == TYPE_ITEM) {
        v = LayoutInflater.from(parent.context).inflate(R.layout.f2_item_search_for_you, parent, false)
        return HeaderViewHolder(v)
//        }
//        return null
    }

    override fun getItemCount(): Int {
        return try {
            items!!.size;
        } catch (e: Exception) {
            0;
        }
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

    inner class HeaderViewHolder : RecyclerView.ViewHolder {

        private var position: Int? = 0;

        constructor(itemView: View?) : super(itemView!!) {
            itemView?.setOnClickListener {
                try {
                    val voucher = Gson().fromJson(Gson().toJson(items?.get(position!!)), ListVoucherResponse.Data.Voucher::class.java)
                    TravelVoucherActivity.startScreenDetail(context, true, TravelVoucherActivity.OpenType.DETAIL, voucher)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


        }

        fun bindItem(position: Int) {
            this.position = position
            itemView.tvTitle.text = items?.get(position)?.name
            itemView.tvEndTime.text = "HSD "+DateUtltils.timeToString(items?.get(position)?.endAt?.div(1000))
        }

    }


}

