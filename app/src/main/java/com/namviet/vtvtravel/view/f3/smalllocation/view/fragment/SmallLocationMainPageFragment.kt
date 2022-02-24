package com.namviet.vtvtravel.view.f3.smalllocation.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.baseapp.utils.KeyboardUtils
import com.jakewharton.rxbinding2.widget.RxTextView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter
import com.namviet.vtvtravel.adapter.f2search.SearchSuggestionKeyWordAdapter
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.database.StorageManager
import com.namviet.vtvtravel.databinding.F2FragmentMainPageSmallLocationBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.newhome.ItemHomeService
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse
import com.namviet.vtvtravel.response.f2biglocation.LocationResponse
import com.namviet.vtvtravel.response.f2searchmain.SearchSuggestionResponse
import com.namviet.vtvtravel.ultils.highlight.HighLightController
import com.namviet.vtvtravel.ultils.highlight.SearchHighLightText
import com.namviet.vtvtravel.view.f3.model.ClickHideMapView
import com.namviet.vtvtravel.view.f3.model.HideMapView
import com.namviet.vtvtravel.view.f3.model.ShowMapView
import com.namviet.vtvtravel.view.f3.smalllocation.viewmodel.SmallLocationMainViewModel
import com.namviet.vtvtravel.view.fragment.f2search.ChooseRegionMainFragment
import com.namviet.vtvtravel.view.fragment.f2smalllocation.SearchResultFragment
import com.namviet.vtvtravel.view.fragment.f2smalllocation.SmallLocationFragment
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel
import com.namviet.vtvtravel.viewmodel.f2search.SearchViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.f2_fragment_main_page_small_location.*
import kotlinx.android.synthetic.main.f2_fragment_main_page_small_location.btnBack
import kotlinx.android.synthetic.main.f2_fragment_main_page_small_location.layoutRegion
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

class SmallLocationMainPageFragment(private var dataMenu: ArrayList<ItemHomeService<*>.Item>? = null, private var position : Int) : BaseFragment<F2FragmentMainPageSmallLocationBinding?>(), Observer {
    private var mainAdapter : MainAdapter? = null
    private var searchSuggestionKeyWordAdapter: SearchSuggestionKeyWordAdapter? = null
    private var searchSuggestions: ArrayList<SearchSuggestionResponse.Data.Item>? = ArrayList()


    @Inject
    lateinit var smallLocationMainViewModel: SmallLocationMainViewModel
    @Inject
    lateinit var storeManager: StorageManager
    @Inject
    lateinit var searchViewModel: SearchViewModel


    private var viewModel: SearchBigLocationViewModel? = null
    private var locationsMain: ArrayList<Location> = ArrayList()
    private val locations: ArrayList<Location>? = ArrayList()

    private var regionId: String? = null;
    private var location: Location? = null;

    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_main_page_small_location
    }


    override fun initView() {
        binding?.smallLocationMainViewModel = smallLocationMainViewModel
        searchViewModel.addObserver(this)

        viewModel = SearchBigLocationViewModel()
        viewModel?.addObserver(this)
        viewModel?.getAllLocation()
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
    public fun getStateMapView() : Int {
        return rll_header_map.visibility
    }
    public fun hideMapView() {
        tabLayout.visibility = View.VISIBLE
        rll_header_map.visibility = View.GONE
        EventBus.getDefault().post(ClickHideMapView())
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
            var smallLocationFragment = SmallLocationFragment(dataMenu?.get(i)?.link, dataMenu?.get(i)?.code, "",i);
            mainAdapter?.addFragment(smallLocationFragment, "")
        }
        vpContent.adapter = mainAdapter
        tabLayout.setTabTextColors(ContextCompat.getColor(mActivity, R.color.md_black_1000), ContextCompat.getColor(mActivity, R.color.f2_color_package))
        tabLayout.setupWithViewPager(vpContent)
        vpContent.setCurrentItem(position,true)

        for(i in 0 until dataMenu!!.size){
            tabLayout.getTabAt(i)?.text = dataMenu?.get(i)?.name
        }



        searchSuggestionKeyWordAdapter = SearchSuggestionKeyWordAdapter(searchSuggestions, mActivity, object : SearchSuggestionKeyWordAdapter.ClickItem{
            override fun onClickItem(searchKeywordSuggestion: SearchSuggestionResponse.Data.Item?) {
                try {
                    edtSearch.setText(searchKeywordSuggestion?.title)
//                    addRecentSearch(edtKeyword.text.toString())
//                    recentAdapter?.setData(getRecentSearch())
                    addFragment(SearchResultFragment("http://api.vtvtravel.vn/nearby?content_type=","APP_WHERE_GO",""))
                    KeyboardUtils.hideKeyboard(mActivity, edtSearch)
                    //edtKeyword.clearFocus()
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



        layoutRegion.setOnClickListener {
            var chooseRegionMainFragment = ChooseRegionMainFragment();
            chooseRegionMainFragment.setData(locationsMain, object : ChooseRegionMainFragment.ChooseRegion{
                override fun clickRegion(location: Location?) {
                    tvRegionName.text = location?.name
                    this@SmallLocationMainPageFragment.location = location
                    this@SmallLocationMainPageFragment.regionId = location?.id
                }
            }, true)
            addFragment(chooseRegionMainFragment)
        }



    }
    override fun setObserver() {}


    override fun update(observable: Observable?, o: Any?) {
        if (observable is SearchBigLocationViewModel && null != o) {
            when (o) {
                is AllLocationResponse -> {
                    locationsMain = o.data as ArrayList<Location>;
                    locations?.addAll(0, locationsMain)
                }
                is LocationResponse -> {
//                    tvRegion.text = o.data.name
                }
                is ErrorResponse -> {
                    val responseError = o
                    try {
                    } catch (e: Exception) {
                    }
                }
            }
        } else if (observable is SearchViewModel && null != o) {
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