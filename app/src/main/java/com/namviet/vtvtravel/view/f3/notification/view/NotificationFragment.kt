package com.namviet.vtvtravel.view.f3.notification.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.F3FragmentNotificationBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse
import com.namviet.vtvtravel.response.f2biglocation.LocationResponse
import com.namviet.vtvtravel.response.f2systeminbox.CountSystemInbox
import com.namviet.vtvtravel.view.f3.notification.NotificationViewModel
import com.namviet.vtvtravel.view.f3.notification.adapter.NotificationTabAdapter
import com.namviet.vtvtravel.view.f3.notification.model.ui.NotificationTab
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel
import com.namviet.vtvtravel.viewmodel.f2systeminbox.SystemInboxViewModel
import com.ornach.richtext.RichText
import kotlinx.android.synthetic.main.f2_fragment_search.*
import kotlinx.android.synthetic.main.f3_fragment_notification.*
import kotlinx.android.synthetic.main.f3_fragment_notification.btnBack
import kotlinx.android.synthetic.main.f3_fragment_notification.tabLayout
import kotlinx.android.synthetic.main.f3_fragment_notification.vpContent
import java.util.*
import kotlin.collections.ArrayList

class NotificationFragment : BaseFragment<F3FragmentNotificationBinding?>(), Observer {
    private var notificationViewModel : NotificationViewModel? = null
    private var notificationTabAdapter: NotificationTabAdapter? = null
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_notification
    }

    override fun initView() {
        notificationViewModel = NotificationViewModel();
        notificationViewModel?.addObserver(this)
        notificationViewModel?.getNotificationTab()
        notificationViewModel?.getSystemInboxCount()


    }
    override fun initData() {

    }

    override fun inject() {}
    override fun setClickListener() {
        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }

        btnViewAll.setOnClickListener {
            for (i in listTab.iterator()){
                i.viewAllNotification()
            }
        }
    }
    override fun setObserver() {}

    private var listTab = ArrayList<NotificationTabFragment>()
    private fun genViewPager(notificationTab: NotificationTab){
        var data = NotificationTab().Data(null, "1", "", "Tất cả", "1000", true)
        var data1 = NotificationTab().Data(null, "1", "", "Chưa đọc", "1001", true)
        var data2 = NotificationTab().Data(null, "1", "", "Đã lưu", "1002", true)
        notificationTab.data.add(0, data1)
        notificationTab.data.add(0, data2)
        notificationTab.data.add(0, data)
        notificationTabAdapter = NotificationTabAdapter(childFragmentManager)
        for (i in 0 until  notificationTab.data.size) {
            val notificationTabFragment = NotificationTabFragment(notificationTab.data[i].isNotType, notificationTab.data[i].id)
            notificationTabAdapter?.addFragment(notificationTabFragment, "")
            listTab.add(notificationTabFragment)
        }

        vpContent.adapter = notificationTabAdapter
        tabLayout.setupWithViewPager(vpContent)

        for (i in 0 until  notificationTab.data.size) {
            val tabHome = LayoutInflater.from(mActivity).inflate(R.layout.f3_item_tab_notification, null)
            val tvHome = tabHome.findViewById<TextView>(R.id.tvTitle) as RichText
            tvHome.text = notificationTab.data[i].name
            if (i == 0) {
                tvHome.setTextColor(Color.parseColor("#FFFFFF"))
                tvHome.backgroundColor = Color.parseColor("#00918D")
            } else {
                tvHome.setTextColor(Color.parseColor("#000000"))
                tvHome.backgroundColor = Color.parseColor("#ECECEC")
            }
            tabLayout.getTabAt(i)!!.customView = tabHome
        }

        tabLayout.addOnTabSelectedListener(onTabSelectedListener)
        vpContent.offscreenPageLimit = 10
    }


    private val onTabSelectedListener: TabLayout.OnTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            val c = tab.position
            notificationTabAdapter?.setOnSelectView(tabLayout, c)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            val c = tab.position
            notificationTabAdapter?.setUnSelectView(tabLayout, c)
        }

        override fun onTabReselected(tab: TabLayout.Tab) {}
    }

    override fun update(observable: Observable?, o: Any?) {
        if (observable is NotificationViewModel && null != o) {
            when (o) {
                is NotificationTab -> {
                    genViewPager(o)
                }

                is CountSystemInbox -> {
                    try {
                        if(o.data.count ==  "0"){
                            tvCountMessage.text = "Thông báo"
                        }else {
                            tvCountMessage.text = "Thông báo(" + o.data.count + ")"
                        }
                    } catch (e: Exception) {
                    }
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
}