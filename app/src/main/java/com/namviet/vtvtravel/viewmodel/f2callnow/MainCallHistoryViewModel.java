package com.namviet.vtvtravel.viewmodel.f2callnow;

import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.DetailResponse;
import com.namviet.vtvtravel.response.f2callnow.CallNowHistoryResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import retrofit2.HttpException;

public class MainCallHistoryViewModel extends BaseViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void getCallHistory() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.getCallHistory()
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CallNowHistoryResponse>() {
                    @Override
                    public void accept(CallNowHistoryResponse callNowHistoryResponse) throws Exception {
                        if (callNowHistoryResponse != null) {
                            if (callNowHistoryResponse.isSuccess()) {
                                requestSuccess(callNowHistoryResponse);
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

    public void deleteCallHistory(String type, List<String> callList) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        RequestBody jsonBodyObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Param.getParams(Param.deleteCallHistory(type, callList)).toString());

        Disposable disposable = newsService.deleteCallHistory(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        if (baseResponse != null) {
                            if (baseResponse.isSuccess()) {
                                if (type.equals("ALL")) {
                                    getCallHistory();
                                } else {
                                    requestSuccess(baseResponse);
                                }
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
