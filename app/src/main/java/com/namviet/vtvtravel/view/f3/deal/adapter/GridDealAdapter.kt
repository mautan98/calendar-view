package com.namviet.vtvtravel.view.f3.deal.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse
import com.namviet.vtvtravel.view.f3.deal.view.dealdetail.DetailDealActivity


class GridDealAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val TYPE_ITEM = 0
    private var dealByCampaign : DealResponse? = null

    private var context: Context? = null
    private var url:String? = "https://core-testing.vtvtravel.vn/api/v1/deals/campaigns/details?id=104&isProcessing=1"
    constructor()
    constructor(dealByCampaign: DealResponse?) : super() {
        this.dealByCampaign = dealByCampaign
    }


    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View? = null;
        v = LayoutInflater.from(parent.context).inflate(R.layout.item_deal, parent, false)
        return HeaderViewHolder(v)

    }

    override fun getItemCount(): Int {
        return try {
            dealByCampaign?.data!!.content.size

        } catch (e: Exception) {
            10
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


        constructor(itemView: View?) : super(itemView!!) {

        }

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
               // DetailDealActivity.startScreen(itemView.context, url)
            }
        }

    }

}

