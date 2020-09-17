package com.namviet.vtvtravel.adapter.f2search

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.model.travelnews.Travel
import com.namviet.vtvtravel.view.f2.TravelNewsActivity
import kotlinx.android.synthetic.main.f2_item_search_news_result.view.*

class NewsSearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val TYPE_ITEM = 0
    private var context: Context? = null
    private var items: List<Travel>? = null

    constructor()

    constructor(items: List<Travel>?, context: Context?) {
        this.context = context
        this.items = items
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View
//        if (viewType == TYPE_ITEM) {
        v = LayoutInflater.from(parent.context).inflate(R.layout.f2_item_search_news_result, parent, false)
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
                    TravelNewsActivity.openScreenDetail(context as Activity?, TravelNewsActivity.OpenType.DETAIL, items?.get(position!!)?.detail_link)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun bindItem(position: Int) {
            this.position = position
            itemView.tvName.text = items?.get(position)?.name
            itemView.tvType.text = items?.get(position)?.category
            Glide.with(context!!).load(items?.get(position)?.logo_url).into(itemView.imgAvatar);
        }

    }


}

