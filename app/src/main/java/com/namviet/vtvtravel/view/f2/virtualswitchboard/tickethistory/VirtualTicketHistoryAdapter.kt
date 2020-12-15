package com.namviet.vtvtravel.view.f2.virtualswitchboard.tickethistory

import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
import com.namviet.vtvtravel.model.virtualcall.VirtualTicketHistory
import kotlinx.android.synthetic.main.f2_item_history_ticket.view.*

class VirtualTicketHistoryAdapter(private val context: Context,
                                  private var histories: List<VirtualTicketHistory>,
                                  private val mListener: VirtualTicketHistoryListener?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setData(histories: List<VirtualTicketHistory>) {
        this.histories = histories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.f2_item_history_ticket, parent, false)
        return VirtualHistoryVH(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (holder is VirtualHistoryVH) {
                holder.bindItem(position)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return try {
            histories!!.size
        } catch (e: Exception) {
            0
        }
    }

    open inner class VirtualHistoryVH : RecyclerView.ViewHolder {
        private var position: Int? = 0

        constructor(itemView: View?) : super(itemView!!) {
            itemView.view_action.setOnClickListener { v -> mListener?.onViewHistory(histories[layoutPosition]) }
        }


        fun bindItem(position: Int) {
            this.position = position
            val history = histories[position]
            itemView.tvTime.text = history.created
            itemView.tvName.text = history.fullname

            if(history.fullname != "1039"){
                itemView.view_action.background = context.getDrawable(R.drawable.f2_bg_d8f5f4_corner_4)
                itemView.view_action.ivView.setImageDrawable(context.getDrawable(R.drawable.ic_edit_history_2))
                itemView.view_action.tvView.setTextColor(Color.parseColor("#00918D"))
            } else{
                itemView.view_action.background = context.getDrawable(R.drawable.f2_bg_yellow_fff5de_corner_4)
                itemView.view_action.ivView.setImageDrawable(context.getDrawable(R.drawable.ic_edit_history))
                itemView.view_action.tvView.setTextColor(Color.parseColor("#FF8000"))
            }
        }
    }

    interface VirtualTicketHistoryListener {
        fun onViewHistory(virtualTicket: VirtualTicketHistory)
    }

}