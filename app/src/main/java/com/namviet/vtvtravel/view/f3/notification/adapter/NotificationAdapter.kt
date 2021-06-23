package com.namviet.vtvtravel.view.f3.notification.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.response.newhome.AppVoucherResponse
import com.namviet.vtvtravel.view.f3.notification.model.Notification
import com.namviet.vtvtravel.view.f3.notification.model.ui.NotificationResponse
import kotlinx.android.synthetic.main.f3_item_notification.view.*

class NotificationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val TYPE_ITEM = 0
    private var context: Context? = null
    private var dataList: ArrayList<Notification>? = null
    private var notificationCallback : NotificationCallback? = null

    constructor()

    constructor(context: Context?, dataList: ArrayList<Notification>?,  notificationCallback : NotificationCallback?) {
        this.context = context
        this.notificationCallback = notificationCallback;
        this.dataList = dataList
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View
//        if (viewType == TYPE_ITEM) {
        v = LayoutInflater.from(parent.context).inflate(R.layout.f3_item_notification, parent, false)
        return HeaderViewHolder(v)
//        }
//        return null
    }

    override fun getItemCount(): Int {
        return try {
            dataList!!.size;
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

            }


        }

        fun bindItem(position: Int) {
            this.position = position
            itemView.btnMenu.setOnClickListener {
                notificationCallback?.onClickItemMenu()
            }

            itemView.tvTitle.text = dataList!![position].title
            itemView.tvMessage.text = dataList!![position].message
            when {
                dataList!![position].typeId.equals("11") -> {
                    itemView.tvTypeName.text = "Hệ thống"
                }
                dataList!![position].typeId.equals("10") -> {
                    itemView.tvTypeName.text = "Khuyến mãi"
                }
                dataList!![position].typeId.equals("9") -> {
                    itemView.tvTypeName.text = "Giao dịch"
                }
            }

        }

    }

    interface NotificationCallback {
        fun onClickItemMenu();
    }


}

