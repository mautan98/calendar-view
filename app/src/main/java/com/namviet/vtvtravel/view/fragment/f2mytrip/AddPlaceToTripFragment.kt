package com.namviet.vtvtravel.view.fragment.f2mytrip

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayout
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter
import com.namviet.vtvtravel.config.Constants
import com.namviet.vtvtravel.databinding.FragmentAddPlaceToTripBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.listener.OnBackToFragmentListener
import com.namviet.vtvtravel.view.f3.model.ClickHideMapView
import com.namviet.vtvtravel.view.f3.model.HideMapView
import com.namviet.vtvtravel.view.f3.model.ShowMapView
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.model.ItemPlaces
import kotlinx.android.synthetic.main.fragment_add_place_to_trip.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class AddPlaceToTripFragment : BaseFragment<FragmentAddPlaceToTripBinding?>(),
    OnBackToFragmentListener {
    private var mainAdapter : MainAdapter? = null

    private val typeDestination = Constants.TypeDestination.PLACES
    private val link: String? = null
    private var scheduleCustomId: String? = null
    private var placesScheduleItem: ItemPlaces? = null
    private var onBackToFragmentListener: OnBackToFragmentListener? = null

    public fun setData(scheduleCustomId: String?){
        this.scheduleCustomId = scheduleCustomId
    }

    fun setPlaceScheduleItem(placesScheduleItem: ItemPlaces?){
        this.placesScheduleItem = placesScheduleItem
    }

    fun setOnBackToFragmentListener(onBackToFragmentListener: OnBackToFragmentListener?) {
        this.onBackToFragmentListener = onBackToFragmentListener
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_add_place_to_trip
    }

    override fun initView() {
    }
    override fun initData() {



        mainAdapter = MainAdapter(childFragmentManager)
        binding!!.vpContent.offscreenPageLimit = 10

        var addPlaceToTripChildFragment1 =
            AddPlaceToTripChildFragment("APP_WHERE_GO", "", scheduleCustomId);
        addPlaceToTripChildFragment1.setOnBackToFragmentListener(this)
        addPlaceToTripChildFragment1.placesScheduleItem = placesScheduleItem
        mainAdapter?.addFragment(addPlaceToTripChildFragment1, "")

        var addPlaceToTripChildFragment2 =
            AddPlaceToTripChildFragment("APP_WHERE_STAY", "", scheduleCustomId);
        addPlaceToTripChildFragment2.placesScheduleItem = placesScheduleItem
        addPlaceToTripChildFragment2.setOnBackToFragmentListener(this)
        mainAdapter?.addFragment(addPlaceToTripChildFragment2, "")

        var addPlaceToTripChildFragment3 =
            AddPlaceToTripChildFragment("APP_WHAT_EAT", "", scheduleCustomId);
        addPlaceToTripChildFragment3.placesScheduleItem = placesScheduleItem
        addPlaceToTripChildFragment3.setOnBackToFragmentListener(this)
        mainAdapter?.addFragment(addPlaceToTripChildFragment3, "")

        var addPlaceToTripChildFragment4 =
            AddPlaceToTripChildFragment("APP_WHAT_PLAY", "", scheduleCustomId);
        addPlaceToTripChildFragment4.placesScheduleItem = placesScheduleItem
        addPlaceToTripChildFragment4.setOnBackToFragmentListener(this)
        mainAdapter?.addFragment(addPlaceToTripChildFragment4, "")

        binding!!.vpContent.adapter = mainAdapter


        binding!!.tabLayout.setTabTextColors(ContextCompat.getColor(mActivity, R.color.md_black_1000), ContextCompat.getColor(mActivity, R.color.f2_color_package))
        binding!!.tabLayout.setupWithViewPager(binding!!.vpContent)

//        for(i in 0 until dataMenu!!.size){
//            tabLayout.getTabAt(i)?.text = dataMenu?.get(i)?.name
//        }

        binding!!.tabLayout.getTabAt(0)?.text = "Đi đâu"
        binding!!.tabLayout.getTabAt(1)?.text = "Ở đâu"
        binding!!.tabLayout.getTabAt(2)?.text = "Ăn gì"
        binding!!.tabLayout.getTabAt(3)?.text = "Chơi gì"




        val tabLayout1 = (binding!!.tabLayout.getChildAt(0)as ViewGroup).getChildAt(
            0
        ) as ViewGroup
        val tabTextView = tabLayout1.getChildAt(1) as TextView
        val typeface = ResourcesCompat.getFont(mActivity, R.font.roboto_medium)
        tabTextView.typeface = typeface

        binding!!.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabLayout = (binding!!.tabLayout.getChildAt(0)as ViewGroup).getChildAt(
                    tab!!.position
                ) as ViewGroup
                val tabTextView = tabLayout.getChildAt(1) as TextView
                val typeface = ResourcesCompat.getFont(mActivity, R.font.roboto_medium)
                tabTextView.typeface = typeface
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabLayout = (binding!!.tabLayout.getChildAt(0)as ViewGroup).getChildAt(
                    tab!!.position
                ) as ViewGroup
                val tabTextView = tabLayout.getChildAt(1) as TextView
                val typeface = ResourcesCompat.getFont(mActivity, R.font.roboto_regular)
                tabTextView.typeface = typeface
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
    override fun inject() {}
    override fun setClickListener() {
        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }

        btnBackmap.setOnClickListener {
            layout_header.visibility = View.VISIBLE
            rll_header_map.visibility = View.GONE
            tabLayout.visibility = View.VISIBLE
            EventBus.getDefault().post(ClickHideMapView())
        }
    }
    override fun setObserver() {}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    public fun showMapView(showMapView: ShowMapView){
        layout_header.visibility = View.GONE
        tabLayout.visibility = View.GONE
        rll_header_map.visibility = View.VISIBLE
    }
    @Subscribe
    public fun hideMapView(hideMapView: HideMapView){
        layout_header.visibility = View.VISIBLE
        tabLayout.visibility = View.VISIBLE
        rll_header_map.visibility = View.GONE
    }

    override fun onBack() {

    }
}