package com.namviet.vtvtravel.view.fragment.f2search

import android.annotation.SuppressLint
import android.graphics.Color
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.baseapp.utils.KeyboardUtils
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jakewharton.rxbinding2.widget.RxTextView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2biglocation.SearchAllLocationAdapter
import com.namviet.vtvtravel.adapter.f2search.RecentAdapter
import com.namviet.vtvtravel.adapter.f2search.RecentAdapter.NoItem
import com.namviet.vtvtravel.adapter.f2search.SearchBlockAdapter
import com.namviet.vtvtravel.adapter.f2search.SearchMainPageAdapter
import com.namviet.vtvtravel.adapter.f2search.SearchSuggestionKeyWordAdapter
import com.namviet.vtvtravel.config.Constants
import com.namviet.vtvtravel.databinding.F2FragmentSearchBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.model.travelnews.Travel
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse
import com.namviet.vtvtravel.response.f2biglocation.LocationResponse
import com.namviet.vtvtravel.response.f2searchmain.MainResultSearchResponse
import com.namviet.vtvtravel.response.f2searchmain.MainSearchResponse
import com.namviet.vtvtravel.response.f2searchmain.SearchSuggestionResponse
import com.namviet.vtvtravel.response.f2searchmain.SubBaseSearch
import com.namviet.vtvtravel.response.newhome.AppVoucherResponse
import com.namviet.vtvtravel.response.newhome.ItemAppExperienceResponse
import com.namviet.vtvtravel.tracking.TrackingAnalytic
import com.namviet.vtvtravel.ultils.F2Util
import com.namviet.vtvtravel.ultils.PreferenceUtil
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel
import com.namviet.vtvtravel.viewmodel.f2search.SearchViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.f2_fragment_search.*
import kotlinx.android.synthetic.main.f2_layout_keyword.*
import kotlinx.android.synthetic.main.f2_layout_keyword.view.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class SearchFragment : BaseFragment<F2FragmentSearchBinding?>(), Observer {
    private var appVoucherResponse: AppVoucherResponse? = null
    private var itemAppExperienceResponse: ItemAppExperienceResponse? = null


    //viewpager

    private var mainAdapter: SearchMainPageAdapter? = null
    private var destinationSearchFragment: DestinationSearchFragment? = null
    private var newsSearchFragment: NewsSearchFragment? = null
    //


    private var locationsMain: ArrayList<Location> = ArrayList()
    private val locations: ArrayList<Location>? = ArrayList()
    private var blocks: ArrayList<MainSearchResponse.Data>? = ArrayList()
    private var searchSuggestions: ArrayList<SearchSuggestionResponse.Data.Item>? = ArrayList()


    private var viewModel: SearchBigLocationViewModel? = null
    private var searchViewModel: SearchViewModel? = null

    private var recentAdapter: RecentAdapter? = null
    private var searchAllLocationAdapter: SearchAllLocationAdapter? = null
    private var blockAdapter: SearchBlockAdapter? = null
    private var searchSuggestionKeyWordAdapter: SearchSuggestionKeyWordAdapter? = null

    private var regionId: String? = null;


    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_search
    }


    override fun initView() {
        viewModel = SearchBigLocationViewModel()
        viewModel?.addObserver(this)
        searchViewModel = SearchViewModel()
        searchViewModel?.addObserver(this)
        viewModel?.getAllLocation()
        searchViewModel?.getBlockSearch()
    }

    override fun initData() {


        recentAdapter = RecentAdapter(getRecentSearch(), mActivity, object : RecentAdapter.ClickItem {
            override fun onClickItem(string: String?) {
                addFragment(ResultSearchFragment(string, regionId))
                KeyboardUtils.hideKeyboard(mActivity, edtKeyword)
            }
        }, object : NoItem {
            override fun onNoItem(b: Boolean) {
                if (b) {
                    tvHaveNoRecent.visibility = View.VISIBLE
                } else {
                    tvHaveNoRecent.visibility = View.GONE
                }
            }
        })
        searchAllLocationAdapter = SearchAllLocationAdapter(mActivity, locations, SearchAllLocationAdapter.ClickItem { location ->
            edtRegion.setText(location?.name)
            edtKeyword.requestFocus();
            regionId = location?.id;
            if(edtKeyword.text.isNotEmpty()) {
                searchViewModel?.getPreResultSearch(edtKeyword.text.toString(), regionId);

                try {
                    TrackingAnalytic.postEvent(TrackingAnalytic.SEARCH, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.SEARCH, TrackingAnalytic.ScreenTitle.SEARCH).setTerm(edtKeyword.text.toString()).setScreen_class(this.javaClass.name))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })

        blockAdapter = SearchBlockAdapter(blocks, mActivity)
        rclBlock.adapter = blockAdapter


        searchSuggestionKeyWordAdapter = SearchSuggestionKeyWordAdapter(searchSuggestions, mActivity, object : SearchSuggestionKeyWordAdapter.ClickItem{
            override fun onClickItem(searchKeywordSuggestion: SearchSuggestionResponse.Data.Item?) {
                try {
                    edtKeyword.setText(searchKeywordSuggestion?.title)
                    addRecentSearch(edtKeyword.text.toString())
                    recentAdapter?.setData(getRecentSearch())
                    addFragment(ResultSearchFragment(edtKeyword.text.toString(), regionId))
                    KeyboardUtils.hideKeyboard(mActivity, edtKeyword)
                    edtKeyword.clearFocus()
                } catch (e: Exception) {
                }
            }

        })
        rclSearchSuggestion.adapter = searchSuggestionKeyWordAdapter


        rclLocation.adapter = searchAllLocationAdapter
        rclRecentSearch.adapter = recentAdapter
        rclRecentSearch.isNestedScrollingEnabled = false
        rclBlock.isNestedScrollingEnabled = false
        val flexboxLayoutManager = FlexboxLayoutManager(mActivity)
        rclRecentSearch.layoutManager = flexboxLayoutManager
        edtKeyword.onFocusChangeListener = OnFocusChangeListener { _, b ->
            if (b) {
                layoutForMainSearch.visibility = View.VISIBLE
                layoutSearchRegion.visibility = View.GONE
            }
        }
        edtRegion.onFocusChangeListener = OnFocusChangeListener { _, b ->
            if (b) {
                layoutForMainSearch.visibility = View.GONE
                layoutSearchRegion.visibility = View.VISIBLE
            }
        }
        handleSearch()
        handleSearchRegion()
        genViewPagerSearchResult()
    }


    private fun genViewPagerSearchResult() {

        mainAdapter = SearchMainPageAdapter(childFragmentManager)
        destinationSearchFragment = DestinationSearchFragment()
        mainAdapter!!.addFragment(destinationSearchFragment, "destinationSearchFragment")
        newsSearchFragment = NewsSearchFragment()
        mainAdapter!!.addFragment(newsSearchFragment, "newsSearchFragment")
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

        tabLayout.addOnTabSelectedListener(onTabSelectedListener)


    }


    @SuppressLint("CheckResult")
    private fun handleSearch() {
        RxTextView.afterTextChangeEvents(binding!!.edtKeyword)
                .skipInitialValue()
                .debounce(790, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (binding!!.edtKeyword.text.toString().isEmpty()) {
                        binding!!.scrollMainSearch.visibility = View.VISIBLE
                        binding!!.layoutSearchResult.visibility = View.GONE
                    } else {
                        binding!!.scrollMainSearch.visibility = View.GONE
                        binding!!.layoutSearchResult.visibility = View.VISIBLE
//                        searchViewModel?.getPreResultSearch(edtKeyword.text.toString(), regionId)
                        searchViewModel?.getSearchSuggestion(edtKeyword.text.toString())
                        layoutKeyword.tvSearchFollow.text = "Tìm kiếm theo "+ edtKeyword.text.toString();

                        try {
                            TrackingAnalytic.postEvent(TrackingAnalytic.SEARCH, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.SEARCH, TrackingAnalytic.ScreenTitle.SEARCH).setTerm(edtKeyword.text.toString()).setScreen_class(this.javaClass.name))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

    }


    @SuppressLint("CheckResult")
    private fun handleSearchRegion() {
        RxTextView.afterTextChangeEvents(edtRegion)
                .skipInitialValue()
                .debounce(450, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (edtRegion.text.toString().isEmpty()) {
                        locations?.clear()
                        locations?.addAll(locationsMain)
                        searchAllLocationAdapter!!.notifyDataSetChanged()
                        regionId = null
                        if(edtKeyword.text.isNotEmpty()) {
                            searchViewModel?.getPreResultSearch(edtKeyword.text.toString(), regionId);

                            try {
                                TrackingAnalytic.postEvent(TrackingAnalytic.SEARCH, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.SEARCH, TrackingAnalytic.ScreenTitle.SEARCH).setTerm(edtKeyword.text.toString()).setScreen_class(this.javaClass.name))
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    } else {
                        rclLocation.visibility = View.VISIBLE
                        locations!!.clear()
                        for (i in locationsMain.indices) {
                            if (F2Util.removeAccent(locationsMain[i].name.toLowerCase()).contains(F2Util.removeAccent(edtRegion.text.toString().toLowerCase()))) {
                                locations.add(locationsMain[i])
                            }
                        }
                        searchAllLocationAdapter!!.notifyDataSetChanged()
                    }
                }
    }


    override fun setClickListener() {
        btnBack.setOnClickListener { mActivity.onBackPressed() }

        edtKeyword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (edtKeyword.text.isNotEmpty()) {
                    addRecentSearch(edtKeyword.text.toString())
                    recentAdapter?.setData(getRecentSearch())
                    addFragment(ResultSearchFragment(edtKeyword.text.toString(), regionId))
                    KeyboardUtils.hideKeyboard(mActivity, edtKeyword)
                    edtKeyword.clearFocus()
                }
                true
            } else {
                false
            }
        }

        imgCloseSearch.setOnClickListener {
            edtKeyword?.setText("")
        }

        imgCloseSearchRegion.setOnClickListener {
            edtRegion.setText("")
            regionId = null
        }

        viewTouch.setOnTouchListener { _, motionEvent ->
            KeyboardUtils.hideKeyboard(mActivity, edtKeyword)
            false
        }

        btnCurrentLocation.setOnClickListener {
            searchViewModel?.getLocation()
        }


    }

    override fun inject() {

    }

    override fun setObserver() {

    }

    override fun update(observable: Observable?, o: Any?) {
        if (observable is SearchBigLocationViewModel && null != o) {
            when (o) {
                is AllLocationResponse -> {
                    locationsMain = o.data as ArrayList<Location>;
                    locations?.addAll(0, locationsMain)
                    searchAllLocationAdapter?.notifyDataSetChanged()
                }
                is LocationResponse -> {
                    edtRegion.setText(o.data.name)
                }
                is ErrorResponse -> {
                    val responseError = o
                    try { //                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                    } catch (e: Exception) {
                    }
                }
            }
        } else if (observable is SearchViewModel && null != o) {
            when (o) {
                is MainSearchResponse -> {
                    blocks?.clear()
                    blocks?.addAll(0, o.data)
                    blockAdapter?.notifyDataSetChanged()

                    for (i in 0 until blocks!!.size) {
                        when (blocks?.get(i)?.code) {
                            "YOUR_VOUCHER" -> {
                                searchViewModel?.getYourVoucher(blocks?.get(i)!!.link)
                            }
                            "HIGHLIGHT_TREND" -> {
                                searchViewModel?.getTrend(blocks?.get(i)!!.link)
                            }

                        }
                    }
                }

                is SubBaseSearch<*> -> {
                    if (o.data is AppVoucherResponse) {
                        blockAdapter?.setForYouList((o.data as AppVoucherResponse).items)
                    } else if (o.data is ItemAppExperienceResponse) {
                        blockAdapter?.setTrendList((o.data as ItemAppExperienceResponse).items)
                    }
                }

                is MainResultSearchResponse -> {
                    destinationSearchFragment?.setList(o.data.items as java.util.ArrayList<Travel>?)
                    newsSearchFragment?.setList(o.data.items_news as java.util.ArrayList<Travel>?)
                }

                is LocationResponse -> {
                    edtRegion.setText(o.data.name)
                }

                is SearchSuggestionResponse -> {
                    searchSuggestionKeyWordAdapter?.setData(o.data.items)
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


    private fun addRecentSearch(string: String) {
        try {
            var arrayListRecentSearchs: ArrayList<String>
            val json = PreferenceUtil.getInstance(mActivity).getValue(Constants.PrefKey.RECENT_SEARCH, "")
            if (json.isEmpty()) {
                arrayListRecentSearchs = ArrayList()
                arrayListRecentSearchs.add(string)
                PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.RECENT_SEARCH, Gson().toJson(arrayListRecentSearchs))
            } else {
                arrayListRecentSearchs = Gson().fromJson(json,
                        object : TypeToken<java.util.ArrayList<String?>?>() {}.type)
                for (i in arrayListRecentSearchs.indices) {
                    if (string == arrayListRecentSearchs[i]) {
                        return
                    }
                }
                if (arrayListRecentSearchs.size >= 10) {
                    arrayListRecentSearchs.removeAt(0)
                }
                arrayListRecentSearchs.add(string)
                PreferenceUtil.getInstance(mActivity).setValue(Constants.PrefKey.RECENT_SEARCH, Gson().toJson(arrayListRecentSearchs))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getRecentSearch(): ArrayList<String> {
        val arrayListRecentSearchs: ArrayList<String>
        val json = PreferenceUtil.getInstance(context).getValue(Constants.PrefKey.RECENT_SEARCH, "")
        arrayListRecentSearchs = if (json.isEmpty()) {
            ArrayList()
        } else {
            Gson().fromJson(json,
                    object : TypeToken<java.util.ArrayList<String?>?>() {}.type)
        }

        arrayListRecentSearchs.reverse()

        return arrayListRecentSearchs;
    }

    private val onTabSelectedListener: OnTabSelectedListener = object : OnTabSelectedListener {
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
        setDataScreen(TrackingAnalytic.ScreenCode.SEARCH, TrackingAnalytic.ScreenTitle.SEARCH)
    }


}