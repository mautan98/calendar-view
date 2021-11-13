package com.namviet.vtvtravel.view.f3.deal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.ultils.DateUtltils
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse
import kotlinx.android.synthetic.main.item_deal.view.*


class GridDealInDealHomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val TYPE_ITEM = 0
    private var dealResponse : DealResponse? = null
    private var context: Context? = null

    constructor()
    constructor(dealResponse: DealResponse?) : super() {
        this.dealResponse = dealResponse
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
            dealResponse!!.data.content.size
        } catch (e: Exception) {
            0
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
            }
            val content = dealResponse!!.data.content[position]
            itemView.tvName.text = content.name


            if (content.isCampaign) {
//                layoutTotalMustHaveChild.setVisibility(View.VISIBLE)
                itemView.layoutTotalHuntingUser.visibility = View.GONE
                itemView.layoutIsHuntingUser.visibility = View.INVISIBLE
            } else {
//                layoutTotalMustHaveChild.setVisibility(View.GONE)
                itemView.layoutTotalHuntingUser.visibility = View.VISIBLE
                if (content.isUserHunting) {
                    itemView.layoutIsHuntingUser.visibility = View.VISIBLE
                } else {
                    itemView.layoutIsHuntingUser.visibility = View.INVISIBLE
                }
            }

            try {
                itemView.tvExpiryDate.text = "HSD: " + DateUtltils.timeToString18(content.expireDate)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }

        }

    }

}

