package com.namviet.vtvtravel.viewmodel.f2share

import com.google.gson.Gson
import com.namviet.vtvtravel.api.Param
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.HttpException
import java.util.*

class ShareViewModel : BaseViewModel() {
    fun share(contentType: String?, detailLink: String?, listContact: ArrayList<String?>?) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelServiceAcc
        val jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Param.getParams2(
                Param.share(contentType, detailLink, listContact)).toString())
        val disposable = newsService.share(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response != null && response.isSuccess) {
                        requestSuccess(response)
                    } else {
                        requestSuccess(null)
                    }
                }) { throwable -> requestFailed(throwable, "") }
        compositeDisposable.add(disposable)
    }

    private fun requestSuccess(`object`: Any?) {
        setChanged()
        notifyObservers(`object`)
    }

    private fun requestFailed(throwable: Throwable, code: String) {
        try {
            onLoadFail()
        } catch (e: Exception) {
        }
        try {
            val error = throwable as HttpException
            val errorBody = error.response().errorBody()!!.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            errorResponse.codeToSplitCase = code
            setChanged()
            notifyObservers(errorResponse)
        } catch (e: Exception) {
            setChanged()
            notifyObservers()
        }
    }
}