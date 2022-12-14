package com.namviet.vtvtravel.adapter.f2search

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.model.f2search.Children
import kotlinx.android.synthetic.main.f3_item_search_category.view.*

class CategorySearchInSlideAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
        v = LayoutInflater.from(parent.context).inflate(R.layout.f3_item_search_category, parent, false)
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

                items!![position!!].isSelected = !items!![position!!].isSelected

                notifyDataSetChanged()
            }


        }

        fun bindItem(position: Int) {
            this.position = position
            itemView.tvTitle.text = items!![position].name


            if (!items!![position].isSelected) {
                itemView.layoutBackground.backgroundColor = context!!.resources.getColor(R.color.white)
                itemView.tvTitle.setTextColor(context!!.resources.getColor(R.color.black))
                when (position){
                    0-> itemView.imgAvatar.setTint(R.color.color_cat_0)
                    1-> itemView.imgAvatar.setTint(R.color.color_cat_1)
                    2-> itemView.imgAvatar.setTint(R.color.color_cat_2)
                    3-> itemView.imgAvatar.setTint(R.color.color_cat_3)
                }
            } else {
                itemView.layoutBackground.backgroundColor = Color.parseColor("#ECB14A")
                itemView.tvTitle.setTextColor(context!!.resources.getColor(R.color.white))
                itemView.imgAvatar.setTint(R.color.white)
            }

            when (position){
                0-> itemView.imgAvatar.setImageResource(R.drawable.f3_ic_go)
                1-> itemView.imgAvatar.setImageResource(R.drawable.f3_ic_stay)
                2-> itemView.imgAvatar.setImageResource(R.drawable.f3_ic_eating)
                3-> itemView.imgAvatar.setImageResource(R.drawable.f3_ic_play)
            }
        }

    }

    interface ClickItem{
        fun onClickItem()
    }


    fun ImageView.setTint(@ColorRes colorRes: Int) {
        ImageViewCompat.setImageTintList(
            this,
            ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
        )
    }


}

