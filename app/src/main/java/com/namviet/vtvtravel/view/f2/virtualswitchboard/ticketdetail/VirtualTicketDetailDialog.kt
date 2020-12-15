package com.namviet.vtvtravel.view.f2.virtualswitchboard.ticketdetail

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.baseapp.utils.KeyboardUtils
import com.jakewharton.rxbinding2.view.RxView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.f2event.UpdateAllListTicket
import com.namviet.vtvtravel.model.virtualcall.ListTicketHistoryResponse
import com.namviet.vtvtravel.model.virtualcall.VirtualTicket
import com.namviet.vtvtravel.response.BaseResponse
import com.namviet.vtvtravel.view.f2.virtualswitchboard.tickethistory.VirtualTicketHistoryDialog
import com.namviet.vtvtravel.view.fragment.f2offline.BaseDialogBottom
import com.namviet.vtvtravel.viewmodel.f2luckywheel.LuckyWheelViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.f2_dialog_virtual_ticket_detail.*
import org.greenrobot.eventbus.EventBus
import java.util.*
import java.util.concurrent.TimeUnit

class VirtualTicketDetailDialog : BaseDialogBottom(), Observer {

    private var virtualTicket: VirtualTicket? = null
    private var obsSubmit: Disposable? = null
    private var checkChanged = false
    private var ticketDetailVM: VirtualTicketDetailVM? = null
    private var canDismiss : Boolean = false
    override fun getLayoutResource(): Int {
        return R.layout.f2_dialog_virtual_ticket_detail
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
        return inflater.inflate(R.layout.f2_dialog_virtual_ticket_detail, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setUp(view: View?) {
        ticketDetailVM = VirtualTicketDetailVM()
        ticketDetailVM?.addObserver(this)
        virtualTicket?.let outer@{
            try {
                if (!TextUtils.isEmpty(it.customerName)) {
                    tvCustomer.text = it.customerName
                    tvCustomer.typeface = context?.let { ResourcesCompat.getFont(it, R.font.roboto_regular) }
                } else {
                    tvCustomer.text = "Không xác định"
                    tvCustomer.typeface = context?.let { ResourcesCompat.getFont(it, R.font.roboto_italic) }
                }

                try {
                    tvTicketId.text = it.ticketId
                    tvPhone.text = it.creatorMobile
                } catch (e: Exception) {
                }


                try {
                    tvTimeMustToFinish.text = Utils.convertDateToString(Date(it.deadline), "dd-MM-yyyy HH:mm:ss")
                } catch (e: Exception) {
                }

                try {
                    if (it.status == 2) {
                        tvFinishTime.text = Utils.convertDateToString(Date(it.finished), "dd-MM-yyyy HH:mm:ss")
                        view_done_time.visibility = View.GONE
                    } else {
                        view_done_time.visibility = View.GONE
                    }
                } catch (e: Exception) {
                }

                btnViewHistory.setOnClickListener {
                    if (virtualTicket != null) {
                        VirtualTicketHistoryDialog.newInstance(virtualTicket!!).show(childFragmentManager, "")
                    }
                }

                edtContent.setOnTouchListener(View.OnTouchListener { v, event ->
                    if (edtContent.hasFocus()) {
                        v.parent.requestDisallowInterceptTouchEvent(true)
                        when (event.action and MotionEvent.ACTION_MASK) {
                            MotionEvent.ACTION_SCROLL -> {
                                v.parent.requestDisallowInterceptTouchEvent(false)
                                return@OnTouchListener true
                            }
                        }
                    }
                    return@OnTouchListener false
                })

                when (it.status) {
                    0 -> {
                        view_process.visibility = View.VISIBLE
                        viewSubmit.visibility = View.VISIBLE
                        tvStatus.text = "Chờ xử lý"
                        btnViewHistory.background = context?.getDrawable(R.drawable.f2_bg_yellow_fff5de_corner_4)
                        ivHistory.setImageDrawable(context?.getDrawable(R.drawable.ic_edit_history))
                        tvHistory.setTextColor(Color.parseColor("#FF8000"))
                        rb_to_processing.text = "Chuyển sang Đang xử lý"
                    }
                    1 -> {
                        view_process.visibility = View.VISIBLE
                        viewSubmit.visibility = View.VISIBLE
                        tvStatus.text = "Đang xử lý"
                        btnViewHistory.background = context?.getDrawable(R.drawable.f2_bg_yellow_fff5de_corner_4)
                        ivHistory.setImageDrawable(context?.getDrawable(R.drawable.ic_edit_history))
                        tvHistory.setTextColor(Color.parseColor("#FF8000"))
                        rb_to_processing.text = "Giữ nguyên trạng thái"
                    }
                    2 -> {
                        view_process.visibility = View.GONE
                        viewSubmit.visibility = View.GONE
                        tvStatus.text = "Chờ khách hàng phản hồi"
                        btnViewHistory.background = context?.getDrawable(R.drawable.f2_bg_d8f5f4_corner_4)
                        ivHistory.setImageDrawable(context?.getDrawable(R.drawable.ic_edit_history_2))
                        tvHistory.setTextColor(Color.parseColor("#00918D"))
                    }
                    3 -> {
                        viewSubmit.visibility = View.GONE
                        view_process.visibility = View.GONE
                        tvStatus.text = "Đã đóng"
                        btnViewHistory.background = context?.getDrawable(R.drawable.f2_bg_d8f5f4_corner_4)
                        ivHistory.setImageDrawable(context?.getDrawable(R.drawable.ic_edit_history_2))
                        tvHistory.setTextColor(Color.parseColor("#00918D"))
                    }
                }




                obsSubmit = Observable.merge(RxView.clicks(btnSave).map { 1 },
                        RxView.clicks(btnSubmit).map { 2 })
                        .throttleFirst(300, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<Int>() {
                            override fun onNext(t: Int) {
                                try {
                                    if (edtContent.text.toString().trim().isEmpty()) {
                                        nestedScrollView.scrollTo(0, view_process.y.toInt())
                                        Toast.makeText(context, "Bạn cần nhập nội dung xử lý", Toast.LENGTH_SHORT).show()
                                    } else {
                                        val content = edtContent.text.toString()
                                        if (t == 2) { // click on btnSubmit
                                            canDismiss = true
                                            when (radioGroup.checkedRadioButtonId) {
                                                R.id.rb_to_processing -> {
                                                    ticketDetailVM?.updateTicket(virtualTicket!!.ticketId,  content, "1")
                                                }
                                                R.id.rb_to_waiting -> {
                                                    ticketDetailVM?.updateTicket(virtualTicket!!.ticketId,  content, "2")
                                                }
                                                else -> {
                                                    ticketDetailVM?.updateTicket(virtualTicket!!.ticketId,  content, "-1")
                                                }
                                            }

                                        } else { // click on btnSave
                                            canDismiss = false
                                            ticketDetailVM?.updateTicket(virtualTicket!!.ticketId,  content, "1")
                                        }

                                        try {
                                            KeyboardUtils.hideKeyboard(context, edtContent)
                                        } catch (e: Exception) {
                                        }
                                    }
                                } catch (e: Exception) {
                                }
                            }

                            override fun onError(e: Throwable) {
                                e.printStackTrace()
                            }

                            override fun onComplete() {
                            }

                        })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        obsSubmit?.dispose()
    }

    companion object {
        fun newInstance(virtualTicket: VirtualTicket): VirtualTicketDetailDialog {
            val virtualTicketDetailDialog = VirtualTicketDetailDialog()
            virtualTicketDetailDialog.virtualTicket = virtualTicket
            return virtualTicketDetailDialog
        }
    }

    override fun update(observable: java.util.Observable?, o: Any?) {
        if (observable is VirtualTicketDetailVM && null != o) {
            when (o) {
                is BaseResponse -> {
                    try {
                        Toast.makeText(context, "Update thành công!", Toast.LENGTH_SHORT).show()
                        EventBus.getDefault().post(UpdateAllListTicket())
                        if(canDismiss) {
                            dismiss()
                        }
                    } catch (e: Exception) {
                    }
                }
                is ErrorResponse -> {

                }
            }
        }
    }
}