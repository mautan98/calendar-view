package com.namviet.vtvtravel.viewmodel;

import android.content.Context;
import android.view.View;

import com.baseapp.menu.SlideMenu;
import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.listener.F2LoadFailListener;
import com.namviet.vtvtravel.model.f2event.OnLoadFail;
import com.namviet.vtvtravel.ultils.ResponseUltils;
import com.namviet.vtvtravel.view.MainActivity;
import com.namviet.vtvtravel.view.fragment.f2service.ResentOtpServiceResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import retrofit2.HttpException;

public class BaseViewModel extends Observable implements F2LoadFailListener {
    private static BaseViewModel baseViewModel;

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean isLoading = false;
    public static BaseViewModel getInstance() {
        if (baseViewModel == null) {
            baseViewModel = new BaseViewModel();
        }
        return baseViewModel;
    }

    private String title = "Địa điểm";

    public String getTitleToolBar() {
        return title;
    }

    public void setTitleToolBar(String title) {
        this.title = title;
    }

    public void onClickBack(View view) {
    }

    protected Context context;
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }

    public void trackLocation(double lat, double lng, String deviceId) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();

        Disposable disposable = newsService.trackLocation(lat, lng, "APP", "ANDROID", deviceId)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {


                }, throwable -> {
                });

        compositeDisposable.add(disposable);

    }

    public void likeEvent(String contentId, String type) {
        RequestBody jsonBodyObject = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                Param.getParams(Param.likeEvent(contentId, type)).toString());
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();

        Disposable disposable = newsService.likeEvent(jsonBodyObject)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, throwable -> {
                });

        compositeDisposable.add(disposable);
    }

    public void requestSuccessRes(Object data) {
        setChanged();
        notifyObservers(data);
    }

    public void requestFailedRes(Throwable throwable) {
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

    @Override
    public void onLoadFail() {
        EventBus.getDefault().post(new OnLoadFail());
    }

}
