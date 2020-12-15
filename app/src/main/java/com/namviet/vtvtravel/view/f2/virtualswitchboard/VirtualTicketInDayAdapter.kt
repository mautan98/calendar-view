package com.namviet.vtvtravel.view.f2.virtualswitchboard

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
import com.namviet.vtvtravel.model.virtualcall.VirtualTicket
import kotlinx.android.synthetic.main.f2_item_virtual_ticket_day.view.*
import java.util.*
import kotlin.collections.ArrayList

class VirtualTicketInDayAdapter(private val context: Context,
                                private var days: List<Pair<Calendar, List<VirtualTicket>>>,
                                private val mListener: VirtualTicketAdapter.OnVirtualTicketActionListener?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setData(days: List<Pair<Calendar, List<VirtualTicket>>>) {
        this.days = days
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.f2_item_virtual_ticket_day, parent, false)
        return VirtualDayVH(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (holder is VirtualDayVH) {
                holder.bindItem(position)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return try {
            days!!.size
        } catch (e: Exception) {
            0
        }
    }

    open inner class VirtualDayVH : RecyclerView.ViewHolder {
        private var position: Int? = 0
        private lateinit var adapter: VirtualTicketAdapter

        constructor(itemView: View?) : super(itemView!!) {
            adapter = VirtualTicketAdapter(context, ArrayList())
            itemView.rcv_virtual_in_day.adapter = adapter
            adapter.listener = mListener
        }


        fun bindItem(position: Int) {
            this.position = position
            adapter.setData(days[position].second)
            itemView.tv_day.text = Utils.convertDateToStringVN(days[position].first.time, "dd/MM/yyyy")
            itemView.tv_2.visibility = if (position != 0) View.GONE else View.VISIBLE
        }
    }


}