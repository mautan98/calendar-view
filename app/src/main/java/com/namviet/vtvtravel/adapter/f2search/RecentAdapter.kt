package com.namviet.vtvtravel.adapter.f2search

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.namviet.vtvtravel.R
import kotlinx.android.synthetic.main.f2_item_search_recent.view.*

class RecentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var context: Context? = null
    public var clickItem: ClickItem? = null
    private var comments: List<String>? = null
    private var noItem: NoItem? = null;

    constructor(comments: List<String>, context: Context, clickItem: ClickItem, noItem: NoItem) {
        this.context = context
        this.clickItem = clickItem
        this.comments = comments
        this.noItem = noItem;
    }

    fun setData(comments: List<String>) {
        this.comments = comments
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View
//        if (viewType == TYPE_ITEM) {
        v = LayoutInflater.from(parent.context).inflate(R.layout.f2_item_search_recent, parent, false)
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
            if(comments!!.isEmpty()){
                noItem!!.onNoItem(true)
            }else{
                noItem!!.onNoItem(false)
            }
            return comments!!.size
        } catch (e: Exception) {
            noItem!!.onNoItem(true)
            0
        }
    }

    inner class HeaderViewHolder : RecyclerView.ViewHolder {
        private var position: Int? = 0

        constructor(itemView: View?) : super(itemView!!) {
            itemView?.setOnClickListener {
                clickItem?.onClickItem(position?.let { it1 -> comments?.get(it1) })
            }
        }


        fun bindItem(position: Int?) {
            this.position = position
            itemView.tvTitle.text = position?.let { comments?.get(it) }
        }
    }

    interface ClickItem {
        fun onClickItem(string: String?)
    }

    interface NoItem {

        fun onNoItem(b: Boolean)
    }

    companion object {
        private const val TYPE_ITEM = 0
    }

}