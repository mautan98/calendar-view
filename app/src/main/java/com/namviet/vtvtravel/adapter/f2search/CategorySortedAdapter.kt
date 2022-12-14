package com.namviet.vtvtravel.adapter.f2search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.slider.library.Tricks.ViewPagerEx
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.model.f2search.Children
import com.namviet.vtvtravel.response.newhome.AppVoucherResponse
import kotlinx.android.synthetic.main.f2_item_category_sorted.view.*

class CategorySortedAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val TYPE_ITEM = 0
    private var context: Context? = null
    private var items: ArrayList<Children>? = null
    private var clickItem : ClickItem? = null

    constructor()

    constructor(items: ArrayList<Children>?, context: Context?, clickItem : ClickItem?) {
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
        v = LayoutInflater.from(parent.context).inflate(R.layout.f2_item_category_sorted, parent, false)
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
                items!![position!!].isSelected = !items!![position!!].isSelected
                notifyDataSetChanged()
                clickItem?.onClickItem()
            }


        }

        fun bindItem(position: Int) {
            this.position = position
            var layoutParam = itemView.layoutRoot.layoutParams

            if(items!![position!!].isSelected){
                layoutParam.width = ViewGroup.LayoutParams.WRAP_CONTENT
                layoutParam.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }else{
                layoutParam.width = 1
                layoutParam.height = 1
            }
            itemView.layoutParams = layoutParam
            itemView.tvTitle.text = items!![position].name
        }

    }

    interface ClickItem{
        fun onClickItem();
    }


}

