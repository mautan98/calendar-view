package com.namviet.vtvtravel.view.fragment.f2search

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2search.NewsSearchAdapter
import com.namviet.vtvtravel.databinding.F2FragmentSearchNewsBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.travelnews.Travel
import kotlinx.android.synthetic.main.f2_fragment_search_destination.*
import java.util.*

class NewsSearchFragment : BaseFragment<F2FragmentSearchNewsBinding>() {

    private var newsSearchAdapter : NewsSearchAdapter? = null

    private var travels : ArrayList<Travel>?  = ArrayList();

    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_search_news
    }

    override fun initView() {

    }

    public fun setList(travels : ArrayList<Travel>? ){
        this.travels = travels
        newsSearchAdapter = NewsSearchAdapter( travels, mActivity)
        rclContent.adapter = newsSearchAdapter
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
        setDataScreen("NEWS_SEARCH", "TÌM KIẾM TIN TỨC")
    }

}