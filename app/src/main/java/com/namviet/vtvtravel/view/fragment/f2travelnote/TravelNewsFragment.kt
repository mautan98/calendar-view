package com.namviet.vtvtravel.view.fragment.f2travelnote

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2video.SortVideoAdapter
import com.namviet.vtvtravel.adapter.travelnews.HighLightTravelNoteAdapter
import com.namviet.vtvtravel.adapter.vtvtabstyle.VTVTabStyleAdapter
import com.namviet.vtvtravel.databinding.F2FragmentTravelNewsBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.f2event.OnScrollTravelNews
import com.namviet.vtvtravel.model.f2search.Children
import com.namviet.vtvtravel.model.f2search.SortAndFilter
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse
import com.namviet.vtvtravel.response.travelnews.NewsCategoryResponse
import com.namviet.vtvtravel.response.travelnews.NotebookResponse
import com.namviet.vtvtravel.tracking.TrackingAnalytic
import com.namviet.vtvtravel.ultils.F2Util
import com.namviet.vtvtravel.view.f2.f2oldbase.SearchActivity
import com.namviet.vtvtravel.view.f3.search.view.SearchSuggestionForSpecificContentActivity
import com.namviet.vtvtravel.view.fragment.f2search.ChooseRegionFragment
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort.SortFollowFragment
import com.namviet.vtvtravel.view.fragment.f2video.DropDownLocationInVideoFragment
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel
import com.namviet.vtvtravel.viewmodel.f2travelnews.TravelNewsViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

class TravelNewsFragment : BaseFragment<F2FragmentTravelNewsBinding?>, Observer {
    private var travelNewsViewModel: TravelNewsViewModel? = null
    private var listSubTravelNewsFragment: MutableList<SubTravelNewsFragment>? = null
    private var isTravelNews = false
    private var vtvTabStyleAdapter: VTVTabStyleAdapter? = null


    private var sortVideoAdapter: SortVideoAdapter? = null
    private var sortAndFilter: SortAndFilter? = null
    private var locationsMain: ArrayList<Location> = ArrayList()
    private val locations: ArrayList<Location>? = ArrayList()
    private var locationViewModel: SearchBigLocationViewModel? = null

    @SuppressLint("ValidFragment")
    constructor(isTravelNews: Boolean) {
        this.isTravelNews = isTravelNews
    }

    constructor() {}

    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_travel_news
    }

    override fun initView() {
        travelNewsViewModel = TravelNewsViewModel()
        listSubTravelNewsFragment = ArrayList()
        binding!!.travelNewsViewModel = travelNewsViewModel
        travelNewsViewModel!!.addObserver(this)
        if (isTravelNews) {
            travelNewsViewModel!!.getNewsCategory()
            binding!!.tvTitle.text = "Tin tức du lịch"
            binding!!.layoutLearnMore.visibility = View.GONE
        } else {
            travelNewsViewModel!!.getNoteBook()
            binding!!.tvTitle.text = "Sổ tay du lịch"
            binding!!.layoutLearnMore.visibility = View.VISIBLE
        }

        locationViewModel = SearchBigLocationViewModel()
        locationViewModel?.addObserver(this)
        locationViewModel?.getAllLocation()

        filterData
        initSlideMenu()
    }

    override fun initData() {
        binding!!.vpContent.offscreenPageLimit = 10
    }

    override fun inject() {}
    override fun setClickListener() {
        binding!!.btnBack.setOnClickListener { mActivity.onBackPressed() }
        binding!!.btnScrollToTop.setOnClickListener {
            try {
                listSubTravelNewsFragment!![binding!!.vpContent.currentItem].scrollToTop()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        binding!!.btnSearch.setOnClickListener {
            SearchSuggestionForSpecificContentActivity.openScreen(mActivity, "", SearchSuggestionForSpecificContentActivity.Type.NEWS)
        }
        binding!!.vpContent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                binding!!.btnScrollToTop.visibility = View.GONE
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    override fun setObserver() {}
    override fun update(observable: Observable, o: Any) {
        hideLoading()
        if (observable is SearchBigLocationViewModel && null != o) {
            when (o) {
                is AllLocationResponse -> {
                    locationsMain = o.data as ArrayList<Location>;
                    locations?.addAll(0, locationsMain)
                }
//                is LocationResponse -> {
//                    tvRegion.text = o.data.name
//                }
//                is ErrorResponse -> {
//                    val responseError = o
//                    try { //                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
//                    } catch (e: Exception) {
//                    }
//                }
            }
        } else if (observable is TravelNewsViewModel && null != o) {
            if (o is NewsCategoryResponse) {
                val newsCategoryResponse = o
                vtvTabStyleAdapter = VTVTabStyleAdapter(childFragmentManager)
                listSubTravelNewsFragment!!.clear()
                for (i in newsCategoryResponse.data.indices) {
                    val subTravelNewsFragment = SubTravelNewsFragment()
                    subTravelNewsFragment.setContentLink(newsCategoryResponse.data[i].link)
                    subTravelNewsFragment.position = i
                    listSubTravelNewsFragment!!.add(subTravelNewsFragment)
                    vtvTabStyleAdapter!!.addFragment(subTravelNewsFragment, "")
                }
                binding!!.vpContent.adapter = vtvTabStyleAdapter
                binding!!.tabLayout.setupWithViewPager(binding!!.vpContent)
                for (i in newsCategoryResponse.data.indices) {
                    val tabHome = LayoutInflater.from(mActivity)
                        .inflate(R.layout.f2_custom_tab_vtv_style, null)
                    val tvHome = tabHome.findViewById<TextView>(R.id.tvTitle)
                    tvHome.text = newsCategoryResponse.data[i].name
                    if (i == 0) {
                        tvHome.setTextColor(Color.parseColor("#00918D"))
                    } else {
                        tvHome.setTextColor(Color.parseColor("#101010"))
                    }
                    val view = tabHome.findViewById<View>(R.id.indicator)
                    if (i == 0) {
                        view.visibility = View.VISIBLE
                    } else {
                        view.visibility = View.INVISIBLE
                    }
                    binding!!.tabLayout.getTabAt(i)!!.customView = tabHome
                }
                binding!!.tabLayout.addOnTabSelectedListener(OnTabSelectedListener)
            } else if (o is NotebookResponse) {
                val response = o
                vtvTabStyleAdapter = VTVTabStyleAdapter(childFragmentManager)
                listSubTravelNewsFragment!!.clear()
                for (i in response.data.category_notebook.indices) {
                    val subTravelNewsFragment = SubTravelNewsFragment()
                    subTravelNewsFragment.setContentLink(response.data.category_notebook[i].link)
                    subTravelNewsFragment.position = i
                    listSubTravelNewsFragment!!.add(subTravelNewsFragment)
                    vtvTabStyleAdapter!!.addFragment(subTravelNewsFragment, "")
                }
                binding!!.vpContent.adapter = vtvTabStyleAdapter
                binding!!.tabLayout.setupWithViewPager(binding!!.vpContent)
                for (i in response.data.category_notebook.indices) {
                    val tabHome = LayoutInflater.from(mActivity)
                        .inflate(R.layout.f2_custom_tab_vtv_style, null)
                    val tvHome = tabHome.findViewById<TextView>(R.id.tvTitle)
                    tvHome.text = response.data.category_notebook[i].name
                    if (i == 0) {
                        tvHome.setTextColor(Color.parseColor("#00918D"))
                    } else {
                        tvHome.setTextColor(Color.parseColor("#101010"))
                    }
                    val view = tabHome.findViewById<View>(R.id.indicator)
                    if (i == 0) {
                        view.visibility = View.VISIBLE
                    } else {
                        view.visibility = View.INVISIBLE
                    }
                    binding!!.tabLayout.getTabAt(i)!!.customView = tabHome
                }
                binding!!.tabLayout.addOnTabSelectedListener(OnTabSelectedListener)
                val highLightTravelNoteAdapter =
                    HighLightTravelNoteAdapter(mActivity, response.data.items) { travel ->
                        val detailNewsTravelFragment = DetailNewsTravelFragment()
                        detailNewsTravelFragment.setDetailLink(travel.detail_linkV2)
                        addFragment(detailNewsTravelFragment)
                    }
                binding!!.rclHighLight.adapter = highLightTravelNoteAdapter
            } else if (o is ErrorResponse) {
                val responseError = o
                try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (e: Exception) {
                }
            }
        }
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
    fun onScroll(onScrollTravelNews: OnScrollTravelNews) {
        if (onScrollTravelNews.isShow) {
            binding!!.btnScrollToTop.visibility = View.VISIBLE
        } else {
            binding!!.btnScrollToTop.visibility = View.GONE
        }
    }

    private val OnTabSelectedListener: TabLayout.OnTabSelectedListener =
        object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val c = tab.position
                vtvTabStyleAdapter!!.SetOnSelectView(binding!!.tabLayout, c)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val c = tab.position
                vtvTabStyleAdapter!!.SetUnSelectView(binding!!.tabLayout, c)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        }

    override fun setScreenTitle() {
        super.setScreenTitle()
        setDataScreen(
            TrackingAnalytic.ScreenCode.NEWS_DETAIL,
            TrackingAnalytic.ScreenTitle.NEWS_DETAIL
        )
    }

    private val filterData: Unit
        private get() {
            sortAndFilter = Gson().fromJson(
                F2Util.loadJSONFromAsset(
                    mActivity,
                    "filter_and_sort_in_search_video"
                ), SortAndFilter::class.java
            )
            sortVideoAdapter = SortVideoAdapter(
                mActivity,
                sortAndFilter?.sortHeader,
                object : SortVideoAdapter.ClickItem {
                    override fun onClickItem(position: Int) {
                        when (position) {
                            0 -> {
                                val sortFollowFragment = SortFollowFragment()
                                sortFollowFragment.setData(
                                    sortAndFilter!!.sortHeader[0].children,
                                    object : SortFollowFragment.Listener {
                                        override fun onApply(listChild: ArrayList<Children>?) {
                                            sortAndFilter!!.sortHeader[0].children = listChild
                                            hideMenuAnim()
                                            sortVideoAdapter?.notifyDataSetChanged()
                                        }
                                    })

                                fragmentManager!!.beginTransaction()
                                    .replace(R.id.sortFrame, sortFollowFragment).commit()
                            }

                            1 -> {
                                var dropDownLocationFragment = DropDownLocationInVideoFragment()
                                dropDownLocationFragment.setData(object : DropDownLocationInVideoFragment.Callback{
                                    override fun onClickChooseLocation() {
                                        binding!!.drawerLayout.openDrawer(GravityCompat.END)
                                    }

                                    override fun onApply() {

                                    }

                                })
                                fragmentManager!!.beginTransaction()
                                    .replace(R.id.sortFrame, dropDownLocationFragment).commit()
                            }
                        }
                        if (binding!!.layoutExpand.visibility != View.VISIBLE) {
                            showMenuAnim()
                        }
                    }
                })
            binding!!.rclSort.adapter = sortVideoAdapter
        }

    private fun initSlideMenu(){

        binding!!.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerOpened(drawerView: View) {
                createMenuFragment()
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {

            }

        })
    }

    private fun createMenuFragment(){
        fragmentManager?.beginTransaction()
            ?.add(R.id.chooseRegionFrame, ChooseRegionFragment())
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.addToBackStack(null)!!.commit()
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
}