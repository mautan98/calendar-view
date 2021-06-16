package com.namviet.vtvtravel.view.f3.notification.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.F3FragmentNotificationBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f3.notification.adapter.NotificationTabAdapter
import com.ornach.richtext.RichText
import kotlinx.android.synthetic.main.f3_fragment_notification.*

class NotificationFragment : BaseFragment<F3FragmentNotificationBinding?>() {
    private var notificationTabAdapter: NotificationTabAdapter? = null
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_notification
    }

    override fun initView() {}
    override fun initData() {
        notificationTabAdapter = NotificationTabAdapter(childFragmentManager)
        for (i in 0..5) {
            val notificationTabFragment = NotificationTabFragment()
            notificationTabAdapter?.addFragment(notificationTabFragment, "")
        }

        vpContent.adapter = notificationTabAdapter
        tabLayout.setupWithViewPager(vpContent)

        for (i in 0..5) {
            val tabHome = LayoutInflater.from(mActivity).inflate(R.layout.f3_item_tab_notification, null)
            val tvHome = tabHome.findViewById<TextView>(R.id.tvTitle) as RichText
            tvHome.text = "Tất cả"
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
    }

    override fun inject() {}
    override fun setClickListener() {
        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }
    }
    override fun setObserver() {}


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
}