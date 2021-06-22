package com.namviet.vtvtravel.view.f3.notification.view

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.F3FragmentNotificationTabBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f3.notification.NotificationViewModel
import com.namviet.vtvtravel.view.f3.notification.adapter.NotificationAdapter
import kotlinx.android.synthetic.main.f3_fragment_notification_tab.*
import java.util.*
import javax.inject.Inject

class NotificationTabFragment : BaseFragment<F3FragmentNotificationTabBinding?>, Observer {
    private var isType: Boolean = false;
    private var type: String? = null;


    private var notificationViewModel: NotificationViewModel? = null

    constructor(isType: Boolean, type: String?) {
        this.isType = isType;
        this.type = type;
    }

    constructor()

    private var notificationAdapter: NotificationAdapter? = null;
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_notification_tab
    }

    override fun initView() {
        notificationViewModel = NotificationViewModel();
        notificationViewModel?.addObserver(this)
        notificationViewModel?.getNotificationByType("SYSTEM")
    }

    override fun initData() {
        notificationAdapter = NotificationAdapter(mActivity, object : NotificationAdapter.NotificationCallback {
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
    override fun update(p0: Observable?, p1: Any?) {

    }
}