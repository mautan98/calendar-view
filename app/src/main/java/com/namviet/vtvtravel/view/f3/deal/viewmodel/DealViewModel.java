package com.namviet.vtvtravel.view.f3.deal.viewmodel;

import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.view.f3.deal.model.BlockResponse;
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

public class DealViewModel extends BaseViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public void getDealBlock() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getBlockDeal(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BlockResponse>() {
                    @Override
                    public void accept(BlockResponse blockResponse) throws Exception {
                        if (blockResponse != null) {
                            requestSuccess(blockResponse);
                        } else {
                            requestSuccess(null);
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
    public void getDeal(String url) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        Disposable disposable = newsService.getDeal(url)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DealResponse>() {
                    @Override
                    public void accept(DealResponse blockResponse) throws Exception {
                        if (blockResponse != null) {
                            requestSuccess(blockResponse);
                        } else {
                            requestSuccess(null);
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
