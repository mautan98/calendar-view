package com.namviet.vtvtravel.viewmodel.f2biglocation;

import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.api.WSConfig;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.City;
import com.namviet.vtvtravel.response.WeatherResponse;
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse;
import com.namviet.vtvtravel.response.f2biglocation.LocationResponse;
import com.namviet.vtvtravel.response.f2biglocation.TopLocationResponse;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;


public class SearchBigLocationViewModel extends BaseViewModel {


    @Inject
    public SearchBigLocationViewModel() {
    }

    public void getLocation() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getLocation(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LocationResponse>() {
                    @Override
                    public void accept(LocationResponse response) throws Exception {
                        if (response != null) {
                            requestSuccess(response);
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

    public void getTopLocation() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getTopLocation(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TopLocationResponse>() {
                    @Override
                    public void accept(TopLocationResponse response) throws Exception {
                        if (response != null) {
                            requestSuccess(response);
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

    public void getAllLocation() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getAllLocation(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AllLocationResponse>() {
                    @Override
                    public void accept(AllLocationResponse response) throws Exception {
                        if (response != null) {
                            requestSuccess(response);
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

    private void requestSuccess(Object object) {
        setChanged();
        notifyObservers(object);
    }

    private void requestFailed(Throwable throwable) {
        try {
            onLoadFail();
        } catch (Exception e) {

        }

        try {
            HttpException error = (HttpException) throwable;
            String errorBody = error.response().errorBody().string();
            ErrorResponse errorResponse = new Gson().fromJson(errorBody, ErrorResponse.class);
            setChanged();
            notifyObservers(errorResponse);
        } catch (Exception e) {
            setChanged();
            notifyObservers();
        }
    }
}
