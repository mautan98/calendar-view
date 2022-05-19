package com.namviet.vtvtravel.view.f3.search.viewmodel

import com.google.gson.Gson
import com.namviet.vtvtravel.api.Param
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.HttpException
import javax.inject.Inject

class SearchSuggestionViewModel @Inject constructor() : BaseViewModel() {
    fun getSearchSuggestion(keyword: String, regionId: String?) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelService
        val queryMap = Param.getDefault()
        val disposable = newsService.getSearchSuggestion(queryMap, keyword, regionId)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ videoResponse ->
                    videoResponse?.let {
                        requestSuccess(it)
                    }
                })
                { throwable ->
                    requestFailed(throwable!!)
                }
        compositeDisposable.add(disposable)
    }


    fun getSearchSuggestionForSpecificContent(keyword: String, regionId: String?, contentType : String?) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelService
        val queryMap = Param.getDefault()
        val disposable = newsService.getSearchSuggestionForSpecificContent(queryMap, keyword, regionId, contentType)
            .subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ videoResponse ->
                videoResponse?.let {
                    requestSuccess(it)
                }
            })
            { throwable ->
                requestFailed(throwable!!)
            }
        compositeDisposable.add(disposable)
    }


    private fun requestSuccess(param: Any?) {
        setChanged()
        notifyObservers(param)
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