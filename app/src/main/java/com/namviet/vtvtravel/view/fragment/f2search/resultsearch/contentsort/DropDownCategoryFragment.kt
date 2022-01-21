package com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2search.CategoryDropdownAdapter
import com.namviet.vtvtravel.adapter.f2search.SortParamAdapter
import com.namviet.vtvtravel.databinding.F3FragmentDropDownCategoryBinding
import com.namviet.vtvtravel.databinding.F3FragmentDropDownLocationBinding
import com.namviet.vtvtravel.databinding.F3FragmentSortFollowBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment

class DropDownCategoryFragment : BaseFragment<F3FragmentDropDownCategoryBinding?>() {
    private var categoryDropdownAdapter: CategoryDropdownAdapter? = null
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_drop_down_category
    }

    override fun initView() {}
    override fun initData() {
        categoryDropdownAdapter = CategoryDropdownAdapter();
        binding!!.rclSortParam.adapter = categoryDropdownAdapter
    }
    override fun inject() {}
    override fun setClickListener() {}
    override fun setObserver() {}
}