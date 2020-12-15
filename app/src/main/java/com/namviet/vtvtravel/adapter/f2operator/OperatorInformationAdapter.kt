package com.namviet.vtvtravel.adapter.f2operator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.model.f2operator.OperatorInformation
import kotlinx.android.synthetic.main.f2_item_operator_information.view.*

class OperatorInformationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val TYPE_ITEM = 0
    private var context: Context? = null
    private var items: List<OperatorInformation>? = null

    constructor()

    constructor(items: List<OperatorInformation>?, context: Context?) {
        this.context = context
        this.items = items
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View
//        if (viewType == TYPE_ITEM) {
        v = LayoutInflater.from(parent.context).inflate(R.layout.f2_item_operator_information, parent, false)
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
        }

        fun bindItem(position: Int) {
            this.position = position
            itemView.tvTitle.text = items?.get(position)?.title
            itemView.tvContent.text = items?.get(position)?.content

            if(items?.get(position)?.isShowTick!!){
                itemView.imgTick.visibility = View.VISIBLE
            }else{
                itemView.imgTick.visibility = View.GONE
            }
        }

    }


}

