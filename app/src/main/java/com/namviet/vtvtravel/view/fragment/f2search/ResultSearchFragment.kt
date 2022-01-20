package com.namviet.vtvtravel.view.fragment.f2search

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.VideoView
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2search.CategorySortedAdapter
import com.namviet.vtvtravel.adapter.f2search.SearchMainPageAdapter
import com.namviet.vtvtravel.adapter.f2search.SortAdapter
import com.namviet.vtvtravel.databinding.F2FragmentResultSearchBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.Video
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.model.travelnews.Travel
import com.namviet.vtvtravel.response.f2searchmain.MainResultSearchResponse
import com.namviet.vtvtravel.response.f2searchmain.SearchSuggestionResponse
import com.namviet.vtvtravel.response.f2searchmain.result.ResultSearch
import com.namviet.vtvtravel.response.f2searchmain.result.ResultVideoSearch
import com.namviet.vtvtravel.response.f2searchmain.result.SearchType
import com.namviet.vtvtravel.tracking.TrackingAnalytic
import com.namviet.vtvtravel.ultils.highlight.HighLightController
import com.namviet.vtvtravel.ultils.highlight.SearchHighLightText
import com.namviet.vtvtravel.view.f3.search.view.SearchSuggestionFragment
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
    private var sortAdapter : SortAdapter? = null
    private var categorySortedAdapter : CategorySortedAdapter? = null

    private var keyword: String? = null
    private var regionId: String? = null
    private var categoryId: String? = null

    private var loadMoreLink: String? = ""


    private var searchViewModel: SearchResultViewModel? = null

    constructor(keyword: String?, regionId: String?, categoryId: String?) {
        this.keyword = keyword
        this.regionId = regionId
        this.categoryId = categoryId
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
        btnFilter.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }
        edtSearch.text = keyword

        sortAdapter = SortAdapter();
        binding!!.rclSort.adapter = sortAdapter

        categorySortedAdapter = CategorySortedAdapter();
        binding!!.rclCategorySorted.adapter = categorySortedAdapter
    }

    override fun inject() {

    }

    override fun setClickListener() {
        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }

        edtSearch.setOnClickListener {
//            addFragment(SearchSuggestionFragment(object :SearchSuggestionFragment.ClickSuggestion{
//
//                override fun onClickSuggestion(searchKeywordSuggestion: SearchSuggestionResponse.Data.Item?, location: Location?) {
//
//                }
//            }, keyword))
        }

        btnFilter.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        btnCloseFilter.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.END)
        }

        btnChooseCity.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.chooseRegionFrame, ChooseRegionFragment())?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.commit()
        }

        layoutExpand.setOnClickListener {
            layoutExpand.visibility = View.GONE
        }
    }


    override fun setObserver() {

    }

    public fun searchAll(type : String?) {
        searchViewModel?.searchAll(type, keyword, regionId, type, categoryId)
    }

    public fun searchAllWithLink(link : String?, type : String?) {
        searchViewModel?.searchAllWithFullLink(link, type)
    }

    public fun searchAllVideo(type : String?) {
        searchViewModel?.searchAllVideo(type, keyword, regionId, type, categoryId)
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
        vpContent.offscreenPageLimit = 3
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
            try {
                when (o) {

                    is ResultSearch -> {
                        when (o.type) {
                            SearchType.NEWS -> {
                                newsSearchFragment?.setList(o.data.items as ArrayList<Travel>?, o.data.more_link, o.data.total, keyword!!, o.data.approximately)
                            }

                            SearchType.DESTINATION -> {
                                destinationSearchFragment?.setList(o.data.items as ArrayList<Travel>?, o.data.more_link, o.data.total, keyword!!, o.data.approximately)
                            }


                        }

                    }

                    is ResultVideoSearch -> {
                        resultVideosSearchFragment?.setList(o.data.items as ArrayList<Video>?, o.data.more_link, o.data.total, keyword!!, o.data.approximately)
                    }

                    is ErrorResponse -> {
                        val responseError = o
                        try { //                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                        } catch (e: Exception) {
                        }
                    }
                }
            } catch (e: Exception) {
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

    public fun setHighLightedText(tv: TextView, textToHighlight: String) {
        var iHighLightText  = SearchHighLightText()
        var highLightController =  HighLightController(iHighLightText)
        highLightController.highLight(context!!, tv, textToHighlight)
    }
}