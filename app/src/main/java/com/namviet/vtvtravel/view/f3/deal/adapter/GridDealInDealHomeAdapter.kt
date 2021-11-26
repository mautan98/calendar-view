package com.namviet.vtvtravel.view.f3.deal.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.ultils.DateUtltils
import com.namviet.vtvtravel.view.f3.deal.constant.DiscountDisplayType
import com.namviet.vtvtravel.view.f3.deal.constant.IsProcessingType
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse
import com.namviet.vtvtravel.view.f3.deal.view.dealdetail.DetailDealActivity
import kotlinx.android.synthetic.main.item_deal.view.*
import java.text.DecimalFormat


class GridDealInDealHomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val TYPE_ITEM = 0
    private var dealResponse: DealResponse? = null
    private var context: Context? = null
    private var url: String? =
        "https://core-testing.vtvtravel.vn/api/v1/deals/campaigns/details?id=105&isProcessing=1"

    constructor()
    constructor(dealResponse: DealResponse?) : super() {
        this.dealResponse = dealResponse
    }


    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View? = null;
        v = LayoutInflater.from(parent.context).inflate(R.layout.item_deal, parent, false)
        return HeaderViewHolder(v)

    }

    override fun getItemCount(): Int {
        return try {
            dealResponse!!.data.content.size
        } catch (e: Exception) {
            0
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


        constructor(itemView: View?) : super(itemView!!) {

        }

        fun bindItem(position: Int) {
            val content = dealResponse!!.data.content[position]
            itemView.tvName.text = content.name
            itemView.setOnClickListener {
                DetailDealActivity.startScreen(itemView.context, content.id.toString(),content.isCampaign)
            }

            itemView.tvUserTotal.text = F3SubDealAdapter.getHuntingUserCount(content.userHuntingCount) + "+"

            if (content.isCampaign) {
                itemView.tvTotalMustHaveChild.visibility = View.VISIBLE
            } else {
                itemView.tvTotalMustHaveChild.visibility = View.GONE
            }


            if (content.isUserHunting) {
                itemView.layoutIsHuntingUser.visibility = View.VISIBLE
            } else {
                itemView.layoutIsHuntingUser.visibility = View.GONE
            }

            try {
                itemView.tvExpiryDate.text =
                    "HSD: " + DateUtltils.timeToString18(content.expireDate)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }


            if (content.isProcessing != null) {
                if (content.isProcessing.equals(IsProcessingType.DANG_DIEN_RA_TYPE)) {
                    val timeStamp = content.endAt
                    val myCurrentTimeMillis = System.currentTimeMillis()
                    if (myCurrentTimeMillis > timeStamp) {
                        itemView.tvDayLeft.text = "Còn lại 0 ngày"
                    } else {
                        val distance = (timeStamp - myCurrentTimeMillis) / 1000
                        val days: String = ((distance / 86400).toInt()).toString() + " ngày "
                        val hours: String = ((distance % 86400 / 3600).toInt()).toString()
                        val minutes: String = ((distance % 3600 / 60).toInt()).toString()
                        val seconds: String = ((distance % 3600 % 60).toInt()).toString()
                        itemView.tvDayLeft.text = "Còn lại $days$hours:$minutes:$seconds"
                    }
                    itemView.progress1.progress = getPercentProgress(content.beginAt, content.endAt)
                } else if (content.isProcessing.equals(IsProcessingType.SAP_DIEN_RA_TYPE)) {
                    val timeStamp = content.beginAt
                    val myCurrentTimeMillis = System.currentTimeMillis()
                    if (myCurrentTimeMillis > timeStamp) {
                        itemView.tvDayLeft.text = "Bắt đầu sau 0 ngày"
                    } else {
                        val distance = (timeStamp - myCurrentTimeMillis) / 1000
                        val days: String = ((distance / 86400).toInt()).toString() + " ngày "
                        val hours: String = ((distance % 86400 / 3600).toInt()).toString()
                        val minutes: String = ((distance % 3600 / 60).toInt()).toString()
                        val seconds: String = ((distance % 3600 % 60).toInt()).toString()
                        itemView.tvDayLeft.text = "Bắt đầu sau $days$hours:$minutes:$seconds"
                    }
                    itemView.progress1.progress = 100
                } else {
                    itemView.tvDayLeft.text = "Đã kết thúc"
                    itemView.progress1.progress = 0
                }
            } else {
                itemView.tvDayLeft.text = "Đã kết thúc"
                itemView.progress1.progress = 0
            }



            try {
                itemView.tvDisplayPrice.text =
                    convertPrice(content.priceAfterPromo.toString()) + " đ"
            } catch (e: Exception) {
                try {
                    itemView.tvDisplayPrice.text = content.priceAfterPromo.toString() + " đ"
                } catch (ex: java.lang.Exception) {
                    ex.printStackTrace()
                }
                e.printStackTrace()
            }


            try {
                itemView.tvOriginPrice.text =
                    convertPrice(content.priceBeforePromo.toString()) + " đ"
            } catch (e: java.lang.Exception) {
                try {
                    itemView.tvOriginPrice.text = content.priceBeforePromo.toString() + " đ"
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
                e.printStackTrace()
            }


            itemView.tvOriginPrice.paintFlags =
                itemView.tvOriginPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG


            try {
                if (content.displayType != null) {
                    if (content.displayType == DiscountDisplayType.PERCENT_TYPE) {
                        itemView.tvPercent.text = content.valuePromotion.toString() + "%"
                        itemView.layoutDiscount.visibility = View.VISIBLE
                    } else {
                        itemView.layoutDiscount.visibility = View.GONE
                    }

                } else {
                    itemView.layoutDiscount.visibility = View.GONE
                }
            } catch (e: Exception) {
                itemView.layoutDiscount.visibility = View.GONE
                e.printStackTrace()
            }

            itemView.tvTotalMustHaveChild.text = "Tích lũy từ " + content.totalDeal+ " CTKM"
            try {
                Glide.with(context!!).load(content.avatarUri).into(itemView.imgAvatar)
            } catch (e: Exception) {
            }
        }

    }

    fun convertPrice(string: String): String? {
        return try {
            val df = DecimalFormat("###,###,###")
            df.format(string.toDouble())
        } catch (e: java.lang.Exception) {
            string
        }
    }

    private fun getPercentProgress(startTime: Long, endTime: Long): Int {
        return try {
            val myCurrentTimeMillis = System.currentTimeMillis()
            if (myCurrentTimeMillis < startTime) {
                return 0
            }
            if (myCurrentTimeMillis > endTime) {
                100
            } else {
                100 - ((myCurrentTimeMillis - startTime).toDouble() / (endTime - startTime).toDouble() * 100).toInt()
            }
        } catch (e: Exception) {
            50
        }
    }

}

