package com.namviet.vtvtravel.view.fragment.f2search.resultsearch

import android.app.Activity
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2search.NewsSearchAdapter
import com.namviet.vtvtravel.adapter.travelnews.SubTravelNewsAdapter
import com.namviet.vtvtravel.databinding.F2FragmentSearchNewsBinding
import com.namviet.vtvtravel.databinding.F2FragmentSearchNewsResultBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.travelnews.Travel
import com.namviet.vtvtravel.view.f2.TravelNewsActivity
import kotlinx.android.synthetic.main.f2_fragment_search_destination.*
import java.util.*

class ResultNewsSearchFragment : BaseFragment<F2FragmentSearchNewsResultBinding>() {

    private var subTravelNewsAdapter : SubTravelNewsAdapter? = null

    private var travels : ArrayList<Travel>?  = ArrayList();

    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_search_news_result
    }

    override fun initView() {

    }

    public fun setList(travels : ArrayList<Travel>? ){
        this.travels = travels
        subTravelNewsAdapter = SubTravelNewsAdapter(mActivity, travels, SubTravelNewsAdapter.ClickItem {travel  ->
            try {
                TravelNewsActivity.openScreenDetail(context as Activity?, TravelNewsActivity.OpenType.DETAIL, travel?.detail_link)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        rclContent.adapter = subTravelNewsAdapter
    }

    override fun initData() {
    }

    override fun inject() {
    }

    override fun setClickListener() {
    }

    override fun setObserver() {
    }

}