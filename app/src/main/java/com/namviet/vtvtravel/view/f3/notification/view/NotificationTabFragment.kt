package com.namviet.vtvtravel.view.f3.notification.view

import android.app.ProgressDialog
import android.util.Log
import android.view.View
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.config.Constants
import com.namviet.vtvtravel.databinding.F3FragmentNotificationTabBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.view.f3.notification.NotificationViewModel
import com.namviet.vtvtravel.view.f3.notification.adapter.NotificationAdapter
import com.namviet.vtvtravel.view.f3.notification.model.Notification
import com.namviet.vtvtravel.view.f3.notification.model.ui.NotificationResponse
import com.namviet.vtvtravel.view.f3.notification.model.ui.UpdateNotificationResponse
import com.namviet.vtvtravel.view.f3.notification.processing.NotificationCaseProcessing
import kotlinx.android.synthetic.main.f3_fragment_notification_tab.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class NotificationTabFragment : BaseFragment<F3FragmentNotificationTabBinding?>, Observer {
    private var isNotType: Boolean = false;
    private var type: String? = null;


    private var notificationViewModel: NotificationViewModel? = null
    private var dataList: ArrayList<Notification> = ArrayList()

    private var progressDialog: ProgressDialog? = null
    private var dataMap: HashMap<String, ArrayList<Notification>>? = null

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
        progressDialog = ProgressDialog(mActivity)
        progressDialog?.setMessage("Đang xử lý...")
        notificationViewModel = NotificationViewModel();
        notificationViewModel?.addObserver(this)
        dataMap = HashMap()
        getData()
        swipeToRefresh.setOnRefreshListener {
            getData()
        }
    }


    private fun getData() {
        swipeToRefresh.isRefreshing = true
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
        notificationAdapter = NotificationAdapter(mActivity, dataList,
            object : NotificationAdapter.NotificationCallback {
                override fun onClickItemMenu(position: Int?, notification: Notification?) {
                    var menuItemNotifyDialog = MenuItemNotifyDialog(notification);
                    menuItemNotifyDialog.setImenuItemNotifyClick {
                        progressDialog?.show()
                        when (it) {
                            MenuItemNotifyDialog.MenuItem.BOOKMARK -> notificationViewModel?.updateMark(
                                notification?.id,
                                if (notification?.isMarked == "0") "1" else "0",
                                position!!
                            )
                            MenuItemNotifyDialog.MenuItem.VIEWED -> notificationViewModel?.updateInbox(
                                notification?.id,
                                if (notification?.status == "0") "1" else "0",
                                position!!
                            )
                            MenuItemNotifyDialog.MenuItem.REMOVE -> notificationViewModel?.updateInbox(
                                notification?.id,
                                "2",
                                position!!
                            )
                            MenuItemNotifyDialog.MenuItem.CANCEL -> progressDialog?.dismiss()

                        }
                    }
                    menuItemNotifyDialog.show(childFragmentManager, "")
                }

                override fun onClickItem(position: Int?, notification: Notification?) {
                    notificationViewModel?.updateInbox(notification?.id, "1", position!! )
                    var notificationCaseProcessing = NotificationCaseProcessing(mActivity)
                    notificationCaseProcessing.handleIntentFromNotification(notification?.data?.code, notification)
                }

                override fun onDataChange(isShow: Boolean) {
                    if(isShow){
                        tvNoMess.visibility = View.VISIBLE
                    }else{
                        tvNoMess.visibility = View.GONE
                    }
                }

            });
        rclContent.adapter = notificationAdapter
    }

    override fun inject() {}
    override fun setClickListener() {}
    override fun setObserver() {}
    override fun update(observable: Observable?, o: Any?) {
        swipeToRefresh.isRefreshing = false
        progressDialog?.dismiss()
        if (observable is NotificationViewModel && null != o) {
            when (o) {
                is NotificationResponse -> {
                    dataList.clear()
                    dataList.addAll(progressData(o.data.notifications))
                    notificationAdapter?.notifyDataSetChanged()
                }

                is UpdateNotificationResponse -> {
                    if(o.position == -1){
                        for (i in 0 until dataList.size){
                            if(!dataList[i].isHeader){
                                dataList[i].status = "1"
                            }
                        }
                        notificationAdapter?.notifyDataSetChanged()
                    }else {
                        dataList[o.position].isMarked = o.data.notifications[0].isMarked
                        dataList[o.position].status = o.data.notifications[0].status
                        if (o.data.notifications[0].status == "2") {
                            dataList.removeAt(o.position)
                        }

                        //Nếu là màn hình đã lưu
                        if(type.equals("1002")){
                            if (o.data.notifications[0].isMarked == "0") {
                                dataList.removeAt(o.position)
                            }
                        }
                        //

                        //Nếu là màn hình chưa đọc
                        if(type.equals("1001")){
                            if (o.data.notifications[0].status == "1") {
                                dataList.removeAt(o.position)
                            }
                        }
                        //


                        notificationAdapter?.notifyDataSetChanged()
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

    private fun progressData(dataList: ArrayList<Notification>) : ArrayList<Notification> {
        var resultDataList = ArrayList<Notification>()
        try {
            var dateStringSave = "";
            val sdf = SimpleDateFormat(Constants.DateFormat.DATE_FORMAT_18, Locale.getDefault())
            for (i in 0 until dataList.size) {
                var date = Date(dataList[i].createdAt.toLong())
                var dateString = sdf.format(date)
                if (i == 0) {
                    var notification = Notification();
                    notification.title = dateString
                    notification.isHeader = true
                    resultDataList.add(notification)
                    resultDataList.add(dataList[i])
                    dateStringSave = dateString
                } else {
                    if (dateStringSave == dateString) {
                        resultDataList.add(dataList[i])
                    } else {
                        var notification = Notification();
                        notification.title = dateString
                        notification.isHeader = true
                        resultDataList.add(notification)
                        resultDataList.add(dataList[i])
                        dateStringSave = dateString
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("", "")
        }
        return resultDataList;
    }

    public fun viewAllNotification(){
        progressDialog?.show()
        notificationViewModel?.updateViewedAllInbox()
    }
}