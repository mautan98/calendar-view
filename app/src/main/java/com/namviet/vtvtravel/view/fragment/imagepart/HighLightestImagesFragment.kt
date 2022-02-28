package com.namviet.vtvtravel.view.fragment.imagepart

import android.annotation.SuppressLint
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2video.SortVideoAdapter
import com.namviet.vtvtravel.adapter.imagepart.HighLightestImagesAdapter
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.databinding.F2FragmentHighLightestImagesBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.f2search.Children
import com.namviet.vtvtravel.model.f2search.Content
import com.namviet.vtvtravel.model.f2search.SortAndFilter
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse
import com.namviet.vtvtravel.response.imagepart.ItemImagePartResponse
import com.namviet.vtvtravel.tracking.TrackingAnalytic
import com.namviet.vtvtravel.ultils.F2Util
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew
import com.namviet.vtvtravel.view.f3.search.view.SearchSuggestionForSpecificContentActivity
import com.namviet.vtvtravel.view.fragment.f2search.ChooseRegionFragment
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort.DropDownLocationFragment
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort.SortFollowFragment
import com.namviet.vtvtravel.view.fragment.f2video.DropDownLocationInVideoFragment
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel
import com.namviet.vtvtravel.viewmodel.imagepart.ImagePartViewModel
import kotlinx.android.synthetic.main.f2_fragment_high_lightest_images.*
import java.util.*

class HighLightestImagesFragment : BaseFragment<F2FragmentHighLightestImagesBinding?>, Observer {
    private var highLightestImagesAdapter: HighLightestImagesAdapter? = null
    private val items: MutableList<ItemImagePartResponse.Data.Item> = ArrayList()
    private var viewModel: ImagePartViewModel? = null
    private var loadMoreLink: String? = null


    private var sortVideoAdapter: SortVideoAdapter? = null
    private var sortAndFilter: SortAndFilter? = null
    private var locationsMain: ArrayList<Location> = ArrayList()
    private val locations: ArrayList<Location>? = ArrayList()
    private var locationViewModel: SearchBigLocationViewModel? = null

    private var dropDownLocationFragment : DropDownLocationInVideoFragment? = null

    @SuppressLint("ValidFragment")
    constructor(idGallery: String?) {
        this.idGallery = idGallery
    }

    constructor() {}

    private var idGallery: String? = null
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_high_lightest_images
    }

    override fun initView() {
        locationViewModel = SearchBigLocationViewModel()
        locationViewModel?.addObserver(this)
        locationViewModel?.getAllLocation()

        filterData
        initSlideMenu()
    }
    override fun initData() {
        viewModel = ImagePartViewModel()
        binding!!.imagePartViewModel = viewModel
        viewModel!!.addObserver(this)
        viewModel!!.getItemGallery(idGallery)

//        itemImagePartResponse = new Gson().fromJson(json, ItemImagePartResponse.class);
        highLightestImagesAdapter = HighLightestImagesAdapter(
            items,
            mActivity,
            object : HighLightestImagesAdapter.ClickItem {
                override fun onClickItem(position: Int) {}
                override fun likeEvent(position: Int) {
                    try {
                        val account = MyApplication.getInstance().account
                        val video = items[position]
                        if (null != account && account.isLogin) {
                            viewModel!!.likeEvent(video.id, video.content_type)
                            try {
                                TrackingAnalytic.postEvent(
                                    TrackingAnalytic.LIKE,
                                    TrackingAnalytic.getDefault(
                                        TrackingAnalytic.ScreenCode.HIGH_LIGHTEST_IMAGE,
                                        TrackingAnalytic.ScreenTitle.HIGH_LIGHTEST_IMAGE
                                    )
                                        .setContent_type(video.content_type)
                                        .setContent_id(video.id)
                                        .setScreen_class(this.javaClass.name)
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            if (video.isLiked) {
                                video.isLiked = false
                                if (null != video.likeCount) {
                                    val likeCount = (video.likeCount.toInt() - 1).toString() + ""
                                    video.likeCount = likeCount
                                }
                            } else {
                                video.isLiked = true
                                if (null != video.likeCount) {
                                    val likeCount = (video.likeCount.toInt() + 1).toString() + ""
                                    video.likeCount = likeCount
                                }
                            }
                            highLightestImagesAdapter!!.notifyItemChanged(position)
                        } else {
                            LoginAndRegisterActivityNew.startScreen(mActivity, 0, false)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })
        binding!!.rclContent.adapter = highLightestImagesAdapter
        binding!!.rclContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel!!.getGalleryByUrl(loadMoreLink, true)
                    loadMoreLink = ""
                }
            }
        })
    }

    override fun inject() {}
    override fun setClickListener() {
        binding!!.btnBack.setOnClickListener { mActivity.onBackPressed() }

        btnSearch.setOnClickListener {
            SearchSuggestionForSpecificContentActivity.openScreen(mActivity, "", SearchSuggestionForSpecificContentActivity.Type.IMAGE, false)
        }
    }

    override fun setObserver() {}
    override fun update(observable: Observable, o: Any) {
        hideLoading()
        try {
            binding!!.shimmerViewContainer.stopShimmer()
            binding!!.shimmerViewContainer.visibility = View.GONE
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
        } else if (observable is ImagePartViewModel && null != o) {
            if (o is ItemImagePartResponse) {
                val response = o
                loadMoreLink = response.data.more_link
                if (response.isLoadMore) {
                    items.addAll(response.data.items)
                } else {
                    items.clear()
                    items.addAll(response.data.items)
                }
                highLightestImagesAdapter!!.notifyDataSetChanged()
            } else if (o is ErrorResponse) {
                val responseError = o
                try {
//                    ((LoginAndRegisterActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (e: Exception) {
                }
            }
        }
    }

    override fun setScreenTitle() {
        super.setScreenTitle()
        setDataScreen(
            TrackingAnalytic.ScreenCode.HIGH_LIGHTEST_IMAGE,
            TrackingAnalytic.ScreenTitle.HIGH_LIGHTEST_IMAGE
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
                                dropDownLocationFragment = DropDownLocationInVideoFragment()
                                dropDownLocationFragment?.setData(object : DropDownLocationInVideoFragment.Callback{
                                    override fun onClickChooseLocation() {
                                        binding!!.drawerLayout.openDrawer(GravityCompat.END)
                                    }

                                    override fun onApply(content: Content) {

                                    }

                                })
                                fragmentManager!!.beginTransaction()
                                    .replace(R.id.sortFrame, dropDownLocationFragment!!).commit()
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
        var chooseRegionFragment = ChooseRegionFragment();
        chooseRegionFragment.setData(sortAndFilter!!.sortHeader[1].content, locations, object : ChooseRegionFragment.ChooseRegion{
            override fun clickRegion(content: Content?) {
                drawerLayout.closeDrawer(GravityCompat.END)
                sortAndFilter!!.sortHeader[1].content = content
                dropDownLocationFragment?.setData(content!!)

            }
        })
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