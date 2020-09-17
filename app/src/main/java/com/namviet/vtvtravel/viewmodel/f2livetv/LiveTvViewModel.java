package com.namviet.vtvtravel.viewmodel.f2livetv;

import com.google.gson.Gson;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.f2livetv.LiveTvResponse;
import com.namviet.vtvtravel.response.newhome.BaseResponseNewHome;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;
import com.namviet.vtvtravel.viewmodel.newhome.NewHomeViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;


public class LiveTvViewModel extends BaseViewModel {

    public void getLiveTvData(String detailLink) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Disposable disposable = newsService.getLiveTvData(detailLink)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponseNewHome>() {
                    @Override
                    public void accept(BaseResponseNewHome baseResponseNewHome) throws Exception {
                        requestSuccess(baseResponseNewHome);

                    }
                }, throwable -> {
                    requestFailed(throwable);
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
