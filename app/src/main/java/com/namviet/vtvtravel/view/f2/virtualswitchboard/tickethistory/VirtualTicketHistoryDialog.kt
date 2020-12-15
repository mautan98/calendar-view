package com.namviet.vtvtravel.view.f2.virtualswitchboard.tickethistory

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.vqmm.LeaderBoardAdapter
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.virtualcall.ListTicketHistoryResponse
import com.namviet.vtvtravel.model.virtualcall.VirtualTicket
import com.namviet.vtvtravel.model.virtualcall.VirtualTicketHistory
import com.namviet.vtvtravel.response.f2wheel.WheelChartResponse
import com.namviet.vtvtravel.view.f2.virtualswitchboard.tickethistory.VirtualTicketHistoryAdapter.VirtualTicketHistoryListener
import com.namviet.vtvtravel.view.fragment.f2offline.BaseDialogBottom
import com.namviet.vtvtravel.viewmodel.f2luckywheel.LuckyWheelViewModel
import kotlinx.android.synthetic.main.f2_dialog_virtual_ticket_history.*
import kotlinx.android.synthetic.main.f2_fragment_leader_board_lucky_wheel.*
import java.util.*
import kotlin.collections.ArrayList

class VirtualTicketHistoryDialog : BaseDialogBottom(), Observer {

    private var virtualTicket: VirtualTicket? = null
    private var histories = ArrayList<VirtualTicketHistory>()
    private var virtualTicketHistoryAdapter: VirtualTicketHistoryAdapter? = null
    private var virtualTicketHistoryVM: VirtualTicketHistoryVM? = null
    override fun getLayoutResource(): Int {
        return R.layout.f2_dialog_sort_voucher
    }

    override fun init(saveInstanceState: Bundle?, view: View) {
        try {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            view.setBackgroundColor(Color.TRANSPARENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (view?.parent as View).setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (this.dialog!!.window != null) {
            this.dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            this.dialog!!.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        return inflater.inflate(R.layout.f2_dialog_virtual_ticket_history, container, false)
    }

    override fun setUp(view: View?) {
        virtualTicketHistoryVM = VirtualTicketHistoryVM()
        virtualTicketHistoryVM!!.addObserver(this)
        virtualTicket?.ticketId?.let { virtualTicketHistoryVM?.getTicketHistory(it, "crm_process") }

        virtualTicketHistoryAdapter = VirtualTicketHistoryAdapter(context!!, histories, object : VirtualTicketHistoryListener {
            override fun onViewHistory(virtualTicket: VirtualTicketHistory) {
                try {
                    TicketHistoryContentDialog.newInstance(virtualTicket.new_value).show(childFragmentManager, null)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })

        rcvHistory.adapter = virtualTicketHistoryAdapter
    }

    companion object {
        fun newInstance(virtualTicket: VirtualTicket): VirtualTicketHistoryDialog {
            val virtualTicketDetailDialog = VirtualTicketHistoryDialog()
            virtualTicketDetailDialog.virtualTicket = virtualTicket
            return virtualTicketDetailDialog
        }
    }

    override fun update(observable: Observable?, o: Any?) {
        if (observable is VirtualTicketHistoryVM && null != o) {
            when (o) {
                is ListTicketHistoryResponse -> {
                    try {
                        histories.clear()
                        histories.addAll(o.data?.items!!)
                        virtualTicketHistoryAdapter?.notifyDataSetChanged()


                        if(histories.size == 0){
                            Toast.makeText(baseActivity, "Chưa có lịch sử hỗ trợ", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(baseActivity, "Chưa có lịch sử hỗ trợ", Toast.LENGTH_SHORT).show()
                    }
                }
                is ErrorResponse -> {

                }
            }
        }
    }
}