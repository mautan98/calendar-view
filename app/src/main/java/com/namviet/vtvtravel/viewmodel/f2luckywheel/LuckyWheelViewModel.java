package com.namviet.vtvtravel.viewmodel.f2luckywheel;

import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.f2chat.GetUserGuildResponse;
import com.namviet.vtvtravel.response.f2review.CreateReviewResponse;
import com.namviet.vtvtravel.response.f2review.GetReviewResponse;
import com.namviet.vtvtravel.response.f2topexperience.SubTopExperienceResponse;
import com.namviet.vtvtravel.response.f2wheel.RuleLuckyWheel;
import com.namviet.vtvtravel.response.f2wheel.WheelActionResponse;
import com.namviet.vtvtravel.response.f2wheel.WheelAreasResponse;
import com.namviet.vtvtravel.response.f2wheel.WheelChartResponse;
import com.namviet.vtvtravel.response.f2wheel.WheelResultResponse;
import com.namviet.vtvtravel.response.f2wheel.WheelRotateResponse;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import retrofit2.HttpException;

public class LuckyWheelViewModel extends BaseViewModel {


    public void getWheelChart() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.getWheelChartResponse()
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WheelChartResponse>() {
                    @Override
                    public void accept(WheelChartResponse baseResponse) throws Exception {
                        if (null != baseResponse && baseResponse.isSuccess()) {
                            requestSuccess(baseResponse);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable, "getWheelChart");
                    }
                });

        compositeDisposable.add(disposable);
    }

    public void wheelRotate(String wheelLogId, String service, String wheelId) {
        RequestBody jsonBodyObject = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                Param.getParams2(Param.wheelRotate(wheelLogId, service, wheelId)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.wheelRotate(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WheelRotateResponse>() {
                    @Override
                    public void accept(WheelRotateResponse response) throws Exception {
                        if (response != null && response.isSuccess()) {
                            requestSuccess(response);
                        } else if(response != null && !response.isSuccess()&& response.getErrorCode().equals("USER_PACKAGE_NOT_VIP")){
                            requestSuccess(response);
                        }else if(response != null && !response.isSuccess()&& response.getErrorCode().equals("NO_TURN")){
                            requestSuccess(response);
                        }else {
                            requestSuccess(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable, "wheelRotate");
                    }
                });

        compositeDisposable.add(disposable);
    }

    public void wheelResult(String service, String os, String channel) {
        RequestBody jsonBodyObject = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                Param.getParams2(Param.wheelResult(service, os, channel)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.wheelResult(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WheelResultResponse>() {
                    @Override
                    public void accept(WheelResultResponse response) throws Exception {
                        if (response != null && response.isSuccess()) {
                            requestSuccess(response);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable, "wheelResult");
                    }
                });

        compositeDisposable.add(disposable);
    }

    public void wheelAreas(String service, List<String> listIdVoucher) {
        RequestBody jsonBodyObject = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                Param.getParams2(Param.wheelAreas(service, listIdVoucher)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.wheelAreas(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WheelAreasResponse>() {
                    @Override
                    public void accept(WheelAreasResponse response) throws Exception {
                        if (response != null && response.isSuccess()) {
                            requestSuccess(response);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable, "wheelAreas");
                    }
                });

        compositeDisposable.add(disposable);
    }

    public void wheelAction(String service, String os, String channel) {
        RequestBody jsonBodyObject = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                Param.getParams2(Param.wheelResult(service, os, channel)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.wheelAction(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WheelActionResponse>() {
                    @Override
                    public void accept(WheelActionResponse response) throws Exception {
                        if (response != null && response.isSuccess()) {
                            requestSuccess(response);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable, "wheelAction");
                    }
                });

        compositeDisposable.add(disposable);
    }

    public void getRuleOrPlayRuleLuckyWheel(String link) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.getRuleOrPlayRuleLuckyWheel(link)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RuleLuckyWheel>() {
                    @Override
                    public void accept(RuleLuckyWheel response) throws Exception {
                        if (response != null) {
                            requestSuccess(response);
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


    public void getVQMMHistories(String link) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Disposable disposable = newsService.getVQMMHistories(link)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WheelChartResponse>() {
                    @Override
                    public void accept(WheelChartResponse response) throws Exception {
                        if (response != null) {
                            requestSuccess(response);
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
