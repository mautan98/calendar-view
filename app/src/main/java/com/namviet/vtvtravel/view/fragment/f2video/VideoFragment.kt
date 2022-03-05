package com.namviet.vtvtravel.view.fragment.f2video

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2video.SortVideoAdapter
import com.namviet.vtvtravel.adapter.vtvtabstyle.VTVTabStyleAdapter
import com.namviet.vtvtravel.databinding.F2FragmentVideoBinding
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.f2search.Children
import com.namviet.vtvtravel.model.f2search.Content
import com.namviet.vtvtravel.model.f2search.SortAndFilter
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse
import com.namviet.vtvtravel.response.f2video.VideoResponse
import com.namviet.vtvtravel.tracking.TrackingAnalytic
import com.namviet.vtvtravel.ultils.F2Util
import com.namviet.vtvtravel.view.f3.search.view.SearchSuggestionForSpecificContentActivity
import com.namviet.vtvtravel.view.f3.search.view.SearchSuggestionForSpecificContentFragment
import com.namviet.vtvtravel.view.fragment.MainFragment
import com.namviet.vtvtravel.view.fragment.f2search.ChooseRegionFragment
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort.SortFollowFragment
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel
import com.namviet.vtvtravel.viewmodel.f2video.VideoViewModel
import java.util.*

class VideoFragment : MainFragment(), Observer {
    private var binding: F2FragmentVideoBinding? = null
    private var viewModel: VideoViewModel? = null
    private var videoResponse: VideoResponse? = null
    private var sortVideoAdapter: SortVideoAdapter? = null
    private var sortAndFilter: SortAndFilter? = null
    private var locationsMain: ArrayList<Location> = ArrayList()
    private val locations: ArrayList<Location>? = ArrayList()
    private var locationViewModel: SearchBigLocationViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_video, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            TrackingAnalytic.postEvent(
                TrackingAnalytic.SCREEN_VIEW,
                TrackingAnalytic.getDefault(
                    TrackingAnalytic.ScreenCode.VIDEOS,
                    TrackingAnalytic.ScreenTitle.VIDEOS
                ).setScreen_class(this.javaClass.name)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        initViews(view)
    }

    override fun initViews(v: View) {
        super.initViews(v)
        binding!!.vpContent.offscreenPageLimit = 10
        viewModel = VideoViewModel()
        binding!!.videoViewModel = viewModel
        viewModel!!.addObserver(this)

        locationViewModel = SearchBigLocationViewModel()
        locationViewModel?.addObserver(this)
        locationViewModel?.getAllLocation()

        Handler().postDelayed({ viewModel!!.getCategoryVideo() }, 500)
        binding!!.btnSearch.setOnClickListener {
            fragmentManager?.beginTransaction()?.add(R.id.frameVideoSearch, SearchSuggestionForSpecificContentFragment("", SearchSuggestionForSpecificContentActivity.Type.VIDEO, false))?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.addToBackStack(null)?.commit()
            fragmentManager?.beginTransaction()?.add(R.id.frameVideoSearch, SearchSuggestionForSpecificContentFragment("", SearchSuggestionForSpecificContentActivity.Type.VIDEO, false))?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.addToBackStack(null)?.commit()
        }
        filterData
        initSlideMenu()
    }

    private var mainAdapter: VTVTabStyleAdapter? = null
    override fun update(observable: Observable, any: Any) {
        Handler().postDelayed({
            try {
                binding!!.shimmerViewContainer.stopShimmer()
                binding!!.shimmerViewContainer.visibility = View.GONE
                binding!!.vpContent.visibility = View.VISIBLE
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, 1000)
        if (observable is SearchBigLocationViewModel && null != any) {
            when (any) {
                is AllLocationResponse -> {
                    locationsMain = any.data as ArrayList<Location>;
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
        } else if (observable is VideoViewModel && null != any) {
            if (any is VideoResponse) {
                videoResponse = any
                mainAdapter = VTVTabStyleAdapter(childFragmentManager)
                for (i in videoResponse!!.data.indices) {
                    val subVideoFragment = SubVideoFragment()
                    subVideoFragment.setContentLink(videoResponse!!.data[i].link)
                    mainAdapter!!.addFragment(subVideoFragment, "")
                }
                binding!!.vpContent.adapter = mainAdapter
                binding!!.tabLayout.setupWithViewPager(binding!!.vpContent)
                for (i in videoResponse!!.data.indices) {
                    val tabHome = LayoutInflater.from(mActivity)
                        .inflate(R.layout.f2_custom_tab_vtv_style, null)
                    val tvHome = tabHome.findViewById<TextView>(R.id.tvTitle)
                    tvHome.text = videoResponse!!.data[i].name
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
            }
        } else if (any is ErrorResponse) {
            val responseError = any
            try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
            } catch (e: Exception) {
            }
        }
    }

    private val OnTabSelectedListener: TabLayout.OnTabSelectedListener =
        object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val c = tab.position
                mainAdapter!!.SetOnSelectView(binding!!.tabLayout, c)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val c = tab.position
                mainAdapter!!.SetUnSelectView(binding!!.tabLayout, c)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        }

    //                                hideMenuAnim()
//                                sortAdapter?.notifyDataSetChanged()
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

                                    override fun onApply(content: Content) {

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
        var chooseRegionFragment = ChooseRegionFragment()
        chooseRegionFragment.setData(null, locations, null)
        fragmentManager?.beginTransaction()
            ?.add(R.id.chooseRegionFrame, chooseRegionFragment)
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