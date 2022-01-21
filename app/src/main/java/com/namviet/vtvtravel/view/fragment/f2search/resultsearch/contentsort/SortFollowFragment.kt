package com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2search.SortParamAdapter
import com.namviet.vtvtravel.databinding.F3FragmentSortFollowBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment

class SortFollowFragment : BaseFragment<F3FragmentSortFollowBinding?>() {
    private var sortParamAdapter: SortParamAdapter? = null
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_sort_follow
    }

    override fun initView() {}
    override fun initData() {
        sortParamAdapter = SortParamAdapter();
        binding!!.rclSortParam.adapter = sortParamAdapter
    }
    override fun inject() {}
    override fun setClickListener() {}
    override fun setObserver() {}
}