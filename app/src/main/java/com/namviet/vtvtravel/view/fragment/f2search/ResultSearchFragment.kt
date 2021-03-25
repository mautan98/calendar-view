package com.namviet.vtvtravel.view.fragment.f2search

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.VideoView
import com.google.android.material.tabs.TabLayout
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2search.SearchMainPageAdapter
import com.namviet.vtvtravel.databinding.F2FragmentResultSearchBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.Video
import com.namviet.vtvtravel.model.travelnews.Travel
import com.namviet.vtvtravel.response.f2searchmain.MainResultSearchResponse
import com.namviet.vtvtravel.response.f2searchmain.result.ResultSearch
import com.namviet.vtvtravel.response.f2searchmain.result.ResultVideoSearch
import com.namviet.vtvtravel.response.f2searchmain.result.SearchType
import com.namviet.vtvtravel.tracking.TrackingAnalytic
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.ResultDestinationSearchFragment
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.ResultNewsSearchFragment
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.ResultVideosSearchFragment
import com.namviet.vtvtravel.viewmodel.f2search.SearchResultViewModel
import kotlinx.android.synthetic.main.f2_fragment_result_search.*
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("ValidFragment")
class ResultSearchFragment : BaseFragment<F2FragmentResultSearchBinding>, Observer {



    //viewpager

    private var mainAdapter: SearchMainPageAdapter? = null
    private var destinationSearchFragment: ResultDestinationSearchFragment? = null
    private var newsSearchFragment: ResultNewsSearchFragment? = null
    private var resultVideosSearchFragment: ResultVideosSearchFragment? = null
    //

    private var keyword: String? = null
    private var regionId: String? = null

    private var loadMoreLink: String? = ""


    private var searchViewModel: SearchResultViewModel? = null

    constructor(keyword: String?, regionId: String?) {
        this.keyword = keyword
        this.regionId = regionId;
    }

    constructor()

    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_result_search
    }

    override fun initView() {
        searchViewModel = SearchResultViewModel()
        searchViewModel?.addObserver(this)
    }

    override fun initData() {
        genViewPagerSearchResult()
//        searchViewModel?.getPreResultSearch(keyword!!, regionId, false)

    }

    override fun inject() {

    }

    override fun setClickListener() {
        imgBack.setOnClickListener {
            mActivity.onBackPressed()
        }
    }


    override fun setObserver() {

    }

    public fun searchAll(type : String?) {
        searchViewModel?.searchAll(type, keyword, regionId, type)
    }

    public fun searchAllWithLink(link : String?, type : String?) {
        searchViewModel?.searchAllWithFullLink(link, type)
    }

    public fun searchAllVideo(type : String?) {
        searchViewModel?.searchAllVideo(type, keyword, regionId, type)
    }

    public fun searchAllVideoWithLink(link : String?, type : String?) {
        searchViewModel?.searchAllVideoWithFullLink(link, type)
    }

    public fun getMoreData() {
//        searchViewModel?.getPreResultSearch(loadMoreLink!!, true)
//        loadMoreLink = ""
    }

    private fun genViewPagerSearchResult() {

        mainAdapter = SearchMainPageAdapter(childFragmentManager)
        destinationSearchFragment = ResultDestinationSearchFragment(this)
        mainAdapter!!.addFragment(destinationSearchFragment, "destinationSearchFragment")
        newsSearchFragment = ResultNewsSearchFragment(this)
        mainAdapter!!.addFragment(newsSearchFragment, "newsSearchFragment")
        resultVideosSearchFragment = ResultVideosSearchFragment(this)
        mainAdapter!!.addFragment(resultVideosSearchFragment, "resultVideosSearchFragment")
        vpContent.adapter = mainAdapter
        tabLayout.setupWithViewPager(vpContent)


        val tabHome = LayoutInflater.from(mActivity).inflate(R.layout.f2_layout_main_tab_search, null)
        val tvHome = tabHome.findViewById<TextView>(R.id.tvTitle)
        tvHome.text = "Điểm đến"
        tvHome.setTextColor(Color.parseColor("#00918D"))
        val view = tabHome.findViewById<View>(R.id.indicator)
        view.visibility = View.VISIBLE
        tabLayout.getTabAt(0)?.customView = tabHome


        val tabNews = LayoutInflater.from(mActivity).inflate(R.layout.f2_layout_main_tab_search, null)
        val tvNews = tabNews.findViewById<TextView>(R.id.tvTitle)
        tvNews.text = "Tin tức"
        tvNews.setTextColor(Color.parseColor("#8A8A8A"))
        val viewNews = tabNews.findViewById<View>(R.id.indicator)
        viewNews.visibility = View.INVISIBLE
        tabLayout.getTabAt(1)?.customView = tabNews


        val tabVideos = LayoutInflater.from(mActivity).inflate(R.layout.f2_layout_main_tab_search, null)
        val tvVideos = tabVideos.findViewById<TextView>(R.id.tvTitle)
        tvVideos.text = "Video"
        tvVideos.setTextColor(Color.parseColor("#8A8A8A"))
        val viewVideos = tabVideos.findViewById<View>(R.id.indicator)
        viewVideos.visibility = View.INVISIBLE
        tabLayout.getTabAt(2)?.customView = tabVideos

        tabLayout.addOnTabSelectedListener(onTabSelectedListener)

    }

    override fun update(observable: Observable?, o: Any?) {
        if (observable is SearchResultViewModel && null != o) {
            when (o) {
                is MainResultSearchResponse -> {
//                    loadMoreLink = o.data.more_link;
//                    destinationSearchFragment?.setList(o.data.items as ArrayList<Travel>?, o.isLoadMore)
//                    newsSearchFragment?.setList(o.data.items_news as ArrayList<Travel>?, o.isLoadMore)
//
//                    tvCountResult.text = "Có " + (o.data.items.size + o.data.items_news.size) + " kết quả phù hợp với " + " \"" + keyword + "\"";
                }

                is ResultSearch -> {
                    when (o.type) {
                        SearchType.NEWS -> {
                            newsSearchFragment?.setList(o.data.items as ArrayList<Travel>?, o.data.more_link)
                        }

                        SearchType.DESTINATION -> {
                            destinationSearchFragment?.setList(o.data.items as ArrayList<Travel>?, o.data.more_link)
                        }


                    }

                }

                is ResultVideoSearch -> {
                    resultVideosSearchFragment?.setList(o.data.items as ArrayList<Video>?, o.data.more_link)
                }

                is ErrorResponse -> {
                    val responseError = o
                    try { //                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }

    private val onTabSelectedListener: TabLayout.OnTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            val c = tab.position
            mainAdapter!!.setOnSelectView(tabLayout, c)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            val c = tab.position
            mainAdapter!!.setUnSelectView(tabLayout, c)
        }

        override fun onTabReselected(tab: TabLayout.Tab) {}
    }

    override fun setScreenTitle() {
        super.setScreenTitle()
        setDataScreen(TrackingAnalytic.ScreenCode.SEARCH_RESULT, TrackingAnalytic.ScreenTitle.SEARCH_RESULT)
    }
}