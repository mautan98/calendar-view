package com.namviet.vtvtravel.view.fragment.f2createtrip.viewmodel

import com.google.gson.Gson
import com.namviet.vtvtravel.api.Param
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.createschedule.BodyCreateTrip
import com.namviet.vtvtravel.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import okhttp3.RequestBody
import org.json.JSONObject

class CreateTripViewModel : BaseViewModel() {

    fun createScheduleTrip(bodyCreate: BodyCreateTrip){
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc
        val jsonObj = JSONObject(Gson().toJson(bodyCreate))
        val param = Param.getParams(jsonObj)
        val resquestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),param.toString())
        val dispose  = newsService.createTrip(resquestBody).subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
                if (it != null) {
                    requestSuccessRes(it)
                } else {
                    requestSuccessRes(null)
                }
            },{requestFailedRes(it)})
        compositeDisposable.add(dispose)
    }
}