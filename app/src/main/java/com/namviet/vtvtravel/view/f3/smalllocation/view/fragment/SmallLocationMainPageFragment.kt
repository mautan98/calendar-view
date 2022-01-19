package com.namviet.vtvtravel.view.f3.smalllocation.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.baseapp.utils.KeyboardUtils
import com.jakewharton.rxbinding2.widget.RxTextView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter
import com.namviet.vtvtravel.adapter.f2search.SearchSuggestionKeyWordAdapter
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.config.Constants
import com.namviet.vtvtravel.database.StorageManager
import com.namviet.vtvtravel.databinding.F2FragmentMainPageSmallLocationBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.f2event.UpdateAllListTicket
import com.namviet.vtvtravel.model.newhome.ItemHomeService
import com.namviet.vtvtravel.model.travelnews.Travel
import com.namviet.vtvtravel.response.f2biglocation.LocationResponse
import com.namviet.vtvtravel.response.f2searchmain.MainResultSearchResponse
import com.namviet.vtvtravel.response.f2searchmain.MainSearchResponse
import com.namviet.vtvtravel.response.f2searchmain.SearchSuggestionResponse
import com.namviet.vtvtravel.response.f2searchmain.SubBaseSearch
import com.namviet.vtvtravel.response.newhome.AppVoucherResponse
import com.namviet.vtvtravel.response.newhome.ItemAppExperienceResponse
import com.namviet.vtvtravel.tracking.TrackingAnalytic
import com.namviet.vtvtravel.ultils.highlight.HighLightController
import com.namviet.vtvtravel.ultils.highlight.SearchHighLightText
import com.namviet.vtvtravel.view.f3.model.ClickHideMapView
import com.namviet.vtvtravel.view.f3.model.HideMapView
import com.namviet.vtvtravel.view.f3.model.ShowMapView
import com.namviet.vtvtravel.view.f3.smalllocation.viewmodel.SmallLocationMainViewModel
import com.namviet.vtvtravel.view.fragment.f2search.ResultSearchFragment
import com.namviet.vtvtravel.view.fragment.f2smalllocation.SmallLocationFragment
import com.namviet.vtvtravel.viewmodel.f2search.SearchViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.f2_fragment_main_page_small_location.*
import kotlinx.android.synthetic.main.f2_fragment_main_page_small_location.btnBack
import kotlinx.android.synthetic.main.f2_fragment_main_page_small_location.rclSearchSuggestion
import kotlinx.android.synthetic.main.f2_fragment_main_page_small_location.tabLayout
import kotlinx.android.synthetic.main.f2_fragment_main_page_small_location.vpContent
import kotlinx.android.synthetic.main.f2_layout_keyword.*
import kotlinx.android.synthetic.main.f2_layout_keyword.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList

class SmallLocationMainPageFragment(private var dataMenu: ArrayList<ItemHomeService<*>.Item>? = null) : BaseFragment<F2FragmentMainPageSmallLocationBinding?>(), Observer {
    private var mainAdapter : MainAdapter? = null
    private var searchSuggestionKeyWordAdapter: SearchSuggestionKeyWordAdapter? = null
    private var searchSuggestions: ArrayList<SearchSuggestionResponse.Data.Item>? = ArrayList()


    @Inject
    lateinit var smallLocationMainViewModel: SmallLocationMainViewModel
    @Inject
    lateinit var storeManager: StorageManager
    @Inject
    lateinit var searchViewModel: SearchViewModel

    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_main_page_small_location
    }


    override fun initView() {
        binding?.smallLocationMainViewModel = smallLocationMainViewModel
        searchViewModel.addObserver(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    public fun ShowMapView(showMapView: ShowMapView){
        tabLayout.visibility = View.GONE
        rll_header_map.visibility = View.VISIBLE
    }
    @Subscribe
    public fun HidemapView(hideMapView: HideMapView){
        tabLayout.visibility = View.VISIBLE
        rll_header_map.visibility = View.GONE
    }
    override fun initData() {
        smallLocationMainViewModel.setStateFirst()

        btnBackmap.setOnClickListener {
            rll_header_map.visibility = View.GONE
            tabLayout.visibility = View.VISIBLE
            EventBus.getDefault().post(ClickHideMapView())
        }
        mainAdapter = MainAdapter(childFragmentManager)
        vpContent.offscreenPageLimit = 10

        for(i in 0 until dataMenu!!.size){
            var smallLocationFragment = SmallLocationFragment(dataMenu?.get(i)?.link, dataMenu?.get(i)?.code, "");
            mainAdapter?.addFragment(smallLocationFragment, "")
        }
        vpContent.adapter = mainAdapter
        tabLayout.setTabTextColors(ContextCompat.getColor(mActivity, R.color.md_black_1000), ContextCompat.getColor(mActivity, R.color.f2_color_package))
        tabLayout.setupWithViewPager(vpContent)

        for(i in 0 until dataMenu!!.size){
            tabLayout.getTabAt(i)?.text = dataMenu?.get(i)?.name
        }



        searchSuggestionKeyWordAdapter = SearchSuggestionKeyWordAdapter(searchSuggestions, mActivity, object : SearchSuggestionKeyWordAdapter.ClickItem{
            override fun onClickItem(searchKeywordSuggestion: SearchSuggestionResponse.Data.Item?) {
                try {
//                    edtKeyword.setText(searchKeywordSuggestion?.title)
//                    addRecentSearch(edtKeyword.text.toString())
//                    recentAdapter?.setData(getRecentSearch())
//                    addFragment(ResultSearchFragment(edtKeyword.text.toString(), regionId, searchKeywordSuggestion?.categoryCode))
//                    KeyboardUtils.hideKeyboard(mActivity, edtKeyword)
//                    edtKeyword.clearFocus()
                } catch (e: Exception) {
                }
            }

        })
        rclSearchSuggestion.adapter = searchSuggestionKeyWordAdapter

    }
    override fun inject() {
        (mActivity.application as MyApplication).viewModelComponent.inject(this)
    }
    @SuppressLint("CheckResult")
    override fun setClickListener() {
        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }

        RxTextView.afterTextChangeEvents(edtSearch)
                .skipInitialValue()
                .debounce(790, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    try {
//                        storeManager.setStringValue(Constants.IntentKey.RECENT_SEARCH_SMALLOCATION, edtSearch.text.toString())
//                        Log.e("hihihihi", storeManager.getStringValue(Constants.IntentKey.RECENT_SEARCH_SMALLOCATION))

//                        searchViewModel.getSearchSuggestion(edtKeyword.text.toString(), regionId)
                        searchViewModel.getSearchSuggestion(edtSearch.text.toString(), "")
                        layoutKeyword.tvSearchFollow.text = "Tìm kiếm theo \""+ edtSearch.text.toString()+"\"";
                        setHighLightedText(layoutKeyword.tvSearchFollow, "\""+ edtSearch.text.toString()+"\"")

//                        try {
//                            TrackingAnalytic.postEvent(TrackingAnalytic.SEARCH, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.SEARCH, TrackingAnalytic.ScreenTitle.SEARCH).setTerm(edtKeyword.text.toString()).setScreen_class(this.javaClass.name))
//                        } catch (e: Exception) {
//                            e.printStackTrace()
//                        }
                    } catch (e: Exception) {
                    }
                }

        edtSearch.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                smallLocationMainViewModel.setStateSecond()
            }else{
                smallLocationMainViewModel.setStateFirst()
            }
         }

        tvCancelSearch.setOnClickListener {
            smallLocationMainViewModel.setStateFirst()
            edtSearch.clearFocus()
            KeyboardUtils.hideKeyboard(mActivity, edtSearch)
        }

    }
    override fun setObserver() {}


    override fun update(observable: Observable?, o: Any?) {
        if (observable is SearchViewModel && null != o) {
            when (o) {
                is SearchSuggestionResponse -> {
                    var list = o.data.items
//                    list.addAll(0, getSuggestionInRecentSearch())
                    searchSuggestionKeyWordAdapter?.setKeyword(edtSearch.text.toString())
                    searchSuggestionKeyWordAdapter?.setData(list)
                }


                is ErrorResponse -> {
                    val responseError = o
                    try {
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }


    public fun setHighLightedText(tv: TextView, textToHighlight: String) {
        val iHighLightText  = SearchHighLightText()
        val highLightController =  HighLightController(iHighLightText)
        highLightController.highLight(mActivity, tv, textToHighlight)
    }


}