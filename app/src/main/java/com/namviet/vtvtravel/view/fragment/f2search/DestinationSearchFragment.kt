package com.namviet.vtvtravel.view.fragment.f2search

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2search.TrendingAdapter
import com.namviet.vtvtravel.databinding.F2FragmentSearchDestinationBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.travelnews.Travel
import kotlinx.android.synthetic.main.f2_fragment_search_destination.*
import java.util.*

class DestinationSearchFragment : BaseFragment<F2FragmentSearchDestinationBinding>() {
    private var trendingAdapter: TrendingAdapter? = null
    private var travels: List<Travel>? = null
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_search_destination
    }

    override fun initView() {
    }

    public fun setList(travels : ArrayList<Travel>? ){
        this.travels = travels
        trendingAdapter = TrendingAdapter(travels, mActivity)
        rclContent.adapter = trendingAdapter
    }

    override fun initData() {

    }

    override fun inject() {
    }

    override fun setClickListener() {
    }

    override fun setObserver() {
    }

    override fun setScreenTitle() {
        super.setScreenTitle()
        setDataScreen("DESTINATION_SEARCH", "TÌM KIẾM ĐIỂM ĐẾN")
    }


}