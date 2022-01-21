package com.namviet.vtvtravel.adapter.f2search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.model.f2search.Children
import com.namviet.vtvtravel.response.newhome.AppVoucherResponse
import com.namviet.vtvtravel.viewmodel.newhome.ChangeRegionDialog
import kotlinx.android.synthetic.main.f3_item_search_category_drop_down.view.*

class CategoryDropdownAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val TYPE_ITEM = 0
    private var context: Context? = null
    private var items: List<Children>? = null
    private var clickItem : ClickItem? = null;

    constructor()

    constructor(context: Context?,items: List<Children>?, clickItem : ClickItem?) {
        this.context = context
        this.items = items
        this.clickItem = clickItem
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View
//        if (viewType == TYPE_ITEM) {
        v = LayoutInflater.from(parent.context).inflate(R.layout.f3_item_search_category_drop_down, parent, false)
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
            itemView.setOnClickListener {
                clickItem?.onClickItem()
            }


        }

        fun bindItem(position: Int) {
            this.position = position
            itemView.tvTitle.text = items!![position].name
        }

    }

    interface ClickItem{
        fun onClickItem()
    }


}

