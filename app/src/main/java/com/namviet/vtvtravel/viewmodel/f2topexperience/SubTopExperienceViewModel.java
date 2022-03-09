package com.namviet.vtvtravel.viewmodel.f2topexperience;

import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.f2biglocation.RegionResponse;
import com.namviet.vtvtravel.response.f2topexperience.SubTopExperienceResponse;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

public class SubTopExperienceViewModel extends BaseViewModel {
    public void getSubTopExperience(String url, boolean isLoadMore) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.getSubTopExperience(url)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SubTopExperienceResponse>() {
                    @Override
                    public void accept(SubTopExperienceResponse response) throws Exception {
                        if (response != null) {
                            response.setLoadMore(isLoadMore);
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

    public void getSubTopExperience(String url, int type) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.getSubTopExperience(url)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SubTopExperienceResponse>() {
                    @Override
                    public void accept(SubTopExperienceResponse response) throws Exception {
                        if (response != null) {
                            response.setType(type);
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

//        try {
//            HttpException error = (HttpException) throwable;
//            String errorBody = error.response().errorBody().string();
//            ErrorResponse errorResponse = new Gson().fromJson(errorBody, ErrorResponse.class);
//            setChanged();
//            notifyObservers(errorResponse);
//        } catch (Exception e) {
//            setChanged();
//            notifyObservers();
//        }
        setChanged();
        notifyObservers(new ErrorResponse());
    }

}
