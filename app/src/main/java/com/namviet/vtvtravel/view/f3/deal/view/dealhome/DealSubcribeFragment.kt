package com.namviet.vtvtravel.view.f3.deal.view.dealhome

import android.view.View
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentDealSubcribeBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe.DealFilterAdapter
import com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe.DealSubscribeParentAdapter
import com.namviet.vtvtravel.view.f3.deal.model.dealfollow.DealFollow
import com.namviet.vtvtravel.view.f3.deal.model.dealfollow.DealFollowResponse
import com.namviet.vtvtravel.view.f3.deal.model.dealfollow.RewardStatus
import com.namviet.vtvtravel.view.f3.deal.viewmodel.DealViewModel
import kotlinx.android.synthetic.main.fragment_deal_subcribe.*
import java.util.*
import kotlin.collections.ArrayList

class DealSubcribeFragment : BaseFragment<FragmentDealSubcribeBinding?>(), Observer {
    private var dealViewModel: DealViewModel? = null
    private var listFilter = ArrayList<RewardStatus>();
    private var listDeal = ArrayList<DealFollow>()
    private var dealSubscribeParentAdapter: DealSubscribeParentAdapter? = null
    private var filter = ""
    override fun getLayoutRes(): Int {
        return R.layout.fragment_deal_subcribe
    }

    override fun initView() {
        dealViewModel = DealViewModel()
        dealViewModel?.addObserver(this)
    }

    override fun initData() {
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
            addFragment(DealMenuDialog())
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