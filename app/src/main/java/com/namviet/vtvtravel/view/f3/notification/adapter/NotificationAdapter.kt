package com.namviet.vtvtravel.view.f3.notification.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.config.Constants
import com.namviet.vtvtravel.response.newhome.AppVoucherResponse
import com.namviet.vtvtravel.view.f3.notification.model.Notification
import com.namviet.vtvtravel.view.f3.notification.model.ui.NotificationResponse
import kotlinx.android.synthetic.main.f3_dialog_menu_item_notify.view.*
import kotlinx.android.synthetic.main.f3_item_notification.view.*
import kotlinx.android.synthetic.main.f3_item_notification_header.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotificationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val TYPE_ITEM = 0
    private val TYPE_ITEM_HEADER = 1
    private var context: Context? = null
    private var dataList: ArrayList<Notification>? = null
    private var notificationCallback: NotificationCallback? = null

    constructor()

    constructor(
            context: Context?,
            dataList: ArrayList<Notification>?,
            notificationCallback: NotificationCallback?
    ) {
        this.context = context
        this.notificationCallback = notificationCallback;
        this.dataList = dataList
    }

    override fun getItemViewType(position: Int): Int {
        if (dataList!![position].isHeader) {
            return TYPE_ITEM_HEADER
        } else {
            return TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.f3_item_notification, parent, false)
            return HeaderViewHolder(v)
        } else {
            v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.f3_item_notification_header, parent, false)
            return HeaderViewHolder2(v)
        }
//        return null
    }

    override fun getItemCount(): Int {
         try {
            if (dataList!!.size > 0) {
                notificationCallback?.onDataChange(false)
                return dataList!!.size;
            }else{
                notificationCallback?.onDataChange(true)
                return 0;
            }
        } catch (e: Exception) {
             notificationCallback?.onDataChange(true)
            return 0;
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (getItemViewType(position) == TYPE_ITEM) {
                (holder as HeaderViewHolder).bindItem(position)
            } else if (getItemViewType(position) == TYPE_ITEM_HEADER) {
                (holder as HeaderViewHolder2).bindItem(position)
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
                notificationCallback?.onClickItemMenu(position, dataList!![position])
            }

            itemView.layoutRoot.setOnClickListener {
                notificationCallback?.onClickItem(position, dataList!![position])
            }

            try {
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
            } catch (e: Exception) {
            }


            try {
                val sdf = SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_2, Locale.getDefault())
                var currentDate = Date()
                var currentDateString = sdf.format(currentDate)


                var dataDate = sdf.format(Date(dataList!![position].createdAt.toLong()))

                if (currentDateString != dataDate) {
                    val sdfHM = SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_19, Locale.getDefault())
                    var dataDateHM = sdfHM.format(Date(dataList!![position].createdAt.toLong()))
                    itemView.tvTime.text = dataDateHM
                } else {
                    val sdfHM = SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_12, Locale.getDefault())
                    var dataDateHM = sdfHM.format(Date(dataList!![position].createdAt.toLong()))
                    itemView.tvTime.text = "$dataDateHM Hôm nay"
                }
            } catch (e: Exception) {
            }


            try {
                Glide.with(context!!).load(dataList!![position].data.icon).into(itemView.imgIcon)
            } catch (e: Exception) {
            }





            try {
                if (dataList!![position].status == "1") {
                    itemView.layoutRoot.alpha = 0.5f
                } else {
                    itemView.layoutRoot.alpha = 1.0f
                }
            } catch (e: Exception) {
            }

        }

    }


    inner class HeaderViewHolder2 : RecyclerView.ViewHolder {

        private var position: Int? = 0;

        constructor(itemView: View?) : super(itemView!!) {
            itemView.setOnClickListener {

            }


        }

        fun bindItem(position: Int) {
            this.position = position
            itemView.tvDate.text = dataList!![position].title

        }

    }

    interface NotificationCallback {
        fun onClickItemMenu(position: Int?, notification: Notification?);
        fun onClickItem(position: Int?, notification: Notification?);
        fun onDataChange(isShow: Boolean);
    }


}

