package com.namviet.vtvtravel.view.f3.deal.view.dealhome

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentDealMenuBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_deal_menu.*
import kotlinx.android.synthetic.main.fragment_page_deal_home.*

class DealMenuDialog : BaseFragment<FragmentDealMenuBinding?>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_deal_menu
    }

    override fun initView() {}
    override fun initData() {}
    override fun inject() {}
    override fun setClickListener() {
        btnClose.setOnClickListener {
            mActivity.onBackPressed()
        }

        btnRule.setOnClickListener {

        }

        btnDealSubcribe.setOnClickListener {

        }

        btnHelpCenter.setOnClickListener {

        }
        btnGoHome.setOnClickListener {

        }

        btnGoDealHome.setOnClickListener {

        }

        viewClose.setOnClickListener {
            mActivity.onBackPressed()
        }
    }
    override fun setObserver() {}
}