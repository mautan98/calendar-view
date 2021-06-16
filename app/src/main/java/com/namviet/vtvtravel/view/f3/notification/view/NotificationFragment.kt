package com.namviet.vtvtravel.view.f3.notification.view

import com.google.android.material.tabs.TabLayout
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.F3FragmentNotificationBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f3.notification.adapter.NotificationTabAdapter
import kotlinx.android.synthetic.main.f3_fragment_notification.*

class NotificationFragment : BaseFragment<F3FragmentNotificationBinding?>() {
    private var notificationTabAdapter : NotificationTabAdapter? = null
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_notification
    }

    override fun initView() {}
    override fun initData() {
        notificationTabAdapter = NotificationTabAdapter(childFragmentManager)
        for (i in 1..5) {
            val notificationTabFragment = NotificationTabFragment()
            notificationTabAdapter?.addFragment(notificationTabFragment, "")
        }

        vpContent.adapter = notificationTabAdapter
        tabLayout.setupWithViewPager(vpContent)

        tabLayout.addOnTabSelectedListener(onTabSelectedListener)
    }
    override fun inject() {}
    override fun setClickListener() {}
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