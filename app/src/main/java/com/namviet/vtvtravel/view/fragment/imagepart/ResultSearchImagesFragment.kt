package com.namviet.vtvtravel.view.fragment.imagepart

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.google.gson.Gson
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2search.CategorySortedAdapter
import com.namviet.vtvtravel.adapter.f2video.SortVideoAdapter
import com.namviet.vtvtravel.adapter.imagepart.HighLightestImagesAdapter
import com.namviet.vtvtravel.databinding.F3FragmentSearchResultImagesBinding
import com.namviet.vtvtravel.databinding.F3FragmentSearchResultNewsBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.f2event.OnCloseDrawerLayout
import com.namviet.vtvtravel.model.f2search.Children
import com.namviet.vtvtravel.model.f2search.Content
import com.namviet.vtvtravel.model.f2search.SortAndFilter
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse
import com.namviet.vtvtravel.response.f2searchmain.result.SearchType
import com.namviet.vtvtravel.response.imagepart.ItemImagePartResponse
import com.namviet.vtvtravel.ultils.F2Util
import com.namviet.vtvtravel.ultils.highlight.HighLightController
import com.namviet.vtvtravel.ultils.highlight.SearchHighLightText
import com.namviet.vtvtravel.view.f3.search.view.SearchSuggestionForSpecificContentActivity
import com.namviet.vtvtravel.view.f3.search.view.SearchSuggestionForSpecificContentFragment
import com.namviet.vtvtravel.view.fragment.f2search.ChooseRegionFragment
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort.DropDownCategoryFragment
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort.SortFollowFragment
import com.namviet.vtvtravel.view.fragment.f2video.DropDownLocationInVideoFragment
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel
import com.namviet.vtvtravel.viewmodel.f2search.SearchResultViewModel
import kotlinx.android.synthetic.main.f2_fragment_high_lightest_images.*
import kotlinx.android.synthetic.main.f3_fragment_search_result_images.*
import kotlinx.android.synthetic.main.f3_fragment_search_result_images.btnBack
import kotlinx.android.synthetic.main.f3_fragment_search_result_images.drawerLayout
import kotlinx.android.synthetic.main.f3_fragment_search_result_images.edtSearch
import kotlinx.android.synthetic.main.f3_fragment_search_result_images.imgCloseSearch
import kotlinx.android.synthetic.main.f3_fragment_search_result_images.layoutExpand
import kotlinx.android.synthetic.main.f3_fragment_search_result_images.rclContent
import kotlinx.android.synthetic.main.f3_fragment_search_result_images.tvCountResult
import kotlinx.android.synthetic.main.f3_fragment_search_result_video.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

class ResultSearchImagesFragment : BaseFragment<F3FragmentSearchResultImagesBinding?>(), Observer {

    private var sortVideoAdapter: SortVideoAdapter? = null
    private var sortAndFilter: SortAndFilter? = null
    private var locationsMain: ArrayList<Location> = ArrayList()
    private val locations: ArrayList<Location>? = ArrayList()
    private var locationViewModel: SearchBigLocationViewModel? = null
    private var searchViewModel: SearchResultViewModel? = null

    private var keyword: String? = ""
    private var regionId: String? = null
    private var categoryId: String? = null

    private var highLightestImagesAdapter: HighLightestImagesAdapter? = null
    private val travels: MutableList<ItemImagePartResponse.Data.Item> = ArrayList()
    private var categorySortedAdapter: CategorySortedAdapter? = null
    private var dropDownLocationFragment : DropDownLocationInVideoFragment? = null


    public fun setData(keyword: String?, regionId: String?, categoryId: String?){
        this.keyword = keyword
        this.regionId = regionId
        this.categoryId = categoryId
    }


    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_search_result_images
    }

    override fun initView() {
        edtSearch.text = keyword
        searchViewModel = SearchResultViewModel()
        searchViewModel?.addObserver(this)

        locationViewModel = SearchBigLocationViewModel()
        locationViewModel?.addObserver(this)
        locationViewModel?.getAllLocation()

        filterData
        initSlideMenu()

        highLightestImagesAdapter = HighLightestImagesAdapter(travels, mActivity, null)
        rclContent.adapter = highLightestImagesAdapter


        searchAll(SearchType.IMAGE)


    }

    override fun initData() {}
    override fun inject() {}
    override fun setClickListener() {
        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }

        imgCloseSearch.setOnClickListener {
            mActivity.onBackPressed()
        }

        layoutExpand.setOnClickListener {
            hideMenuAnim()
        }

        edtSearch.setOnClickListener {
//            SearchSuggestionForSpecificContentActivity.openScreen(mActivity, keyword, SearchSuggestionForSpecificContentActivity.Type.IMAGE, true)
            addFragment(SearchSuggestionForSpecificContentFragment( keyword, SearchSuggestionForSpecificContentActivity.Type.IMAGE, true))
        }
    }
    override fun setObserver() {}
    override fun update(observable: Observable, o: Any?) {
        if (observable is SearchBigLocationViewModel && null != o) {
            when (o) {
                is AllLocationResponse -> {
                    locationsMain = o.data as ArrayList<Location>;
                    locations?.addAll(0, locationsMain)
                }

            }
        } else if (observable is SearchResultViewModel && null != o) {
            try {
                when (o) {
                    is ItemImagePartResponse -> {
//                        resultVideosSearchFragment?.setList(
//                            o.data.items as ArrayList<Video>?,
//                            o.data.more_link,
//                            o.data.total,
//                            keyword!!,
//                            o.data.approximately
//                        )

                        travels?.let { this.travels?.addAll(o.data.items) }
//                        this.moreLink = moreLink
                        highLightestImagesAdapter?.notifyDataSetChanged()
                        if(!o.data.approximately) {
                            tvCountResult.text = "C?? ${o.data.items.size} k???t qu??? t??m ki???m g??c ???nh kh???p v???i \"$keyword\""
                            setHighLightedText(tvCountResult, "\"$keyword\"")
                        }else{
                            tvCountResult.text = "C?? ${o.data.items.size} k???t qu??? t??m ki???m g??c ???nh g???n ????ng kh???p v???i \"$keyword\""
                            setHighLightedText(tvCountResult, "\"$keyword\"")
                        }

                    }

                    is ErrorResponse -> {
                        val responseError = o
                        try {
                        } catch (e: Exception) {
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    public fun setHighLightedText(tv: TextView, textToHighlight: String) {
        var iHighLightText = SearchHighLightText()
        var highLightController = HighLightController(iHighLightText)
        highLightController.highLight(context!!, tv, textToHighlight)
    }

    public fun searchAll(type: String?) {
        searchViewModel?.searchAllImage("news", keyword, regionId, type, categoryId)
    }

    public fun searchAllWithLink(link: String?, type: String?) {
        searchViewModel?.searchAllImageWithFullLink(link, "news")
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
                                            getParamAndSearch()
                                        }
                                    })

                                fragmentManager!!.beginTransaction()
                                    .replace(R.id.sortFrame, sortFollowFragment).commit()
                            }

                            1 -> {
                                dropDownLocationFragment = DropDownLocationInVideoFragment()
                                dropDownLocationFragment?.setData(object :
                                    DropDownLocationInVideoFragment.Callback {
                                    override fun onClickChooseLocation() {
                                        binding!!.drawerLayout.openDrawer(GravityCompat.END)
                                    }

                                    override fun onApply(content: Content) {
                                        hideMenuAnim()
                                        sortAndFilter!!.sortHeader[1].content = content
                                        sortVideoAdapter?.notifyDataSetChanged()
                                        getParamAndSearch()
                                    }

                                })
                                dropDownLocationFragment?.setData(sortAndFilter!!.sortHeader[1].content)
                                fragmentManager!!.beginTransaction()
                                    .replace(R.id.sortFrame, dropDownLocationFragment!!).commit()
                            }

                            2 -> {
                                var dropDownCategoryFragment = DropDownCategoryFragment()
                                dropDownCategoryFragment.setData(sortAndFilter!!.sortHeader[2].children, object : DropDownCategoryFragment.Listener{
                                    override fun onApply(listChild: ArrayList<Children>?) {
                                        sortAndFilter!!.sortHeader[2].children.clear()
                                        sortAndFilter!!.sortHeader[2].children.addAll(listChild!!)
                                        hideMenuAnim()
                                        categorySortedAdapter?.notifyDataSetChanged()
                                        sortVideoAdapter?.notifyDataSetChanged()
                                        getParamAndSearch()
                                    }

                                })
                                fragmentManager!!.beginTransaction()
                                    .replace(R.id.sortFrame, dropDownCategoryFragment).commit()
                            }
                        }
                        if (binding!!.layoutExpand.visibility != View.VISIBLE) {
                            showMenuAnim()
                        }
                    }
                })
            binding!!.rclSort.adapter = sortVideoAdapter

            categorySortedAdapter = CategorySortedAdapter(sortAndFilter!!.sortHeader[2].children, mActivity, object : CategorySortedAdapter.ClickItem{
                override fun onClickItem() {
                    sortVideoAdapter?.notifyDataSetChanged()
                }

            });
            binding!!.rclCategorySorted.adapter = categorySortedAdapter
        }

    private fun initSlideMenu() {

        binding!!.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerOpened(drawerView: View) {
                createMenuFragment()
            }

            override fun onDrawerClosed(drawerView: View) {
                chooseRegionFragment?.deleteFragment()
            }

            override fun onDrawerStateChanged(newState: Int) {

            }

        })
    }

    private var chooseRegionFragment : ChooseRegionFragment? = null

    private fun createMenuFragment() {
        chooseRegionFragment = ChooseRegionFragment();
        chooseRegionFragment?.setData(sortAndFilter!!.sortHeader[1].content, locations, object : ChooseRegionFragment.ChooseRegion{
            override fun clickRegion(content: Content?) {
                drawerLayout.closeDrawer(GravityCompat.END)
                sortAndFilter!!.sortHeader[1].content = content
                dropDownLocationFragment?.setData(content!!)

            }
        })
        fragmentManager?.beginTransaction()
            ?.add(R.id.chooseRegionFrame, chooseRegionFragment!!)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.addToBackStack(null)!!
            .commit()
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


    @Subscribe
    public fun onDoneSearchSuggestion(onDone : SearchSuggestionForSpecificContentFragment.Done){
        if(onDone.type == SearchSuggestionForSpecificContentActivity.Type.IMAGE){
            keyword = onDone.keyword
            categoryId = if (onDone.searchKeywordSuggestion == null)  null else onDone!!.searchKeywordSuggestion!!.categoryCode
            edtSearch.text = keyword
            searchAll(SearchType.IMAGE)
        }
    }

    @Subscribe
    public fun onCloseDrawer(onDone: OnCloseDrawerLayout) {
        try {
            drawerLayout.closeDrawer(GravityCompat.END, true)
        } catch (e: Exception) {
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

    private fun getParamAndSearch(){
        var sortParam = ""
        for (i in 0 until sortAndFilter!!.sortHeader[0].children.size){
            if(sortAndFilter!!.sortHeader[0].children[i].isSelected){
                sortParam = sortAndFilter!!.sortHeader[0].children[i].id
                break
            }
        }

        Log.e("sortParam", sortParam)

        Log.e("cityID", if(sortAndFilter!!.sortHeader[1].content.cityId != null) sortAndFilter!!.sortHeader[1].content.cityId else "null")

        var categoryParam = ""


        for (i in 0 until sortAndFilter!!.sortHeader[2].children.size){
            if(sortAndFilter!!.sortHeader[2].children[i].isSelected){
                categoryParam = categoryParam + ","+sortAndFilter!!.sortHeader[2].children[i].id
            }
        }

        Log.e("categoryParam", categoryParam)

    }
}