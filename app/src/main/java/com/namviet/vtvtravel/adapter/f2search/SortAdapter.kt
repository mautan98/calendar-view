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
import com.namviet.vtvtravel.model.f2search.SortHeader
import com.namviet.vtvtravel.response.newhome.AppVoucherResponse
import com.namviet.vtvtravel.viewmodel.newhome.ChangeRegionDialog
import kotlinx.android.synthetic.main.f3_item_sort_in_search.view.*

class SortAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val TYPE_ITEM = 0
    private var context: Context? = null
    private var items: List<SortHeader>? = null
    private var clickItem: ClickItem? = null;
    private var type : ArrayList<String>? = null

    constructor()

    constructor(context: Context?, items: List<SortHeader>?, clickItem: ClickItem?, type : ArrayList<String>) {
        this.context = context
        this.items = items
        this.clickItem = clickItem
        this.type = type
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View
//        if (viewType == TYPE_ITEM) {
        v = LayoutInflater.from(parent.context)
            .inflate(R.layout.f3_item_sort_in_search, parent, false)
        return HeaderViewHolder(v)
//        }
//        return null
    }

    override fun getItemCount(): Int {
        return try {
            if(type!![0] == "place"){
                return 4
            }else{
                return 2
            }
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



        }

        fun bindItem(position: Int) {
            var isShow = false
            this.position = position
            if (getStatus(position)) {
                itemView.viewBackground.backgroundColor = Color.parseColor("#00918D")
                itemView.tvTitle.setTextColor(context!!.resources!!.getColor(R.color.white))
                itemView.imgDown.setTint(R.color.white)
            } else {
                itemView.viewBackground.backgroundColor = Color.parseColor("#F6F6F6")
                itemView.tvTitle.setTextColor(context!!.resources!!.getColor(R.color.black))
                itemView.imgDown.setTint(R.color.black)
            }
            itemView.tvTitle.text = items!![position].label

            if(items!![position].isOpen){
                itemView.imgDown.rotation = 180f
            }else{
                itemView.imgDown.rotation = 0f
            }
            itemView.setOnClickListener {
                isShow = !isShow
                clickItem?.onClickItem(position,isShow)
            }
        }

        fun getStatus(position: Int): Boolean {

            when (position) {
                0 -> {
                    if(type!![0] == "place") {
                        items!![position].label = items!![position].namePlace
                        for (i in items!![position].childrenPlace.indices) {
                            if (items!![position].childrenPlace[i].isSelected) {
                                items!![position].label = items!![position].childrenPlace[i].name
                                return true
                            }
                        }
                    }else if (type!![0] == "news"){
                        items!![position].label = items!![position].nameNews
                        for (i in items!![position].childrenNews.indices) {
                            if (items!![position].childrenNews[i].isSelected) {
                                items!![position].label = items!![position].childrenNews[i].name
                                return true
                            }
                        }
                    }else{
                        items!![position].label = items!![position].nameVideo
                        for (i in items!![position].childrenVideo.indices) {
                            if (items!![position].childrenVideo[i].isSelected) {
                                items!![position].label = items!![position].childrenVideo[i].name
                                return true
                            }
                        }
                    }
                }

                1 -> {
                    items!![position].label = "Địa điểm"
                    if (!items!![position].content.commune.isNullOrBlank()) {
                        items!![position].label = items!![position].content.communeName
                        return true
                    } else if (!items!![position].content.district.isNullOrBlank()) {
                        items!![position].label = items!![position].content.districtName
                        return true
                    } else if (!items!![position].content.cityId.isNullOrBlank()) {
                        items!![position].label = items!![position].content.cityName
                        return true
                    }
                }
                2 -> {
                    items!![position].label = items!![position].name
                    for (i in items!!.indices) {
                        if (items!![position].children[i].isSelected) {
                            return true
                        }
                    }
                }
                3 -> {
                    items!![position].label = "Trạng thái"
                    if (items!![position].content.isOpen != null) {
                        if(items!![position].content.isOpen){
                            items!![position].label = "Đang mở"
                        }else{
                            items!![position].label = "Tất cả"
                        }
                        return true
                    }
                }
            }

            return false
        }

        fun ImageView.setTint(@ColorRes colorRes: Int) {
            ImageViewCompat.setImageTintList(
                this,
                ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
            )
        }

    }

    interface ClickItem {
        fun onClickItem(position: Int, isShowDropdown:Boolean)
    }


}

