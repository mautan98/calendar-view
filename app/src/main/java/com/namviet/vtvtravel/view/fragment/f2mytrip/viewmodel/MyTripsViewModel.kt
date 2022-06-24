package com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel

import com.google.gson.Gson
import com.namviet.vtvtravel.api.Param
import com.namviet.vtvtravel.api.WSConfig
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.cost.TypeCost
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.createschedule.BodyCreateTrip
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.createschedule.DataCreateTrips
import com.namviet.vtvtravel.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException

class MyTripsViewModel: BaseViewModel() {

    fun getListScheduleTrips(){
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc

        val dispose  = newsService.allTripsSchedule.subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
                if (it != null) {
                    requestSuccessRes(it)
                } else {
                    requestSuccessRes(null)
                }
            },{requestFailedRes(it)})
        compositeDisposable.add(dispose)
    }

    fun getDetailplaceById(scheduleId:String){
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc
        val dispose  = newsService.getDetailPlaceByScheduleid(scheduleId).subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
                if (it != null) {
                    requestSuccessRes(it)
                } else {
                    requestSuccessRes(null)
                }
            },{requestFailedRes(it)})
        compositeDisposable.add(dispose)
    }

    fun getCostList(scheduleId:String?){
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc
        val dispose  = newsService.getCostDetail(scheduleId).subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
                if (it != null) {
                    requestSuccessRes(it)
                } else {
                    requestSuccessRes(null)
                }
            },{requestFailedRes(it)})
        compositeDisposable.add(dispose)
    }

    fun updateCost(list:MutableList<TypeCost>?){
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc
        val jsArray = JSONArray(Gson().toJson(list))
        val jsonObject = JSONObject()
        jsonObject.putOpt("scheduleCostList",jsArray)
        val param = Param.getParams(jsonObject)
        val resquestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),param.toString())
        val dispose  = newsService.updateCost(resquestBody).subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
                if (it != null) {
                    requestSuccessRes(it)
                } else {
                    requestSuccessRes(null)
                }
            },{requestFailedRes(it)})
        compositeDisposable.add(dispose)
    }

    fun updateSchedule(name:String,description:String,scheduleId: String){
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc
        val jsonObject = JSONObject()
        jsonObject.put("name",name)
        jsonObject.put("description",description)
        jsonObject.put("scheduleCustomId",scheduleId)
        val param = Param.getParams(jsonObject)
        val resquestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),param.toString())
        val dispose  = newsService.updateSchedule(resquestBody).subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
                if (it != null) {
                    it.apiCode = "updateScheduleCustom"
                    requestSuccessRes(it)
                } else {
                    requestSuccessRes(null)
                }
            },{requestFailedRes(it)})
        compositeDisposable.add(dispose)
    }

    fun deleteSchedule(scheduleId: String){
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc
        val jsonObject = JSONObject()
        jsonObject.put("scheduleCustomId",scheduleId)
        val param = Param.getParams(jsonObject)
        val resquestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),param.toString())
        val dispose  = newsService.deleteSchedule(resquestBody).subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
                if (it != null) {
                    it.apiCode = "deleteScheduleCustom"
                    requestSuccessRes(it)
                } else {
                    requestSuccessRes(null)
                }
            },{requestFailedRes(it)})
        compositeDisposable.add(dispose)
    }

    fun updateRangeScheduleTime(scheduleId: String, startAt: String, endAt: String) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc
        val jsonObject = JSONObject()
        jsonObject.put("scheduleCustomId", scheduleId)
        jsonObject.put("startAt", startAt)
        jsonObject.put("endAt", endAt)
        val param = Param.getParams(jsonObject)
        val resquestBody = RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            param.toString()
        )
        val dispose = newsService.updateRangeSchedule(resquestBody)
            .subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
                if (it != null) {
                    it.apiCode = WSConfig.Api.UPDATE_RANGE_SCHEDULE_CUSTOM
                    requestSuccessRes(it)
                } else {
                    requestSuccessRes(null)
                }
            }, { requestFailedRes(it) })
        compositeDisposable.add(dispose)
    }

    fun getDetailPlaces(scheduleId: String) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc
        val dispose = newsService.getDetailPlaces(scheduleId)
            .subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
                if (it != null) {
                    requestSuccessRes(it)
                } else {
                    requestSuccessRes(null)
                }
            }, { requestFailedRes(it) })
        compositeDisposable.add(dispose)
    }

    fun updateNoteSchedule(schedulePlaceId:String,noteContent: String) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc
        val jsonObject = JSONObject()
        jsonObject.put("schedulePlaceId",schedulePlaceId)
        jsonObject.put("note",noteContent)
        val param = Param.getParams(jsonObject)
        val resquestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),param.toString())
        val dispose = newsService.updateNoteSchedule(resquestBody)
            .subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
                if (it != null) {
                    requestSuccessRes(it)
                } else {
                    requestSuccessRes(null)
                }
            }, { requestFailedRes(it) })
        compositeDisposable.add(dispose)
    }

    fun inviteSchedule(scheduleId:String,mobileInvite: String) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc
        val jsonObject = JSONObject()
        jsonObject.put("mobileInvited",mobileInvite)
        jsonObject.put("scheduleCustomId",scheduleId)
        val param = Param.getParams(jsonObject)
        val resquestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),param.toString())
        val dispose = newsService.inviteSchedule(resquestBody)
            .subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
                if (it != null) {
                    it.apiCode = WSConfig.Api.INVITE_SCHEDULE
                    requestSuccessRes(it)
                } else {
                    requestSuccessRes(null)
                }
            }, { requestFailedRes(it) })
        compositeDisposable.add(dispose)
    }

}