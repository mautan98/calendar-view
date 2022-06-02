package com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel

import com.google.gson.Gson
import com.namviet.vtvtravel.api.Param
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.createschedule.BodyCreateTrip
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.createschedule.DataCreateTrips
import com.namviet.vtvtravel.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.HttpException

class MyTripsViewModel: BaseViewModel() {

    fun getListScheduleTrips(){
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc

        val dispose  = newsService.allTripsSchedule.subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
                if (it != null) {
                    requestSuccess(it)
                } else {
                    requestSuccess(null)
                }
            },{requestFailed(it)})
        compositeDisposable.add(dispose)
    }

    fun createScheduleTrip(bodyCreate:BodyCreateTrip){
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc
        val jsonObj = JSONObject(Gson().toJson(bodyCreate))
        val param = Param.getParams(jsonObj)
        val resquestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),param.toString())
        val dispose  = newsService.createTrip(resquestBody).subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
                if (it != null) {
                    requestSuccess(it)
                } else {
                    requestSuccess(null)
                }
            },{requestFailed(it)})
        compositeDisposable.add(dispose)
    }

    fun getDetailplaceById(scheduleId:String){
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc
        val dispose  = newsService.getDetailPlaceByScheduleid(scheduleId).subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
                if (it != null) {
                    requestSuccess(it)
                } else {
                    requestSuccess(null)
                }
            },{requestFailed(it)})
        compositeDisposable.add(dispose)
    }

    private fun requestSuccess(data: Any?) {
        setChanged()
        notifyObservers(data)
    }

    private fun requestFailed(throwable: Throwable) {
        try {
            onLoadFail()
        } catch (e: Exception) {
        }
        try {
            val error = throwable as HttpException
            val errorBody = error.response().errorBody()!!.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            setChanged()
            notifyObservers(errorResponse)
        } catch (e: Exception) {
            setChanged()
            notifyObservers()
        }
    }

}