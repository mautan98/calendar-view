package com.namviet.vtvtravel.viewmodel.f2callnow;

import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.f2callnow.VerifyUserResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import retrofit2.HttpException;

public class SearchNumberViewModel extends BaseViewModel {


    public void verifyUser(String mobile) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.verifyUser(mobile)).toString());

        Disposable disposable = newsService.verifyUser(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VerifyUserResponse>() {
                    @Override
                    public void accept(VerifyUserResponse verifyUserResponse) throws Exception {
                        if (verifyUserResponse != null) {
                            if(verifyUserResponse.isSuccess()) {
                                requestSuccess(verifyUserResponse);
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
            setChanged();
            notifyObservers(ResponseUltils.requestFailed(throwable));

            HttpException error = (HttpException) throwable;
            String errorBody = error.response().errorBody().string();
            ResponseUltils.logEventApiError(context, errorBody);
        } catch (Exception e) {
            setChanged();
            notifyObservers();
        }
    }
}
