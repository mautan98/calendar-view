package com.namviet.vtvtravel.view.fragment.f2search

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyboardShortcutInfo
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.baseapp.utils.KeyboardUtils
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2search.*
import com.namviet.vtvtravel.databinding.F2FragmentResultSearchBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.Video
import com.namviet.vtvtravel.model.f2event.OnTouchRCLLocation
import com.namviet.vtvtravel.model.f2search.Children
import com.namviet.vtvtravel.model.f2search.Content
import com.namviet.vtvtravel.model.f2search.SortAndFilter
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.model.travelnews.Travel
import com.namviet.vtvtravel.response.f2searchmain.SearchSuggestionResponse
import com.namviet.vtvtravel.response.f2searchmain.result.ResultSearch
import com.namviet.vtvtravel.response.f2searchmain.result.ResultVideoSearch
import com.namviet.vtvtravel.response.f2searchmain.result.SearchType
import com.namviet.vtvtravel.tracking.TrackingAnalytic
import com.namviet.vtvtravel.ultils.F2Util
import com.namviet.vtvtravel.ultils.highlight.HighLightController
import com.namviet.vtvtravel.ultils.highlight.SearchHighLightText
import com.namviet.vtvtravel.view.f3.search.view.SearchSuggestionFragment
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.ResultDestinationSearchFragment
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.ResultNewsSearchFragment
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.ResultVideosSearchFragment
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.SlideMenuSearchFragment
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort.DropDownCategoryFragment
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort.DropDownLocationFragment
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort.DropDownStatusFragment
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort.SortFollowFragment
import com.namviet.vtvtravel.viewmodel.f2search.SearchResultViewModel
import kotlinx.android.synthetic.main.f2_fragment_result_search.*
import kotlinx.android.synthetic.main.f2_fragment_result_search.btnBack
import kotlinx.android.synthetic.main.f2_fragment_result_search.tabLayout
import kotlinx.android.synthetic.main.f2_fragment_result_search.vpContent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("ValidFragment")
class ResultSearchFragment : BaseFragment<F2FragmentResultSearchBinding>, Observer,
    SearchSuggestionFragment.SearchSuggestionCallback {


    //viewpager

    private var mainAdapter: SearchMainPageAdapter? = null
    private var destinationSearchFragment: ResultDestinationSearchFragment? = null
    private var newsSearchFragment: ResultNewsSearchFragment? = null
    private var resultVideosSearchFragment: ResultVideosSearchFragment? = null


    //
    private var sortAdapter: SortAdapter? = null
    private var categorySortedAdapter: CategorySortedAdapter? = null


    private var keyword: String? = null
    private var regionId: String? = null
    private var location: Location? = null;
    private var categoryId: String? = null
    private var district_id: String? = ""
    private var ward_id: String? = ""
    private var open: Boolean? = null
    private var sortPlace: String? = ""
    private var sortNews: String? = ""
    private var sortVideo: String? = ""
    private var content_type: String? = ""

    private var loadMoreLink: String? = ""


    private var searchViewModel: SearchResultViewModel? = null

    private var sortAndFilter: SortAndFilter? = null

    private var locationMain: ArrayList<Location>? = null
    private var type = ArrayList<String>()

    constructor(
        keyword: String?,
        regionId: String?,
        location: Location?,
        categoryId: String?,
        locationMain: ArrayList<Location>?
    ) {
        this.keyword = keyword
        this.regionId = regionId
        this.categoryId = categoryId
        this.locationMain = locationMain
        this.location = location
    }

    constructor()

    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_result_search
    }

    override fun initView() {
        searchViewModel = SearchResultViewModel()
        searchViewModel?.addObserver(this)

        type.clear()
        type.add("place")
    }

    override fun initData() {
        genViewPagerSearchResult()
//        searchViewModel?.getPreResultSearch(keyword!!, regionId, false)
        btnFilter.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }
        edtSearch.text = keyword

        createSortData()

        initSlideMenu()


    }


    override fun inject() {

    }

    private fun initSlideMenu() {

        binding!!.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerOpened(drawerView: View) {
                createMenuFragment()
                if (layoutExpand.visibility == View.VISIBLE) {
                    hideMenuAnim()
                }
            }

            override fun onDrawerClosed(drawerView: View) {
                slideMenuSearchFragment?.deleteFragment()
                KeyboardUtils.hideKeyboard1(mActivity)
            }

            override fun onDrawerStateChanged(newState: Int) {

            }

        })
    }

    private var slideMenuSearchFragment: SlideMenuSearchFragment? = null

    private fun createMenuFragment() {
        slideMenuSearchFragment = SlideMenuSearchFragment();
        slideMenuSearchFragment?.setData(sortAndFilter, object : SlideMenuSearchFragment.Listener {
            override fun onApply(sortAndFilter: SortAndFilter?) {
                this@ResultSearchFragment.sortAndFilter!!.sortHeader[2].children.clear()
                this@ResultSearchFragment.sortAndFilter!!.sortHeader[2].children.addAll(
                    sortAndFilter!!.sortHeader[2].children
                )
                categorySortedAdapter!!.notifyDataSetChanged()

                this@ResultSearchFragment.sortAndFilter!!.sortHeader[3].content.isOpen =
                    sortAndFilter.sortHeader[3].content.isOpen

                this@ResultSearchFragment.sortAndFilter!!.sortHeader[1].content =
                    sortAndFilter.sortHeader[1].content

                this@ResultSearchFragment.sortAndFilter!!.sortHeader[0].childrenPlace.clear()
                this@ResultSearchFragment.sortAndFilter!!.sortHeader[0].childrenPlace.addAll(
                    sortAndFilter!!.sortHeader[0].childrenPlace
                )

                this@ResultSearchFragment.sortAndFilter!!.sortHeader[0].childrenNews.clear()
                this@ResultSearchFragment.sortAndFilter!!.sortHeader[0].childrenNews.addAll(
                    sortAndFilter!!.sortHeader[0].childrenNews
                )

                this@ResultSearchFragment.sortAndFilter!!.sortHeader[0].childrenVideo.clear()
                this@ResultSearchFragment.sortAndFilter!!.sortHeader[0].childrenVideo.addAll(
                    sortAndFilter!!.sortHeader[0].childrenVideo
                )

                sortAdapter?.notifyDataSetChanged()
                binding!!.drawerLayout.closeDrawer(GravityCompat.END)
                getParamAndSearch()
            }

            override fun onClose() {
                binding!!.drawerLayout.closeDrawer(GravityCompat.END)
            }

        }, locationMain!!)


        fragmentManager!!.beginTransaction()
            .replace(R.id.chooseRegionFrame, slideMenuSearchFragment!!).commit()
    }

    private fun createSortData() {
        sortAndFilter = Gson().fromJson(
            F2Util.loadJSONFromAsset(mActivity, "filter_and_sort_in_search"),
            SortAndFilter::class.java
        )

        sortAdapter =
            SortAdapter(mActivity, sortAndFilter!!.sortHeader, object : SortAdapter.ClickItem {
                override fun onClickItem(position: Int) {
                    when (position) {
                        0 -> {
                            var sortFollowFragment = SortFollowFragment()
                            sortFollowFragment.setData(
                                if(type[0] == "place") sortAndFilter!!.sortHeader[0].childrenPlace else if(type[0] == "news") sortAndFilter!!.sortHeader[0].childrenNews else  sortAndFilter!!.sortHeader[0].childrenVideo,
                                object : SortFollowFragment.Listener {
                                    override fun onApply(listChild: ArrayList<Children>?) {
                                        if(type[0] == "place") {
                                            sortAndFilter!!.sortHeader[0].childrenPlace = listChild
                                            hideMenuAnim()
                                            sortAdapter?.notifyDataSetChanged()
                                            getParamAndSearch()
                                        }else if (type[0] == "news"){
                                            sortAndFilter!!.sortHeader[0].childrenNews = listChild
                                            hideMenuAnim()
                                            sortAdapter?.notifyDataSetChanged()
                                            getParamAndSearch()
                                        }else{
                                            sortAndFilter!!.sortHeader[0].childrenVideo = listChild
                                            hideMenuAnim()
                                            sortAdapter?.notifyDataSetChanged()
                                            getParamAndSearch()
                                        }

                                    }

                                })
                            fragmentManager!!.beginTransaction()
                                .replace(R.id.sortFrame, sortFollowFragment).commit()
                        }

                        1 -> {
                            var dropDownLocationFragment = DropDownLocationFragment()
                            dropDownLocationFragment.setData(
                                sortAndFilter!!.sortHeader[1].content,
                                locationMain,
                                object : DropDownLocationFragment.Callback {
                                    override fun onApply(content: Content?) {
                                        hideMenuAnim()
                                        sortAndFilter!!.sortHeader[1].content = content
                                        sortAdapter?.notifyDataSetChanged()
                                        getParamAndSearch()

                                    }

                                })
                            fragmentManager!!.beginTransaction()
                                .replace(R.id.sortFrame, dropDownLocationFragment).commit()
                        }

                        2 -> {
                            var dropDownCategoryFragment = DropDownCategoryFragment()
                            dropDownCategoryFragment.setData(
                                sortAndFilter!!.sortHeader[2].children,
                                object : DropDownCategoryFragment.Listener {
                                    override fun onApply(listChild: ArrayList<Children>?) {
                                        sortAndFilter!!.sortHeader[2].children.clear()
                                        sortAndFilter!!.sortHeader[2].children.addAll(listChild!!)
                                        hideMenuAnim()
                                        categorySortedAdapter?.notifyDataSetChanged()
                                        sortAdapter?.notifyDataSetChanged()
                                        getParamAndSearch()
                                    }

                                })
                            fragmentManager!!.beginTransaction()
                                .replace(R.id.sortFrame, dropDownCategoryFragment).commit()
                        }

                        3 -> {
                            var dropDownStatusFragment = DropDownStatusFragment()
                            dropDownStatusFragment.setData(
                                sortAndFilter!!.sortHeader[3].content.isOpen,
                                object : DropDownStatusFragment.Listener {
                                    override fun onApply(isOpen: Boolean?) {
                                        sortAndFilter!!.sortHeader[3].content.isOpen = isOpen
                                        hideMenuAnim()
                                        sortAdapter?.notifyDataSetChanged()
                                        getParamAndSearch()

                                    }

                                })
                            fragmentManager!!.beginTransaction()
                                .replace(R.id.sortFrame, dropDownStatusFragment).commit()
                        }

                    }

                    if (binding!!.layoutExpand.visibility != View.VISIBLE) {
                        showMenuAnim()
                    }
                }

            }, type);
        binding!!.rclSort.adapter = sortAdapter

        categorySortedAdapter = CategorySortedAdapter(
            sortAndFilter!!.sortHeader[2].children,
            mActivity,
            object : CategorySortedAdapter.ClickItem {
                override fun onClickItem() {
                    sortAdapter?.notifyDataSetChanged()
                    getParamAndSearch()
                }

            });
        binding!!.rclCategorySorted.adapter = categorySortedAdapter


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

            addFragment(
                SearchSuggestionFragment(
                    edtSearch.text.toString(),
                    location,
                    locationMain,
                    false,
                    this
                )
            )

        }

        btnFilter.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }


        layoutExpand.setOnClickListener {
            hideMenuAnim()
        }
    }


    override fun setObserver() {

    }

    public fun searchAll(type: String?, isLoadMore: Boolean) {
        searchViewModel?.searchAll(
            type,
            keyword,
            regionId,
            type,
            categoryId,
            district_id,
            ward_id,
            open,
            if(type == SearchType.DESTINATION) sortPlace else sortNews,
            content_type,
            isLoadMore
        )
    }

    public fun searchAllWithLink(link: String?, type: String?, isLoadMore: Boolean) {
        searchViewModel?.searchAllWithFullLink(link, type, isLoadMore)
    }

    public fun searchAllVideo(type: String?, isLoadMore: Boolean) {
        searchViewModel?.searchAllVideo(
            type,
            keyword,
            regionId,
            type,
            categoryId,
            district_id,
            ward_id,
            open,
            sortVideo,
            content_type,
            isLoadMore
        )
    }

    public fun searchAllVideoWithLink(link: String?, type: String?, isLoadMore: Boolean) {
        searchViewModel?.searchAllVideoWithFullLink(link, type, isLoadMore)
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


        val tabHome =
            LayoutInflater.from(mActivity).inflate(R.layout.f2_layout_main_tab_search, null)
        val tvHome = tabHome.findViewById<TextView>(R.id.tvTitle)
        tvHome.text = "Điểm đến"
        tvHome.setTextColor(Color.parseColor("#00918D"))
        val view = tabHome.findViewById<View>(R.id.indicator)
        view.visibility = View.VISIBLE
        tabLayout.getTabAt(0)?.customView = tabHome


        val tabNews =
            LayoutInflater.from(mActivity).inflate(R.layout.f2_layout_main_tab_search, null)
        val tvNews = tabNews.findViewById<TextView>(R.id.tvTitle)
        tvNews.text = "Tin tức"
        tvNews.setTextColor(Color.parseColor("#8A8A8A"))
        val viewNews = tabNews.findViewById<View>(R.id.indicator)
        viewNews.visibility = View.INVISIBLE
        tabLayout.getTabAt(1)?.customView = tabNews


        val tabVideos =
            LayoutInflater.from(mActivity).inflate(R.layout.f2_layout_main_tab_search, null)
        val tvVideos = tabVideos.findViewById<TextView>(R.id.tvTitle)
        tvVideos.text = "Video"
        tvVideos.setTextColor(Color.parseColor("#8A8A8A"))
        val viewVideos = tabVideos.findViewById<View>(R.id.indicator)
        viewVideos.visibility = View.INVISIBLE
        tabLayout.getTabAt(2)?.customView = tabVideos

        tabLayout.addOnTabSelectedListener(onTabSelectedListener)

        vpContent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    type[0] = "place"
                } else if (position == 1) {
                    type[0] = "news"
                } else {
                    type[0] = "video"
                }
                sortAdapter?.notifyDataSetChanged()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

    }

    override fun update(observable: Observable?, o: Any?) {
        if (observable is SearchResultViewModel && null != o) {
            try {
                when (o) {

                    is ResultSearch -> {
                        when (o.type) {
                            SearchType.NEWS -> {
                                newsSearchFragment?.setList(
                                    o.data.items as ArrayList<Travel>?,
                                    o.data.more_link,
                                    o.data.total,
                                    keyword!!,
                                    o.data.approximately,
                                    o.isLoadMore
                                )
                            }

                            SearchType.DESTINATION -> {
                                destinationSearchFragment?.setList(
                                    o.data.items as ArrayList<Travel>?,
                                    o.data.more_link,
                                    o.data.total,
                                    keyword!!,
                                    o.data.approximately,
                                    o.isLoadMore
                                )
                            }


                        }

                    }

                    is ResultVideoSearch -> {
                        resultVideosSearchFragment?.setList(
                            o.data.items as ArrayList<Video>?,
                            o.data.more_link,
                            o.data.total,
                            keyword!!,
                            o.data.approximately,
                            o.isLoadMore
                        )
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

    private val onTabSelectedListener: TabLayout.OnTabSelectedListener =
        object : TabLayout.OnTabSelectedListener {
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
        setDataScreen(
            TrackingAnalytic.ScreenCode.SEARCH_RESULT,
            TrackingAnalytic.ScreenTitle.SEARCH_RESULT
        )
    }

    public fun setHighLightedText(tv: TextView, textToHighlight: String) {
        var iHighLightText = SearchHighLightText()
        var highLightController = HighLightController(iHighLightText)
        highLightController.highLight(context!!, tv, textToHighlight)
    }


    private fun showMenuAnim() {
        val scaleDown = AnimationUtils.loadAnimation(mActivity, R.anim.scale_top_to_bottom)
        scaleDown.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                try {
                    binding!!.layoutExpand.visibility = View.VISIBLE
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

            override fun onAnimationEnd(animation: Animation) {
                try {
                    binding!!.viewCover.visibility = View.VISIBLE
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        binding!!.layoutExpand.startAnimation(scaleDown)
    }

    private fun hideMenuAnim() {
        val scaleDown = AnimationUtils.loadAnimation(mActivity, R.anim.un_scale_bottom_to_top)
        scaleDown.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                try {
                    binding!!.viewCover.visibility = View.GONE
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

            override fun onAnimationEnd(animation: Animation) {
                try {
                    binding!!.layoutExpand.visibility = View.GONE
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        binding!!.layoutExpand.startAnimation(scaleDown)
    }


    private fun getParamAndSearch() {
        var sortParamPlace = ""
        for (i in 0 until sortAndFilter!!.sortHeader[0].childrenPlace.size) {
            if (sortAndFilter!!.sortHeader[0].childrenPlace[i].isSelected) {
                sortParamPlace = sortAndFilter!!.sortHeader[0].childrenPlace[i].id
                break
            }
        }

        sortPlace = sortParamPlace


        var sortParamNews = ""
        for (i in 0 until sortAndFilter!!.sortHeader[0].childrenNews.size) {
            if (sortAndFilter!!.sortHeader[0].childrenNews[i].isSelected) {
                sortParamNews = sortAndFilter!!.sortHeader[0].childrenNews[i].id
                break
            }
        }

        sortNews = sortParamNews

        var sortParamVideo = ""
        for (i in 0 until sortAndFilter!!.sortHeader[0].childrenVideo.size) {
            if (sortAndFilter!!.sortHeader[0].childrenVideo[i].isSelected) {
                sortParamVideo = sortAndFilter!!.sortHeader[0].childrenVideo[i].id
                break
            }
        }

        sortVideo = sortParamVideo

//        Log.e("sortParam", sortParam)

        var districtID =
            if (sortAndFilter!!.sortHeader[1].content.district != null) sortAndFilter!!.sortHeader[1].content.district else ""
        var communeID =
            if (sortAndFilter!!.sortHeader[1].content.commune != null) sortAndFilter!!.sortHeader[1].content.commune else ""


        district_id = districtID
        regionId =
            if (sortAndFilter!!.sortHeader[1].content.cityId != null) sortAndFilter!!.sortHeader[1].content.cityId else ""
        if (regionId == "all") regionId = ""

        ward_id = communeID

        var categoryParam = ""


        for (i in 0 until sortAndFilter!!.sortHeader[2].children.size){
            if(sortAndFilter!!.sortHeader[2].children[i].isSelected){
                categoryParam = categoryParam + " "+sortAndFilter!!.sortHeader[2].children[i].id
            }
        }

//        Log.e("categoryParam", categoryParam!!)

        content_type = categoryParam.trim()


        var isOpen = sortAndFilter!!.sortHeader[3].content.isOpen


        open = isOpen

//        Log.e("isOpen", isOpen?.toString() ?: "null")

        loadOtherData()

    }

    private fun loadOtherData() {
        destinationSearchFragment?.clearData()
        newsSearchFragment?.clearData()
        resultVideosSearchFragment?.clearData()
        searchAll(SearchType.DESTINATION, false)
        searchAll(SearchType.NEWS, false)
        searchAllVideo(SearchType.VIDEO, false)
    }

    @Subscribe
    fun onTouchRCLLocation(onTouchRCLLocation: OnTouchRCLLocation) {
        drawerLayout.requestDisallowInterceptTouchEvent(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    override fun onClickSuggestion(
        searchKeywordSuggestion: SearchSuggestionResponse.Data.Item?,
        mLocation: Location?
    ) {
        this.keyword = keyword
        this.location = location
        edtSearch.text = keyword
        loadOtherData()
    }

    override fun onCancelSearch(location: Location?, keyword: String?) {
//        edtKeyword.text = keyword
    }

    override fun onClickRegion(location: Location?, keyword: String?) {
        this.keyword = keyword
        this.location = location
        edtSearch.text = keyword
        loadOtherData()
    }

    override fun onClickLayoutKeyword(location: Location?, keyword: String?) {
        this.keyword = keyword
        this.location = location
        edtSearch.text = keyword
        loadOtherData()
    }

}