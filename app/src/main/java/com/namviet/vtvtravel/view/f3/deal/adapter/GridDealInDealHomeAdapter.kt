package com.namviet.vtvtravel.view.f3.deal.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.ultils.DateUtltils
import com.namviet.vtvtravel.view.f3.deal.constant.DiscountDisplayType
import com.namviet.vtvtravel.view.f3.deal.constant.IsProcessingType
import com.namviet.vtvtravel.view.f3.deal.model.deal.Content
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse
import com.namviet.vtvtravel.view.f3.deal.view.dealdetail.DetailDealActivity
import kotlinx.android.synthetic.main.item_deal.view.*
import java.text.DecimalFormat


class GridDealInDealHomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val TYPE_ITEM = 0
    private var listDeal: ArrayList<Content>? = null
    private var context: Context? = null
    private var onDataChange: OnDataChange? = null;
    private var isDealByCampaign = false

    constructor()
    constructor(listDeal: ArrayList<Content>?, isDealByCampaign: Boolean) : super() {
        this.listDeal = listDeal
        this.isDealByCampaign = isDealByCampaign
    }

    constructor(listDeal: ArrayList<Content>?) : super() {
        this.listDeal = listDeal
    }

    public fun setOnDataChangeListener(onDataChange: OnDataChange?) {
        this.onDataChange = onDataChange
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
            if (listDeal!!.size > 0) {
                onDataChange?.onDataChange(false)
            } else {
                onDataChange?.onDataChange(true)
            }
            return listDeal!!.size

        } catch (e: Exception) {
            onDataChange?.onDataChange(true)
            return 0
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
            val content = listDeal!![position]

            if (content.name != null) {
                itemView.tvName.text = content.name
            } else {
                itemView.tvName.text = ""
            }

            itemView.setOnClickListener {
                DetailDealActivity.startScreen(
                    itemView.context,
                    content.id.toString(),
                    content.isCampaign
                )
            }

            itemView.tvUserTotal.text =
                F3SubDealAdapter.getHuntingUserCount(content.userHuntingCount)

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
                if (content.expireDate != null) {
                    itemView.tvExpiryDate.text =
                        "HSD: " + DateUtltils.timeToString18(content.expireDate)
                } else {
                    itemView.tvExpiryDate.text = ""
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }


            if (content.isProcessing != null) {
                if (content.isProcessing.equals(IsProcessingType.DANG_DIEN_RA_TYPE)) {
                    val timeStamp = content.endAt
                    val myCurrentTimeMillis = System.currentTimeMillis()
                    if (myCurrentTimeMillis > timeStamp) {
                        itemView.tvDayLeft.text = "C??n l???i 0 ng??y"
                    } else {
                        val distance = (timeStamp - myCurrentTimeMillis) / 1000
                        var days: String = ((distance / 86400).toInt()).toString() + " ng??y "
                        var hours: String = ((distance % 86400 / 3600).toInt()).toString()
                        var minutes: String = ((distance % 3600 / 60).toInt()).toString()
                        var seconds: String = ((distance % 3600 % 60).toInt()).toString()

                        if (days.length == 1) {
                            days = "0$days"
                        }

                        if (hours.length == 1) {
                            hours = "0$hours"
                        }

                        if (minutes.length == 1) {
                            minutes = "0$minutes"
                        }

                        if (seconds.length == 1) {
                            seconds = "0$seconds"
                        }

                        itemView.tvDayLeft.text = "C??n l???i $days$hours:$minutes:$seconds"
                    }
                    itemView.progress1.progress = getPercentProgress(content.beginAt, content.endAt)
                } else if (content.isProcessing.equals(IsProcessingType.SAP_DIEN_RA_TYPE)) {
                    val timeStamp = content.beginAt
                    val myCurrentTimeMillis = System.currentTimeMillis()
                    if (myCurrentTimeMillis > timeStamp) {
                        itemView.tvDayLeft.text = "B???t ?????u sau 0 ng??y 00:00:00"
                    } else {
                        val distance = (timeStamp - myCurrentTimeMillis) / 1000
                        var days: String = ((distance / 86400).toInt()).toString() + " ng??y "
                        var hours: String = ((distance % 86400 / 3600).toInt()).toString()
                        var minutes: String = ((distance % 3600 / 60).toInt()).toString()
                        var seconds: String = ((distance % 3600 % 60).toInt()).toString()

                        if (days.length == 1) {
                            days = "0$days"
                        }

                        if (hours.length == 1) {
                            hours = "0$hours"
                        }

                        if (minutes.length == 1) {
                            minutes = "0$minutes"
                        }

                        if (seconds.length == 1) {
                            seconds = "0$seconds"
                        }

                        itemView.tvDayLeft.text = "B???t ?????u sau $days$hours:$minutes:$seconds"
                    }
                    itemView.progress1.progress = 100
                } else if (content.isProcessing.equals(IsProcessingType.KET_THUC_TYPE)) {
                    if (isDealByCampaign) {

                        if(content.rewardStatus != null && content.rewardStatus.toInt() == 6){
                            itemView.layoutIsAccumulate.visibility = View.VISIBLE
                        }else{
                            itemView.layoutIsAccumulate.visibility = View.GONE
                        }

                        try {
                            var account = MyApplication.getInstance().account
                            if(content.dealScoresList != null && content.dealScoresList.size > 0 && content.dealScoresList[0].mobile == account.mobile){
                                itemView.layoutIsWinner.visibility = View.VISIBLE
                                itemView.layoutProgressBar.visibility = View.GONE
                                itemView.layoutIsHuntingUser.visibility = View.GONE
                            }else{
                                if(content.isUserHunting) {
                                    itemView.layoutIsWinner.visibility = View.GONE
                                    itemView.layoutProgressBar.visibility = View.VISIBLE
                                    itemView.progress1.progress = 0
                                    itemView.tvDayLeft.text = "S??n kh??ng th??nh c??ng"
                                    itemView.layoutIsHuntingUser.visibility = View.GONE
                                }else{
                                    itemView.layoutIsWinner.visibility = View.GONE
                                    itemView.layoutProgressBar.visibility = View.VISIBLE
                                    itemView.progress1.progress = 0
                                    itemView.tvDayLeft.text = "???? k???t th??c"
                                }

                            }
                        } catch (e: Exception) {
                            itemView.layoutIsWinner.visibility = View.GONE
                            itemView.layoutProgressBar.visibility = View.VISIBLE
                            itemView.progress1.progress = 0
                            itemView.tvDayLeft.text = "???? k???t th??c"
                        }


                    } else {
                        itemView.tvDayLeft.text = "???? k???t th??c"
                        itemView.progress1.progress = 0
                    }
                }
            } else {
                itemView.tvDayLeft.text = "???? k???t th??c"
                itemView.progress1.progress = 0
            }



            try {
                if (content.priceAfterPromo != null) {
                    itemView.tvDisplayPrice.text =
                        convertPrice(content.priceAfterPromo.toString()) + " ??"
                    itemView.tvDisplayPrice.visibility = View.VISIBLE
                } else {
                    itemView.tvDisplayPrice.text = ""
                    itemView.tvDisplayPrice.visibility = View.GONE
                }
            } catch (e: Exception) {
                try {
                    itemView.tvDisplayPrice.text = content.priceAfterPromo.toString() + " ??"
                    itemView.tvDisplayPrice.visibility = View.VISIBLE
                } catch (ex: java.lang.Exception) {
                    itemView.tvDisplayPrice.visibility = View.GONE
                    ex.printStackTrace()
                }
                e.printStackTrace()
            }


            try {
                if (content.priceBeforePromo != null) {
                    itemView.tvOriginPrice.text =
                        convertPrice(content.priceBeforePromo.toString()) + " ??"
                    itemView.tvOriginPrice.visibility = View.VISIBLE
                } else {
                    itemView.tvOriginPrice.text = ""
                    itemView.tvOriginPrice.visibility = View.GONE
                }

            } catch (e: java.lang.Exception) {
                try {
                    itemView.tvOriginPrice.visibility = View.VISIBLE
                    itemView.tvOriginPrice.text = content.priceBeforePromo.toString() + " ??"
                } catch (ex: Exception) {
                    itemView.tvOriginPrice.visibility = View.GONE
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

            itemView.tvTotalMustHaveChild.text = "T??ch l??y t??? " + content.totalDeal + " CTKM"
            try {
                Glide.with(itemView.context!!).load(content.avatarUri).into(itemView.imgAvatar)
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


    public interface OnDataChange {
        fun onDataChange(isShow: Boolean)
    }

    companion object {
        fun getPercentProgress(startTime: Long, endTime: Long): Int {
            return try {
                val myCurrentTimeMillis = System.currentTimeMillis()
                if (myCurrentTimeMillis < startTime) {
                    return 100
                }
                if (myCurrentTimeMillis > endTime) {
                    0
                } else {
                    100 - ((myCurrentTimeMillis - startTime).toDouble() / (endTime - startTime).toDouble() * 100).toInt()
                }
            } catch (e: Exception) {
                50
            }
        }
    }

}

