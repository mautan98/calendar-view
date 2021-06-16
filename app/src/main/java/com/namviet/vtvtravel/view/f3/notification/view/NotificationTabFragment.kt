package com.namviet.vtvtravel.view.f3.notification.view

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.F3FragmentNotificationTabBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f3.notification.adapter.NotificationAdapter
import kotlinx.android.synthetic.main.f3_fragment_notification_tab.*

class NotificationTabFragment : BaseFragment<F3FragmentNotificationTabBinding?>() {
    private var notificationAdapter : NotificationAdapter? = null;
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_notification_tab
    }

    override fun initView() {}
    override fun initData() {
        notificationAdapter = NotificationAdapter(mActivity, object : NotificationAdapter.NotificationCallback{
            override fun onClickItemMenu() {
                var menuItemNotifyDialog = MenuItemNotifyDialog();
                menuItemNotifyDialog.setImenuItemNotifyClick {

                }
                menuItemNotifyDialog.show(childFragmentManager, "")
            }

        });
        rclContent.adapter = notificationAdapter
    }
    override fun inject() {}
    override fun setClickListener() {}
    override fun setObserver() {}
}