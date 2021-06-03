package com.namviet.vtvtravel.adapter.f2search

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.model.travelnews.Travel
import com.namviet.vtvtravel.response.f2searchmain.MainSearchResponse
import com.namviet.vtvtravel.response.newhome.AppVoucherResponse
import com.namviet.vtvtravel.view.f2.TravelVoucherActivity
import kotlinx.android.synthetic.main.f2_item_search_block.view.*


class SearchBlockAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val TYPE_ITEM = 0
    private var context: Context? = null
    private var datas: List<MainSearchResponse.Data>? = null
    private var forYouList: List<AppVoucherResponse.Item>? = null
    private var trendList: List<Travel>? = null

    constructor()

    public fun setForYouList (forYouList: List<AppVoucherResponse.Item>?){
        this.forYouList = forYouList
        notifyDataSetChanged()
    }

    public fun setTrendList (trendList: List<Travel>?){
        this.trendList = trendList
        notifyDataSetChanged()
    }

    constructor(datas: List<MainSearchResponse.Data>?, context: Context?) {
        this.context = context
        this.datas = datas
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View = LayoutInflater.from(parent.context).inflate(R.layout.f2_item_search_block, parent, false)
        return HeaderViewHolder(v)
    }

    override fun getItemCount(): Int {
        return try {
            datas!!.size;
        } catch (e :Exception) {
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

        private var forYouAdapter : ForYouAdapter? = null
        private var trendingAdapter : TrendingAdapter? = null
        private var discoveryAdapter : DiscoveryAdapter? = null
        private var position: Int? = 0;

        constructor(itemView: View?) : super(itemView!!) {

        }

        fun bindItem(position: Int) {
            this.position = position
            itemView.tvTitle.text = datas?.get(position)?.title
            when (datas?.get(position)?.code) {
                "YOUR_VOUCHER" -> {
                    forYouAdapter = ForYouAdapter(forYouList, context)
                    itemView.rclContent.adapter = forYouAdapter
                    itemView.tvSeeMore.visibility = View.VISIBLE
                    itemView.tvSeeMore.setOnClickListener {
                        try {
                            TravelVoucherActivity.openScreen(context, true, TravelVoucherActivity.OpenType.LIST, false)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                "HIGHLIGHT_TREND" -> {
                    trendingAdapter = TrendingAdapter(trendList, context)
                    itemView.rclContent.adapter = trendingAdapter
                    itemView.tvSeeMore.visibility = View.GONE
0                }
                "NEARBY_SUGGESTION" -> {
                    discoveryAdapter = DiscoveryAdapter(datas?.get(position)?.items, context)
                    itemView.rclContent2.adapter = discoveryAdapter
                    itemView.tvSeeMore.visibility = View.GONE
                }
            }

            itemView.tvTitle.setOnClickListener {
                try {
                    if(itemView.rclContent.visibility == View.GONE){
                        itemView.rclContent.visibility = View.VISIBLE
                        itemView.rclContent2.visibility = View.VISIBLE
                        itemView.imgDown.rotation = 0f
                    }else{
                        itemView.rclContent.visibility = View.GONE
                        itemView.rclContent2.visibility = View.GONE
                        itemView.imgDown.rotation = 180f
                    }
                } catch (e: Exception) {
                }
            }
        }

    }

}

