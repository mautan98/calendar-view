package com.namviet.vtvtravel.view.fragment.f2video

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.google.gson.Gson
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2video.SortVideoAdapter
import com.namviet.vtvtravel.databinding.F3FragmentSearchResultVideoBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.f2search.Children
import com.namviet.vtvtravel.model.f2search.SortAndFilter
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse
import com.namviet.vtvtravel.ultils.F2Util
import com.namviet.vtvtravel.view.fragment.f2search.ChooseRegionFragment
import com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort.SortFollowFragment
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel
import java.util.*

class ResultSearchVideoFragment : BaseFragment<F3FragmentSearchResultVideoBinding?>(), Observer {

    private var sortVideoAdapter: SortVideoAdapter? = null
    private var sortAndFilter: SortAndFilter? = null
    private var locationsMain: ArrayList<Location> = ArrayList()
    private val locations: ArrayList<Location>? = ArrayList()
    private var locationViewModel: SearchBigLocationViewModel? = null
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_search_result_video
    }

    override fun initView() {
        locationViewModel = SearchBigLocationViewModel()
        locationViewModel?.addObserver(this)
        locationViewModel?.getAllLocation()

        filterData
        initSlideMenu()
    }
    override fun initData() {}
    override fun inject() {}
    override fun setClickListener() {}
    override fun setObserver() {}
    override fun update(observable: Observable, o: Any) {
        if (observable is SearchBigLocationViewModel && null != o) {
            when (o) {
                is AllLocationResponse -> {
                    locationsMain = o.data as ArrayList<Location>;
                    locations?.addAll(0, locationsMain)
                }

            }
        }
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