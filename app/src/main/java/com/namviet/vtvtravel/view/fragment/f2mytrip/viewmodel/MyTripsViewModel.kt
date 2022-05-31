package com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel

import com.google.gson.Gson
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.createschedule.DataCreateTrips
import com.namviet.vtvtravel.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
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

    fun createScheduleTrip(bodyCreate:DataCreateTrips){
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc
        val dispose  = newsService.createTrip(bodyCreate).subscribeOn(myApplication.subscribeScheduler())
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