package com.namviet.vtvtravel.viewmodel.f2operator;

import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.f2operator.OperatorInformationResponse;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.HttpException;

public class OperatorViewModel extends BaseViewModel {
    public void getOperatorInformation() {
        RequestBody jsonBodyObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Param.getParams2(
                new JSONObject()).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.getOperatorInformation(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OperatorInformationResponse>() {
                    @Override
                    public void accept(OperatorInformationResponse baseResponse) throws Exception {
                        if (null != baseResponse && baseResponse.isSuccess()) {
                            requestSuccess(baseResponse);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable, "getOperatorInformation");
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
