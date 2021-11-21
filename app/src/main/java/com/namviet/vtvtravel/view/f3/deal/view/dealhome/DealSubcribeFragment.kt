package com.namviet.vtvtravel.view.f3.deal.view.dealhome

import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentDealSubcribeBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.f2booking.DataHelpCenter
import com.namviet.vtvtravel.ultils.F2Util
import com.namviet.vtvtravel.view.f2.MyGiftActivity
import com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe.DealFilterAdapter
import com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe.DealSubscribeParentAdapter
import com.namviet.vtvtravel.view.f3.deal.event.ChangeToCenterTab
import com.namviet.vtvtravel.view.f3.deal.event.FinishDeal
import com.namviet.vtvtravel.view.f3.deal.model.dealfollow.DealFollow
import com.namviet.vtvtravel.view.f3.deal.model.dealfollow.DealFollowResponse
import com.namviet.vtvtravel.view.f3.deal.model.dealfollow.RewardStatus
import com.namviet.vtvtravel.view.f3.deal.viewmodel.DealViewModel
import kotlinx.android.synthetic.main.fragment_deal_home.*
import kotlinx.android.synthetic.main.fragment_deal_menu.*
import kotlinx.android.synthetic.main.fragment_deal_subcribe.*
import kotlinx.android.synthetic.main.fragment_deal_subcribe.btnMenu
import kotlinx.android.synthetic.main.fragment_deal_subcribe.viewFullScreen
import org.greenrobot.eventbus.EventBus
import java.util.*
import kotlin.collections.ArrayList

class DealSubcribeFragment : BaseFragment<FragmentDealSubcribeBinding?>(), Observer {
    private var dealViewModel: DealViewModel? = null
    private var listFilter = ArrayList<RewardStatus>();
    private var listDeal = ArrayList<DealFollow>()
    private var dealSubscribeParentAdapter: DealSubscribeParentAdapter? = null
    private var filter = ""
    private var location : Int? = null // 0: trong tab, 1 từ add fragment

    private var isFullScreen = false

    public fun setLocation(location : Int?){
        this.location = location
    }

    public fun setFullScreen(isFullScreen : Boolean){
        this.isFullScreen = isFullScreen
    }


    override fun getLayoutRes(): Int {
        return R.layout.fragment_deal_subcribe
    }

    override fun initView() {
        dealViewModel = DealViewModel()
        dealViewModel?.addObserver(this)
    }

    override fun initData() {

        if(isFullScreen){
            viewFullScreen.visibility = View.VISIBLE
        }else{
            viewFullScreen.visibility = View.GONE
        }

        listFilter.add(RewardStatus("", "Tất cả", true))
        listFilter.add(RewardStatus("2", "Đang săn", false))
        listFilter.add(RewardStatus("3", "Săn thành công", false))
        listFilter.add(RewardStatus("5", "Săn không thành công", false))

        rclFilterDeal.adapter = DealFilterAdapter(mActivity, listFilter) { position ->
            listDeal.clear()
            dealSubscribeParentAdapter?.notifyDataSetChanged()
            filter = listFilter[position].id
            dealViewModel?.getDealFollow(
                "https://core-testing.vtvtravel.vn/api/v1/deals/campaigns/follows",
                filter
            )
        }

        dealSubscribeParentAdapter = DealSubscribeParentAdapter(mActivity, listDeal) {
            if (it) {
                layoutNoData.visibility = View.VISIBLE
            } else {
                layoutNoData.visibility = View.GONE
            }

        }
        rclContent.adapter = dealSubscribeParentAdapter

        dealViewModel?.getDealFollow(
            "https://core-testing.vtvtravel.vn/api/v1/deals/campaigns/follows",
            filter
        )
    }

    override fun inject() {}
    override fun setClickListener() {
        btnMenu.setOnClickListener {
            var dealMenuDialog = DealMenuDialog()
            dealMenuDialog.setClickListener(object : DealMenuDialog.Click{
                override fun onClickRule() {
                    Toast.makeText(mActivity, "Thể lệ", Toast.LENGTH_SHORT).show();
                }

                override fun onClickSubscribeDeal() {

                }

                override fun onClickHelpCenter() {
                    val dataHelpCenter = Gson().fromJson(
                        F2Util.loadJSONFromAsset(mActivity, "helpcenter_pro"),
                        DataHelpCenter::class.java
                    )
                    MyGiftActivity.startScreen(
                        mActivity,
                        dataHelpCenter.itemMenus,
                        dataHelpCenter.name
                    )
                }

                override fun onClickGoDealHome() {
                    if(location == 0){
                        EventBus.getDefault().post(ChangeToCenterTab(1))
                    }else{
                        mActivity.finish()
                        EventBus.getDefault().post(FinishDeal())
                    }
                }

                override fun onClickGoHome() {
                    if(location == 0) {
                        mActivity.onBackPressed()
                    }else{
                        mActivity.finish()
                        EventBus.getDefault().post(FinishDeal())
                    }
                }

            })
            addFragment(dealMenuDialog)
        }
    }

    override fun setObserver() {}


    override fun update(observable: Observable?, o: Any?) {
        if (observable is DealViewModel && null != o) {
            when (o) {
                is DealFollowResponse -> {
                    listDeal.clear()
                    listDeal.addAll(o.data.dealFollows)
                    dealSubscribeParentAdapter?.notifyDataSetChanged()
                }
                is ErrorResponse -> {
                    try {
                        val responseError = o as ErrorResponse
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }


}