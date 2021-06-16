package com.namviet.vtvtravel.view.f3.notification.view

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.ActivityNotificationBinding
import com.namviet.vtvtravel.f2base.base.BaseActivityNew
import com.namviet.vtvtravel.f2base.base.BaseFragment

class NotificationActivity : BaseActivityNew<ActivityNotificationBinding?>() {
    override fun getLayoutRes(): Int {
        return R.layout.activity_notification
    }

    override fun getFrame(): Int {
        return R.id.mainFrame
    }

    override fun getDataFromIntent() {}
    override fun doAfterOnCreate() {}
    override fun setClick() {}
    override fun initFragment(): BaseFragment<*>? {
        return NotificationFragment( )
    }
}