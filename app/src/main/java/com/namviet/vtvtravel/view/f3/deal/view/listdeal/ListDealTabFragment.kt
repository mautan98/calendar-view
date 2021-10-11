package com.namviet.vtvtravel.view.f3.deal.view.listdeal

import androidx.recyclerview.widget.GridLayoutManager
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentListDealTabBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f3.deal.adapter.GridDealAdapter
import kotlinx.android.synthetic.main.fragment_list_deal_tab.*

class ListDealTabFragment : BaseFragment<FragmentListDealTabBinding?>() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_list_deal_tab
    }

    override fun initView() {}
    override fun initData() {
        rclContent.adapter = GridDealAdapter()
        val gridLayoutManager = GridLayoutManager(mActivity, 2)
        rclContent.layoutManager = gridLayoutManager
    }
    override fun inject() {}
    override fun setClickListener() {
    }
    override fun setObserver() {}
}