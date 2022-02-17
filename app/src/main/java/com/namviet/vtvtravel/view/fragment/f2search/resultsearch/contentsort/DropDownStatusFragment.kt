package com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.F3FragmentDropDownStatusBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.f2search.Children

class DropDownStatusFragment : BaseFragment<F3FragmentDropDownStatusBinding?>() {
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_drop_down_status
    }

    override fun initView() {}
    override fun initData() {}
    override fun inject() {}
    override fun setClickListener() {}
    override fun setObserver() {}

    public interface Listener{
        fun onApply()
    }
}