package com.namviet.vtvtravel.viewmodel;

import android.content.Context;
import android.util.Log;

import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.NotificationCategoriesResponse;
import com.namviet.vtvtravel.response.NotifyResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class NotifyViewModel extends BaseViewModel {

    public NotifyViewModel(Context context) {
        this.context = context;
    }

    public void getListNotify(String token, String deviceId) {
        Map<String, Object> queryMap = Param.getListNotify(deviceId);
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Disposable disposable = newsService.getListNotify(token, queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    if (null != baseResponse) {
                        loadSuccess(baseResponse);
                    } else {
                        loadSuccess(null);
                    }
                }, throwable -> requestFailed(throwable));

        compositeDisposable.add(disposable);
    }

    public void getListNotifyMore(String url) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Disposable disposable = newsService.getListNotifyMore(url)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    if (null != baseResponse) {
                        loadSuccess(baseResponse);
                    } else {
                        loadSuccess(null);
                    }

                }, throwable -> requestFailed(throwable));

        compositeDisposable.add(disposable);
    }

    private void loadSuccess(Object object) {
        setChanged();
        notifyObservers(object);
    }

    public void readNotify(String view_link) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Disposable disposable = newsService.readNotify(view_link)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    if (null != baseResponse) {
                        loadSuccess(baseResponse);
                    } else {
                        loadSuccess(null);
                    }

                }, throwable -> requestFailed(throwable));

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
