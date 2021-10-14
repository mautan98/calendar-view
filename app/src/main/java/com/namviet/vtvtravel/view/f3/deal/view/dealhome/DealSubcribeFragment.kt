package com.namviet.vtvtravel.view.f3.deal.view.dealhome

import androidx.recyclerview.widget.GridLayoutManager
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentDealSubcribeBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f3.deal.adapter.GridDealAdapter
import com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe.DealFilterAdapter
import com.namviet.vtvtravel.view.f3.deal.adapter.dealsubscribe.DealSubscribeParentAdapter
import kotlinx.android.synthetic.main.fragment_deal_subcribe.*

class DealSubcribeFragment : BaseFragment<FragmentDealSubcribeBinding?>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_deal_subcribe
    }

    override fun initView() {}
    override fun initData() {
        rclContent.adapter = DealSubscribeParentAdapter(mActivity)
        rclFilterDeal.adapter = DealFilterAdapter(mActivity)
    }
    override fun inject() {}
    override fun setClickListener() {}
    override fun setObserver() {}
}