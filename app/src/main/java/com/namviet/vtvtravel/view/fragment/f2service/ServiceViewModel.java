package com.namviet.vtvtravel.view.fragment.f2service;

import android.content.Context;

import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import retrofit2.HttpException;

public class ServiceViewModel extends BaseViewModel {
    private Context context;

    public ServiceViewModel(Context context) {
        this.context = context;
    }

    public void getService(String channel) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        Map<String, Object> map = Param.getService(channel);
        Disposable disposable = newsService.getService(map)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ServiceResponse>() {
                    @Override
                    public void accept(ServiceResponse serviceResponse) throws Exception {
                        if (null != serviceResponse) {
                            requestSuccess(serviceResponse);
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

    public void requestServiceOtp(String mobile, String packageCode, String channel) {
        RequestBody jsonBodyObject = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                Param.getParams(Param.requestServiceOtp(mobile, packageCode, channel)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.requestServiceOtp(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ServiceOtpResponse>() {
                    @Override
                    public void accept(ServiceOtpResponse baseResponse) throws Exception {
                        requestSuccess(baseResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable);
                    }
                });

        compositeDisposable.add(disposable);
    }

    public void confirmServiceOtp(String mobile, String packageCode, String channel, String otp) {
        RequestBody jsonBodyObject = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                Param.getParams(Param.confirmServiceOtp(mobile, packageCode, channel, otp)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.confirmServiceOtp(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ServiceOtpResponse>() {
                    @Override
                    public void accept(ServiceOtpResponse baseResponse) throws Exception {
                        requestSuccess(baseResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable);
                    }
                });

        compositeDisposable.add(disposable);
    }

    public void resentServiceOtp(String mobile, String packageCode, String channel) {
        RequestBody jsonBodyObject = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                Param.getParams(Param.resentServiceOtp(mobile, packageCode, channel)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.resentServiceOtp(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResentOtpServiceResponse>() {
                    @Override
                    public void accept(ResentOtpServiceResponse baseResponse) throws Exception {
                        requestSuccess(baseResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable);
                    }
                });

        compositeDisposable.add(disposable);
    }

    public void getInfo(String code, String token) {
        RequestBody jsonBodyObject = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                Param.getParams(Param.getInfo(code)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.getInfo(jsonBodyObject, token)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetInfoResponse>() {
                    @Override
                    public void accept(GetInfoResponse baseResponse) throws Exception {
                        if(baseResponse != null && baseResponse.isSuccess()) {
                            requestSuccess(baseResponse);
                        }else {
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

    private void requestSuccess(Object obj) {
        setChanged();
        notifyObservers(obj);
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
            errorResponse.setCodeToSplitCase("");
            setChanged();
            notifyObservers(errorResponse);
        } catch (Exception e) {
            setChanged();
            notifyObservers();
        }
    }
}
