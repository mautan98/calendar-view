package com.namviet.vtvtravel.view.f3.notification;

import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.Schedule;
import com.namviet.vtvtravel.response.f2systeminbox.CountSystemInbox;
import com.namviet.vtvtravel.view.f3.notification.model.ui.NotificationResponse;
import com.namviet.vtvtravel.response.f2systeminbox.SystemInbox;
import com.namviet.vtvtravel.view.f3.notification.model.Notification;
import com.namviet.vtvtravel.view.f3.notification.model.ui.NotificationResponse;
import com.namviet.vtvtravel.view.f3.notification.model.ui.NotificationTab;
import com.namviet.vtvtravel.view.f3.notification.model.ui.UpdateNotificationResponse;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
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
                .subscribe(new Consumer<NotificationResponse>() {
                    @Override
                    public void accept(NotificationResponse response) throws Exception {
                        if (response != null && response.isSuccess()) {
                            requestSuccess(response);
                        } else {
                            requestSuccess(null);
                        }
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
        Disposable disposable = newsService.getNotificationByStatus("0", jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NotificationResponse>() {
                    @Override
                    public void accept(NotificationResponse response) throws Exception {
                        if (response != null && response.isSuccess()) {
                            requestSuccess(response);
                        } else {
                            requestSuccess(null);
                        }
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
                .subscribe(new Consumer<NotificationResponse>() {
                    @Override
                    public void accept(NotificationResponse response) throws Exception {
                        if (response != null && response.isSuccess()) {
                            requestSuccess(response);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        compositeDisposable.add(disposable);
    }

    public void getNotification() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Param.getParams2(new JSONObject()).toString());
        Disposable disposable = newsService.getAllNotification(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NotificationResponse>() {
                    @Override
                    public void accept(NotificationResponse response) throws Exception {
                        if (response != null && response.isSuccess()) {
                            requestSuccess(response);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        compositeDisposable.add(disposable);
    }

    public void updateMark(String id, String status, int position) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Param.getParams2(Param.updateInbox(id, status)).toString());
        Disposable disposable = newsService.updateMark(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdateNotificationResponse>() {
                    @Override
                    public void accept(UpdateNotificationResponse response) throws Exception {
                        if (response != null && response.isSuccess()) {
                            response.setPosition(position);
                            requestSuccess(response);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        compositeDisposable.add(disposable);
    }

    public void updateInbox(String id, String status, int position) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Param.getParams2(Param.updateInbox(id, status)).toString());
        Disposable disposable = newsService.updateInbox(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdateNotificationResponse>() {
                    @Override
                    public void accept(UpdateNotificationResponse response) throws Exception {
                        if (response != null && response.isSuccess()) {
                            response.setPosition(position);
                            requestSuccess(response);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        compositeDisposable.add(disposable);
    }

    public void updateViewedAllInbox() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Param.getParams2(Param.updateViewedAllInbox()).toString());
        Disposable disposable = newsService.updateInbox(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdateNotificationResponse>() {
                    @Override
                    public void accept(UpdateNotificationResponse response) throws Exception {
                        if (response != null && response.isSuccess()) {
                            response.setPosition(-1);
                            requestSuccess(response);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        compositeDisposable.add(disposable);
    }

    public void getNotificationTab() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        Disposable disposable = newsService.getNotificationTab("https://core-testing.vtvtravel.vn/sys/v1/notifications/types/list")
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NotificationTab>() {
                    @Override
                    public void accept(NotificationTab response) throws Exception {
                        if (response != null && response.isSuccess()) {
                            requestSuccess(response);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        compositeDisposable.add(disposable);
    }



    public void getSystemInboxCount() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.getCountSystemInbox()
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CountSystemInbox>() {
                    @Override
                    public void accept(CountSystemInbox countSystemInbox) throws Exception {
                        if (countSystemInbox != null) {
                            if (countSystemInbox.isSuccess()) {
                                requestSuccess(countSystemInbox);
                            }
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable, "");
                    }
                });

        compositeDisposable.add(disposable);
    }

    public void logout(String token){
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        RequestBody jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Param.getParams2(Param.logout(token)).toString());
        Disposable disposable = newsService.logout(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

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
