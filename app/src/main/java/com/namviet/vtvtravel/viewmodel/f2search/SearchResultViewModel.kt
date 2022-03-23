package com.namviet.vtvtravel.viewmodel.f2search

import com.google.gson.Gson
import com.namviet.vtvtravel.api.Param
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.HttpException

class SearchResultViewModel : BaseViewModel() {
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


    fun getPreResultSearch(keyword: String, regionId: String?, isLoadMore: Boolean) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelService
        val queryMap = Param.getDefault()
        val disposable = newsService.getPreResultSearch(queryMap, keyword, regionId)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ videoResponse ->
                    videoResponse?.let {
                        it.isLoadMore = isLoadMore
                        requestSuccess(it)
                    }
                })
                { throwable ->
                    requestFailed(throwable!!)
                }
        compositeDisposable.add(disposable)
    }

    fun getPreResultSearch(link: String, isLoadMore: Boolean) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelService
        val queryMap = Param.getDefault()
        val disposable = newsService.getPreResultSearch(link)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ videoResponse ->
                    videoResponse?.let {
                        it.isLoadMore = isLoadMore
                        requestSuccess(it)
                    }
                })
                { throwable ->
                    requestFailed(throwable!!)
                }
        compositeDisposable.add(disposable)
    }


    fun searchAll(path: String?, keyword: String?, regionId: String?, type:String?, categoryCode: String?,
                  district_id : String?, ward_id : String?, open : Boolean?, sort : String?, content_type : String?) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelService
        val queryMap = Param.getDefault()
        val disposable = newsService.searchAll(path, queryMap,  keyword, regionId, categoryCode, district_id, ward_id, open, sort, content_type)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    videoResponse ->
                    videoResponse?.let {
                        it.type = type
                        requestSuccess(it)
                    }
                })
                { throwable ->
                    requestFailed(throwable!!)
                }
        compositeDisposable.add(disposable)
    }

    fun searchAllWithFullLink(link:String?, type: String?) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelService
        val queryMap = Param.getDefault()
        val disposable = newsService.searchAllWithFullLink( link, queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ videoResponse ->
                    videoResponse?.let {
                        it.type = type
                        requestSuccess(it)
                    }
                })
                { throwable ->
                    requestFailed(throwable!!)
                }
        compositeDisposable.add(disposable)
    }


    fun searchAllVideo(path: String?, keyword: String?, regionId: String?, type:String?, categoryCode : String?,
                       district_id : String?, ward_id : String?, open : Boolean?, sort : String?, content_type : String?) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelService
        val queryMap = Param.getDefault()
        val disposable = newsService.searchAllVideo(path, queryMap,  keyword, regionId, categoryCode, district_id, ward_id, open, sort, content_type)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    videoResponse ->
                    videoResponse?.let {
                        requestSuccess(it)
                    }
                })
                { throwable ->
                    requestFailed(throwable!!)
                }
        compositeDisposable.add(disposable)
    }

    fun searchAllVideoWithFullLink(link:String?, type: String?) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelService
        val queryMap = Param.getDefault()
        val disposable = newsService.searchAllVideoWithFullLink( link, queryMap)
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



    fun searchAllImage(path: String?, keyword: String?, regionId: String?, type:String?, categoryCode : String?) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelService
        val queryMap = Param.getDefault()
        val disposable = newsService.searchAllImage(path, queryMap,  keyword, regionId, categoryCode)
            .subscribeOn(myApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    videoResponse ->
                videoResponse?.let {
                    requestSuccess(it)
                }
            })
            { throwable ->
                requestFailed(throwable!!)
            }
        compositeDisposable.add(disposable)
    }

    fun searchAllImageWithFullLink(link:String?, type: String?) {
        val myApplication = MyApplication.getInstance()
        val newsService = myApplication.travelService
        val queryMap = Param.getDefault()
        val disposable = newsService.searchAllImageWithFullLink( link, queryMap)
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