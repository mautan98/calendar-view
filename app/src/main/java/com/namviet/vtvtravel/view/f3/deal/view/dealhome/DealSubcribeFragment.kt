package com.namviet.vtvtravel.view.f3.deal.view.dealhome

import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentDealSubcribeBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.response.f2wheel.WheelAreasResponse
import com.namviet.vtvtravel.response.f2wheel.WheelResultResponse
import com.namviet.vtvtravel.response.f2wheel.WheelRotateResponse
import com.namviet.vtvtravel.view.f3.deal.adapter.GridDealAdapter
import com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe.DealFilterAdapter
import com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe.DealSubscribeParentAdapter
import com.namviet.vtvtravel.view.f3.deal.model.dealfollow.DealFollowResponse
import com.namviet.vtvtravel.view.f3.deal.model.dealfollow.RewardStatus
import com.namviet.vtvtravel.view.f3.deal.viewmodel.DealViewModel
import com.namviet.vtvtravel.view.fragment.f2webview.LuckyWheelDialog
import com.namviet.vtvtravel.viewmodel.f2luckywheel.LuckyWheelViewModel
import kotlinx.android.synthetic.main.f2_fragment_detail_deal_webview.*
import kotlinx.android.synthetic.main.fragment_deal_subcribe.*
import java.util.*
import kotlin.collections.ArrayList

class DealSubcribeFragment : BaseFragment<FragmentDealSubcribeBinding?>(), Observer {
    private var dealViewModel : DealViewModel? = null
    private var listFilter  = ArrayList<RewardStatus>();
    override fun getLayoutRes(): Int {
        return R.layout.fragment_deal_subcribe
    }

    override fun initView() {
        dealViewModel = DealViewModel()
        dealViewModel?.addObserver(this)
    }
    override fun initData() {
        listFilter.add(RewardStatus("2", "Đang săn", false))
        listFilter.add(RewardStatus("3", "Săn thành công", false))
        listFilter.add(RewardStatus("5", "Săn không thành công", false))


        dealViewModel?.getDealFollow("https://core-testing.vtvtravel.vn/api/v1/deals/campaigns/follows")
    }
    override fun inject() {}
    override fun setClickListener() {}
    override fun setObserver() {}


    override fun update(observable: Observable?, o: Any?) {
        if (observable is DealViewModel && null != o) {
            when (o) {
                is DealFollowResponse -> {
                    rclContent.adapter = DealSubscribeParentAdapter(mActivity, o.data.dealFollows)
                    rclFilterDeal.adapter = DealFilterAdapter(mActivity, listFilter)
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