package com.namviet.vtvtravel.viewmodel;

import android.content.Context;

import com.baseapp.utils.L;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.model.City;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.FilterResponse;
import com.namviet.vtvtravel.response.FilterSearchResponse;
import com.namviet.vtvtravel.response.PlaceResponse;
import com.namviet.vtvtravel.response.SearchResponse;
import com.namviet.vtvtravel.response.SearchResultResponse;
import com.namviet.vtvtravel.response.WeatherResponse;
import com.namviet.vtvtravel.response.f2callnow.CallNowHistoryResponse;
import com.namviet.vtvtravel.response.f2callnow.ZipVersionResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SearchViewModel extends BaseViewModel {
    public SearchViewModel(Context context) {
        this.context = context;
    }


    public void loadSearch(String keyWord) {
        Map<String, Object> param = Param.getSearchList(keyWord, 1);
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.getSearchSuggest(param)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SearchResponse>() {
                    @Override
                    public void accept(SearchResponse searchResponse) throws Exception {
                        if (searchResponse != null && null != searchResponse.getData()) {
                            resultSearch(searchResponse);
                        } else {
                            resultSearch(null);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable);
                    }
                });

        compositeDisposable.add(disposable);
    }

    private void resultSearch(Object object) {
        setChanged();
        notifyObservers(object);
    }

    public void loadSearchTrend() {
        Map<String, Object> param = Param.getDefault();
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.loadSearchTrend(param)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResponse -> {
                    if (searchResponse != null && null != searchResponse.getData()) {
                        resultSearch(searchResponse);
                    } else {
                        resultSearch(null);
                    }

                }, throwable -> requestFailed(throwable));

        compositeDisposable.add(disposable);
    }

    public void loadSearchResult(String trim, int page) {
        Map<String, Object> param = Param.getSearchList(trim, page);
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.loadSearchResult(param)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SearchResultResponse>() {
                    @Override
                    public void accept(SearchResultResponse searchResponse) throws Exception {
                        if (searchResponse != null && null != searchResponse.getData()) {
                            resultSearch(searchResponse);
                        } else {
                            resultSearch(null);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable);
                    }
                });

        compositeDisposable.add(disposable);
    }

    public void loadListSearchVoucher(String link) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.loadListSearchVoucher(link)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SearchResultResponse>() {
                    @Override
                    public void accept(SearchResultResponse placeResponse) throws Exception {
                        if (placeResponse != null && null != placeResponse.getData()) {
                            resultSearch(placeResponse);
                        } else {
                            resultSearch(null);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable);
                    }
                });

        compositeDisposable.add(disposable);
    }

    public void loadFilterSearch() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.loadFilterSearch(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FilterSearchResponse>() {
                    @Override
                    public void accept(FilterSearchResponse filterResponse) throws Exception {
                        if (filterResponse != null && null != filterResponse.getData()) {
                            resultSearch(filterResponse);
                        } else {
                            resultSearch(null);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable);
                    }
                });

        compositeDisposable.add(disposable);
    }

    public void zipVersion() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Map<String, Object> queryMap = Param.zipVersion();

        Disposable disposable = newsService.zipVersion(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ZipVersionResponse>() {
                    @Override
                    public void accept(ZipVersionResponse zipVersionResponse) throws Exception {
                        if (zipVersionResponse != null) {
                            if(zipVersionResponse.getData() != null) {
                                resultSearch(zipVersionResponse);
                            }
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable);
                    }
                });

        compositeDisposable.add(disposable);
    }

    private void requestFailed(Throwable throwable) {
        try {
            onLoadFail();
        } catch (Exception e) {

        }
        try {
            setChanged();
            notifyObservers(ResponseUltils.requestFailed(throwable));
        } catch (Exception e) {
            setChanged();
            notifyObservers();
        }
    }
}
