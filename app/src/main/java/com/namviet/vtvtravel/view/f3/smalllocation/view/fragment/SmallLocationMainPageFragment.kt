package com.namviet.vtvtravel.view.f3.smalllocation.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.baseapp.utils.KeyboardUtils
import com.jakewharton.rxbinding2.widget.RxTextView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter
import com.namviet.vtvtravel.adapter.f2search.SearchSuggestionKeyWordAdapter
import com.namviet.vtvtravel.api.WSConfig
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.config.Constants
import com.namviet.vtvtravel.database.StorageManager
import com.namviet.vtvtravel.databinding.F2FragmentMainPageSmallLocationBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.f2event.OnChooseRegionSmallLocation
import com.namviet.vtvtravel.model.f2event.OnDetectLocation
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
import com.namviet.vtvtravel.viewmodel.f2smalllocation.SmallLocationViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.f2_fragment_main_page_small_location.*
import kotlinx.android.synthetic.main.f2_fragment_main_page_small_location.btnBack
import kotlinx.android.synthetic.main.f2_fragment_main_page_small_location.layoutRegion
import kotlinx.android.synthetic.main.f2_fragment_main_page_small_location.rclSearchSuggestion
import kotlinx.android.synthetic.main.f2_fragment_main_page_small_location.tabLayout
import kotlinx.android.synthetic.main.f2_fragment_main_page_small_location.vpContent
import kotlinx.android.synthetic.main.f2_fragment_search.*
import kotlinx.android.synthetic.main.f2_layout_keyword.*
import kotlinx.android.synthetic.main.f2_layout_keyword.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList


class SmallLocationMainPageFragment : BaseFragment<F2FragmentMainPageSmallLocationBinding?>, Observer, SearchSuggestionSmallLocationFragment.SearchSuggestionCallback {
    private var mainAdapter : MainAdapter? = null
    private var searchSuggestionKeyWordAdapter: SearchSuggestionKeyWordAdapter? = null
    private var searchSuggestions: ArrayList<SearchSuggestionResponse.Data.Item>? = ArrayList()
    private var tabSelectedPosition = 0;

    @Inject
    lateinit var smallLocationMainViewModel: SmallLocationMainViewModel
    @Inject
    lateinit var storeManager: StorageManager
    @Inject
    lateinit var searchViewModel: SearchViewModel


    private var searchBigLocationViewModel: SearchBigLocationViewModel? = null
    private var locationsMain: ArrayList<Location> = ArrayList()
    private val locations: ArrayList<Location>? = ArrayList()

    private var regionId: String? = null;
    private var location: Location? = null;

    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_main_page_small_location
    }

    private var dataMenu: ArrayList<ItemHomeService<*>.Item>? = null;
    private var position : Int = 0;
    private var regionIdToLoadSmallLocation : String? = null

    constructor(
        dataMenu: ArrayList<ItemHomeService<*>.Item>?,
        position : Int,
        regionIdToLoadSmallLocation : String?
    ){
        this.dataMenu = dataMenu
        this.position = position
        this.regionIdToLoadSmallLocation = regionIdToLoadSmallLocation
    }

    constructor()


    override fun initView() {
        binding?.smallLocationMainViewModel = smallLocationMainViewModel
        searchViewModel.addObserver(this)

        searchBigLocationViewModel = SearchBigLocationViewModel()
        searchBigLocationViewModel?.addObserver(this)
        searchBigLocationViewModel?.getAllLocation()
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
        Handler().postDelayed(Runnable {
            smallLocationMainViewModel.setStateFirst()

            btnBackmap.setOnClickListener {
                rll_header_map.visibility = View.GONE
                tabLayout.visibility = View.VISIBLE
                EventBus.getDefault().post(ClickHideMapView())
            }
            mainAdapter = MainAdapter(childFragmentManager)
            vpContent.offscreenPageLimit = 10

            for(i in 0 until dataMenu!!.size){
                if(regionIdToLoadSmallLocation == null || (regionIdToLoadSmallLocation != null && regionIdToLoadSmallLocation!!.isEmpty())) {
                    var smallLocationFragment = SmallLocationFragment(dataMenu?.get(i)?.link, dataMenu?.get(i)?.code, "",i);
                    mainAdapter?.addFragment(smallLocationFragment, "")
                }else{
                    var smallLocationFragment = SmallLocationFragment(dataMenu?.get(i)?.link, dataMenu?.get(i)?.code, regionIdToLoadSmallLocation,i);
                    mainAdapter?.addFragment(smallLocationFragment, "")
                }

            }
            vpContent.adapter = mainAdapter
            vpContent?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }
                override fun onPageSelected(position: Int) {
                    tabSelectedPosition = position;
                }

            })
            tabLayout.setTabTextColors(ContextCompat.getColor(mActivity, R.color.md_black_1000), ContextCompat.getColor(mActivity, R.color.f2_color_package))
            tabLayout.setupWithViewPager(vpContent)
            vpContent.setCurrentItem(position,true)

            for(i in 0 until dataMenu!!.size){
                tabLayout.getTabAt(i)?.text = dataMenu?.get(i)?.name
            }
        }, 200)

    }
    override fun inject() {
        (mActivity.application as MyApplication).viewModelComponent.inject(this)
    }
    @SuppressLint("CheckResult")
    override fun setClickListener() {
        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }

        layoutRegion.setOnClickListener {
            var chooseRegionMainFragment = ChooseRegionMainFragment();
            chooseRegionMainFragment.setData(locationsMain, object : ChooseRegionMainFragment.ChooseRegion{
                override fun clickRegion(location: Location?) {
                    tvRegionName.text = location?.name
                    tvRegionName2.text = location?.name
                    this@SmallLocationMainPageFragment.location = location
                    this@SmallLocationMainPageFragment.regionId = location?.id
                    EventBus.getDefault().post(OnChooseRegionSmallLocation(location?.id))
                }
            }, true)
            addFragment(chooseRegionMainFragment)
        }


        edtSearch.setOnClickListener {
            addFragment(SearchSuggestionSmallLocationFragment( "", location, locationsMain, false, this, getContentType(dataMenu!![tabSelectedPosition].code)))
        }

        btnChooseRegion2.setOnClickListener {
            var chooseRegionMainFragment = ChooseRegionMainFragment();
            chooseRegionMainFragment.setData(locationsMain, object : ChooseRegionMainFragment.ChooseRegion{
                override fun clickRegion(location: Location?) {
                    tvRegionName.text = location?.name
                    tvRegionName2.text = location?.name
                    this@SmallLocationMainPageFragment.location = location
                    this@SmallLocationMainPageFragment.regionId = location?.id
                    EventBus.getDefault().post(OnChooseRegionSmallLocation(location?.id))
                }
            }, true)
            addFragment(chooseRegionMainFragment)
        }

    }

    private fun getContentType(code : String) : String{
        when (code) {
            "APP_WHERE_GO" -> {
                return  Constants.TypeDestination.PLACES
            }
            "APP_WHAT_EAT" -> {
                return Constants.TypeDestination.RESTAURANTS
            }
            "APP_WHAT_PLAY" -> {
                return Constants.TypeDestination.CENTERS
            }
            "APP_WHERE_STAY" -> {
                return Constants.TypeDestination.HOTELS
            }
        }
        return Constants.TypeDestination.PLACES
    }


    override fun setObserver() {}


    override fun update(observable: Observable?, o: Any?) {
        if (observable is SearchBigLocationViewModel && null != o) {
            when (o) {
                is AllLocationResponse -> {
                    locationsMain = o.data as ArrayList<Location>;
                    locations?.addAll(0, locationsMain)
                    if(regionIdToLoadSmallLocation == null || (regionIdToLoadSmallLocation != null && regionIdToLoadSmallLocation!!.isEmpty())) {

                    }else {
                        getLocationFromPreviousScreen()
                    }
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

    override fun onClickSuggestion(searchKeywordSuggestion: SearchSuggestionResponse.Data.Item?, mLocation: Location?) {
        handleLocation(mLocation)
//        edtSearch.text = searchKeywordSuggestion?.title
        if(searchKeywordSuggestion?.type.equals("category")){

        }else{
            edtSearch.text = searchKeywordSuggestion?.title
        }
        for(i in 0 until dataMenu!!.size){
            if(i == tabSelectedPosition){
                addFragment(SearchResultFragment(WSConfig.HOST+"nearby?content_type=", dataMenu?.get(i)?.code,mLocation?.id,searchKeywordSuggestion?.title,i, this@SmallLocationMainPageFragment.location))
            }
        }

        KeyboardUtils.hideKeyboard(mActivity, edtSearch)
    }

    override fun onCancelSearch(location: Location?, keyword: String?) {
//        handleLocation(location)
//        edtSearch.text = keyword
    }

    override fun onClickRegion(location: Location?, keyword: String?) {
        goToSearchResult(location, keyword)
    }

    override fun onClickLayoutKeyword(location: Location?, keyword: String?) {
        goToSearchResult(location, keyword)
    }


    private fun goToSearchResult(location: Location?, keyword: String?){
        handleLocation(location)
//        edtSearch.text = keyword
        for(i in 0 until dataMenu!!.size){
            if(i == tabSelectedPosition){
                addFragment(SearchResultFragment(WSConfig.HOST+"nearby?content_type=", dataMenu?.get(i)?.code, location?.id, keyword, i, this@SmallLocationMainPageFragment.location))
            }
        }
        KeyboardUtils.hideKeyboard(mActivity, edtSearch)
    }


    private fun handleLocation(location: Location?){
        this.location = location
        if(location != null) {
            tvRegionName.text = location.name
            tvRegionName2.text = location.name
        }else {
            tvRegionName.text = "Tất cả"
            tvRegionName2.text = "Tất cả"
        }
    }

    @Subscribe
    public fun onDetectLocation(onDetectLocation: OnDetectLocation){
        if(regionIdToLoadSmallLocation == null || (regionIdToLoadSmallLocation != null && regionIdToLoadSmallLocation!!.isEmpty())) {
            tvRegionName.text = onDetectLocation.regionName
            tvRegionName2.text = onDetectLocation.regionName

            for (i in 0 until locationsMain.size) {
                if (onDetectLocation.regionName == locationsMain!![i].name) {
                    location = locationsMain!![i]
                    regionId = locationsMain!![i].id
                    break
                }
            }
        }
    }


    private fun getLocationFromPreviousScreen(){
        for (i in 0 until locationsMain.size) {
            if (regionIdToLoadSmallLocation == locationsMain!![i].id) {
                location = locationsMain!![i]
                regionId = locationsMain!![i].id
                tvRegionName.text = location?.name
                tvRegionName2.text = location?.name
                break
            }
        }


    }


}