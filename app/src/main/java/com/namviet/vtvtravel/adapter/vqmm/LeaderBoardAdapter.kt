package com.namviet.vtvtravel.adapter.vqmm

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.response.f2wheel.WheelChartResponse
import kotlinx.android.synthetic.main.f2_item_leader_board.view.*
import java.text.SimpleDateFormat
import java.util.*

class LeaderBoardAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var context: Context? = null
    private var comments: List<WheelChartResponse.Data.Item>? = null

    constructor(comments: List<WheelChartResponse.Data.Item>?, context: Context?) {
        this.context = context
        this.comments = comments
    }

    fun setData(comments: List<WheelChartResponse.Data.Item>) {
        this.comments = comments
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View
//        if (viewType == TYPE_ITEM) {
        v = LayoutInflater.from(parent.context).inflate(R.layout.f2_item_leader_board, parent, false)
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
            comments!!.size
        } catch (e: Exception) {
            0
        }
    }

    inner class HeaderViewHolder : RecyclerView.ViewHolder {
        private var position: Int? = 0

        constructor(itemView: View?) : super(itemView!!) {
        }


        fun bindItem(position: Int?) {
            this.position = position
            if (position != null) {
                itemView.tvSTT.text = (position + 1).toString()
            }else{
                itemView.tvSTT.text = ""
            }
//            itemView.tvTime.text = comments?.get(position!!)?.createdAt
            itemView.tvPhone.text = comments?.get(position!!)?.mobile
            itemView.tvGift.text = comments?.get(position!!)?.areasWinning
            itemView.tvStatus.text = "Thành công"

            var formatter  =  SimpleDateFormat("HH:mm:ss \ndd/MM/yyyy")
            var dateString = formatter.format(comments?.get(position!!)?.createdAt?.toLong()?.let { Date(it) })
            itemView.tvTime.text = dateString
        }
    }

    companion object {
        private const val TYPE_ITEM = 0
    }

}