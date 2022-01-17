package com.namviet.vtvtravel.viewmodel.f2search

import com.google.gson.Gson
import com.namviet.vtvtravel.api.Param
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.HttpException
import javax.inject.Inject

class SearchViewModel @Inject constructor() : BaseViewModel() {
    fun getYourVoucher(link: String) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelService
        val disposable = newsService.getYourVoucher(link)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ videoResponse -> videoResponse?.let { requestSuccess(it) } }) { throwable -> requestFailed(throwable!!) }
        compositeDisposable.add(disposable)
    }

    fun getTrend(link: String) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelService
        val queryMap = Param.getDefault()
        val disposable = newsService.getTrend(link, queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ videoResponse -> videoResponse?.let { requestSuccess(it) } }) { throwable -> requestFailed(throwable!!) }
        compositeDisposable.add(disposable)
    }

    fun getBlockSearch() {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelService
        val queryMap = Param.getDefault()
        val disposable = newsService.getBlockSearch(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ videoResponse -> videoResponse?.let { requestSuccess(it) } }) { throwable -> requestFailed(throwable!!) }
        compositeDisposable.add(disposable)
    }


    fun getPreResultSearch(keyword: String, regionId: String?) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelService
        val queryMap = Param.getDefault()
        val disposable = newsService.getPreResultSearch(queryMap, keyword, regionId)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ videoResponse ->
                    videoResponse?.let { requestSuccess(it) }
                }) { throwable -> requestFailed(throwable!!) }
        compositeDisposable.add(disposable)
    }

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


    fun getLocation() {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelService
        val queryMap = Param.getDefault()
        val disposable = newsService.getLocation(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> response?.let { requestSuccess(it) } }) { throwable -> requestFailed(throwable!!) }
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