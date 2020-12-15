package com.namviet.vtvtravel.view.f2.virtualswitchboard.tickethistory

import android.content.Context
import com.namviet.vtvtravel.api.Param
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.model.virtualcall.ListTicketHistoryResponse
import com.namviet.vtvtravel.model.virtualcall.ListTicketResponse
import com.namviet.vtvtravel.ultils.ResponseUltils
import com.namviet.vtvtravel.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap

class VirtualTicketHistoryVM() : BaseViewModel() {

    fun getTicketHistory(ticketId: String, field: String) {
        val myApplication = MyApplication.getInstance()
        val jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                Param.getParams2(Param.getListHistoryTicket(ticketId, field)).toString())
        val newsService = myApplication.travelServiceAcc
        val disposable = newsService.getTicketHistory(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ baseResponse: ListTicketHistoryResponse? ->
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