package com.namviet.vtvtravel.view.f3.notification;

import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.NotifyResponse;
import com.namviet.vtvtravel.response.f2systeminbox.SystemInbox;
import com.namviet.vtvtravel.view.f3.notification.model.Notification;
import com.namviet.vtvtravel.view.f3.notification.model.ui.NotificationResponse;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.HttpException;

public class NotificationViewModel extends BaseViewModel {


    public void getNotificationByType(String type) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Param.getParams2(new JSONObject()).toString());
        Disposable disposable = newsService.getNotificationByType(type, jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NotifyResponse>() {
                    @Override
                    public void accept(NotifyResponse notifyResponse) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        compositeDisposable.add(disposable);
    }

    public void getNotificationByStatus() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Param.getParams2(new JSONObject()).toString());
        Disposable disposable = newsService.getNotificationByStatus(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NotifyResponse>() {
                    @Override
                    public void accept(NotifyResponse notifyResponse) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        compositeDisposable.add(disposable);
    }

    public void getNotificationByMark() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Param.getParams2(new JSONObject()).toString());
        Disposable disposable = newsService.getNotificationByMark(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NotifyResponse>() {
                    @Override
                    public void accept(NotifyResponse notifyResponse) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        compositeDisposable.add(disposable);
    }








    private void requestSuccess(Object object) {
        setChanged();
        notifyObservers(object);
    }

    private void requestFailed(Throwable throwable, String code) {
        try {
            onLoadFail();
        } catch (Exception e) {

        }
        try {
            HttpException error = (HttpException) throwable;
            String errorBody = error.response().errorBody().string();
            ErrorResponse errorResponse = new Gson().fromJson(errorBody, ErrorResponse.class);
            errorResponse.setCodeToSplitCase(code);
            setChanged();
            notifyObservers(errorResponse);
        } catch (Exception e) {
            setChanged();
            notifyObservers();
        }
    }
}
