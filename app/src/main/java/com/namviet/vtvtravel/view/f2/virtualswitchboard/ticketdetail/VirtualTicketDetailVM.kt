package com.namviet.vtvtravel.view.f2.virtualswitchboard.ticketdetail

import com.namviet.vtvtravel.api.Param
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.model.virtualcall.ListTicketHistoryResponse
import com.namviet.vtvtravel.model.virtualcall.ProcessTicketResponse
import com.namviet.vtvtravel.response.BaseResponse
import com.namviet.vtvtravel.ultils.ResponseUltils
import com.namviet.vtvtravel.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

class VirtualTicketDetailVM() : BaseViewModel() {
    /**
     * @param content noi dung xu ly
     * @param status    0 => Chờ xử lý
    1 => Đang xử lý
    2 => Chờ khách hàng phản hồi
    3 => Đã đóng
    -1 => không cập nhật trạng thái
     */
    fun submit(content: String?, status: Int) {

        val myApplication = MyApplication.getInstance()
        val jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                Param.getParams(Param.processTicket(content, status)).toString())
        val disposable = myApplication.travelServiceAcc.submitProcessTicket(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ baseResponse: ProcessTicketResponse? ->
                    if (null != baseResponse) {
                        loadSuccess(baseResponse)
                    } else {
                        loadSuccess(null)
                    }
                }) { throwable: Throwable -> requestFailed(throwable) }
        compositeDisposable.add(disposable)
    }

    fun updateTicket(ticketId: String,  content: String, status: String) {
        val myApplication = MyApplication.getInstance()
        val jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                Param.getParams2(Param.updateTicket(ticketId, content, status)).toString())
        val newsService = myApplication.travelServiceAcc
        val disposable = newsService.updateTicket(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ baseResponse: BaseResponse? ->
                    if (null != baseResponse) {
                        loadSuccess(baseResponse)
                    } else {
                        loadSuccess(null)
                    }
                }) { throwable: Throwable -> requestFailed(throwable) }
        compositeDisposable.add(disposable)
    }

    private fun loadSuccess(`object`: Any?) {
        setChanged()
        notifyObservers(`object`)
    }

    private fun requestFailed(throwable: Throwable) {
        try {
            onLoadFail()
        } catch (e: Exception) {
        }
        try {
            setChanged()
            notifyObservers(ResponseUltils.requestFailed(throwable))
        } catch (e: Exception) {
            setChanged()
            notifyObservers()
        }
    }

}