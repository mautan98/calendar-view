package com.namviet.vtvtravel.viewmodel.newhome;

import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.response.ResponseError;
import com.namviet.vtvtravel.response.newhome.HomeServiceResponse;
import com.namviet.vtvtravel.ultils.ResponseUltils;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

public class SplashViewModel extends BaseViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void getHomeService() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getHomeService(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HomeServiceResponse>() {
                    @Override
                    public void accept(HomeServiceResponse homeServiceResponse) throws Exception {
                        if (homeServiceResponse != null) {
                            requestSuccess(homeServiceResponse);
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
            notifyObservers(new ResponseError(""));
        }
    }
}
