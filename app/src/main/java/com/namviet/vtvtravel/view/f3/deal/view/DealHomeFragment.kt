package com.namviet.vtvtravel.view.f3.deal.view

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentDealHomeBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f3.deal.adapter.TabAdapter
import kotlinx.android.synthetic.main.fragment_page_deal_home.*

class DealHomeFragment : BaseFragment<FragmentDealHomeBinding?>() {
    private var tabAdapter: TabAdapter? = null
    override fun getLayoutRes(): Int {
        return R.layout.fragment_deal_home
    }

    override fun initView() {
        tabAdapter = TabAdapter(childFragmentManager, mActivity)
        var dealHomeChildFragment = DealHomeChildFragment()
        tabAdapter?.addFragment(dealHomeChildFragment, "")

        var dealHomeChildFragment1 = DealHomeChildFragment()
        tabAdapter?.addFragment(dealHomeChildFragment1, "")

        vpContent.adapter = tabAdapter
        vpContent.offscreenPageLimit = 10
    }
    override fun initData() {}
    override fun inject() {}
    override fun setClickListener() {}
    override fun setObserver() {}
}