package com.namviet.vtvtravel.view.f3.deal.view.listdeal

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentListDealBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_list_deal.*

class ListDealFragment : BaseFragment<FragmentListDealBinding?>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_list_deal
    }

    override fun initView() {}
    override fun initData() {}
    override fun inject() {}
    override fun setClickListener() {
        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }
    }
    override fun setObserver() {}
}