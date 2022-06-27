package com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel

import com.namviet.vtvtravel.api.Param
import com.namviet.vtvtravel.api.WSConfig
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import okhttp3.RequestBody
import org.json.JSONObject

class DetailPlaceScheduleViewModel : BaseViewModel() {

    fun updateArrivalTime(schedulePlaceId:String,arrivalTime:String,){
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc
        val jsonObject = JSONObject()
        jsonObject.put("arrivalTime",arrivalTime)
        jsonObject.put("schedulePlaceId",schedulePlaceId)
        val param = Param.getParams(jsonObject)
        val resquestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),param.toString())
        val dispose = newsService.updateArrivalTime(resquestBody)
            .subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
                if (it != null) {
                    it.apiCode = WSConfig.Api.UPDATE_ARRIVAL_TIME
                    requestSuccessRes(it)
                } else {
                    requestSuccessRes(null)
                }
            }, { requestFailedRes(it) })
        compositeDisposable.add(dispose)
    }

    fun updateFreeTime(schedulePlaceId:String,freeTime:String,){
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc
        val jsonObject = JSONObject()
        jsonObject.put("arrivalTime",freeTime)
        jsonObject.put("schedulePlaceId",schedulePlaceId)
        val param = Param.getParams(jsonObject)
        val resquestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),param.toString())
        val dispose = newsService.updateFreeTime(resquestBody)
            .subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
                if (it != null) {
                    it.apiCode = WSConfig.Api.UPDATE_ARRIVAL_TIME
                    requestSuccessRes(it)
                } else {
                    requestSuccessRes(null)
                }
            }, { requestFailedRes(it) })
        compositeDisposable.add(dispose)
    }

}