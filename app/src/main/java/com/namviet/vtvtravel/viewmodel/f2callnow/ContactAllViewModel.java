package com.namviet.vtvtravel.viewmodel.f2callnow;

import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.model.f2.CheckCountInviteFail;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.f2callnow.CallListResponse;
import com.namviet.vtvtravel.response.f2callnow.CheckNumberHaveInvitedOrNot;
import com.namviet.vtvtravel.ultils.ResponseUltils;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import retrofit2.HttpException;

public class ContactAllViewModel extends BaseViewModel {

    public void callList(List<String> list, boolean isCheckOneNumber) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.callList(list)).toString());

        Disposable disposable = newsService.callList(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CallListResponse>() {
                    @Override
                    public void accept(CallListResponse callListResponse) throws Exception {
                        if (callListResponse != null) {
                            if(callListResponse.isSuccess()) {
                                if (isCheckOneNumber){
                                    CheckNumberHaveInvitedOrNot checkNumberHaveInvitedOrNot = new CheckNumberHaveInvitedOrNot();
                                    checkNumberHaveInvitedOrNot.setCallListResponse(callListResponse);
                                    requestSuccess(checkNumberHaveInvitedOrNot);
                                }else {
                                    requestSuccess(callListResponse);
                                }
                            }
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (isCheckOneNumber){
                            requestFail2(new CheckCountInviteFail());
                            return;
                        }
                        requestFailed(throwable);

                    }
                });

        compositeDisposable.add(disposable);
    }

    private void requestSuccess(Object object) {
        setChanged();
        notifyObservers(object);
    }

    private void requestFail2(CheckCountInviteFail checkCountInviteFail){
        setChanged();
        notifyObservers(checkCountInviteFail);
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
