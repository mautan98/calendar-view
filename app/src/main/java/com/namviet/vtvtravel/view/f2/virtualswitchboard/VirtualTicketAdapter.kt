package com.namviet.vtvtravel.view.f2.virtualswitchboard

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
import com.namviet.vtvtravel.model.virtualcall.VirtualTicket
import kotlinx.android.synthetic.main.f2_item_virtual_ticket.view.*
import java.util.*

class VirtualTicketAdapter(private val context: Context, private var tickets: List<VirtualTicket>)
    : RecyclerView.Adapter<VirtualTicketAdapter.VirtualCallVH>() {

    var listener: OnVirtualTicketActionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VirtualCallVH {
        return VirtualCallVH(LayoutInflater.from(parent.context).inflate(R.layout.f2_item_virtual_ticket, parent, false))
    }

    override fun onBindViewHolder(holder: VirtualCallVH, position: Int) {
        try {
            if (holder is VirtualCallVH) {
                holder.bindItem(position)
            }
        } catch (e: Exception) {

        }
    }

    override fun getItemCount(): Int {
        return tickets?.size ?: 0
    }

    fun setData(data: List<VirtualTicket>) {
        tickets = data;
        notifyDataSetChanged()
    }

    inner class VirtualCallVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.iv_menu.setOnClickListener {
                run {
                    listener?.onSelectMenuTicket(tickets[layoutPosition])
                }
            }
            itemView.setOnClickListener {
                run {
                    listener?.onSelectTicket(tickets[layoutPosition])
                }
            }
        }

        fun bindItem(position: Int) {

            val date = Date(tickets[position].deadline)

            itemView.tv_user.setTextColor(if (tickets[position].deadline > System.currentTimeMillis())
                Color.parseColor("#000000")
            else Color.parseColor("#FF0000"))
            if (!TextUtils.isEmpty(tickets[position].customerName)) {
                itemView.tv_user.text = tickets[position].customerName
                itemView.tv_user.typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
            } else {
                itemView.tv_user.text = "Không xác định"
                itemView.tv_user.typeface = ResourcesCompat.getFont(context, R.font.roboto_italic)
            }
            itemView.tv_time.text = Utils.convertDateToString(date, "HH:mm")
            itemView.tv_date.text = Utils.convertDateToString(date, "dd-MM-yyyy") //dd-MM-yyyy
            itemView.tv_phone.text = tickets[position].creatorMobile

        }
    }

    interface OnVirtualTicketActionListener {
        fun onSelectTicket(virtualTicket: VirtualTicket)
        fun onSelectMenuTicket(virtualTicket: VirtualTicket)
    }
}
