package com.namviet.vtvtravel.view.f2.virtualswitchboard.page

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.F2FragmentVirtualTicketPageBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.f2event.UpdateAllListTicket
import com.namviet.vtvtravel.model.virtualcall.ListTicketResponse
import com.namviet.vtvtravel.model.virtualcall.VirtualTicket
import com.namviet.vtvtravel.response.*
import com.namviet.vtvtravel.response.f2room.CallRoomResponse
import com.namviet.vtvtravel.view.f2.call.CallingOutAtOperatorLinPhone
//import com.namviet.vtvtravel.view.f2.call.CallingOutAtOperatorLinPhone
import com.namviet.vtvtravel.view.f2.virtualswitchboard.SelectVirtualCallTypeDialog
import com.namviet.vtvtravel.view.f2.virtualswitchboard.SelectVirtualCallTypeDialog.ClickButton
import com.namviet.vtvtravel.view.f2.virtualswitchboard.VirtualTicketAdapter
import com.namviet.vtvtravel.view.f2.virtualswitchboard.VirtualTicketInDayAdapter
import com.namviet.vtvtravel.view.f2.virtualswitchboard.ticketdetail.VirtualTicketDetailDialog
import com.namviet.vtvtravel.widget.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.f2_fragment_virtual_ticket_page.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*
import kotlin.collections.ArrayList

class VirtualTicketPageFragment : BaseFragment<F2FragmentVirtualTicketPageBinding>(), Observer {

    companion object {
        fun newInstance(type: Int, listener: OnWaitingCountUpdateListener?): VirtualTicketPageFragment {
            return VirtualTicketPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(K_TYPE, type)
                    onWaitingCountUpdateListener = listener
                }
            }
        }

        const val K_TYPE: String = "type"
    }

    private val pairDateListTicket: ArrayList<Pair<Calendar, ArrayList<VirtualTicket>>> = ArrayList()
    private var ticketInDayAdapter: VirtualTicketInDayAdapter? = null
    private var virtualTicketPageVM: VirtualTicketPageVM? = null
    private var onWaitingCountUpdateListener: OnWaitingCountUpdateListener? = null

    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private var mPage = 1
    private var filterOverDate = false
    private var loadMoreAble = true
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_virtual_ticket_page
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnWaitingCountUpdateListener) {
            onWaitingCountUpdateListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        onWaitingCountUpdateListener = null
    }

    override fun initView() {

        virtualTicketPageVM = VirtualTicketPageVM()
        virtualTicketPageVM?.addObserver(this)

        if (arguments?.getInt(K_TYPE) == 0 || arguments?.getInt(K_TYPE) == 1) {
            btn_filter.visibility = View.VISIBLE
            btn_filter.isSelected = filterOverDate
            btn_filter.setOnClickListener {
                filterOverDate = !filterOverDate
                btn_filter.isSelected = filterOverDate
                mPage = 1
                loadMoreAble = true
                initData()
            }
        }
        ticketInDayAdapter = context?.let {
            VirtualTicketInDayAdapter(it, pairDateListTicket,
                    object : VirtualTicketAdapter.OnVirtualTicketActionListener {
                        override fun onSelectTicket(virtualTicket: VirtualTicket) {
                            try {
                                SelectVirtualCallTypeDialog.newInstance(virtualTicket,
                                        object : ClickButton {
                                            override fun onClickCallInApp(selectVirtualCallTypeDialog: SelectVirtualCallTypeDialog) {
//                                                selectVirtualCallTypeDialog.dismiss()
//                                                virtualTicketPageVM?.getRoomId("123132", "1231232");
                                            }

                                            override fun onClickCallNormal(selectVirtualCallTypeDialog: SelectVirtualCallTypeDialog) {
                                                try {
                                                    Handler().postDelayed(Runnable {
                                                        try {
                                                            VirtualTicketDetailDialog.newInstance(virtualTicket).show(childFragmentManager, null)
                                                        } catch (e: Exception) {
                                                        }
                                                    }, 200)

                                                    CallingOutAtOperatorLinPhone.startScreen(mActivity, virtualTicket.creatorMobile.replaceFirst("84", "1039"))
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }

                                            }

                                        }).show(childFragmentManager, null)
                            } catch (e: Exception) {
                            }
                        }

                        override fun onSelectMenuTicket(virtualTicket: VirtualTicket) {
                            try {
                                VirtualTicketDetailDialog.newInstance(virtualTicket).show(childFragmentManager, null)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                    })
        }
        rcv_day.layoutManager = LinearLayoutManager(context)
        scrollListener = object : EndlessRecyclerViewScrollListener(rcv_day.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                initData()
            }
        }
        rcv_day.adapter = ticketInDayAdapter
        rcv_day.addOnScrollListener(scrollListener)
        swipeLayout.setOnRefreshListener {
            reload()
        }
    }


    private fun reload(){
        mPage = 1
        loadMoreAble = true
        initData()
        Handler(Looper.getMainLooper()).postDelayed({
            swipeLayout?.isRefreshing = false
        }, 500)
    }

    override fun initData() {
        if (loadMoreAble) {
            virtualTicketPageVM?.getListTicket(arguments?.get(K_TYPE) as Int, mPage++, filterOverDate)
        }
    }

    override fun inject() {
    }

    override fun setClickListener() {
    }

    override fun setObserver() {
    }

    override fun update(o: Observable?, arg: Any?) {
        if (o is VirtualTicketPageVM) {
            mActivity.runOnUiThread {
                if (arg is ListTicketResponse && tvEmpty != null) {
                    tvEmpty.visibility = View.GONE

                    var date: Calendar? = null
                    arg.data?.totalElements?.let { onWaitingCountUpdateListener?.onUpdate(arguments!!.getInt(K_TYPE), it) }
                    if (arg.data?.pageable?.pageNumber == 0) {
                        pairDateListTicket.clear()
                    }

                    if(mPage == 2){
                        pairDateListTicket.clear()
                    }
                    if (pairDateListTicket.size > 0) {
                        date = pairDateListTicket[pairDateListTicket.size - 1].first
                    }
                    if (arg.data != null && arg.data.content != null) {
                        if (arg.data.content.size < 10) {
                            loadMoreAble = false
                        }
                        for (ticket in arg.data.content) {

                            if (date == null) {
                                date = createDateFromTimestamp(ticket.created)
                                val dayTickets = ArrayList<VirtualTicket>()
                                dayTickets.add(ticket)
                                val pair = Pair(date, dayTickets)
                                pairDateListTicket.add(pair)
                                continue
                            }

                            if (isTimestampInDate(date, ticket.created)) {
                                pairDateListTicket[pairDateListTicket.size - 1].second.add(ticket)
                            } else {
                                date = createDateFromTimestamp(ticket.created)
                                val dayTickets = ArrayList<VirtualTicket>()
                                dayTickets.add(ticket)
                                val pair = Pair(date, dayTickets)
                                pairDateListTicket.add(pair)
                            }
                        }
                    }

                    ticketInDayAdapter?.notifyDataSetChanged()
                    if (pairDateListTicket.size == 0) {
                        tvEmpty.visibility = View.VISIBLE
                    }
                } else if (arg is CallRoomResponse) {
//                    CallingOutAtOperatorLinPhone.startScreen(mActivity)
                } else if (o is ResponseError) {
                    loadMoreAble = false
                    val responseError = o as ResponseError
                    showToast(responseError.message)
                }
                shimmerMain?.visibility = View.GONE
                shimmer_view_container?.stopShimmer()
            }
        }
    }

    private fun createDateFromTimestamp(timestamp: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar
    }

    fun isTimestampInDate(date: Calendar, timestamp: Long): Boolean {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timestamp
        return date.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR) && date.get(Calendar.YEAR) == cal.get(Calendar.YEAR)
    }

    @Subscribe
    public fun onUpdateAllListTicket(updateAllListTicket: UpdateAllListTicket){
        reload()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}