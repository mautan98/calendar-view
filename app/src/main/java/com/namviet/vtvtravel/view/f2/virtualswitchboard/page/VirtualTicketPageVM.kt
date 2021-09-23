package com.namviet.vtvtravel.view.f2.virtualswitchboard.page

import com.namviet.vtvtravel.api.Param
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.model.virtualcall.ListTicketResponse
import com.namviet.vtvtravel.response.f2room.CallRoomResponse
import com.namviet.vtvtravel.ultils.ResponseUltils
import com.namviet.vtvtravel.view.f2.virtualswitchboard.VirtualSwitchBoardActivity
import com.namviet.vtvtravel.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

class VirtualTicketPageVM() : BaseViewModel() {

    fun getListTicket(status: Int, page: Int, isOverDate: Boolean = false, ticketType: String) {
        val queryMap = Param.page(page)
        queryMap["status"] = status
        if (isOverDate) queryMap["isOverDate"] = 1
        if (!ticketType.isNullOrEmpty()) queryMap["ticketTypeCode"] = ticketType
        val myApplication = MyApplication.getInstance()
        val jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                Param.getParams(JSONObject()).toString())
        val newsService = myApplication.travelServiceAcc
        val disposable = newsService.getListTicket(jsonBodyObject, queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ baseResponse: ListTicketResponse? ->
                    if (null != baseResponse) {
                        loadSuccess(baseResponse)
                    } else {
                        loadSuccess(null)
                    }
                }) {
                    throwable: Throwable -> requestFailed(throwable)
                }
        compositeDisposable.add(disposable)
    }



    fun getRoomId(myMobile: String, guestMobile: String) {
        val myApplication = MyApplication.getInstance()
        val jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                Param.getParams(Param.getRoomId(myMobile, guestMobile)).toString())
        val newsService = myApplication.travelServiceAcc
        val disposable = newsService.createRoomAndGetRoomId(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ baseResponse: CallRoomResponse? ->
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