package com.namviet.vtvtravel.view.f3.deal.view

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentPageDealHomeBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f3.deal.adapter.TabAdapter
import kotlinx.android.synthetic.main.fragment_page_deal_home.*

class PageDealHomeFragment : BaseFragment<FragmentPageDealHomeBinding?>() {
    private var tabAdapter: TabAdapter? = null
    override fun getLayoutRes(): Int {
        return R.layout.fragment_page_deal_home
    }

    override fun initView() {
        tabAdapter = TabAdapter(childFragmentManager, mActivity)
        var myGiftFragment = MyGiftFragment()
        tabAdapter?.addFragment(myGiftFragment, "")

        var dealHomeFragment = DealHomeFragment()
        tabAdapter?.addFragment(dealHomeFragment, "")

        var dealSubcribeFragment = DealSubcribeFragment()
        tabAdapter?.addFragment(dealSubcribeFragment, "")

        vpContent.adapter = tabAdapter
        vpContent.offscreenPageLimit = 10

    }
    override fun initData() {}
    override fun inject() {}
    override fun setClickListener() {}
    override fun setObserver() {}
}