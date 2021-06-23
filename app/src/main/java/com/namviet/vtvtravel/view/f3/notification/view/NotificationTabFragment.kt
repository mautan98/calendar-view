package com.namviet.vtvtravel.view.f3.notification.view

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.F3FragmentNotificationTabBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.view.f3.notification.NotificationViewModel
import com.namviet.vtvtravel.view.f3.notification.adapter.NotificationAdapter
import com.namviet.vtvtravel.view.f3.notification.model.Notification
import com.namviet.vtvtravel.view.f3.notification.model.ui.NotificationResponse
import com.namviet.vtvtravel.view.f3.notification.model.ui.NotificationTab
import kotlinx.android.synthetic.main.f3_fragment_notification_tab.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class NotificationTabFragment : BaseFragment<F3FragmentNotificationTabBinding?>, Observer {
    private var isNotType: Boolean = false;
    private var type: String? = null;


    private var notificationViewModel: NotificationViewModel? = null
    private var dataList: ArrayList<Notification> = ArrayList()

    constructor(isNotType: Boolean, type: String?) {
        this.isNotType = isNotType;
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
        if (!isNotType) {
            notificationViewModel?.getNotificationByType(type)
        } else {
            when {
                type.equals("1002") -> {
                    notificationViewModel?.getNotificationByMark()
                }
                type.equals("1001") -> {
                    notificationViewModel?.getNotificationByStatus()
                }
                type.equals("1000") -> {
                    notificationViewModel?.getNotification()
                }
            }
        }
    }

    override fun initData() {
        notificationAdapter = NotificationAdapter(
            mActivity,
            dataList,
            object : NotificationAdapter.NotificationCallback {
                override fun onClickItemMenu() {
                    var menuItemNotifyDialog = MenuItemNotifyDialog();
                    menuItemNotifyDialog.setImenuItemNotifyClick {
                        when (it) {
                            MenuItemNotifyDialog.MenuItem.BOOKMARK -> notificationViewModel?.updateMark()
                            MenuItemNotifyDialog.MenuItem.VIEWED -> notificationViewModel?.updateInbox()
                        }
                    }
                    menuItemNotifyDialog.show(childFragmentManager, "")
                }

            });
        rclContent.adapter = notificationAdapter
    }

    override fun inject() {}
    override fun setClickListener() {}
    override fun setObserver() {}
    override fun update(observable: Observable?, o: Any?) {
        if (observable is NotificationViewModel && null != o) {
            when (o) {
                is NotificationResponse -> {
                    dataList.addAll(o.data.notifications)
                    notificationAdapter?.notifyDataSetChanged()
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